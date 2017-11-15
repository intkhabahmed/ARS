package com.cg.ars.dao;

import java.util.List;

import com.cg.ars.entity.Airport;
import com.cg.ars.entity.BookingInformation;
import com.cg.ars.entity.Flight;
import com.cg.ars.entity.User;

/**
 * @description Data Access Object Interface containing all functions associated
 *              with the corresponding actor
 * @author inahmed
 *
 */
public interface AirlineDAO {

	/**
	 * @param query 
	 * @param searchBasis
	 * @return List
	 * @throws RuntimeException
	 */
	public List<Flight> retrieveFlights(String query, String searchBasis)
			throws RuntimeException;

	/**
	 * @param query
	 * @param searchBasis
	 * @return List
	 * @throws RuntimeException
	 */
	public List<BookingInformation> retrieveBookings(String query,
			String searchBasis) throws RuntimeException;

	/**
	 * @param user
	 * @return User
	 * @throws RuntimeException
	 */
	public User addUser(User user) throws RuntimeException;

	/**
	 * @param user
	 * @return User
	 * @throws RuntimeException
	 */
	public User validateLogin(User user) throws RuntimeException;

	/**
	 * @param bookingId
	 * @return BookingInformation
	 * @throws RuntimeException
	 */
	public BookingInformation cancelBooking(int bookingId)
			throws RuntimeException;

	/**
	 * @param flightNo
	 * @return Array
	 * @throws RuntimeException
	 */
	public int[] getFlightOccupancyDetails(String flightNo)
			throws RuntimeException;

	/**
	 * @param booking
	 * @return BookingInformation
	 * @throws RuntimeException
	 */
	public BookingInformation confirmBooking(BookingInformation booking)
			throws RuntimeException;

	/**
	 * @param query
	 * @param searchBasis
	 * @return List
	 * @throws RuntimeException
	 */
	public List<String> checkAvailabiltiy(String query, String searchBasis)
			throws RuntimeException;

	/**
	 * @param user
	 * @return User
	 * @throws RuntimeException
	 */
	public User updateUser(User user) throws RuntimeException;


	/**
	 * @param flight
	 * @return void
	 * @throws RuntimeException
	 */
	public void updateFlight(Flight flight) throws RuntimeException;
	
	/**
	 * @param username 
	 * @return User
	 * @throws RuntimeException
	 */
	public User getUserDetails(String username) throws RuntimeException;

	/**
	 * @return List
	 * @throws RuntimeException
	 */
	public List<Airport> getAirportDetails() throws RuntimeException;
}