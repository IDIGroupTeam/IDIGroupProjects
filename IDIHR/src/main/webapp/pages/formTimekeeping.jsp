<%@page import="com.idi.hr.common.PropertiesManager"%>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

</script>
<title>Dữ liệu chấm công nhân viên</title>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
</head>
<body>
	<%
		//	PropertiesManager hr = new PropertiesManager("hr.properties");
		//	int timeInMorning = Integer.parseInt(hr.getProperty("TIME_CHECK_IN_MORNING"));
		//	int timeOutMorning = Integer.parseInt(hr.getProperty("TIME_CHECK_OUT_MORNING"));
		//	int timeInAfternoon = Integer.parseInt(hr.getProperty("TIME_CHECK_IN_AFTERNOON"));
		//	int timeOutAfternoon = Integer.parseInt(hr.getProperty("TIME_CHECK_OUT_AFTERNOON"));
	%>
	<a href="${url}/timekeeping/prepareGenerateLeaveReport">
		<button class="btn btn-lg btn-primary btn-sm">Xuất báo cáo</button>
	</a>
	<a href="${url}/timekeeping/leaveInfo">
		<button class="btn btn-lg btn-primary btn-sm">Chấm công phát sinh</button>
	</a> 	
	<a href="${url}/timekeeping/listWorkingDay">
		<button class="btn btn-lg btn-primary btn-sm">Định nghĩa số ngày công chuẩn</button>
	</a>
	<br />
	<br />
	<form:form action="listByDate" modelAttribute="leaveInfoForm"
		method="POST">
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
				<td align="center"><input class="btn btn-lg btn-primary btn-sm" type="submit"
					value="Xem danh sách" /></td>
			</tr>
		</table>
		<c:if test="${not empty message}">
			<div class="alert alert-success">${message}</div>
		</c:if>
	</form:form>
	<form:form action="updateData" modelAttribute="timekeepingForm"
		method="POST" enctype="multipart/form-data">
		<table class="table">
			<tr>
				<td><b><i>Cập nhật dữ liệu chấm công từ file excel:</i></b></td>
				<td align="left"><input class="btn btn-lg btn-primary btn-sm"
					name="timeKeepingFile" type="file" /></td>
				<td align="left"><input class="btn btn-lg btn-primary btn-sm"
					type="submit" value="Cập nhật" /></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</table>
		<c:if test="${not empty comment}">
			<div class="alert alert-info">${comment}</div>
		</c:if>
		<br />
		<div class="table-responsive">
			<b style="color: blue;">Chấm công phát sinh</b>
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
				</tr>
				<c:forEach var="leaveInfo" items="${leaveInfos}">
					<tr style="font-size: 10">
						<td>${leaveInfo.employeeId}</td>
						<td>${leaveInfo.employeeName}</td>
						<td>${leaveInfo.department}</td>
						<td>${leaveInfo.title}</td>
						<td>${leaveInfo.date}</td>
						<c:if test="${leaveInfo.timeValue == 4}">
							<td>${leaveInfo.leaveName} nửa ngày</td>
						</c:if>
						<c:if test="${leaveInfo.timeValue != '4'}">
							<td>${leaveInfo.leaveName}</td>
						</c:if>
						<td>${leaveInfo.timeValue}</td>
						<td>${leaveInfo.comment}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<b style="color:blue;">Dữ liệu từ máy chấm công</b>
		<table class="table table-bordered">
			<tr>
				<th>Mã NV</th>
				<th>Họ tên</th>
				<th>Bộ phận</th>
				<th>Chức vụ</th>
				<th>Ngày</th>
				<th>Giờ vào</th>
				<th>Giờ ra</th>
				<th>ĐM sáng</th>
				<th>ĐM chiều</th>
				<th>VS sáng</th>
				<th>VS chiều</th>
				<th>Ghi chú</th>
			</tr>
			<c:forEach var="timekeeping" items="${timekeepings}">
				<tr style="font-size: 10">
					<td>${timekeeping.employeeId}</td>
					<td>${timekeeping.employeeName}</td>
					<td>${timekeeping.department}</td>
					<td>${timekeeping.title}</td>
					<td>${timekeeping.date}</td>
					<c:if test="${not empty timekeeping.timeIn}">
						<td>${timekeeping.timeIn}</td>
					</c:if>
					<c:if test="${empty timekeeping.timeIn}">
						<td bgcolor="EDC7D0">${timekeeping.timeIn}</td>
					</c:if>
					<c:if test="${not empty timekeeping.timeOut}">
						<td>${timekeeping.timeOut}</td>
					</c:if>
					<c:if test="${empty timekeeping.timeOut}">
						<td bgcolor="EDC7D0">${timekeeping.timeOut}</td>
					</c:if>
					<c:if test="${not empty timekeeping.comeLateM}">
						<td bgcolor="#F5F6CE"><fmt:formatNumber value="${timekeeping.comeLateM/60}" pattern="0"/>h<fmt:formatNumber value="${timekeeping.comeLateM % 60}" pattern="00"/></td>
					</c:if>
					<c:if test="${empty timekeeping.comeLateM}">
						<td>${timekeeping.comeLateM}</td>
					</c:if>
					<c:if test="${not empty timekeeping.comeLateA}">
						<td bgcolor="#F5F6CE"><fmt:formatNumber value="${timekeeping.comeLateA/60}" pattern="0"/>h<fmt:formatNumber value="${timekeeping.comeLateA % 60}" pattern="00"/></td>
					</c:if>
					<c:if test="${empty timekeeping.comeLateA}">
						<td>${timekeeping.comeLateA}</td>
					</c:if>
					<c:if test="${not empty timekeeping.leaveSoonM}">
						<td bgcolor="#F5F6CE"><fmt:formatNumber value="${timekeeping.leaveSoonM/60}" pattern="0" />h<fmt:formatNumber value="${timekeeping.leaveSoonM % 60}" pattern="00"/></td>
					</c:if>
					<c:if test="${empty timekeeping.leaveSoonM}">
						<td>${timekeeping.leaveSoonM}</td>
					</c:if>
					<c:if test="${not empty timekeeping.leaveSoonA}">
						<td bgcolor="#F5F6CE"><fmt:formatNumber value="${timekeeping.leaveSoonA/60}" pattern="0" />h<fmt:formatNumber value="${timekeeping.leaveSoonA%60}" pattern="00"/></td>
					</c:if>
					<c:if test="${empty timekeeping.leaveSoonA}">
						<td>${timekeeping.leaveSoonA}</td>
					</c:if>
					<td>${timekeeping.comment}</td>
				</tr>
			</c:forEach>
		</table>
	</form:form>
</body>
</html>