<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true" %>

<!DOCTYPE html>
<html lang="en">
  <%@include file="pageHeader.jsp"%>
  <body>
	<%@include file="header.jsp"%>
	<script type="text/javascript" src="<c:url value="/resources/js/jquery-1.11.3.js" />"></script>

	<main id="content" class="mainContent bank-template" role="main">
	<br/>
	<div class="container">
		<%@include file="errorMessage.jsp"%>
		<div id="applyForCreditCard">
			<form id="newCreditCardForm" action="applyCreditCard" method="post">
			<br/><br/>
				<div id="input-group-transcode" class="form-group">
					<label for="transcode" class="control-label">Transaction code</label>
					<input type="text" class="form-control" id="transcode" name="transcode" placeholder="Transaction Code">
				</div>
				<div id="input-group-fromAccount" class="form-group">
					<label for="fromAccountNum" class="control-label">From (account number)</label>
					<input type="number" class="form-control" id="fromAccountNum" name="fromAccountNum" placeholder="From Account Number">
				</div>
				<button id="createTransBtn" type="submit" class="btn btn-default">Submit</button>
			</form>
		</div>
	</div>
	</main>
	<script type="text/javascript" src="<c:url value="/resources/js/jquery-1.11.3.js" />"></script>
  </body>
</html>