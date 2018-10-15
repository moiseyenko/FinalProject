<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:setBundle basename="resource.i18n.messages" var="rb" />
<fmt:bundle basename="resource.i18n.interface" prefix="confirmationemail.">
<html>
<head>
<title><fmt:message key="title" /></title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<jsp:include page="/locale" />
	<br />
	<hr />
	<h2><fmt:message key="message" /></h2>
	<hr />
	<form action="${pageContext.request.contextPath}/controller" autocomplete="off" method="post">
		<input type="hidden" name="command" value="check_key_and_signUp" />
		<input type="text" name="emailConfirmationKey" 
		pattern="^[0-9a-f]{10}$" title="<fmt:message key="message.keyconfirmationerror" bundle="${ rb }" />"/> 
		<input type="submit" value="<fmt:message key="submit" />" size="20" />
		${errorKeyConfirmationMessage }
	</form>
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="back_to_signup" />
		<input type="submit" value="<fmt:message key="backbutton" />" size="20" />
	</form>
</body>
</html>
</fmt:bundle>