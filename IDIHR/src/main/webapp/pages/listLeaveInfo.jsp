<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
<!-- Initialize the plugin: -->
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
<script type="text/javascript">
$(function() {
	$("#dept")
		.change(
			function() {
				
				// Lấy dữ liệu của phòng
				val = $(this).val();
					$							
					.ajax({						
						dataType : "json",
						url : "${url}/timekeeping/selection",
						data : {							
							dept : val
						},
						success : function(obj) {							
							eIdSel = "<option value='0'>Tất cả nhân viên</option>";
							for (i = 0; i < obj.length; i++) {
								eIdSel += "<option value='" + obj[i].employeeId + "'>"
										+ "Mã NV " + obj[i].employeeId +", "
										+ obj[i].fullName
										+ ", "
										+ obj[i].jobTitle
										+ "</option>";
							}
							$("#eId").html(eIdSel);
						}
					});
				
			});
});

	function ConfirmDelete() {
	  return confirm("Bạn có chắc chắn muốn xóa không?");
	}
</script>
<title>Thông tin ngày nghỉ, công tác, làm thêm ...</title>
<style>
table {
	font-family: arial, sans-serif;
	border-collapse: collapse;
	width: 100%;
}

td, th {
	border: 1px solid #E8E3E3;
	text-align: left;
	padding: 8px;
}

tr:nth-child(even) {
	background-color: #E8E3E3;
}
</style>
</head>
<body>
	<a href="${url}/timekeeping/addLeaveInfo"><button
			class="btn btn-lg btn-primary btn-sm">Thêm mới</button></a>
	<a href="${url}/timekeeping/"><button
			class="btn btn-lg btn-primary btn-sm">Quay lại thông tin chấm công</button></a>
	<br />	
	<br />
	<form:form action="leaveInfo" modelAttribute="leaveInfoForm" method="GET">
		<table class="table">
			<tr>
				<td>Chọn xem từ ngày:(*) &nbsp;<form:input path="date" type="date"
						required="required" class="form-control animated"/></td>
				<td>Đến ngày:(*) &nbsp;<form:input path="toDate" type="date"
						required="required" class="form-control animated"/></td>
				<td>Phòng: &nbsp;
					<form:select path="dept" class="form-control animated">
						<form:option value="all" label="Tất cả phòng ban"></form:option>
						<form:options items="${departmentMap}" var="dept"/>
					</form:select>
				</td>
				<td>Nhân viên: &nbsp;
					<form:select path="eId" class="form-control animated">
						<form:option value="0" label="Tất cả nhân viên"></form:option>
						<form:options items="${employeeMap}" var="eId"/>
					</form:select>
				</td>
				<td align="center">&nbsp;&nbsp;
					<input class="btn btn-lg btn-primary btn-sm" type="submit"
					value="Xem danh sách" />
				</td>
			</tr>
		</table>
			<br/>
			<c:if test="${not empty message}">
				<div class="alert alert-success">${message}</div>
			</c:if>
			
	</form:form>
	<br/>
	<div class="table-responsive">
		<table class="table table-bordered">
			<tr>
				<th>Mã NV</th>
				<th>Họ tên</th>
				<th>Bộ phận</th>
				<th>Chức vụ</th>
				<th>Ngày</th>
				<th>Loại</th>
				<th>Số giờ/lần</th>
				<th>Ghi chú</th>

				<!-- <th>Sửa</th> -->
				<th style="color: red;">Xóa</th>
			</tr>
			<c:forEach var="leaveInfo" items="${leaveInfos}">
				<tr style="font-size: 10">
					<td>${leaveInfo.employeeId}</td>
					<td nowrap="nowrap">${leaveInfo.employeeName}</td>
					<td>${leaveInfo.department}</td>
					<td>${leaveInfo.title}</td>
					<td nowrap="nowrap">${leaveInfo.date}</td>
					<c:if test="${leaveInfo.timeValue == 4}">
						<td nowrap="nowrap">${leaveInfo.leaveName} nửa ngày</td>
					</c:if>
					<c:if test="${leaveInfo.timeValue != '4'}">
						<td nowrap="nowrap">${leaveInfo.leaveName}</td>
					</c:if>
					<td>${leaveInfo.timeValue}</td>
					<td>${leaveInfo.comment}</td>

					<%-- <td bgcolor="#F5F6CE"><a href="editLeaveInfo?employeeId=${leaveInfo.employeeId}&date=${leaveInfo.date}&leaveType=${leaveInfo.leaveType}">Sửa</a></td> --%>
					<td><a style="color: red;"
						href="deleteLeaveInfo?employeeId=${leaveInfo.employeeId}&date=${leaveInfo.date}&leaveType=${leaveInfo.leaveType}" Onclick="return ConfirmDelete()">Xóa</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>