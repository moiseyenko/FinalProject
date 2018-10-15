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
<fmt:bundle basename="resource.i18n.interface" prefix="allemails.">
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
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="to_all_emails" /> 
	    <input type="hidden" name="currentPage" value="1">
	    <label for="records"><fmt:message key="selectrpp" />:</label>
	    <select id="recordsPerPageId" name="recordsPerPage" onchange="this.form.submit();" >
	        <option value="5">5</option> 
	        <option value="10">10</option>
	        <option value="15">15</option>
	    </select>
	    <script>document.getElementById("recordsPerPageId").value = "${sessionData.recordsPerPage}";</script>  
	</form>
	<hr />
	<form action="${pageContext.request.contextPath}/controller" method="post" >
   		<input type="hidden" name="command" value="subject_text_send" />
	    <input type="submit" value="<fmt:message key="futher" />" />
    </form>
  	<hr />
  <c:choose>
		<c:when test="${fn:length(sessionData.accountList)==0}">
			<fmt:message key="noemailmsg" />
		</c:when>
		<c:otherwise>
			<table class="tg">
			  <tr>
			    <th class="tg-88nc"><fmt:message key="login" /></th>
			    <th class="tg-88nc"><fmt:message key="email" /></th>
			    <th class="tg-88nc"><fmt:message key="admin" /></th>
			    <th class="tg-88nc"><fmt:message key="status" /></th>
			    <th class="tg-88nc"><fmt:message key="addtosend" /></th>
			    <th class="tg-88nc"><fmt:message key="mark" /></th>
			  </tr>
			  <c:forEach var="account" items="${sessionData.accountList }" varStatus="status">
					<tr>
						<td class="tg-c3ow">${account.login }</td>
						<td class="tg-c3ow">${account.email }</td>
						<td>  
					    	<c:choose>
						    	<c:when test="${account.admin }">
						    		<fmt:message key="adminmessage" />
						    	</c:when>
						    	<c:otherwise>
						    		
						    	</c:otherwise>
					    	</c:choose>  
					    </td>
					    <td>   
					    	<c:choose>
						    	<c:when test="${account.removed }">
						    		<fmt:message key="removedmessage" />
						    	</c:when>
						    	<c:otherwise>
						    		
						    	</c:otherwise>
					    	</c:choose>  
					    </td>
					    <td>   
					    	<c:choose>
						    	<c:when test="${sessionData.sendList.contains(account) }">
						    		<fmt:message key="addedmessage" />
						    	</c:when>
						    	<c:otherwise>
						    		
						    	</c:otherwise>
					    	</c:choose>  
					    </td>
					    
					    <td>   
					    <c:choose>
					   		<c:when test="${sessionData.sendList.contains(account) }">
						    <form action="${pageContext.request.contextPath}/controller" method="post" >
					    		<input type="hidden" name="command" value="add_remove_to_sendlist" />
							    <input type="hidden" name="accountIndex" value="${status.count }" />
							    <input type="submit" value="<fmt:message key="removefromsl" />" />
						    </form>
					    	</c:when>
					    	<c:otherwise>
				    		<form action="${pageContext.request.contextPath}/controller" method="post" >
					    		<input type="hidden" name="command" value="add_remove_to_sendlist" />
							    <input type="hidden" name="accountIndex" value="${status.count }" />
							    <input type="submit" value="<fmt:message key="addtosl" />" />
						    </form>
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
				<c:param name="command" value="to_all_emails" />
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
						<c:param name="command" value="to_all_emails" />
					</c:url> 
                    <a href="${URL}">${i}</a>
                   
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:if test="${sessionData.currentPage lt sessionData.noOfPages}">
       		<c:url value="/controller" var="URL">
				<c:param name="recordsPerPage" value="${sessionData.recordsPerPage}" />
				<c:param name="currentPage" value="${sessionData.currentPage+1}" />
				<c:param name="command" value="to_all_emails" />
			</c:url> 
            <a href="${URL}"><fmt:message key="next" /></a>
        </c:if>      
	<hr/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="back_to_admin_main" />
		<input type="submit" value="<fmt:message key="backbutton" />" size="20" />
	</form>
</body>
</html>
</fmt:bundle>