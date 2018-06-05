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
<script type="text/javascript" src="js/bootstrap.min.js"></script>

<!-- Include the plugin's CSS and JS: -->
<script type="text/javascript" src="js/bootstrap-multiselect.js"></script>

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
					.ajax({dataType : "json", url : "${url}/selection",
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
<title>Báo cáo công việc</title>
</head>
<body>
	<a href="${url}/"><button class="btn btn-lg btn-primary btn-sm"> Quay lại dach sách công việc</button></a>
	<form:form action="generateTaskReport"
		modelAttribute="taskReportForm" method="POST">				
		<br/>
		<table class="table table-bordered table-hover">
			<tr>
				<td>Từ ngày:(*)</td>
				<td><form:input path="fromDate" type="date"
					required="required" class="form-control animated"/>
				</td>
				<td>Đến ngày:(*) </td>
				<td><form:input path="toDate" type="date"
					required="required" class="form-control animated"/>
				</td>
			</tr>
			<tr>			
				<td>Phòng: </td>
				<td>
					<form:select path="department" class="form-control animated">
						<form:option value="all" label="Tất cả phòng ban"></form:option>
						<form:options items="${departmentMap}" var="department"/>
					</form:select>
				</td>
				<td>Nhân viên:</td>
				<td>
					<form:select path="employeeId" class="form-control animated">
						<form:option value="0" label="Tất cả nhân viên"></form:option>
						<form:options items="${employeesList}" var="employeeId"/>
					</form:select>
				</td>
			</tr>
		</table>
		<input class="btn btn-lg btn-primary btn-sm" type="submit" value="Tạo báo cáo" /> &nbsp;
	</form:form>	
</body>
</html>