<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/controller" autocomplete="off" method="post">
		<input type="hidden" name="command" value="signup" />
		Login: <input type="text" name="login" size="40" />${errorLoginSignupMessage }<br />
		Email: <input type="text" name="email" size="40" />${errorEmailSignupMessage }<br />
		Password: <input type="password" name="password" size="40" />${errorPasswordSignupMessage }<br />
		<input type="submit" value="Create account" size="20" />
		${errorSignupMessage }
	</form>
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="signupBack" />
		<input type="submit" value="Back" size="20" />
	</form>
</body>
</html>