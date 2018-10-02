<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Records Per Page</title>
</head>
<body>
<h1>Show orders</h1>

<form action="${pageContext.request.contextPath}/controller" method="post">
	<input type="hidden" name="command" value="toAllOrders" /> 
    <input type="hidden" name="currentPage" value="1">
    <label for="records">Select records per page:</label>
    <select name="recordsPerPage"> 
        <option value="5">5</option> 
        <option value="10" selected>10</option>
        <option value="15">15</option>
    </select>
    <input type="submit" value="Submit" />
</form>
<hr/>
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="backToAdminmain" />
		<input type="submit" value="Back" size="20" />
	</form>


</body>
</html>