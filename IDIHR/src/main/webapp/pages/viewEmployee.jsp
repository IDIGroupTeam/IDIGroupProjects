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
 	<a href="${pageContext.request.contextPath}/"><button class="btn btn-lg btn-primary btn-sm">Quay
								lại danh sách NV</button></a>
	<br /><br />
	<div class="table table-bordered">
		<table class="table">
			<tbody>
				<tr>
					<td>Mã nhân viên: <c:out value="${employeeForm.employeeId}" /></td>
					<td bgcolor="#EEEEEE">Họ tên:</td>
					<td><c:out value="${employeeForm.fullName}" /></td>
					
					<td bgcolor="#EEEEEE">Số đt:</td>
					<td><c:out value="${employeeForm.phoneNo}" /></td>
				</tr>

				<tr>
					<td rowspan="5">
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
					<td bgcolor="#EEEEEE">Email:</td>
					<td><c:out value="${employeeForm.email}" /></td>

					<td bgcolor="#EEEEEE">T/T hôn nhân:</td>
					<td><c:out value="${employeeForm.maritalStatus}" /></td>
				</tr>
				<tr>
					<td bgcolor="#EEEEEE">Account:</td>
					<td><c:out value="${employeeForm.loginAccount}" /></td>
					
					<td bgcolor="#EEEEEE">Trạng thái LĐ:</td>
					<td><c:out value="${employeeForm.statusName}" /></td>
				</tr>
				<tr>
					<td bgcolor="#EEEEEE">Giới tính:</td>
					<td><c:out value="${employeeForm.gender}" /></td>
					
					<td bgcolor="#EEEEEE">Chức vụ:</td>
					<td><c:out value="${employeeForm.jobTitle}" /></td>
				</tr>
				<tr>
					<td bgcolor="#EEEEEE">Ngày sinh:</td>
					<td><c:out value="${employeeForm.DOB}" /></td>
					
					<td bgcolor="#EEEEEE">Phòng:</td>
					<td><c:out value="${employeeForm.department}" /></td>
				</tr>
				<tr>
					<td bgcolor="#EEEEEE">Ngày vào cty:</td>
					<td><c:out value="${employeeForm.joinDate}" /></td>
					
					<td bgcolor="#EEEEEE">Ngày ký HĐLĐ:</td>
					<td><c:out value="${employeeForm.officalJoinDate}" /></td>
				</tr>
				<tr>
					<td colspan="6" nowrap="nowrap" bgcolor="#C4C4C4"></td>						
				</tr>
				<tr>
					<td bgcolor="#EEEEEE">CMND/CCCD/passportNo:</td>
					<td colspan="2"><c:out value="${employeeForm.personalId}" /></td>

					<td bgcolor="#EEEEEE">Ngày cấp:</td>
					<td><c:out value="${employeeForm.issueDate}" /></td>				
				</tr>
				<tr>					
					<td bgcolor="#EEEEEE">Nơi cấp:</td>
					<td colspan="2"><c:out value="${employeeForm.issuePlace}" /></td>	
						
					<td bgcolor="#EEEEEE">Quốc tịch:</td>
					<td><c:out value="${employeeForm.nation}" /></td>			
				</tr>
				<tr>
					<td bgcolor="#EEEEEE">Đ/c hiện tại:</td>
					<td colspan="2"><c:out value="${employeeForm.currentAdress}" /></td>

					<td bgcolor="#EEEEEE">Đ/c thường trú:</td>
					<td><c:out value="${employeeForm.permanentAdress}" /></td>					
				</tr>
				<tr>
					<td colspan="6" nowrap="nowrap" bgcolor="#C4C4C4">Liên lạc
						khẩn cấp:</td>						
				</tr>
				<tr>
					<td bgcolor="#EEEEEE">Tên người liên lạc:</td>
					<td colspan="2"><c:out value="${employeeForm.emerName}" /></td>
					
					<td bgcolor="#EEEEEE">Số đt:</td>
					<td><c:out value="${employeeForm.emerPhoneNo}" /></td>
				</tr>
				<tr>
					<td bgcolor="#EEEEEE">Ghi chú:</td>
					<td colspan="5"><c:out value="${employeeForm.note}" /></td>
				</tr>
			</tbody>
		</table>
		</div>
		<a href="editEmployee?employeeId=${employeeForm.employeeId}"><button class="btn btn-lg btn-primary btn-sm">Sửa
					thông tin</button></a>
	
</body>
</html>