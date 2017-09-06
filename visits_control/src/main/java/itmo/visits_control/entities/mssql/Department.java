package itmo.visits_control.entities.mssql;

import javax.persistence.*;

@Entity
@Table(name = "Departments", schema = "dbo")
public class Department {
	@Id
	@Column(name = "DepartmentCode")
	private int departmentCode;
	@Column(name = "DepartmentName")
	private String departmentName;
	@Column(name = "Hidden")
	private boolean hidden;

	public int getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(int departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

}
