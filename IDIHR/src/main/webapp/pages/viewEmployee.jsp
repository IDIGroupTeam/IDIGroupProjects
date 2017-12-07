<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

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
					<td>Mã nhân viên: <c:out value="${employeeForm.employeeId}" /></td>
					<td bgcolor="#E6E6E6">Họ tên:</td>
					<td><c:out value="${employeeForm.fullName}" /></td>
					<td bgcolor="#E6E6E6">Số đt:</td>
					<td><c:out value="${employeeForm.phoneNo}" /></td>
				</tr>

				<tr>
					<td rowspan="4">
<%-- 					<%if (latestPhotoUpload != null && !"".equals(latestPhotoUpload)) {%>
                        <div class="row">
                            <div class="col-md-12">
                                <img src="<%=baseURL%>/img/<%=latestPhotoUpload%>" class="col-md-12"/>
                            </div>
                        </div>
                        <%}%> --%>
					<img src="<c:out value="${employeeForm.imagePath}"/>" alt="profile image" width="125px" height="150px"/>
					</td>
<!-- 					<img src="D://IBM_ADMIN//Pictures//Pics//TruongNV.jpg" width="125px" height="150px"></td> -->
					<td bgcolor="#E6E6E6">Email:</td>
					<td><c:out value="${employeeForm.email}" /></td>

					<td bgcolor="#E6E6E6">T/T hôn nhân:</td>
					<td><c:out value="${employeeForm.maritalStatus}" /></td>
				</tr>
				<tr>
					<td bgcolor="#E6E6E6">Account(*):</td>
					<td><c:out value="${employeeForm.loginAccount}" /></td>
					<td bgcolor="#E6E6E6">Trạng thái LĐ:</td>
					<td><c:out value="${employeeForm.workStatus}" /></td>
				</tr>
				<tr>
					<td bgcolor="#E6E6E6">Giới tính:</td>
					<td><c:out value="${employeeForm.gender}" /></td>
					<td bgcolor="#E6E6E6">Chức vụ:</td>
					<td><c:out value="${employeeForm.jobTitle}" /></td>
				</tr>
				<tr>
					<td bgcolor="#E6E6E6">Ngày sinh:</td>
					<td><c:out value="${employeeForm.DOB}" /></td>
					<td bgcolor="#E6E6E6">Phòng:</td>
					<td><c:out value="${employeeForm.department}" /></td>
				</tr>
				<tr>
						<td bgcolor="#E6E6E6">Ngày vào cty:</td>
						<td><c:out value="${employeeForm.joinDate}" /></td>
						<td bgcolor="#E6E6E6">Ngày ký HĐLĐ:</td>
						<td><c:out value="${employeeForm.officalJoinDate}" /></td>
					</tr>
				<tr>
					<td bgcolor="#E6E6E6">CMND/CCCD/passportNo:</td>
					<td><c:out value="${employeeForm.personalId}" /></td>

					<td bgcolor="#E6E6E6">Ngày cấp:</td>
					<td><c:out value="${employeeForm.issueDate}" /></td>


					<td>Quốc tịch: <c:out value="${employeeForm.nation}" /></td>

				</tr>
				<tr>
					<td bgcolor="#E6E6E6">Đ/c hiện tại:</td>
					<td><c:out value="${employeeForm.currentAdress}" /></td>

					<td bgcolor="#E6E6E6">Đ/c thường trú:</td>
					<td><c:out value="${employeeForm.permanentAdress}" /></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td colspan="6" nowrap="nowrap" bgcolor="#F6CED8">Liên lạc
						khẩn cấp:</td>
				</tr>
				<tr>
					<td bgcolor="#FBEFF2">Tên người liên lạc:</td>
					<td><c:out value="${employeeForm.emerName}" /></td>
					<td bgcolor="#FBEFF2">Số đt:</td>
					<td><c:out value="${employeeForm.emerPhoneNo}" /></td>

				</tr>

				<tr>
					<td bgcolor="#FBEFF2">Ghi chú:</td>
					<td colspan="5"><c:out value="${employeeForm.note}" /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><a
						href="editEmployee?employeeId=${employeeForm.employeeId}"><button>Sửa
								thông tin</button></a> <a href="${pageContext.request.contextPath}/"><button>Quay
								lại danh sách NV</button></a></td>
					<td>&nbsp;</td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>