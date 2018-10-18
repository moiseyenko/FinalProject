<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:setBundle basename="resource.i18n.messages" var="rb" />
<fmt:bundle basename="resource.i18n.interface" prefix="order.">
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
		<div class="row justify-content-center">
			<div class="col">
				<div class="table-wrapper-scroll-y">
					<table class="table std-text-color">
						<th scope="col"><fmt:message key="clientsmsg" /></th>
						<c:forEach var="client" items="${sessionData.clients }"
							varStatus="status">
							<tr>
								<td><c:url value="/controller" var="clientsURL">
										<c:param name="clientIndex" value="${status.count}" />
										<c:param name="command" value="fill_order_form" />
									</c:url> <a href="${clientsURL}"> ${client.firstName}
										${client.lastName} ${client.passport} (${client.nationality})
								</a></td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
			<div class="col std-text-color">
				<form action="${pageContext.request.contextPath}/controller"
					method="post">
					<input type="hidden" name="command" value="find_room" />
					<table class="table table-sm std-text-color">
						<tr>
							<td valign="top"><fmt:message key="fname" /></td>
							<td valign="top"><input class="form-control" type="text"
								name="fname" value="${tempFName}"
								pattern="^[A-ZА-ЯЁ][a-zA-ZА-Яа-яЁё\\'.-]{1,44}$"
								title="<fmt:message key="message.fnameerror" bundle="${ rb }" />" /></td>
							<td valign="top">${errorFNameMessage}</td>
						</tr>
						<tr>
							<td valign="top"><fmt:message key="lname" /></td>
							<td valign="top"><input class="form-control" type="text"
								name="lname" value="${tempLName}"
								pattern="^[a-zA-ZА-Яа-яЁё\\'.-]{1,45}$"
								title="<fmt:message key="message.lnameerror" bundle="${ rb }" />" /></td>
							<td valign="top">${errorLNameMessage}</td>
						</tr>
						<tr>
							<td valign="top"><fmt:message key="passport" /></td>
							<td valign="top"><input class="form-control" type="text"
								name="passport" value="${tempPassport}"
								pattern="^[a-zA-Z0-9]{1,15}$"
								title="<fmt:message key="message.passporterror" bundle="${ rb }" />" /></td>
							<td valign="top">${errorPassportMessage}</td>
						</tr>
						<tr>
							<td valign="top"><fmt:message key="nationality" /></td>
							<td valign="top"><input type="text"
								name="nationality" value="${tempNationality}"
								list="nationalities" /> <datalist id="nationalities">
									<c:forEach var="nation" items="${sessionData.nationalities }"
										varStatus="status">
										<option value="${nation.countryId }">(${nation.countryId })
											${nation.country}</option>
									</c:forEach>
								</datalist></td>
							<td valign="top">${errorBlackListClientMessage}${errorNationalityMessage}</td>
						</tr>
						<tr>
							<td valign="top"><fmt:message key="roomclass" /></td>
							<td valign="top"><select name="class">
									<c:forEach var="roomClass" items="${sessionData.roomClasses }"
										varStatus="status">
										<option value="${roomClass.classId }">${roomClass.classId }</option>
									</c:forEach>
							</select></td>
						</tr>
						<tr>
							<td valign="top"><fmt:message key="capacity" /></td>
							<td valign="top"><input class="form-control" type="text"
								name="capacity" pattern="^[0-9]{1,5}$"
								title="<fmt:message key="message.capacityerror" bundle="${ rb }" />" /></td>
							<td valign="top">${errorCapacityMessage}</td>
						</tr>
						<tr>
							<td valign="top"><fmt:message key="from" /></td>
							<td valign="top"><input type="date" name="from"
								value="${sessionData.from }" /></td>
						</tr>
						<tr>
							<td valign="top"><fmt:message key="to" /></td>
							<td valign="top"><input type="date" name="to"
								value="${sessionData.to }" /></td>
							<td valign="top">${errorFromToMessage}</td>
						</tr>
						<tr>
							<td />
							<td valign="top"><input type="submit"
								class="btn btn-lg btn-block btn-primary"
								value="<fmt:message key="find" />" /></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<div class="row justify-content-center">
			<div class="col-2 justify-content-center">
				<form action="${pageContext.request.contextPath}/controller"
					method="post">
					<input type="hidden" name="command" value="back_to_client_main" />
					<input class="btn btn-block btn-outline-primary" type="submit"
						value="<fmt:message key="backbutton" />" />
				</form>
			</div>
		</div>
	</div>
</body>
	</html>
</fmt:bundle>