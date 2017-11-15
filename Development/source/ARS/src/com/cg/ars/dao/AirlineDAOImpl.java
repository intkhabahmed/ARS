package com.cg.ars.dao;

import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cg.ars.entity.Airport;
import com.cg.ars.entity.BookingInformation;
import com.cg.ars.entity.Flight;
import com.cg.ars.entity.User;
import com.cg.ars.utility.ARSConstants;
import com.cg.ars.utility.QueryMapper;

/**
 * @author prasrani
 *
 */

@Repository
public class AirlineDAOImpl implements AirlineDAO {

	@PersistenceContext
	private EntityManager entityManager;

	private static Logger logger = Logger
			.getLogger(com.cg.ars.dao.AirlineDAOImpl.class);
	
	/**
	 * @description - Retrieves list of flights from database
	 * @param searchBasis - Criteria on which list of flights is returned. Can be by source, destination and date
	 * @param query - Values of (source, destination and date) passed
	 * @return List - List of flights based on searchBasis is returned
	 * @exception RuntimeException - If no flight is present in the database
	 */
	@Override
	public List<Flight> retrieveFlights(String query, String searchBasis)
			throws RuntimeException {
		TypedQuery<Flight> sqlQuery = null;
		if (searchBasis.equals(ARSConstants.FLIGHTNO)) {
			sqlQuery = entityManager.createQuery(
					QueryMapper.SEARCHFLIGHTBYFLIGHTNUMBER, Flight.class);
			sqlQuery.setParameter(ARSConstants.FLIGHTNO, query);
		} else if (searchBasis.equals("all")) {
			sqlQuery = entityManager.createQuery(QueryMapper.FLIGHTINFORMATION,
					Flight.class);
		} else if (searchBasis.equals(ARSConstants.BYROUTE)) {
			String[] route = query.split("=");
			sqlQuery = entityManager
					.createQuery(
							QueryMapper.SEARCHFLIGHTBYARRIVALANDDEPARTURECITYANDDEPARTUREDATE,
							Flight.class);
			sqlQuery.setParameter(ARSConstants.DEPCITY, route[0]);
			sqlQuery.setParameter(ARSConstants.ARRCITY, route[1]);
			sqlQuery.setParameter(ARSConstants.DEPDATE, Date.valueOf(route[2]));
		}
		logger.info("List of flights retrieved");
		return sqlQuery.getResultList();
	}

	/**
	 * @description - Retrieves a list of bookings based on search basis
	 * @param searchBasis - Criteria on which list of bookings is returned. Can be by flight number, username and booking id
	 * @param query - Values of username, flight number or booking id is passed
	 * @return List - List of bookings based on searchBasis is returned
	 * @exception RuntimeException - If no booking details is present in the database
	 */
	@Override
	public List<BookingInformation> retrieveBookings(String query,
			String searchBasis) throws RuntimeException {

		TypedQuery<BookingInformation> sqlQuery = null;

		if (searchBasis.equals(ARSConstants.BYFLIGHT)) {
			sqlQuery = entityManager.createQuery(
					QueryMapper.BOOKINGINFORMATIONOFAFLIGHT,
					BookingInformation.class);
			sqlQuery.setParameter(ARSConstants.FLIGHTNO, query);
		} else if (searchBasis.equals(ARSConstants.BYROUTE)) {
			TypedQuery<User> userQuery = entityManager.createQuery(
					QueryMapper.USERINFORMATION, User.class);
			userQuery.setParameter(ARSConstants.USERNAME, query);
			User user = userQuery.getSingleResult();
			sqlQuery = entityManager.createQuery(
					QueryMapper.BOOKINGINFORMATIONBYEMAIL,
					BookingInformation.class);
			sqlQuery.setParameter(ARSConstants.EMAIL, user.getEmail());
		} else if (searchBasis.equals(ARSConstants.BYBOOKINGID)) {
			sqlQuery = entityManager.createQuery(
					QueryMapper.BOOKINGINFOBYBOOKINGID,
					BookingInformation.class);
			sqlQuery.setParameter(ARSConstants.BOOKINGID,
					Integer.parseInt(query));
		}

		logger.info("List of Bookings retrieved");
		return sqlQuery.getResultList();

	}

	/**
	 * @description - Validates user credentials
	 * @param user - User for whom credentials are validated
	 * @return user - User is returned if found
	 * @exception RuntimeException - If no flight is present in the database
	 */
	@Override
	public User validateLogin(User user) throws RuntimeException {
		TypedQuery<User> sqlQuery = entityManager.createQuery(
				QueryMapper.VALIDATEUSERNAMEANDPASSWORD, User.class);
		sqlQuery.setParameter(ARSConstants.USER, user.getUsername());
		sqlQuery.setParameter(ARSConstants.PASS, user.getPwd());
		logger.info("Following User Logged in:" + user.getUsername());
		return sqlQuery.getSingleResult();

	}

	/**
	 * @description - Inserts a new user in the database
	 * @param user - User who is to be inserted in the database
	 * @return user - User who is inserted in the database
	 * @exception RuntimeException - If user is already present in the database
	 */
	@Override
	public User addUser(User user) throws RuntimeException {
		entityManager.persist(user);
		entityManager.flush();
		logger.info("New User signed in with following username:"
				+ user.getUsername());
		return user;
	}
	
