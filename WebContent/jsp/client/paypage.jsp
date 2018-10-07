<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:bundle basename="resource.i18n.interface" prefix="paypage.">
<html>
<head>
<title><fmt:message key="title" /></title>
</head>
<body>
<jsp:include page="/locale" />
	<br />
	<hr />
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="pay" />	
		<table border="0" >
			<tr>
				<td valign="top"><fmt:message key="curbankaccount" />: </td>
				<td valign="top"><input type="text" value="${sessionData.currentAmount}" size="40" disabled="disabled" /></td>
			</tr>
			<tr>
				<td valign="top"><fmt:message key="topay" />: </td>
				<td valign="top"><input type="text" value="${sessionData.toPay}" size="40" disabled="disabled" /></td>
			</tr>
			<tr>
				<td />
				<td valign="top"><input type="submit" value="<fmt:message key="pay" />" size="20" /></td>
				<td valign="top">${errorEnoughMoneyMessage }</td>
				<td valign="top">${PaymentErrorMessage }</td>
			</tr>
		</table>
	</form>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="toReplenish" />
		<input type="submit" value="<fmt:message key="replenish" />" size="20" />
	</form>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="backToInfoPayment" />
		<input type="submit" value="<fmt:message key="backbutton" />" size="20" />
	</form>
</body>
</html>
</fmt:bundle>