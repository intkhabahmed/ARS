package com.cg.ars.controller;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.NoResultException;
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
import com.cg.ars.exception.AirlineException;
import com.cg.ars.service.AirlineService;
import com.cg.ars.utility.ARSConstants;
import com.cg.ars.utility.MyUtil;

/**
 * @author inahmed
 *
 */
@Controller
@SessionAttributes(ARSConstants.USER)
public class AirlineController {
	/**
	 * injecting the airlineService class object
	 */
	@Autowired
	AirlineService airlineService;

	/**
	 *@description - Retrieves list of flights from database
	 *@param bookingInformation - Booking Information details are collected from form and passed as parameter
	 *@param model - Model
	 */
	@RequestMapping(value = ARSConstants.URLRETRIEVELISTOFFLIGHTS, method = RequestMethod.POST)
	public String retrieveFlights(
			@ModelAttribute(ARSConstants.BOOKING) BookingInformation bookingInformation,
			Model model) {
		try {
			String str = bookingInformation.getSrcCity() + "="
					+ bookingInformation.getDestCity() + "="
					+ bookingInformation.getTravelDate();
			model.addAttribute(ARSConstants.FLIGHTS,
					airlineService.retrieveFlights(str, ARSConstants.BYROUTE));
			model.addAttribute(ARSConstants.BOOKING, bookingInformation);
			model.addAttribute(ARSConstants.CLASSTYPEOPTION, new String[] {
					ARSConstants.FIRST, ARSConstants.BUSINESS });
			model.addAttribute(ARSConstants.AIRPORTS,
					airlineService.getAirportDetails());
			model.addAttribute(ARSConstants.DATE, Date.valueOf(LocalDate.now()));
			if (bookingInformation.getSrcCity().equals(
					bookingInformation.getDestCity())) {
				throw new AirlineException(
						ARSConstants.SOURCEDESTINATIONCANNOTSAME);
			}
		} catch (AirlineException airlineException) {
			model.addAttribute(ARSConstants.MESSAGE, ARSConstants.ERROR
					+ airlineException.getMessage());
			return ARSConstants.INDEX;
		} catch (RuntimeException runtimeException) {
			model.addAttribute(ARSConstants.MESSAGE,
					runtimeException.getMessage());
			return ARSConstants.ERRORPAGE;
		}
		return ARSConstants.FLIGHTLIST;
	}

	/**
	 * @description - Shows home page 
	 * @param model - Model
	 * @param session - HttpSession
	 */
	@RequestMapping(value = ARSConstants.URLINDEX)
	public String showHomePage(Model model, HttpSession session) {
		try {
			if (null == session.getAttribute(ARSConstants.USER)) {
				model.addAttribute(ARSConstants.USER, new User());
			} else {
				model.addAttribute(ARSConstants.USER,
						session.getAttribute(ARSConstants.USER));
			}
			model.addAttribute(ARSConstants.BOOKING, new BookingInformation());
			model.addAttribute(ARSConstants.CLASSTYPEOPTION, new String[] {
					ARSConstants.FIRST, ARSConstants.BUSINESS });

			model.addAttribute(ARSConstants.AIRPORTS,
					airlineService.getAirportDetails());
			model.addAttribute(ARSConstants.DATE, Date.valueOf(LocalDate.now()));
		} catch (RuntimeException runtimeException) {
			model.addAttribute(ARSConstants.MESSAGE,
					runtimeException.getMessage());
			return ARSConstants.ERRORPAGE;
		}
		return ARSConstants.INDEX;
	}

	/**
	 * @description - Shows login Page
	 * @param model - Model
	 */
	@RequestMapping(value = ARSConstants.URLSHOWLOGIN)
	public String showLoginPage(Model model) {
		model.addAttribute(ARSConstants.BOOKING, new BookingInformation());
		model.addAttribute(ARSConstants.USER, new User());
		return ARSConstants.LOGIN;
	}

	/**
	 * @description Shows the login page after searching a flight 
	 * @param model - Model
	 * @param bookingInformation - Booking Information details are collected from form and passed as parameter
	 */
	@RequestMapping(value = ARSConstants.URLSHOWLOGINAFTERSEARCH, method = RequestMethod.POST)
	public String showLoginPageAfterSearch(
			Model model,
			@ModelAttribute(ARSConstants.BOOKING) BookingInformation bookingInformation) {
		model.addAttribute(ARSConstants.USER, new User());
		model.addAttribute(ARSConstants.BOOKING, bookingInformation);
		return ARSConstants.LOGIN;
	}

