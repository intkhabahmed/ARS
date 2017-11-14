package com.cg.ars.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.cg.ars.entity.BookingInformation;
import com.cg.ars.entity.Flight;
import com.cg.ars.entity.User;
import com.cg.ars.service.IAirlineService;
import com.cg.ars.utility.ARSConstants;
import com.cg.ars.utility.MyUtil;

/**
 * @author inahmed
 *
 */
@Controller
@SessionAttributes(ARSConstants.USER)
public class AirlineController {

	@Autowired
	IAirlineService airlineService;

	@RequestMapping(value = ARSConstants.URLLISTOFFLIGHT, method = RequestMethod.POST)
	public String getAllFlights(
			@ModelAttribute(ARSConstants.BOOKING) BookingInformation bookingInformation,
			Model model) {
		try {
			String src = airlineService.getAbbreviation(bookingInformation
					.getSrcCity());
			String dest = airlineService.getAbbreviation(bookingInformation
					.getDestCity());
			String str = src + "=" + dest + "="
					+ bookingInformation.getTravelDate();

			List<Flight> flights = airlineService.viewListOfFlights(str,
					ARSConstants.BYUSER);
			if (flights.isEmpty()) {
				model.addAttribute(ARSConstants.FLIGHTS, null);
			} else {
				model.addAttribute(ARSConstants.FLIGHTS, flights);
			}
			model.addAttribute(ARSConstants.BOOKING, bookingInformation);

			model.addAttribute(ARSConstants.CLASSTYPEOPTION, new String[] {
					ARSConstants.FIRST, ARSConstants.BUSINESS });
			model.addAttribute(ARSConstants.AIRPORT, airlineService.getCities());
			model.addAttribute(ARSConstants.DATE, Date.valueOf(LocalDate.now()));
			if (bookingInformation.getSrcCity().equals(
					bookingInformation.getDestCity())) {
				throw new RuntimeException(
						ARSConstants.SOURCEDESTINATIONCANNOTSAME);
			}
		} catch (RuntimeException e) {

			model.addAttribute(ARSConstants.MESSAGE,
					ARSConstants.ERROR + e.getMessage());
			return ARSConstants.INDEX;
		}
		return ARSConstants.FLIGHTLIST;
	}

	/**
	 * @description It calls the function getCities() of AirlineServiceImpl
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = ARSConstants.URLINDEX)
	public String showHome(Model model, HttpSession session) {
		try {
			if (session.getAttribute(ARSConstants.USER) == null) {
				model.addAttribute(ARSConstants.USER, new User());
			} else {
				model.addAttribute(ARSConstants.USER,
						session.getAttribute(ARSConstants.USER));
			}
			model.addAttribute(ARSConstants.BOOKING, new BookingInformation());
			model.addAttribute(ARSConstants.CLASSTYPEOPTION, new String[] {
					ARSConstants.FIRST, ARSConstants.BUSINESS });

			model.addAttribute(ARSConstants.AIRPORT, airlineService.getCities());
			model.addAttribute(ARSConstants.DATE, Date.valueOf(LocalDate.now()));
		} catch (RuntimeException e) {
			model.addAttribute(ARSConstants.MESSAGE,
					ARSConstants.ERROR + e.getMessage());
		}
		return ARSConstants.INDEX;
	}

	/**
	 * @description It adds booking object and user object to model
	 * @param model
	 * @return
	 */
	@RequestMapping(value = ARSConstants.URLSHOWLOGIN)
	public String showLogin(Model model) {
		model.addAttribute(ARSConstants.BOOKING, new BookingInformation());
		model.addAttribute(ARSConstants.USER, new User());
		return ARSConstants.LOGIN;
	}

	/**
	 * @description It adds booking object and user object to model
	 * @param model
	 * @param bookingInformation
	 * @return
	 */
	@RequestMapping(value = ARSConstants.URLSHOWLOGINAFTERSEARCH, method = RequestMethod.POST)
	public String showLoginAfterSearch(
			Model model,
			@ModelAttribute(ARSConstants.BOOKING) BookingInformation bookingInformation) {
		model.addAttribute(ARSConstants.USER, new User());
		model.addAttribute(ARSConstants.BOOKING, bookingInformation);
		return ARSConstants.LOGIN;
	}

