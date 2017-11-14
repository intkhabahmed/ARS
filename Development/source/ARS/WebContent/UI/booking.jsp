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
  <div class="container" id="bookingContainer">
    <div class="row">
      <hr>
        <div id="flight">
          <div class="panel panel-primary">
            <div class="panel-heading">
              <h2 class="text-center">Your Flight Details</h2>
            </div>
            <div class="panel-body">
              <table class="table">
              	<tr>
					<th></th>
					<th>Airlines</th>
					<th>Departure</th>
					<th>Duration</th>
					<th>Arrival</th>
				</tr>
                  <tr>
                    <td><i class="fa fa-plane fa-2x"></i></td>
                    <td>${flight.flightNo}<br>${flight.flightName}</td>
                    <td>${flight.deptCity}<br>${flight.deptTime}</td>
                    <td>${flight.duration}</td>
                    <td>${flight.arrCity}<br>${flight.arrTime}</td>
                  </tr>
              </table>
            </div>
          </div><!--End Panel-->
        </div>
        <!-- End FlightList -->
    </div><!--End Row-->
    <div class="row">
        <div class="col-sm-5">
          <div class="panel panel-primary padding-10x" id="paymentForm">
            <div class="panel-heading">
              <h3 class="text-center panel-title">Credit/Debit Card Details</h3>
            </div>
            <div class="panel-body">
              <form:form class="form-horizontal" method="post" action="confirmBooking.html" modelAttribute="booking">
                <br>
                <div class="form-group">
                  <form:label path="creditCardInfo" class="form-label">Card No:</form:label>
                  <i class="fa fa-credit-card pull-right fa-2x"></i>
                  <form:input type="text" path="creditCardInfo" required="required" class="form-control" 
                  	pattern="^[1-9][0-9]{9}$" title="Card number should be of 10 digits"/>
                  <form:hidden path="userEmail"/>
                  <form:hidden path="noOfPassengers"/>
                  <form:hidden path="classType"/>
                  <form:hidden path="totalFare"/>
                  <form:hidden path="srcCity"/>
                  <form:hidden path="destCity"/>
                  <form:hidden path="flightNo"/>
                  <form:hidden path="bookingDate"/>
                  <form:hidden path="travelDate"/>
                </div>
                <div class="text-center">
                  <input type="submit" value="Pay" class="btn btn-danger btn-lg">
                </div>
              </form:form>
            </div>
          </div>
        </div>
        <div class="col-sm-4 col-sm-offset-3">
          <div class="panel panel-primary padding-10x" id="fareSummary">
            <div class="panel-heading">
              <h3 class="text-center panel-title">Fare Summary</h3>
            </div>
            <div class="panel-body">
              <table class="table">
                <tr>
                  <th>No of Passengers:</th>
                  <td>${booking.noOfPassengers}</td>
                </tr>
                <tr>
                  <th>Base Fare:</th>
                  <c:if test="${booking.classType eq 'First' }">
					<td><i class="fa fa-rupee"></i> ${flight.firstSeatsFare}</td>
				  </c:if>
				<c:if test="${booking.classType eq 'Business' }">
					<td><i class="fa fa-rupee"></i> ${flight.bussSeatsFare}</td>
				</c:if>
                </tr>
                <tr>
                  <th>Total Fare:</th>
                  <td><i class="fa fa-rupee"></i> ${booking.totalFare}</td>
                </tr>
              </table>
            </div>
          </div>
        </div>
    </div>
  </div><!--End Booking Container-->
  <jsp:include page="footer.jsp" />
</body>
</html>