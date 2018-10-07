<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Send Message</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/controller" method="post" >
	<input type="hidden" name="command" value="sendMessage" />
   		Subject: <input type="text" name="subject" /><br/>
	    Text: <input type="text" name="text" /><br/>
	    <input type="submit" value="Send Message" />
    </form>
    ${errorSendMessageMessage }
  	<hr />
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="backToAllEmails" />
		<input type="submit" value="Back" size="20" />
	</form>
</body>
</html>