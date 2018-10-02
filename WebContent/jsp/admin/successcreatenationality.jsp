<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>Successfull Nationality Creation</title>
</head>
<body>

 Nationality has been successfully added.
	<br/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="backToAdminNationalities" /> <input
			type="submit" value="OK" />
	</form>
</body>
</html>