<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<title>Locale</title>
</head>
<body>
	<c:url value="/controller" var="russianURL" >
		<c:param name="locale" value="Russian" />
		<c:param name="command" value="changeLocale" />
		<c:param name="jsppath" value="${pageContext.request.requestURI }" />
	</c:url>
	<a href="${russianURL}" > Русский </a>
	
	<c:url value="/controller" var="englishURL">
		<c:param name="locale" value="English" />
		<c:param name="command" value="changeLocale" />
		<c:param name="jsppath" value="${pageContext.request.requestURI }" />
	</c:url>
	<a href="${englishURL}"> English </a>
<%-- 	<br/>
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="locale" value="English" />
		<input type="hidden" name="command" value="changeLocale" />
		<input type="hidden" name="jsppath" value="${pageContext.request.requestURI }" />
		<input type="submit" value="English" size="20" />
	</form>
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="locale" value="Russian" />
		<input type="hidden" name="command" value="changeLocale" />
		<input type="hidden" name="jsppath" value="${pageContext.request.requestURI }" />
		<input type="submit" value="Русский" size="20" />
	</form> --%>
</body>
</html>