<%@page import="com.idi.task.form.ReportForm"%>
<%@page import="com.idi.task.common.Utils"%>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>

<title>Báo cáo công việc</title>
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
/* 
tr:nth-child(even) {
	background-color: #E8E3E3;
} */
</style>
</head>
<script src="${url}/public/js/bootstrap-combobox.js"></script>
<script>
    // var $x = jQuery.noConflict(true);
    //alert($x.fn.jquery);
/* 	$(function() {		
		$('#sender').combobox();		
	});  */	
 
</script> 
<body>
	<a href="${url}/prepareReport?fDate=${reportForm.fromDate}&tDate=${reportForm.toDate}&eName=${tasks[0].ownerName}&dept=${reportForm.department}"><button class="btn btn-primary btn-sm">Lựa chọn lại thông tin cần báo cáo</button></a>
<%-- 	<c:if test="${tasks.size() > 0}">
		<a href="${url}/exportToPDF?fDate=${reportForm.fromDate}&tDate=${reportForm.toDate}&eName=${tasks[0].ownerName}&dept=${reportForm.department}&eId=${reportForm.employeeId}&sender=${sender}&comment=${comment}"><button class="btn btn-primary btn-sm">Export ra file PDF và gửi báo cáo</button></a>
	</c:if>	 --%>
	<br />
	<h3>Báo cáo công việc từ ngày ${reportForm.fromDate} đến ngày ${reportForm.toDate}
		<c:if test="${reportForm.employeeId > 0}">
			của ${tasks[0].ownerName}
       	</c:if>	
		<c:if test="${reportForm.department != 'all'}"> phòng ${reportForm.department}</c:if>
	</h3>
	<br />
	<form:form action="exportToPDF" modelAttribute="reportForm" method="POST">
		<c:if test="${tasks.size() > 0}">
			<input class="btn btn-lg btn-primary btn-sm" type="submit" name="Export ra file PDF và gửi báo cáo" value="Export ra file PDF và gửi báo cáo" /> <br/><br/>
		</c:if>
		<table>
			<form:hidden path="fromDate" />
			<form:hidden path="toDate" />
			<form:hidden path="department" />
			<form:hidden path="employeeId" />
			<form:hidden path="employeeName" />
			<tr>
				<td width="15%">&nbsp; Người báo cáo:</td>
				<td width="85%"><form:input path="sender" type="text" class="form-control animated" /></td>			
<%-- 				<td>						
					<form:select path="sender" class="form-control animated">
						<form:options items="${employeeEmailMap}" />
					</form:select>
				</td> --%>	
			</tr>
			<tr>
				<td>&nbsp; Ý kiến/ Đề xuất:</td>
				<td><form:input path="comment" type="text" class="form-control animated" /></td>
			</tr>
		</table>
		<br/>
	
	<table class="table table-striped">
			<tr>
				<th title="Check để loại bỏ trong báo cáo">Bỏ</th>
				<th nowrap="nowrap" title="Check để thêm cột này vào báo cáo">Mã việc &nbsp;<form:checkbox path="idCheck" class="form-check-input" value="Y" id="idCheck"/></th>
				<th>Tên việc</th>
				<th title="Check để thêm cột này vào báo cáo">Mô tả &nbsp;<form:checkbox path="desCheck" value="Y" class="form-check-input" id="desCheck"/></th>
				<th>Người làm</th>
				<th nowrap="nowrap">Trạng thái</th>			
				<th nowrap="nowrap" title="Check để thêm cột này vào báo cáo">Thời gian ước lượng  &nbsp;<form:checkbox path="estimateCheck" value="Y" class="form-check-input" id="estimateCheck"/></th>
				<th nowrap="nowrap">Thời gian đã làm</th>	
				<th nowrap="nowrap" title="Check để thêm cột này vào báo cáo">Cập nhật gần nhất  &nbsp;<form:checkbox path="updateTimeCheck" value="Y" class="form-check-input" id="updateTimeCheck"/></th>
				<th nowrap="nowrap" title="Check để thêm cột này vào báo cáo">Ngày phải xong  &nbsp;<form:checkbox path="dueDateCheck" value="Y" class="form-check-input" id="dueDateCheck"/></th>
				<th nowrap="nowrap">Nhận xét đánh giá</th>
			</tr>
			<c:forEach var="task" items="${tasks}">
				<tr>
					<td  title="Check để loại bỏ dòng này trong báo cáo"><form:checkbox path="unSelect" class="form-check-input" value="${task.taskId}" id="unSelect"/></td>
					<td>${task.taskId}</td>
					<td>${task.taskName}</td>
					<td>${task.description}</td>
					<c:if test="${task.ownedBy == 0}">
						<td nowrap="nowrap">Chưa giao cho ai</td>
					</c:if>
					<c:if test="${task.ownedBy > 0}">
						<td nowrap="nowrap">${task.ownerName}</td>
					</c:if>
					<td>${task.status}</td>
					<td>${task.estimate} ${task.estimateTimeType}</td>
					<td>${task.timeSpent} ${task.timeSpentType}</td>
					<td nowrap="nowrap">${task.updateTS}</td>
					<%-- <td><fmt:formatDate pattern="dd-MM-yyyy" value="${task.dueDate}" /></td> --%>
					<td>${task.dueDate}</td>
					<td>${task.reviewComment}</td>
				</tr>
			</c:forEach>
		</table>
	</form:form>
		<c:if test="${tasks.size() < 1}">
			<div class="alert alert-success">Không có công việc nào được làm trong thời gian và điều kiện như trên!
			Vui lòng chọn lại thông tin ...
			</div>
		</c:if>
</body>
</html>