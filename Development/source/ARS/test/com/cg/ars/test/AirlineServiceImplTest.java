package com.cg.ars.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.cg.ars.dao.AirlineDAO;
import com.cg.ars.entity.Flight;
import com.cg.ars.service.AirlineService;
import com.cg.ars.service.AirlineServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class AirlineServiceImplTest {
	@Mock
	private AirlineDAO airlineDAO;
	
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

}
