package itmo.visits_control.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.datetime.standard.Jsr310DateTimeFormatAnnotationFormatterFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

import itmo.visits_control.components.PersonInfoBuilder;
import itmo.visits_control.dal.firebird.FireBirdDao;
import itmo.visits_control.dal.mssql.MSSQLDao;
import itmo.visits_control.entities.mssql.Person;
import itmo.visits_control.models.PersonInfo;
import itmo.visits_control.models.ResultModel;

@Service
public class MainService {

	@Autowired
	@Qualifier("MSSQLDao")
	private MSSQLDao mssqlDao;

	@Autowired
	@Qualifier("firebirdDao")
	private FireBirdDao firebirdDao;
	@Autowired
	private PersonInfoBuilder builder;

	public List<ResultModel> computeResults(int month, int year, List<String> personsJSON) throws Exception {
		List<ResultModel> result = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper().registerModule(new JSR310Module());
		List<Person> persons = new ArrayList(personsJSON.size());
		try {
			for (String pj : personsJSON) {
				persons.add(mapper.readValue(pj, Person.class));
			}
		} catch (Exception e) {
			String j = "";
			for (String pj : personsJSON)
				j += pj + ",";
			persons.add(mapper.readValue(j, Person.class));
		}
		List<PersonInfo> personsInfo = new ArrayList();
		persons.forEach(p -> {
			personsInfo.add(new PersonInfo(p.getPersonalCode(), p.getLastName(), p.getName(), p.getMiddleName(),
					p.getPersonDepartmentName()));
		});
		personsInfo.stream().collect(Collectors.groupingBy(new Function<PersonInfo, String>() {
			@Override
			public String apply(PersonInfo t) {
				return t.getDepartmentName();
			};
		})).forEach((k, v) -> {
			result.add(new ResultModel(k, v));
		});
		PersonInfoBuilder builder = this.builder.buildMonthRegime(month, year);
		result.forEach(r -> {
			r.getPersonsInfo().forEach(p -> {
				try {
					builder.getListRegimeDays(p);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		});
		return result;
	}

}
