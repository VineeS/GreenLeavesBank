<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true" %>
<br/>
<div id="messageBox" class="hidden"></div>
<c:if test="${not empty req_error}">
	<br/><br/>
	<div id="errorMsg">
		<p class="text-danger">${req_error}</p>
	</div>
</c:if>
<c:remove var="req_error" scope="session" /> 