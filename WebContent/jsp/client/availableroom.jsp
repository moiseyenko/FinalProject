<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<title>Available Rooms</title>
</head>
<body>
	<jsp:include page="../loginlogout.jsp" />
	<hr />
	Available rooms:
	<c:choose>
		<c:when test="${fn:length(sessionData.availableRoomList)==0}">
			Sorry, room not found. Try to change criteria
		</c:when>
		<c:otherwise>
			<table>
				<c:forEach var="room" items="${sessionData.availableRoomList }" varStatus="status">
					<tr>
						<td>
							<c:url value="/controller" var="roomsURL">
								<c:param name="number" value="${room.number}" />
								<c:param name="command" value="chooseroom" />
							</c:url> 
							<a href="${roomsURL}"> ${room.number}:Capacity-${room.capacity};Price-${room.price};</a> 
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>  	
	<hr/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="backToOrder" />
		<input type="submit" value="Back" size="20" />
	</form>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="backToClientmain" />
		<input type="submit" value="Back to main" size="20" />
	</form>
</body>
</html>