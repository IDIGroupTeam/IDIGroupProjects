<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
<title>Danh sách nhân viên</title>
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
	<a href="${url}/insertEmployee"><button
			class="btn btn-primary btn-sm">Thêm mới nhân viên</button></a>
	<a href="${url}/listEmployeeBirth?quarter=${quarter}"><button
			class="btn btn-primary btn-sm">Danh sách nhân viên sinh nhật quý</button></a>		
	<a href="${url}/workStatusReport"><button
			class="btn btn-primary btn-sm">Thống kê trạng thái LĐ</button></a>				
	<br />
	<br />
	<form:form action="listEmployeeSearch" modelAttribute="employeeForm" method="GET">
	<table class="table">
		<tr>
			<td style="color: purple;"><i>Nhập thông tin nhân viên muốn tìm kiếm: Tên/Email/Account/Mã NV/Mã phòng/Mã chức vụ/trạng thái LĐ </i></td>
			<td align="center">
				<form:input path="searchValue" required="required" class="form-control animated"/>
			</td>
			<td>
				<input class="btn btn-lg btn-primary btn-sm" type="submit" value="Tìm" />
			</td>			
		</tr>
	</table>
	</form:form>
	<div class="table-responsive">
		<c:if test="${not empty message}">
			<div class="alert alert-success">${message}</div>
		</c:if>
		<table class="table table-striped">
			<tr>
				<th nowrap="nowrap">Mã NV</th>
				<th>Họ tên</th>
				<th>Account</th>
				<th>Email</th>
				<th nowrap="nowrap">Chức vụ</th>
				<th>Phòng</th>
				<th>Trạng thái</th>
				<!-- th nowrap="nowrap">Giới tính</th-->
				<th>Số đt</th>
				<th nowrap="nowrap">Chi tiết</th>
				<th>Sửa</th>
			</tr>
			<c:forEach var="employee" items="${employees}">
				<tr>
					<td>${employee.employeeId}</td>
					<td nowrap="nowrap">${employee.fullName}</td>
					<td>${employee.loginAccount}</td>
					<td>${employee.email}</td>
					<td>${employee.jobTitle}</td>
					<td>${employee.department}</td>
					<td nowrap="nowrap">${employee.statusName}</td>
					<!-- td>${employee.gender}</td-->
					<td>${employee.phoneNo}</td>
					<td><a href="/IDIHR/viewEmployee?employeeId=${employee.employeeId}">Xem</a></td>
					<td><a href="/IDIHR/editEmployee?employeeId=${employee.employeeId}">Sửa</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>