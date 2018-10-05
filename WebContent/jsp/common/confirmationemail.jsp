<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
</head>
<body>
	<jsp:include page="local.jsp" />
	<br />
	<hr />
	<h2>Confirmation code has been sent to your email. Please, enter this code in field</h2>
	<hr />
	<form action="${pageContext.request.contextPath}/controller" autocomplete="off" method="post">
		<input type="hidden" name="command" value="checkKeyAndSignUp" />
		<input type="text" name="emailConfirmationKey" /> 
		<input type="submit" value="OK" size="20" />
		${errorKeyConfirmationMessage }
	</form>
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="backToSignup" />
		<input type="submit" value="Back" size="20" />
	</form>

</body>
</html>