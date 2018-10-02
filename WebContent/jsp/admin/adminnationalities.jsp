<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<title>NATIONALITIES</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="toSetRecordsPerPageNationality" />
		<input type="submit" value="All nationalities" size="20" />
	</form>
	<hr/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="toCreateNationality" />
		<input type="submit" value="Create nationality" size="20" />
	</form>
	<hr/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="backToAdminmain" />
		<input type="submit" value="Back" size="20" />
	</form>
</body>
</html>