<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:setBundle basename="resource.i18n.messages" var="rb" />
<fmt:bundle basename="resource.i18n.interface" prefix="changepassword.">
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
		<jsp:include page="/loginlogout" />
		<div class="container justify-content-center">
			<div class="row justify-content-center">
				<div class="col-4"></div>
				<div class="col">
					<form action="${pageContext.request.contextPath}/controller"
						method="post">
						<input type="hidden" name="command" value="change_password" />
						<div class="input-group mb-3">
							<div class="input-group-prepend">
								<span class="input-group-text fixed-input-group"
									id="basic-addon1"><fmt:message key="newpassword" /></span>
							</div>
							<input class="form-control" type="password" name="newPassword" autocomplete="off"
								pattern="^(?=.*[0-9])(?=.*[a-zа-я])(?=.*[A-ZА-Я])(?=.*[@#$%^&+=])(?=\S+$).{8,}$"
								title="<fmt:message key="message.passwordvalidateerror" bundle="${ rb }" />" />
						</div>
						<div class="text-danger text-center">${errorPasswordValidateMessage }${errorChangePasswordMessage }</div>
						<div class="input-group mb-3">
							<div class="input-group-prepend">
								<span class="input-group-text fixed-input-group"
									id="basic-addon1"><fmt:message key="oldpassword" /></span>
							</div>
							<input class="form-control" type="password" name="oldPassword"
								autocomplete="off" /> 
						</div>
						<div class="text-danger text-center">${errorCheckLoginPasswordMessage }</div>
						<input class="btn btn-lg btn-block btn-primary" type="submit"
							value="<fmt:message key="changepassword" />" />
					</form>
					<form action="${pageContext.request.contextPath}/controller"
						method="post">
						<input type="hidden" name="command"
							value="to_change_personal_data" /> <input
							class="btn btn-block btn-outline-primary" type="submit"
							value="<fmt:message key="backbutton" />" />
					</form>
				</div>
				<div class="col-4"></div>
			</div>
		</div>
	</div>
</body>
	</html>
</fmt:bundle>