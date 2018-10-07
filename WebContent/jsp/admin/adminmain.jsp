<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:bundle basename="resource.i18n.interface" prefix="adminmain.">
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
	<fmt:message key="adminmessage" />
	<hr />
	${errorRelogMessage} ${errorReSignupMessage}
	
	<hr />
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="toSetRecordsPerPageOrder" /> 
		<input type="submit" value="<fmt:message key="orders" />" />
	</form>
	<hr />
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="toSetRecordsPerPageClient" /> 
		<input type="submit" value="<fmt:message key="clients" />" />
	</form>
	<hr />
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="toAdminRooms" /> 
		<input type="submit" value="<fmt:message key="rooms" />" />
	</form>
	<hr />
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="toAdminNationalities" /> 
		<input type="submit" value="<fmt:message key="nationalities" />" />
	</form>
	<hr />
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="toAdminClasses" /> 
		<input type="submit" value="<fmt:message key="roomclasses" />" />
	</form>
	<hr />
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="toSetRecordsPerPageAccount" /> 
		<input type="submit" value="<fmt:message key="accounts" />" />
	</form>
	<hr />
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="toChangePersonalData" /> 
		<input type="submit" value="<fmt:message key="changepersonaldata" />" />
	</form>
	<hr />
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="toSetRecordsPerPageEmail" /> 
		<input type="submit" value="<fmt:message key="sendmessage" />" />
	</form>
</body>
</html>
</fmt:bundle>