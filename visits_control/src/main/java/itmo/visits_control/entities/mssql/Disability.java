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
 * The persistent class for the Disabilities database table.
 * 
 */
@Entity
@Table(name = "Disabilities", schema = "dbo")
@NamedQuery(name = "Personal.getDisabilities", query = "select d from Disability d where d.personalCode = :personalCode and d.disabilityStart < :firstDayOfNextMonth and d.disabilityEnd>=:firstDayOfMonth ")

public class Disability implements Serializable, Escape {
	private static final long serialVersionUID = 1L;
	@Transient
	private final EscapeType type = EscapeType.Disability;
	@Column(name = "DisabilityEnd")
	private Date disabilityEnd;

	@Column(name = "DisabilityStart")
	private Date disabilityStart;

	@Id
	@Column(name = "PersonalCode")
	private int personalCode;

	public Disability() {
	}

	public Date getDisabilityEnd() {
		return disabilityEnd;
	}

	public void setDisabilityEnd(Date disabilityEnd) {
		this.disabilityEnd = disabilityEnd;
	}

	public Date getDisabilityStart() {
		return disabilityStart;
	}

	public void setDisabilityStart(Date disabilityStart) {
		this.disabilityStart = disabilityStart;
	}

	public int getPersonalCode() {
		return personalCode;
	}

	public void setPersonalCode(int personalCode) {
		this.personalCode = personalCode;
	}

	public EscapeType getType() {
		return type;
	}

	@Override
	public boolean checkEscapeDate(LocalDate tempDate) {
		LocalDate startDate = disabilityStart.toLocalDate();
		LocalDate endDate = disabilityEnd.toLocalDate();
		boolean isAfterOrEquals = tempDate.isAfter(startDate) || tempDate.isEqual(startDate);
		boolean isBeforeOrEquals = tempDate.isBefore(endDate) || tempDate.isEqual(endDate);
		return isAfterOrEquals && isBeforeOrEquals ? true : false;

	}

}