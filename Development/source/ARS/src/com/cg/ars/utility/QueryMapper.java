package com.cg.ars.utility;

public interface QueryMapper {
	public static String SEARCHFLIGHTBYARRIVALCITY = "SELECT f FROM Flight f WHERE f.arrCity=:arrCity";
	public static String SEARCHFLIGHTBYDEPARTUREDATE = "SELECT f FROM Flight f WHERE f.deptDate=:deptDate";
	public static String SEARCHFLIGHTBYDEPARTUREANDARRIVALCITY = "SELECT f FROM Flight f WHERE f.deptCity=:deptCity AND f.arrCity=:arrCity";
	public static String SEARCHFLIGHTBYFLIGHTNUMBER = "SELECT f FROM Flight f WHERE f.flightNo=:flightNo";
	public static String FLIGHTINFORMATION = "SELECT f FROM Flight f";
	public static String SEARCHFLIGHTBYARRIVALANDDEPARTURECITYANDDEPARTUREDATE = "SELECT f FROM Flight f where f.deptCity=:deptCity AND f.arrCity=:arrCity AND f.deptDate=:deptDate AND (f.firstSeats>0 OR f.bussSeats>0)";
	public static String BOOKINGINFORMATIONOFAFLIGHT = "SELECT b FROM BookingInformation b WHERE b.flightNo=:flightNo";
	public static String USERINFORMATION = "SELECT u FROM User u WHERE u.username=:username";
	public static String BOOKINGINFORMATIONBYEMAIL = "SELECT b FROM BookingInformation b WHERE b.userEmail=:email";
	public static String VALIDATEUSERNAMEANDPWD = "SELECT u FROM User u WHERE u.username=:user AND u.pwd=:pass";
	public static String FIRSTSEATSOFAFLIGHT = "SELECT f.firstSeats FROM Flight f where f.flightNo=:flightNo";
	public static String BUSINESSSEATSOFAFLIGHT = "SELECT f.bussSeats FROM Flight f where f.flightNo=:flightNo";
	public static String PASSENGERSINFIRSTCLASSOFAFLIGHT = "SELECT f.noOfPassengers FROM BookingInformation f where f.flightNo=:flightNo AND f.classType='first'";
	public static String PASSENGERSINBUSINESSCLASSOFAFLIGHT = "SELECT f.noOfPassengers FROM BookingInformation f where f.flightNo=:flightNo AND f.classType='business'";
	public static String FORGOTPWD = "Update User u Set u.pwd where u.username=:username";
	public static String CHECKUSERNAMEISAVAILABLE = "Select u.username from User u where u.username=:query";
	public static String CHECKEMAILISAVAILABLE = "Select u.email from User u where u.email=:query";
	public static String GETAIRPORTDETAILS = "Select a from Airport a";
	public static String GETABBREVIATION = "SELECT a.abbreviation FROM Airport a WHERE a.location=:location";
	public static String BOOKINGINFOBYBOOKINGID = "SELECT b FROM BookingInformation b WHERE b.bookingId=:bookingId";
	public static String VALIDATEUSERNAMEANDPASSWORD = "SELECT u FROM User u WHERE u.username=:user AND u.pwd=:pass";
}
