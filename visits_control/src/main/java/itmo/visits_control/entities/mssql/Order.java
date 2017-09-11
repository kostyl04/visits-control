package itmo.visits_control.entities.mssql;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "Orders", schema = "dbo")
@NamedQueries({ @NamedQuery(name = "Personal.checkOrderWorking", query = "select  o from Order o "
		+ " where o.rateSize=:rateSize" + " and o.personalCode=:personalCode" + " and o.comeIntoForce <:searchDate"
		+ " and o.comeIntoForce>=:orderDate" + " and ordertype=6" + " order by(o.comeIntoForce) desc") })
public class Order {
	@Column(name = "RateSize", columnDefinition = "decimal", precision = 18, scale = 3)
	private BigDecimal rateSize;
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

	public BigDecimal getRateSize() {
		return rateSize;
	}

	public void setRateSize(BigDecimal rateSize) {
		this.rateSize = rateSize;
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

	public BigDecimal getDayRate(LocalDate tempDate) {
		LocalDate startDate = comeIntoForce.toLocalDate();	
		boolean isAfterOrEquals = tempDate.isAfter(startDate) || tempDate.isEqual(startDate);
		//boolean isBeforeOrEquals = tempDate.isBefore(endDate) || tempDate.isEqual(endDate);
		return isAfterOrEquals  ? this.rateSize : new BigDecimal(0d);
		// return new BigDecimal(0);

	}

	@Override
	public String toString() {
		return "Order [rateSize=" + rateSize + ", personalCode=" + personalCode + ", orderType=" + orderType
				+ ", comeIntoForce=" + comeIntoForce + ", lastDate=" + lastDate + "]";
	}

}
