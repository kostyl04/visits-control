package itmo.visits_control.entities.firebird;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TABEL_INTERMEDIADATE")
public class VisitTime {
	@Id
	@Column(name = "ID_TB_IN")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "STAFF_ID")
	private Integer staffid;
	@Column(name = "DATE_PASS")
	private Date datePass;
	@Column(name = "TIME_PASS")
	private Time timePass;
	@Column(name = "TYPE_PASS")
	private Integer passType;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDatePass() {
		return datePass;
	}

	public void setDatePass(Date datePass) {
		this.datePass = datePass;
	}

	public Time getTimePass() {
		return timePass;
	}

	public void setTimePass(Time timePass) {
		this.timePass = timePass;
	}

	public Integer getPassType() {
		return passType;
	}

	public void setPassType(Integer passType) {
		this.passType = passType;
	}
	
	public Integer getStaffid() {
		return staffid;
	}

	public void setStaffid(Integer staffid) {
		this.staffid = staffid;
	}

	public String getInfo(){
		return "VisitTime [staffid=" + staffid + ", datePass=" + datePass + ", timePass=" + timePass + ", passType="
				+ passType + "]";
	}

	
}
