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
<fmt:bundle basename="resource.i18n.interface" prefix="allclasses.">
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
		<c:when test="${fn:length(sessionData.roomClassList)==0}">
			<fmt:message key="noclassmessage" />
		</c:when>
		<c:otherwise>
			<table class="tg">
			  <tr>
			    <th class="tg-88nc"><fmt:message key="classid" /></th>
			    <th class="tg-88nc"><fmt:message key="status" /></th>
			    <th class="tg-88nc"><fmt:message key="removerestore" /></th>
			  </tr>
				<c:forEach var="roomclass" items="${sessionData.roomClassList }" varStatus="status">
					<tr>
						<td class="tg-c3ow">${roomclass.classId }</td>
					    <td>   
					    <c:choose>
					    	<c:when test="${roomclass.removed }">
					    		<fmt:message key="removed" />
					    	</c:when>
					    	<c:otherwise>
					    		
					    	</c:otherwise>
					    </c:choose>  
					    </td>
					    <td>   
					    <c:choose>
					   		<c:when test="${roomclass.removed }">
						    <form action="${pageContext.request.contextPath}/controller" method="post" >
					    		<input type="hidden" name="command" value="changeClassRemoved" />
							    <input type="hidden" name="classIndex" value="${status.count }" />
							    <input type="submit" value="<fmt:message key="restore" />" />
						    </form>
					    	</c:when>
					    	<c:otherwise>
					    		<form action="${pageContext.request.contextPath}/controller" method="post" >
					    		<input type="hidden" name="command" value="changeClassRemoved" />
							    <input type="hidden" name="classIndex" value="${status.count }" />
							    <input type="submit" value="<fmt:message key="remove" />" />
						    </form>
					    	</c:otherwise>
					    </c:choose> 
					    ${errorChangeClassRemovedMessage } 
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
			<c:param name="command" value="toAllClasses" />
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
					<c:param name="command" value="toAllClasses" />
				</c:url> 
                <a href="${URL}">${i}</a>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    <c:if test="${sessionData.currentPage lt sessionData.noOfPages}">
   		<c:url value="/controller" var="URL">
			<c:param name="recordsPerPage" value="${sessionData.recordsPerPage}" />
			<c:param name="currentPage" value="${sessionData.currentPage+1}" />
			<c:param name="command" value="toAllClasses" />
		</c:url> 
        <a href="${URL}"><fmt:message key="next" /></a>
    </c:if>  
	<hr/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="backToAdminClasses" />
		<input type="submit" value="<fmt:message key="backbutton" />" size="20" />
	</form>
</body>
</html>
</fmt:bundle>