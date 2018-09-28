<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>Too late</title>
</head>
<body>

	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="toPay" />
		<table>
			<tr>
				<td>First Name: </td>
				<td><input type="text" value="${sessionData.chosenClient.firstName}" size="40" disabled="disabled" /></td>
			</tr>
			<tr>
				<td>Last Name: </td>
				<td><input type="text" value="${sessionData.chosenClient.lastName}" size="40" disabled="disabled" /></td>
			</tr>
			<tr>
				<td>Passport: </td>
				<td><input type="text" value="${sessionData.chosenClient.passport}" size="40" disabled="disabled" /></td>
			</tr>
			<tr>
				<td>Nationality: </td>
				<td><input type="text" value="${sessionData.chosenClient.nationality}" size="40" disabled="disabled" /></td>
			</tr>
			<tr>
				<td>Room Number: </td>
				<td><input type="text" value="${sessionData.chosenRoom.number}" size="40" disabled="disabled" /></td>
			</tr>
			<tr>
				<td>Room Class: </td>
				<td><input type="text" value="${sessionData.chosenRoom.classRoom}" size="40" disabled="disabled" /></td>
			</tr>
			<tr>
				<td>Capacity: </td>
				<td><input type="text" value="${sessionData.chosenRoom.capacity}" size="40" disabled="disabled" /></td>
			</tr>
			<tr>
				<td>Price: </td>
				<td><input type="text" value="${sessionData.chosenRoom.price}" size="40" disabled="disabled" /></td>
			</tr>
			<tr>
				<td>From: </td>
				<td><input type="text" value="${sessionData.from}" size="40" disabled="disabled" /></td>
			</tr>
			<tr>
				<td>To: </td>
				<td><input type="text" value="${sessionData.to}" size="40" disabled="disabled" /></td>
			</tr>
			<tr>
				<td>To Pay: </td>
				<td><input type="text" value="${sessionData.toPay}" size="40" disabled="disabled" /></td>
			</tr>
		</table>
		<input type="submit" value="Pay" size="20" />
	</form>
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="backToRooms" />
		<input type="submit" value="Back" size="20" />
	</form>
</body>
</html>