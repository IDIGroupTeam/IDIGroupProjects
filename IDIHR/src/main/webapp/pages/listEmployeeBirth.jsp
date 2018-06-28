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
	<div class="table-responsive">
		<form:form action="listEmployeeBirth" modelAttribute="bithForm"
			method="GET">
			<table class="table">
				<tr>
					<td><b><i>Chọn quý khác</i></b></td>
					<td align="center"><form:select path="quarter" class="form-control animated">
							<%-- <form:option value="" label="-Chon quý-" /> --%>
							<form:option value="1" label="Quý 1" />
							<form:option value="2" label="Quý 2" />
							<form:option value="3" label="Quý 3" />
							<form:option value="4" label="Quý 4" />
						</form:select></td>
					<td><input class="btn btn-lg btn-primary btn-sm" type="submit"
						value="Tìm kiếm" /></td>
				</tr>
			</table>
			<c:if test="${not empty message}">
				<div class="alert alert-success">${message}</div>
			</c:if>
		</form:form>
		<table class="table table-striped">
			<tr>
				<th>Mã NV</th>
				<th>Họ tên</th>
				<th>Chức vụ</th>
				<th>Phòng</th>
				<th>Trạng thái</th>
				<th>Giới tính</th>
				<th>Ngày sinh</th>
				<th>Ngày vào cty</th>
				<th>Thâm niên (tháng)</th>
			</tr>
			<c:forEach var="employee" items="${employees}">
				<tr>
					<td>${employee.employeeId}</td>
					<td>${employee.fullName}</td>
					<td>${employee.jobTitle}</td>
					<td>${employee.department}</td>
					<td>${employee.statusName}</td>
					<td>${employee.gender}</td>
					<td>${employee.DOB}</td>
					<td>${employee.joinDate}</td>
					<c:if test="${employee.seniority < 6}">
						<td bgcolor="F7F498">${employee.seniority}</td>
					</c:if>
					<c:if test="${employee.seniority >= 6}">
						<td>${employee.seniority}</td>
					</c:if>
				</tr>
			</c:forEach>
		</table>
		<a href="${pageContext.request.contextPath}/"><button class="btn btn-lg btn-primary btn-sm">
			Quay lại danh sách NV</button></a>
		<c:if test="${not empty message}">
			<div class="alert alert-success">${message}</div>
		</c:if>
	</div>
</body>
</html>