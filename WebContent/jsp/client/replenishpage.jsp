<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:bundle basename="resource.i18n.interface" prefix="replenishpage.">
<html>
<head>
<title><fmt:message key="title" /></title>
</head>
<body>

	<jsp:include page="/locale" />
	<br />
	<hr />
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="replenish" />	
		<table border="0" >
			<tr>
				<td valign="top"><fmt:message key="replamount" />: </td>
				<td valign="top"><input type="text" name="replenishAmount" size="40" autocomplete="off" /></td>
				<td valign="top">${wrongInputReplenishAmount}${errorReplenishMessage}</td>
			</tr>
			<tr>
				<td />
				<td valign="top"><input type="submit" value="<fmt:message key="repelnish" />" size="20" /></td>
			</tr>
		</table>
	</form>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="backToPayPage" />
		<input type="submit" value="<fmt:message key="backbutton" />Back" size="20" />
	</form>
</body>
</html>
</fmt:bundle>