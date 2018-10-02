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
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<title>Approve Cancel</title>
</head>
<body>
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
		</tr>
		<tr>
			<td class="tg-c3ow">${orderToCancel.id }</td>
			<td class="tg-c3ow">${orderToCancel.client.firstName }</td>
			<td class="tg-c3ow">${orderToCancel.client.lastName }</td>
			<td class="tg-c3ow">${orderToCancel.client.passport }</td>
			<td class="tg-c3ow">${orderToCancel.client.nationality }</td>
			<td class="tg-c3ow">${orderToCancel.nationality.country }</td>
			<td class="tg-c3ow">${orderToCancel.room.number }</td>
			<td class="tg-c3ow">${orderToCancel.room.classRoom }</td>
			<td class="tg-c3ow">${orderToCancel.room.capacity }</td>
			<td class="tg-c3ow">${orderToCancel.room.price }</td>
			<td class="tg-c3ow">${orderToCancel.from }</td>
			<td class="tg-c3ow">${orderToCancel.to }</td>
			<td class="tg-c3ow">${orderToCancel.cost }</td>
		</tr>
	</table>
	<br />
	<hr />
	SUM TO RETURN: <c:out value="${returnedSum }" />
	<br />
	<br />
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="returnedSum" value="${returnedSum }" />
		<input type="hidden" name="orderId" value="${orderToCancel.id }" />
		<input type="hidden" name="command" value="approveOrderCancel" />
		<input type="submit" value="approve CANCEL" size="20" />
	</form>
	${errorOrderCancelMessage }
	<br />
	<form action="${pageContext.request.contextPath}/controller"
		method="post">
		<input type="hidden" name="command" value="backToAccountOrders" />
		<input type="submit" value="Back" size="20" />
	</form>
</body>
</html>