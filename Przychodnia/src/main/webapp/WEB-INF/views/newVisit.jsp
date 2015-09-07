<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<head>


<style>
.error {
	color: #ff0000;
}

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

	<h2>Registration Form</h2>

	<form:errors path="visit" class="errorBlock" />

	<form:form method="POST" modelAttribute="visit">

		<form:input type="hidden" path="id" id="id" />
		<table>

			<tr>
				<td><form:label path="patient.id">Patient:</form:label></td>
				<td><form:select path="patient.id" cssStyle="width: 150px;">
						<option value="-1">Select a patient</option>
						<c:forEach items="${users}" var="user">
							<option value="${user.id}">${user.surname}${user.name}
								${user.pesel}</option>
						</c:forEach>
					</form:select></td>
			</tr>

			<%-- 						<tr>
							<td><label for="patient.id">Patient: </label></td>
							<td><form:input path="patient.id" id="patient.id" /></td>
							<td><form:errors path="patient.id" cssClass="error" /></td>
						</tr> --%>

			<tr>
				<td><label for="doctor.id">Doctor: </label></td>
				<td><form:input path="doctor.id" id="doctor.id" /></td>
				<td><form:errors path="doctor.id" cssClass="error" /></td>
			</tr>

			<tr>
				<td><label for="datee">datee: </label></td>
				<td><form:input path="datee" id="datee" /></td>
				<td><form:errors path="datee" cssClass="error" /></td>
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