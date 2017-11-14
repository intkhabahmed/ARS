package com.cg.ars.service;

import java.util.List;

import com.cg.ars.entity.BookingInformation;
import com.cg.ars.entity.Flight;
import com.cg.ars.entity.User;
import com.cg.ars.exception.AirlineException;

public interface IAirlineService {

	/**
	 * @param query
	 * @param searchBasis
	 * @return type List
	 * @throws RuntimeException
	 */
	public List<Flight> viewListOfFlights(String query, String searchBasis)
			throws RuntimeException;

	/**
	 * @param query
	 * @param searchBasis
	 * @return type List
	 * @throws RuntimeException
	 */
	public List<BookingInformation> viewBookings(String query,
			String searchBasis) throws RuntimeException;

	/**
	 * @param user
	 * @return type User
	 * @throws RuntimeException
	 */
	public User signUp(User user) throws RuntimeException;

	/**
	 * @param bookingId
	 * @return type BookingInformation
	 * @throws RuntimeException
	 */
	public BookingInformation bookingCancel(int bookingId)
			throws RuntimeException;

	/**
	 * @param user
	 * @return type User
	 * @throws RuntimeException
	 */
	public User validLogin(User user) throws RuntimeException;

	/**
	 * @param flightNo
	 * @return type Integer Array
	 * @throws RuntimeException
	 */
	public int[] flightOccupancyDetails(String flightNo)
			throws RuntimeException;

	/**
	 * @param booking
	 * @return type BookingInformation
	 * @throws RuntimeException
	 */
	public BookingInformation modifyBookingInformation(
			BookingInformation booking) throws RuntimeException;

	/**
	 * @param booking
	 * @return type BookingInformation
	 * @throws RuntimeException
	 */
	public BookingInformation confirmBooking(BookingInformation booking)
			throws RuntimeException;

	/**
	 * @param query
	 * @param searchBasis
	 * @return type boolean
	 * @throws RuntimeException
	 */
	public boolean checkAvailabiltiy(String query, String searchBasis)
			throws RuntimeException;

	/**
	 * @param username
	 * @param password
	 * @return type User
	 * @throws RuntimeException
	 * @throws AirlineException 
	 */
	public User forgotPassword(User user) throws RuntimeException, AirlineException;

	/**
	 * @param user
	 * @return type User
	 * @throws RuntimeException
	 */
	public User updateUser(User user) throws RuntimeException;
	
	/**
	 * @return type List
	 * @throws RuntimeException
	 */
	public List<String> getCities() throws RuntimeException;

	/**
	 * @param cityName
	 * @return Type String
	 * @throws RuntimeException
	 */
	public String getAbbreviation(String cityName) throws RuntimeException;
}
