<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:bundle basename="resource.i18n.interface" prefix="changepersonaldata.">
<html>
<head>
<title><fmt:message key="title" /></title>
</head>
<body>
<jsp:include page="/locale" />
	<br />
	<hr />
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="toChangeLogin" />
		<input type="submit" value="<fmt:message key="changelogin" />" size="20" />
	</form>
	<hr/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="toChangePassword" />
		<input type="submit" value="<fmt:message key="changepassword" />" size="20" />
	</form>
	<hr/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="toDeleteAccount" />
		<input type="submit" value="<fmt:message key="deleteaccount" />" size="20" />
	</form>
	<hr/>
	<c:choose>
		<c:when test="${sessionData.role eq 'CLIENT' }">
			<form action="${pageContext.request.contextPath}/controller"
			method="post">
				<input type="hidden" name="command" value="backToClientmain" />
				<input type="submit" value="<fmt:message key="backbutton" />" size="20" />
			</form>
		</c:when>
		<c:otherwise>
			<form action="${pageContext.request.contextPath}/controller"
			method="post">
				<input type="hidden" name="command" value="backToAdminmain" />
				<input type="submit" value="<fmt:message key="backbutton" />Back" size="20" />
			</form>
		</c:otherwise>
	</c:choose>
</body>
</html>
</fmt:bundle>