package itmo.visits_control.models;

import java.util.List;

public class ResultModel {
	private String depName;
	private List<PersonInfo> personsInfo;

	public ResultModel( String depName,List<PersonInfo> personsInfo) {
		super();
		this.depName=depName;
		this.personsInfo = personsInfo;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public List<PersonInfo> getPersonsInfo() {
		return personsInfo;
	}

	public void setPersonsInfo(List<PersonInfo> personsInfo) {
		this.personsInfo = personsInfo;
	}

}
