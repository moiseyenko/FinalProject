<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:setBundle basename="resource.i18n.messages" var="rb" />
<fmt:bundle basename="resource.i18n.interface" prefix="signup.">
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
			<div class="col-4"></div>
			<div class="col">
				<form action="${pageContext.request.contextPath}/controller"
					autocomplete="off" method="post">

					<input type="hidden" name="command" value="signup" />

					<div class="input-group mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text fixed-input-group"
								id="basic-addon1"><fmt:message key="loginlabel" /></span>
						</div>
						<input type="text" class="form-control" name="login"
							pattern="^[a-zA-ZА-Яа-я0-9_-]{3,25}$"
							title="<fmt:message key="message.loginsignuperror" bundle="${ rb }" />"
							aria-describedby="basic-addon1">
					</div>

					<div class="text-danger text-center">${errorLoginSignupMessage }</div>

					<div class="input-group mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text fixed-input-group"
								id="basic-addon1"><fmt:message key="emaillabel" /></span>
						</div>
						<input type="text" class="form-control" name="email"
							pattern="^[A-Za-z0-9_.%+-]{1,30}@[A-Za-z0-9.-]{1,10}\.[A-Za-z]{2,6}$"
							title="<fmt:message key="message.emailsignuperror" bundle="${ rb }" />"
							aria-describedby="basic-addon1">
					</div>

					<div class="text-danger text-center">${errorEmailSignupMessage }</div>

					<div class="input-group mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text fixed-input-group"
								id="basic-addon1"><fmt:message key="passwordlabel" /></span>
						</div>
						<input type="password" class="form-control" name="password"
							pattern="^(?=.*[0-9])(?=.*[a-zа-я])(?=.*[A-ZА-Я])(?=.*[@#$%^&+=])(?=\S+$).{8,}$"
							title="<fmt:message key="message.passwordsignuperror" bundle="${ rb }" />"
							aria-describedby="basic-addon1">
					</div>

					<div class="text-danger text-center">${errorPasswordSignupMessage }</div>

					<input class="btn btn-lg btn-primary btn-block" type="submit"
						value="<fmt:message key="createsubmit" />" />

					<div class="text-danger text-center">${errorSignupMessage }</div>
					<div class="text-danger text-center">${errorSendConfirmationEmailMessage }</div>
					
				</form>
				<form action="${pageContext.request.contextPath}/controller"
					method="post">
					<input type="hidden" name="command" value="signup_back" /> <input
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