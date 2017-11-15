<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Airline Reservation System</h1>

	<!-- <a href="flight.htm"></a> -->


	<form:form action="" method="post" modelAttribute="booking">

		<table>
			<tr>
				<td><input type="submit" value="Login" /></td>
				<td><input type="submit" value="SignUp" /></td>
			</tr>

			<tr>
				<td>From</td>
				<td><form:select path="srcCity">
						<form:option value="select">Select</form:option>
						<form:option value="Pune">Pune(PNQ)</form:option>
						<form:option value="Mumbai">Mumbai(BOM)</form:option>
						<form:option value="Chennai">Chennai(MAA)</form:option>
						<form:option value="Kolkata">Kolkata(CCU)</form:option>
						<form:option value="Delhi">Delhi(DEL)</form:option>
						<form:option value="Bangalore">Bangalore(BLR)</form:option>
					</form:select></td>
			</tr>

			<tr>
				<td>To</td>
				<td><form:select path="destCity">
						<form:option value="select">Select</form:option>
						<form:option value="Pune">Pune(PNQ)</form:option>
						<form:option value="Mumbai">Mumbai(BOM)</form:option>
						<form:option value="Chennai">Chennai(MAA)</form:option>
						<form:option value="Kolkata">Kolkata(CCU)</form:option>
						<form:option value="Delhi">Delhi(DEL)</form:option>
						<form:option value="Bangalore">Bangalore(BLR)</form:option>
					</form:select></td>
			</tr>

			<tr>
				<td>Depart</td>
				<td><form:input path="bookingDate" type="date" /></td>
			</tr>

			<tr>
				<td>No. of Passengers</td>
				<td><form:input path="noOfPassengers" type="text"/></td>
			</tr>

			<tr>
				<td>Class Type</td>
				<td><form:input path="classType" type="text"/></td>
			</tr>


			<tr></tr>
			<tr>
				<td><input type="submit" value="Search" /></td>
			</tr>

		</table>




	</form:form>
</body>
</html>