	/**
	 * @description - shows signUp page
	 * @param model - Model
	 */
	@RequestMapping(value = ARSConstants.URLSHOWSIGNUP)
	public String showSignupPage(Model model) {
		model.addAttribute(ARSConstants.USEROBJ, new User());
		return ARSConstants.SIGNUP;
	}

	/**
	 * @description - Adds a new user to the database
	 * @param model - Model
	 * @param user - User object from form is passed
	 */
	@RequestMapping(value = ARSConstants.URLSIGNUP, method = RequestMethod.POST)
	public String addUser(Model model,
			@Valid @ModelAttribute(ARSConstants.USEROBJ) User user,
			BindingResult bindingResult, HttpSession session) {
		if (bindingResult.hasErrors()) {
			model.addAttribute(ARSConstants.USER, user);
			return ARSConstants.SIGNUP;
		}
		try {
			user.setRole(ARSConstants.CUSTOMER);
			if (!airlineService.checkAvailabiltiy(user.getUsername(),
					ARSConstants.BYUSERNAME)) {
				throw new AirlineException(ARSConstants.USERNAMETAKEN);
			}
			if (!airlineService.checkAvailabiltiy(user.getEmail(),
					ARSConstants.BYEMAIL)) {
				throw new AirlineException(ARSConstants.EMAILTAKEN);
			}
			airlineService.addUser(user);
			model.addAttribute(ARSConstants.MESSAGE, ARSConstants.SIGNUPSUCCESS);
			model.addAttribute(ARSConstants.USER, new User());
			if (null != session.getAttribute(ARSConstants.BOOKINGINFO)) {
				model.addAttribute(ARSConstants.BOOKING,
						session.getAttribute(ARSConstants.BOOKINGINFO));
			} else {
				model.addAttribute(ARSConstants.BOOKING,
						new BookingInformation());
			}
			return ARSConstants.LOGIN;
		} catch (AirlineException airlineException) {
			model.addAttribute(ARSConstants.MESSAGE,
					airlineException.getMessage());
			model.addAttribute(ARSConstants.USEROBJ, user);
			return ARSConstants.SIGNUP;
		} catch (RuntimeException runtimeException) {
			model.addAttribute(ARSConstants.MESSAGE,
					runtimeException.getMessage());
			return ARSConstants.ERRORPAGE;
		}
	}

	/**
	 * @description -Terminates the session 
	 * @param model - Model
	 * @param session - HttpSession
	 */
	@RequestMapping(value = ARSConstants.URLLOGOUT)
	public String logout(Model model, SessionStatus status, HttpSession session) {
		status.setComplete();
		showHomePage(model, session);
		return ARSConstants.INDEX;
	}

	/**
	 * @description - Validates the login details after and before searching the
	 *              flight
	 * @param user - User details for whom validation is to be carried out
	 * @param model - Model
	 * @param session - HttpSession
	 */
	@RequestMapping(value = ARSConstants.URLLOGIN)
	public String validateLogin(@ModelAttribute(ARSConstants.USER) User user,
			Model model, HttpSession session) {
		String returnPage = "";
		BookingInformation bookingInformation = (BookingInformation) session
				.getAttribute(ARSConstants.BOOKINGINFO);
		try {
			user = airlineService.validateLogin(user);
			if (null != user) {
				session.setAttribute(ARSConstants.USER, user);
				session.removeAttribute(ARSConstants.BOOKINGINFO);
				if (null != bookingInformation.getFlightNo()) {
					return bookFlight(bookingInformation, model, session);
				}
				return showHomePage(model, session);
			}

		} catch (NoResultException runtimeException) {
			model.addAttribute(ARSConstants.MESSAGE,
					ARSConstants.INVALIDUSERNAMEPWD);
			model.addAttribute(ARSConstants.USER, new User());
			returnPage = ARSConstants.LOGIN;
		} catch (RuntimeException runtimeException) {
			model.addAttribute(ARSConstants.MESSAGE,
					runtimeException.getMessage());
			return ARSConstants.ERRORPAGE;
		}
		model.addAttribute(ARSConstants.BOOKING, bookingInformation);
		return returnPage;
	}

