<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:bundle basename="resource.i18n.interface" prefix="infofopayment.">
<html>
<head>
<title><fmt:message key="title" /></title>
</head>
<body>
<jsp:include page="/locale" />
	<br />
	<hr />
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="toPay" />
		<table>
			<tr>
				<td><fmt:message key="fname" />: </td>
				<td><input type="text" value="${sessionData.chosenClient.firstName}" size="40" disabled="disabled" /></td>
			</tr>
			<tr>
				<td><fmt:message key="lname" />: </td>
				<td><input type="text" value="${sessionData.chosenClient.lastName}" size="40" disabled="disabled" /></td>
			</tr>
			<tr>
				<td><fmt:message key="passport" />: </td>
				<td><input type="text" value="${sessionData.chosenClient.passport}" size="40" disabled="disabled" /></td>
			</tr>
			<tr>
				<td><fmt:message key="nationality" />: </td>
				<td><input type="text" value="${sessionData.chosenClient.nationality}" size="40" disabled="disabled" /></td>
			</tr>
			<tr>
				<td><fmt:message key="roomnumber" />: </td>
				<td><input type="text" value="${sessionData.chosenRoom.number}" size="40" disabled="disabled" /></td>
			</tr>
			<tr>
				<td><fmt:message key="roomclass" />: </td>
				<td><input type="text" value="${sessionData.chosenRoom.classRoom}" size="40" disabled="disabled" /></td>
			</tr>
			<tr>
				<td><fmt:message key="capaccity" />: </td>
				<td><input type="text" value="${sessionData.chosenRoom.capacity}" size="40" disabled="disabled" /></td>
			</tr>
			<tr>
				<td><fmt:message key="price" />: </td>
				<td><input type="text" value="${sessionData.chosenRoom.price}" size="40" disabled="disabled" /></td>
			</tr>
			<tr>
				<td><fmt:message key="from" />: </td>
				<td><input type="text" value="${sessionData.from}" size="40" disabled="disabled" /></td>
			</tr>
			<tr>
				<td><fmt:message key="to" />: </td>
				<td><input type="text" value="${sessionData.to}" size="40" disabled="disabled" /></td>
			</tr>
			<tr>
				<td><fmt:message key="topay" />: </td>
				<td><input type="text" value="${sessionData.toPay}" size="40" disabled="disabled" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="<fmt:message key="pay" />" size="20" /></td>
				<td>${errorFindBankAccountMessage }</td>
			</tr>
		</table>
	</form>
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="backToRooms" />
		<input type="submit" value="<fmt:message key="backbutton" />" size="20" />
	</form>
</body>
</html>
</fmt:bundle>