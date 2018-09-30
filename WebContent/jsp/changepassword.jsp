<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Change Password</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/controller" method="post">
	<input type="hidden" name="command" value="changePassword" />
	<table>
		<tr>
		<td>New Password: </td><td><input type="password" name="newPassword" autocomplete="off"/>
		${errorPasswordValidateMessage }${errorChangePasswordMessage }
		</td>
		</tr>
		<tr>
		<td>Old Password: </td><td><input type="password" name="oldPassword" autocomplete="off"/>
		${errorCheckLoginPasswordMessage }
		</td>
		</tr>
		<tr>
		<td><input type="submit" value="Change Password" /></td>
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