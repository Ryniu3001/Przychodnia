<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>University Enrollments</title>
 
    <style>
        tr:first-child{
            font-weight: bold;
            background-color: #C6C9C4;
        }
    </style>
 
</head>
 
 
<body>
<h2><%@include file="helpers/welcomeUserName.jsp" %></h2>
    <h2>List of ${type}s</h2>  
    <table>
        <tr>
            <td>NAME</td><td>Surname</td><td>Pesel</td><td>SSN</td><td></td>
        </tr>
        <c:forEach items="${users}" var="user">
            <tr>
            <td><a href="<c:url value='/user/edit-${user.username}-user' />">${user.username}</a></td>
            <td>${user.surname}</td>
            <td>${user.city}</td>
            <td>${user.pesel}</td>
            <td><a href="<c:url value='/user/delete-${user.pesel}-user' />">delete</a></td>
            </tr>
        </c:forEach>
    </table>
    <br/>
    <input type="text" name="type" value="${type}" />
    <a href="<c:url value='/user/new-${type}' />">Add new ${type}</a>
</body>
</html>