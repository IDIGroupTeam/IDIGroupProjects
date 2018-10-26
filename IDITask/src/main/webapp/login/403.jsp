<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<h1>HTTP Status 403 - Access is denied</h1>
    <h1> ${username} </h1>
    <h1> ${password} </h1>
	<c:choose>
		<c:when test="${empty username}">
			<h2>Bạn không có quyền truy cập vào trang này!!</h2>
		</c:when>
		<c:otherwise>
			<h2>Username : ${username} <br/>Bạn không có quyền truy cập vào trang này!</h2>
		</c:otherwise>
	</c:choose>

</body>
</html>