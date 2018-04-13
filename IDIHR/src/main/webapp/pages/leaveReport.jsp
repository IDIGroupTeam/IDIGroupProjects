<%@page import="java.util.Calendar"%>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
<title>Báo cáo thông tin ngày nghỉ, công tác, làm thêm ...</title>
</head>
<body>
	<a href="${url}/timekeeping/prepareGenerateLeaveReport"><button
			class="btn btn-primary btn-sm">Xuất lại báo cáo</button></a>
	<a href="${url}/timekeeping/"><button
			class="btn btn-primary btn-sm">Quay lại thông tin chấm công</button></a>
	<a href="${url}/timekeeping/leaveInfo"><button
			class="btn btn-primary btn-sm">Quay lại chấm công phát sinh</button></a>
	<a href="${url}/timekeeping/export.xls?month=${month}&year=${year}&department=${department}"><button
			class="btn btn-primary btn-sm">Xuất ra file excel</button></a>		
			
	<br />
	<br />
	<table><tr>
	<c:if test="${month > 0}">
		<td> Tháng: ${month} &nbsp;</td>
	</c:if>
	<c:if test="${year != null}">
		<td> Năm: ${year} &nbsp;</td>
	</c:if>
	<c:if test="${department != 'all' && empty message}">
		<td> Phòng: ${department}</td>
	</c:if>
	<c:if test="${department == 'all'}">
		<td> Tất cả các phòng ban</td>
	</c:if>
	</tr><tr>
	</tr></table>
	<div class="table-responsive">
	<c:if test="${not empty message}">
		<div class="alert alert-success">${message}</div>
		</c:if>
		<table class="table table-bordered">
			<tr bgcolor=B5CBCE>
				<th nowrap="nowrap">Mã NV</th>
				<th>Tên nhân viên</th>
				<th>Ngày vào</th>
				<th>Phòng</th>
				<th>Thâm niên</th>

				<!-- dynamic generate begin  -->
				<c:forEach var="leaveTypeName" items="${leaveForReport}">
					<th>${leaveTypeName.value}</th>
				</c:forEach>
				<!-- dynamic generate end -->

				<!-- <th>Phép còn năm trước</th> -->
				<th>Phép năm nay</th>
				<th>Phép đã sử dụng</th>
				<th>Phép còn lại</th>
			</tr>
			<c:forEach var="leaveReport" items="${leaveReports}">
				<tr style="font-size: 10">
					<td>${leaveReport.employeeId}</td>
					<td nowrap="nowrap">${leaveReport.name}</td>
					<td nowrap="nowrap">${leaveReport.joinDate}</td>
					<td nowrap="nowrap">${leaveReport.department}</td>
					<td nowrap="nowrap">${leaveReport.seniority} tháng</td>

					<!-- dynamic generate begin  -->
					<c:forEach var="leaveTypes" items="${leaveReport.leaveTypes}">
						<c:if test="${leaveTypes.value !='0'}">
							<c:if test="${leaveTypes.key !='TNC'}" >
								<td>${leaveTypes.value}</td>
							</c:if>
							<c:if test="${leaveTypes.key =='TNC'}" >
								<td><a href="listByDate?date=${year}-${month}-01&toDate=viewDetail&dept=${leaveReport.department}&eId=${leaveReport.employeeId}">${leaveTypes.value}</a></td>
							</c:if>
						</c:if>
						<c:if test="${leaveTypes.value == '0'}">
							<td></td>
						</c:if>
					</c:forEach>
					<!-- dynamic generate end -->

				<%-- 	<td bgcolor="#F5F6CE">${leaveReport.restQuata}</td> --%>
					<td bgcolor="#F5F6CE">${leaveReport.quataLeave}</td>
					<td bgcolor="#F5F6CE">${leaveReport.leaveUsed}</td>
					<td bgcolor="#F5F6CE">${leaveReport.leaveRemain}</td>
				</tr>
			</c:forEach>
		</table>		
	</div>
</body>
</html>