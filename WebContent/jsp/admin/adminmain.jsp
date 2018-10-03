<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale.value}" scope="session" />
<fmt:bundle basename="resource.i18n.i18n" prefix="clientmain.">
<html>
<head>
<title>Admin Main</title>
</head>
<body>
	<jsp:include page="../local.jsp" />
	<br />
	<hr />
	<jsp:include page="../loginlogout.jsp" />
	<br />
	<hr />
	ADMIN
	
	<hr />
	${errorRelogMessage} ${errorReSignupMessage}
	
	<hr />
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="toSetRecordsPerPageOrder" /> 
		<input type="submit" value="Orders" />
	</form>
	<hr />
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="toSetRecordsPerPageClient" /> 
		<input type="submit" value="Clients" />
	</form>
	<hr />
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="toAdminRooms" /> 
		<input type="submit" value="Rooms" />
	</form>
	<hr />
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="toAdminNationalities" /> 
		<input type="submit" value="Nationalities" />
	</form>
	<hr />
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="toAdminClasses" /> 
		<input type="submit" value="Room Classes" />
	</form>
	<hr />
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="toSetRecordsPerPageAccount" /> 
		<input type="submit" value="Accounts" />
	</form>
	<hr />
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="toChangePersonalData" /> 
		<input type="submit" value="Change personal data" />
	</form>
</body>
</html>
</fmt:bundle>