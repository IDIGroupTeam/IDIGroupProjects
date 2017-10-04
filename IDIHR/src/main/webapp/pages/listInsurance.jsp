<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
<title>Danh sách BH của nhân viên</title>
</head>
<body>
	<a href="${pageContext.request.contextPath}/insurance/insertInsurance"><button
			class="btn btn-primary">Thêm mới </button></a>
	<br />
	<br />
	<div class="table-responsive">
		
		<table class="table table-striped">
			<tr>
				<th>Mã NV</th>
				<th>Số sổ BHXH</th>
				<th>Lương BH</th>
				<th>Cty đóng BHXH</th>
				<th>Đóng tại</th>
				<th>Tình trạng đóng BHXH</th>
				<th>Số thẻ BHYT</th>
				<th>Nơi ĐK khám bệnh</th>
				<th>Quá trình đóng BH</th>
				<th>Xem chi tiết</th>
				
				<th>Sửa</th>
			</tr>
			<c:forEach var="insurance" items="${insurances}">
				<tr>
					<td>${insurance.employeeId}</td>
					<td>${insurance.socicalInsuNo}</td>
					<td>${insurance.salarySocicalInsu}</td>
					<td>${insurance.companyPay}</td>
					<td>${insurance.place}</td>
					<td>${insurance.status}</td>
					<td>${insurance.hInsuNo}</td>
					<td>${insurance.place}</td>
					<td><a href="viewInsurance?socicalInsuNo=${insurance.socicalInsuNo}">Xem chi tiết</a></td>
					<td><a href="listProcessInsurance?socicalInsuNo=${insurance.socicalInsuNo}&employeeId=${insurance.employeeId}">Quá trình đóng</a></td>
					<td><a href="editInsurance?socicalInsuNo=${insurance.socicalInsuNo}">Sửa</a></td>
				</tr>
			</c:forEach>
		</table>
		<c:if test="${not empty message}">
			<div class="alert alert-success">${message}</div>
		</c:if>
	</div>
</body>
</html>