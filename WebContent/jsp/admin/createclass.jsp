<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:bundle basename="resource.i18n.interface" prefix="createclass.">
<html>
<head>
<title><fmt:message key="title" />Create Room Class</title>
</head>
<body>
<jsp:include page="/locale" />
	<br />
	<hr />
	<fmt:message key="writeinmessage" />:
	<br/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="createClass" />
		<table>
			<tr>
				<td><fmt:message key="countryid" />: </td>
				<td><input type="text" name="classId" /></td>
				<td>${errorClassIdMessage}</td>
			</tr>
			<tr>
				<td><input type="submit" value="<fmt:message key="create" />" size="20" /></td>
				<td>${errorCreateClassMessage}</td>
			</tr>
		</table>
	</form>
	<hr/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="backToAdminClasses" />
		<input type="submit" value="<fmt:message key="backbutton" />" size="20" />
	</form>
</body>
</html>
</fmt:bundle>