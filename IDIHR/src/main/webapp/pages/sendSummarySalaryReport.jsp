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
	<a href="${pageContext.request.contextPath}/salary/prepareSummarySalary">
	<button	class="btn btn-lg btn-primary btn-sm">Quay lại thống kê lương</button></a>		
	<c:if test="${not empty message}">		
		<a href="${pageContext.request.contextPath}/salary/viewPDF?fileName=${link}" target="_blank"><button	class="btn btn-lg btn-primary btn-sm">Xem và In</button></a>			
		<br/><br/>
	</c:if>		
	<form:form action="sendSummarySalaryReport" modelAttribute="sendForm"
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
<%-- 		<form:input type="hidden" path="sendTo" value="${sendForm.sendTo}" /> --%>
		
		<table class="table table-bordered table-hover">
			<tr>
				<td title="Nhập địa chỉ email của người nhận ví dụ: bcsidigroup@gmail.com. Các email cách nhau bằng dấu phẩy , ">Gửi tới:</td>
				<td title="Nhập địa chỉ email của người nhận ví dụ: bcsidigroup@gmail.com. Các email cách nhau bằng dấu phẩy , "><form:input path="sendTo" class="form-control"/></td>
			</tr>
			<tr>
				<td>Tiêu đề:</td>
				<td>${title}</td>
			</tr>
			<tr>
				<td>Nội dung:</td>
				<td>Gửi file '${fileName}'</td>
			</tr>
		</table>
		
		<c:if test="${empty validateEmail}">
			<input class="btn btn-lg btn-primary btn-sm" type="submit"	value="Gửi mail" /> &nbsp;
		</c:if>		
	</form:form>
</body>
</html>