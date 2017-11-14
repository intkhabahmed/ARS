<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-default">
		  <div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
			  <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			  </button>
			  <a class="navbar-brand" href="index.html">MyAirlines <i class="fa fa-plane"></i></a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			  <ul class="nav navbar-nav navbar-left">
			  	 <li class="active"><a href="index.html"><i class="fa fa-home"></i> Home <span class="sr-only">(current)</span></a></li>
			  </ul>
			  <ul class="nav navbar-nav navbar-right">
			  	<c:if test="${sessionScope.user.username eq null}">
			  		<li id="login_btn_cover"><a href="showLogin.html" class="btn btn-default">Login/Signup</a></li>
			  	</c:if>
			  	<c:if test="${sessionScope.user.username ne null}">
			  		<c:set var="user" value="${sessionScope.user}" scope="session"/>
			  		<li class="dropdown">
					  <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="fa fa-user"></i> ${sessionScope.user.username} <span class="caret"></span></a>
					  <ul class="dropdown-menu">
						<li><a href="showUserProfile.html">View Profile</a></li>
						<li role="separator" class="divider"></li>
						<li><a href="logout.html">Logout</a></li>
					  </ul>
					</li>
			  	</c:if>
				
			  </ul>
			</div><!-- /.navbar-collapse -->
		  </div><!-- /.container-fluid -->
		</nav>