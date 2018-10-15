<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:setBundle basename="resource.i18n.messages" var="rb" />
<fmt:bundle basename="resource.i18n.interface" prefix="signup.">
<html>
<head>
<title><fmt:message key="title" /></title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/locale" />
	<br />
	<hr />
	<form action="${pageContext.request.contextPath}/controller" autocomplete="off" method="post">
		<input type="hidden" name="command" value="signup" />
		<fmt:message key="loginlabel" />: <input type="text" name="login" size="40" 
		pattern="^[a-zA-ZА-Яа-я0-9_-]{3,25}$" title="<fmt:message key="message.loginsignuperror" bundle="${ rb }" />"
		/>${errorLoginSignupMessage }<br />
		<fmt:message key="emaillabel" />: <input type="text" name="email" size="40" 
		pattern="^[A-Za-z0-9_.%+-]{1,30}@[A-Za-z0-9.-]{1,10}\.[A-Za-z]{2,6}$" title="<fmt:message key="message.emailsignuperror" bundle="${ rb }" />"
		/>${errorEmailSignupMessage }<br />
		<fmt:message key="passwordlabel" />: <input type="password" name="password" size="40" 
		pattern="^(?=.*[0-9])(?=.*[a-zа-я])(?=.*[A-ZА-Я])(?=.*[@#$%^&+=])(?=\S+$).{8,}$" 
		title="<fmt:message key="message.passwordsignuperror" bundle="${ rb }" />"
		/>${errorPasswordSignupMessage }<br />
		<input type="submit" value="<fmt:message key="createsubmit" />" size="20" />
		${errorSignupMessage }${errorSendConfirmationEmailMessage }
	</form>
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="signup_back" />
		<input type="submit" value="<fmt:message key="backbutton" />" size="20" />
	</form>
</body>
</html>
</fmt:bundle>