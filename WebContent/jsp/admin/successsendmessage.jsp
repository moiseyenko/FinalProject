<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:bundle basename="resource.i18n.interface" prefix="successsendmessage.">
<html>
<head>
<title><fmt:message key="title" />Successfull Message</title>
</head>
<body>
<jsp:include page="/locale" />
	<br />
	<hr />
<fmt:message key="successmesage" />
	<br/>
	<hr/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="backToAdminmain" />
		<input type="submit" value="<fmt:message key="okbutton" />" size="20" />
	</form>
</body>
</html>
</fmt:bundle>