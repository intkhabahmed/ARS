Create table Users( 
userid number(11) primary key,
username varchar2(20) not null,
cust_email varchar2(40), 
password varchar2(20) not null, 
role varchar2(10) not null,
mobile_no number(10)
);

Create table  Airport(
AirportId number(11) primary key,
AirportName varchar2(50) not null, 
Abbreviation varchar2(6) not null,
Location varchar2(40) not null
);

Create table FlightInformation(
flightno varchar2(10) primary key, 
airline varchar2(20), 
dep_city varchar2(10), 
arr_city varchar2(10), 
dep_date date, 
arr_date date, 
dep_time varchar2(10), 
arr_time varchar2(10), 
FirstSeats number(10), 
FirstSeatFare number(10,2), 
BussSeats number, 
BussSeatsFare number(10,2)
);



Create table BookingInformation ( 
Booking_id varchar2(10) primary key, 
cust_email varchar2(40) not null, 
no_of_passengers number, 
class_type varchar2(10),
total_fare number(10,2),
CreditCard_info varchar2(10), 
src_city varchar2(10), 
dest_city varchar2(10),
flightNo varchar2(10) references flightInformation(flightno),
booking_date date
);