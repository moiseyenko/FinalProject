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
<fmt:bundle basename="resource.i18n.interface" prefix="allrooms.">
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
		<c:when test="${fn:length(sessionData.roomList)==0}">
			<fmt:message key="noroommessage" />
		</c:when>
		<c:otherwise>
			<table class="tg">
			  <tr>
			    <th class="tg-88nc"><fmt:message key="number" /></th>
			    <th class="tg-88nc"><fmt:message key="roomclass" /></th>
			    <th class="tg-88nc"><fmt:message key="capacity" /></th>
			    <th class="tg-88nc"><fmt:message key="price" /></th>
			    <th class="tg-88nc"><fmt:message key="status" /></th>
			    <th class="tg-88nc"><fmt:message key="removerestore" /></th>
			    <th class="tg-88nc"> </th>
			  </tr>
			  <c:forEach var="room" items="${sessionData.roomList }" varStatus="status">
					<tr>
						<td class="tg-c3ow">${room.number }</td>
					    <td class="tg-c3ow">${room.classRoom }</td>
					    <td class="tg-c3ow">${room.capacity }</td>
					    <td class="tg-c3ow">${room.price }</td>
					    <td>   
					    <c:choose>
					    	<c:when test="${room.removed }">
					    		<fmt:message key="removed" />
					    	</c:when>
					    	<c:otherwise>
					    		
					    	</c:otherwise>
					    </c:choose>  
					    </td>
					    <td>   
					    <c:choose>
					   		<c:when test="${room.removed }">
						    <form action="${pageContext.request.contextPath}/controller" method="post" >
					    		<input type="hidden" name=command value="changeRoomRemoved" />
							    <input type="hidden" name="roomIndex" value="${status.count }" />
							    <input type="submit" value="<fmt:message key="restore" />" />
						    </form>
					    	</c:when>
					    	<c:otherwise>
					    		<form action="${pageContext.request.contextPath}/controller" method="post" >
					    		<input type="hidden" name=command value="changeRoomRemoved" />
							    <input type="hidden" name="roomIndex" value="${status.count }" />
							    <input type="submit" value="<fmt:message key="remove" />" />
						    </form>
					    	</c:otherwise>
					    </c:choose>
						${errorChangeRoomRemovedMessage }  
					    </td>
					    <td>
					    <form action="${pageContext.request.contextPath}/controller" method="post" >
				    		<input type="hidden" name=command value="changeRoom" />
						    <input type="hidden" name="roomIndex" value="${status.count }" />
						    <input type="submit" value="<fmt:message key="change" />" />
					    </form>
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
			<c:param name="command" value="toAllRooms" />
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
					<c:param name="command" value="toAllRooms" />
				</c:url> 
                <a href="${URL}">${i}</a>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    <c:if test="${sessionData.currentPage lt sessionData.noOfPages}">
   		<c:url value="/controller" var="URL">
			<c:param name="recordsPerPage" value="${sessionData.recordsPerPage}" />
			<c:param name="currentPage" value="${sessionData.currentPage+1}" />
			<c:param name="command" value="toAllRooms" />
		</c:url> 
        <a href="${URL}"><fmt:message key="next" /></a>
    </c:if>  
	<hr/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="backToAdminRooms" />
		<input type="submit" value="<fmt:message key="backbutton" />" size="20" />
	</form>
</body>
</html>
</fmt:bundle>