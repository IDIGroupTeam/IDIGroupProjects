<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
<!-- Include Twitter Bootstrap and jQuery: -->
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>

<!-- Include the plugin's CSS and JS: -->
<script type="text/javascript" src="js/bootstrap-multiselect.js"></script>
<link rel="stylesheet" href="css/bootstrap-multiselect.css"
	type="text/css" />

<!-- Initialize the plugin: -->
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
<script type="text/javascript">
$(function() {
	$("#department")
		.change(
			function() {
				// Lấy dữ liệu của phòng
				val = $(this).val();
					$							
					.ajax({
						dataType : "json",
						url : "${url}/KPI/selection",
						data : {
							department : val
						},
						success : function(obj) {
							employeeIdSel = "<option value='0'>Tất cả nhân viên</option>";
							for (i = 0; i < obj.length; i++) {
								employeeIdSel += "<option value='" + obj[i].employeeId + "'>"
										+ "Mã NV " + obj[i].employeeId +", "
										+ obj[i].fullName
										+ ", chức vụ: "
										+ obj[i].jobTitle
										+ "</option>";
							}
							$("#employeeId").html(employeeIdSel);
						}
					});
				
			});
});
</script>
<title>Báo cáo theo các tiêu chí được chọn ...</title>
</head>
<body>
	<a href="${url}/KPI/"><button class="btn btn-lg btn-primary btn-sm">Khôi phục thông tin lựa chọn như ban đầu</button></a> <br><br>
	<form:form action="generateReport" modelAttribute="reportForm" method="POST">		
		<table class="table table-bordered">
			<jsp:useBean id="now" class="java.util.Date" />
			<fmt:formatDate var="year" value="${now}" pattern="yyyy" />
			<tr>
				<td bgcolor="#FAFAFA" width="30%">Năm:</td>
				<td><form:select path="yearReport" value="${currentYear}" class="form-control animated">
						<form:option value="${currentYear}" label="${currentYear}" />
						<form:option value="${currentYear-1}" label="${currentYear-1}" />
						<form:option value="${currentYear-2}" label="${currentYear-2}" />
						<form:option value="${currentYear-3}" label="${currentYear-3}" />
					<%--<c:forEach begin="0" end="3" varStatus="loop">
							<c:set var="currentYear" value="${year - loop.index}" />
							<option value="${currentYear}"
								${form.yearReport == currentYear ? 'selected="selected"' : ''}>${currentYear}</option>
						</c:forEach> --%>
					</form:select></td>
				<td bgcolor="#FAFAFA">Tháng:</td>
				<td>
					<form:select path="monthReport" class="form-control animated">
						<form:option value="" label="Cả năm" />
						<form:option value="01" label="Tháng 1" />
						<form:option value="02" label="Tháng 2" />
						<form:option value="03" label="Tháng 3" />
						<form:option value="04" label="Tháng 4" />
						<form:option value="05" label="Tháng 5" />
						<form:option value="06" label="Tháng 6" />
						<form:option value="07" label="Tháng 7" />
						<form:option value="08" label="Tháng 8" />
						<form:option value="09" label="Tháng 9" />
						<form:option value="10" label="Tháng 10" />
						<form:option value="11" label="Tháng 11" />
						<form:option value="12" label="Tháng 12" />						
					</form:select>
				</td>
			</tr>
			<tr>
				<td bgcolor="#FAFAFA">Phòng ban:</td>
				<td><form:select path="department" class="form-control animated">
						<form:option value="all" label="Tất cả phòng ban"></form:option>
						<form:options items="${departmentMap}" />
					</form:select></td>
				<td bgcolor="#FAFAFA" nowrap="nowrap">Nhân viên:</td>
				<td><form:select path="employeeId" class="form-control animated">
						<form:option value="0" label="Tất cả nhân viên"></form:option>
						<form:options items="${employeeMap}" />
					</form:select></td>
			</tr>
			<tr>
				<td bgcolor="#FAFAFA">Thông tin cần báo cáo:</td>
				<td colspan="3">
				    <form:select path="leaveTypeReport" class="form-control animated">
						<form:option value="TTLD" label="Trạng thái lao động"></form:option>
						<form:option value="TLLDTP" label="Tỷ lệ lao động theo phòng ban"></form:option>
						<form:option value="DM" label="Đi muộn"></form:option>
						<form:option value="VS" label="Về sớm"></form:option>
						<form:options items="${leaveTypeMap}" />
					</form:select></td>
			</tr>
		</table>
		<input class="btn btn-lg btn-primary btn-sm" type="submit" value="Tạo biểu đồ"/> &nbsp;
		<table align="center">
			<tr>
				<td><img src="${url}${chart}" alt="chart" width="750" height="400" /></td>
			</tr>
		</table>		
	</form:form>	
</body>
</html>