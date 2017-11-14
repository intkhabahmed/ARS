package com.cg.ars.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "flightinformation")
public class Flight {

	@Id
	@Column(name = "Flightno")
	private String flightNo;

	@Column(name = "Airline")
	private String flightName;

	@Column(name = "Dep_city")
	private String deptCity;

	@Column(name = "Arr_city")
	private String arrCity;

	@Column(name = "Dep_date")
	private Date deptDate;

	@Column(name = "Arr_date")
	private Date arrDate;

	@Column(name = "Dep_time")
	private String deptTime;

	@Column(name = "Arr_time")
	private String arrTime;

	@Column(name = "FirstSeats")
	private int firstSeats;

	@Column(name = "FirstSeatFare")
	private double firstSeatsFare;

	@Column(name = "BussSeats")
	private int bussSeats;

	@Column(name = "BussSeatsFare")
	private double bussSeatsFare;

	@Column(name = "duration")
	private String duration;

	public Flight() {
		super();
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getFlightName() {
		return flightName;
	}

	public void setFlightName(String flightName) {
		this.flightName = flightName;
	}

	public String getDeptCity() {
		return deptCity;
	}

	public void setDeptCity(String deptCity) {
		this.deptCity = deptCity;
	}

	public String getArrCity() {
		return arrCity;
	}

	public void setArrCity(String arrCity) {
		this.arrCity = arrCity;
	}

	public Date getDeptDate() {
		return deptDate;
	}

	public void setDeptDate(Date deptDate) {
		this.deptDate = deptDate;
	}

	public Date getArrDate() {
		return arrDate;
	}

	public void setArrDate(Date arrDate) {
		this.arrDate = arrDate;
	}

	public String getDeptTime() {
		return deptTime;
	}

	public void setDeptTime(String deptTime) {
		this.deptTime = deptTime;
	}

	public String getArrTime() {
		return arrTime;
	}

	public void setArrTime(String arrTime) {
		this.arrTime = arrTime;
	}

	public int getFirstSeats() {
		return firstSeats;
	}

	public void setFirstSeats(int firstSeats) {
		this.firstSeats = firstSeats;
	}

	public double getFirstSeatsFare() {
		return firstSeatsFare;
	}

	public void setFirstSeatsFare(double firstSeatsFare) {
		this.firstSeatsFare = firstSeatsFare;
	}

	public int getBussSeats() {
		return bussSeats;
	}

	public void setBussSeats(int bussSeats) {
		this.bussSeats = bussSeats;
	}

	public double getBussSeatsFare() {
		return bussSeatsFare;
	}

	public void setBussSeatsFare(double bussSeatsFare) {
		this.bussSeatsFare = bussSeatsFare;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}
}
