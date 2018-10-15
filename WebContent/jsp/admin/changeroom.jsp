<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:setBundle basename="resource.i18n.messages" var="rb" />
<fmt:bundle basename="resource.i18n.interface" prefix="changeroom.">
<html>
<head>
<title><fmt:message key="title" /></title>
</head>
<body>
<jsp:include page="/locale" />
	<br />
	<hr />
	<fmt:message key="changemessage" />:
	<br/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="approve_change_room" />
		<table>
			<tr>
				<td><fmt:message key="number" />: </td>
				<td><input type="text" name="number"  value="${sessionData.roomToChange.number }" disabled="disabled" /></td>
			</tr>
			<tr>
				<td><fmt:message key="capacity" />: </td>
				<td><input type="text" name="capacity" value="${sessionData.roomToChange.capacity }" 
				pattern="^[0-9]{1,5}$" title="<fmt:message key="message.capacityerror" bundle="${ rb }" />" /></td>
				<td>${errorCapacityMessage}</td>
			</tr>
			<tr>
				<td><fmt:message key="roomclass" />: </td>
				<td>
				<select name="class" >
					<c:forEach var="roomClass" items="${sessionData.roomClasses }" varStatus="status">
				  		<option value="${roomClass.classId }">${roomClass.classId }</option>
					</c:forEach>	
				</select>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="price" />: </td>
				<td><input type="text" name="price" value="${sessionData.roomToChange.price }" 
				pattern="^[0-9]{1,10}([\\.,][0-9]{0,2})?$" title="<fmt:message key="message.wronginputamount" bundle="${ rb }" />"/></td>
				<td>${wrongInputAmount}</td>
			</tr>
			<tr>
				<td><input type="submit" value="<fmt:message key="approvechange" />" size="20" /></td>
				<td>${errorChangeRoomMessage}</td>
			</tr>
		</table>
	</form>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="back_to_all_rooms" />
		<input type="submit" value="<fmt:message key="backbutton" />" size="20" />
	</form>
</body>
</html>
</fmt:bundle>