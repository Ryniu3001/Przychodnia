<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<script src="<c:url value="/resources/jquery/jquery-1.11.3.js" />"></script>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/main.css" />">
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$('.delete')
								.click(
										function(event) {
											event.preventDefault();
											var r = confirm("Are you sure you want to delete user and notification?");
											if (r == true) {
												window.location = $(this).attr(
														'href');
											}

										});
					});
</script>

<title>Registration Notifications</title>

</head>
<body>
	<h2><%@include file="helpers/welcomeUserName.jsp"%></h2>
	<h2 class="success">
		<c:out value="${param['success']}" />
	</h2>
	<h2>List of notifications</h2>
	<div class="CSSTableGenerator">
		<table>
			<tr>
				<td>Accept</td>
				<td>User</td>
				<td>Remove</td>
			</tr>
			<c:forEach items="${notifications}" var="notify">
				<tr>
					<c:url var="deleteUrl" value="/admin/delete-${notify.id}-${notify.user_id.pesel}-notification" />
					<c:url var="acceptUrl" value="/admin/accept-${notify.id}-${notify.user_id.pesel}-notification" />
					<td id="notifyAccept"><form:form method="POST"
							action="${acceptUrl}">
							<input type="submit" value="" class="acceptSubmit">
						</form:form></td>
					<td>${notify.user_id.surname}${notify.user_id.name}
						${notify.user_id.pesel}</td>
					<%-- 				<td class="remove"><a href="<c:url value='/admin/delete-${notify.id}-${notify.user_id.pesel}-notification' />" class="delete"></a></td>	 --%>
					<td class="remove"><form:form method="POST"
							action="${deleteUrl}">
							<input type="submit" value="" class="deleteSubmit">
						</form:form></td>

				</tr>
			</c:forEach>
		</table>
	</div>
	<br />
	<a href="<c:url value='/admin' />">Back to Main Page</a>
	<br />
</body>
</html>