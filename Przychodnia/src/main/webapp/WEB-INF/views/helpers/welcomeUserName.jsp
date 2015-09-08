<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<head>
<script>
	function formSubmit() {
		document.getElementById("logoutForm").submit();
	}
</script>
<style>
#welcome {
	/*     border: 1px solid black; */
	width: 500px;
	text-align: right;
	float: right;
	font-size: 20px;
}
</style>
</head>
<body>
	<div id="welcome">
		<c:url value="/logout" var="logoutUrl" />

		Welcome, <span style="color: silver;">${pageContext.request.userPrincipal.name}</span>
		<br /> <a href="<c:url value="/user/edit-${pageContext.request.userPrincipal.name}-user" /> "> Profile</a> <a
			href="javascript:formSubmit()"> Logout</a>

		<!-- csrt support -->
		<form action="${logoutUrl}" method="post" id="logoutForm">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form>
	</div>
</body>
</html>