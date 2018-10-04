<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
	<fmt:setLocale value="${sessionData.locale.value}" scope="session" />
	<fmt:setBundle basename="resource.l10n" var="rb" />
	<fmt:message key="welcome" bundle="${ rb }" />
	<br/>
	<hr/>
	<%@include file="local.jsp" %>
</body>
</html>