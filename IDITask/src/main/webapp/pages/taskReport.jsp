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
<!-- Initialize the plugin: -->
<link rel="bootstrap-autosize"	href="${url}/public/css/bootstrap-autosize.css" />
<script src="${url}/public/js/bootstrap-autosize.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
<script type="text/javascript">
	$(function() {
		$('.normal').autosize();
		$('.animatedArea').autosize({
			append : "\n"
		});
	});
</script>

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
		<c:if test="${not empty reportForm.ids && reportForm.ids.contains(',')}"> của một số người	</c:if>
		<c:if test="${not empty reportForm.ids && !reportForm.ids.contains(',')}"> của nv mã ${reportForm.ids} </c:if>
		<c:if test="${reportForm.department != 'all'}"> phòng ${reportForm.department}</c:if>
		<c:if test="${reportForm.department == 'all' && empty reportForm.ids}"> tất cả các phòng ban</c:if>
	</h3>
	<br />
	<form:form action="exportToPDF" modelAttribute="reportForm" method="POST">
		<c:if test="${tasks.size() > 0 || tasksNext.size() > 0}">
			<input class="btn btn-lg btn-primary btn-sm" type="submit" name="Export ra file PDF và gửi báo cáo" value="Export ra file PDF và gửi báo cáo" /> <br/><br/>
		</c:if> 
		<table>
			<form:hidden path="fromDate" />
			<form:hidden path="toDate" />
			<form:hidden path="department" />
			<form:hidden path="ids" />
			<form:hidden path="employeeName" />
			<tr>
				<td width="15%">&nbsp;<b> Người báo cáo:</b></td>
				<td width="85%"><form:input path="sender" type="text" class="form-control animated" /></td>			
<%-- 				<td>						
					<form:select path="sender" class="form-control animated">
						<form:options items="${employeeEmailMap}" />
					</form:select>
				</td> --%>	
			</tr>			
			<tr>
				<td>&nbsp; <b> Đánh giá thực hiện kế hoạch:</b></td>
				<td><form:textarea path="summary" class="form-control animatedArea" rows="2" /></td>
			</tr>
			<tr>
				<td>&nbsp; <b> Ý kiến/ Đề xuất:</b></td>
				<td><form:textarea path="comment" class="form-control animatedArea" rows="2" /></td>
			</tr>
		</table>
		<br/>
	
		<table class="table table-striped">
			<tr><td colspan="11"><b><i>Kết quả thực hiện:</i></b></td></tr>
			<tr>
				<th title="Check để loại bỏ trong báo cáo">Bỏ</th>
				<th nowrap="nowrap" title="Check để thêm cột này vào báo cáo">Mã việc &nbsp;<form:checkbox path="idCheck" class="form-check-input" value="Y" id="idCheck"/></th>
				<th>Tên việc</th>
				<th title="Check để thêm cột này vào báo cáo">Mô tả &nbsp;<form:checkbox path="desCheck" value="Y" class="form-check-input" id="desCheck"/></th>
				<th>Người làm</th>
				<th nowrap="nowrap">Trạng thái</th>			
				<th nowrap="nowrap" title="Check để thêm cột này vào báo cáo">Thời gian kế hoạch  &nbsp;<form:checkbox path="estimateCheck" value="Y" class="form-check-input" id="estimateCheck"/></th>
				<th nowrap="nowrap">Thời gian đã làm</th>	
				<th nowrap="nowrap" title="Check để thêm cột này vào báo cáo">Cập nhật gần nhất  &nbsp;<form:checkbox path="updateTimeCheck" value="Y" class="form-check-input" id="updateTimeCheck"/></th>
				<th nowrap="nowrap" title="Check để thêm cột này vào báo cáo">Ngày phải xong  &nbsp;<form:checkbox path="dueDateCheck" value="Y" class="form-check-input" id="dueDateCheck"/></th>
				<th nowrap="nowrap">Nhận xét đánh giá</th>
			</tr>			
			<c:forEach var="task" items="${tasks}">
				<tr>
					<td title="Check để loại bỏ dòng này trong báo cáo"><form:checkbox path="unSelect" class="form-check-input" value="${task.taskId}" id="unSelect"/></td>
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
					<td>${task.dueDateConverted}</td>
					<td>${task.reviewComment}</td>
				</tr>
			</c:forEach>
			</table>
			
			<table  class="table table-striped">
			<tr><td colspan="11"><b><i>Kế hoạch:</i></b></td></tr>
			<tr>
				<th title="Check để loại bỏ trong báo cáo">Bỏ</th>
				<th nowrap="nowrap" title="Check để thêm cột này vào báo cáo">Mã việc </th>
				<th>Tên việc</th>
				<th title="Check để thêm cột này vào báo cáo">Mô tả </th>
				<th>Người làm</th>
				<th nowrap="nowrap">Trạng thái</th>			
				<th nowrap="nowrap" title="Check để thêm cột này vào báo cáo">Thời gian kế hoạch</th>
				<th nowrap="nowrap">Thời gian đã làm</th>	
				<th nowrap="nowrap" title="Check để thêm cột này vào báo cáo">Cập nhật gần nhất </th>
				<th nowrap="nowrap" title="Check để thêm cột này vào báo cáo">Ngày phải xong </th>
				<th nowrap="nowrap">Nhận xét đánh giá</th>
			</tr>	
			<c:forEach var="taskNext" items="${tasksNext}">
				<tr>
					<td  title="Check để loại bỏ dòng này trong báo cáo"><form:checkbox path="unSelected" class="form-check-input" value="${taskNext.taskId}" id="unSelect"/></td>
					<td>${taskNext.taskId}</td>
					<td>${taskNext.taskName}</td>
					<td>${taskNext.description}</td>
					<c:if test="${taskNext.ownedBy == 0}">
						<td nowrap="nowrap">Chưa giao cho ai</td>
					</c:if>
					<c:if test="${taskNext.ownedBy > 0}">
						<td nowrap="nowrap">${taskNext.ownerName}</td>
					</c:if>
					<td>${taskNext.status}</td>
					<td>${taskNext.estimate} ${taskNext.estimateTimeType}</td>
					<td>${taskNext.timeSpent} ${taskNext.timeSpentType}</td>
					<td nowrap="nowrap">${taskNext.updateTS}</td>
					<%-- <td><fmt:formatDate pattern="dd-MM-yyyy" value="${task.dueDate}" /></td> --%>
					<td>${taskNext.dueDateConverted}</td>
					<td>${taskNext.reviewComment}</td>
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