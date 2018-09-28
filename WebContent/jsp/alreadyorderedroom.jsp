<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>Too late</title>
</head>
<body>

Sorry, this room has already been ordered<br/>
<hr/>

	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="backToRooms" />
		<input type="submit" value="Back" size="20" />
	</form>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="backToClientmain" />
		<input type="submit" value="Back to main" size="20" />
	</form>
</body>
</html>