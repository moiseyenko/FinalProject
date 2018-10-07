<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:bundle basename="resource.i18n.interface" prefix="availableroom.">
<html>
<head>
<title><fmt:message key="title" /></title>
</head>
<body>
<jsp:include page="/locale" />
	<br />
	<hr />
	<jsp:include page="/loginlogout" />
	<hr />
	<fmt:message key="availroomsmsg" />:
	<c:choose>
		<c:when test="${fn:length(sessionData.availableRoomList)==0}">
			<fmt:message key="sorrymsg" />
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
							<a href="${roomsURL}"> ${room.number}:<fmt:message key="capacity" />-${room.capacity};<fmt:message key="price" />-${room.price};</a> 
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
		<input type="submit" value="<fmt:message key="backbutton" />" size="20" />
	</form>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="backToClientmain" />
		<input type="submit" value="<fmt:message key="backtomainbutton" />" size="20" />
	</form>
</body>
</html>
</fmt:bundle>