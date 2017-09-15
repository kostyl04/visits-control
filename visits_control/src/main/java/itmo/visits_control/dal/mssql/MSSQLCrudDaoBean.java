package itmo.visits_control.dal.mssql;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import itmo.visits_control.entities.mssql.Department;
import itmo.visits_control.entities.mssql.Order;
import itmo.visits_control.entities.mssql.Person;
import itmo.visits_control.models.Escape;

@Repository(value = "MSSQLDao")
@Transactional(value = "mssqlTX")
public class MSSQLCrudDaoBean implements MSSQLDao {
	@Autowired
	@Qualifier("mssqlSessionFactory")
	private SessionFactory sessionFactory;

	/**
	 * Default constructor
	 */
	public MSSQLCrudDaoBean() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T merge(T t) {

		return (T) currentSession().merge(t);

	}

	public <T, PK extends Serializable> T find(Class<T> type, PK id) {
		System.out.println("getting object with id=" + id);
		return (T) currentSession().get(type, id);

	}

	public <T, PK extends Serializable> void delete(Class<T> type, PK id) {

		Session currentSession = currentSession();
		Object object = currentSession.get(type, id);
		currentSession.delete(object);

	}

	public <T> List<T> list(Class<T> type) {
		CriteriaBuilder builder = currentSession().getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(type);
		Root<T> contactRoot = criteria.from(type);
		criteria.select(contactRoot);
		return currentSession().createQuery(criteria).getResultList();

	}

	protected Session currentSession() {
		Session currentSession = sessionFactory.getCurrentSession();
		// Session currentSession = sessionFactory.openSession();
		return currentSession;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Department> getNotHiddenDepartments() {
		String queryString = "select d from Department d where d.hidden=false order by(departmentName)";
		Query query = currentSession().createQuery(queryString);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Person> getActualPersonal(LocalDate date) {

		String queryString = "select dismiss,name,patronymic,personalCode,surname,personDepartmentName=("
				+ "select top 1 departmentName from Departments d,Contracts c WHERE "
				+ "c.personalCode=p.personalCode and d.DepartmentCode=c.DepartmentCode "
				+ "and c.istate=0 and d.hidden='false' order by(c.contractStart) DESC "
				+ "),DismissDate as disDate  from personal p " + " " + "order by (surname);";
		Query query = currentSession().createNativeQuery(queryString, "PersonWithDepResult");
		return query.getResultList();
	}

	@Override
	public List<Order> findActualOrders(BigDecimal rateSize, int personalCode, Date searchDate) {
		String queryString = "select  o from Order o "
				+ "where o.rateSize=:rateSize and o.personalCode=:personalCode and o.comeIntoForce <:firstDayOfNextMonth and ordertype!=6 and ordertype!=14 and ordertype!=7 "
				+ "order by(o.comeIntoForce) desc";
		Query query = currentSession().createQuery(queryString);
		query.setParameter("personalCode", personalCode);
		query.setParameter("firstDayOfNextMonth", searchDate);
		query.setParameter("rateSize", rateSize);
		return query.getResultList();
	}

	@Override
	public List<Order> checkOrderWorking(BigDecimal orderRate, int personalCode, Date orderDate, Date searchDate) {
		String queryString = "select  o from Order o " + " where o.rateSize=:rateSize and o.personalCode=:personalCode "
				+ "and o.comeIntoForce <:searchDate " + "and o.comeIntoForce>=:orderDate " + "and ordertype=6 "
				+ "order by(o.comeIntoForce) desc";
		Query query = currentSession().createQuery(queryString);
		query.setParameter("personalCode", personalCode);
		query.setParameter("searchDate", searchDate);
		query.setParameter("orderDate", orderDate);
		query.setParameter("rateSize", orderRate);
		return query.getResultList();
	}

	/*
	 * @NamedQuery(name = "Personal.findOrders", query =
	 * "select  o from Order o " +
	 * "where o.rateSize=:rateSize and o.personalCode=:personalCode and o.comeIntoForce <=:searchDate and ordertype!=6 and ordertype!=14 and ordertype!=7 "
	 * + "order by(o.comeIntoForce) desc"),
	 * 
	 * @NamedQuery(name = "Personal.checkOrderWorking", query =
	 * "select  o from Order o " +
	 * " where o.rateSize=:rateSize and o.personalCode=:personalCode and o.comeIntoForce <:searchDate and o.comeIntoForce>=:orderDate and ordertype=6 order by(o.comeIntoForce) desc"
	 * )
	 */
	@Override
	public String getWorkRegimes(int yearNumber, int month) {
		String queryString = "select r.month" + month + "StringRegime FROM Regimes r WHERE r.yearNumber=:year";
		Query query = currentSession().createQuery(queryString);
		query.setParameter("year", yearNumber);

		String regimeString = (String) query.getResultList().get(0);
		return regimeString;
	}

	@Override
	public List<Escape> getPersonMissions(int personalCode, Date firstDayOfMonth, Date firstDayOfNextMonth) {
		Query query = currentSession().createNamedQuery("Personal.getMissions");
		query.setParameter("personalCode", personalCode);
		query.setParameter("firstDayOfMonth", firstDayOfMonth);
		query.setParameter("firstDayOfNextMonth", firstDayOfNextMonth);

		return query.getResultList();

	}

	@Override
	public List<Escape> getPersonDisabilities(int personalCode, Date firstDayOfMonth, Date firstDayOfNextMonth) {
		Query query = currentSession().createNamedQuery("Personal.getDisabilities");
		query.setParameter("personalCode", personalCode);
		query.setParameter("firstDayOfMonth", firstDayOfMonth);
		query.setParameter("firstDayOfNextMonth", firstDayOfNextMonth);

		return query.getResultList();
	}

	@Override
	public List<Escape> getPersonLeaves(int personalCode, Date firstDayOfMonth, Date firstDayOfNextMonth) {
		Query query = currentSession().createNamedQuery("Personal.getLeaves");
		query.setParameter("personalCode", personalCode);
		query.setParameter("firstDayOfMonth", firstDayOfMonth);
		query.setParameter("firstDayOfNextMonth", firstDayOfNextMonth);

		return query.getResultList();
	}
	
	public List<String> getPersonDeppartments(final int personalCode,Date firstDayOfMonth,Date firstDayOfNextMonth){
		String queryString = 
				 "select  distinct departmentName from Departments d,Contracts c WHERE "
				+ " c.personalCode=:personalCode"
				+ " and d.DepartmentCode=c.DepartmentCode "
				+" and c.contractEnd>=:firstDayOfMonth "
				+ " and d.hidden='false' "
				+"and c.contractStart<:firstDayOfNextMonth "
				+"and c.prolongDate<:firstDayOfNextMonth ";
		Query query = currentSession().createNativeQuery(queryString);
		query.setParameter("personalCode", personalCode);
		query.setParameter("firstDayOfMonth", firstDayOfMonth);
		query.setParameter("firstDayOfNextMonth", firstDayOfNextMonth);
		return query.getResultList();
	}

}
