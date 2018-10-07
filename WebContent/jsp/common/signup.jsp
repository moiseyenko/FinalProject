<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:bundle basename="resource.i18n.interface" prefix="signup.">
<html>
<head>
<title><fmt:message key="title" /></title>
</head>
<body>
<jsp:include page="/locale" />
	<br />
	<hr />
	<form action="${pageContext.request.contextPath}/controller" autocomplete="off" method="post">
		<input type="hidden" name="command" value="signup" />
		<fmt:message key="loginlabel" />: <input type="text" name="login" size="40" />${errorLoginSignupMessage }<br />
		<fmt:message key="emaillabel" />: <input type="text" name="email" size="40" />${errorEmailSignupMessage }<br />
		<fmt:message key="passwordlabel" />: <input type="password" name="password" size="40" />${errorPasswordSignupMessage }<br />
		<input type="submit" value="<fmt:message key="createsubmit" />" size="20" />
		${errorSignupMessage }${errorSendConfirmationEmailMessage }
	</form>
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="signupBack" />
		<input type="submit" value="<fmt:message key="backbutton" />" size="20" />
	</form>
</body>
</html>
</fmt:bundle>