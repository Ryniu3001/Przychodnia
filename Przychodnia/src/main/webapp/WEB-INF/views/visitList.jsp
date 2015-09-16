<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<script src="<c:url value="/resources/jquery/jquery-1.11.3.js" />"></script>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/main.css" />">
<script type="text/javascript">
	$(document).ready(function() {
		$('.delForm').submit(function(){
			var c = confirm("Are you sure you want to delete?");
			return c;
		});
	});
</script>

<title>Registration Notifications</title>

</head>
<body>
	<h2><%@include file="helpers/welcomeUserName.jsp"%></h2>
	<h2 class="success">
		<c:out value="${param['success']}" />
	</h2>
	<h2>Visits</h2>
	<div class="CSSTableGenerator">
		<table>
			<tr>
				<td>Clinic</td>
				<td>Doctor</td>
				<td>Date</td>
				<td>Confirm/Cancel</td>
			</tr>
		<c:forEach items="${visits}" var="visit">
		<c:url var="deleteUrl" value="/visits/remove-${visit.id}" />
			<tr>
				<td>${visit.clinic.name}</td>
				<td>${visit.doctor.name} ${visit.doctor.surname}</td>
				<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${visit.datee}"/></td>
				<td class="remove" style="width: 10%">
					<form:form method="POST" action="${deleteUrl}" class="delForm">
						<input type="submit" value="" alt="asasd" class="deleteSubmit">
					</form:form>
				</td>
				</tr>
		</c:forEach>
		</table>
	</div>
	<br />
	<a href="<c:url value='/admin' />">Back to Main Page</a>
	<br />
</body>
</html>