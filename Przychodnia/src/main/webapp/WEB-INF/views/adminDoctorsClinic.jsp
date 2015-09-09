<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<head>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/main.css" />">
<title>Form</title>
</head>

<body>
	<h2><%@include file="helpers/welcomeUserName.jsp"%></h2>
	<h2 class="success">
		<c:out value="${param['success']}" />
	</h2>
	<h2>FORM</h2>

	<form:form method="POST" modelAttribute="dc">

<%-- 		<form:input type="hidden" path="pk" id="pk" /> --%>
		<table>
			<tr></tr>
			<tr>
				<td><label for="dayOfWeek">dayOfWeek: </label></td>
				<td><form:input path="dayOfWeek" id="dayOfWeek" /></td>
				<td><form:errors path="dayOfWeek" cssClass="error" /></td>
			</tr>
			<tr>
				<td><label for="hourFrom">hourFrom: </label></td>
				<td><form:input path="hourFrom" id="hourFrom" /></td>
				<td><form:errors path="hourFrom" cssClass="error" /></td>
			</tr>
			<tr>
				<td><label for="hourTo">hourTo: </label></td>
				<td><form:input path="hourTo" id="hourTo" /></td>
				<td><form:errors path="hourTo" cssClass="error" /></td>
			</tr>
			<tr>
				<td><label for="contract_Expire">contract_Expire: </label></td>
				<td><form:input path="contract_Expire" id="contract_Expire" /></td>
				<td><form:errors path="contract_Expire" cssClass="error" /></td>
			</tr>
			<tr>
				<td><label for="doctor">Doctor: </label></td>
				<td><form:select name="doctor" path="doctor.id">
						<option value="select">SELECT</option>
						<c:forEach items="${doctors}" var="doctor">
							<option value="${doctor.id}">${doctor}</option>
						</c:forEach>
				</form:select></td>
				<td><form:errors path="doctor" cssClass="error" /></td>
			</tr>
			<tr>
				<td><label for="clinic">Clinic: </label></td>
				<td><form:select name="clinic" path="clinic.id">
						<option value="select">SELECT</option>
						<c:forEach items="${clinics}" var="clinic">
							<option value="${clinic.id}">${clinic}</option>
						</c:forEach>
				</form:select></td>
				<td><form:errors path="clinic" cssClass="error" /></td>
			</tr>
			<tr>
				<td colspan="3"><c:choose>
						<c:when test="${edit}">
							<input class="jq" type="submit" value="Update" />
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
	<a href="<c:url value='/' />">Main Page</a>
</body>
</html>