package itmo.visits_control.services;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import itmo.visits_control.dal.firebird.FireBirdDao;
import itmo.visits_control.dal.mssql.MSSQLDao;
import itmo.visits_control.entities.mssql.Department;
import itmo.visits_control.entities.mssql.Person;
import itmo.visits_control.entities.mssql.Regimes;
import itmo.visits_control.models.StartForm;

@Service
public class FormService {
	@Autowired
	@Qualifier("MSSQLDao")
	private MSSQLDao mssqlDao;
	@Autowired
	@Qualifier("firebirdDao")
	private FireBirdDao firebirdDao;

	public StartForm getStartForm() {
		Map <String,List<String>> map=new LinkedHashMap<String, List<String>>();
		List<String> years = mssqlDao.list(Regimes.class).stream().map(r -> String.valueOf(r.getYearNumber()))
				.collect(Collectors.toList());
		years.forEach(y->{
			int year=Integer.parseInt(y);
			List<String> months= firebirdDao.getMonthsFromYear(year);
			map.put(y, months);
		});
		List<Person> persons=mssqlDao.getActualPersonal();
		List<Department> deparments=mssqlDao.getNotHiddenDepartments();
		StartForm form=new StartForm(map,deparments,persons);
		
		return form;
	}
}
