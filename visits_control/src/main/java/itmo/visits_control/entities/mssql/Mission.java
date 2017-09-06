package itmo.visits_control.entities.mssql;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import itmo.visits_control.models.Escape;
import itmo.visits_control.models.EscapeType;



@Entity
@Table(name = "Orders", schema = "dbo")
/*
 * @NamedQueries({ @NamedQuery(name = "Personal.checkOrderWorking", query =
 * "select  o from Order o " + " where o.rateSize=:rateSize" +
 * " and o.personalCode=:personalCode" + " and o.comeIntoForce <:searchDate" +
 * " and o.comeIntoForce>=:orderDate" + " and ordertype=6" +
 * " order by(o.comeIntoForce) desc") })
 */
@NamedQueries({ @NamedQuery(name = "Personal.getMissions", query = "select  o from Mission o "
		+ " where o.personalCode=:personalCode" + " and o.comeIntoForce <:firstDayOfNextMonth and o.lastDate>=:firstDayOfMonth" + " and ordertype=14"
		+ " order by(o.comeIntoForce) desc") })
public class Mission implements Escape{
	@Transient
	private final EscapeType type = EscapeType.Mission;
	@Id
	@Column(name = "PersonalCode")
	private int personalCode;
	@Column(name = "OrderType")
	private int orderType;
	@Column(name = "ComeIntoForce")
	private Date comeIntoForce;
	@Column(name = "LastDate")
	private Date lastDate;

	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public int getPersonalCode() {
		return personalCode;
	}

	public void setPersonalCode(int personalCode) {
		this.personalCode = personalCode;
	}

	public Date getComeIntoForce() {
		return comeIntoForce;
	}

	public void setComeIntoForce(Date comeIntoForce) {
		this.comeIntoForce = comeIntoForce;
	}

	public EscapeType getType() {
		return type;
	}

	@Override
	public boolean checkEscapeDate(LocalDate tempDate) {
		LocalDate startDate = comeIntoForce.toLocalDate();
		LocalDate endDate = lastDate.toLocalDate();
		boolean isAfterOrEquals = tempDate.isAfter(startDate) || tempDate.isEqual(startDate);
		boolean isBeforeOrEquals = tempDate.isBefore(endDate) || tempDate.isEqual(endDate);
		return isAfterOrEquals && isBeforeOrEquals ? true : false;

	}

	

}
