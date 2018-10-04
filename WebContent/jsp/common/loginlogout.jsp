<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale.value}" scope="session" />
<fmt:bundle basename="resource.i18n.i18n" prefix="loginlogout.">
<html>
<head>
<title></title>
</head>
<body>
	<table>
		<tr>
			<td>${sessionData.login}</td>
		</tr>
		<tr>
			<td>
				<form action="${pageContext.request.contextPath}/controller"
					method="post">
					<input type="hidden" name="command" value="logout" /> <input
						type="submit" value="<fmt:message key="logout" />" />
				</form>
			</td>
		</tr>
	</table>
</body>
</html>
</fmt:bundle>