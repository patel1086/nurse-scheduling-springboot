package com.example.nursescheduling.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="nurses")
public class Nurse {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="nurse_1")
	private String nurse1;
	
	@Column(name="nurse_2")
	private String nurse2;
	
	@Column(name="nurse_3")
	private String nurse3;
	
	@Column(name="nurse_4")
	private String nurse4;
	
	@Column(name="nurse_5")
	private String nurse5;

	public Nurse() {
		
	}
	public Nurse(String nurse1, String nurse2, String nurse3, String nurse4, String nurse5) {
		//super();
		this.nurse1 = nurse1;
		this.nurse2 = nurse2;
		this.nurse3 = nurse3;
		this.nurse4 = nurse4;
		this.nurse5 = nurse5;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNurse1() {
		return nurse1;
	}

	public void setNurse1(String nurse1) {
		this.nurse1 = nurse1;
	}

	public String getNurse2() {
		return nurse2;
	}

	public void setNurse2(String nurse2) {
		this.nurse2 = nurse2;
	}

	public String getNurse3() {
		return nurse3;
	}

	public void setNurse3(String nurse3) {
		this.nurse3 = nurse3;
	}

	public String getNurse4() {
		return nurse4;
	}

	public void setNurse4(String nurse4) {
		this.nurse4 = nurse4;
	}

	public String getNurse5() {
		return nurse5;
	}

	public void setNurse5(String nurse5) {
		this.nurse5 = nurse5;
	}
	
//	@Column(name="nurse_6")
//	private String nurse6;
//	
//	@Column(name="nurse_7")
//	private String nurse7;
//	
//	@Column(name="nurse_8")
//	private String nurse8;
//	
//	@Column(name="nurse_9")
//	private String nurse9;
//	
//	@Column(name="nurse_10")
//	private String nurse10;
	
	
	
}
