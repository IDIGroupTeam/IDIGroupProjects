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
	$(document).ready(function() {
		$('#example-getting-started').multiselect();
	});
</script>
<title>Báo cáo thông tin ngày nghỉ, công tác, làm thêm ...</title>
</head>
<body>
	<form:form action="generateLeaveReport"
		modelAttribute="leaveReportForm" method="POST">
		<input class="btn btn-lg btn-primary btn-sm" type="submit" value="Tạo báo cáo" name="Lưu"/> &nbsp;		
		<br/><br/>
		<table class="table table-bordered">
			<jsp:useBean id="now" class="java.util.Date" />
			<fmt:formatDate var="year" value="${now}" pattern="yyyy" />
			<tr>
				<td bgcolor="#FAFAFA" width="30%">Chọn năm:</td>
				<td><form:select path="yearReport">
						<c:forEach begin="0" end="5" varStatus="loop">
							<c:set var="currentYear" value="${year - loop.index}" />
							<option value="${currentYear}"
								${form.yearReport == currentYear ? 'selected="selected"' : ''}>${currentYear}</option>
						</c:forEach>
					</form:select></td>
				<td bgcolor="#FAFAFA">Chọn tháng:</td>
				<td><form:select path="monthReport">
						<form:option value="" label="Cả năm" />
						<form:option value="1" label="Tháng 1" />
						<form:option value="2" label="Tháng 2" />
						<form:option value="3" label="Tháng 3" />
						<form:option value="4" label="Tháng 4" />
						<form:option value="5" label="Tháng 5" />
						<form:option value="6" label="Tháng 6" />
						<form:option value="7" label="Tháng 7" />
						<form:option value="8" label="Tháng 8" />
						<form:option value="9" label="Tháng 9" />
						<form:option value="10" label="Tháng 10" />
						<form:option value="11" label="Tháng 11" />
						<form:option value="12" label="Tháng 12" />						
					</form:select></td>
			</tr>
			<tr>
				<td bgcolor="#FAFAFA">Chọn phòng ban:</td>
				<td><form:select path="department">
						<form:option value="all" label="Tất cả phòng ban"></form:option>
						<form:options items="${departmentMap}" />
					</form:select></td>
				<td bgcolor="#FAFAFA">Chọn cá nhân:</td>
				<td><form:select path="employeeId">
						<form:option value="0" label="Tất cả nhân viên"></form:option>
						<form:options items="${employeeMap}" />
					</form:select></td>
			</tr>
			<tr>
				<td bgcolor="#FAFAFA">Lựa chọn các thông tin cần báo cáo</td>
				<td colspan="3"><form:select path="leaveTypeReport"
						multiple="multiple" size="12">
						<form:option value="DM" label="Đi muộn"></form:option>
						<form:option value="VS" label="Về sớm"></form:option>
						<form:options items="${leaveTypeMap}" />
					</form:select></td>
			</tr>
		</table>
	</form:form>
	<a href="${url}/timekeeping/"><button class="btn btn-lg btn-primary btn-sm"> Quay lại thông tin chấm công</button></a>
</body>
</html>