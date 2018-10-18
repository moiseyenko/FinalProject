<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:bundle basename="resource.i18n.interface" prefix="hotelinfo.">
	<html>
<head>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css">

<title><fmt:message key="title" /></title>
</head>
<body>
	<jsp:include page="/locale" />
	<div class="container">
		<div class="row justify-content-center">
			<h2 class="std-text-color text-center">
				<fmt:message key="mainmsg" />
			</h2>
			<br />
			<ul class="std-text-color">
				<li><fmt:message key="standard" /></li>
				<li><fmt:message key="family" /></li>
				<li><fmt:message key="honeymoon" /></li>
				<li><fmt:message key="business" /></li>
				<li><fmt:message key="luxury" /></li>
				<li><fmt:message key="president" /></li>
			</ul>
		</div>
		<div class="row justify-content-center">
			<form action="${pageContext.request.contextPath}/controller"
				method="post">
				<input type="hidden" name="command" value="login_back" /> <input
					class="btn btn btn-block btn-outline-primary" type="submit"
					value="<fmt:message key="backbutton" />" />
			</form>
		</div>
	</div>
</body>
	</html>
</fmt:bundle>