<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Tập đoàn IDI - Xem thông tin nhân viên</title>
<style>
.error-message {
	color: red;
	font-size: 90%;
	font-style: italic;
}
</style>
</head>
<body>
	<br />
	<div class="table table-bordered">
		<table class="table">
			<tbody>
				<tr>
					<td>Mã NV:</td>
					<td><c:out value="${employeeForm.employeeId}" /></td>

					<td>Ngày vào cty:</td>
					<td><c:out value="${employeeForm.joinDate}" /></td>

					<td>Lương:</td>
					<td><c:out value="${employeeForm.salary}" /></td>
				</tr>
				<tr>
					<td>Họ tên:</td>
					<td><c:out value="${employeeForm.fullName}" /></td>

					<td>Ngày ký HĐLĐ:</td>
					<td><c:out value="${employeeForm.officalJoinDate}" /></td>

					<td>Lương đóng BHXH:</td>
					<td><c:out value="${employeeForm.salarySocicalInsu}" /></td>
				</tr>
				<tr>
					<td>Email:</td>
					<td><c:out value="${employeeForm.email}" /></td>

					<td>Chức vụ:</td>
					<td><c:out value="${employeeForm.jobTitle}" /></td>

					<td>Tỉ lệ đóng BHXH:</td>
					<td><c:out value="${employeeForm.percentSocicalInsu}" /></td>
				</tr>
				<tr>
					<td>Account:</td>
					<td><c:out value="${employeeForm.loginAccount}" /></td>
					<td>Phòng:</td>
					<td><c:out value="${employeeForm.department}" /></td>
					<td>Số sổ BHXH:</td>
					<td><c:out value="${employeeForm.socicalInsuNo}" /></td>
				</tr>
				<tr>
					<td>Giới tính:</td>
					<td><c:out value="${employeeForm.gender}" /></td>

					<td>T/T hôn nhân:</td>
					<td><c:out value="${employeeForm.maritalStatus}" /></td>

					<td>Số thẻ BHYT:</td>
					<td><c:out value="${employeeForm.healthInsuNo}" /></td>
				</tr>
				<tr>

					<td>Ngày sinh:</td>
					<td><c:out value="${employeeForm.DOB}" /></td>
					<td>Trạng thái LĐ:</td>
					<td><c:out value="${employeeForm.workStatus}" /></td>

					<td>Số đt:</td>
					<td><c:out value="${employeeForm.phoneNo}" /></td>
				</tr>
				<tr>
					<td>CMND/CCCD:</td>
					<td><c:out value="${employeeForm.personalId}" /></td>

					<td>Ngày cấp:</td>
					<td><c:out value="${employeeForm.issueDate}" /></td>


					<td>Quốc tịch:</td>
					<td><c:out value="${employeeForm.nation}" /></td>

				</tr>
				<tr>
					<td>Đ/c hiện tại:</td>
					<td><c:out value="${employeeForm.currentAdress}" /></td>
					<td>Đ/c thường trú:</td>
					<td><c:out value="${employeeForm.permanentAdress}" /></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td colspan="4" nowrap="nowrap" bgcolor="999999">Liên lạc khẩn
						cấp:</td>
					<td bgcolor="#9F81F7">Ngày thôi việc:</td>
					<td><c:out value="${employeeForm.terminationDate}" /></td>
				</tr>
				<tr>
					<td>Tên người liên lạc:</td>
					<td><c:out value="${employeeForm.emerName}" /></td>
					<td>Số đt:</td>
					<td><c:out value="${employeeForm.emerPhoneNo}" /></td>
					<td bgcolor="#9F81F7">Lý do thôi việc:</td>
					<td><c:out value="${employeeForm.reasonforLeave}" /></td>
				</tr>
				<tr>
					<td colspan="6" bgcolor="999999">Tài khoản ngân hàng</td>
				</tr>
				<tr>
					<td>Tên NH:</td>
					<td><c:out value="${employeeForm.bankName}" /></td>
					<td>Số TK:</td>
					<td><c:out value="${employeeForm.bankNo}" /></td>
					<td>Chi nhánh:</td>
					<td><c:out value="${employeeForm.bankBranch}" /></td>
				</tr>
				<tr>
					<td>Ghi chú:</td>
					<td colspan="5"><c:out value="${employeeForm.note}" /></td>					
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td><a
						href="editEmployee?employeeId=${employeeForm.employeeId}"><button>Sửa
								thông tin</button></a> <a href="${pageContext.request.contextPath}/"><button>Quay
								lại danh sách</button></a></td>
					<td>&nbsp;</td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>