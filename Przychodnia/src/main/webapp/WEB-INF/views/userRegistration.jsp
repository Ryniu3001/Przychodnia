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

	<form:errors path="user" class="errorBlock" />

	<form:form method="POST" modelAttribute="user">

		<form:input type="hidden" path="id" id="id" />
		<table>
			<tr></tr>
			<tr>
				<td><label for="name">Name: </label></td>
				<td><form:input path="name" id="name" /></td>
				<td><form:errors path="name" cssClass="error" /></td>
			</tr>

			<tr>
				<td><label for="surname">Surname: </label></td>
				<td><form:input path="surname" id="surname" /></td>
				<td><form:errors path="surname" cssClass="error" /></td>
			</tr>

			<tr>
				<td><label for="salary">City: </label></td>
				<td><form:input path="city" id="city" /></td>
				<td><form:errors path="city" cssClass="error" /></td>
			</tr>

			<tr>
				<td><label for="pesel">PESEL: </label></td>
				<td><form:input path="pesel" id="pesel" /></td>
				<td><form:errors path="pesel" cssClass="error" /></td>
			</tr>

			<tr>
				<td><label for="ssn">ZIP Code: </label></td>
				<td><form:input path="zip_code" id="zip_code" /></td>
				<td><form:errors path="zip_code" cssClass="error" /></td>
			</tr>

			<tr>
				<td><label for="ssn">House Nr: </label></td>
				<td><form:input path="house_nr" id="house_nr" /></td>
				<td><form:errors path="house_nr" cssClass="error" /></td>
			</tr>

			<tr>
				<td><label for="ssn">Username: </label></td>
				<td><form:input path="username" id="username" /></td>
				<td><form:errors path="username" cssClass="error" /></td>
			</tr>

			<tr>
				<td><label for="ssn">Password: </label></td>
				<td><form:input path="password" id="password" /></td>
				<td><form:errors path="password" cssClass="error" /></td>
			</tr>
			<c:choose>
				<c:when test="!${edit}">
					<tr>
						<td><label for="ssn">Account Type: </label></td>
						<td><form:input path="type" id="type" /></td>
						<td><form:errors path="type" cssClass="error" /></td>
					</tr>
				</c:when>
			</c:choose>
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
	<br />
	<c:choose>
		<c:when test="${edit == false}">
			Go back to <a href="<c:url value='/login' />">Login Page</a>
		</c:when>
		<c:otherwise>
			Go back to <a href="<c:url value='/' />">Main Page</a>
		</c:otherwise>

	</c:choose>
</body>
</html>