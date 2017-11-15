<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
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
</head>
<body>
	<div class="jumbotron center" id="errorBlock">
		<h1 class="text-center">Internal Server Error 500<br> ${message}</h1>
		<h2 class="text-center"><a href="index.html" class="btn btn-danger btn-lg">Go Home</a></h2>
		<h2 class="text-center"><a href="showUserProfile.html" class="btn btn-danger btn-lg">Go to Profile</a></h2>
	</div>
</body>
</html>