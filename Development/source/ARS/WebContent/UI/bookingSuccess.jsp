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
  <div class="container" id="bookingSuccessContainer">
    <div class="row">
      <hr>
        <div id="flight">
          <div class="panel panel-primary">
            <div class="panel-heading">
              <h2 class="text-center">Booking Success</h2>
            </div>
            <div class="panel-body jumbotron">
              <h3 class="text-center">${message}</h3>
              <img alt="Thankyou" src="images/thankyou.jpg" width="720" height="320" class="center img-responsive">
            </div>
          </div><!--End Panel-->
        </div>
        <!-- End FlightList -->
    </div><!--End Row-->
    <div class="row">
    	<div class="col-sm-6 text-center">
    		<a href="showUserProfile.html" class="btn btn-danger btn-lg">View Booking</a>
    	</div>
    	<div class="col-sm-6 text-center">
    		<a href="index.html" class="btn btn-danger btn-lg">Go Home</a>
    	</div>
    </div>
  </div><!--End Booking Container-->
  <jsp:include page="footer.jsp" />
</body>
</html>