	/**
	 * @description - Books the flight 
	 * @param bookingInformation - Booking Information details are collected from form and passed as parameter
	 * @param model - Model
	 * @param session - HttpSession
	 */
	@RequestMapping(value = ARSConstants.URLSHOWBOOKING, method = RequestMethod.POST)
	public String bookFlight(
			@ModelAttribute(ARSConstants.BOOKING) BookingInformation bookingInformation,
			Model model, HttpSession session) {
		User user = (User) session.getAttribute(ARSConstants.USER);
		try {
			Flight flight = airlineService.retrieveFlights(
					bookingInformation.getFlightNo(), ARSConstants.FLIGHTNO)
					.get(0);
			if (ARSConstants.FIRST.equalsIgnoreCase(bookingInformation
					.getClassType())) {
				bookingInformation.setTotalFare(MyUtil.calculatefare(
						bookingInformation.getNoOfPassengers(),
						flight.getFirstSeatsFare()));
			} else if (ARSConstants.BUSINESS
					.equalsIgnoreCase(bookingInformation.getClassType())) {
				bookingInformation.setTotalFare(MyUtil.calculatefare(
						bookingInformation.getNoOfPassengers(),
						flight.getBussSeatsFare()));
			}
			bookingInformation.setUserEmail(user.getEmail());
			bookingInformation.setBookingDate(Date.valueOf(LocalDate.now()));
			model.addAttribute(ARSConstants.FLIGHT, flight);
			model.addAttribute(ARSConstants.BOOKING, bookingInformation);
			model.addAttribute(ARSConstants.USER, user);
			airlineService.checkFlightOccupancyDetails(flight.getFlightNo(), bookingInformation.getClassType(),bookingInformation.getNoOfPassengers());
		} catch (AirlineException airlineException) {
			model.addAttribute(ARSConstants.MESSAGE,
					airlineException.getMessage());
			model.addAttribute(ARSConstants.BOOKING, bookingInformation);
			return retrieveFlights(bookingInformation,model);
		} catch (RuntimeException runtimeException) {
			model.addAttribute(ARSConstants.MESSAGE,
					runtimeException.getMessage());
			return ARSConstants.ERRORPAGE;
		}
		return ARSConstants.BOOKING;
	}

	/**
	 * @description - confirms the booking for given booking Object
	 * @param bookingInformation - Booking Information details are collected from form and passed as parameter
	 * @param model - Model
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
			return ARSConstants.BOOKINGSUCCESS;
		} catch (RuntimeException runtimeException) {
			model.addAttribute(ARSConstants.MESSAGE,
					runtimeException.getMessage());
			return ARSConstants.ERRORPAGE;
		}
	}

	/**
	 * @description - Updates the user details
	 * @param user - User details for whom updation is to be carried out
	 * @param model - Model
	 */
	@RequestMapping(value = ARSConstants.URLUPDATEUSER, method = RequestMethod.POST)
	public String updateUser(
			@ModelAttribute(ARSConstants.USEROBJ) @Valid User user,
			BindingResult bindingResult, Model model) {
		try {
			model.addAttribute(ARSConstants.BOOKINGS, airlineService
					.retrieveBookings(user.getUsername(), ARSConstants.BYROUTE));
			if (bindingResult.hasErrors()) {

				model.addAttribute(ARSConstants.USEROBJ, user);
				return ARSConstants.USERPROFILE;
			}
			user.setRole(ARSConstants.CUSTOMER);
			airlineService.updateUser(user);
			model.addAttribute(ARSConstants.MESSAGE,
					"Information updated successfully");
			model.addAttribute(ARSConstants.USEROBJ, user);
			return ARSConstants.USERPROFILE;
		} catch (RuntimeException runtimeException) {
			model.addAttribute(ARSConstants.MESSAGE, ARSConstants.ERROR
					+ runtimeException.getMessage());
			return ARSConstants.ERRORPAGE;
		}

	}

