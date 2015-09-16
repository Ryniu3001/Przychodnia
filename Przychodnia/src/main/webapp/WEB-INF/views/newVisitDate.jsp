<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>

<head>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/main.css" />">
<title>User Registration Form</title>

</head>

<body>
<h2><%@include file="helpers/welcomeUserName.jsp"%></h2>
	<h2>New Visit Form</h2>

	<div class="errorBlockDiv">
	<form:errors path="visit" class="errorBlock" />
	</div>
	<c:url var="postUrl" value="/visits/new/" />

	<form:form method="POST" modelAttribute="visit" action="${postUrl}">

		<form:input type="hidden" path="id" id="id" />
		<table>

			<tr>
				<td><label for="patient">Patient: </label></td>
				<%-- 				<td><form:input value="${patient.name} ${patient.surname} ${patient.pesel}" path="" disabled="true"/></td> --%>
				<td><form:input path="patient" id="patient" disabled="true"
						value="${patient.name} ${patient.surname} ${patient.pesel}" /></td>
				<form:input type="hidden" path="patient.id" id="patient.id" value="${patient.id}" />
			</tr>

			<tr>
				<td><label for="clinic">Clinic: </label></td>
				<%-- 				<td><form:input value="${doctor.name} " path="" disabled="true"/></td> --%>
				<td><form:input path="clinic" id="clinic" disabled="true"
						value="${clinic.name}" /></td>
				<form:input type="hidden" path="clinic.id" id="clinic.id" value="${clinic.id}" />
			</tr>

			<tr>
				<td><label for="doctor">Doctor: </label></td>
				<td><form:input path="doctor" id="doctor" disabled="true"
						value="${doctor.name}" /></td>
				<form:input type="hidden" path="doctor.id" id="doctor.id" value="${doctor.id}" />
			</tr>

			<tr>
				<td><label for="datee">Availability: </label></td>
				<td><form:select name="datee" size="5" path="datee" style="padding: 10px;">
						<c:forEach items="${dates}" var="date">
							<option><fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${date}"/></option>
						</c:forEach>
					</form:select></td>
			</tr>
			
			<tr>
				<td colspan="3"><c:choose>
						<c:when test="${edit}">
							<input type="submit" value="Update" />
						</c:when>
						<c:otherwise>
							<input type="submit" value="Register" />
						</c:otherwise>
					</c:choose></td>
			</tr>
		</table>
	</form:form>

	<br />
	<br /> 
	<a href="<c:url value='/visits/list' />">List of All Visits</a> <br/>
	<a href="<c:url value='/' />">Back to Main Page</a>
</body>
</html>