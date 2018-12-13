<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
<title>Danh sách phòng ban</title>
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
	<div class="table-responsive">
		<a href="${url}/department/insertDepartment"><button
				class="btn btn-primary btn-sm">Thêm mới phòng ban</button></a> <br />
		<br />
		<table class="table table-bordered">
			<tr>
				<th>Mã phòng</th>
				<th>Tên phòng</th>
				<th>Số lượng NV</th>
				<th>Danh sách NV</th>
				<th>Sửa thông tin</th>
			</tr>
			<c:forEach var="department" items="${departments}">
				<tr>
					<td>${department.departmentId}</td>
					<td>${department.departmentName}</td>
					<td>${department.numberOfMember}</td>
					<td><a
						href="listEmployeeOfDepartment?departmentId=${department.departmentId}">Xem</a>
					</td>
					<td><a
						href="editDepartment?departmentId=${department.departmentId}">Sửa</a>
					</td>
				</tr>
			</c:forEach>
		</table>
		<table align="center">
			<tr align="center">
				<td align="center"><img align="middle" src="${url}/charts/departmentChart.png" alt="charts"
					width="750" height="400" /></td>
			</tr>

		</table>
		<c:if test="${not empty message}">
			<div class="alert alert-success">${message}</div>
		</c:if>
	</div>
</body>
</html>