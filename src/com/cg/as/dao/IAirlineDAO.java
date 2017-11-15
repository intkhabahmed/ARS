package com.cg.as.dao;

import java.util.List;

import com.cg.as.entity.BookingInformation;
import com.cg.as.entity.Flight;
import com.cg.as.entity.User;
import com.cg.as.exception.AirlineException;

public interface IAirlineDAO {
	public List<Flight> viewListOfFlights(String query, String searchBasis) throws AirlineException;
	public List<BookingInformation> viewBookings(String query, String searchBasis) throws AirlineException;
	public User signUp(User user) throws AirlineException;
	public User validLogin(User user) throws AirlineException;
	public BookingInformation bookingCancel(String bookingId) throws AirlineException;
    /*public int[] flightOccupancyDetails(String flightNo) throws AirlineException;
	public int bookingConfirm(String username,String flightno, int noOfPassengers, String classType,
			String creditCard) throws AirlineException;
	public int checkAvailability(String query, String type) throws AirlineException;*/

}
