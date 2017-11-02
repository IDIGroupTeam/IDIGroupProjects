<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Tập đoàn IDI - Quản lý lịch sử công tác</title>
<style>
.error-message {
	color: red;
	font-size: 90%;
	font-style: italic;
}
</style>
</head>
<body>
	<form:form modelAttribute="workHistoryForm" method="POST"
		action="insertWorkHistory">
		<div class="table table-bordered">
			<table class="table">
				<tbody>
					<tr>
						<td colspan="4" nowrap="nowrap" bgcolor="#F6CED8">Thông tin lịch sử công tác</td>
					</tr>
					<tr>
						<td bgcolor="#E6E6E6">Chọn NV:</td>
						<td><form:select path="employeeId">
								<form:options items="${employeeMap}" />
							</form:select></td>
						<td bgcolor="#E6E6E6">Lương:</td>
						<td><form:input path="salary" maxlength="12"
								required="required" /></td>
					</tr>
					<tr>
						<td bgcolor="#E6E6E6">Từ ngày:</td>
						<td><form:input path="fromDate" type="date"
								required="required" /></td>
						<td bgcolor="#E6E6E6">Đến ngày:</td>
						<td><form:input path="toDate" type="date" required="required" /></td>
					</tr>
					<tr>
						<td bgcolor="#E6E6E6">Chức vụ:</td>
						<td><form:select path="title">
								<form:options items="${titleMap}" />
							</form:select></td>
						<td bgcolor="#E6E6E6">Phòng:</td>
						<td><form:select path="department">
								<form:options items="${departmentMap}" />
							</form:select></td>

					</tr>
					<tr>
						<td bgcolor="#E6E6E6">Thuộc cty:</td>
						<td><form:input path="company" required="required"
								maxlength="32" /></td>
						<td bgcolor="#E6E6E6">Thành tích:</td>
						<td><form:input path="achievement" maxlength="32" /></td>		
					</tr>
					<tr>
						<td bgcolor="#E6E6E6">Nhận xét:</td>
						<td colspan="4"><form:textarea path="appraise" cols="64" maxlength="64"/></td>
					<tr>
						<td>&nbsp;</td>
						<td align="right"><input type="submit" value="Lưu" /> <a
							href="${pageContext.request.contextPath}/workHistory/"><button>Thoát</button></a></td>
						<td>&nbsp;</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form:form>
</body>
</html>