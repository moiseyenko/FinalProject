<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:bundle basename="resource.i18n.interface" prefix="hotelinfo.">
	<html>
<head>
<title><fmt:message key="title" /></title>
</head>
<body>
	<jsp:include page="/locale" />
	<br />
	<h2>
		<fmt:message key="mainmsg" />
		:
	</h2>
	<ul>
		<li><fmt:message key="standard" /></li>
		<li><fmt:message key="family" /></li>
		<li><fmt:message key="honeymoon" /></li>
		<li><fmt:message key="business" /></li>
		<li><fmt:message key="luxury" /></li>
		<li><fmt:message key="president" /></li>
	</ul>

	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="login_back" />
		<input type="submit" value="<fmt:message key="backbutton" />" />
	</form>

</body>
	</html>
</fmt:bundle>