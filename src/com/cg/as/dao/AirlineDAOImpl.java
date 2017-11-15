package com.cg.as.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.cg.as.entity.BookingInformation;
import com.cg.as.entity.Flight;
import com.cg.as.entity.User;
import com.cg.as.exception.AirlineException;

@Repository
public class AirlineDAOImpl implements IAirlineDAO {
	/*private static Logger logger = Logger
			.getLogger(com.cg.as.dao.AirlineDAOImpl.class);*/

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.cg.dao.IAirlineDAO#viewListOfFlights() Method for retrieving all
	 * flight details
	 */
	@Override
	public List<Flight> viewListOfFlights(String query, String searchBasis)
			throws AirlineException {
		TypedQuery<Flight> sqlQuery = null;
		if (searchBasis.equals("dest")) {
			sqlQuery = entityManager.createQuery(
					"SELECT f FROM Flight f WHERE f.arrCity=:arrCity",
					Flight.class);
			sqlQuery.setParameter("arrCity", query);
		} else if (searchBasis.equals("day")) {
			sqlQuery = entityManager.createQuery(
					"SELECT f FROM Flight f WHERE f.deptDate=:deptDate",
					Flight.class);
			sqlQuery.setParameter("deptDate", query);
		} else if (searchBasis.equals("route")) {
			String route[] = query.split("-");
			sqlQuery = entityManager
					.createQuery(
							"SELECT f FROM Flight f WHERE f.deptCity=:deptCity AND f.arrCity=:arrCity",
							Flight.class);
			sqlQuery.setParameter("deptCity", route[0]);
			sqlQuery.setParameter("arrCity", route[1]);
		} else if (searchBasis.equals("flightNo")) {
			sqlQuery = entityManager.createQuery(
					"SELECT f FROM Flight f WHERE f.flightNo=:flightNo",
					Flight.class);
			sqlQuery.setParameter("flightNo", query);
		} else if (searchBasis.equals("all")) {
			sqlQuery = entityManager.createQuery("SELECT f FROM Flight f",
					Flight.class);
		}

		return sqlQuery.getResultList();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.cg.dao.IAirlineDAO#viewBookingsOfFlight(java.lang.String) Method
	 * to see booking details of a particular flight
	 */
	@Override
	public List<BookingInformation> viewBookings(String query,
			String searchBasis) throws AirlineException {

		TypedQuery<BookingInformation> sqlQuery = null;

		if (searchBasis.equals("byFlight")) {
			sqlQuery = entityManager.createQuery(
					"SELECT b FROM BookingInfo b WHERE b.flightNo=:flightNo",
					BookingInformation.class);
			sqlQuery.setParameter("flightNo", query);
		} else if (searchBasis.equals("byUser")) {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<BookingInformation> criteria = cb.createQuery(BookingInformation.class);
			Root<BookingInformation> bookingRoot = criteria.from(BookingInformation.class);
			criteria.select(bookingRoot);
			
			CriteriaQuery<User> userCriteria = cb.createQuery(User.class);
			Root<>
			List<BookingInformation> bookings = entityManager
					.createQuery(
							"SELECT b FROM BookingInformation b WHERE b.custEmail=(SELECT u.custEmail FROM USER u WHERE u.username=:user)",BookingInformation.class)
					.setParameter("user", query).getResultList();
			return bookings;
		}
		return sqlQuery.getResultList();

	}

	/**
	 * (non-Javadoc)
	 * @see com.cg.as.dao.IAirlineDAO#validLogin(com.cg.as.entity.User)
	 * 
	 */
	@Override
	public User validLogin(User user) throws AirlineException {
		TypedQuery<User> sqlQuery = entityManager
				.createQuery(
						"SELECT u FROM User WHERE u.username=:user AND password=:pass",
						User.class);
		sqlQuery.setParameter(":user", user.getUsername());
		sqlQuery.setParameter(":pass", user.getPassword());
		return sqlQuery.getSingleResult();

	}

	@Override
	public User signUp(User user) throws AirlineException {
		entityManager.persist(user);
		return user;
	}

	@Override
	public BookingInformation bookingCancel(String bookingId)
			throws AirlineException {
		BookingInformation booking = entityManager.find(BookingInformation.class, bookingId);
		entityManager.remove(booking);
		return booking;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cg.dao.IAirlineDAO#flightOccupancyDetails(java.lang.String,
	 * java.lang.String) for getting flight occupancy details
	 
	public int[] flightOccupancyDetails(String flightNo)
			throws AirlineException {
		int seats[] = new int[4];
		ResultSet rs = null;
		PreparedStatement pst = null;
		String sql = "";
		try {

			airlineConn = DBUtil.createConnection();
			sql = "select firstSeats from flightInformation where flightNo=UPPER(?)";
			pst = airlineConn.prepareStatement(sql);
			pst.setString(1, flightNo);
			rs = pst.executeQuery();
			if (rs.next()) {
				seats[0] = rs.getInt(1);
			}

			sql = "select bussSeats from flightInformation where flightNo=UPPER(?)";
			pst = airlineConn.prepareStatement(sql);
			pst.setString(1, flightNo);
			rs = pst.executeQuery();
			if (rs.next()) {
				seats[1] = rs.getInt(1);
			}

			sql = "select sum(no_of_passengers) from Bookinginformation where class_type='first' and flightno=UPPER(?) group by class_type,flightno";

			pst = airlineConn.prepareStatement(sql);
			pst.setString(1, flightNo);
			rs = pst.executeQuery();
			if (rs.next()) {
				seats[2] = rs.getInt(1);
			}

			sql = "select sum(no_of_passengers) from Bookinginformation where class_type='business' and flightno=UPPER(?) group by class_type,flightno";

			pst = airlineConn.prepareStatement(sql);
			pst.setString(1, flightNo);
			rs = pst.executeQuery();

			if (rs.next()) {
				seats[3] = rs.getInt(1);
			}
			logger.info("Flight occupany details checked for " + flightNo);
		} catch (Exception e) {
			logger.error("Flight occupancy details checking failed");
			throw new AirlineException(
					"Server Error: Cannot get number of seats", e);
		} finally {
			try {
				DBUtil.closeConnection();

			} catch (SQLException se) {
				throw new AirlineException(
						"Server Error: Problems in Closing Connection", se);
			}
		}
		return seats;
	}

	@Override
	public int bookingConfirm(String username, String flightNo,
			int noOfPassengers, String classType, String creditCard)
			throws AirlineException {
		String depCity = null;
		String arrCity = null;
		double fare = 0;
		int status = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBUtil.createConnection();
			;
			String sql = "SELECT * FROM FLIGHTINFORMATION WHERE FLIGHTNO=UPPER(?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, flightNo);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				depCity = rs.getString(3);
				arrCity = rs.getString(4);
				fare = 0;
				if (classType.equalsIgnoreCase("first")) {
					fare = rs.getDouble(10);
				} else
					fare = rs.getDouble(12);
				fare *= noOfPassengers;
			}

			String sql2 = "INSERT INTO BOOKINGINFORMATION VALUES(ticketbooking_id_seq.nextval,(SELECT cust_email FROM users WHERE username=?),?,?,?,?,?,?,UPPER(?))";
			pst = conn.prepareStatement(sql2);
			pst.setString(1, username);
			pst.setInt(2, noOfPassengers);
			pst.setString(3, classType);
			pst.setDouble(4, fare);
			pst.setString(5, creditCard);
			pst.setString(6, depCity);
			pst.setString(7, arrCity);
			pst.setString(8, flightNo);
			status = pst.executeUpdate();

			if (status == 1) {
				rs = pst.executeQuery("SELECT ticketbooking_id_seq.currval from dual");
				if (rs.next()) {
					return rs.getInt(1);
				}

			}
			logger.info(username + "booked the tickets");
		} catch (Exception e) {
			logger.error("Booking failed");
			throw new AirlineException(
					"Server Error: Cannot process booking of ticket", e);
		} finally {
			try {
				DBUtil.closeConnection();
			} catch (SQLException e) {
				throw new AirlineException(
						"Server Error: Cannot close database connection", e);
			}
		}
		return status;

	}

	@Override
	public int checkAvailability(String query, String type)
			throws AirlineException {
		int status = 0;
		Connection connBook = null;
		PreparedStatement pstBook = null;
		String sql = "";

		try {
			connBook = DBUtil.createConnection();
			if (type.equals("username")) {
				sql = "Select count(username) from users where username=?";
				pstBook = connBook.prepareStatement(sql);
				pstBook.setString(1, query);

			} else if (type.equals("email")) {
				sql = "Select count(cust_email) from users where cust_email=?";
				pstBook = connBook.prepareStatement(sql);
				pstBook.setString(1, query);
			}

			ResultSet rset = pstBook.executeQuery();
			if (rset.next()) {
				status = rset.getInt(1);
			}
			logger.info("Availability checked");
		} catch (SQLException se) {
			logger.error("Availability checking  failed");
			throw new AirlineException(
					"Server Error: Problem in cheking the query", se);

		} finally {
			try {
				DBUtil.closeConnection();

			} catch (SQLException se) {
				throw new AirlineException(
						"Server Error: Problems in Closing Connection", se);
			}
		}
		return status;
	}*/
}
