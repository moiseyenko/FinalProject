<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:bundle basename="resource.i18n.interface" prefix="clientmain.">
<html>
<head>
<title><fmt:message key="title" /></title>
</head>
<body>
	<jsp:include page="/locale" />
	<br />
	<hr />
	<jsp:include page="/loginlogout" />
	<br />
	<hr />
	<h3>
		<fmt:message key="welcome" />
	</h3>
	<hr />
	${sessionData.login},<fmt:message key="hello" />!
	<hr />
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="toSetRecordsPerPageAccountOrder" /> 
		<input type="submit" value="<fmt:message key="orders" />" />
	</form>
	<hr />
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="order" /> <input
			type="submit" value="<fmt:message key="makeorder" />" />
	</form>
	${errorRelogMessage} ${errorReSignupMessage}
	<hr />
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="toChangePersonalData" /> 
		<input type="submit" value="<fmt:message key="changepersonaldata" />" />
	</form>
</body>
</html>
</fmt:bundle>