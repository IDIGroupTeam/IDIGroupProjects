<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Tập đoàn IDI - Sửa thông tin quá trình đóng BH</title>
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
		moneyConvert("salarySocicalInsu");
	});
</script>
</head>
<body>
<a href="${pageContext.request.contextPath}/insurance/listProcessInsurance?socicalInsuNo=${socicalInsuNo}&employeeId=${employeeId}"><button class="btn btn-primary btn-sm">Quay	lại danh sách</button></a>
<br/><br/>
	<form:form modelAttribute="pInsuranceForm" method="POST"
		action="updateProcessInsurance?employeeId=${employeeId}">
			<table class="table">
				<tbody>
					<tr>
						<td colspan="4" nowrap="nowrap" bgcolor="#F6CED8">Thông tin	bảo hiểm xã hội</td>
					</tr>
					<tr>
						<td bgcolor="#E6E6E6">Nhân viên:</td>
						<td colspan="3"><c:out value="${name}" /></td>
					</tr>
					<tr>
						<td bgcolor="#E6E6E6">Số sổ BHXH:</td>
						<td><form:input path="socicalInsuNo" readonly="true" class="form-control animated"/></td>
					</tr>
					<tr>
						<td bgcolor="#E6E6E6">Mức lương đóng BH(*):</td>
						<td><form:input path="salarySocicalInsu" size="12"
								maxlength="12" required="required" class="form-control animated"/></td>
					<td bgcolor="#E6E6E6">Công ty đóng(*):</td>
						<td><form:input path="companyPay" required="required" class="form-control animated"/></td>							
					</tr>
					<tr>
						<td colspan="4" nowrap="nowrap" bgcolor="#F6CED8">Thời gian	đóng</td>
					</tr>
					<tr>
						<td bgcolor="#E6E6E6">Từ ngày(*):</td>
						<td><form:input path="fromDate" required="required"
								type="date" readonly="true" class="form-control animated"/></td>
						<td bgcolor="#E6E6E6">Đến ngày:</td>
						<td><form:input path="toDate" type="date" class="form-control animated"/></td>
					</tr>
					<tr>
						<td bgcolor="#E6E6E6">Ghi chú:</td>
						<td colspan="3"><form:textarea path="comment" cols="64" class="form-control animated"/></td>					
					</tr>
				</tbody>
			</table>
			<input type="submit" value="Lưu" class="btn btn-primary btn-sm"/>
	</form:form>
</body>
</html>