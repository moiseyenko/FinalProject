<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:setBundle basename="resource.i18n.messages" var="rb" />
<fmt:bundle basename="resource.i18n.interface" prefix="changelogin.">
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
						<input type="hidden" name="command" value="change_login" />
						<div class="input-group mb-3">
							<div class="input-group-prepend">
								<span class="input-group-text fixed-input-group"
									id="basic-addon1"><fmt:message key="labelnewlogin" /></span>
							</div>
							<input type="text" class="form-control" name="newLogin"
								pattern="^[a-zA-ZА-Яа-я0-9_-]{3,25}$"
								title="<fmt:message key="message.loginsignuperror" bundle="${ rb }" />"
								aria-describedby="basic-addon1">
						</div>
						<div class="text-danger text-center">${errorLoginValidateMessage }${errorChangeLoginMessage }</div>
						<div class="input-group mb-3">
							<div class="input-group-prepend">
								<span class="input-group-text fixed-input-group"
									id="basic-addon1"><fmt:message key="password" /></span>
							</div>
							<input type="password" class="form-control" name="password"
								pattern="^[a-zA-ZА-Яа-я0-9_-]{3,25}$"
								title="<fmt:message key="message.loginsignuperror" bundle="${ rb }" />"
								aria-describedby="basic-addon1" autocomplete="off">
						</div>
						<div class="text-danger text-center">${errorCheckLoginPasswordMessage }</div>
						<input class="btn btn-lg btn-block btn-primary" type="submit"
							value="<fmt:message key="submitchangelogin" />" />
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