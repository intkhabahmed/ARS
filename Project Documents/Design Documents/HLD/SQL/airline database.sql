--drop constraints

ALTER TABLE Users DROP CONSTRAINT pk_user_userid CASCADE
/
ALTER TABLE Users DROP CONSTRAINT nn_user_username CASCADE
/
ALTER TABLE Users DROP CONSTRAINT nn_user_email CASCADE
/
ALTER TABLE Users DROP CONSTRAINT nn_user_password CASCADE
/
ALTER TABLE Users DROP CONSTRAINT nn_user_role CASCADE
/
ALTER TABLE Airport DROP CONSTRAINT pk_airport_airportid CASCADE
/
ALTER TABLE Airport DROP CONSTRAINT nn_airport_airportname CASCADE
/
ALTER TABLE Airport DROP CONSTRAINT nn_airport_abbreviation CASCADE
/
ALTER TABLE Airport DROP CONSTRAINT pk_airport_location CASCADE
/
ALTER TABLE Flight DROP CONSTRAINT pk_flightinfo_flightno CASCADE
/
ALTER TABLE BookingInformation  DROP CONSTRAINT pk_bookinginfo_bookingid CASCADE
/
ALTER TABLE BookingInformation  DROP CONSTRAINT nn_bookinginfo_useremail CASCADE
/
ALTER TABLE BookingInformation  DROP CONSTRAINT fk_bookinginfo_flightno CASCADE
/

--Drop Tables

DROP TABLE Users
/
DROP TABLE Airport
/
DROP TABLE BookingInformation
/
DROP TABLE Flight
/
--Drop Sequences

Drop sequence userid_sequence
/
Drop sequence ticketbooking_id_seq
/


--Create tables

Create table Users( 
Userid Number(11) constraint pk_user_userid Primary Key,
Username Varchar2(20) constraint nn_user_username Not Null,
User_email Varchar2(40) constraint nn_user_email Not Null, 
Password Varchar2(20) constraint nn_user_password Not Null, 
Role Varchar2(10) constraint nn_user_role Not Null,
Mobile_no Varchar2(10)
)
/


Create table  Airport(
AirportId Number(11) constraint pk_airport_airportid Primary Key,
AirportName Varchar2(50) constraint nn_airport_airportname Not Null, 
Abbreviation Varchar2(6) constraint nn_airport_abbreviation Not Null,
Location Varchar2(40) constraint pk_airport_location Not Null
)
/

Create table Flight(
Flightno Varchar2(10) constraint pk_flightinfo_flightno primary key, 
Airline Varchar2(20), 
Dep_city Varchar2(10), 
Arr_city Varchar2(10), 
Dep_date Date, 
Arr_date Date, 
Dep_time Varchar2(10), 
Arr_time Varchar2(10), 
FirstSeats Number(10), 
FirstSeatFare Number(10,2), 
BussSeats Number, 
BussSeatsFare Number(10,2),
duration Varchar2(30)
)
/

Create table BookingInformation ( 
Booking_id Number(6) constraint pk_bookinginfo_bookingid primary key, 
User_email Varchar2(40) constraint nn_bookinginfo_useremail not null, 
No_of_passengers Number, 
Class_type Varchar2(10),
Total_fare Number(10,2),
CreditCard_info Varchar2(10), 
Src_city Varchar2(10), 
Dest_city Varchar2(10),
FlightNo varchar2(10) constraint fk_bookinginfo_flightno references Flight(Flightno),
Booking_date Date,
Travel_date Date
)
/
--Create Sequence

create sequence userid_sequence start with 3
/
create sequence ticketbooking_id_seq start with 1
/



--Insert Data to Create database

insert into flight
values('9W-617','JET AIRWAYS','PNQ','BOM',to_date('2017-11-25','yyyy-mm-dd'),to_date('2017-11-25','yyyy-mm-dd'),'17:35','18:20',30,4000,40,10000,'45 min')
/

insert into flight
values('G8-170','GO AIR','PNQ', 'BOM' ,to_date('2017-11-22','yyyy-mm-dd'),to_date('2017-11-22','yyyy-mm-dd'),'12:10','01:30',20,6000,30,11000,'1 hr 20 min')
/

insert into flight 
values('AI-617','AIR INDIA','BOM','PNQ',to_date('2017-11-23','yyyy-mm-dd'),to_date('2017-11-23','yyyy-mm-dd'),'13:55','14:05',30,4500,40,9000,'10 min')
/

insert into flight 
values('SG-221','SPICE JET','BOM','PNQ',to_date('2017-11-30','yyyy-mm-dd'),to_date('2017-11-30','yyyy-mm-dd'),'01:20','01:35',20,7000,10,12000,'15 min')
/

insert into Flight 
values('SG-981','SPICE JET','MAA','PNQ',to_date('2017-12-01','yyyy-mm-dd'),to_date('2017-11-02','yyyy-mm-dd'),'23:50','1:20',30,3879,40,12700,'1 hr 30 min')
/

