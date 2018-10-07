<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:bundle basename="resource.i18n.interface" prefix="setrpporders.">
<html>
<head>
<title>setrpporders</title>
</head>
<body>
<jsp:include page="/locale" />
	<br />
	<hr />
<h1><fmt:message key="showorders" /></h1>

<form action="${pageContext.request.contextPath}/controller" method="post">
	<input type="hidden" name="command" value="toAllOrders" /> 
    <input type="hidden" name="currentPage" value="1">
    <label for="records"><fmt:message key="selectrpp" />:</label>
    <select name="recordsPerPage"> 
        <option value="5">5</option> 
        <option value="10" selected>10</option>
        <option value="15">15</option>
    </select>
    <input type="submit" value="<fmt:message key="submit" />" />
</form>
<hr/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="backToAdminmain" />
		<input type="submit" value="<fmt:message key="backbutton" />" size="20" />
	</form>


</body>
</html>
</fmt:bundle>