<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:bundle basename="resource.i18n.interface" prefix="subjecttextsend.">
<html>
<head>
<title><fmt:message key="title" /></title>
</head>
<body>
<jsp:include page="/locale" />
	<br />
	<hr />
	<form action="${pageContext.request.contextPath}/controller" method="post" >
	<input type="hidden" name="command" value="sendMessage" />
   		<fmt:message key="subject" />: <input type="text" name="subject" /><br/>
	    <fmt:message key="text" />: <input type="text" name="text" /><br/>
	    <input type="submit" value="<fmt:message key="sendmessage" />" />
    </form>
    ${errorSendMessageMessage }
  	<hr />
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="backToAllEmails" />
		<input type="submit" value="<fmt:message key="backbutton" />" size="20" />
	</form>
</body>
</html>
</fmt:bundle>