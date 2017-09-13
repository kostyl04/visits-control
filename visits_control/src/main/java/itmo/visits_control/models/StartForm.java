package itmo.visits_control.models;

import java.util.List;
import java.util.Map;

import itmo.visits_control.entities.mssql.Department;
import itmo.visits_control.entities.mssql.Person;

public class StartForm {
	
	private Map <String,List<String>> map;
	private List<Department> selectDepartmentsList;
	private List<Person> selectPersonsList;
	
	
	

	public StartForm(Map<String, List<String>> map, List<Department> selectDepartmentsList,
			List<Person> selectPersonsList) {
		super();
		this.map = map;
		this.selectDepartmentsList = selectDepartmentsList;
		this.selectPersonsList = selectPersonsList;
	}

	public Map<String, List<String>> getMap() {
		return map;
	}

	public void setMap(Map<String, List<String>> map) {
		this.map = map;
	}

	public List<Department> getSelectDepartmentsList() {
		return selectDepartmentsList;
	}

	public void setSelectDepartmentsList(List<Department> selectDepartmentsList) {
		this.selectDepartmentsList = selectDepartmentsList;
	}

	public List<Person> getSelectPersonsList() {
		return selectPersonsList;
	}

	public void setSelectPersonsList(List<Person> selectPersonsList) {
		this.selectPersonsList = selectPersonsList;
	}

}
