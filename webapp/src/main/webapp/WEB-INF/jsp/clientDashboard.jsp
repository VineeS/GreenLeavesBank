<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true" %>

<!DOCTYPE html>
<html lang="en">
  <%@include file="pageHeader.jsp"%>
  <body>
	<%@include file="header.jsp"%>
	
	<main id="content" class="mainContent bank-template" role="main">
	<div class="container">
		<%@include file="errorMessage.jsp"%>
		<br/>
		<div id="accountBalance">
			<h4>ACCOUNT BALANCE</h4>
			<div><h1 style="color: slategray; font-size: 80px;">$${clientInfo.account.amount}</h1></div>
		</div>
		<div id="loanBalance">
			<h4>LOAN BALANCE</h4>
			<div><h1 style="color: slategray; font-size: 80px;">$${clientInfo.account.loanAmount}</h1></div>
		</div>
		<div id="creditCardBalance">
			<h4>CREDIT CARD BALANCE</h4>
			<div><h1 style="color: slategray; font-size: 80px;">$${clientInfo.account.creditCardAmount}</h1></div>
		</div>
		<div id="transHistory">
			<h4>TRANSACTION HISTORY</h4>
			<table border="1" cellpadding="5" class="commonTable">
				<tr>
					<th style="width: 150px">Transaction code</th>
					<th style="width: 150px">To (account number)</th>
					<th style="width: 150px">Datetime</th>
					<th style="width: 150px">Amount</th>
					<th style="width: 150px">Status</th>
					<th style="width: 150px">Type of Transaction</th>
				</tr>
				<c:forEach var="trans" items="${clientInfo.transactions}">
					<c:choose>
						<c:when test="${trans.status=='APPROVED'}">
							<tr bgcolor="#27ae60">
								<td>${trans.transCode}</td>
								<td>${trans.toAccountNum}</td>
								<td>${trans.dateTime}</td>
								<td>${trans.amount}</td>
								<td>${trans.status}</td>
								<td>${trans.type_of_transaction}</td>
								
							</tr>
						</c:when>
						<c:when test="${trans.status=='DECLINED'}">
							<tr bgcolor="#e74c3c">
								<td>${trans.transCode}</td>
								<td>${trans.toAccountNum}</td>
								<td>${trans.dateTime}</td>
								<td>${trans.amount}</td>
								<td>${trans.status}</td>
								<td>${trans.type_of_transaction}</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<td>${trans.transCode}</td>
								<td>${trans.toAccountNum}</td>
								<td>${trans.dateTime}</td>
								<td>${trans.amount}</td>
								<c:if test="${empty trans.status}">
									<td>Pending</td>
								</c:if>
								<c:if test="${not empty trans.status}">
									<td>${trans.status}</td>
								</c:if>
								<td>${trans.type_of_transaction}</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</table>
		</div>
		<div id="createTransaction" style="padding-top: 50px">
			<h4>TRANSACTION CREATION</h4>
			<h6>SUBMIT TRANSACTION</h6>
			<form id="registrationForm" action="newTransaction" method="get">
				<button id="createTransBtn" type="submit" class="btn btn-default">New Transaction</button>
			</form>
			
		</div>
		<div id="applyForLoan" style="padding-top: 50px">
			<h4>APPLY FOR LOAN</h4>
			<form id="loanForm" action="applyLoan" method="get">
				<button id="applyLoanBtn" type="submit" class="btn btn-default">Apply For Loan</button>
			</form>
		</div>
		<div id="applyForCreditCard" style="padding-top: 50px">
			<h4>APPLY FOR CREDIT CARD</h4>
			<form id="creditCardForm" action="applyCreditCard" method="get">
				<button id="applyCreditCardBtn" type="submit" class="btn btn-default">Apply For Credit Card</button>
			</form>
		</div>
	</main>
  </body>
</html>
