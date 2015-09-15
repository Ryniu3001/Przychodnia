<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
	<c:url var="postUrl" value="/visits/new/choose-doctor" />
	<form:form method="POST" modelAttribute="visit" action="${postUrl}">

		<form:input type="hidden" path="id" id="id" />
		<table>

			<tr>
				<td><label for="patient.id">Patient: </label></td>
				<td><form:input value="${patient.name} ${patient.surname} ${patient.pesel}" path="" disabled="true"/></td>
				<form:input type="hidden" path="patient.id" id="patient.id" value="${patient.id}" />
			</tr>

			<tr>
				<td><label for="clinic">Clinic: </label></td>
				<td><form:select name="clinic" path="clinic.id" style="width: 200px;">
						<option value="-1">SELECT</option>
						<c:forEach items="${clinics}" var="clinic">
							<option value="${clinic.id}">${clinic}</option>
						</c:forEach>
				</form:select></td>
				<td><form:errors path="clinic" cssClass="error" /></td>
			</tr>

<%-- 			<tr>
				<td><label for="datee">datee: </label></td>
				<td><form:input path="datee" id="datee" /></td>
				<td><form:errors path="datee" cssClass="error" /></td>
			</tr> --%>

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