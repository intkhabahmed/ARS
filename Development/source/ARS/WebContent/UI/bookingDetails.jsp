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
<title>Booking Details | MyAirlines</title>
</head>
<body>
  <jsp:include page="header.jsp" />
  <!--Navigation bar-->
  <div class="container" id="bookingSuccessContainer">
    <div class="row">
      <hr>
        <div id="flight">
          <div class="panel panel-primary">
            <div class="panel-heading">
              <h2 class="text-center">Booking Details</h2>
            </div>
            
            	<div class="panel-body">
            		<c:if test="${message!=''}">
              			<h3 class="text-center">${message}</h3>
              		</c:if>
              		
              		<table class="table">
			    		<tr>
			    			<th>Booking Id</th>
			    			<th>User Email</th>
			    			<th>No of Passengers</th>
			    			<th>Class Type</th>
			    			<th>Total Fare</th>
			    			<th>Source City</th>
			    			<th>Destination City</th>
			    			<th>Flight No</th>
			    			<th>Booking Date</th>
			    			<th>Travel Date</th>
			    		</tr>
			    		<tr>
			    			<td>${booking.bookingId}</td>
			    			<td>${booking.userEmail}</td>
			    			<td>${booking.noOfPassengers}</td>
			    			<td>${booking.classType}</td>
			    			<td><i class="fa fa-rupee"></i> ${booking.totalFare}</td>
			    			<td>${booking.srcCity}</td>
			    			<td>${booking.destCity}</td>
			    			<td>${booking.flightNo}</td>
			    			<td><fmt:formatDate pattern="dd/MMM/YYYY" value="${booking.bookingDate}"/></td>
			    			<td><fmt:formatDate pattern="dd/MMM/YYYY" value="${booking.travelDate}"/></td>
			    		</tr>
			    	</table>
            	</div>
            
          </div><!--End Panel-->
        </div>
        <!-- End FlightList -->
    </div><!--End Row-->
    <div class="row text-center">
    	<div class="col-sm-4 col-sm-offset-2">
    		<a href="cancelBooking.html?bookingId=${booking.bookingId}" class="btn btn-danger btn-lg">Cancel Booking</a>
    	</div>
    	<div class="col-sm-4">
    		<a href="showUserProfile.html" class="btn btn-danger btn-lg">Go Back to Profile</a>
    	</div>
    </div>
  </div><!--End Booking Container-->
  <jsp:include page="footer.jsp" />
</body>
</html>