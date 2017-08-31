<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
<meta charset="UTF-8">
<title>Danh sách nhân viên</title>
</head>
<body>
	<a href="${pageContext.request.contextPath}/insertEmployee">Thêm mới nhân viên</a>
	<br />
	<div class="table-responsive">
		<h1>Danh sách nhân viên</h1>
		<table class="table table-striped">
			<tr>
				<th>Mã NV</th>
				<th>Họ tên</th>
				<th>Account</th>
				<th>Email</th>
				<th>Chức vụ</th>
				<!--            <th>Phòng<th> -->
				<th>Giới tính</th>
				<th>Số đt</th>
				<th>view</th>
				<th>Edit</th>
			</tr>
			<c:forEach var="employee" items="${employees}">
				<tr>
					<td>${employee.employeeId}</td>
					<td>${employee.fullName}</td>
					<td>${employee.loginAccount}</td>
					<td>${employee.email}</td>
					<td>${employee.jobTitle}</td>
					<td>${employee.gender}</td>
					<td>${employee.phoneNo}</td>
					<td><a href="viewEmployeeInfo?id=${employee.employeeId}">View</a>
					</td>
					<td><a href="editEmployeeInfo?id=${employee.employeeId}">Edit</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>