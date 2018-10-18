<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tld/format.tld" prefix="fmtl"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:bundle basename="resource.i18n.interface" prefix="accountorders.">
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
			<div class="col std-text-color">
				<form action="${pageContext.request.contextPath}/controller"
					method="post">
					<input type="hidden" name="command" value="to_account_orders" /> <input
						type="hidden" name="currentPage" value="1"> <label
						for="records"><fmt:message key="selectrpp" /></label> <select
						id="recordsPerPageId" name="recordsPerPage"
						onchange="this.form.submit();">
						<option value="5">5</option>
						<option value="10">10</option>
						<option value="15">15</option>
					</select>
					<script>
						document.getElementById("recordsPerPageId").value = "${sessionData.recordsPerPage}";
					</script>
				</form>

				<c:choose>
					<c:when
						test="${fn:length(sessionData.listAccountFullInfoOrder)==0}">
						<fmt:message key="noordersmsg" />
					</c:when>
					<c:otherwise>
						<table class="table std-text-color">
							<thead>
								<tr>
									<th scope="col"><fmt:message key="id" /></th>
									<th scope="col"><fmt:message key="fname" /></th>
									<th scope="col"><fmt:message key="lname" /></th>
									<th scope="col"><fmt:message key="passport" /></th>
									<th scope="col"><fmt:message key="nationalitycode" /></th>
									<th scope="col"><fmt:message key="country" /></th>
									<th scope="col"><fmt:message key="roomnumber" /></th>
									<th scope="col"><fmt:message key="roomclass" /></th>
									<th scope="col"><fmt:message key="roomcapacity" /></th>
									<th scope="col"><fmt:message key="price" /></th>
									<th scope="col"><fmt:message key="from" /></th>
									<th scope="col"><fmt:message key="to" /></th>
									<th scope="col"><fmt:message key="cost" /></th>
									<th scope="col"><fmt:message key="status" /></th>
								</tr>
							</thead>
							<tbody>
								<jsp:useBean id="localDateNow"
									class="by.epam.hotel.entity.LocalDateNow" />
								<c:forEach var="order"
									items="${sessionData.listAccountFullInfoOrder }"
									varStatus="status">
									<tr>
										<td>${order.id }</td>
										<td>${order.client.firstName }</td>
										<td>${order.client.lastName }</td>
										<td>${order.client.passport }</td>
										<td>${order.client.nationality }</td>
										<td>${order.nationality.country }</td>
										<td>${order.room.number }</td>
										<td>${order.room.classRoom }</td>
										<td>${order.room.capacity }</td>
										<td>${fmtl:parseCurrency(order.room.price, sessionData.locale) }</td>
										<td><fmtl:localDate date="${order.from }" /></td>
										<td><fmtl:localDate date="${order.to }" /></td>
										<td>${fmtl:parseCurrency(order.cost, sessionData.locale)}</td>
										<td><c:choose>
												<c:when
													test="${order.from.isAfter(localDateNow.now) && order.removed=='false' }">
													<form
														action="${pageContext.request.contextPath}/controller">
														<input type="hidden" name=command value="cancel_order" />
														<input type="hidden" name="orderIndex"
															value="${status.count }" /> <input type="submit"
															value="<fmt:message key="cancel" />" />
													</form>
												</c:when>
												<c:when
													test="${order.from.isEqual(localDateNow.now) && order.removed=='false' }">
													<form
														action="${pageContext.request.contextPath}/controller">
														<input type="hidden" name=command value="cancel_order" />
														<input type="hidden" name="orderIndex"
															value="${status.count }" /> <input type="submit"
															value="Cancel" />
													</form>
												</c:when>
												<c:when test="${order.removed }">
													<fmt:message key="cancelled" />
												</c:when>
												<c:otherwise>
													<fmt:message key="successed" />
												</c:otherwise>
											</c:choose></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:otherwise>
				</c:choose>

				<c:if test="${sessionData.currentPage != 1}">
					<c:url value="/controller" var="URL">
						<c:param name="recordsPerPage"
							value="${sessionData.recordsPerPage}" />
						<c:param name="currentPage" value="${sessionData.currentPage-1}" />
						<c:param name="command" value="to_account_orders" />
					</c:url>
					<a href="${URL}"><fmt:message key="previous" /></a>

				</c:if>
				<c:forEach begin="1" end="${sessionData.noOfPages}" var="i">
					<c:choose>
						<c:when test="${sessionData.currentPage eq i}">
							<a><span style="color: blue; font-weight: bold">${i}</span></a>
						</c:when>
						<c:otherwise>
							<c:url value="/controller" var="URL">
								<c:param name="recordsPerPage"
									value="${sessionData.recordsPerPage}" />
								<c:param name="currentPage" value="${i}" />
								<c:param name="command" value="to_account_orders" />
							</c:url>
							<a href="${URL}">${i}</a>

						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${sessionData.currentPage lt sessionData.noOfPages}">
					<c:url value="/controller" var="URL">
						<c:param name="recordsPerPage"
							value="${sessionData.recordsPerPage}" />
						<c:param name="currentPage" value="${sessionData.currentPage+1}" />
						<c:param name="command" value="to_account_orders" />
					</c:url>
					<a href="${URL}"><fmt:message key="next" /></a>
				</c:if>
			</div>
		</div>
		<div class="row justify-content-center">
			<div class="col-2 justify-content-center">
				<form action="${pageContext.request.contextPath}/controller"
					method="post">
					<input type="hidden" name="command" value="back_to_client_main" />
					<input class="btn btn btn-block btn-outline-primary" type="submit"
						value="<fmt:message key="backbutton" />" />
				</form>
			</div>
		</div>
	</div>
</body>
	</html>
</fmt:bundle>