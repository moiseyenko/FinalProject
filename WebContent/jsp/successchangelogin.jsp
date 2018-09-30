<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<title>Success login change</title>
</head>
<body>
	Your login has been successfully changed. New login: ${sessionData.login }
	<hr/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="backToChangePersonalData" />
		<input type="submit" value="OK" size="20" />
	</form>
</body>
</html>