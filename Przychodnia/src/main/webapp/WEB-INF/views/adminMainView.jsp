<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/main.css" />">
<title>Admin panel:</title>


</head>
<body>
<h2 id="welcomeUser"><%@include file="helpers/welcomeUserName.jsp" %></h2>
<h2 class="success"><c:out value="${param['success']}" /></h2>
<h2>Choose operation:</h2>
	<div id="list">
		<ul>
			<li><a href="<c:url value='/admin/notifications/' />">Notifications</a></li>
			<li><a href="<c:url value='/admin/clinics/' />">Clinics</a></li>
			<li><a href="<c:url value='/admin/patients/' />">Patients list</a></li>
			<li><a href="<c:url value='/admin/doctors/' />">Doctor lists</a></li>
			<li><a href="<c:url value='/admin/clinics/assign/' />">add Doctor to Clinic</a></li>
		</ul>
	</div>
</body>
</html>