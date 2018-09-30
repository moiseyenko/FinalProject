<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Change Login</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/controller" method="post">
	<input type="hidden" name="command" value="changeLogin" />
	<table>
		<tr>
		<td>New Login: </td><td><input type="text" name="newLogin" autocomplete="off"/>
		${errorLoginValidateMessage }${errorChangeLoginMessage }
		</td>
		</tr>
		<tr>
		<td>Password: </td><td><input type="password" name="password" autocomplete="off"/>
		${errorCheckLoginPasswordMessage }
		</td>
		</tr>
		<tr>
		<td><input type="submit" value="Change Login" size="20" /></td>
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