<%@page import="com.idi.hr.common.PropertiesManager"%>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
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
	<a href="${pageContext.request.contextPath}/timekeeping/leaveInfo">
		<button class="btn btn-primary">Chấm công phát sinh</button>
	</a> &nbsp;
	<a href="${pageContext.request.contextPath}/timekeeping/report">
		<button class="btn btn-primary">Xuất báo cáo</button>
	</a>
	<br />
	<br />
	<form:form action="updateData" modelAttribute="timekeepingForm"
		method="POST" enctype="multipart/form-data">
		<table class="table">
			<tr>
				<td><b><i>Cập nhật dữ liệu chấm công từ file excel:</i></b></td>
				<td align="left"><input class="btn btn-primary"
					name="timeKeepingFile" type="file" /></td>
				<td align="left"><input class="btn btn-primary" type="submit"
					value="Cập nhật" /></td>
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
		<table class="table table-bordered">
			<tr bgcolor="#D8D8D8">
				<th>Mã NV</th>
				<th>Họ tên</th>
				<th>Bộ phận</th>
				<th>Chức vụ</th>
				<th>Ngày</th>
				<th>Giờ vào</th>
				<th>Giờ ra</th>
				<th>Đến muộn sáng</th>
				<th>Đến muộn chiều</th>
				<th>Về sớm sáng</th>
				<th>Về sớm chiều</th>
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
						<td bgcolor="#F5F6CE">${timekeeping.comeLateM}</td>
					</c:if>
					<c:if test="${empty timekeeping.comeLateM}">
						<td>${timekeeping.comeLateM}</td>
					</c:if>
					<c:if test="${not empty timekeeping.comeLateA}">
						<td bgcolor="#F5F6CE">${timekeeping.comeLateA}</td>
					</c:if>
					<c:if test="${empty timekeeping.comeLateA}">
						<td>${timekeeping.comeLateA}</td>
					</c:if>
					<c:if test="${not empty timekeeping.leaveSoonM}">
						<td bgcolor="#F5F6CE">${timekeeping.leaveSoonM}</td>
					</c:if>
					<c:if test="${empty timekeeping.leaveSoonM}">
						<td>${timekeeping.leaveSoonM}</td>
					</c:if>
					<c:if test="${not empty timekeeping.leaveSoonA}">
						<td bgcolor="#F5F6CE">${timekeeping.leaveSoonA}</td>
					</c:if>
					<c:if test="${empty timekeeping.leaveSoonA}">
						<td>${timekeeping.leaveSoonA}</td>
					</c:if>					
				</tr>
			</c:forEach>
		</table>
	</form:form>
</body>
</html>