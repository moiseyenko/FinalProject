<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:bundle basename="resource.i18n.interface" prefix="successpayment.">
<html>
<head>
<title><fmt:message key="title" />Success Payment</title>
</head>
<body>
<jsp:include page="/locale" />
	<br />
	<hr />
 <fmt:message key="successmsg" />SUCCESS PAYMENT
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="backToOrder" /> <input
			type="submit" value="<fmt:message key="backbutton" />" size="20" />
	</form>

	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="backToClientmain" /> <input
			type="submit" value="<fmt:message key="backtomainbutton" />" />
	</form>
</body>
</html>
</fmt:bundle>