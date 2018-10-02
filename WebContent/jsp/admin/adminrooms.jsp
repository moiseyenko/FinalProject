<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<title>Rooms</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="toSetRecordsPerPageRoom" />
		<input type="submit" value="All rooms" size="20" />
	</form>
	<hr/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="toCreateRoom" />
		<input type="submit" value="Create room" size="20" />
	</form>
	<hr/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="backToAdminmain" />
		<input type="submit" value="Back" size="20" />
	</form>
</body>
</html>