	/**
	 * @description - Shows user profile
	 * @param model - Model
	 * @param session - HttpSession
	 */
	@RequestMapping(value = ARSConstants.URLSHOWPROFILE)
	public String showUserProfile(Model model, HttpSession session) {
		User user = (User) session.getAttribute(ARSConstants.USER);
		model.addAttribute(ARSConstants.USEROBJ, user);
		try {
			model.addAttribute(ARSConstants.BOOKINGS, airlineService
					.retrieveBookings(user.getUsername(), ARSConstants.BYROUTE));
			return ARSConstants.USERPROFILE;
		} catch (RuntimeException runtimeException) {
			model.addAttribute(ARSConstants.MESSAGE,
					runtimeException.getMessage());
			return ARSConstants.ERRORPAGE;
		}
	}

	/**
	 * @description - Cancels the booking details for given booking Id
	 * @param bookingId - booking Id for which booking is cancelled
	 * @param model - Model
	 * @param session - HttpSession
	 */
	@RequestMapping(value = ARSConstants.URLCANCELBOOKING, method = RequestMethod.GET)
	public String cancelBooking(
			@RequestParam(ARSConstants.BOOKINGID) int bookingId, Model model,
			HttpSession session) {
		User user = (User) session.getAttribute(ARSConstants.USER);
		model.addAttribute(ARSConstants.USEROBJ, user);

		try {
			BookingInformation booking = airlineService
					.cancelBooking(bookingId);
			model.addAttribute(ARSConstants.BOOKINGS, airlineService
					.retrieveBookings(user.getUsername(), ARSConstants.BYROUTE));
			model.addAttribute(ARSConstants.MESSAGE, ARSConstants.TICKETCANCEL
					+ booking.getBookingId());
			return ARSConstants.USERPROFILE;
		} catch (RuntimeException runtimeException) {
			model.addAttribute(ARSConstants.MESSAGE,
					runtimeException.getMessage());
			return ARSConstants.ERRORPAGE;
		}
	}

	/**
	 * @description - Shows the booking details according to given booking Id
	 * @param bookingId - bookingId for which shows the booking Details
	 * @param model - Model
	 * @param session - HttpSession
	 */
	@RequestMapping(value = ARSConstants.URLVIEWBOOKING, method = RequestMethod.GET)
	public String viewBooking(
			@RequestParam(ARSConstants.BOOKINGID) String bookingId,
			Model model, HttpSession session) {
		try {
			model.addAttribute(
					ARSConstants.BOOKING,
					airlineService.retrieveBookings(bookingId,
							ARSConstants.BYBOOKINGID).get(0));
			return ARSConstants.BOOKINGDETAILS;
		} catch (RuntimeException runtimeException) {
			model.addAttribute(ARSConstants.MESSAGE,
					runtimeException.getMessage());
			return ARSConstants.ERRORPAGE;
		}
	}

	/**
	 * @description - Allows a user to change ones password
	 * @param model - Model
	 */
	@RequestMapping(value = ARSConstants.URLSHOWFORGOTPWD)
	public String showForgotPassword(Model model) {
		model.addAttribute(ARSConstants.USEROBJ, new User());
		return ARSConstants.FORGOTPWD;
	}

	/**
	 * @description Changes the password of the user
	 * @param user - User details for whom password has to be changed
	 * @param model - Model
	 * @param session - HttpSession
	 */
	@RequestMapping(value = ARSConstants.URLFORGOTPWD, method = RequestMethod.POST)
	public String changePassword(
			@ModelAttribute(ARSConstants.USEROBJ) User user, Model model,
			HttpSession session) {
		String returnPage;
		try {
			user = airlineService.changePassword(user);
			model.addAttribute(ARSConstants.MESSAGE, ARSConstants.PWDCHANGED);
			model.addAttribute(ARSConstants.USER, new User());
			if (null != session.getAttribute(ARSConstants.BOOKINGINFO)) {
				model.addAttribute(ARSConstants.BOOKING,
						session.getAttribute(ARSConstants.BOOKINGINFO));
			} else {
				model.addAttribute(ARSConstants.BOOKING,
						new BookingInformation());
			}
			returnPage = ARSConstants.LOGIN;
		} catch (AirlineException airlineException) {
			model.addAttribute(ARSConstants.MESSAGE, ARSConstants.ERROR
					+ airlineException.getMessage());
			model.addAttribute(ARSConstants.USEROBJ, new User());
			returnPage = ARSConstants.FORGOTPWD;
		} catch (RuntimeException runtimeException) {
			model.addAttribute(ARSConstants.MESSAGE,
					runtimeException.getMessage());
			returnPage = ARSConstants.ERRORPAGE;
		}
		return returnPage;
	}
}
