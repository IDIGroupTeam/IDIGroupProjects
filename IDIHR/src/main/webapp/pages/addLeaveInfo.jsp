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
								<form:options items="${leaveTypeMap}" />
							</form:select>
						</td>	
					<tr>	
						<td bgcolor="#FBEFF2">Điền số giờ nếu cần:</td>					
						<td><form:input path="timeValue" size="2" /></td>
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
			<input type="submit" value="Lưu" /> 			
		</div>
	</form:form>
	<a href="${pageContext.request.contextPath}/timekeeping/leaveInfo/"><button>Xem dữ liệu chấm công</button></a>
</body>
</html>