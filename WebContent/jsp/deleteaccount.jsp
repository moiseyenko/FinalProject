<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Delete Account</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/controller" method="post">
	<input type="hidden" name="command" value="deleteAccount" />
	<table>
		<tr>
		<td>Password: </td><td><input type="password" name="password" autocomplete="off"/>
		${errorCheckLoginPasswordMessage }
		</td>
		</tr>
		<tr>
		<td><input type="submit" value="DELETE ACCOUNT" /></td>
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
		<input type="submit" value="Back" size="20" />
	</form>
</body>
</html>