<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
<title>Gửi thông tin thống kê lương chi tiết</title>
</head>
<body>
	<a href="${pageContext.request.contextPath}/salary/prepareSummarySalary">
	<button	class="btn btn-lg btn-primary btn-sm">Quay lại thống kê lương</button></a>	
	<br />
	<br />	
	<c:if test="${not empty message}">
		<div class="alert alert-success">${message}</div>
	</c:if>		
	<c:if test="${not empty warning}">
		<div class="alert alert-warning">${warning}</div>
	</c:if>
	<c:if test="${not empty error}">
		<div class="alert alert-danger">${error}</div>
	</c:if>	
</body>
</html>