<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>Successfull Message</title>
</head>
<body>

 Message has been successfully sent.
	<br/>
	<hr/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="backToAdminmain" />
		<input type="submit" value="OK" size="20" />
	</form>
</body>
</html>