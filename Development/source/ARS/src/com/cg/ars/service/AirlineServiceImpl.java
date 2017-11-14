package com.cg.ars.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.ars.dao.AirlineDAO;
import com.cg.ars.entity.BookingInformation;
import com.cg.ars.entity.Flight;
import com.cg.ars.entity.User;
import com.cg.ars.exception.AirlineException;
import com.cg.ars.utility.ARSConstants;

@Service
@Transactional
public class AirlineServiceImpl implements AirlineService {

	@Autowired
	private AirlineDAO airlineDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cg.ars.service.IAirlineService#retrieveFlights(java.lang.String,
	 * java.lang.String) description: It calls the function
	 * viewListOfFlights(query, searchBasis) of AirlineDaoImpl and returns the
	 * list of flights to AirlineController
	 */
	@Override
	public List<Flight> retrieveFlights(String query, String searchBasis)
			throws RuntimeException {
		return airlineDAO.retrieveFlights(query, searchBasis);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cg.ars.service.IAirlineService#retrieveBookings(java.lang.String,
	 * java.lang.String) description: It calls the function
	 * retrieveBookings(query, searchBasis) of AirlineDaoImpl and returns the
	 * result to AirlineController
	 */
	@Override
	public List<BookingInformation> retrieveBookings(String query,
			String searchBasis) throws RuntimeException {
		return airlineDAO.retrieveBookings(query, searchBasis);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cg.ars.service.IAirlineService#addUser(com.cg.ars.entity.User)
	 * description: It calls the function addUser(user) of AirlineDaoImpl and
	 * returns the result to AirlineController
	 */
	@Override
	public User addUser(User user) throws RuntimeException {
		return airlineDAO.addUser(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cg.ars.service.IAirlineService#validateLogin(com.cg.ars.entity.User)
	 * description: It calls the function validateLogin(user) of AirlineDaoImpl
	 * and returns the result to AirlineController
	 */
	@Override
	public User validateLogin(User user) throws RuntimeException {
		return airlineDAO.validateLogin(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cg.ars.service.IAirlineService#cancelBooking(int) description:
	 * It calls the function cancelBooking(bookingId),
	 * viewListOfFlights(booking.getFlightNo(),"flightNo") and
	 * updateFlight(flight) of AirlineDaoImpl and returns the result to
	 * AirlineController
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
			flight.setFirstSeats(flight.getBussSeats()
					+ booking.getNoOfPassengers());
		}
		airlineDAO.updateFlight(flight);
		return booking;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cg.ars.service.IAirlineService#flightOccupancyDetails(java.lang.String
	 * ) description: It calls the function flightOccupancyDetails(flightNo) of
	 * AirlineDaoImpl and returns the result to AirlineController
	 */
	@Override
	public int[] getFlightOccupancyDetails(String flightNo)
			throws RuntimeException {
		return airlineDAO.getFlightOccupancyDetails(flightNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cg.ars.service.IAirlineService#confirmBooking(com.cg.ars.entity.
	 * BookingInformation) description: It calls the function
	 * confirmBooking(booking),
	 * viewListOfFlights(booking.getFlightNo(),"flightNo") and
	 * updateFlight(flight) of AirlineDaoImpl and returns the result to
	 * AirlineController
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
			flight.setFirstSeats(flight.getBussSeats()
					- booking.getNoOfPassengers());
		}
		airlineDAO.updateFlight(flight);
		return booking;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cg.ars.service.IAirlineService#forgotPassword(com.cg.ars.entity.User)
	 * description: It calls the function getUserDetails(user.getUsername()) of
	 * AirlineDaoImpl and returns the updated result to AirlineController
	 */
	@Override
	public User changePassword(User user) throws RuntimeException,
			AirlineException {
		String password = user.getPwd();
		user = airlineDAO.getUserDetails(user.getUsername());
		if (ARSConstants.CUSTOMER.equals(user.getRole())) {
			user.setPwd(password);
			return airlineDAO.updateUser(user);
		}
		throw new AirlineException(ARSConstants.USERNAMENOTEXIST);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cg.ars.service.IAirlineService#checkAvailabiltiy(java.lang.String,
	 * java.lang.String) description: It calls the function
	 * checkAvailabiltiy(query, searchBasis) of AirlineDaoImpl and if user is
	 * not available then returns false otherwise it returns true
	 */
	@Override
	public boolean checkAvailabiltiy(String query, String searchBasis)
			throws RuntimeException {
		if (airlineDAO.checkAvailabiltiy(query, searchBasis).isEmpty()) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cg.ars.service.IAirlineService#updateUser(com.cg.ars.entity.User)
	 * description: It calls the function of AirlineDaoImpl and returns the
	 * result to AirlineController
	 */
	@Override
	public User updateUser(User user) throws RuntimeException {
		return airlineDAO.updateUser(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cg.ars.service.IAirlineService#getCities() description: It calls
	 * the function getCities() of AirlineDaoImpl and returns the result to
	 * AirlineController
	 */
	@Override
	public List<String> getCities() throws RuntimeException {
		return airlineDAO.getCities();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cg.ars.service.IAirlineService#getAbbreviation(java.lang.String)
	 * description: It calls getAbbreviation(cityName) of data access layer and
	 * return the abbreviation of cities
	 */
	@Override
	public String getCityAbbreviation(String cityName) throws RuntimeException {
		return airlineDAO.getCityAbbreviation(cityName);
	}

}
