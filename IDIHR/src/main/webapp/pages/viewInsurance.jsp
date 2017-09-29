<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Tập đoàn IDI - Quản lý phòng ban</title>
<style>
.error-message {
	color: red;
	font-size: 90%;
	font-style: italic;
}
</style>
</head>
<body>
		<div class="table table-bordered">
			<table class="table">
				<tbody>
					<tr>
						<td colspan="4" nowrap="nowrap" bgcolor="#F6CED8">Thông tin
							Bảo hiểm xã hội</td>
					</tr>
					<tr>
						<td bgcolor="#E6E6E6">Mã NV:</td>
						<td><c:out value="${insuranceForm.employeeId}" /></td>
						<td bgcolor="#E6E6E6">Tỷ lệ đóng (%): Cty</td>
						<td><c:out value="${insuranceForm.percentSInsuC}" /></td>
					</tr>
					<tr>
						<td bgcolor="#E6E6E6">Số sổ BHXH:</td>
						<td><c:out value="${insuranceForm.socicalInsuNo}" /></td>
						<td bgcolor="#E6E6E6" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: Người LĐ</td>
						<td><c:out value="${insuranceForm.percentSInsuE}" /></td>
					</tr>
					<tr>
						<td bgcolor="#E6E6E6">Lương BH:</td>
						<td><c:out value="${insuranceForm.salarySocicalInsu}" /></td>
						<td bgcolor="#E6E6E6">Vùng lương:</td>
						<td><c:out value="${insuranceForm.salaryZone}" /></td>
					</tr>
					<tr>
						<td bgcolor="#E6E6E6">Cty đóng:</td>
						<td><c:out value="${insuranceForm.companyPay}" /></td>
						<td bgcolor="#E6E6E6">Phương thức đóng:</td>
						<td><c:out value="${insuranceForm.payType}" /></td>
					</tr>
					<tr>
						<td bgcolor="#E6E6E6">Tình trạng đóng:</td>
						<td><c:out value="${insuranceForm.status}" /></td>
						
						<td bgcolor="#E6E6E6">Nơi đóng:</td>
						<td><c:out value="${insuranceForm.place}" /></td>
					</tr>
					<tr>
						<td colspan="4" nowrap="nowrap" bgcolor="#F6CED8">Thông tin
							Bảo hiểm y tế</td>
					</tr>
					<tr>
						<td bgcolor="#E6E6E6">Số thẻ BHYT:</td>
						<td><c:out value="${insuranceForm.hInsuNo}" /></td>
						<td bgcolor="#E6E6E6">Nơi ĐK khám bệnh:</td>
						<td><c:out value="${insuranceForm.hInsuPlace}" /></td>
					<tr>
					<tr>
						<td bgcolor="#FBEFF2">Ghi chú:</td>
						<td colspan="3"><c:out value="${insuranceForm.comment}" /></td>
				<tr>		
				<td></td>			
					<td align="right"><a href="editInsurence?socicalInsuNo=${insurance.socicalInsuNo}"><button>Sửa</button></a>
					 <a href="${pageContext.request.contextPath}/insurance/"><button>Quay lại danh sách</button></a></td>					
				</tr>
				</tbody>
			</table>
		</div>
</body>
</html>