<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<script src="${url}/public/js/jquery-ui.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
<script src="${url}/public/js/bootstrap-datetimepicker.min.js"></script>
<%-- <script src="${url}/public/js/bootstrap-datetimepicker.vi.js"></script> --%>
 
<script type="text/javascript">
$(function() {
	$(".datetime").datetimepicker({
		language : 'vi',
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		minView : 2,
		forceParse : 0,
		pickerPosition : "bottom-left"
	});
	
/* 	alert("xxxxx");
    $('#officalJoinDate').daterangepicker({
        locale: {
                 format: 'DD/MM/YYYY'
                 }
      });
    alert("xxxxxyyyyyyyy");
    $('#joinDate').datetimepicker({
      language: 'pt-BR'
    });
    alert("xxxxxyyyyyyyyzzzzzzzzz"); */

}); 
</script>

</head>
<fmt:formatDate value="${today}" pattern="dd/M/yyyy" type="Date"
	dateStyle="SHORT" var="today" />
<body>
	<form:form modelAttribute="employeeForm" method="POST"
		action="insertOrUpdateEmployee" enctype="multipart/form-data">
		<input class="btn btn-lg btn-primary btn-sm" type="submit" value="Lưu" name="Lưu" /><br/><br/>
		<div class="table table-bordered">
			<form:hidden path="employeeId" />
			<table>
				<tr>
					<td><form:errors path="fullName" class="error-message" /></td>
				</tr>
				<tr>
					<td><form:errors path="email" class="error-message" /></td>
				</tr>
				<tr>
					<td><form:errors path="gender" class="error-message" /></td>
				</tr>
				<tr>
					<td><form:errors path="loginAccount" class="error-message" /></td>
				</tr>

			</table>
			<table class="table">
				<tbody>
					<%-- <tr>
						<td>Mã NV:</td>
						<td><form:input path="employeeId" size="5" disabled="true"
								title="Tự động tăng" /></td>
								
						<td>Ngày vào cty:</td>
						<td><form:input path="joinDate" type="date" /></td>

						<td>Lương:</td>
						<td><form:input path="salary" size="12" /></td>
					</tr> --%>

					<tr>
						<td nowrap="nowrap"><input type="file" accept="image/jpeg"
							name="image" class="form-control animated"/></td>
						<td bgcolor="#E6E6E6">Họ tên(*):</td>
						<td><form:input path="fullName" required="required" maxlength="64" class="form-control animated"/></td>
						<td bgcolor="#E6E6E6">Số điện thoại:</td>
						<td><form:input path="phoneNo" type="tel" class="form-control animated"/></td>
						<%-- <td>Lương đóng BHXH:</td>
						<td><form:input path="salarySocicalInsu" size="12" /></td> --%>
					</tr>

					<tr>
						<td rowspan="4"><form:hidden path="imagePath" /> <c:if
								test="${ not empty employeeForm.imagePath}">
								<img src="${employeeForm.imagePath}" height="170px"
									width="145px" />
							</c:if> <c:if test="${ empty employeeForm.imagePath}">
								<img src="/IDIHR/public/images/avatar.gif" height="170px"
									width="145px" />
							</c:if></td>
						<td bgcolor="#E6E6E6">Email(*):</td>
						<!-- can check duplicate  -->
						<td><form:input path="email" required="required" maxlength="45" class="form-control animated"/></td>

						<td bgcolor="#E6E6E6">T/T hôn nhân:</td>
						<td><form:select path="maritalStatus" class="form-control animated">
								<%-- <form:option value="" label="-Hôn nhân-" /> --%>
								<form:option value="Độc thân" label="Độc thân" />
								<form:option value="Đã lập gia đình" label="Đã lập gia đình" />
								<form:option value="Guá" label="Góa" />
							</form:select></td>

						<%-- 
						<td>Tỉ lệ đóng BHXH:</td>
						<td><form:input path="percentSocicalInsu" size="6" /></td> --%>
					</tr>
					<tr>
						<td bgcolor="#E6E6E6">Account(*):</td>
						<!-- can check duplicate  -->
						<td><form:input path="loginAccount" size="10" maxlength="45" required="required" class="form-control animated"/></td>
						<td bgcolor="#E6E6E6">Trạng thái LĐ:</td>
						<td><form:select path="workStatus" class="form-control animated">
								<form:options items="${workStatusMap}" />
							</form:select></td>
						<%--<td>Số sổ BHXH:</td>
						<td><form:input path="socicalInsuNo" size="12" /></td> --%>
					</tr>
					<tr>
						<td bgcolor="#E6E6E6">Giới tính:</td>
						<td><form:select path="gender" class="form-control animated">
								<%-- <form:option value="" label="-Giới tính-" /> --%>
								<form:option value="Nam" label="Nam" />
								<form:option value="Nữ" label="Nữ" />
							</form:select></td>
						<td bgcolor="#E6E6E6">Chức vụ:</td>
						<td><form:select path="jobTitle" class="form-control animated">
								<form:options items="${titleMap}" />
							</form:select></td>
					</tr>
					<tr>
						<td bgcolor="#E6E6E6">Ngày sinh:</td>					
						<td>
							<div class="input-group date datetime smallform">
								<form:input path="DOB" class="form-control"
									placeholder="dd/mm/yyyy" />
								<span class="input-group-addon"><span
									class="glyphicon glyphicon-calendar"></span></span>
							</div>
						</td>	
