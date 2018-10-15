<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:bundle basename="resource.i18n.interface" prefix="adminclasses.">
<html>
<head>
<title><fmt:message key="title" /></title>
</head>
<body>
<jsp:include page="/locale" />
	<br />
	<hr />
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="to_all_classes" />
		<input type="hidden" name="currentPage" value="1">
	    <input type="hidden" name="recordsPerPage" value="10">
		<input type="submit" value="<fmt:message key="allclasses" />" size="20" />
	</form>
	<hr/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="to_create_class" />
		<input type="submit" value="<fmt:message key="createclass" />" size="20" />
	</form>
	<hr/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="back_to_admin_main" />
		<input type="submit" value="<fmt:message key="backbuttom" />" size="20" />
	</form>
</body>
</html>
</fmt:bundle>