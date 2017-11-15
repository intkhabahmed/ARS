<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Home | MyAirlines</title>
<style type="text/css">
    <%@include file="../css/bootstrap.min.css" %>
    <%@include file="../css/font-awesome.min.css" %>
     <%@include file="../css/custom.css" %>
</style>
<script>
	<%@include file="../js/jquery-3.1.0.min.js" %>
	<%@include file="../js/bootstrap.min.js" %>
</script>
<link href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css" rel="stylesheet">
</head>
<body>
	<jsp:include  page="header.jsp" />
	<!--Navigation bar-->
	<div class="container" id="mainContainer">
		<div class="row">
			<div class="col-sm-12">
				<div id="formContainer">
					<c:if test="${message!=''}">
						<p class="text-center">${message}</p>
					</c:if>
					<div class="panel panel-primary">
						<div class="panel-heading">
							<h1 class="text-center">
								<i class="fa fa-plane"></i> Search Flights
							</h1>
						</div>
						<div class="panel-body">
			
							<hr>
							<form:form class="form-horizontal" action="retrieveListOfFlights.html"
								method="post" modelAttribute="booking">
								<div class="row">
									<div class="col-sm-4 col-sm-offset-1">
										<div class="form-group">
											<form:label path="srcCity">Source City</form:label>
											<form:input path="srcCity" list="airports" class="form-control" required="required"/>
											<datalist id="airports">
												<c:forEach items="${airports}" var="airport">
													<option value="${airport.abbreviation}">${airport.location} ( ${airport.abbreviation} )</option>
												</c:forEach>
											</datalist>
											<form:errors path="srcCity"></form:errors><br>
										</div>
									</div>
									<div class="col-sm-2 text-center">
										<br> <span class="fa fa-exchange fa-3x"></span>

									</div>
									<div class="col-sm-4">
										<div class="form-group">
											<form:label path="destCity">Destination City</form:label>
											<form:input path="destCity" list="airports" class="form-control" required="required"/>
											<datalist id="airports">
												<c:forEach items="${airports}" var="airport">
													<option value="${airport.abbreviation}">${airport.location} ( ${airport.abbreviation} )</option>
												</c:forEach>
											</datalist>
											<form:errors path="destCity"></form:errors><br>
										</div>
									</div>
								</div>
								<!--end row-->
								<div class="row">
									<div class="col-sm-2 col-sm-offset-1">
										<div class="form-group">
											<form:label path="travelDate">Depart Date</form:label>
											<form:input type="date" class="form-control"
												path="travelDate" required="required" min="${date}"/>
											<form:errors path="travelDate"></form:errors><br>
										</div>
									</div>
									<div class="col-sm-3 col-sm-offset-1">
										<div class="form-group">
											<form:label path="noOfPassengers">No of Travellers</form:label>
											<form:input type="text" class="form-control"
												path="noOfPassengers" value="1" required="required"
												pattern="[1-9]{1}" title="min=1 & max=9"/>
											<form:errors path="noOfPassengers"></form:errors><br>
										</div>
									</div>
									<div class="col-sm-3 col-sm-offset-1">
										<div class="form-group">
											<form:label path="classType">Class Type</form:label>
											<form:select path="classType" class="form-control" required="required">
												<form:option value="">--Select--</form:option>
												<form:options items="${classTypeOptions}" />
											</form:select>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="text-center">
										<input type="submit" value="Search"
											class="btn btn-danger btn-lg" />
									</div>
								</div>
							</form:form>
							<input type="hidden" id="airport" value="${airport}"/>
						</div>
					</div>
				</div>
				<!--end searchForm container -->
			</div>
			<!--end col-sm-12 -->
		</div><!--end row -->
		<div class="row" id="homeBannerImage">
			<img src="images/homeBanner.jpg" alt="HomeBanner" class="center img-responsive"/>
		</div>
	</div>
	<!--end main container-->
	<jsp:include page="footer.jsp" />
</body>
</html>