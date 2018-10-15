<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:setBundle basename="resource.i18n.messages" var="rb" />
<fmt:bundle basename="resource.i18n.interface" prefix="createnationality.">
<html>
<head>
<title><fmt:message key="title" /></title>
</head>
<body>
<jsp:include page="/locale" />
	<br />
	<hr />
	<fmt:message key="writeinmessage" />:
	<br/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="create_nationality" />
		<table>
			<tr>
				<td><fmt:message key="countryid" />: </td>
				<td><input type="text" name="countryId" 
				pattern="^[A-Z][A-Z]$" title="<fmt:message key="message.countryiderror" bundle="${ rb }" />"/></td>
				<td>${errorCountryIdMessage}</td>
			</tr>
			<tr>
				<td><fmt:message key="country" />: </td>
				<td><input type="text" name="country" 
				pattern="^[A-Za-zА-яа-я Ёё'-]{1,80}$" title="<fmt:message key="message.countryerror" bundle="${ rb }" />" /></td>
				<td>${errorCountryMessage}</td>
			</tr>
			<tr>
				<td><input type="submit" value="<fmt:message key="create" />" size="20" /></td>
				<td>${errorCreateNationalityMessage}</td>
			</tr>
		</table>
	</form>
	<hr/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="to_admin_nationalities" />
		<input type="submit" value="<fmt:message key="backbutton" />" size="20" />
	</form>
</body>
</html>
</fmt:bundle>