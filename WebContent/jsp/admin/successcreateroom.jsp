<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>Successfull Room Creation</title>
</head>
<body>

 Room has been successfully added.
	<br/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="backToAdminRooms" /> <input
			type="submit" value="OK" />
	</form>
</body>
</html>