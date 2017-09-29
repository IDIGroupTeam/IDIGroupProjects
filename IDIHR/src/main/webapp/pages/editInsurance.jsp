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
	<form:form modelAttribute="insuranceForm" method="POST"
		action="updateInsurance">
		<div class="table table-bordered">
			<table class="table">
				<tbody>
					<tr>
						<td colspan="4" nowrap="nowrap" bgcolor="#F6CED8">Thông tin
							Bảo hiểm xã hội</td>
					</tr>
					<tr>
						<td bgcolor="#E6E6E6">Chọn NV:</td>
						<td><form:select path="employeeId">
								<form:options items="${employeeMap}" />
							</form:select></td>
						<td bgcolor="#E6E6E6">Tỷ lệ đóng (%): Cty</td>
						<td><form:input path="percentSInsuC" size="6" maxlength="6"
								required="required" /></td>
					</tr>
					<tr>
						<td bgcolor="#E6E6E6">Số sổ BHXH:</td>
						<td><form:input path="socicalInsuNo" maxlength="12" size="12"
								required="required" /></td>
						<td bgcolor="#E6E6E6" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: Người LĐ</td>
						<td><form:input path="percentSInsuE" size="6" maxlength="6"
								required="required" /></td>
					</tr>
					<tr>
						<td bgcolor="#E6E6E6">Lương BH:</td>
						<td><form:input path="salarySocicalInsu" size="12" maxlength="12"
								required="required" /></td>
						<td bgcolor="#E6E6E6">Vùng lương:</td>
						<td><form:input path="salaryZone" size="12" maxlength="16"
								required="required" /></td>
					</tr>
					<tr>
						<td bgcolor="#E6E6E6">Cty đóng:</td>
						<td><form:input path="companyPay" maxlength="64"/></td>
						<td bgcolor="#E6E6E6">Phương thức đóng:</td>
						<td><form:input path="payType" maxlength="12" size="12"/></td>
					</tr>
					<tr>
						<td bgcolor="#E6E6E6">Tình trạng đóng:</td>
						<td><form:select path="status">
								<form:option value="Dang nop" label="Đang nộp" />
								<form:option value="Dang nghi thai san" label="Đang nghỉ thai sản" />
								<form:option value="Dang nghi om" label="Đang nghỉ ốm" />
								<form:option value="Dang nghi khong luong" label="Đang nghỉ không lương" />
								<form:option value="Da nghi han" label="Đã nghỉ hẳn" />
							</form:select></td>
						
						<td bgcolor="#E6E6E6">Nơi đóng:</td>
						<td><form:input path="place" required="required" maxlength="64"/></td>
					</tr>
					<tr>
						<td colspan="4" nowrap="nowrap" bgcolor="#F6CED8">Thông tin
							Bảo hiểm y tế</td>
					</tr>
					<tr>
						<td bgcolor="#E6E6E6">Số thẻ BHYT:</td>
						<td><form:input path="hInsuNo"  maxlength="12" /></td>
						<td bgcolor="#E6E6E6">Nơi ĐK khám bệnh:</td>
						<td><form:input path="hInsuPlace" maxlength="64"/></td>
					<tr>
					<tr>
						<td bgcolor="#FBEFF2">Ghi chú:</td>
						<td colspan="3"><form:textarea path="comment" cols="64" /></td>
					<tr>
						<td>&nbsp;</td>
						<td align="right"><input type="submit" value="Lưu" /> <a
							href="${pageContext.request.contextPath}/insurance/">Thoát</a></td>
						<td>&nbsp;</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form:form>
</body>
</html>