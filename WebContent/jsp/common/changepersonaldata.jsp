<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:bundle basename="resource.i18n.interface" prefix="changepersonaldata.">
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
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="to_change_login" />
		<input type="submit" value="<fmt:message key="changelogin" />" size="20" />
	</form>
	<hr/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="to_change_password" />
		<input type="submit" value="<fmt:message key="changepassword" />" size="20" />
	</form>
	<hr/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="to_delete_account" />
		<input type="submit" value="<fmt:message key="deleteaccount" />" size="20" />
	</form>
	<hr/>
	<c:choose>
		<c:when test="${sessionData.role eq 'CLIENT' }">
			<form action="${pageContext.request.contextPath}/controller"
			method="post">
				<input type="hidden" name="command" value="back_to_client_main" />
				<input type="submit" value="<fmt:message key="backbutton" />" size="20" />
			</form>
		</c:when>
		<c:otherwise>
			<form action="${pageContext.request.contextPath}/controller"
			method="post">
				<input type="hidden" name="command" value="back_to_admin_main" />
				<input type="submit" value="<fmt:message key="backbutton" />" size="20" />
			</form>
		</c:otherwise>
	</c:choose>
</body>
</html>
</fmt:bundle>