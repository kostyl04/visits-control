package itmo.visits_control.components;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import itmo.visits_control.dal.firebird.FireBirdDao;
import itmo.visits_control.dal.mssql.MSSQLDao;
import itmo.visits_control.entities.firebird.Staff;
import itmo.visits_control.entities.firebird.VisitTime;
import itmo.visits_control.entities.mssql.Order;
import itmo.visits_control.exceptions.DataException;
import itmo.visits_control.models.Escape;
import itmo.visits_control.models.EscapeType;
import itmo.visits_control.models.PersonInfo;
import itmo.visits_control.models.PersonInfoStatus;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PersonInfoBuilder {
	@Autowired
	@Qualifier("MSSQLDao")
	private MSSQLDao mssqlDao;
	@Autowired
	@Qualifier("firebirdDao")
	private FireBirdDao firebirdDao;
	private String regimeString;
	private List<Duration> dayWorkingTime;
	private LocalDate date;
	private static final BigDecimal[] rateSizes = new BigDecimal[] { new BigDecimal(0.25), new BigDecimal(0.5),
			new BigDecimal(1) };
	private double rateProperty;

	public void getListRegimeDays(PersonInfo personInfo) {
		long fullReqieredHours;
		long workedOutHours = 0;
		long leaveHours;
		long disabilityHours;
		long missionHours;
		Duration duration = Duration.ofHours(0);
		Duration leaveDuration = Duration.ofHours(0);
		Duration disabilityDuration = Duration.ofHours(0);
		Duration missionDuration = Duration.ofHours(0);

		List<Duration> days = parseWorkRegime();
		for (Duration d : days) {
			duration = duration.plus(d);
		}
		fullReqieredHours = duration.toHours();
		List<Escape> escapes = getEscapes(personInfo.getPersonalCode());
		List<Order> orders = getActualOrders(personInfo.getPersonalCode(), Date.valueOf(this.date));
		double fullMonthRate = 0;
		for (Order o : orders) {
			fullMonthRate += o.getRateSize().doubleValue();
		}
		fullReqieredHours *= fullMonthRate>rateProperty?rateProperty:fullMonthRate;
		for (int i = 0; i < days.size(); i++) {
			LocalDate tempDate = date.plusDays(i);
			Double rate = 0d;
			int k = 1;

			for (Order o : orders) {

				rate += o.getDayRate(tempDate).doubleValue();

			}
			rate = rate > rateProperty ? rateProperty : rate;

			for (Escape e : escapes) {
				long l = (long) (days.get(i).toMillis() * rate);
				if (e.getType().equals(EscapeType.Disability)) {

					if (e.checkEscapeDate(tempDate)) {

						disabilityDuration = disabilityDuration.plus(Duration.ofMillis(l));
						break;
					}
				} else if (e.getType().equals(EscapeType.Leave)) {
					if (e.checkEscapeDate(tempDate)) {
						leaveDuration = leaveDuration.plus(Duration.ofMillis(l));
						break;
					}
				} else if (e.getType().equals(EscapeType.Mission)) {
					if (e.checkEscapeDate(tempDate)) {
						missionDuration = missionDuration.plus(Duration.ofMillis(l));
						break;
					}
				}
			}
		}
		Integer staffid = checkExistInVisits(personInfo);
		if (staffid != null) {
			workedOutHours = calculateRealTime(
					firebirdDao.findAllVisits(staffid, this.date.getYear(), this.date.getMonth().getValue())).toHours();
		} else {
			personInfo.setStatus(PersonInfoStatus.NotFoundInVisits);
		}
		List<String> personDeps=new ArrayList();
		leaveHours = leaveDuration.toHours();
		disabilityHours = disabilityDuration.toHours();
		missionHours = missionDuration.toHours();
		
		personInfo.setPersonDeps(mssqlDao.getPersonDeppartments(personInfo.getPersonalCode(),Date.valueOf(this.date),Date.valueOf(this.date.plusMonths(1))));
		personInfo.setDisabilityHours(disabilityHours);
		personInfo.setFullReqieredHours(fullReqieredHours);
		personInfo.setLeaveHours(leaveHours);
		personInfo.setMissionHours(missionHours);
		personInfo.setRateSize(fullMonthRate);
		personInfo.setWorkedOutHours(workedOutHours);

	}

	private List<Duration> parseWorkRegime() {
		List<Duration> dayWorkingTime = new ArrayList<Duration>();
		Matcher m = Pattern.compile("([0-9][0-9][\\.][0-9][0-9])").matcher(this.regimeString);
		while (m.find()) {
			Duration d = Duration.ofHours(0);
			String s = m.group();
			String[] tmparr = s.split("\\.");
			d = d.plusHours(Long.valueOf(tmparr[0]));
			d = d.plusMinutes(Long.valueOf(tmparr[1]));
			dayWorkingTime.add(d);

		}

		return dayWorkingTime;

	}

	private boolean workingRegimeStringIsValid(String regimeString) {
		Pattern p = Pattern.compile("([0-9][0-9][\\.][0-9][0-9])");
		boolean validLength = regimeString.trim().length() % 5 == 0 ? true : false;
		boolean patternMatch = p.matcher(regimeString).lookingAt();
		return validLength && patternMatch ? true : false;

	}

	private List<Order> getActualOrders(int personalCode, Date searchDate) {
		Date date = Date.valueOf(searchDate.toLocalDate().plusMonths(1));
		List<Order> orders = new ArrayList<>();
		for (BigDecimal rateSize : rateSizes) {
			List<Order> tempOrders = mssqlDao.findActualOrders(rateSize, personalCode, date);
			if (!tempOrders.isEmpty())
				orders.add(tempOrders.get(0));
		}
		for (Order o : orders)
			System.out.println(o);
		Iterator<Order> it = orders.iterator();
		while (it.hasNext()) {
			Order o = it.next();
			boolean orderIsActual = checkOrderWorking(o.getPersonalCode(), Date.valueOf(this.date),
					o.getComeIntoForce(), o.getRateSize());
			if (!orderIsActual) {
				it.remove();
			}
		}
		return orders;

	}

	private boolean checkOrderWorking(int personalCode, Date searchDate, Date orderDate, BigDecimal orderRate) {
		List<Order> list = mssqlDao.checkOrderWorking(orderRate, personalCode, orderDate, searchDate);
		for (Order o : list)
			System.out.println(o);
		return list.isEmpty();
	}

	private List<Escape> getEscapes(int personalCode) {
		List<Escape> escapes = new ArrayList<Escape>();
		Date firstDayOfNextMonth = Date.valueOf(date.plusMonths(1));

		Date firstDayOfMonth = Date.valueOf(date);
		escapes.addAll(mssqlDao.getPersonDisabilities(personalCode, firstDayOfMonth, firstDayOfNextMonth));
		escapes.addAll(mssqlDao.getPersonLeaves(personalCode, firstDayOfMonth, firstDayOfNextMonth));
		escapes.addAll(mssqlDao.getPersonMissions(personalCode, firstDayOfMonth, firstDayOfNextMonth));
		return escapes;
	}

	public PersonInfoBuilder buildMonthRegime(int month, int year, double rateProperty) {
		this.regimeString = mssqlDao.getWorkRegimes(year, month);
		this.dayWorkingTime = parseWorkRegime();
		this.date = LocalDate.of(year, month, 1);
		if (!workingRegimeStringIsValid(this.regimeString)) {
			throw new DataException("Не удалось получить режим работы за " + month + " месяц, " + year + " год!");
		}
		this.rateProperty = rateProperty;
		return this;
	}

	private Integer checkExistInVisits(PersonInfo personInfo) {
		List<Staff> list = firebirdDao.findIdByInitials(personInfo.getName(), personInfo.getLastName(),
				personInfo.getMiddleName());
		Integer i = list.isEmpty() ? null : list.get(0).getId();
		return i;
	}

	private Duration calculateRealTime(List<VisitTime> list) {
		Duration result = Duration.ofHours(0);
		LocalTime start = null;
		LocalTime end = null;
		int type = 1;
		for (VisitTime v : list) {
			if (v.getPassType().equals(type)) {
				if (type == 1) {
					start = v.getTimePass().toLocalTime();
					type = 2;
				}

				else {
					end = v.getTimePass().toLocalTime();
					type = 1;
				}

			}
			if (start != null && end != null) {
				LocalTime temp = end.minusHours(start.getHour()).minusMinutes(start.getMinute());
				result = result.plusHours(temp.getHour()).plusMinutes(temp.getMinute());
				start = null;
				end = null;
			}
		}
		return result;
	}

}
