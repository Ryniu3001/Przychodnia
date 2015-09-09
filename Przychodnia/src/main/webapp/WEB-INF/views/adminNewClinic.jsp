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
	<%-- 	<h2><%@include file="helpers/welcomeUserName.jsp"%></h2> --%>
	<h2 class="success">
		<c:out value="${param['success']}" />
	</h2>
	<c:choose>
		<c:when test="${edit}">
			<h2>Edit Clinic Form</h2>
		</c:when>
		<c:otherwise>
			<h2>New Clinic Form</h2>
		</c:otherwise>
	</c:choose>

	<form:form method="POST" modelAttribute="clinic">

		<form:input type="hidden" path="id" id="id" />
		<table>
			<tr></tr>
			<tr>
				<td><label for="name">Name: </label></td>
				<td><form:input path="name" id="name" /></td>
				<td><form:errors path="name" cssClass="error" /></td>
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