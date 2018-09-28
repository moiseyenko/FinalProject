<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>loginsignup</title>
</head>
<body>
	<c:choose>
		<c:when test="${sessionData.role=='GUEST' }">
			<table>
				<tr>
					<td>
					<form action="${pageContext.request.contextPath}/controller" method="post">
							<input type="hidden" name="command" value="tologin" /> <input
								type="submit" value="Log in" size="20" />
						</form></td>
				</tr>
				<tr>
					<td>
						
						<form action="${pageContext.request.contextPath}/controller" method="post">
						 <input type="hidden" name="command" value="tosignup" />
						 <input type="submit" value="Sign up"
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
								type="submit" value="Log out" />
						</form>
					</td>
				</tr>
			</table>
		</c:otherwise>
	</c:choose>

</body>
</html>