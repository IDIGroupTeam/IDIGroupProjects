<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
<!-- Include Twitter Bootstrap and jQuery: -->

<script src="${url}/public/js/jquery.min.js"></script>
<script src="${url}/public/js/bootstrap-datetimepicker.min.js"></script>
<script src="${url}/public/js/bootstrap-datetimepicker.vi.js"></script>

<link rel="stylesheet" href="${url}/public/css/bootstrap.min.css" type="text/css" />
<script type="text/javascript" src="${url}/public/js/bootstrap.min.js"></script>

<!-- Include the plugin's CSS and JS: -->
<script type="text/javascript" src="${url}/public/js/bootstrap-multiselect.js"></script>

<script type="text/javascript">
var $j = jQuery.noConflict();
$j(function() {
	$j(".datetime").datetimepicker({
		//language : 'vi',
		format : 'dd/mm/yyyy',
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		minView : 2,
		forceParse : 0,
		pickerPosition : "bottom-left"
	});
	
	$j("#department")
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
							idsSel = "<option value=''></option>";						
							for (i = 0; i < obj.length; i++) {
								idsSel += "<option value='" + obj[i].idsSel + "'>"										
										+ obj[i].fullName
										+ ", chức vụ: "
										+ obj[i].jobTitle
										+ "</option>";
							}
							$j("#ids").html(idsSel);
						}
					});
				
			});
	});

</script>
<title>Báo cáo công việc</title>
</head>
<body>	
	<a href="${pageContext.request.contextPath}/"><button
		class="btn btn-lg btn-primary btn-sm">Danh sách tất cả
		công việc</button></a>
	<a href="${pageContext.request.contextPath}/listTasksOwner"><button
		class="btn btn-lg btn-primary btn-sm">Công việc của tôi</button></a><br/><br/>
	<form:form action="generateTaskReport"
		modelAttribute="taskReportForm" method="POST">				
		<table class="table table-bordered table-hover">
			<tr>
				<td>Từ ngày:(*)</td>
				<td>
					<div class="input-group date datetime smallform">
						<form:input path="fromDate" class="form-control"
						 placeholder="dd/mm/yyyy" autocomplete="off" required="required"/>
						<span class="input-group-addon"><span
							class="glyphicon glyphicon-calendar"></span></span>
					</div>	
<%-- 				<form:input path="fromDate" type="date"
					required="required" class="form-control animated"/> --%>
				</td>
				<td>Đến ngày:(*) </td>
				<td>
					<div class="input-group date datetime smallform">
						<form:input path="toDate" class="form-control"
						 placeholder="dd/mm/yyyy" autocomplete="off" required="required"/>
						<span class="input-group-addon"><span
							class="glyphicon glyphicon-calendar"></span></span>
					</div>	
<%-- 				<form:input path="toDate" type="date" value="${taskReportForm.toDate}"
					required="required" class="form-control animated"/> --%>
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
				<td title="Mặc định là chọn tất cả">Nhân viên:</td>
				<td>
					<%-- <form:select path="employeeId" class="form-control" multiple="multiple" size="3">
						<form:option value="0" label="Tất cả nhân viên"></form:option>
						<form:options items="${employeesList}" var="employeeId"/>
					</form:select> --%>
					<form:select path="ids" class="form-control" multiple="multiple" size="10">
						<form:option value="" label=""></form:option>
						<form:options items="${employeesList}" var="ids"/>
					</form:select>
				</td>
			</tr>
		</table>
		<input class="btn btn-lg btn-primary btn-sm" type="submit" name="generateTaskReport" value="Tạo báo cáo" /> &nbsp;
		<input class="btn btn-lg btn-primary btn-sm" type="submit" name="summary" value="Thống kê khối lượng công việc" /> &nbsp;
		<input class="btn btn-lg btn-primary btn-sm" type="submit" name="chat" value="Vẽ biểu đồ" /> &nbsp;
		<c:if test="${chat > 0}">
			<table align="center">
				<tr>
					<td><img src="${url}${chart}" alt="chart" width="750" height="400" /></td>
				</tr>
			</table>
		</c:if>
	</form:form>	
</body>
</html>