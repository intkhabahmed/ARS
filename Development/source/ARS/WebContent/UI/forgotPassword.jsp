<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Login | MyAirlines</title>
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
	<div class="container" id="loginFormContainer">
			<div class="row">
				<div class="col-sm-4 col-sm-offset-4" >
						<c:if test="${message!=''}">
							<p class="text-center">${message}</p>
						</c:if>
						<div class="panel panel-primary padding-5x" id="loginFormBody">
							<div class="panel-heading">
								<h1 class="text-center">Forgot Password Form</h1>
							</div>
							<div class="panel-body">
								<hr>
								<form:form class="form-horizontal" action="forgotPassword.html" method="post" modelAttribute="userObj">
									<div class="form-group">
										<form:label path="username">Username:</form:label>
										<form:input type="text" class="form-control" path="username" required="required"
											pattern="^[a-zA-Z][a-zA-Z0-9._]{2,20}$" 
											title="Username(min=3 & max=20) can have only characters,digits, '.(dot)' and '_' and start with an alphabet"/>
									</div>
									<div class="form-group">
										<form:label path="pwd">New Password:</form:label>
										<form:input type="password" class="form-control" path="pwd" required="required" 
											pattern="((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})" title="Password(min=8 & max=20) must contain alteast a small, an uppercase letter, a digit and a special symbol"/>
									</div>
									<div class="row">
										<div class="text-center">
											<input type="submit" value="Update" class="btn btn-danger btn-lg"/>
										</div>	
									</div>
								</form:form>
							</div>
						</div>
				</div>
			</div><!--Form container end-->
		</div>
		<jsp:include page="footer.jsp" />
</body>
</html>