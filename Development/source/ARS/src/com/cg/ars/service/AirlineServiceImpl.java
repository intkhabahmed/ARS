package com.cg.ars.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.ars.dao.AirlineDAO;
import com.cg.ars.entity.Airport;
import com.cg.ars.entity.BookingInformation;
import com.cg.ars.entity.Flight;
import com.cg.ars.entity.User;
import com.cg.ars.exception.AirlineException;
import com.cg.ars.utility.ARSConstants;

/**
 * @author INTKHAB
 *
 */
@Service
@Transactional
public class AirlineServiceImpl implements AirlineService {

	/**
	 * Injecting the airlineDAO Object
	 */
	@Autowired
	private AirlineDAO airlineDAO;

	/**
	 * @description - Retrieves list of flights from database
	 * @param searchBasis
	 *            - Criteria on which list of flights is returned. Can be by
	 *            source, destination and date
	 * @param query
	 *            - Values of (source, destination and date) passed
	 * @return List - List of flights based on searchBasis is returned
	 * @exception RuntimeException
	 *                - If no flight is present in the database
	 */
	@Override
	public List<Flight> retrieveFlights(String query, String searchBasis)
			throws RuntimeException {
		return airlineDAO.retrieveFlights(query, searchBasis);
	}

	/**
	 * @description - Retrieves a list of bookings based on search basis
	 * @param searchBasis
	 *            - Criteria on which list of bookings is returned. Can be by
	 *            flight number, username and booking id
	 * @param query
	 *            - Values of username, flight number or booking id is passed
	 * @return List - List of bookings based on searchBasis is returned
	 * @exception RuntimeException
	 *                - If no booking details is present in the database
	 */
	@Override
	public List<BookingInformation> retrieveBookings(String query,
			String searchBasis) throws RuntimeException {
		return airlineDAO.retrieveBookings(query, searchBasis);
	}

	/**
	 * @description - Inserts a new user in the database
	 * @param user
	 *            - User who is to be inserted in the database
	 * @return user - User who is inserted in the database
	 * @exception RuntimeException
	 *                - If user is already present in the database
	 */
	@Override
	public User addUser(User user) throws RuntimeException {
		return airlineDAO.addUser(user);
	}

	/**
	 * @description - Validates user credentials
	 * @param user
	 *            - User for whom credentials are validated
	 * @return user - User is returned if found
	 * @exception RuntimeException
	 *                - If no flight is present in the database
	 */
	@Override
	public User validateLogin(User user) throws RuntimeException {
		return airlineDAO.validateLogin(user);
	}

	/**
	 * @description - Cancels the booking details for given booking Id
	 * @param bookingId
	 *            - Booking Id for which booking is cancelled
	 * @return BookingInformation - Booking details for which booking has been
	 *         cancelled is returned
	 * @exception RuntimeException
	 *                - If no booking details are found corresponding to a
	 *                booking id
	 */
	@Override
	public BookingInformation cancelBooking(int bookingId)
			throws RuntimeException {
		BookingInformation booking = airlineDAO.cancelBooking(bookingId);
		Flight flight = airlineDAO.retrieveFlights(booking.getFlightNo(),
				ARSConstants.FLIGHTNO).get(0);
		if (ARSConstants.FIRST.equalsIgnoreCase(booking.getClassType())) {
			flight.setFirstSeats(flight.getFirstSeats()
					+ booking.getNoOfPassengers());
		} else if (ARSConstants.BUSINESS.equalsIgnoreCase(booking
				.getClassType())) {
			flight.setBussSeats(flight.getBussSeats()
					+ booking.getNoOfPassengers());
		}
		airlineDAO.updateFlight(flight);
		return booking;
	}

	/**
	 * @description - Retrieves total first seats, total business seats, no. of
	 *              passengers in class type First and business Type
	 * @param flightNo
	 *            - flightNo for which total first seats, total business seats,
	 *            are to be retrieved
	 * @param classType
	 *            - Airline Class (First or Business)
	 * @param noOfPassengers
	 *            - No of passengers selected to book flight
	 * @return Integer Array - An array of total first seats, total business
	 *         seats, no. of passengers in class type First and business Type
	 * @exception RuntimeException
	 *                - If a flight for a particular flight number is not
	 *                available
	 */
	@Override
	public void checkFlightOccupancyDetails(String flightNo, String classType,
			int noOfPassengers) throws RuntimeException, AirlineException {
		int[] seats = airlineDAO.getFlightOccupancyDetails(flightNo);
		if ("First".equalsIgnoreCase(classType)) {
			if (seats[0] - noOfPassengers < 0) {
				throw new AirlineException(ARSConstants.NOTENOUGHFIRSTSEATS);
			}
		} else {
			if (seats[1] - noOfPassengers < 0) {
				throw new AirlineException(ARSConstants.NOTENOUGHBUSINESSSEATS);
			}
		}
	}

	/**
	 * @description - Confirms the booking for given booking Object
	 * @param booking
	 *            - Booking for which booking is confirmed
	 * @return BookingInformation - Booking object which has been inserted to
	 *         the database
	 * @exception RuntimeException
	 *                - If no seats are available for a particular flight
	 */
	@Override
	public BookingInformation confirmBooking(BookingInformation booking)
			throws RuntimeException {
		booking = airlineDAO.confirmBooking(booking);
		Flight flight = airlineDAO.retrieveFlights(booking.getFlightNo(),
				ARSConstants.FLIGHTNO).get(0);
		if (ARSConstants.FIRST.equalsIgnoreCase(booking.getClassType())) {
			flight.setFirstSeats(flight.getFirstSeats()
					- booking.getNoOfPassengers());
		} else if (ARSConstants.BUSINESS.equalsIgnoreCase(booking
				.getClassType())) {
			flight.setBussSeats(flight.getBussSeats()
					- booking.getNoOfPassengers());
		}
		airlineDAO.updateFlight(flight);
		return booking;
	}

	/**
	 * @description - Changes the password of a user
	 * @param User
	 *            - Password for this user
	 * @exception RuntimeException
	 *                - When user is not found
	 */
	@Override
	public User changePassword(User user) throws RuntimeException,
			AirlineException {
		String password = user.getPwd();
		user = airlineDAO.getUserDetails(user.getUsername());
		if (ARSConstants.CUSTOMER.equals(user.getRole())) {
			user.setPwd(password);
		}
		return airlineDAO.updateUser(user);
	}

	/**
	 * @description - checks the availability of user
	 * @param query
	 *            - Values of username, user mail id
	 * @param searchBasis
	 *            - Criteria on which checks the availability of user.Can be
	 *            userName , userEmail
	 * @return List - List of username or email which matches with the username
	 *         or email passes as parameter
	 * @exception RuntimeException
	 *                - If username or email id does not exist
	 */
	@Override
	public boolean checkAvailabiltiy(String query, String searchBasis)
			throws RuntimeException {
		if (airlineDAO.checkAvailabiltiy(query, searchBasis).isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * @description - Modifies the details of a user
	 * @param User
	 *            - User details are modified for this user
	 * @exception RuntimeException
	 *                - When user is not found
	 */
	@Override
	public User updateUser(User user) throws RuntimeException {
		return airlineDAO.updateUser(user);
	}

	/**
	 * @description - Returns the list of airport from the database
	 * @return List - A list of airports in the database is returned
	 * @exception RuntimeException
	 *                - When airport detail is not found
	 */
	@Override
	public List<Airport> getAirportDetails() throws RuntimeException {
		return airlineDAO.getAirportDetails();
	}
}