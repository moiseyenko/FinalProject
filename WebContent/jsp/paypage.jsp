<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<title>Pay Page</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="pay" />	
		<table border="0" >
			<tr>
				<td valign="top">Current bank account: </td>
				<td valign="top"><input type="text" value="${sessionData.currentAmount}" size="40" disabled="disabled" /></td>
			</tr>
			<tr>
				<td valign="top">To pay: </td>
				<td valign="top"><input type="text" value="${sessionData.toPay}" size="40" disabled="disabled" /></td>
			</tr>
			<tr>
				<td />
				<td valign="top"><input type="submit" value="Pay" size="20" /></td>
				<td valign="top">${errorEnoughMoneyMessage }</td>
				<td valign="top">${PaymentErrorMessage }</td>
			</tr>
		</table>
	</form>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="toReplenish" />
		<input type="submit" value="Replenish" size="20" />
	</form>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="backToInfoPayment" />
		<input type="submit" value="Back" size="20" />
	</form>
</body>
</html>