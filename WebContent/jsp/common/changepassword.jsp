<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:setBundle basename="resource.i18n.messages" var="rb" />
<fmt:bundle basename="resource.i18n.interface" prefix="changepassword.">
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
		<input type="hidden" name="command" value="change_password" />
		<table>
			<tr>
				<td><fmt:message key="newpassword" />:</td>
				<td><input type="password" name="newPassword"
						autocomplete="off"
						pattern="^(?=.*[0-9])(?=.*[a-zа-я])(?=.*[A-ZА-Я])(?=.*[@#$%^&+=])(?=\S+$).{8,}$"
						title="<fmt:message key="message.passwordvalidateerror" bundle="${ rb }" />" />
					${errorPasswordValidateMessage }${errorChangePasswordMessage }</td>
			</tr>
			<tr>
				<td><fmt:message key="oldpassword" />:</td>
				<td><input type="password" name="oldPassword"
						autocomplete="off" /> ${errorCheckLoginPasswordMessage }</td>
			</tr>
			<tr>
				<td><input type="submit"
						value="<fmt:message key="changepassword" />" /></td>
			</tr>
		</table>
	</form>
	<hr />
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command"
			value="to_change_personal_data" />
		<input type="submit" value="<fmt:message key="backbutton" />"
			size="20" />
	</form>
</body>
	</html>
</fmt:bundle>