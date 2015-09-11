<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<script src="<c:url value="/resources/jquery/jquery-1.11.3.js" />" ></script>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/main.css" />">
 <script type="text/javascript">
 $(document).ready(function () {
	 $('.delete').click(function(event) {
		    event.preventDefault();
		    var r=confirm("Are you sure you want to delete?");
		    if (r==true)   {  
		       window.location = $(this).attr('href');
		    }

		});
 });
 </script>

<title>List of Clinics</title>

<style>
tr:first-child {
	font-weight: bold;
	background-color: #C6C9C4;
}
</style>

</head>
<body>
	<h2><%@include file="helpers/welcomeUserName.jsp"%></h2>
	<h2 class="success"><c:out value="${param['success']}" /></h2>
	<h2>List of Clinics</h2>
	<div class="CSSTableGenerator" style="width: 400px;">
	<table>
		<tr>
			<td>Clinic Name</td>
			<td/>
		</tr>
		<c:forEach items="${clinics}" var="clinic">
			<tr>
				<td width="300px"><a href="<c:url value='/admin/clinics/edit-${clinic.id}-${clinic.name}-clinic' />">${clinic.name}</a></td>
				<td class="remove"><a href="<c:url value='/admin/clinics/delete-${clinic.id}-${clinic.name}-clinic' />" class="delete"></a></td>
				
			</tr>
		</c:forEach>
	</table>
	</div>
	<br />
	<a href="<c:url value='/user/new-1' />">Add new doctor</a><br/>
	<a href="<c:url value='/user/new-2' />">Add new administrator</a><br/>
	<a href="<c:url value='/admin/clinics/new-clinic' />">Add new clinic</a><br/>
	<a href="<c:url value='/admin/clinics/assign/' />">Add Doctor to Clinic</a>
	<br/><br/>
	<a href="<c:url value='/admin/' />">Back to Main Page</a>
	
</body>
</html>