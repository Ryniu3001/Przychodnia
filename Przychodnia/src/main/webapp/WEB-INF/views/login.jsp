<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/main.css" />">
<style>
.error {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;
}

.msg {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
}

#login-box {
	width: 300px;
	padding: 20px;
	margin: 100px auto;
	background: #fff;
	-webkit-border-radius: 2px;
	-moz-border-radius: 2px;
	border: 1px solid #000;
}
</style>
</head>
<body onload='document.loginForm.username.focus();'>
<h2 class="success"><c:out value="${param['success']}" /></h2>
	<h1>Welcome in my medical clinic</h1>

	<div id="login-box">

		<h2>Login with Username and/or Password</h2>

		<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>

		<form name='loginForm'
			action="<c:url value='j_spring_security_check' />" method='POST'>

			<table>
				<tr>
					<td>User:</td>
					<td><input type='text' name='j_username' value=''></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type='password' name='j_password' /></td>
				</tr>
				<tr>
					<td colspan='2'><input name="submit" type="submit"
						value="submit" /></td>
				</tr>
				<tr>
					<td colspan='2'>If you don't have an account <a
						href="<c:url value='/user/new-0' />">Register</a></td>
				</tr>
			</table>

			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form>
	</div>

</body>
</html>