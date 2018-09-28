<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>Error Page</title>
</head>
<body>
	Request from ${pageContext.errorData.requestURI} is failed
	<br /> Servlet name or type: ${pageContext.errorData.servletName }
	<br /> Status code: ${pageContext.errorData.statusCode }
	<br /> Exception: ${pageContext.errorData.throwable }
	<form action="${pageContext.request.contextPath}/jsp/login.jsp" method="post">
		<input type="submit" value="Back" />
	</form>
</body>
</html>