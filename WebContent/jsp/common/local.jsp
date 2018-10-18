<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:bundle basename="resource.i18n.interface" prefix="contacts.">
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
	<div class="navbar-collapse justify-content-md">
		<ul class="coordinates">
			<li><fmt:message key="address" /></li>
			<li><fmt:message key="phone" />: +375(44) 722-50-81</li>
			<li><fmt:message key="email" />: javahotel2018@gmail.com</li>
		</ul>
	</div>
	<div class="navbar-collapse justify-content-md-end">
		<form action="${pageContext.request.contextPath}/controller"
			method="post" style="vertical-align: middle;">
			<input type="hidden" name="locale" value="English" />
			<input type="hidden" name="command" value="change_locale" />
			<input type="hidden" name="jsppath"
				value="${pageContext.request.requestURI }" />
			<input class="btn btn-dark btn-primary btn-block" type="submit"
				value="English" />
		</form>
		<form action="${pageContext.request.contextPath}/controller"
			method="post">
			<input type="hidden" name="locale" value="Russian" />
			<input type="hidden" name="command" value="change_locale" />
			<input type="hidden" name="jsppath"
				value="${pageContext.request.requestURI }" />
			<input class="btn btn-dark btn-primary btn-block" type="submit"
				value="Русский" />
		</form>
	</div>
</nav>
</fmt:bundle>
