package itmo.visits_control.entities.firebird;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;

/**
 * The persistent class for the employees database table.
 * 
 */
@Entity
@Table(name = "STAFF")
@NamedQuery(name = "STAFF.findAll", query = "SELECT e FROM Staff e")
public class Staff implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_STAFF")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "FIRST_NAME")
	private String firstName;
	@Column(name = "LAST_NAME")
	private String lastName;
	@Column(name = "MIDDLE_NAME")
	private String middleName;
	@Column(name="DATE_DISMISS")
	private Date dateDismiss;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstname) {
		this.firstName = firstname;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public Staff() {
	}

	public Date getDateDismiss() {
		return dateDismiss;
	}

	public void setDateDismiss(Date dateDismiss) {
		this.dateDismiss = dateDismiss;
	}

	@Override
	public String toString() {
		return "Staff [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", middleName=" + middleName
				+ "]";
	}

}