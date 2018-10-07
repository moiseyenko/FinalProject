<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:bundle basename="resource.i18n.interface" prefix="deleteaccount.">
<html>
<head>
<title><fmt:message key="title" /></title>
</head>
<body>
<jsp:include page="/locale" />
	<br />
	<hr />
	<form action="${pageContext.request.contextPath}/controller" method="post">
	<input type="hidden" name="command" value="deleteAccount" />
	<table>
		<tr>
		<td><fmt:message key="labelpassword" />: </td><td><input type="password" name="password" autocomplete="off"/>
		${errorCheckLoginPasswordMessage }
		</td>
		</tr>
		<tr>
		<td><input type="submit" value="<fmt:message key="deletesubmit" />" /></td>
		</tr>	
		<tr>
		<td>${errorDeleteAccountMessage }</td>
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