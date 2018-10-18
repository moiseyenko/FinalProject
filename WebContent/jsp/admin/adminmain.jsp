<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:bundle basename="resource.i18n.interface" prefix="adminmain.">
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
				<fmt:message key="adminmessage" />
			</h2>
		</div>

		<div class="text-danger text-center">${errorRelogMessage}</div>
		<div class="text-danger text-center">${errorReSignupMessage}</div>

		<div class="row justify-content-center">
			<div class="col-4"></div>
			<div class="col">
				<form action="${pageContext.request.contextPath}/controller"
					method="post">
					<input type="hidden" name="command" value="to_all_orders" /> <input
						type="hidden" name="currentPage" value="1"> <input
						type="hidden" name="recordsPerPage" value="10"> <input
						class="btn btn-lg btn-primary btn-block " type="submit"
						value="<fmt:message key="orders" />" />
				</form>
				<form action="${pageContext.request.contextPath}/controller"
					method="post">
					<input type="hidden" name="command" value="to_all_clients" /> <input
						type="hidden" name="currentPage" value="1"> <input
						type="hidden" name="recordsPerPage" value="10"> <input
						class="btn btn-lg btn-primary btn-block " type="submit"
						value="<fmt:message key="clients" />" />
				</form>
				<form action="${pageContext.request.contextPath}/controller"
					method="post">
					<input type="hidden" name="command" value="to_admin_rooms" /> <input
						class="btn btn-lg btn-primary btn-block " type="submit"
						value="<fmt:message key="rooms" />" />
				</form>
				<form action="${pageContext.request.contextPath}/controller"
					method="post">
					<input type="hidden" name="command" value="to_admin_nationalities" />
					<input class="btn btn-lg btn-primary btn-block " type="submit"
						value="<fmt:message key="nationalities" />" />
				</form>
				<form action="${pageContext.request.contextPath}/controller"
					method="post">
					<input type="hidden" name="command" value="to_admin_classes" /> <input
						class="btn btn-lg btn-primary btn-block " type="submit"
						value="<fmt:message key="roomclasses" />" />
				</form>
				<form action="${pageContext.request.contextPath}/controller"
					method="post">
					<input type="hidden" name="command" value="to_all_accounts" /> <input
						type="hidden" name="currentPage" value="1"> <input
						type="hidden" name="recordsPerPage" value="10"> <input
						class="btn btn-lg btn-primary btn-block " type="submit"
						value="<fmt:message key="accounts" />" />
				</form>
				<c:if test="${sessionData.login ne 'superadmin' }">
					<form action="${pageContext.request.contextPath}/controller"
						method="post">
						<input type="hidden" name="command"
							value="to_change_personal_data" /> <input
							class="btn btn-lg btn-primary btn-block " type="submit"
							value="<fmt:message key="changepersonaldata" />" />
					</form>
				</c:if>
				<form action="${pageContext.request.contextPath}/controller"
					method="post">
					<input type="hidden" name="command" value="to_all_emails" /> <input
						type="hidden" name="currentPage" value="1"> <input
						type="hidden" name="recordsPerPage" value="10"> <input
						class="btn btn-lg btn-primary btn-block" type="submit"
						value="<fmt:message key="sendmessage" />" />
				</form>
			</div>
			<div class="col-4"></div>
		</div>
	</div>
</body>
	</html>
</fmt:bundle>