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
	<div class="editClinic">
		<%@include file="adminNewClinic.jsp"%>
	</div>
	<div class="clinicDoctors">
<%-- 		<%@include file="adminDoctorsClinicAssignment.jsp"%> --%>
	</div>


</body>
</html>