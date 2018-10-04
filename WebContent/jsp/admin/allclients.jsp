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
<title>All Clients</title>
</head>
<body>
<jsp:include page="/loginlogout" />
	<hr />

  
  <c:choose>
		<c:when test="${fn:length(sessionData.clientList)==0}">
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
			    <th class="tg-88nc">Status</th>
			    <th class="tg-88nc">Punish/Justify</th>
			  </tr>
			  <c:forEach var="client" items="${sessionData.clientList }" varStatus="status">
					<tr>
						<td class="tg-c3ow">${client.id }</td>
					    <td class="tg-c3ow">${client.firstName }</td>
					    <td class="tg-c3ow">${client.lastName }</td>
					    <td class="tg-c3ow">${client.passport }</td>
					    <td class="tg-c3ow">${client.nationality }</td>
					    <td>   
					    <c:choose>
					    	<c:when test="${client.blacklist }">
					    		IN BLACKLIST
					    	</c:when>
					    	<c:otherwise>
					    		
					    	</c:otherwise>
					    </c:choose>  
					    </td>
					    <td>   
					    <c:choose>
					   		<c:when test="${client.blacklist }">
						    <form action="${pageContext.request.contextPath}/controller" method="post" >
					    		<input type="hidden" name=command value="changeBlackList" />
							    <input type="hidden" name="clientIndex" value="${status.count }" />
							    <input type="submit" value="Remove from blacklist" />
						    </form>
					    	</c:when>
					    	<c:otherwise>
					    		<form action="${pageContext.request.contextPath}/controller" method="post" >
					    		<input type="hidden" name=command value="changeBlackList" />
							    <input type="hidden" name="clientIndex" value="${status.count }" />
							    <input type="submit" value="Add to blacklist" />
						    </form>
					    	</c:otherwise>
					    </c:choose> 
					    ${errorChangeBlacklistClientMessage } 
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
			<c:param name="command" value="toAllClients" />
		</c:url> 
       <a href="${URL}">Previous</a>
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
					<c:param name="command" value="toAllClients" />
				</c:url> 
                <a href="${URL}">${i}</a>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    <c:if test="${sessionData.currentPage lt sessionData.noOfPages}">
   		<c:url value="/controller" var="URL">
			<c:param name="recordsPerPage" value="${sessionData.recordsPerPage}" />
			<c:param name="currentPage" value="${sessionData.currentPage+1}" />
			<c:param name="command" value="toAllClients" />
		</c:url> 
        <a href="${URL}">Next</a>
    </c:if>  
	<hr/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="backToAdminmain" />
		<input type="submit" value="Back" size="20" />
	</form>
</body>
</html>