	/**
	 * @description It adds user object to model
	 * @param model
	 * @return
	 */
	@RequestMapping(value = ARSConstants.URLSHOWSIGNUP)
	public String showSignup(Model model) {
		model.addAttribute(ARSConstants.USER, new User());
		return ARSConstants.SIGNUP;
	}

	/**
	 * @description signUp
	 * @param model
	 * @param user
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = ARSConstants.URLSIGNUP, method = RequestMethod.POST)
	public String signup(Model model,
			@Valid @ModelAttribute(ARSConstants.USER) User user,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			model.addAttribute(ARSConstants.USER, user);
			return ARSConstants.SIGNUP;
		} else {
			try {
				user.setRole(ARSConstants.CUSTOMER);
				if (!airlineService.checkAvailabiltiy(user.getUsername(),
						ARSConstants.BYUSERNAME)) {
					throw new RuntimeException(ARSConstants.USERNAMETAKEN);
				}
				if (!airlineService.checkAvailabiltiy(user.getEmail(),
						ARSConstants.BYEMAIL)) {
					throw new RuntimeException(ARSConstants.EMAILTAKEN);
				}
				airlineService.signUp(user);
				model.addAttribute(ARSConstants.MESSAGE,
						ARSConstants.SIGNUPSUCCESS);
				model.addAttribute(ARSConstants.USER, new User());
				return ARSConstants.LOGIN;
			} catch (RuntimeException e) {
				model.addAttribute(ARSConstants.MESSAGE, e.getMessage());
				model.addAttribute(ARSConstants.BYUSER, user);
				return ARSConstants.SIGNUP;
			}
		}

	}

	/**
	 * @description logout
	 * @param model
	 * @param status
	 * @return
	 */
	@RequestMapping(value = ARSConstants.URLLOGOUT)
	public String logout(Model model, SessionStatus status) {
		status.setComplete();
		model.addAttribute(ARSConstants.BOOKING, new BookingInformation());
		model.addAttribute(ARSConstants.CLASSTYPEOPTION, new String[] {
				ARSConstants.FIRST, ARSConstants.BUSINESS });
		model.addAttribute(ARSConstants.DATE, Date.valueOf(LocalDate.now()));
		return ARSConstants.INDEX;
	}

	/**
	 * @description It calls validLogin(user) of AirlineServiceImpl
	 * @param user
	 * @param model
	 * @param req
	 * @return
	 */
	@RequestMapping(value = ARSConstants.URLLOGIN)
	public String loginValidation(@ModelAttribute(ARSConstants.USER) User user,
			Model model, HttpServletRequest req) {
		String returnPage = "";
		try {
			user = airlineService.validLogin(user);
			if (user != null) {
				model.addAttribute(ARSConstants.BOOKING,
						new BookingInformation());
				model.addAttribute(ARSConstants.CLASSTYPEOPTION, new String[] {
						ARSConstants.FIRST, ARSConstants.BUSINESS });
				model.addAttribute(ARSConstants.USER, user);
				model.addAttribute(ARSConstants.DATE,
						Date.valueOf(LocalDate.now()));
				returnPage = ARSConstants.INDEX;
			}

		} catch (RuntimeException e) {
			model.addAttribute(ARSConstants.MESSAGE,
					ARSConstants.INVALIDUSERNAMEPWD);
			model.addAttribute(ARSConstants.USER, new User());
			returnPage = ARSConstants.LOGIN;
		}
		return returnPage;
	}

