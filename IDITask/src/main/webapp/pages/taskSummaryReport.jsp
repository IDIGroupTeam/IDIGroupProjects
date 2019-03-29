<%@page import="com.idi.task.form.ReportForm"%>
<%@page import="com.idi.task.common.Utils"%>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>

<title>Báo cáo thống kê khối lượng công việc</title>
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
	background-color: #FAFAFA;
}
</style>
</head>
<body>
	<a href="${url}/prepareReport?fDate=${reportForm.fromDate}&tDate=${reportForm.toDate}&eName=${tasks[0].ownerName}&dept=${reportForm.department}&eId=${reportForm.employeeId}"><button
			class="btn btn-primary btn-sm">Lựa chọn lại thông tin cần
			thống kê khối lượng công việc</button></a>
	<c:if test="${tasks.size() > 0}">
		<a
			href="${url}/exportToPDF?fDate=${reportForm.fromDate}&tDate=${reportForm.toDate}&eName=${tasks[0].ownerName}&dept=${reportForm.department}&eId=${reportForm.employeeId}"><button
				class="btn btn-primary btn-sm">Export ra file PDF và gửi
				báo cáo</button></a>
	</c:if>
	<br />
	<h3>
		Thống kê khối lượng công việc từ ngày ${reportForm.fromDate} đến ngày
		${reportForm.toDate}
		<c:if test="${reportForm.employeeId > 0}">
			của ${reportForm.employeeName}
       	</c:if>
		<c:if test="${reportForm.department != 'all'}"> phòng ${reportForm.department} làm</c:if>
	</h3>
	<br />
	<input type="hidden" name="employeeName" value="${tasks[0].ownerName}">
	<table class="table table-striped">
		<tr>
			<th nowrap="nowrap">Người làm</th>
			<th nowrap="nowrap">Tổng số</th>
			<th nowrap="nowrap">Mới</th>
			<th nowrap="nowrap">Đang làm</th>
			<th nowrap="nowrap">Tạm dừng</th>
			<th nowrap="nowrap">Hủy bỏ</th>
			<th nowrap="nowrap">Chờ đánh giá</th>
			<th nowrap="nowrap">Đã xong</th>
		</tr>
		<c:forEach var="task" items="${listTaskSummary}">
			<tr>
				<td>${task.employeeName}</td>
				<td><B>${task.taskTotal}</B></td>			
				<td>${task.taskNew}</td>				
				<td>${task.taskInprogess}</td>
				<td>${task.taskStoped}</td>
				<td>${task.taskinvalid}</td>
				<td>${task.taskReviewing}</td>
				<td>${task.taskCompleted}</td>				
			</tr>
		</c:forEach>
	</table>
</body>
</html>