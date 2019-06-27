<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Tập đoàn IDI - Quản lý tiền lương</title>
<style>
.error-message {
	color: red;
	font-size: 90%;
	font-style: italic;
}
</style>
<!-- Initialize the plugin: -->
<script src="${url}/public/js/jquery.min.js"></script>
<script src="${url}/public/js/common.js"></script>
<script type="text/javascript">
	$(function() {
		moneyConvert("salary");
	});
</script>
</head>
<body>
	<a href="${url}/salary/"><button class="btn btn-primary btn-sm">Quay lại danh sách lương nhân viên</button></a>
	<br/><br/>
	<form:form modelAttribute="salaryForm" method="POST" action="updateSalary">		
		<table class="table table-bordered">
			<tbody>
				<form:hidden path="employeeId" /> 
				<tr>
					<td colspan="5" nowrap="nowrap" bgcolor="#E6E6E6">Thông tin
						lương nhân viên</td>
				</tr>
				<tr>
					<td bgcolor="#FAFAFA">Họ tên NV:</td>
					<td colspan="4"><form:input path="fullName" class="form-control animated" disabled="true"/>	</td>						
				</tr>
				<tr>
					<td bgcolor="#FAFAFA">Lương:(*)</td>
					<td><form:input path="salary" maxlength="12" 
							required="required" class="form-control animated"/></td>
					<td>		
						<form:select path="moneyType" class="form-control animated" >
							<form:option value="VND" label="VND" />
							<form:option value="USD" label="USD" />
						</form:select>		
					</td>		
							
					<td bgcolor="#FAFAFA">Số TK ngân hàng:</td>
					<td><form:input path="bankNo" maxlength="16" type="number"
							class="form-control animated"/></td>						
				</tr>	
				<tr>
					<td bgcolor="#FAFAFA">Tên ngân hàng:</td>
					<td colspan="2"><form:input path="bankName" maxlength="64" 
							class="form-control animated"/></td>
							
					<td bgcolor="#FAFAFA">Tên chi nhánh:</td>
					<td><form:input path="bankBranch" maxlength="64"
							class="form-control animated"/></td>						
				</tr>						
				<tr>
					<td bgcolor="#FAFAFA">Ghi chú:</td>
					<td colspan="4"><form:textarea path="desc" cols="64" class="form-control animated"/></td>
				</tr>
			</tbody>
		</table>
		<input class="btn btn-primary btn-sm" type="submit" value="Lưu" />
	</form:form>	
</body>
</html>