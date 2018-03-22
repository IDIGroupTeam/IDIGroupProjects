<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
<title>Danh sách phòng ban</title>

</head>
<body>
	<div class="table-responsive">
		<a href="${url}/timekeeping/setWorkingDayForMonth"><button
				class="btn btn-primary btn-sm">Thêm tháng định nghĩa</button></a>
		<a href="${url}/timekeeping/"><button
				class="btn btn-primary btn-sm">Quay lại chấm công</button></a>		
		<br />
		<br />
		<table class="table table-bordered">
			<tr>
				<th>Tháng</th>
				<th>Số ngày làm việc chuẩn</th>
				<th>Áp dụng cho công ty</th>
				<th>Người định nghĩa</th>
				<th>Ngày định nghĩa</th>
				<th>Ghi chú</th>
				<th>Sửa thông tin</th>
			</tr>
			<c:forEach var="workingDay" items="${workingDays}">
				<tr>
					<td>${workingDay.month}</td>
					<td>${workingDay.workDayOfMonth}</td>
					<td>${workingDay.forCompany}</td>
					<td>${workingDay.updateId}</td>
					<td>${workingDay.updateTs}</td>
					<td>${workingDay.comment}</td>
					<td><a
						href="${url}/timekeeping/editWorkingDay?month=${workingDay.month}&forCompany=${workingDay.forCompany}">Sửa</a>
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