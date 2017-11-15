package com.cg.ars.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "Bookinginformation")
public class BookingInformation {

	@Id
	@Column(name = "Booking_id")
	@SequenceGenerator(name = "seq1", sequenceName = "ticketbooking_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq1")
	private int bookingId;

	@Column(name = "User_email")
	private String userEmail;

	@Column(name = "No_of_passengers")
	@NotNull(message = "provide passengers seat")
	private int noOfPassengers;

	@Column(name = "Class_type")
	@NotEmpty(message = "Provide a class")
	private String classType;

	@Column(name = "Total_fare")
	private double totalFare;

	@Column(name = "CreditCard_info")
	@NotEmpty(message = "Provide your credit card detail")
	private String creditCardInfo;

	@Column(name = "Src_city")
	@Pattern(regexp = "[A-Za-z]{2,10}", message = "Please provide valid source city")
	@Size(min = 2, max = 10, message = "size of the source between 2 to 10")
	private String srcCity;

	@Column(name = "Dest_city")
	@Pattern(regexp = "[A-Za-z]{2,10}", message = "Please provide valid destination city")
	@Size(min = 2, max = 10, message = "size of the destination city between 2 to 10")
	private String destCity;

	@Column(name = "FlightNo")
	private String flightNo;

	@Column(name = "Booking_date")
	@NotNull(message = "Provide a date please")
	private Date bookingDate;

	@Column(name = "Travel_date")
	@NotNull(message = "Provide a date please")
	@Future
	private Date travelDate;

	public BookingInformation() {
		super();
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public int getNoOfPassengers() {
		return noOfPassengers;
	}

	public void setNoOfPassengers(int noOfPassengers) {
		this.noOfPassengers = noOfPassengers;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public double getTotalFare() {
		return totalFare;
	}

	public void setTotalFare(double totalFare) {
		this.totalFare = totalFare;
	}

	public String getCreditCardInfo() {
		return creditCardInfo;
	}

	public void setCreditCardInfo(String creditCardInfo) {
		this.creditCardInfo = creditCardInfo;
	}

	public String getSrcCity() {
		return srcCity;
	}

	public void setSrcCity(String srcCity) {
		this.srcCity = srcCity;
	}

	public String getDestCity() {
		return destCity;
	}

	public void setDestCity(String destCity) {
		this.destCity = destCity;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Date getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}
}
