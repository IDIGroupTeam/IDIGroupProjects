<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
<title>Quá trình đóng BHXH của nhân viên</title>
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
<script type="text/javascript">
	function ConfirmDelete() {
	  return confirm("Bạn có chắc chắn muốn xóa không?");
	}
</script>
</head>
<body>
	<a
		href="${pageContext.request.contextPath}/processInsurance/insertProcessInsuranceForm?socicalInsuNo=${socicalInsuNo}&employeeId=${employeeId}"><button
			class="btn btn-primary btn-sm">Thêm mới</button></a>
	<br />
	<br />
	<div class="table-responsive">
		<table class="table">
			<tr>
				<td bgcolor="DDC5C5">Nhân viên:</td>
				<td><c:out value="${name}" /></td>
			</tr>
			<tr>
				<td bgcolor="DDC5C5">Số sổ BHXH:</td>
				<td><c:out value="${socicalInsuNo}" /></td>
			</tr>
		</table>
		<c:if test="${empty pInsurances}">
			<div class="alert alert-info">Chưa có thông tin quá trình đóng BHXH</div>
		</c:if>
		<c:if test="${not empty pInsurances}">
			<table class="table table-striped">
				<tr>
					<th>Số sổ BHXH</th>
					<th>Lương BH</th>
					<th>Cty đóng BHXH</th>
					<th>Từ ngày</th>
					<th>Đến ngày</th>
					<th>Ghi chú</th>
					<th>Sửa</th>
					<th>Xóa</th>
				</tr>
				<c:forEach var="insurance" items="${pInsurances}">
					<tr>
						<td>${insurance.socicalInsuNo}</td>
						<td><fmt:formatNumber value="${insurance.salarySocicalInsu.replaceAll(',', '')}"/></td>
						<td>${insurance.companyPay}</td>
						<td>${insurance.fromDate}</td>
						<c:if test="${empty insurance.toDate}">
							<td>Đến nay</td>
						</c:if>
						<c:if test="${not empty insurance.toDate}">
							<td>${insurance.toDate}</td>
						</c:if>
						<td>${insurance.comment}</td>
						<td><a
							href="${pageContext.request.contextPath}/processInsurance/editProcessInsuranceForm?socicalInsuNo=${socicalInsuNo}&employeeId=${employeeId}&fromDate=${insurance.fromDate}">Sửa</a></td>
						<td><a
							href="${pageContext.request.contextPath}/processInsurance/deleteProcessInsurance?socicalInsuNo=${socicalInsuNo}&employeeId=${employeeId}&fromDate=${insurance.fromDate}" Onclick="return ConfirmDelete()">Xóa</a></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		<c:if test="${not empty message}">
			<div class="alert alert-success">${message}</div>
		</c:if>
		<a href="${pageContext.request.contextPath}/insurance/"><button class="btn btn-primary btn-sm">Quay lại danh sách đóng BH</button></a>
	</div>
</body>
</html>