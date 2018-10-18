<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:bundle basename="resource.i18n.interface" prefix="clientmain.">
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
	<div class="container justify-content-center">
		<jsp:include page="/loginlogout" />
		<div class="row justify-content-center">
			<h2 class="std-text-color text-center">
				<fmt:message key="welcome" />, ${sessionData.login}
			</h2>
		</div>

		<div class="row justify-content-center">
			<div class="col-4"></div>
			<div class="col">
				<form action="${pageContext.request.contextPath}/controller"
					method="post">
					<input type="hidden" name="command" value="to_account_orders" /> <input
						type="hidden" name="currentPage" value="1"> <input
						type="hidden" name="recordsPerPage" value="10"> <input
						class="btn btn-lg btn-primary btn-block" type="submit"
						value="<fmt:message key="orders" />" />
				</form>
				<form action="${pageContext.request.contextPath}/controller"
					method="post">
					<input type="hidden" name="command" value="order" /> <input
						class="btn btn-lg btn-primary btn-block" type="submit"
						value="<fmt:message key="makeorder" />" />
				</form>
				<div class="text-danger text-center">${errorRelogMessage}</div>
				<div class="text-danger text-center">${errorReSignupMessage}</div>
				<form action="${pageContext.request.contextPath}/controller"
					method="post">
					<input type="hidden" name="command" value="to_change_personal_data" />
					<input class="btn btn-lg btn-primary btn-block" type="submit"
						value="<fmt:message key="changepersonaldata" />" />
				</form>
			</div>
			<div class="col-4"></div>
		</div>
	</div>
</body>
	</html>
</fmt:bundle>