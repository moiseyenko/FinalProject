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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionData.locale}" scope="session" />
<fmt:bundle basename="resource.i18n.interface" prefix="accountorders.">
<html>
<head>
<title><fmt:message key="title" /></title>
</head>
<body>
<jsp:include page="/locale" />
	<br />
	<hr />
<jsp:include page="/loginlogout" />
	<hr /> 
  <c:choose>
		<c:when test="${fn:length(sessionData.listAccountFullInfoOrder)==0}">
			No order was found
		</c:when>
		<c:otherwise>
			<table class="tg">
			  <tr>
			    <th class="tg-88nc"><fmt:message key="id" /></th>
			   	<th class="tg-88nc"><fmt:message key="fname" /></th>
			    <th class="tg-88nc"><fmt:message key="lname" /></th>
			    <th class="tg-88nc"><fmt:message key="passport" /></th>
			    <th class="tg-88nc"><fmt:message key="nationalitycode" /></th>
			    <th class="tg-88nc"><fmt:message key="country" /></th>
			    <th class="tg-88nc"><fmt:message key="roomnumber" /></th>
			    <th class="tg-88nc"><fmt:message key="roomclass" /></th>
			    <th class="tg-88nc"><fmt:message key="roomcapacity" /></th>
			    <th class="tg-88nc"><fmt:message key="price" /></th>
			    <th class="tg-88nc"><fmt:message key="from" /></th>
			    <th class="tg-88nc"><fmt:message key="to" /></th>
			    <th class="tg-88nc"><fmt:message key="cost" /></th>
			    <th class="tg-88nc"><fmt:message key="status" /></th>
			  </tr>
			   <jsp:useBean id = "localDateNow" class = "by.epam.hotel.entity.LocalDateNow"/> 
				<c:forEach var="order" items="${sessionData.listAccountFullInfoOrder }" varStatus="status">
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
							    <input type="submit" value="<fmt:message key="cancel" />" />
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
					    		<fmt:message key="cancelled" />
					    	</c:when>
					    	<c:otherwise>
					    		<fmt:message key="successed" />
					    	</c:otherwise>
					    </c:choose>  
					    </td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose> 
	<hr/>
	<c:if test="${sessionData.currentPage != 1}">
        	<c:url value="/controller" var="URL">
				<c:param name="recordsPerPage" value="${sessionData.recordsPerPage}" />
				<c:param name="currentPage" value="${sessionData.currentPage-1}" />
				<c:param name="command" value="toAccountOrders" />
			</c:url> 
           <a href="${URL}"><fmt:message key="previous" /></a>
           
        </c:if>
        <c:forEach begin="1" end="${sessionData.noOfPages}" var="i">
            <c:choose>
                <c:when test="${sessionData.currentPage eq i}">
                    <a><span style="color:blue;font-weight:bold">${i}</span></a>
                </c:when>
                <c:otherwise>
                	<c:url value="/controller" var="URL">
						<c:param name="recordsPerPage" value="${sessionData.recordsPerPage}" />
						<c:param name="currentPage" value="${i}" />
						<c:param name="command" value="toAccountOrders" />
					</c:url> 
                    <a href="${URL}">${i}</a>
                   
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:if test="${sessionData.currentPage lt sessionData.noOfPages}">
       		<c:url value="/controller" var="URL">
				<c:param name="recordsPerPage" value="${sessionData.recordsPerPage}" />
				<c:param name="currentPage" value="${sessionData.currentPage+1}" />
				<c:param name="command" value="toAccountOrders" />
			</c:url> 
            <a href="${URL}"><fmt:message key="next" /></a>
        </c:if>      

	<hr/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="backToClientmain" />
		<input type="submit" value="<fmt:message key="backbutton" />" size="20" />
	</form>
</body>
</html>
</fmt:bundle>