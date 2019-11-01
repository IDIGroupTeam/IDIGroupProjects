<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
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

<script src="${url}/public/js/jquery.min.js"></script>
<script src="${url}/public/js/bootstrap-datetimepicker.min.js"></script>
<script src="${url}/public/js/bootstrap-datetimepicker.vi.js"></script>
<script src="${url}/public/js/common.js"></script>
<script type="text/javascript">
	var $j = jQuery.noConflict();
    //alert($j.fn.jquery);
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
	}); 	
	
	$j(function() {
		moneyConvert("salary");
	});
</script> 

<a href="${pageContext.request.contextPath}/workHistory/"><button class="btn btn-primary btn-sm">Quay lại danh sách</button></a>
<br/><br/>
	<form:form modelAttribute="workHistoryForm" method="POST"
		action="updateWorkHistory">
		<table class="table table-bordered">
			<tbody>
				<tr>
					<td colspan="4" nowrap="nowrap" bgcolor="#E6E6E6">Thông tin lịch sử công tác</td>
				</tr>
				<tr></tr>
				<tr>
					<form:hidden path="employeeId" />
					<form:hidden path="fromDate" />
					<td bgcolor="#FAFAFA">Tên NV:</td>
					<td><form:input path="employeeName" disabled="true" class="form-control animated"/></td>
					<td bgcolor="#FAFAFA">Lương:(*)</td>
					<td><form:input path="salary" maxlength="12"
							required="required" class="form-control animated"/></td>
				</tr>
				<tr>
					<td bgcolor="#FAFAFA">Từ ngày:(*)</td>
					<td> 
					<%-- 	<div class="input-group date datetime smallform">
							<form:input path="fromDate" class="form-control"
							 placeholder="dd/mm/yyyy" autocomplete="off" />
							<span class="input-group-addon">
							<span class="glyphicon glyphicon-calendar"></span>
							</span>
						</div> --%>
					<form:input path="fromDate" required="required" disabled="true" class="form-control animated"/>
					</td>
					<td bgcolor="#FAFAFA">Đến ngày:(*)</td>
					<td>
						<div class="input-group date datetime smallform">
							<form:input path="toDate" class="form-control"
							 placeholder="dd/mm/yyyy" autocomplete="off" />
							<span class="input-group-addon"><span
								class="glyphicon glyphicon-calendar"></span></span>
						</div>
						<%-- <form:input path="toDate" type="date" required="required" class="form-control animated"/> --%>
					</td>
				</tr>
				<tr>
					<td bgcolor="#FAFAFA">Chức vụ:</td>
					<td><form:select path="title" class="form-control animated">
							<form:options items="${titleMap}" />
						</form:select></td>
					<td bgcolor="#FAFAFA">Phòng:</td>
					<td><form:select path="department" class="form-control animated">
							<form:options items="${departmentMap}" />
						</form:select>
					</td>
				</tr>
				<tr>
					<td bgcolor="#FAFAFA">Thuộc cty:(*)</td>
					<td><form:input path="company" required="required"
							maxlength="32" class="form-control animated"/></td>
					<td bgcolor="#FAFAFA">Thành tích:</td>
					<td><form:input path="achievement" maxlength="32" class="form-control animated"/></td>		
				</tr>
				<tr>
					<td bgcolor="#FAFAFA">Nhận xét:</td>
					<td colspan="4"><form:textarea path="appraise" cols="64" maxlength="64" class="form-control animated"/></td>
				</tr>
			</tbody>
		</table>
		<input class="btn btn-lg btn-primary btn-sm" type="submit" value="Lưu" />
	</form:form>	 
</body>
</html>