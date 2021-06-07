<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true" %>

	<header class="bank-template">
	    <nav class="navbar navbar-inverse navbar-fixed-top">
	      <div class="container">
	        <div class="navbar-header">
	          <button id="navbar-button" type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
	            <span class="sr-only">Toggle navigation</span>
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	          </button>
	          <a class="navbar-brand" href="welcome"><img alt="Bank Logo" src="<c:url value="/resources/img/leafIcon.jpg" />"width="100" height="100" ></a>
	          <a class="navbar-brand" href="welcome"><img alt="BankName Logo" src="<c:url value="/resources/img/name.png" />" ></a>
	        </div>
	        <div id="navbar" class="collapse navbar-collapse">
	          <ul class="nav navbar-nav">
	            <c:if test="${empty sessionScope.authenticatedUser}">
	            	<li><a href="login" style="font-size: 20px;">Sign In</a></li>
            	 	<li><a href="register" style="font-size: 20px;">Open an Account</a></li>
	            </c:if>
	            <c:if test="${not empty sessionScope.authenticatedUser}">
					<li><a href="clientDashboard" style="font-size: 20px;">HOME</a></li>
					<li><a href="logout" style="font-size: 20px;">LOGOUT</a></li>
	            </c:if>
	          </ul>
	        </div><!--/.nav-collapse -->
	      </div>
	    </nav>
	</header>

	<c:if test="${not empty sessionScope.authenticatedUser}">
		<!-- Logout form -->
		<form id="logoutForm" action="logout" method="post">
		</form>
	</c:if>
