<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<title>Success delete account</title>
</head>
<body>
	Your account has been successfully deleted.
	<hr/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="loginBack" />
		<input type="submit" value="OK" size="20" />
	</form>
</body>
</html>