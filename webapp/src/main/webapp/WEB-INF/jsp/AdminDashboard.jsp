<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true"%>

<!DOCTYPE html>
<html lang="en">
<%@include file="pageHeader.jsp"%>
<body>
	<%@include file="header.jsp"%>
	<main id="content" class="mainContent bank-template" role="main">
	<div class="container">
		<div id="messageBox" class="hidden"></div>
			<c:if test="${not empty req_error}">
				<div id="errorMsg">
					<p class="text-danger">${req_error}</p>
				</div>
			</c:if>
			<c:remove var="req_error" scope="session" /> 
		<div id="registrationList" >
			<br> </br>
			<h2>Client Registration</h2>
				<c:if test="${not empty registrationList}">
				<form id="editRegistrationForm" action="AdminDashboard" method="post" >
					<table border="1" cellpadding="5" class="commonTable">
						<tr>
                            <th style="width: 150px">Username</th>
							<th style="width: 150px">Full name</th>
							<th style="width: 150px">Date of birth</th>
							<th style="width: 150px">occupation</th>
							<th style="width: 150px">SSN</th>							
							<th style="width: 250px">Address</th>
							<th style="width: 100px">Decision</th>
						</tr>
						<c:forEach var="client" items="${sessionScope.registrationList}">
							<tr>
                                <td>${client.user.userName}</td>
								<td>${client.fullName}</td>
								<td>${client.dateOfBirth}</td>
								<td>${client.occupation}</td>
								<td>${client.fin}</td>
								<td>${client.address}</td>
								<td><select name="decision" id="AdminDecision">
										<option value="waiting"></option>
										<option value="approve">Approve Application</option>
										<option value="decline">Decline Application</option>
									</select>
									<input type="hidden" name="user_id" value="${client.user.id}"> 
									<input type="hidden" name="user_email" value="${client.email}">
								</td>
							</tr>
						</c:forEach>
					</table>
				<input type="hidden" name="actionType" value="registrationDecisionAction">
				<!-- Record internal toolbar -->
				<div id="submitBar">
					<div class="btn-group toolbar" role="group">
						<button id="regDecisionBtn" type="submit" value="AdminDashboard" class="btn btn-default">Update</button>
					</div>
				</div>
			</form>
			</c:if>
				<c:if test="${empty registrationList}">
				<div>No Pending User Request</div>
			</c:if>
		</div>
		
		<div id="transactionList" >
			<h2>Client Transactions</h2>
			<c:if test="${not empty transList}">
				<form id="editTransactionForm" action="AdminDashboard" method="post" >
					<table border="1" cellpadding="5" class="commonTable">
						<tr>
							<th style="width: 150px">Transaction code</th>
							<th style="width: 150px">To (account number)</th>
							<th style="width: 150px">Datetime</th>
							<th style="width: 150px">Amount</th>
							<th style="width: 150px">Type of Transaction</th>
							<th style="width: 150px">Decision</th>
						</tr>
						<c:forEach var="trans" items="${transList}">
							<tr>
								<td>${trans.transCode}</td>
								<td>${trans.toAccountNum}</td>
								<td>${trans.dateTime}</td>
								<td>${trans.amount}</td>
								<td>${trans.type_of_transaction}</td>
								<td><select name="decision" id="AdminDecision">
										<option value="waiting"></option>
										<option value="approve">Approve Transaction</option>
										<option value="decline">Decline Transaction</option>
									</select>
									<input type="hidden" name="trans_id" value="${trans.id}">
                                    <input type="hidden" name="trans_toAccountNum" value="${trans.toAccountNum}">
                                    <input type="hidden" name="trans_amount" value="${trans.amount}">
                                    <input type="hidden" name="trans_type_of_transaction" value="${trans.type_of_transaction}">
								</td>
							</tr>
						</c:forEach>
					</table>
					<input type="hidden" name="actionType" value="transactionDecisionAction">
					<div id="submitBar">
						<div class="btn-group toolbar" role="group">
							<button id="transDecisionBtn" type="submit" value="AdminDashboard" class="btn btn-default">Update</button>
						</div>
					</div>
			</form>
			</c:if>
				<c:if test="${empty transList}">
				<div>No Transaction</div>
			</c:if>
		</div>
	</div>
	</main>
</body>
</html>
