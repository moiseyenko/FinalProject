<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Super Admin</title>
</head>
<body>
	<c:set target="${sessionData }" property="role" value="ADMIN" />
	<c:set target="${sessionData }" property="login" value="superadmin" />
	<jsp:forward page="/jsp/admin/adminmain.jsp" />
</body>
</html>