<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
<title>Thông tin ngày nghỉ, công tác, làm thêm ...</title>
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
	<a href="${url}/timekeeping/addLeaveInfo"><button class="btn btn-lg btn-primary btn-sm">Thêm mới </button></a>
	<a href="${url}/timekeeping/"><button class="btn btn-lg btn-primary btn-sm"> Quay lại thông tin chấm công</button></a>
	<br />
	<br />
	<div class="table-responsive">
				<table class="table table-bordered">
			<tr bgcolor="#D8D8D8">
				<th>Mã NV</th>
				<th>Họ tên</th>
				<th>Bộ phận</th>
				<th>Chức vụ</th>
				<th>Ngày</th>
				<th>Loại</th>
				<th>Số giờ</th>
				<th>Ghi chú</th>
				
				<!-- <th>Sửa</th> -->
				<th>Xóa</th>
			</tr>
			<c:forEach var="leaveInfo" items="${leaveInfos}">
				<tr style="font-size: 10">
					<td>${leaveInfo.employeeId}</td>
					<td>${leaveInfo.employeeName}</td>
					<td>${leaveInfo.department}</td>
					<td>${leaveInfo.title}</td>
					<td>${leaveInfo.date}</td>
					<td>${leaveInfo.leaveType}</td>
					<td>${leaveInfo.timeValue}</td>
					<td>${leaveInfo.comment}</td>
					
					<%-- <td bgcolor="#F5F6CE"><a href="editLeaveInfo?employeeId=${leaveInfo.employeeId}&date=${leaveInfo.date}&leaveType=${leaveInfo.leaveType}">Sửa</a></td> --%>
					<td bgcolor="#F5F6CE"><a href="deleteLeaveInfo?employeeId=${leaveInfo.employeeId}&date=${leaveInfo.date}&leaveType=${leaveInfo.leaveType}">Xóa</a></td>
				</tr>
			</c:forEach>
		</table>
		<c:if test="${not empty message}">
			<div class="alert alert-success">${message}</div>
		</c:if>
	</div>
</body>
</html>