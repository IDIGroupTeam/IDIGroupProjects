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
	<a href="${url}/prepareReport"><button class="btn btn-primary btn-sm">Lựa chọn lại thông tin cần báo cáo</button></a>
	<c:if test="${tasks.size() > 0}">
		<a href="${url}/exportToPDF?fDate=${reportForm.fromDate}&tDate=${reportForm.toDate}&eName=${tasks[0].ownerName}&dept=${reportForm.department}&eId=${reportForm.employeeId}"><button class="btn btn-primary btn-sm">Export ra file PDF và gửi báo cáo</button></a>
	</c:if>	
	<br />
	<h3>Báo cáo công việc từ ngày ${reportForm.fromDate} đến ngày ${reportForm.toDate}
		<c:if test="${reportForm.employeeId > 0}">
			của ${tasks[0].ownerName}
       	</c:if>	
		<c:if test="${reportForm.department != 'all'}"> phòng ${reportForm.department}</c:if>
	</h3>
	<br />
	<input type="hidden" name="employeeName" value="${tasks[0].ownerName}">
	<table class="table table-striped">
			<tr>
				<th nowrap="nowrap">Mã việc</th>
				<th>Tên việc</th>
				<th>Người làm</th>
				<th>Trạng thái</th>				
				<th nowrap="nowrap">Cập nhật gần nhất</th>
				<th nowrap="nowrap">Ngày phải xong</th>
				<th>Nhận xét đánh giá</th>
			</tr>
			<c:forEach var="task" items="${tasks}">
				<tr>
					<td>${task.taskId}</td>
					<td>${task.taskName}</td>
					<c:if test="${task.ownedBy == 0}">
						<td nowrap="nowrap">Chưa giao cho ai</td>
					</c:if>
					<c:if test="${task.ownedBy > 0}">
						<td nowrap="nowrap">${task.ownerName}</td>
					</c:if>
					<td>${task.status}</td>
					<td nowrap="nowrap">${task.updateTS}</td>
					<%-- <td><fmt:formatDate pattern="dd-MM-yyyy" value="${task.dueDate}" /></td> --%>
					<td>${task.dueDate}</td>
					<td>${task.reviewComment}</td>
				</tr>
			</c:forEach>
		</table>

		<c:if test="${tasks.size() < 1}">
			<div class="alert alert-success">Không có công việc nào được làm trong thời gian và điều kiện như trên!
			Vui lòng chọn lại thông tin ...
			</div>
		</c:if>
</body>
</html>