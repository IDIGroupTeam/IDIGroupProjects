<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Tập đoàn IDI - Quản lý phòng ban</title>
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
	<form:form modelAttribute="departmentForm" method="POST"
		action="addDepartment">
		<div class="table table-bordered">	
			<table class="table">
				<tbody>
					<tr>
						<td bgcolor="#E6E6E6">Mã phòng:</td>
						<td><form:input path="departmentId" size="12" required="required"/>	</td>						
					</tr>
					<tr>
						<td bgcolor="#E6E6E6">Tên phòng:</td>
						<td><form:input path="departmentName" size="24" required="required"/></td>
					</tr>	
					<tr>
						<td bgcolor="#E6E6E6">Ghi chú:</td>
						<td colspan="5"><form:textarea path="desc" cols="45"/></td>
					</tr>
				</tbody>
			</table>
		</div>
		<input class="btn btn-lg btn-primary btn-sm" type="submit" value="Lưu" /><br/><br/>
	</form:form>
	<a href="${pageContext.request.contextPath}/department/"><button class="btn btn-primary btn-sm">Quay lại danh sách phòng ban</button></a>
</body>
</html>