Airline Reservation System


Intorduction
	
This project is aimed at developing an Airline System for Online Ticket Booking. This is a web based application that can be accessed over the web. This system can be used by a user for searching flight, book those flights and cancel the booking. This is an integrated system that contains three user components, Admin component, Executive component and Customer component.

Login Module:
	Login module is common for all the users of the system ie. Admin, Customer and Executive Manager.
Basic functionality of module are as follows:
1. Taking login credentials from the user.
2. If given credentials are wrong, show appropriate message.
3. If login succeeds user will be navigated to their respective homepage as per the role.

Registration Module:
	If a visitor wants to book tickets then he/she will be asked to register.
Visitor will be asked to enter the following details:
User Name
Email Id
Password
Contact Number

Note:  If the username and email already exists in the database then the visitor will be asked to change the username. 
Search Flight Module:
Flights can be viewed by a visitor or a registered customer
If a user (who is not logged in) directly tries to book tickets then he/she will be redirected to the login page.
If a user (who is already logged in) tries to book tickets then by giving appropriate information tickets will be booked.   
Customer can book tickets or search different flights.
Booking Ticket Module:
The customer is shown the tickets details with the total amount. 
Confirm Ticket by making payment
10 digit Card number should be entered to make payment
Assumption: Confirm tickets facility is provided to the customer who wants to book ticket. 
Booking Cancel Module:
If a visitor wants to cancel his/her already booked tickets then he/she will have to click the cancel button of the respective booking in the given list of bookings.
Once he/she click on cancel the flight ticket will be cancelled.

Added functionalities
Welcome message along with user’s name is displayed after successful login on the Homepage.
Change password functionality is provided.
Option for user to register directly from Homepage.
User can see and update his/her personal details from the Profile section.