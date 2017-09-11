package itmo.visits_control.entities.mssql;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "Personal", schema = "dbo")
@SqlResultSetMapping(name = "PersonWithDepResult", classes = {
		@ConstructorResult(targetClass = Person.class, columns = { @ColumnResult(name = "dismiss"),
				@ColumnResult(name = "name"), @ColumnResult(name = "patronymic"), @ColumnResult(name = "personalCode"),
				@ColumnResult(name = "surname"), @ColumnResult(name = "personDepartmentName"),
				@ColumnResult(name = "disDate") }) })
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "Dismiss")
	private boolean dismiss;
	// ИМЯ
	@Column(name = "Name")
	private String name;
	// Отчество
	@Column(name = "Patronymic")
	private String middleName;
	@Id
	@Column(name = "PersonalCode")
	private int personalCode;
	// Фамилия
	@Column(name = "Surname")
	private String lastName;
	@Transient
	private String personDepartmentName;
	@Transient
	private LocalDate dismissDate;

	public Person() {
	}

	public Person(boolean dismiss, String name, String middleName, int personalCode, String lastName,
			String personDepartmentName, String dismissDate) {
		super();
		this.dismiss = dismiss;
		this.name = name;
		this.middleName = middleName;
		this.personalCode = personalCode;
		this.lastName = lastName;
		this.personDepartmentName = personDepartmentName;
		dismissDate = dismissDate.trim();
		if (dismissDate == null || dismissDate.equals(""))
			this.dismissDate = null;
		else {
			String[] arr = dismissDate.split("\\.");
			this.dismissDate = LocalDate.parse(arr[2] + "-" + arr[1] + "-" + arr[0]);
		}

	}

	public String getPersonDepartmentName() {
		return personDepartmentName;
	}

	public void setPersonDepartmentName(String personDepartmentName) {
		this.personDepartmentName = personDepartmentName;
	}

	public boolean isDismiss() {
		return dismiss;
	}

	public void setDismiss(boolean dismiss) {
		this.dismiss = dismiss;
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

	public int getPersonalCode() {
		return personalCode;
	}

	public void setPersonalCode(int personalCode) {
		this.personalCode = personalCode;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return lastName + " " + name + " " + middleName;
	}

	public LocalDate getDismissDate() {
		return dismissDate;
	}

	public void setDismissDate(LocalDate dismissDate) {
		this.dismissDate = dismissDate;
	}

	@Override
	public String toString() {
		return "Person [dismiss=" + dismiss + ", name=" + name + ", middleName=" + middleName + ", personalCode="
				+ personalCode + ", lastName=" + lastName + ", personDepartmentName=" + personDepartmentName + "]";
	}

}