<%-- 						<td>						
						<div id="datetimepicker">
						<form:input path="DOB" type="date" id="datetimepicker" data-date-format="DD/MM/YYYY" class="form-control animated"/>
						</div>
						</td> --%>
						<td bgcolor="#E6E6E6">Phòng:</td>
						<td><form:select path="department" class="form-control animated">
								<form:options items="${departmentMap}" />
							</form:select></td>
					</tr>
					<tr>
						<td bgcolor="#E6E6E6">Ngày vào cty(*):</td>
						<td><div class="input-group date datetime smallform"><form:input path="joinDate" type="date" required="required" class="form-control animated"/></div></td>
						<td bgcolor="#E6E6E6">Ngày ký HĐLĐ:</td>
						<td><form:input path="officalJoinDate" type="date" class="form-control animated"/></td>
						<td>Quốc tịch:  <form:input path="nation" size="18" maxlength="32"/></td>
					</tr>
					<tr>
						<td bgcolor="#E6E6E6">CMND/CCCD/passportNo:</td>
						<td><form:input path="personalId" size="12" class="form-control animated"/></td>

						<td bgcolor="#E6E6E6">Ngày cấp:</td>
						<td><form:input path="issueDate" type="date" class="form-control animated"/></td>

						<td> Nơi cấp: &nbsp; <form:input path="issuePlace" size="18" maxlength="12"/></td>

					</tr>
					<tr>
						<td bgcolor="#E6E6E6">Đ/c hiện tại:</td>
						<td><form:input path="currentAdress" maxlength="255" class="form-control animated"/></td>

						<td bgcolor="#E6E6E6">Đ/c thường trú:</td>
						<td colspan="2"><form:input path="permanentAdress" class="form-control animated"/></td>
						
					</tr>
					<tr>
						<td colspan="6" nowrap="nowrap" bgcolor="#F6CED8">Liên lạc khẩn cấp: </td>
						<%-- <td bgcolor="#9F81F7">Ngày thôi việc:</td>
						<td><form:input path="terminationDate" type="date" /></td> --%>
					</tr>
					<tr>
						<td bgcolor="#FBEFF2">Tên người liên lạc:</td>
						<td><form:input path="emerName" maxlength="32" class="form-control animated"/></td>
						<td bgcolor="#FBEFF2">Số đt:</td>
						<td><form:input path="emerPhoneNo" size="12" maxlength="20" class="form-control animated"/></td>
						<%--<td bgcolor="#9F81F7">Lý do thôi việc:</td>
						<td><form:input path="reasonforLeave" /></td> --%>
					</tr>
					<%--<tr>
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
					</tr> --%>
					<tr>
						<td bgcolor="#FBEFF2">Ghi chú:</td>
						<td colspan="5"><form:textarea path="note" cols="100" class="form-control animated"/></td>
					</tr>
				</tbody>
			</table>
		</div>
		<input class="btn btn-lg btn-primary btn-sm" type="submit" value="Lưu" name="Lưu" /><br/>
	</form:form>
	<br/>
	<a href="${pageContext.request.contextPath}/">
	<button	class="btn btn-lg btn-primary btn-sm">Quay lại danh sách NV</button></a>
</body>
</html>