insert into Flight 
values('AI-40','AIR INDIA','MAA','PNQ',to_date('2017-11-22','yyyy-mm-dd'),to_date('2017-11-22','yyyy-mm-dd'),'09:50','11:25',30,4567,40,5432,'1 hr 35 min')
/

insert into Flight 
values('6E-6406','INDIGO','PNQ','MAA',to_date('2017-12-22','yyyy-mm-dd'),to_date('2017-12-22','yyyy-mm-dd'),'00:40','02:25',30,4567,40,5432,'2 hr 45 min')
/

insert into Flight 
values('SG-423','SPICEJET','PNQ','MAA',to_date('2017-12-05','yyyy-mm-dd'),to_date('2017-12-06','yyyy-mm-dd'),'23:15','00:55',23,2300,46,5600,'1 hr 40 min')
/

insert into Flight 
values('G8-176','GO AIR','PNQ','DEL',to_date('2017-12-15','yyyy-mm-dd'),to_date('2017-12-15','yyyy-mm-dd'),'01:20','03:30',30,4567,40,5432,'2 hr 10 min')
/

insert into Flight 
values('UK-990','VISTARA','PNQ','DEL',to_date('2017-12-18','yyyy-mm-dd'),to_date('2017-12-18','yyyy-mm-dd'),'05:15','07:10',30,4567,40,5432,'1 hr 45 min')
/

insert into Flight 
values('I5-731','AIR ASIA','DEL','PNQ',to_date('2017-12-20','yyyy-mm-dd'),to_date('2017-12-20','yyyy-mm-dd'),'15:20','17:20',30,4567,40,5432,'2 hr')
/

insert into Flight 
values('6E-105','INDIGO','DEL','PNQ',to_date('2017-12-22','yyyy-mm-dd'),to_date('2017-12-22','yyyy-mm-dd'),'14:40','16:50',30,6700,40,15000,'2 hr 10 min')
/

insert into Flight 
values('UK-985','VISTARA','CCU','PNQ',to_date('2017-12-24','yyyy-mm-dd'),to_date('2017-12-24','yyyy-mm-dd'),'02:00','04:40',30,4567,40,5432,'2 hr 40 min')
/


insert into Flight 
values('G8-394','GO-AIR','CCU','PNQ',to_date('2017-12-26','yyyy-mm-dd'),to_date('2017-12-27','yyyy-mm-dd'),'21:55','00:50',30,4567,40,5432,'2 hr 55 min')
/


insert into Flight 
values('G8-393','GO AIR','PNQ','CCU',to_date('2017-12-28','yyyy-mm-dd'),to_date('2017-12-29','yyyy-mm-dd'),'22:30','01:00',30,4567,40,5432,'2 hr 30 min')
/

insert into Flight 
values('6E-135','INDIGO','PNQ','CCU',to_date('2017-12-30','yyyy-mm-dd'),to_date('2017-12-30','yyyy-mm-dd'),'14:30','17:20',30,4567,40,5432,'2 hr 50 min')
/

insert into Flight 
values('SG-519','SPICE JET','PNQ','BLR',to_date('2017-12-31','yyyy-mm-dd'),to_date('2017-12-31','yyyy-mm-dd'),'05:55','07:20',30,4567,40,5432,'1 hr 25 min')
/


insert into Flight 
values('I5-1424','AIR ASIA','BLR','PNQ',to_date('2017-12-21','yyyy-mm-dd'),to_date('2017-12-21','yyyy-mm-dd'),'00:25','02:00',30,4567,40,5432,'1 hr 35 min')
/


insert into Flight 
values('G8-173','GO AIR','DEL','BOM',to_date('2017-12-28','yyyy-mm-dd'),to_date('2017-12-28','yyyy-mm-dd'),'08:50','11:00',30,4567,40,5432,'2 hr 10 min')
/


insert into Flight 
values('AI-635','AIR INDIA','BOM','DEL',to_date('2017-11-28','yyyy-mm-dd'),to_date('2017-11-28','yyyy-mm-dd'),'07:05','10:35',30,4567,40,5432,'3 hr 30 min')
/


insert into Flight 
values('SG-105','SPICE JET','DEL','MAA',to_date('2017-12-06','yyyy-mm-dd'),to_date('2017-12-06','yyyy-mm-dd'),'20:40','23:20',30,4567,40,5432,'2 hr 40 min')
/


insert into Flight 
values('AI-43','AIR INDIA','MAA','DEL',to_date('2017-12-05','yyyy-mm-dd'),to_date('2017-12-05','yyyy-mm-dd'),'21:10','23:55',30,4567,40,5432,'2 hr 5 min')
/


insert into Flight 
values('I5-546','AIR ASIA','DEL','CCU',to_date('2017-12-03','yyyy-mm-dd'),to_date('2017-12-03','yyyy-mm-dd'),'19:35','22:50',30,4567,40,5432,'3 hr 15 min')
/

insert into Flight 
values('UK-720','VISTARA','CCU','DEL',to_date('2017-11-26','yyyy-mm-dd'),to_date('2017-11-26','yyyy-mm-dd'),'07:10','09:25',30,4567,40,5432,'2 hr 15 min')
/

