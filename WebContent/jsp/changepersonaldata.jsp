<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Change Data</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="toChangeLogin" />
		<input type="submit" value="Change login" size="20" />
	</form>
	<hr/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="toChangePassword" />
		<input type="submit" value="Change password" size="20" />
	</form>
	<hr/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="toDeleteAccount" />
		<input type="submit" value="Delete account" size="20" />
	</form>
	<hr/>
	<c:choose>
		<c:when test="${sessionData.role eq 'CLIENT' }">
			<form action="${pageContext.request.contextPath}/controller"
			method="post">
				<input type="hidden" name="command" value="backToClientmain" />
				<input type="submit" value="Back" size="20" />
			</form>
		</c:when>
		<c:otherwise>
			<form action="${pageContext.request.contextPath}/controller"
			method="post">
				<input type="hidden" name="command" value="backToAdminmain" />
				<input type="submit" value="Back" size="20" />
			</form>
		</c:otherwise>
	</c:choose>
</body>
</html>