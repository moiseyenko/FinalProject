<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:setBundle basename="resource.i18n.messages" var="rb" />
<fmt:bundle basename="resource.i18n.interface"
	prefix="confirmationemail.">
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
	<div class="container justify-content-center">
		<div class="row justify-content-center">
			<h2 class="std-text-color text-center">
				<fmt:message key="message" />
			</h2>
		</div>
		<div class="row">
			<div class="col-4"></div>
			<div class="col">
				<form action="${pageContext.request.contextPath}/controller"
					autocomplete="off" method="post">
					<input type="hidden" name="command" value="check_key_and_signUp" />
					
					<div class="input-group mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text fixed-input-group"
								id="basic-addon1"><fmt:message key="codelabel" /></span>
						</div>
						<input type="text" class="form-control" name="emailConfirmationKey"
							pattern="^[0-9a-f]{10}$"
							title="<fmt:message key="message.keyconfirmationerror" bundle="${ rb }" />"
							aria-describedby="basic-addon1">
					</div>
											
					<input class="btn btn-lg btn-primary btn-block" type="submit"
						value="<fmt:message key="submit" />" />
					<div class="text-danger text-center">${errorKeyConfirmationMessage }</div>
				</form>
				<form action="${pageContext.request.contextPath}/controller"
					method="post">
					<input type="hidden" name="command" value="back_to_signup" /> <input
						class="btn btn btn-block btn-outline-primary" type="submit"
						value="<fmt:message key="backbutton" />" />
				</form>
			</div>
			<div class="col-4"></div>
		</div>
	</div>
</body>
	</html>
</fmt:bundle>