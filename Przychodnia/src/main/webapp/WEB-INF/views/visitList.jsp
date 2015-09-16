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
				<c:choose>
					<c:when test="${doctor == true}">
						<td>Patient</td>
					</c:when>
					<c:otherwise>
						<td>Doctor</td>
					</c:otherwise>
				</c:choose>
				<td>Date</td>
				<td>Confirm/Cancel</td>
			</tr>
		<c:forEach items="${visits}" var="visit">
		<c:url var="deleteUrl" value="/visits/remove-${visit.id}" />
			<tr>
				<td>${visit.clinic.name}</td>
				<c:choose>
					<c:when test="${doctor == true}">
						<td>${visit.patient.name} ${visit.patient.surname}</td>
					</c:when>
					<c:otherwise>
						<td>${visit.doctor.name} ${visit.doctor.surname}</td>
					</c:otherwise>
				</c:choose>
				<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${visit.datee}"/></td>
				<td class="remove" style="width: 10%">
					<form:form method="POST" action="${deleteUrl}" class="delForm">
						<input type="submit" value="" class="deleteSubmit">
					</form:form>
				</td>
				</tr>
		</c:forEach>
		</table>
	</div>
	<br />
	<a href="<c:url value='/' />">Back to Main Page</a>
	<br />
</body>
</html>