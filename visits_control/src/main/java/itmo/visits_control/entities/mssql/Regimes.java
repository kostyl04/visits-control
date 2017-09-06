package itmo.visits_control.entities.mssql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Regimes",schema="dbo")
public class Regimes {
	@Id
	@Column(name="RegimeCode")
	private int RegimeCode;

	
	@Column(name="YearNumber")
	private int yearNumber;
	
	//Режимы месяцев в строке
	@Column(name="Month1")
	private String month1StringRegime;
	@Column(name="Month2")
	private String month2StringRegime;
	@Column(name="Month3")
	private String month3StringRegime;
	@Column(name="Month4")
	private String month4StringRegime;
	@Column(name="Month5")
	private String month5StringRegime;
	@Column(name="Month6")
	private String month6StringRegime;
	@Column(name="Month7")
	private String month7StringRegime;
	@Column(name="Month8")
	private String month8StringRegime;
	@Column(name="Month9")
	private String month9StringRegime;
	@Column(name="Month10")
	private String month10StringRegime;
	@Column(name="Month11")
	private String month11StringRegime;
	@Column(name="Month12")
	private String month12StringRegime;
	public int getYearNumber() {
		return yearNumber;
	}
	public void setYearNumber(int yearNumber) {
		this.yearNumber = yearNumber;
	}
	public String getMonth1StringRegime() {
		return month1StringRegime;
	}
	public void setMonth1StringRegime(String month1StringRegime) {
		this.month1StringRegime = month1StringRegime;
	}
	public String getMonth2StringRegime() {
		return month2StringRegime;
	}
	public void setMonth2StringRegime(String month2StringRegime) {
		this.month2StringRegime = month2StringRegime;
	}
	public String getMonth3StringRegime() {
		return month3StringRegime;
	}
	public void setMonth3StringRegime(String month3StringRegime) {
		this.month3StringRegime = month3StringRegime;
	}
	public String getMonth4StringRegime() {
		return month4StringRegime;
	}
	public void setMonth4StringRegime(String month4StringRegime) {
		this.month4StringRegime = month4StringRegime;
	}
	public String getMonth5StringRegime() {
		return month5StringRegime;
	}
	public void setMonth5StringRegime(String month5StringRegime) {
		this.month5StringRegime = month5StringRegime;
	}
	public String getMonth6StringRegime() {
		return month6StringRegime;
	}
	public void setMonth6StringRegime(String month6StringRegime) {
		this.month6StringRegime = month6StringRegime;
	}
	public String getMonth7StringRegime() {
		return month7StringRegime;
	}
	public void setMonth7StringRegime(String month7StringRegime) {
		this.month7StringRegime = month7StringRegime;
	}
	public String getMonth8StringRegime() {
		return month8StringRegime;
	}
	public void setMonth8StringRegime(String month8StringRegime) {
		this.month8StringRegime = month8StringRegime;
	}
	public String getMonth9StringRegime() {
		return month9StringRegime;
	}
	public void setMonth9StringRegime(String month9StringRegime) {
		this.month9StringRegime = month9StringRegime;
	}
	public String getMonth10StringRegime() {
		return month10StringRegime;
	}
	public void setMonth10StringRegime(String month10StringRegime) {
		this.month10StringRegime = month10StringRegime;
	}
	public String getMonth11StringRegime() {
		return month11StringRegime;
	}
	public void setMonth11StringRegime(String month11StringRegime) {
		this.month11StringRegime = month11StringRegime;
	}
	public String getMonth12StringRegime() {
		return month12StringRegime;
	}
	public void setMonth12StringRegime(String month12StringRegime) {
		this.month12StringRegime = month12StringRegime;
	}
	
	public int getRegimeCode() {
		return RegimeCode;
	}
	public void setRegimeCode(int regimeCode) {
		RegimeCode = regimeCode;
	}
	
}
