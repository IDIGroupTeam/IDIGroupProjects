<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
<title>Chấm công nhân viên</title>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
</head>
<body>
	<form:form modelAttribute="timekeepingForm" method="POST"
		action="insertOrUpdateTimekeeping">	
	<div class="table-responsive">		
		<table class="table table-striped">
			<tr>
				<th>Mã NV</th>
				<th>Họ tên</th>
				<th>ĐM sáng</th>
				<th>VS sáng</th>
				<th>ĐM chiều </th>
				<th>VS chiều</th>
				<th>Nghỉ ...</th>
				<th>Làm thêm giờ ...</th>
				<th>Số giờ làm thêm</th>
				<th>Số ngày đã nghỉ</th>
				<th>Số ngày phép còn</th>
			</tr>
			<c:forEach var="timekeeping" items="${timekeepings}">
				<tr style="font-size: 10">
					<td>${timekeeping.employeeId}</td>
					<td>${timekeeping.employeeName}</td>					
					<td><form:checkbox path="comeLateM" value="Y"/>${timekeeping.comeLateM}</td>
					<td contenteditable='true'> <form:checkbox path="leaveSoonM" value="Y"/> ${timekeeping.leaveSoonM}</td>
					<td><div contentEditable='true'> <form:checkbox path="comeLateA" value="Y"/> ${timekeeping.comeLateA}</div></td>
					<td>					
						<form:checkbox path="leaveSoonM" value="Y" />

					</td>
					<td><form:select path="leaveType">
								<form:option value="" label="--Chọn--" />
								<form:option value="NP" label="Nghỉ phép" />
								<form:option value="NB" label="Nghỉ bù" />								
								<form:option value="NO" label="Nghỉ ốm" />
							</form:select>${timekeeping.leaveType}</td>					
					<td><form:select path="overtimeType">
								<form:option value="" label="--Chọn--" />
								<form:option value="NT" label="Ngày thường" />
								<form:option value="CT" label="Cuối tuần" />								
								<form:option value="NL" label="Ngày lễ" />
							</form:select>${timekeeping.overtimeType}</td>
					<td><form:input path="overTimeValue" size="1"/>${timekeeping.overTimeValue}</td>
					<td>${timekeeping.leaveUsed}</td>
					<td>${timekeeping.leaveRemain}</td>
				</tr>
			</c:forEach>
			<tr><td colspan="6" align="right"><input type="submit" value="Lưu" name="Lưu" /></td></tr>
		</table>
		<c:if test="${not empty message}">
			<div class="alert alert-success">${message}</div>
		</c:if>
	</div>
	</form:form>
</body>
</html>