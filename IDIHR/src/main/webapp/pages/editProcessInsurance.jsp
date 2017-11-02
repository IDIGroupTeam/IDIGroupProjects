<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Tập đoàn IDI - Sửa thông tin quá trình đóng BH</title>
<style>
.error-message {
	color: red;
	font-size: 90%;
	font-style: italic;
}
</style>
</head>
<body>
	<form:form modelAttribute="pInsuranceForm" method="POST"
		action="updateProcessInsurance?employeeId=${employeeId}">
		<div class="table table-bordered">
			<table class="table">
				<tbody>
					<tr>
						<td colspan="4" nowrap="nowrap" bgcolor="#F6CED8">Thông tin
							bảo hiểm xã hội</td>
					</tr>
					<tr>
						<td bgcolor="#E6E6E6">Nhân viên:</td>
						<td colspan="3"><c:out value="${name}" /></td>
					</tr>
					<tr>
						<td bgcolor="#E6E6E6">Số sổ BHXH:</td>
						<td><form:input path="socicalInsuNo" readonly="true"/></td>
					</tr>
					<tr>
						<td bgcolor="#E6E6E6">Lương BH(*):</td>
						<td><form:input path="salarySocicalInsu" size="12"
								maxlength="12" required="required" /></td>
					<td bgcolor="#E6E6E6">Cty đóng:</td>
						<td><form:input path="companyPay" required="required" /></td>							
					</tr>
					<tr>
						<td colspan="4" nowrap="nowrap" bgcolor="#F6CED8">Thời gian
							đóng</td>
					</tr>
					<tr>
						<td bgcolor="#E6E6E6">Từ ngày(*):</td>
						<td><form:input path="fromDate" required="required"
								type="date" readonly="true"/></td>
						<td bgcolor="#E6E6E6">Đến ngày:</td>
						<td><form:input path="toDate" type="date" /></td>
					</tr>
					<tr>
						<td bgcolor="#FBEFF2">Ghi chú:</td>
						<td colspan="3"><form:textarea path="comment" cols="64" /></td>
					<tr>
						<td>&nbsp;</td>
						<td align="right"><input type="submit" value="Lưu" /> <a
							href="${pageContext.request.contextPath}/insurance/listProcessInsurance?socicalInsuNo=${socicalInsuNo}&employeeId=${employeeId}">Thoát</a></td>
						<td>&nbsp;</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form:form>
</body>
</html>