	/**
	 * @description this function validates the login details after searching
	 *              the flight
	 * @param user
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = ARSConstants.URLLOGINAFTERSEARCH)
	public String loginValidationAfterSearch(
			@ModelAttribute(ARSConstants.USER) User user, Model model,
			HttpSession session) {
		String returnPage = "";
		BookingInformation bookingInformation = (BookingInformation) session
				.getAttribute(ARSConstants.BOOKINGINFO);

		try {
			user = airlineService.validLogin(user);
			if (user != null) {
				session.removeAttribute(ARSConstants.BOOKINGINFO);
				List<Flight> flights = airlineService
						.viewListOfFlights(bookingInformation.getFlightNo(),
								ARSConstants.FLIGHTNO);
				if (ARSConstants.FIRST.equalsIgnoreCase(bookingInformation
						.getClassType())) {
					bookingInformation.setTotalFare(MyUtil.calculatefare(
							bookingInformation.getNoOfPassengers(), flights
									.get(0).getFirstSeatsFare()));
				} else if (ARSConstants.BUSINESS
						.equalsIgnoreCase(bookingInformation.getClassType())) {
					bookingInformation.setTotalFare(MyUtil.calculatefare(
							bookingInformation.getNoOfPassengers(), flights
									.get(0).getBussSeatsFare()));
				}

				bookingInformation.setUserEmail(user.getEmail());
				bookingInformation
						.setBookingDate(Date.valueOf(LocalDate.now()));
				model.addAttribute(ARSConstants.FLIGHT, flights.get(0));
				returnPage = ARSConstants.BOOKING;
			}

		} catch (RuntimeException e) {
			model.addAttribute(ARSConstants.MESSAGE,
					ARSConstants.INVALIDUSERNAMEPWD);
			model.addAttribute(ARSConstants.USER, new User());

			returnPage = ARSConstants.LOGIN;
		}
		model.addAttribute(ARSConstants.USER, user);
		model.addAttribute(ARSConstants.BOOKING, bookingInformation);
		return returnPage;
	}

	/**
	 * @description It calls viewListOfFlights function of AirlineServiceImpl
	 *              then book the flights
	 * @param bookingInformation
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = ARSConstants.URLSHOWBOOKING, method = RequestMethod.POST)
	public String bookFlight(
			@ModelAttribute(ARSConstants.BOOKING) BookingInformation bookingInformation,
			Model model, HttpSession session) {
		try {
			List<Flight> flights = airlineService.viewListOfFlights(
					bookingInformation.getFlightNo(), ARSConstants.FLIGHTNO);
			if (ARSConstants.FIRST.equalsIgnoreCase(bookingInformation
					.getClassType())) {
				bookingInformation.setTotalFare(MyUtil.calculatefare(
						bookingInformation.getNoOfPassengers(), flights.get(0)
								.getFirstSeatsFare()));
			} else if (ARSConstants.BUSINESS
					.equalsIgnoreCase(bookingInformation.getClassType())) {
				bookingInformation.setTotalFare(MyUtil.calculatefare(
						bookingInformation.getNoOfPassengers(), flights.get(0)
								.getBussSeatsFare()));
			}
			bookingInformation.setUserEmail(((User) session
					.getAttribute(ARSConstants.USER)).getEmail());
			bookingInformation.setBookingDate(Date.valueOf(LocalDate.now()));
			model.addAttribute(ARSConstants.FLIGHT, flights.get(0));
			model.addAttribute(ARSConstants.BOOKING, bookingInformation);
		} catch (RuntimeException e) {
			e.getMessage();
		}
		return ARSConstants.BOOKING;

	}

	/**
	 * @description It checks that flight is booked or not
	 * @param bookingInformation
	 * @param model
	 * @return
	 * @throws RuntimeException
	 */
	@RequestMapping(value = ARSConstants.URLCONFIRMBOOKING, method = RequestMethod.POST)
	public String confirmBooking(
			@ModelAttribute(ARSConstants.BOOKING) BookingInformation bookingInformation,
			Model model) throws RuntimeException {
		try {
			airlineService.confirmBooking(bookingInformation);
			model.addAttribute(
					ARSConstants.MESSAGE,
					"Your flight booking is successful with bookingId-"
							+ bookingInformation.getBookingId()
							+ " for Flight No: "
							+ bookingInformation.getFlightNo());
		} catch (RuntimeException e) {
			model.addAttribute(ARSConstants.MESSAGE,
					ARSConstants.ERROR + e.getMessage());
		}
		return ARSConstants.BOOKINGSUCCESS;
	}

