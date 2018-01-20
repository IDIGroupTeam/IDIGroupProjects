<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Tập đoàn IDI - Chấm công</title>
<style>
.error-message {
	color: red;
	font-size: 90%;
	font-style: italic;
}
</style>
</head>
<body>
	<form:form modelAttribute="leaveInfoForm" method="POST"
		action="insertLeaveInfo">
		<div class="table">
			<table class="table table-bordered">
				<tbody>
					<tr>
						<td colspan="2" nowrap="nowrap" bgcolor="#F6CED8">Thêm thông tin ngày nghỉ phép, nghỉ ốm, công tác, làm thêm, đi học ....</td>
					</tr>
					<tr>
						<td bgcolor="#FBEFF2">Chọn nhân viên(*):</td>
						<td><form:select path="employeeId">
								<form:options items="${employeeMap}" />
							</form:select></td>						
					</tr>
					<tr>
						<td bgcolor="#FBEFF2">Chọn ngày(*):</td>
						<td><form:input path="date" type="date" required="required" value="11/11/2017"/></td>
					</tr>	
					<tr>
						<td bgcolor="#FBEFF2">Chọn loại(*):</td>
						<td>
							<form:select path="leaveType">
								<form:option value="DMS" label="Đi muộn sáng"></form:option>
								<form:option value="DMC" label="Đi muộn chiều"></form:option>
								<form:option value="VSS" label="Về sớm sáng"></form:option>
								<form:option value="VSC" label="Về sớm chiều"></form:option>
								<form:options items="${leaveTypeMap}" />
								
							</form:select>
						</td>	
					<tr>	
						<td bgcolor="#FBEFF2">Điền số giờ nếu cần (Làm thêm giờ):</td>					
						<td><form:input path="timeValue" size="1" type="number" min="0" max="24"/> (Mặc định cả ngày = 8h, nửa ngày = 4h)</td>
					</tr>
					<tr>
						<td bgcolor="#FBEFF2">Ghi chú:</td>
						<td colspan="3"><form:textarea path="comment" cols="64" /></td>
					<tr>
					<tr>	
						<td>${timekeeping.leaveUsed}</td>
						<td>${timekeeping.leaveRemain}</td>
					<tr/>
				</tbody>
			</table>
			<input class="btn btn-lg btn-primary btn-sm" type="submit" value="Lưu" /> 			
		</div>
	</form:form>
	<a href="${pageContext.request.contextPath}/timekeeping/leaveInfo/"><button class="btn btn-lg btn-primary btn-sm">Xem dữ liệu chấm công</button></a>
</body>
</html>