	/**
	 * @description - Cancels the booking details for given booking Id
	 * @param bookingId - Booking Id for which booking is cancelled
	 * @return BookingInformation - Booking details for which booking has been cancelled is returned
	 * @exception RuntimeException - If no booking details are found corresponding to a booking id
	 */
	@Override
	public BookingInformation cancelBooking(int bookingId)
			throws RuntimeException {
		BookingInformation booking = entityManager.find(
				BookingInformation.class, bookingId);
		entityManager.remove(booking);
		entityManager.flush();
		logger.info("Booking cancelled for booking id: " + bookingId);
		return booking;
	}

	/**
	 * @description - Retrieves total first seats, total business seats, no. of passengers in class type First and business Type
	 * @param flightNo - flightNo for which total first seats, total business seats, no. of passengers in class type First and business Type are calculated
	 * @return Integer Array - An array of total first seats, total business seats, no. of passengers in class type First and business Type
	 * @exception RuntimeException - If a flight for a particular flight number is not available
	 */
	@Override
	public int[] getFlightOccupancyDetails(String flightNo)
			throws RuntimeException {
		int[] seatDetails = new int[4];
		TypedQuery<Integer> sqlQuery = null;
		sqlQuery = entityManager.createQuery(QueryMapper.FIRSTSEATSOFAFLIGHT,
				Integer.class);
		sqlQuery.setParameter(ARSConstants.FLIGHTNO, flightNo);
		seatDetails[0] = sqlQuery.getSingleResult();
		sqlQuery = entityManager.createQuery(
				QueryMapper.BUSINESSSEATSOFAFLIGHT, Integer.class);
		sqlQuery.setParameter(ARSConstants.FLIGHTNO, flightNo);
		seatDetails[1] = sqlQuery.getSingleResult();
		logger.info("Flight occupancy details retrieved for flight: "
				+ flightNo);
		return seatDetails;
	}

	/**
	 * @description - Confirms the booking for given booking Object
	 * @param booking - Booking for which booking is confirmed
	 * @return BookingInformation - Booking object which has been inserted to the database
	 * @exception RuntimeException - If no seats are available for a particular flight
	 */
	@Override
	public BookingInformation confirmBooking(BookingInformation booking)
			throws RuntimeException {
		entityManager.persist(booking);
		entityManager.flush();
		logger.info("Booking confirmed for booking id: "
				+ booking.getBookingId());
		return booking;
	}

	/**
	 * @description - checks the availability of user 
	 * @param query - Values of username, user mail id
	 * @param searchBasis - Criteria on which checks the availability of user.Can be userName , userEmail
	 * @return List - List of username or email which matches with the username or email passes as parameter
	 * @exception RuntimeException - If username or email id does not exist
	 */
	@Override
	public List<String> checkAvailabiltiy(String query, String searchBasis)
			throws RuntimeException {
		TypedQuery<String> sqlQuery = null;
		if (searchBasis.equals(ARSConstants.BYUSERNAME)) {
			sqlQuery = entityManager.createQuery(
					QueryMapper.CHECKUSERNAMEISAVAILABLE, String.class);
			sqlQuery.setParameter(ARSConstants.QUERY, query);
		} else if (searchBasis.equals(ARSConstants.BYEMAIL)) {
			sqlQuery = entityManager.createQuery(
					QueryMapper.CHECKEMAILISAVAILABLE, String.class);
			sqlQuery.setParameter(ARSConstants.QUERY, query);
		}
		return sqlQuery.getResultList();
	}

	/**
	 * @description - Modifies the details of a user
	 * @param User -  User details are modified for this user
	 * @exception RuntimeException - When user is not found
	 */
	@Override
	public User updateUser(User user) throws RuntimeException {
		entityManager.merge(user);
		entityManager.flush();
		return user;
	}

	/**
	 * @description - Returns the details of a user
	 * @param flight -  User details are fetched for this username
	 * @exception RuntimeException - When user is not found
	 */
	@Override
	public void updateFlight(Flight flight) throws RuntimeException {
		entityManager.merge(flight);
		entityManager.flush();
	}

	/**
	 * @description - Returns the details of a user
	 * @param username -  User details are fetched for this username
	 * @return User - Fetched  user details from the database is returned 
	 * @exception RuntimeException - When user is not found
	 */
	@Override
	public User getUserDetails(String username) throws RuntimeException {
		TypedQuery<User> query = entityManager.createQuery(
				QueryMapper.USERINFORMATION, User.class);
		query.setParameter(ARSConstants.USERNAME, username);
		return query.getSingleResult();
	}

	/**
	 * @description - Returns the list of airport from the database
	 * @return List - A list of airports in the database is returned
	 * @exception RuntimeException - When airport detail is not found
	 */
	@Override
	public List<Airport> getAirportDetails() throws RuntimeException {
		TypedQuery<Airport> query = entityManager.createQuery(
				QueryMapper.GETAIRPORTDETAILS, Airport.class);
		return query.getResultList();
	}

}