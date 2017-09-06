package itmo.visits_control.models;

import java.util.List;

public class PersonInfo {
	private String departmentName;
	private int personalCode;
	private String lastName;
	private String name;
	private String middleName;
	private double rateSize;
	private long fullReqieredHours;
	private long workedOutHours;
	private long leaveHours;
	private long disabilityHours;
	private long missionHours;
	private PersonInfoStatus status = PersonInfoStatus.Ok;

	public PersonInfoStatus getStatus() {
		return status;
	}

	public void setStatus(PersonInfoStatus status) {
		this.status = status;
	}

	public PersonInfo(String lastName, String name, String middleName, double rateSize, long fullReqieredHours,
			long workedOutHours) {
		super();
		this.lastName = lastName;
		this.name = name;
		this.middleName = middleName;
		this.rateSize = rateSize;
		this.fullReqieredHours = fullReqieredHours;
		this.workedOutHours = workedOutHours;
	}

	public PersonInfo(int personalCode, String lastName, String name, String middleName, String departmentName) {
		super();
		this.lastName = lastName;
		this.name = name;
		this.middleName = middleName;
		this.personalCode = personalCode;
		this.departmentName = departmentName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public double getRateSize() {
		return rateSize;
	}

	public void setRateSize(double rateSize) {
		this.rateSize = rateSize;
	}

	public long getFullReqieredHours() {
		return fullReqieredHours;
	}

	public void setFullReqieredHours(long fullReqieredHours) {
		this.fullReqieredHours = fullReqieredHours;
	}

	public long getWorkedOutHours() {
		return workedOutHours;
	}

	public void setWorkedOutHours(long workedOutHours) {
		this.workedOutHours = workedOutHours;
	}

	public long getLeaveHours() {
		return leaveHours;
	}

	public void setLeaveHours(long leaveHours) {
		this.leaveHours = leaveHours;
	}

	public long getDisabilityHours() {
		return disabilityHours;
	}

	public void setDisabilityHours(long disabilityHours) {
		this.disabilityHours = disabilityHours;
	}

	public long getMissionHours() {
		return missionHours;
	}

	public void setMissionHours(long missionHours) {
		this.missionHours = missionHours;
	}

	public int getPersonalCode() {
		return personalCode;
	}

	public void setPersonalCode(int personalCode) {
		this.personalCode = personalCode;
	}

	@Override
	public String toString() {
		return "PersonInfo [personalCode=" + personalCode + ", lastName=" + lastName + ", name=" + name
				+ ", middleName=" + middleName + ", rateSize=" + rateSize + ", fullReqieredHours=" + fullReqieredHours
				+ ", workedOutHours=" + workedOutHours + ", leaveHours=" + leaveHours + ", disabilityHours="
				+ disabilityHours + ", missionHours=" + missionHours + ", status=" + status + "]";
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getFullName() {
		return this.lastName + " " + this.name + " " + this.middleName;
	}
	public long getWorkedOutPercent(){
		return this.fullReqieredHours!=0?(long)(1d*this.workedOutHours/this.fullReqieredHours*100):0;
	}
	public long getNotWorkedHours(){
		return Math.max(0, this.fullReqieredHours-this.workedOutHours-leaveHours-missionHours-disabilityHours);
	}
	public long getNotWorkedHoursPercent(){
		long closedHours=this.workedOutHours+leaveHours+missionHours+disabilityHours;
		return this.fullReqieredHours!=0?Math.max(0,(long)(100-1d*closedHours/this.fullReqieredHours*100)):0;
	}

}
