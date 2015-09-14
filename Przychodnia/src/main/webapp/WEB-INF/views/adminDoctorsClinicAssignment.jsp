<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

<title>List</title>

</head>
<body>
	<h2><%@include file="helpers/welcomeUserName.jsp"%></h2>
	<h2 class="success">
		<c:out value="${param['success']}" />
	</h2>
	<h2>List</h2>
	<div class="CSSTableGenerator">
		<c:set var="count" value="0" scope="page" />
		<table>
			<tr>
				<td>Clinic Name</td>
				<td>Harmonogram</td>
				<td>Remove</td>
			</tr>
			<c:forEach items="${clinics}" var="clinic">
				<tr>
					<c:set var="count" value="0" scope="page" />
					<td rowspan="${clinic.doctorsInClinic.size()}">${clinic.name}</td>

					<c:forEach items="${clinic.doctorsInClinic}" var="doctor">
					<c:url var="deleteUrl" value="/admin/clinics/assignment/delete-${clinic.id}-${doctor.pk.doctor.id}-${doctor.pk.dayOfWeek}" />
						<c:choose>
							<c:when test="${count == 0}">
							
								<td>${doctor}</td>
								<td class="remove"><form:form method="POST"	action="${deleteUrl}" class="delForm">
											<input type="submit" value="" class="deleteSubmit">
										</form:form></td>
								<c:set var="count" value="${count+1}" scope="page" />
							</c:when>
							<c:otherwise>
								<tr>									
									<td>${doctor}</td>
									<td class="remove"><form:form method="POST" action="${deleteUrl}" class="delForm">
											<input type="submit" value="" class="deleteSubmit">
										</form:form></td>
								</tr>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:if test="${count == 0}">
						<td>-</td>
						<td>-</td>
					</c:if>
				</tr>
			</c:forEach>
		</table>
	</div>
	<br/><br/>
	<a href="<c:url value='/admin/clinics/assign/' />">Add Doctor to Clinic</a>
	<br/>
	<a href="<c:url value='/admin/' />">Back to Main Page</a>

</body>
</html>