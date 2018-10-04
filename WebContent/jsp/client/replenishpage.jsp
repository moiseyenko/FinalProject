<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<title>Replenish Page</title>
</head>
<body>
	<jsp:include page="/locale" />
	<br />
	<hr />
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="replenish" />	
		<table border="0" >
			<tr>
				<td valign="top">Replenish amount: </td>
				<td valign="top"><input type="text" name="replenishAmount" size="40" autocomplete="off" /></td>
				<td valign="top">${wrongInputReplenishAmount}${errorReplenishMessage}</td>
			</tr>
			<tr>
				<td />
				<td valign="top"><input type="submit" value="Replenish" size="20" /></td>
			</tr>
		</table>
	</form>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="backToPayPage" />
		<input type="submit" value="Back" size="20" />
	</form>
</body>
</html>