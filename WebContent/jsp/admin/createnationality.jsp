<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Create Nationality</title>
</head>
<body>
	Write in necessary fields:
	<br/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="createNationality" />
		<table>
			<tr>
				<td>Country ID: </td>
				<td><input type="text" name="countryId" /></td>
				<td>${errorCountryIdMessage}</td>
			</tr>
			<tr>
				<td>Country: </td>
				<td><input type="text" name="country"  /></td>
				<td>${errorCountryMessage}</td>
			</tr>
			<tr>
				<td><input type="submit" value="CREATE" size="20" /></td>
				<td>${errorCreateNationalityMessage}</td>
			</tr>
		</table>
	</form>
	<hr/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="backToAdminNationalities" />
		<input type="submit" value="Back" size="20" />
	</form>
</body>
</html>