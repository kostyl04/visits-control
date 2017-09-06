package itmo.visits_control.entities.mssql;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import itmo.visits_control.models.Escape;
import itmo.visits_control.models.EscapeType;

/**
 * The persistent class for the Leaves database table.
 * 
 */
@Entity
@Table(name = "Leaves", schema = "dbo")
@NamedQuery(name = "Personal.getLeaves", query = "select l from Leave l where l.personalCode = :personalCode and l.startLeaveDate < :firstDayOfNextMonth and l.endOfLeaveDate >= :firstDayOfMonth")
public class Leave implements Serializable, Escape {
	private static final long serialVersionUID = 1L;
	@Transient
	private final EscapeType type = EscapeType.Leave;
	@Column(name = "EndOfLeaveDate")
	private Date endOfLeaveDate;
	@Id
	@Column(name = "PersonalCode")
	private int personalCode;

	@Column(name = "StartLeaveDate")
	private Date startLeaveDate;

	public Leave() {

	}

	public Date getEndOfLeaveDate() {
		return endOfLeaveDate;
	}

	public void setEndOfLeaveDate(Date endOfLeaveDate) {
		this.endOfLeaveDate = endOfLeaveDate;
	}

	public int getPersonalCode() {
		return personalCode;
	}

	public void setPersonalCode(int personalCode) {
		this.personalCode = personalCode;
	}

	public Date getStartLeaveDate() {
		return startLeaveDate;
	}

	public void setStartLeaveDate(Date startLeaveDate) {
		this.startLeaveDate = startLeaveDate;
	}

	public EscapeType getType() {
		return type;
	}

	@Override
	public boolean checkEscapeDate(LocalDate tempDate) {
		LocalDate startDate = startLeaveDate.toLocalDate();
		LocalDate endDate = endOfLeaveDate.toLocalDate();
		boolean isAfterOrEquals = tempDate.isAfter(startDate) || tempDate.isEqual(startDate);
		boolean isBeforeOrEquals = tempDate.isBefore(endDate) || tempDate.isEqual(endDate);
		return isAfterOrEquals && isBeforeOrEquals ? true : false;

	}

}