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

<title>Registration Notifications</title>

</head>
<body>
	<h2><%@include file="helpers/welcomeUserName.jsp"%></h2>
	<h2 class="success"><c:out value="${param['success']}" /></h2>
	<h2>List of notifications</h2>
	<div class="CSSTableGenerator">
	<table>
		<tr>
			<td>ACCEPTED</td>
			<td>USER</td>
		</tr>
		<c:forEach items="${notifications}" var="notify">
			<tr>
				<td>${notify.read}</td>
				<td>${notify.user_id.surname} ${notify.user_id.name} ${notify.user_id.pesel}</td>
				<td><a href="<c:url value='/admin/delete-${notify.id}-notification' />" class="delete">
				<img src="<c:url value='/resources/images/error.png' />" alt="Delete" /></a></td>			
			</tr>
		</c:forEach>
	</table>
	</div>
	<br />
	<a href="<c:url value='/admin' />">Back to Main Page</a><br/>
</body>
</html>