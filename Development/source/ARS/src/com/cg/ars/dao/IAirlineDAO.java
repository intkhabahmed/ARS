package com.cg.ars.dao;

import java.util.List;

import com.cg.ars.entity.BookingInformation;
import com.cg.ars.entity.Flight;
import com.cg.ars.entity.User;

/**
 * @author inahmed
 *
 */
public interface IAirlineDAO {

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
	 * @param user
	 * @return type User
	 * @throws RuntimeException
	 */
	public User validLogin(User user) throws RuntimeException;

	/**
	 * @param bookingId
	 * @return type BookingInformation
	 * @throws RuntimeException
	 */
	public BookingInformation bookingCancel(int bookingId)
			throws RuntimeException;

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
	 * @return type String
	 * @throws RuntimeException
	 */
	public String checkAvailabiltiy(String query, String searchBasis)
			throws RuntimeException;

	/**
	 * @param user
	 * @return type User
	 * @throws RuntimeException
	 */
	public User updateUser(User user) throws RuntimeException;


	/**
	 * @param flight
	 * @return type void
	 * @throws RuntimeException
	 */
	public void updateFlight(Flight flight) throws RuntimeException;
	
	/**
	 * @param username
	 * @return type User
	 * @throws RuntimeException
	 */
	public User getUserDetails(String username) throws RuntimeException;

	/**
	 * @return type List
	 * @throws RuntimeException
	 */
	public List<String> getCities() throws RuntimeException;

	/**
	 * @param cityName
	 * @return type String
	 * @throws RuntimeException
	 */
	public String getAbbreviation(String cityName) throws RuntimeException;

}
