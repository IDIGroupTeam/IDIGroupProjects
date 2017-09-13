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
<div class="tab">
  <a href="${pageContext.request.contextPath}/"><button>Quản lý nhân viên</button></a>
  <a href="${pageContext.request.contextPath}/department/"><button class="btn btn-default">Quản lý phòng ban</button></a>
  <a href="${pageContext.request.contextPath}/"><button disabled="disabled">Quản lý chức danh</button></a>
</div>
	<br />
	<h4>
		<b>${formTitle}</b>
	</h4>

	<form:form modelAttribute="departmentForm" method="POST"
		action="updateDepartment">
		<div class="table table-bordered">	
			<table class="table">
				<tbody>
					<tr>
						<td>Mã phòng:</td>
						<td><form:input path="departmentId" size="12" required="required"/>	</td>						
					</tr>
					<tr>
						<td>Tên phòng:</td>
						<td><form:input path="departmentName" size="24" required="required"/></td>
					</tr>	
					<tr>
						<td>Ghi chú:</td>
						<td colspan="5"><form:textarea path="desc" /></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td><input type="submit" value="Submit" /> <a
							href="${pageContext.request.contextPath}/department/">Cancel</a></td>
						<td>&nbsp;</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form:form>
</body>
</html>