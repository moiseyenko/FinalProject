<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:bundle basename="resource.i18n.interface" prefix="loginlogout.">
	<div class="row">
		<div class="col"></div>
		<div
			class="col-2 text-right justify-content-end std-text-color">
			${sessionData.login}</div>
		<div class="col-2 justify-content-end">
			<form action="${pageContext.request.contextPath}/controller"
				method="post">
				<input type="hidden" name="command" value="logout" /> <input
					class="btn-sm btn-block btn-outline-primary" type="submit"
					value="<fmt:message key="logout" />" />
			</form>
		</div>
	</div>
</fmt:bundle>