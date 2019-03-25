<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>

<title>Báo cáo công việc</title>
<style>
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

tr:nth-child(even) {
	background-color: #E8E3E3;
}
</style>
</head>
<body>
	<a href="${url}/prepareReport"><button
			class="btn btn-primary btn-sm">Lựa chọn lại thông tin cần
			báo cáo</button></a>
	<br />
	<h3>
		Báo cáo công việc từ ngày ${reportForm.fromDate} đến ngày
		${reportForm.toDate}
		<c:if test="${reportForm.employeeId > 0}">
			của ${reportForm.employeeName}
       	</c:if>
		<c:if test="${reportForm.department != 'all'}"> phòng ${reportForm.department}</c:if>
	</h3>
	<br />	
	<c:if test="${not empty isOpen}">
		<div class="alert alert-warning">
			Vui lòng tắt file báo cáo đang mở trước khi tạo lại báo cáo! 
		</div>
	</c:if>
	<c:if test="${empty isOpen}">
		<div class="alert alert-success">
			Báo cáo công việc đã được export ra file PDF và lưu tại thư mục ${path} 
		</div>
	</c:if>
	<a href="${url}/sendReportForm?fDate=${reportForm.fromDate}&tDate=${reportForm.toDate}&eName=${reportForm.employeeName}&dept=${reportForm.department}&eId=${reportForm.employeeId}"><button class="btn btn-primary btn-sm">Gửi báo cáo</button></a>
</body>
</html>