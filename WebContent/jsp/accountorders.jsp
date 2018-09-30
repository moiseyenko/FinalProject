<style type="text/css">
.tg  {border-collapse:collapse;border-spacing:0;}
.tg td{font-family:Arial, sans-serif;font-size:12px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:black;}
.tg th{font-family:Arial, sans-serif;font-size:12px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:black;}
.tg .tg-88nc{font-weight:bold;border-color:inherit;text-align:center}
.tg .tg-c3ow{border-color:inherit;text-align:center;vertical-align:top}
.tg .tg-7btt{font-weight:bold;border-color:inherit;text-align:center;vertical-align:top}
</style>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html>
<head>
<title>Account Orders</title>
</head>
<body>
<jsp:include page="loginlogout.jsp" />
	<hr />

  
  <c:choose>
		<c:when test="${fn:length(sessionData.listFullInfoOrder)==0}">
			No order was found
		</c:when>
		<c:otherwise>
			<table class="tg">
			  <tr>
			    <th class="tg-88nc">Id</th>
			    <th class="tg-88nc">First name</th>
			    <th class="tg-88nc">Last name</th>
			    <th class="tg-88nc">Passport</th>
			    <th class="tg-88nc">Nationality code</th>
			    <th class="tg-88nc">Country</th>
			    <th class="tg-88nc">Room number</th>
			    <th class="tg-88nc">Room class</th>
			    <th class="tg-88nc">Room capacity</th>
			    <th class="tg-88nc">Price</th>
			    <th class="tg-88nc">From</th>
			    <th class="tg-88nc">To</th>
			    <th class="tg-88nc">Cost</th>
			    <th class="tg-88nc">Status</th>
			  </tr>
			   <jsp:useBean id = "localDateNow" class = "by.epam.hotel.dao.entity.LocalDateNow"/> 
				<c:forEach var="order" items="${sessionData.listFullInfoOrder }" varStatus="status">
					<tr>
						<td class="tg-c3ow">${order.id }</td>
					    <td class="tg-c3ow">${order.client.firstName }</td>
					    <td class="tg-c3ow">${order.client.lastName }</td>
					    <td class="tg-c3ow">${order.client.passport }</td>
					    <td class="tg-c3ow">${order.client.nationality }</td>
					    <td class="tg-c3ow">${order.nationality.country }</td>
					    <td class="tg-c3ow">${order.room.number }</td>
					    <td class="tg-c3ow">${order.room.classRoom }</td>
					    <td class="tg-c3ow">${order.room.capacity }</td>
					    <td class="tg-c3ow">${order.room.price }</td>
					    <td class="tg-c3ow">${order.from }</td>
					    <td class="tg-c3ow">${order.to }</td>
					    <td class="tg-c3ow">${order.cost }</td>
					    <td>   
					    <c:choose>
					    	<c:when test="${order.from.isAfter(localDateNow.now) && order.removed=='false' }">
					    	<form action="${pageContext.request.contextPath}/controller">
					    		 <input type="hidden" name=command value="cancelorder" />
							    <input type="hidden" name="orderIndex" value="${status.count }" />
							    <input type="submit" value="Cancel" />
						    </form>
					    	</c:when>
					    	<c:when test="${order.from.isEqual(localDateNow.now) && order.removed=='false' }">
					    	<form action="${pageContext.request.contextPath}/controller">
					    		 <input type="hidden" name=command value="cancelorder" />
							    <input type="hidden" name="orderIndex" value="${status.count }" />
							    <input type="submit" value="Cancel" />
						    </form>
					    	</c:when>
					    	<c:when test="${order.removed }">
					    		CANCELLED
					    	</c:when>
					    	<c:otherwise>
					    		SUCCESSED
					    	</c:otherwise>
					    </c:choose>  
					    </td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose> 
	<hr/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="backToClientmain" />
		<input type="submit" value="Back" size="20" />
	</form>
</body>
</html>