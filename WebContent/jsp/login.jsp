<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale.value}" scope="session" />
<fmt:bundle basename="resource.i18n.i18n" prefix="login.">
<html>
<head>
<!-- <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0"> -->
<title><fmt:message key="title" /></title>
</head>
<body>
<jsp:include page="local.jsp" />
	<br />
	<hr />

	<h2><fmt:message key="hello" /></h2>
	<c:url var="controllerUrl" value="/controller" />
	<form action="${pageContext.request.contextPath}/controller" name="LoginForm"  autocomplete="off" method="post">
		<input type="hidden" name="command" value="login" />
		<table border="0">
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
				<td valign="top">${wrongAction}</td>

			</tr>
			<tr>
				<td valign="top">${nullPage}</td>

			</tr>
			<tr>
				<td />
				<td valign="top"><input type="submit" value="<fmt:message key="login" />" size="20" /></td>
			</tr>

		</table>
	</form>
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="loginBack" />
		<input type="submit" value=<fmt:message key="back" /> size="20" />
	</form>
	
</body>
</html>
</fmt:bundle>