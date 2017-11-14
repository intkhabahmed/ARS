package com.cg.ars.dao;

import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

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
public class AirlineDAOImpl implements IAirlineDAO {

	@PersistenceContext
	private EntityManager entityManager;

	private static Logger logger = Logger
			.getLogger(com.cg.ars.dao.AirlineDAOImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cg.ars.dao.IAirlineDAO#viewListOfFlights(java.lang.String,
	 * java.lang.String) It returns the list of flights on search basis to
	 * service layer
	 */
	@Override
	public String getAbbreviation(String cityName) throws RuntimeException {
		TypedQuery<String> sqlQuery = entityManager.createQuery(
				QueryMapper.GETABBREVIATION, String.class);
		sqlQuery.setParameter("location", cityName.toUpperCase());
		return sqlQuery.getSingleResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cg.as.dao.IAirlineDAO#viewListOfFlights(java.lang.String,
	 * java.lang.String) It returns the list of flight to service layer
	 */
	@Override
	public List<Flight> viewListOfFlights(String query, String searchBasis)
			throws RuntimeException {
		TypedQuery<Flight> sqlQuery = null;
		if (searchBasis.equals("dest")) {
			sqlQuery = entityManager.createQuery(
					QueryMapper.SEARCHFLIGHTBYARRIVALCITY, Flight.class);
			sqlQuery.setParameter(ARSConstants.ARRCITY, query);
		} else if (searchBasis.equals("day")) {
			sqlQuery = entityManager.createQuery(
					QueryMapper.SEARCHFLIGHTBYDEPARTUREDATE, Flight.class);
			sqlQuery.setParameter(ARSConstants.DEPDATE, Date.valueOf(query));
		} else if (searchBasis.equals("route")) {
			String[] route = query.split("=");
			sqlQuery = entityManager.createQuery(
					QueryMapper.SEARCHFLIGHTBYDEPARTUREANDARRIVALCITY,
					Flight.class);
			sqlQuery.setParameter(ARSConstants.DEPCITY, route[0]);
			sqlQuery.setParameter(ARSConstants.ARRCITY, route[1]);
		} else if (searchBasis.equals(ARSConstants.FLIGHTNO)) {
			sqlQuery = entityManager.createQuery(
					QueryMapper.SEARCHFLIGHTBYFLIGHTNUMBER, Flight.class);
			sqlQuery.setParameter(ARSConstants.FLIGHTNO, query);
		} else if (searchBasis.equals("all")) {
			sqlQuery = entityManager.createQuery(QueryMapper.FLIGHTINFORMATION,
					Flight.class);
		} else if (searchBasis.equals(ARSConstants.BYUSER)) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cg.as.dao.IAirlineDAO#viewBookings(java.lang.String,
	 * java.lang.String) It views the booking information and returns the result
	 * to service layer
	 */
	@Override
	public List<BookingInformation> viewBookings(String query,
			String searchBasis) throws RuntimeException {

		TypedQuery<BookingInformation> sqlQuery = null;

		if (searchBasis.equals(ARSConstants.BYFLIGHT)) {
			sqlQuery = entityManager.createQuery(
					QueryMapper.BOOKINGINFORMATIONOFAFLIGHT,
					BookingInformation.class);
			sqlQuery.setParameter(ARSConstants.FLIGHTNO, query);
		} else if (searchBasis.equals(ARSConstants.BYUSER)) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cg.as.dao.IAirlineDAO#validLogin(com.cg.as.entity.User) This
	 * function checks that user is valid or not and returns the result to
	 * service layer
	 */
	@Override
	public User validLogin(User user) throws RuntimeException {
		TypedQuery<User> sqlQuery = entityManager.createQuery(
				QueryMapper.VALIDATEUSERNAMEANDPASSWORD, User.class);
		sqlQuery.setParameter(ARSConstants.USER, user.getUsername());
		sqlQuery.setParameter(ARSConstants.PASS, user.getPwd());
		logger.info("Following User Logged in:" + user.getUsername());
		return sqlQuery.getSingleResult();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cg.as.dao.IAirlineDAO#signUp(com.cg.as.entity.User) This
	 * function does sign up and return user object to service layer
	 */
	@Override
	public User signUp(User user) throws RuntimeException {
		entityManager.persist(user);
		entityManager.flush();
		logger.info("New User signed in with following username:"
				+ user.getUsername());
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cg.as.dao.IAirlineDAO#bookingCancel(java.lang.String) This
	 * function cancels the booking and returns the booking object to service
	 * layer
	 */
	@Override
	public BookingInformation bookingCancel(int bookingId)
			throws RuntimeException {
		BookingInformation booking = entityManager.find(
				BookingInformation.class, bookingId);
		entityManager.remove(booking);
		entityManager.flush();
		logger.info("Booking cancelled for booking id: " + bookingId);
		return booking;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cg.as.dao.IAirlineDAO#flightOccupancyDetails(java.lang.String)
	 * It returns the total first seats, total business seats, no. of passengers
	 * in class type First and in class type Business to service layer
	 */
	@Override
	public int[] flightOccupancyDetails(String flightNo)
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
		sqlQuery = entityManager.createQuery(
				QueryMapper.PASSENGERSINFIRSTCLASSOFAFLIGHT, Integer.class);
		sqlQuery.setParameter(ARSConstants.FLIGHTNO, flightNo);
		seatDetails[2] = sqlQuery.getSingleResult();
		sqlQuery = entityManager.createQuery(
				QueryMapper.PASSENGERSINBUSINESSCLASSOFAFLIGHT, Integer.class);
		sqlQuery.setParameter(ARSConstants.FLIGHTNO, flightNo);
		seatDetails[3] = sqlQuery.getSingleResult();
		logger.info("Flight occupancy details retrieved for flight: "
				+ flightNo);
		return seatDetails;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cg.as.dao.IAirlineDAO#modifyBookingInformation(com.cg.as.entity.
	 * BookingInformation) It modifies the booking information and returns
	 * booking object to service layer
	 */
	@Override
	public BookingInformation modifyBookingInformation(
			BookingInformation booking) throws RuntimeException {
		booking = entityManager.merge(booking);
		entityManager.flush();
		logger.info("Booking modified for booking id: "
				+ booking.getBookingId());
		return booking;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cg.as.dao.IAirlineDAO#confirmBooking(com.cg.as.entity.BookingInformation
	 * ) It confirms the booking and returns booking object to service layer
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cg.ars.dao.IAirlineDAO#checkAvailabiltiy(java.lang.String,
	 * java.lang.String) It checks the availability of user in database and
	 * returns the result to service layer
	 */
	@Override
	public String checkAvailabiltiy(String query, String searchBasis)
			throws RuntimeException {
		TypedQuery<String> sqlQuery = null;
		String isAvail;
		if (searchBasis.equals(ARSConstants.BYUSERNAME)) {

			sqlQuery = entityManager.createQuery(
					QueryMapper.CHECKUSERNAMEISAVAILABLE, String.class);
			sqlQuery.setParameter(ARSConstants.QUERY, query);
		} else if (searchBasis.equals(ARSConstants.BYEMAIL)) {
			sqlQuery = entityManager.createQuery(
					QueryMapper.CHECKEMAILISAVAILABLE, String.class);
			sqlQuery.setParameter(ARSConstants.QUERY, query);
		}
		isAvail = sqlQuery.getSingleResult();
		return isAvail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cg.ars.dao.IAirlineDAO#updateUser(com.cg.ars.entity.User) It
	 * updates the details of user and returns User object to service layer
	 */
	@Override
	public User updateUser(User user) throws RuntimeException {
		entityManager.merge(user);
		entityManager.flush();
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cg.ars.dao.IAirlineDAO#updateFlight(com.cg.ars.entity.Flight) It
	 * updates the flight details
	 */
	@Override
	public void updateFlight(Flight flight) throws RuntimeException {
		entityManager.merge(flight);
		entityManager.flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cg.ars.dao.IAirlineDAO#getUserDetails(java.lang.String) It
	 * returns the details of given user to service layer
	 */
	@Override
	public User getUserDetails(String username) throws RuntimeException {
		TypedQuery<User> query = entityManager.createQuery(
				QueryMapper.USERINFORMATION, User.class);
		query.setParameter(ARSConstants.USERNAME, username);
		return query.getSingleResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cg.ars.dao.IAirlineDAO#getCities() It returns the list of all
	 * cities present in the database to service layer
	 */
	@Override
	public List<String> getCities() throws RuntimeException {
		TypedQuery<String> query = entityManager.createQuery(
				QueryMapper.GETALLCITIES, String.class);
		return query.getResultList();
	}

}