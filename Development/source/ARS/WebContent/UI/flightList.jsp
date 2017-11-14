<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
	<%@include file="../css/bootstrap.min.css"%>
	<%@include file="../css/font-awesome.min.css"%>
	<%@include file="../css/custom.css"%>
</style>
<script>
	
<%@include file="../js/jquery-3.1.0.min.js" %>
	
<%@include file="../js/bootstrap.min.js" %>
	
</script>
<link
	href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css"
	rel="stylesheet">
<title>Search Results | MyAirlines</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<!--Navigation bar-->
	<div class="container" id="signupFormContainer">
		<div class="row">
			<div id="searchFormContainer">
				<hr>
				<form:form class="form-inline" action="retrieveListOfFlights.html"
					method="post" modelAttribute="booking">
					<div class="row">
						<div class="col-sm-2">
							<div class="form-group">
								<form:label path="srcCity">Source City</form:label>
								<form:select class="form-control" path="srcCity" required="required">
									<form:option value="">--Select--</form:option>
									<form:options items="${airport}"/>
								</form:select>
							</div>
						</div>
						<div class="col-sm-2">
							<div class="form-group">
								<form:label path="destCity">Destination City</form:label>
								<form:select class="form-control" path="destCity" required="required">
									<form:option value="">--Select--</form:option>
									<form:options items="${airport}"/>
								</form:select>
							</div>
						</div>
						<div class="col-sm-2">
							<div class="form-group">
								<form:label path="travelDate">Depart Date</form:label>
								<form:input type="date" class="form-control" path="travelDate" required="required" min="${date}"/>
							</div>
						</div>
						<div class="col-sm-2">
							<div class="form-group">
								<form:label path="noOfPassengers">No of Travellers</form:label>
								<form:input type="text" class="form-control"
												path="noOfPassengers" required="required"
												pattern="[1-9]{1}" title="min=1 & max=9"/>
							</div>
						</div>
						<div class="col-sm-2">
							<div class="form-group">
								<form:label path="classType">Class Type</form:label>
								<form:select path="classType" class="form-control" required="required">
									<form:option value="">--Select--</form:option>
									<form:options items="${classTypeOptions }" />
								</form:select>
							</div>
						</div>
						<div class="col-sm-2">
							<br> <input type="submit" value="Search"
								class="btn btn-danger" />
						</div>
					</div>
				</form:form>
			</div>
			<!--end searchFormContainer-->
			<hr>
			<c:if test="${flights.isEmpty() eq false}">
				<div id="flightList">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<h1 class="text-center">Flights List</h1>
						</div>
						<div class="panel-body">
							<table class="table">
								<tr>
									<th></th>
									<th>Airlines</th>
									<th>Departure</th>
									<th>Duration</th>
									<th>Arrival</th>
									<th>Price</th>
									<th></th>
								</tr>
								<c:forEach items="${flights}" var="flight">
									<tr>
										<td><i class="fa fa-plane fa-2x"></i></td>
										<td>${flight.flightNo}<br>${flight.flightName}</td>
										<td>${flight.deptCity}<br>${flight.deptTime}</td>
										<td>${flight.duration}</td>
										<td>${flight.arrCity}<br>${flight.arrTime}</td>
										<c:if test="${booking.classType eq 'First' }">
											<td><i class="fa fa-rupee"></i> ${flight.firstSeatsFare}</td>
										</c:if>
										<c:if test="${booking.classType eq 'Business' }">
											<td><i class="fa fa-rupee"></i> ${flight.bussSeatsFare}</td>
										</c:if>
										<td class="text-center">
											<c:if test="${sessionScope.user.username eq null}">
												<c:set var="action" value="showLoginAfterSearch.html"></c:set>
											</c:if>
											<c:if test="${sessionScope.user.username ne null}">
												<c:set var="action" value="showBooking.html"></c:set>
											</c:if>
											<form:form action="${action}" method="post" modelAttribute="booking">
												<input type="hidden" name="flightNo" value="${flight.flightNo}"/>
												<form:hidden path="noOfPassengers" />
												<form:hidden path="classType"/>
												<form:hidden path="srcCity"/>
												<form:hidden path="destCity"/>
												<form:hidden path="travelDate"/>
												<input type="submit" value="Book" class="btn btn-danger"/>
											</form:form>
										</td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</div>
				</div>
				<!-- End FlightList -->
			</c:if>
			<c:if test="${flights.isEmpty() eq true}">
				<h3 class="jumbotron text-center"><i class="fa fa-exclamation-triangle"></i> Sorry! No Flights Found for the given query, Try again</h3>
			</c:if>
		</div>
		<!--Form container end-->
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>