insert into Flight 
values('6E-4376','INDIGO','DEL','BLR',to_date('2017-12-06','yyyy-mm-dd'),to_date('2017-12-07','yyyy-mm-dd'),'23:45','02:25',30,4567,40,5432,'2 hr 40 min')
/


insert into Flight 
values('9W-816','JET AIRWAYS','BLR','DEL',to_date('2017-11-26','yyyy-mm-dd'),to_date('2017-11-26','yyyy-mm-dd'),'17:45','20:30',30,4567,40,5432,'2 hr 45 min')
/


insert into Flight 
values('AI-50','AIR INDIA','BOM','MAA',to_date('2017-12-12','yyyy-mm-dd'),to_date('2017-12-12','yyyy-mm-dd'),'20:50','22:50',30,4567,40,5432,'2 hr')
/


insert into Flight 
values('G8-302','GO AIR','MAA','BOM',to_date('2018-01-11','yyyy-mm-dd'),to_date('2018-01-11','yyyy-mm-dd'),'05:20','07:10',30,4567,40,5432,'1 hr 50 min')
/


insert into Flight 
values('SG-415','SPICE JET','BOM','BLR',to_date('2018-01-20','yyyy-mm-dd'),to_date('2018-01-20','yyyy-mm-dd'),'18:05','19:55',30,4567,40,5432,'1 hr 50 min')
/


insert into Flight 
values('6E-5449','INDIGO','BLR','BOM',to_date('2018-01-24','yyyy-mm-dd'),to_date('2018-01-24','yyyy-mm-dd'),'18:10','19:45',30,4567,40,5432,'1 hr 35 min')
/


insert into Flight 
values('9W-355','JET AIRWAYS','BOM','CCU',to_date('2017-11-26','yyyy-mm-dd'),to_date('2017-11-26','yyyy-mm-dd'),'20:05','22:45',30,4567,40,5432,'2 hr 40 min')
/

insert into Flight 
values('AI-765','AIR INDIA','CCU','BOM',to_date('2017-12-26','yyyy-mm-dd'),to_date('2017-12-27','yyyy-mm-dd'),'23:10','02:20',30,4567,40,5432,'3 hr 10 min')
/

insert into Flight 
values('SG-517','SPICE JET','BLR','CCU',to_date('2017-12-23','yyyy-mm-dd'),to_date('2017-12-23','yyyy-mm-dd'),'18:10','20:35',30,4567,40,5432,'2 hr 25 min')
/

insert into Flight 
values('I5-535','AIR ASIA','CCU','BLR',to_date('2017-12-14','yyyy-mm-dd'),to_date('2017-12-14','yyyy-mm-dd'),'08:10','11:55',30,4567,40,5432,'3 hr 45 min')
/

insert into Flight 
values('6E-389','INDIGO','BLR','MAA',to_date('2017-12-11','yyyy-mm-dd'),to_date('2017-12-11','yyyy-mm-dd'),'12:25','13:25',30,4567,40,5432,'1 hr')
/

insert into Flight 
values('SG-3309','SPICE JET','MAA','BLR',to_date('2017-12-08','yyyy-mm-dd'),to_date('2017-12-08','yyyy-mm-dd'),'16:55','17:50',30,4567,40,5432,'55 min')
/

insert into Flight 
values('AI-764','AIR INDIA','CCU','MAA',to_date('2017-12-04','yyyy-mm-dd'),to_date('2017-12-04','yyyy-mm-dd'),'14:45','17:05',30,4567,40,5432,'2 hr 20 min')
/

insert into Flight 
values('9W-2476','JET AIRWAYS','CCU','MAA',to_date('2018-01-02','yyyy-mm-dd'),to_date('2018-01-02','yyyy-mm-dd'),'21:50','23:05',30,4567,40,5432,'1 hr 15 min')
/

insert into Flight 
values('6E-797','INDIGO','MAA','CCU',to_date('2018-01-09','yyyy-mm-dd'),to_date('2018-01-09','yyyy-mm-dd'),'7:50','10:05',30,4567,40,5432,'2 hr 15 min')
/

insert into Flight 
values('SG-277','SPICE JET','MAA','CCU',to_date('2018-01-05','yyyy-mm-dd'),to_date('2018-01-05','yyyy-mm-dd'),'11:50','14:20',30,4567,40,5432,'2 hr 30 min')
/


insert into airport values(1,'Lohegaon Airport','PNQ','PUNE')
/
insert into airport values(2,'CHHATRAPATI SHIVAJI AIRPORT','BOM','MUMBAI')
/
insert into airport values(3,'CHENNAI INTERNATIONAL AIRPORT','MAA','CHENNAI')
/
insert into airport values(4,'INDRA GANDHI INTERNATIONAL AIRPORT','DEL','DELHI')
/
insert into airport values(5,'NETAJI SUBHAS CHANDRA AIRPORT','CCU','KOLKATA')
/
insert into airport values(6,'BENGALURU IINTERNATIONAL AIRPORT','BLR','BANGALORE')
/
