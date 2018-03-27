<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Tập đoàn IDI - Quản lý ngày làm việc</title>
<style>
.error-message {
	color: red;
	font-size: 90%;
	font-style: italic;
}
</style>
</head>
<body>
	<form:form modelAttribute="workingDayForm" method="POST"
		action="updateWorkingDay">
		<div class="table table-bordered" >
			<table class="table">
				<tbody>
					<tr>
						<td bgcolor="#FAFAFA">Chọn tháng muốn định nghĩa:(*)</td>						
						<td><form:input path="month" required="required" type="Month" class="form-control animated"/></td>
						<td></td>
					</tr>
					<tr>
						<td bgcolor="#FAFAFA">Chọn công ty áp dụng (mặc định chỉ áp dụng cho IDI):</td>
						<td>
							<form:select path="forCompany" class="form-control animated">
								<form:option value="IDI" label="IDI"></form:option>
								<form:option value="Cabeco" label="Cabeco"></form:option>
								<form:option value="HGTC" label="Hoàng gia toàn cầu"></form:option>
								<!-- se lay du lieu dong sau ... -->							
							</form:select>		
						</td>						
						<td></td>
					</tr>
					<tr>
						<td bgcolor="#FAFAFA">Số ngày làm viêc chuẩn:(*)</td>
						<td><form:input path="workDayOfMonth" type="number" step="0.5" min="1" max="28" 
								required="required" class="form-control animated"/></td>
						<td></td>						
					</tr>
					<tr>
						<td bgcolor="#FAFAFA">Ghi chú:</td>
						<td colspan="2"><form:textarea path="comment" cols="64" class="form-control animated"/></td>						
					</tr>					
				</tbody>
			</table>
		</div>
		<input class="btn btn-primary btn-sm" type="submit" value="Lưu" /><br/><br/>
	</form:form>
	 <a href="${pageContext.request.contextPath}/timekeeping/listWorkingDay/"><button class="btn btn-primary btn-sm">Quay lại danh sách tháng đã định nghĩa</button></a>
</body>
</html>