package itmo.visits_control.dal.mssql;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import itmo.visits_control.dal.CrudDao;
import itmo.visits_control.entities.mssql.Department;
import itmo.visits_control.entities.mssql.Order;
import itmo.visits_control.entities.mssql.Person;
import itmo.visits_control.models.Escape;

public interface MSSQLDao extends CrudDao {
	List<Department> getNotHiddenDepartments();

	List<Person> getActualPersonal(LocalDate date);

	List<Order> findActualOrders(BigDecimal rateSize, int personalCode, Date searchDate);

	List<Order> checkOrderWorking(BigDecimal orderRate, int personalCode, Date orderDate, Date searchDate);

	String getWorkRegimes(int yerNumber, int month);

	List<Escape> getPersonMissions(int personalCode, Date firstDayOfMonth, Date firstDayOfNextMonth);

	List<Escape> getPersonDisabilities(int personalCode, Date firstDayOfMonth, Date firstDayOfNextMonth);

	List<Escape> getPersonLeaves(int personalCode, Date firstDayOfMonth, Date firstDayOfNextMonth);
	List<String> getPersonDeppartments(final int personalCode,Date date,Date nextMonthDate);
}
