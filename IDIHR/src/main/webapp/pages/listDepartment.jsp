<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
<title>Danh sách phòng ban</title>
</head>
<body>
	<a
		href="${pageContext.request.contextPath}/department/insertDepartment"><button
			class=".btn-success">Thêm mới phòng ban</button></a>
	<br />
	<div class="table-responsive">
		<h1>Danh sách phòng ban</h1>
		<table class="table table-striped">
			<tr>
				<th>Mã phòng</th>
				<th>Tên phòng</th>
				<th>Ghi chú</th>

				<th>Edit</th>
			</tr>
			<c:forEach var="department" items="${departments}">
				<tr>
					<td>${department.departmentId}</td>
					<td>${department.departmentName}</td>
					<td>${department.desc}</td>
					<td><a
						href="editDepartment?departmentId=${department.departmentId}">Edit</a>
					</td>
				</tr>
			</c:forEach>
		</table>
		<c:if test="${not empty message}">
			<div class="alert alert-success">${message}</div>
		</c:if>
	</div>
</body>
</html>