<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
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
	<a href="${url}/insurance/"><button class="btn btn-primary btn-sm">Quay lại danh sách bảo hiểm</button></a>
	<br/><br/>
	<form:form modelAttribute="insuranceForm" method="POST"
		action="updateInsurance">
		<c:if test="${not empty duplicate}">
			<table><tr><td style="color: red;"> ${duplicate} </td></tr></table>	
		</c:if>
			<table class="table">
				<form:hidden path="socicalInsuNo"/>
				<form:hidden path="employeeId"/>
				<tbody>
					<tr>
						<td colspan="4" nowrap="nowrap" bgcolor="#C4C4C4">Thông tin
							Bảo hiểm xã hội</td>
					</tr>
					<tr>
						<td bgcolor="#EEEEEE">Nhân viên:</td>
						<td><form:select path="employeeId" class="form-control animated" disabled="true">
								<form:options items="${employeeMap}" />
							</form:select></td>
						<td bgcolor="#EEEEEE" title="Vui lòng nhập số lớn hơn hoặc bằng 0">% Tỷ lệ đóng của công ty:(*) </td>
						<td><form:input path="percentSInsuC" size="6" maxlength="6" min="0" step="0.1" type="number"
								required="required" class="form-control animated"/></td>
					</tr>
					<tr>
						<td bgcolor="#EEEEEE">Số sổ BHXH:(*)</td>
						<td><form:input path="socicalInsuNo" maxlength="12" size="12"
								required="required" class="form-control animated" disabled="true"/></td>
						<td bgcolor="#EEEEEE" title="Vui lòng nhập số lớn hơn hoặc bằng 0">% Tỷ lệ đóng của người LĐ:(*) </td>
						<td><form:input path="percentSInsuE" size="6" maxlength="6" min="0" step="0.1" type="number"
								required="required" class="form-control animated"/></td>
					</tr>
					<tr>
						<td bgcolor="#EEEEEE">Lương BH:(*)</td>
						<td><form:input path="salarySocicalInsu" size="12"
								class="form-control animated" maxlength="12" required="required" /></td>
						<td bgcolor="#EEEEEE">Vùng lương:</td>
						<td><form:select path="salaryZone" class="form-control animated">
								<form:option value="1" label="Vùng 1" />
								<form:option value="2" label="Vùng 2" />
								<form:option value="3" label="Vùng 3" />
								<form:option value="4" label="Vùng 4" />
							</form:select></td>	
					</tr>
					<tr>
						<%--<td bgcolor="#E6E6E6">Cty đóng:</td>
						<td><form:input path="companyPay" maxlength="64"/></td> --%>
						<td bgcolor="#EEEEEE">Nơi đóng:(*)</td>
						<td><form:input path="place" required="required"
								maxlength="64" class="form-control animated"/></td>
						<td bgcolor="#EEEEEE">Phương thức đóng:</td>
						<td><form:select path="payType" class="form-control animated">
								<form:option value="T" label="Theo tháng" />
								<form:option value="Q" label="Theo quý" />
								<form:option value="N" label="Theo năm" />
							</form:select></td>
					</tr>
					<tr>
						<td bgcolor="#EEEEEE">Tình trạng đóng:</td>
						<td><form:select path="status" class="form-control animated">
								<form:option value="Dang nop" label="Đang nộp" />
								<form:option value="Dang nghi thai san"
									label="Đang nghỉ thai sản" />
								<form:option value="Dang nghi om" label="Đang nghỉ ốm" />
								<form:option value="Dang nghi khong luong"
									label="Đang nghỉ không lương" />
								<form:option value="Da nghi han" label="Đã nghỉ hẳn" />
							</form:select></td>
					</tr>
					<tr>
						<td colspan="4" nowrap="nowrap" bgcolor="#C4C4C4">Thông tin
							Bảo hiểm y tế</td>
					</tr>
					<tr>
						<td bgcolor="#EEEEEE">Số thẻ BHYT:(*)</td>
						<td><form:input path="hInsuNo" maxlength="32" class="form-control animated" required="required"/></td>
						<td bgcolor="#EEEEEE">Nơi ĐK khám chữa bệnh:</td>
						<td><form:input path="hInsuPlace" maxlength="64" class="form-control animated"/></td>
					<tr>
					<tr>
						<td bgcolor="#EEEEEE">Ghi chú:</td>
						<td colspan="3"><form:textarea path="comment" cols="64" class="form-control animated"/></td>
					</tr>
				</tbody>
			</table>
			<input class="btn btn-primary btn-sm" type="submit" value="Lưu" />
	</form:form>
</body>
</html>