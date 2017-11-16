package com.cg.ars.test;

<<<<<<< HEAD
import static org.junit.Assert.*;
=======
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.verify;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
>>>>>>> 6e507750599e948f91433e979ed0d391e8c1ee7f

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.cg.ars.dao.AirlineDAO;
<<<<<<< HEAD
import com.cg.ars.entity.Flight;
=======
import com.cg.ars.entity.BookingInformation;
import com.cg.ars.entity.Flight;
import com.cg.ars.entity.User;
>>>>>>> 6e507750599e948f91433e979ed0d391e8c1ee7f
import com.cg.ars.service.AirlineService;
import com.cg.ars.service.AirlineServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class AirlineServiceImplTest {
	@Mock
	private AirlineDAO airlineDAO;
<<<<<<< HEAD
	
	@InjectMocks
	private AirlineService airlineService = new AirlineServiceImpl();
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
	}

	/*@Test
	public void testViewListOfFlights() {
		Flight flight = new Flight("SG-277","SPICE JET","MAA","CCU",Date.valueOf("2018-01-05"),Date.valueOf("2018-01-05"),"11:50","14:20",30,4567,40,5432,"2 hr 30 min");
		List<Flight> flights = new ArrayList<Flight>();
		flights.add(flight);
		String strTest = flight.getDeptCity()+"="+flight.getArrCity()+"="+flight.getDeptDate();
		assertEquals(flights,airlineService.viewListOfFlights(strTest, "dest"));
	}*/

=======

	@InjectMocks
	private AirlineService airlineService = new AirlineServiceImpl();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testValidateLogin() {
		User user = new User(150, "sample", "S@mple999",
				"sample@capgemini.com", "9876543210", "customer");
		stub(airlineDAO.validateLogin(user)).toReturn(user);
		assertEquals(user, airlineService.validateLogin(user));
		verify(airlineDAO).validateLogin(user);
	}

	@Test
	public void retrieveFlights() {
		Flight flight = new Flight("SG-277", "SPICE JET", "MAA", "CCU",
				Date.valueOf("2018-01-05"), Date.valueOf("2018-01-05"),
				"11:50", "14:20", 30, 4567, 40, 5432, "2 hr 30 min");
		List<Flight> flights = new ArrayList<Flight>();
		flights.add(flight);
		String strTest = flight.getDeptCity() + "=" + flight.getArrCity() + "="
				+ flight.getDeptDate();
		stub(airlineDAO.retrieveFlights(strTest, "byUser")).toReturn(flights);
		assertEquals(flights, airlineService.retrieveFlights(strTest, "byUser"));
		verify(airlineDAO).retrieveFlights(strTest, "byUser");
	}

	@Test
	public void testRetrieveBookings() {
		String searchBasis = "byBookingId";
		String query = "50";
		BookingInformation booking = new BookingInformation(50,
				"sample@capgemini.com", 1, "first", 4567, "1234567890", "MAA",
				"CCU", "SG-277", Date.valueOf("2017-11-15"),
				Date.valueOf("2018-01-05"));
		List<BookingInformation> bookings = new ArrayList<BookingInformation>();
		bookings.add(booking);
		stub(airlineDAO.retrieveBookings(query, searchBasis))
				.toReturn(bookings);
		assertEquals(bookings,
				airlineService.retrieveBookings(query, searchBasis));
		verify(airlineDAO).retrieveBookings(query, searchBasis);
	}
>>>>>>> 6e507750599e948f91433e979ed0d391e8c1ee7f
}
