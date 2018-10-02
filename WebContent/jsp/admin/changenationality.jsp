<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Change Nationality</title>
</head>
<body>
	Change the necessary fields:
	<br/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="approveChangeNationality" />
		<table>
			<tr>
				<td>CountryID: </td>
				<td><input type="text" name="countryId"  value="${sessionData.nationalityToChange.countryId }" disabled="disabled" /></td>
			</tr>
			<tr>
				<td>Country: </td>
				<td><input type="text" name="country" value="${sessionData.nationalityToChange.country }" /></td>
				<td>${errorCountryMessage}</td>
			</tr>
			<tr>
				<td><input type="submit" value="approve CHANGE" size="20" /></td>
				<td>${errorChangeNationalityMessage}</td>
			</tr>
		</table>
	</form>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="backToAllNationalities" />
		<input type="submit" value="Back" size="20" />
	</form>
</body>
</html>