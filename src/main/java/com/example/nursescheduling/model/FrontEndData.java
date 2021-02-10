package com.example.nursescheduling.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="nurse_front_end_data")
public class FrontEndData {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="total_nurse")
	private String totalNurse;
	
	@Column(name="morning_nurse")
	private String morning;
	
	@Column(name="evening_nurse")
	private String evening;
	
	@Column(name="night_nurse")
	private String night;
	
	@Column(name="holiday_nurse")
	private String off;
	
	public FrontEndData() {
		
	}

	public FrontEndData(String totalNurse, String morning, String evening, String night, String off) {
		super();
		this.totalNurse = totalNurse;
		this.morning = morning;
		this.evening = evening;
		this.night = night;
		this.off = off;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTotalNurse() {
		return totalNurse;
	}

	public void setTotalNurse(String totalNurse) {
		this.totalNurse = totalNurse;
	}

	public String getMorning() {
		return morning;
	}

	public void setMorning(String morning) {
		this.morning = morning;
	}

	public String getEvening() {
		return evening;
	}

	public void setEvening(String evening) {
		this.evening = evening;
	}

	public String getNight() {
		return night;
	}

	public void setNight(String night) {
		this.night = night;
	}

	public String getOff() {
		return off;
	}

	public void setOff(String off) {
		this.off = off;
	}

	@Override
	public String toString() {
		return "FrontEndData [id=" + id + ", totalNurse=" + totalNurse + ", morning=" + morning + ", evening=" + evening
				+ ", night=" + night + ", off=" + off + "]";
	}

	
	

}