	/**
	 * @description It updates the user details
	 * @param user
	 * @param bindingResult
	 * @param model
	 * @return
	 */
	@RequestMapping(value = ARSConstants.URLUPDATEUSER, method = RequestMethod.POST)
	public String updateUser(
			@ModelAttribute(ARSConstants.USEROBJ) @Valid User user,
			BindingResult bindingResult, Model model) {
		try {
			model.addAttribute(ARSConstants.BOOKINGS, airlineService
					.viewBookings(user.getUsername(), ARSConstants.BYUSER));
			if (bindingResult.hasErrors()) {

				model.addAttribute(ARSConstants.USEROBJ, user);
			} else {

				user.setRole(ARSConstants.CUSTOMER);
				airlineService.updateUser(user);

				model.addAttribute(ARSConstants.MESSAGE,
						"Information updated successfully");
				model.addAttribute(ARSConstants.USEROBJ, user);

			}
		} catch (RuntimeException e) {
			model.addAttribute(ARSConstants.MESSAGE,
					ARSConstants.ERROR + e.getMessage());
		}
		return ARSConstants.USERPROFILE;
	}

	/**
	 * @description It shows user profile
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = ARSConstants.URLSHOWPROFILE)
	public String showUserProfile(Model model, HttpSession session) {
		User user = (User) session.getAttribute(ARSConstants.USER);
		model.addAttribute(ARSConstants.USEROBJ, user);
		try {
			model.addAttribute(ARSConstants.BOOKINGS, airlineService
					.viewBookings(user.getUsername(), ARSConstants.BYUSER));
		} catch (RuntimeException e) {
			model.addAttribute(ARSConstants.MESSAGE,
					ARSConstants.ERROR + e.getMessage());
		}
		return ARSConstants.USERPROFILE;
	}

	/**
	 * @description It cancels the booking of flight
	 * @param bookingId
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = ARSConstants.URLCANCELBOOKING, method = RequestMethod.GET)
	public String cancelBooking(
			@RequestParam(ARSConstants.BOOKINGID) int bookingId, Model model,
			HttpSession session) {
		User user = (User) session.getAttribute(ARSConstants.USER);
		model.addAttribute(ARSConstants.USEROBJ, user);

		try {
			BookingInformation booking = airlineService
					.bookingCancel(bookingId);
			model.addAttribute(ARSConstants.BOOKINGS, airlineService
					.viewBookings(user.getUsername(), ARSConstants.BYUSER));
			model.addAttribute(ARSConstants.MESSAGE, ARSConstants.TICKETCANCEL
					+ booking.getBookingId());
		} catch (RuntimeException e) {
			model.addAttribute(ARSConstants.MESSAGE,
					ARSConstants.ERROR + e.getMessage());
		}
		return ARSConstants.USERPROFILE;
	}

	/**
	 * @description It gives the booking details of flight
	 * @param bookingId
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = ARSConstants.URLVIEWBOOKING, method = RequestMethod.GET)
	public String viewBooking(
			@RequestParam(ARSConstants.BOOKINGID) String bookingId,
			Model model, HttpSession session) {
		try {
			model.addAttribute(ARSConstants.BOOKING, airlineService
					.viewBookings(bookingId, ARSConstants.BYBOOKINGID).get(0));
		} catch (RuntimeException e) {
			model.addAttribute(ARSConstants.MESSAGE,
					ARSConstants.ERROR + e.getMessage());
		}
		return ARSConstants.BOOKINGDETAILS;
	}

	/**
	 * @description it adds user object to model
	 * @param model
	 * @return
	 */
	@RequestMapping(value = ARSConstants.URLSHOWFORGOTPWD)
	public String showForgotPassword(Model model) {
		model.addAttribute(ARSConstants.USEROBJ, new User());
		return ARSConstants.FORGOTPWD;
	}

	/**
	 * @description It updates the password of the user and return to login page
	 *              if successful or else go to same page and shows error
	 * @param user
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = ARSConstants.URLFORGOTPWD, method = RequestMethod.POST)
	public String forgotPassword(
			@ModelAttribute(ARSConstants.USEROBJ) User user, Model model,
			HttpSession session) {
		String returnPage;
		try {
			user = airlineService.forgotPassword(user);
			model.addAttribute(ARSConstants.MESSAGE, ARSConstants.PWDCHANGED);
			model.addAttribute(ARSConstants.USER, new User());
			returnPage = ARSConstants.LOGIN;
		} catch (RuntimeException e) {
			model.addAttribute(ARSConstants.MESSAGE,
					ARSConstants.ERROR + e.getMessage());
			model.addAttribute(ARSConstants.USEROBJ, new User());
			returnPage = ARSConstants.FORGOTPWD;
		}
		return returnPage;
	}
}
