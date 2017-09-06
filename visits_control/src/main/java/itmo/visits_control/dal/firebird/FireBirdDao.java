package itmo.visits_control.dal.firebird;

import java.util.List;

import itmo.visits_control.dal.CrudDao;
import itmo.visits_control.entities.firebird.Staff;
import itmo.visits_control.entities.firebird.VisitTime;

public interface FireBirdDao extends CrudDao {
	List<String> getMonthsFromYear(int year);

	List<Staff> findIdByInitials(String firstName, String lastName, String middleName);

	List<VisitTime> findAllVisits(int staffid, int year, int month);
}
