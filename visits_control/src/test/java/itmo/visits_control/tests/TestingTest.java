package itmo.visits_control.tests;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import itmo.visits_control.components.PersonInfoBuilder;
import itmo.visits_control.configuration.AppConfig;
import itmo.visits_control.dal.firebird.FireBirdDao;
import itmo.visits_control.dal.mssql.MSSQLDao;
import itmo.visits_control.entities.mssql.Person;
import itmo.visits_control.entities.mssql.ProfEducation;
import itmo.visits_control.models.Escape;
import itmo.visits_control.models.PersonInfo;
import itmo.visits_control.services.FormService;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
public class TestingTest {
	@Autowired
	@Qualifier("MSSQLDao")
	private MSSQLDao mssqlDao;
	@Autowired
	private FormService service;
	@Autowired
	@Qualifier("firebirdDao")
	private FireBirdDao firebirdDao;
	@Autowired
	private PersonInfoBuilder regimeBuilder;

	/*
	 * @Test public void regimesEntityTest() {
	 * System.out.println("Running Test:RegimesEntityTest"); List<Regime>
	 * regimes = cruddao.list(Regime.class);
	 * assertEquals("Check regimes size is 1", 1, regimes.size()); }
	 * 
	 * @Test public void getMonthLists() {
	 * System.out.println("Running Tests:getMonthLists"); List<String> month =
	 * firebirdDao.getMonthsFromYear(2017); System.out.println("month ");
	 * System.out.println(month.size());
	 * 
	 * }
	 * 
	 * @Test public void getNotHiddenDepartments(){ List<Department>
	 * result=mssqlDao.getNotHiddenDepartments();
	 * result.forEach(d->System.out.println(d.getDepartmentCode()+" "+d.
	 * getDepartmentName())); }
	 * 
	 * @Test public void getActualWorkers(){ List<Person>
	 * result=mssqlDao.getActualPersonal(); result.forEach(p->{
	 * if(p.getPersonDepartmentName()==null)
	 * System.out.println(p.getLastName()); });
	 * System.out.println(result.size()); }
	 */
	/*
	 * @Test public void jsonTest() throws JsonParseException,
	 * JsonMappingException, IOException { // // List<Person> result =
	 * mssqlDao.getActualPersonal(); Person person = // result.stream().filter(p
	 * -> { return p.getPersonalCode() == 2027 ? // true : false;
	 * }).findFirst().get(); ObjectMapper mapper = new // ObjectMapper(); String
	 * jsonString = // mapper.writeValueAsString(person);
	 * System.out.println(jsonString); // // Person obj =
	 * mapper.readValue(jsonString, Person.class); // System.out.println(obj);
	 * //
	 * 
	 * StartForm form = service.getStartForm(); ObjectMapper mapper = new
	 * ObjectMapper(); String jsonString = mapper.writeValueAsString(form);
	 * System.out.println(jsonString);
	 * 
	 * }
	 */
	// @Test
	// public void getRegimesTest(){
	// String regimes = mssqlDao.getWorkRegimes(2017,2);
	// regimeBuilder.parseWorkRegime(regimes).forEach(d->{
	// System.out.println(d.toString());
	// });;
	// }

	/*
	 * @Test public void getEscapes() { List<Escape> escapes = new
	 * ArrayList<Escape>(); Date firstDayOfNextMonth =
	 * Date.valueOf(LocalDate.of(2017, 8, 1)); Date firstDayOfMonth =
	 * Date.valueOf(LocalDate.of(2017, 7, 1));
	 * escapes.addAll(mssqlDao.getPersonDisabilities(1981, firstDayOfMonth,
	 * firstDayOfNextMonth)); escapes.addAll(mssqlDao.getPersonLeaves(1981,
	 * firstDayOfMonth, firstDayOfNextMonth));
	 * escapes.addAll(mssqlDao.getPersonMissions(1981, firstDayOfMonth,
	 * firstDayOfNextMonth)); escapes.forEach(e->{
	 * System.out.println(e.getType()); }); }
	 */
	// @Test
	// public void getEscapes() throws Exception {
	// long t=System.currentTimeMillis();
	// List<Person> persons = mssqlDao.getActualPersonal();
	// //Person person=mssqlDao.find(Person.class, 1981);
	// List<PersonInfo> personsInfo = new ArrayList<>();
	// //personsInfo.add(new PersonInfo(person.getPersonalCode(),
	// person.getLastName(), person.getName(), person.getMiddleName()));
	// persons.forEach(p -> {
	// personsInfo.add(new PersonInfo(p.getPersonalCode(), p.getLastName(),
	// p.getName(), p.getMiddleName()));
	// });
	// PersonInfoBuilder builder=regimeBuilder.buildMonthRegime(3, 2017);
	// personsInfo.forEach(p->{
	// builder.getListRegimeDays(p);
	// System.out.println(p);
	// });
	// System.out.println(System.currentTimeMillis()-t);
	//
	// }
	// @Test
	// public void asd(){
	// List<Person> persons=mssqlDao.getActualPersonal(LocalDate.of(2017, 1,
	// 1));
	// persons.forEach(System.out::println);
	//
	// }

	@Test
	public void getDepartments() throws Exception {
		int code = 438;
		// SELECT CONVERT(Datetime,EducationStart , 105) from dbo.ProfEducation;
		List<Escape>list = mssqlDao.getPersonEducations(119,Date.valueOf(LocalDate.of(2017, 5, 1)),Date.valueOf(LocalDate.of(2017, 6, 1)));
		System.out.println(list.size());
			for(Escape d:list){
				System.out.println(d);
				
			}
		
		
		
		
	}
}
