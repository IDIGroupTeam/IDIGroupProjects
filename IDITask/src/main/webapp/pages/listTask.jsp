<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
<title>Danh sách công việc</title>
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
	<a href="${url}/addNewTask"><button
			class="btn btn-primary btn-sm">Tạo việc mới</button></a>					
	<br />
	<br />
	<div class="table-responsive">		
		<table class="table table-striped">
			<tr>
				<th>Mã việc</th>				
				<th>Tên việc</th>
				<th>Của phòng</th>
				<th>Giao cho</th>
				<th>Trạng thái</th>
				<th>Mức độ</th>
				<th>Ngày cập nhật</th>				
			</tr>
			<c:forEach var="task" items="${tasks}">
				<tr>
					<td>${task.taskId}</td>
					<td><a href="/IDITask/editTask?taskId=${task.taskId}">${task.taskName}</a></td>
					<td>${task.area}</td>
					<c:if test="${task.ownedBy == 0}">
						<td>Chưa giao cho ai</td>
					</c:if>
					<c:if test="${task.ownedBy > 0}">
						<td>${task.ownedBy}</td>
					</c:if>
					<td>${task.status}</td>
					<td>${task.priority}</td>
					<td>${task.updateTS}</td>			
				</tr>
			</c:forEach>
		</table>
		<c:if test="${not empty message}">
			<div class="alert alert-success">${message}</div>
		</c:if>
	</div>
</body>
</html>