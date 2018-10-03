<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Create Room Class</title>
</head>
<body>
	Write in necessary fields:
	<br/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="createClass" />
		<table>
			<tr>
				<td>Country ID: </td>
				<td><input type="text" name="classId" /></td>
				<td>${errorClassIdMessage}</td>
			</tr>
			<tr>
				<td><input type="submit" value="CREATE" size="20" /></td>
				<td>${errorCreateClassMessage}</td>
			</tr>
		</table>
	</form>
	<hr/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="backToAdminClasses" />
		<input type="submit" value="Back" size="20" />
	</form>
</body>
</html>