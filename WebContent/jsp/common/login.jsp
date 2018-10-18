<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:bundle basename="resource.i18n.interface" prefix="login.">
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
		<div class="row justify-content-center">
			<h2 class="std-text-color text-center">
				<fmt:message key="hello" />
			</h2>
		</div>
		<div class="row">
			<div class="col-4"></div>
			<div class="col">
				<form action="${pageContext.request.contextPath}/controller"
					name="LoginForm" autocomplete="off" method="post">

					<input type="hidden" name="command" value="login" />

					<div class="input-group mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text fixed-input-group"
								id="basic-addon1"><fmt:message key="loginlabel" /></span>
						</div>
						<input type="text" class="form-control" name="login"
							aria-describedby="basic-addon1">
					</div>

					<div class="input-group mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text fixed-input-group"
								id="basic-addon1"><fmt:message key="password" /></span>
						</div>
						<input type="password" class="form-control" name="password"
							autocomplete="off" aria-describedby="basic-addon1">
					</div>

					<div class="text-danger text-center">${errorLoginPassMessage}</div>
					<br/> <input type="submit" class="btn btn-lg btn-primary btn-block"
						value="<fmt:message key="login" />" />
				</form>
				<form action="${pageContext.request.contextPath}/controller"
					method="post">
					<input type="hidden" name="command" value="login_back" /> <input
						class="btn btn btn-block btn-outline-primary" type="submit"
						value="<fmt:message key="backbutton" />" />
				</form>
			</div>
			<div class="col-4"></div>
		</div>
	</div>
</body>
	</html>
</fmt:bundle>