<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>

<style type="text/css">
table {
	font-family: arial, sans-serif;
	border-collapse: collapse;
	width: 100%;
}

td, th {
	border: 1px solid #E8E3E3;
	text-align: left;
	padding: 8px;
}
</style>

<title>${formTitle}</title>
</head>
<body>
	<a href="${pageContext.request.contextPath}/salary/listSalaryDetail?employeeId=${employeeId}">
	<button	class="btn btn-lg btn-primary btn-sm">Quay lại danh sách lương các tháng</button></a>			
	<form:form action="sendSummarySalary" modelAttribute="sendForm"
		enctype="multipart/form-data" method="POST" >
		<br />
		<c:if test="${not empty message}">
			<div class="alert alert-success">${message}</div>
		</c:if>		
		<c:if test="${not empty warning}">
			<div class="alert alert-warning">${warning}</div>
		</c:if>
		<c:if test="${not empty validateEmail}">
			<div class="alert alert-warning">${validateEmail}</div>
			<a href="${pageContext.request.contextPath}/editEmployee?employeeId=${employeeId}">
			<button	class="btn btn-lg btn-primary btn-sm">Quay lại danh sách lương các tháng</button></a>
		</c:if>
		
		<form:input type="hidden" path="fileName" value="${fileName}" />
		<form:input type="hidden" path="sendTo" value="${sendForm.sendTo}" />
		<form:input type="hidden" path="employeeId" value="${sendForm.employeeId}" />
		<table class="table table-bordered table-hover">
			<tr>
				<td>Gửi tới:</td>
				<td>${sendForm.sendTo}</td>
			</tr>
			<tr>
				<td>Tiêu đề:</td>
				<td>${formTitle}</td>
			</tr>
			<tr>
				<td>Nội dung:</td>
				<td>Gửi file '${fileSave}</td>
			</tr>
		</table>
		
		<c:if test="${empty validateEmail}">
			<input class="btn btn-lg btn-primary btn-sm" type="submit"	value="Gửi" /> &nbsp;
		</c:if>		
	</form:form>
</body>
</html>