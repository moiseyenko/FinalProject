<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:bundle basename="resource.i18n.interface" prefix="loginsignup.">
<html>
<head>
<title></title>
</head>
<body>
	<c:choose>
		<c:when test="${sessionData.role=='GUEST' }">
			<table>
				<tr>
					<td>
					<form action="${pageContext.request.contextPath}/controller" method="post">
							<input type="hidden" name="command" value="tologin" /> <input
								type="submit" value="<fmt:message key="login" />" size="20" />
						</form></td>
				</tr>
				<tr>
					<td>	
						<form action="${pageContext.request.contextPath}/controller" method="post">
						 <input type="hidden" name="command" value="tosignup" />
						 <input type="submit" value="<fmt:message key="signup" />"
							size="20" />
						</form>
					</td>
				</tr>
			</table>

		</c:when>
		<c:otherwise>
			<table>
				<tr>
					<td>${sessionData.login}</td>
				</tr>
				<tr>
					<td>
							<form action="${pageContext.request.contextPath}/controller" method="post" >
							<input type="hidden" name="command" value="logout" /> <input
								type="submit" value="<fmt:message key="logout" />" />
						</form>
					</td>
				</tr>
			</table>
		</c:otherwise>
	</c:choose>
</body>
</html>
</fmt:bundle>