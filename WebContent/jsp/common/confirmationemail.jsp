<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:bundle basename="resource.i18n.interface" prefix="confirmationemail.">
<html>
<head>
<title><fmt:message key="title" /></title>
</head>
<body>
	<jsp:include page="/locale" />
	<br />
	<hr />
	<h2><fmt:message key="message" /></h2>
	<hr />
	<form action="${pageContext.request.contextPath}/controller" autocomplete="off" method="post">
		<input type="hidden" name="command" value="checkKeyAndSignUp" />
		<input type="text" name="emailConfirmationKey" /> 
		<input type="submit" value="<fmt:message key="submit" />" size="20" />
		${errorKeyConfirmationMessage }
	</form>
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="backToSignup" />
		<input type="submit" value="<fmt:message key="backbutton" />" size="20" />
	</form>
</body>
</html>
</fmt:bundle>