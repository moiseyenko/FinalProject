<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:bundle basename="resource.i18n.interface" prefix="login.">
<html>
<head>
<title><fmt:message key="title" /></title>
</head>
<body>
<jsp:include page="/locale" />
	<br />
	<hr />
	<h2><fmt:message key="hello" /></h2>
	<form action="${pageContext.request.contextPath}/controller" name="LoginForm"  autocomplete="off" method="post">
		<input type="hidden" name="command" value="login" />
		<table>
			<tr>
				<td valign="top"><fmt:message key="loginalabel" />:</td>
				<td valign="top"><input type="text" name="login" size="40"  /></td>
			</tr>
			<tr>
				<td valign="top"><fmt:message key="password" />:</td>
				<td valign="top"><input type="password" name="password" autocomplete="off" size="40" /></td>
			</tr>
			<tr>
				<td valign="top">${errorLoginPassMessage}</td>
			</tr>
			<tr>
				<td />
				<td valign="top"><input type="submit" value="<fmt:message key="login" />" size="20" /></td>
			</tr>

		</table>
	</form>
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="loginBack" />
		<input type="submit" value=<fmt:message key="backbutton" /> size="20" />
	</form>
</body>
</html>
</fmt:bundle>