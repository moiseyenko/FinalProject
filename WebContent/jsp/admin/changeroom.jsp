<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Change Room</title>
</head>
<body>
	Change the necessary fields:
	<br/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="approveChangeRoom" />
		<table>
			<tr>
				<td>Number: </td>
				<td><input type="text" name="number"  value="${sessionData.roomToChange.number }" disabled="disabled" /></td>
			</tr>
			<tr>
				<td>Capacity: </td>
				<td><input type="text" name="capacity" value="${sessionData.roomToChange.capacity }" /></td>
				<td>${errorCapacityMessage}</td>
			</tr>
			<tr>
				<td>Room class: </td>
				<td>
				<select name="class" >
					<c:forEach var="roomClass" items="${sessionData.roomClasses }" varStatus="status">
				  		<option value="${roomClass.classId }">${roomClass.classId }</option>
					</c:forEach>	
				</select>
				</td>
			</tr>
			<tr>
				<td>Price: </td>
				<td><input type="text" name="price" value="${sessionData.roomToChange.price }" /></td>
				<td>${wrongInputAmount}</td>
			</tr>
			<tr>
				<td><input type="submit" value="approve CHANGE" size="20" /></td>
				<td>${errorChangeRoomMessage}</td>
			</tr>
		</table>
	</form>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="backToAllrooms" />
		<input type="submit" value="Back" size="20" />
	</form>
</body>
</html>