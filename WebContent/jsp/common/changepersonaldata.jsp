<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:bundle basename="resource.i18n.interface"
	prefix="changepersonaldata.">
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
		<div class="container justify-content-center">
			<div class="row justify-content-center">
				<div class="col-4"></div>
				<div class="col">
					<form action="${pageContext.request.contextPath}/controller"
						method="post">
						<input type="hidden" name="command" value="to_change_login" /> <input
							class="btn btn-lg btn-primary btn-block" type="submit"
							value="<fmt:message key="changelogin" />" />
					</form>
					<form action="${pageContext.request.contextPath}/controller"
						method="post">
						<input type="hidden" name="command" value="to_change_password" />
						<input class="btn btn-lg btn-primary btn-block" type="submit"
							value="<fmt:message key="changepassword" />" />
					</form>
					<form action="${pageContext.request.contextPath}/controller"
						method="post">
						<input type="hidden" name="command" value="to_delete_account" />
						<input class="btn btn-lg btn-primary btn-block" type="submit"
							value="<fmt:message key="deleteaccount" />" />
					</form>
					<c:choose>
						<c:when test="${sessionData.role eq 'CLIENT' }">
							<form action="${pageContext.request.contextPath}/controller"
								method="post">
								<input type="hidden" name="command" value="back_to_client_main" />
								<input class="btn btn btn-block btn-outline-primary"
									type="submit" value="<fmt:message key="backbutton" />" />
							</form>
						</c:when>
						<c:otherwise>
							<form action="${pageContext.request.contextPath}/controller"
								method="post">
								<input type="hidden" name="command" value="back_to_admin_main" />
								<input class="btn btn btn-block btn-outline-primary"
									type="submit" value="<fmt:message key="backbutton" />" />
							</form>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="col-4"></div>
			</div>
		</div>
	</div>
</body>
	</html>
</fmt:bundle>