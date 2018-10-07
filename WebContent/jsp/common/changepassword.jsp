<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:bundle basename="resource.i18n.interface" prefix="changepassword.">
<html>
<head>
<title><fmt:message key="title" /></title>
</head>
<body>
<jsp:include page="/locale" />
	<br />
	<hr />
	<form action="${pageContext.request.contextPath}/controller" method="post">
	<input type="hidden" name="command" value="changePassword" />
	<table>
		<tr>
		<td><fmt:message key="newpassword" />: </td><td><input type="password" name="newPassword" autocomplete="off"/>
		${errorPasswordValidateMessage }${errorChangePasswordMessage }
		</td>
		</tr>
		<tr>
		<td><fmt:message key="oldpassword" />: </td><td><input type="password" name="oldPassword" autocomplete="off"/>
		${errorCheckLoginPasswordMessage }
		</td>
		</tr>
		<tr>
		<td><input type="submit" value="<fmt:message key="changepassword" />" /></td>
		</tr>	
	</table>	
	</form>
	<hr/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="backToChangePersonalData" />
		<input type="submit" value="<fmt:message key="backbutton" />" size="20" />
	</form>
</body>
</html>
</fmt:bundle>