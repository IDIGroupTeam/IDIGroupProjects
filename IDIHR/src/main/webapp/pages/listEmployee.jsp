<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
<title>Danh sách nhân viên</title>
</head>
<body>
<div class="tab">
  <a href="${pageContext.request.contextPath}/"><button class="btn btn-default">Quản lý nhân viên</button></a>
  <a href="${pageContext.request.contextPath}/department/"><button>Quản lý phòng ban</button></a>
  <a href="${pageContext.request.contextPath}/"><button disabled="disabled">Quản lý chức danh</button></a>
</div>
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
					<td><a href="viewEmployee?employeeId=${employee.employeeId}">View</a>
					</td>
					<td><a href="editEmployee?employeeId=${employee.employeeId}">Edit</a>
					</td>
				</tr>
			</c:forEach>
		</table>
		<c:if test="${not empty message}">
			<div class="alert alert-success">${message}</div>
		</c:if>
			<a href="${pageContext.request.contextPath}/insertEmployee"><button class="btn btn-primary">Thêm
			mới nhân viên</button></a>
	</div>
</body>
</html>