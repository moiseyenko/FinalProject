<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:bundle basename="resource.i18n.interface" prefix="welcome.">
<html>
<head>
<title><fmt:message key="title" /></title>
</head>
<body>
<jsp:include page="/locale" />
	<br />
	<hr />
	<table>
		<tr>
			<td></td>
			<td><jsp:include page="loginsignup.jsp" /></td>
			<td></td>
		</tr>
		<tr>
			<td colspan="3"><fmt:message key="message" /></td>
		</tr>
		<tr>
			<td></td>
			<td><fmt:message key="hotelinfo" /></td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td><fmt:message key="contacts" /></td>
			<td></td>
		</tr>
	</table>
</body>
</html>
</fmt:bundle>