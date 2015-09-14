<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<head>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/main.css" />">
<title>User Registration Form</title>

<style>
.errorBlock {
	border: 1px solid;
	margin: 10px 0px;
	padding: 15px 10px 15px 50px;
	background-repeat: no-repeat;
	background-position: 10px center;
	color: #D8000C;
	background-color: #FFBABA;
	background-image: url('resources/error.png');
	background-size: 3%;
}
}
</style>

</head>

<body>
<h2><%@include file="helpers/welcomeUserName.jsp"%></h2>
	<h2>New Visit Form</h2>

	<form:errors path="visit" class="errorBlock" />
	<c:url var="postUrl"
		value="/visits/new/choose-date" />

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
				<td><form:select name="doctor" path="doctor.id"
						style="width: 200px;">
						<option value="-1">SELECT</option>
						<c:forEach items="${doctors}" var="doctor">
							<option value="${doctor.id}">${doctor.name}</option>
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
	<br /> Go back to
	<a href="<c:url value='/visits/list' />">List of All Visits</a>
</body>
</html>