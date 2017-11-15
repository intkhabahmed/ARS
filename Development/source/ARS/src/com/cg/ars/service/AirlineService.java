package com.cg.ars.service;

import java.util.List;

import com.cg.ars.entity.Airport;
import com.cg.ars.entity.BookingInformation;
import com.cg.ars.entity.Flight;
import com.cg.ars.entity.User;
import com.cg.ars.exception.AirlineException;

public interface AirlineService {

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
	 * @param bookingId
	 * @return BookingInformation
	 * @throws RuntimeException
	 */
	public BookingInformation cancelBooking(int bookingId)
			throws RuntimeException;

	/**
	 * @param user
	 * @return User
	 * @throws RuntimeException
	 */
	public User validateLogin(User user) throws RuntimeException;

	/**
	 * @param flightNo
	 * @return Integer Array
	 * @throws RuntimeException
	 */
	public void checkFlightOccupancyDetails(String flightNo, String classType,
			int noOfPassengers) throws RuntimeException,AirlineException;

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
	 * @return boolean
	 * @throws RuntimeException
	 */
	public boolean checkAvailabiltiy(String query, String searchBasis)
			throws RuntimeException;

	/**
	 * @param username
	 * @param password
	 * @return User
	 * @throws RuntimeException
	 * @throws AirlineException
	 */
	public User changePassword(User user) throws RuntimeException,
			AirlineException;

	/**
	 * @param user
	 * @return User
	 * @throws RuntimeException
	 */
	public User updateUser(User user) throws RuntimeException;

	/**
	 * @return List
	 * @throws RuntimeException
	 */
	public List<Airport> getAirportDetails() throws RuntimeException;
}
