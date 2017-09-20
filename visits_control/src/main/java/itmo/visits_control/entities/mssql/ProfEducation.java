package itmo.visits_control.entities.mssql;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Transient;

import itmo.visits_control.models.Escape;
import itmo.visits_control.models.EscapeType;

/**
 * The persistent class for the Leaves database table.
 * 
 */
@Entity
@Table(name = "ProfEducation", schema = "dbo")
@SqlResultSetMapping(name = "Educations", classes = { @ConstructorResult(targetClass = ProfEducation.class, columns = {
		@ColumnResult(name = "PersonalCode"), @ColumnResult(name = "edEnd", type = Date.class), @ColumnResult(name = "edStart", type = Date.class) }) })
public class ProfEducation implements Serializable, Escape {
	private static final long serialVersionUID = 1L;
	@Transient
	private final EscapeType type = EscapeType.Education;
	@Transient
	private Date endOfEducationDate;
	@Id
	@Column(name = "PersonalCode")
	private int personalCode;

	public ProfEducation(int personalCode, Date endOfEducationDate,Date startOfEducationDate) {
		super();
		this.personalCode = personalCode;
		this.endOfEducationDate = endOfEducationDate;
		this.startEducationDate=startOfEducationDate;
	}

	@Transient
	private Date startEducationDate;

	public ProfEducation() {

	}

	public Date getEndOfEducationDate() {
		return endOfEducationDate;
	}

	public void setEndOfEducationDate(Date endOfEducationDate) {
		this.endOfEducationDate = endOfEducationDate;
	}

	public int getPersonalCode() {
		return personalCode;
	}

	public void setPersonalCode(int personalCode) {
		this.personalCode = personalCode;
	}

	public Date getStartEducationDate() {
		return startEducationDate;
	}

	public void setStartEducationDate(Date startEducationDate) {
		this.startEducationDate = startEducationDate;
	}

	public EscapeType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "ProfEducation [type=" + type + ", endOfEducationDate=" + endOfEducationDate + ", personalCode="
				+ personalCode + ", startEducationDate=" + startEducationDate + "]";
	}

	@Override
	public boolean checkEscapeDate(LocalDate tempDate) {
		LocalDate startDate = startEducationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate endDate = endOfEducationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		boolean isAfterOrEquals = tempDate.isAfter(startDate) || tempDate.isEqual(startDate);
		boolean isBeforeOrEquals = tempDate.isBefore(endDate) || tempDate.isEqual(endDate);
		return isAfterOrEquals && isBeforeOrEquals ? true : false;

	}

}