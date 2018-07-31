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
<a href="${pageContext.request.contextPath}/timekeeping/leaveInfo"><button class="btn btn-lg btn-primary btn-sm">Quay lại danh sách chấm công phát sinh</button></a>
<br/><br/>
	<form:form modelAttribute="leaveInfoForm" method="POST"
		action="insertLeaveInfo">
		<div class="table">
			<table>
				<tr>
					<td><form:errors path="employeeId" class="error-message" /></td>
					<td><form:errors path="date" class="error-message" /></td>
					<td><form:errors path="leaveType" class="error-message" /></td>
					<td><form:errors path="timeValue" class="error-message" /></td>
					<td><form:errors path="comment" class="error-message" /></td>
					
					<td><form:errors path="overLeave" class="error-message" /></td>
					<td><form:errors path="duplicate" class="error-message" /></td>						
				</tr>
			</table>
			<table class="table table-bordered">
				<tbody>
					<tr>
						<td colspan="2" nowrap="nowrap" bgcolor="#F6CED8">Thêm thông tin ngày nghỉ phép, nghỉ ốm, công tác, làm thêm, đi học ....</td>
					</tr>
					<tr>
						<td bgcolor="#FBEFF2">Chọn nhân viên(*):</td>
						<td><form:select path="employeeId" class="form-control animated">
								<form:options items="${employeeMap}" />
							</form:select></td>						
					</tr>
					<tr>
						<td bgcolor="#FBEFF2">Chọn ngày(*):</td>
						<td><form:input path="date" type="date" required="required" class="form-control animated"/></td>
					</tr>	
					<tr>
						<td bgcolor="#FBEFF2">Chọn loại(*):</td>
						<td>
							<form:select path="leaveType" class="form-control animated">
								<form:options items="${leaveTypeMap}" />								
							</form:select>
						</td>	
					<tr>	
						<td bgcolor="#FBEFF2">Điền số giờ với những t/h tính thời gian (*):</td>					
						<td><form:input path="timeValue" step="0.5" type="number" min="0" max="24" class="form-control animated"  required="required"/> (Mặc định để 0 => cả ngày = 8h, nửa ngày = 4h)</td>
					</tr>
					<tr>
						<td bgcolor="#FBEFF2">Ghi chú:</td>
						<td colspan="3"><form:textarea path="comment" cols="64" class="form-control animated"/></td>
					<tr>
<%-- 					<tr>	
						<td>${timekeeping.leaveUsed}</td>
						<td>${timekeeping.leaveRemain}</td>
					<tr/> --%>
				</tbody>
			</table>
			<input class="btn btn-lg btn-primary btn-sm" type="submit" value="Lưu" /> 			
		</div>
	</form:form>	
</body>
</html>