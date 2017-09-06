<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Tập đoàn IDI - Quản lý nhân viên</title>
<style>
.error-message {
	color: red;
	font-size: 90%;
	font-style: italic;
}
</style>
</head>
<body>
	<h4>
		<b>${formTitle}</b>
	</h4>
	<form:form modelAttribute="employeeForm" method="POST"
		action="insertOrUpdateEmployee">
		<div class="table table-bordered">
			<form:hidden path="employeeId" />
			<table class="table">
				<tbody>
					<tr>
						<td>Mã NV:</td>
						<td><form:input path="employeeId" size="5" disabled="true"
								title="Tự động tăng" /></td>
						<%-- <td><form:errors path="employeeId" class="error-message" /></td> --%>

						<td>Ngày vào cty:</td>
						<td><form:input path="joinDate" size="10" /></td>

						<td>Lương:</td>
						<td><form:input path="salary" size="12" /></td>
					</tr>

					<tr>
						<td>Họ tên:</td>
						<td><form:input path="fullName" /></td>
						<%-- 	<td><form:errors path="fullName" class="error-message" /></td> --%>

						<td>Ngày ký HĐLĐ:</td>
						<td><form:input path="officalJoinDate" size="10" /></td>

						<td>Lương đóng BHXH:</td>
						<td><form:input path="salarySocicalInsu" size="12" /></td>
					</tr>

					<tr>
						<td>Email:</td>
						<td><form:input path="email" /></td>
						<%-- <td><form:errors path="email" class="error-message" /></td> --%>

						<td>Chức vụ:</td>
						<td><form:select path="jobTitle">
								<form:options items="${titleMap}" />
							</form:select></td>

						<td>Tỉ lệ đóng BHXH:</td>
						<td><form:input path="percentSocicalInsu" size="6" /></td>
					</tr>
					<tr>
						<td>Account:</td>
						<td><form:input path="loginAccount" size="10" /></td>
						<td>Phòng:</td>
						<td><form:select path="department">
								<form:options items="${departmentMap}" />
							</form:select></td>
						<td>Số sổ BHXH:</td>
						<td><form:input path="socicalInsuNo" size="12" /></td>
					</tr>
					<tr>
						<td>Giới tính:</td>
						<td><form:select path="gender">
								<form:option value="" label="-Giới tính-" />
								<form:option value="Nam" label="Nam" />
								<form:option value="Nữ" label="Nữ" />
							</form:select></td>

						<td>T/T hôn nhân:</td>
						<td><form:select path="maritalStatus">
								<form:option value="" label="-Hôn nhân-" />
								<form:option value="Độc thân" label="Độc thân" />
								<form:option value="Đã lập gia đình" label="Đã lập gia đình" />
								<form:option value="Góa" label="Góa" />
							</form:select></td>

						<td>Số thẻ BHYT:</td>
						<td><form:input path="healthInsuNo" size="12" /></td>
					</tr>
					<tr>
						<td>Ngày sinh:</td>
						<td><form:input path="DOB" size="10" /></td>

						<td>Trạng thái LĐ:</td>
						<td><form:select path="workStatus">
								<form:options items="${workStatusMap}" />
							</form:select></td>

						<td>Số đt:</td>
						<td><form:input path="phoneNo" size="12" /></td>
					</tr>
					<tr>
						<td>CMND/CCCD:</td>
						<td><form:input path="personalId" size="12" /></td>

						<td>Ngày cấp:</td>
						<td><form:input path="issueDate" size="10" /></td>

						<td>Quốc tịch:</td>
						<td><form:input path="nation" size="14" /></td>
					</tr>
					<tr>
						<td>Đ/c hiện tại:</td>
						<td><form:input path="currentAdress" /></td>
						<td>Đ/c thường trú:</td>
						<td><form:input path="permanentAdress" /></td>
						<td>yyyyy:</td>
						<td></td>
					</tr>
					<tr>
						<td colspan="4" nowrap="nowrap" bgcolor="999999">Liên lạc
							khẩn cấp:</td>
						<td bgcolor="#9F81F7">Ngày thôi việc:</td>
						<td><form:input path="terminationDate" size="10" /></td>
					</tr>
					<tr>
						<td>Tên người liên lạc:</td>
						<td><form:input path="emerName" /></td>
						<td>Số đt:</td>
						<td><form:input path="emerPhoneNo" size="12" /></td>
						<td bgcolor="#9F81F7">Lý do thôi việc:</td>
						<td><form:input path="reasonforLeave" /></td>
					</tr>
					<tr>
						<td colspan="6" nowrap="nowrap" bgcolor="999999">Tài khoản
							ngân hàng</td>
					</tr>
					<tr>
						<td>Tên NH:</td>
						<td><form:input path="bankName" /></td>
						<td>Số TK:</td>
						<td><form:input path="bankNo" size="12" /></td>
						<td>Chi nhánh:</td>
						<td><form:input path="bankBranch" /></td>
					</tr>
					<tr>
						<td>Ghi chú:</td>
						<td colspan="5"><form:textarea path="note" /></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td><input type="submit" value="Submit" /> <a
							href="${pageContext.request.contextPath}/">Cancel</a></td>
						<td>&nbsp;</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form:form>
</body>
</html>