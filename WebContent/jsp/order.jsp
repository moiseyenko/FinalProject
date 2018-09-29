<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<title>Order</title>
</head>
<body>
	<jsp:include page="loginlogout.jsp" />
	<hr />
	Your clients:
	<div style="height:80;width:300px; border:1px solid #ccc;overflow:auto;">
	   	<table>
			<c:forEach var="client" items="${sessionData.clients }" varStatus="status">
				<tr>
					<td>
						<c:url value="/controller" var="clientsURL">
							<c:param name="index" value="${status.count}" />
							<c:param name="command" value="fillorderform" />
						</c:url> 
						<a href="${clientsURL}"> ${client.firstName} ${client.lastName} ${client.passport} (${client.nationality}) </a> 
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<hr/>
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="findRoom" />	
		<table border="0" >
			<tr>
				<td valign="top">First Name: </td>
				<td valign="top"><input type="text" name="fname" value="${tempFName}" size="40" /></td>
				<td valign="top">${errorFNameMessage}</td>
			</tr>
			<tr>
				<td valign="top">Last Name: </td>
				<td valign="top"><input type="text" name="lname" value="${tempLName}" size="40" /></td>
				<td valign="top">${errorLNameMessage}</td>
			</tr>
			<tr>
				<td valign="top">Passport: </td>
				<td valign="top"><input type="text" name="passport" value="${tempPassport}" size="40" /></td>
				<td valign="top">${errorPassportMessage}</td>
			</tr>
			<tr>
				<td valign="top">Nationality: </td>
				<td valign="top"><input type="text" name="nationality" value="${tempNationality}" size="40" list="nationalities"/>
				<datalist id="nationalities">
					<c:forEach var="nation" items="${sessionData.nationalities }" varStatus="status">
				  		<option value="${nation.countryId }">(${nation.countryId }) ${nation.country}</option>
					</c:forEach>	
				</datalist>
				</td>
				<td valign="top">${errorBlackListClientMessage}${errorNationalityMessage}</td>	
			</tr>
			<tr>
				<td valign="top">Room's class: </td>
				<td valign="top">
				<select name="class" >
					<c:forEach var="roomClass" items="${sessionData.roomClasses }" varStatus="status">
				  		<option value="${roomClass.classId }">${roomClass.classId }</option>
					</c:forEach>	
				</select>
				</td>
			</tr>
			<tr>
				<td valign="top">Capacity: </td>
				<td valign="top"><input type="text" name="capacity" size="40" /></td>
				<td valign="top">${errorCapacityMessage}</td>
			</tr>
			<tr>
				<td valign="top">From: </td>
				<td valign="top"><input type="date" name="from" value="${sessionData.from }"/></td>
			</tr>
			<tr>
				<td valign="top">To: </td>
				<td valign="top"><input type="date" name="to" value="${sessionData.to }"/></td>
				<td valign="top">${errorFromToMessage}</td>
			</tr>
			<tr>
				<td />
				<td valign="top"><input type="submit" value="Find" size="20" /></td>
			</tr>
		</table>
	</form>
	<hr/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="backToClientmain" />
		<input type="submit" value="Back" size="20" />
	</form>
</body>
</html>