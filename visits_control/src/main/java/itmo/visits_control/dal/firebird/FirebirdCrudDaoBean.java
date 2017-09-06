package itmo.visits_control.dal.firebird;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import itmo.visits_control.entities.firebird.Staff;
import itmo.visits_control.entities.firebird.VisitTime;

/**
 * Hibernate crud dao interface implementation
 * 
 * @author harchevnikov_m Date: 18.09.11 Time: 21:20
 */
@Repository(value = "firebirdDao")
@Transactional(value = "firebirdTX")
public class FirebirdCrudDaoBean implements FireBirdDao {
	@Autowired
	@Qualifier("firebirdSessionFactory")
	private SessionFactory sessionFactory;

	/**
	 * Default constructor
	 */
	public FirebirdCrudDaoBean() {
	}

	// default methods
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

	// Custom methods

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getMonthsFromYear(int year) {
		String queryString = "SELECT DISTINCT EXTRACT (month FROM t.date_pass) FROM TABEL_INTERMEDIADATE t WHERE EXTRACT (year FROM date_pass)=:selectedYear";
		Query query = currentSession().createNativeQuery(queryString);
		query.setParameter("selectedYear", year);
		List<String> list = (List<String>) query.getResultList().stream().map(m -> {
			short s = (Short) m;
			return Short.toString(s);
		}).collect(Collectors.toList());
		return list;
	}

	@Override
	public List<Staff> findIdByInitials(String firstName, String lastName, String middleName) {
		// "select s.id from Staff s where upper(s.lastName) = upper(:lastName)
		// AND upper(s.middleName) = upper(:middleName) AND upper(s.firstName) =
		// upper(:firstName)"
		Query query = currentSession()
				.createQuery(
						"select  s from Staff s where upper(s.lastName) = upper(:lastName) AND upper(s.middleName) = upper(:middleName) AND upper(s.firstName) = upper(:firstName)")
				.setParameter("firstName", firstName).setParameter("middleName", middleName)
				.setParameter("lastName", lastName);
		return query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<VisitTime> findAllVisits(int staffid, int year, int month) {
		// select v from VisitTime v where v.staffid=:staffid and
		// YEAR(v.datePass)=:currentYear and MONTH(v.datePass)=:currentMonth
		Query query = currentSession()
				.createQuery(
						"select  v from VisitTime v where v.staffid=:staffid and YEAR(v.datePass)=:currentYear and MONTH(v.datePass)=:currentMonth")
				.setParameter("currentYear", year).setParameter("currentMonth", month).setParameter("staffid", staffid);
		List<VisitTime> resultList = (List<VisitTime>) query.getResultList();

		return resultList;
	}
}