<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
<!-- Include Twitter Bootstrap and jQuery: -->
<script type="text/javascript" src="${url}/public/js/bootstrap-multiselect.js"></script>
<script type="text/javascript" src="${url}/public/js/jquery.min.js"></script>
<!-- Include the plugin's CSS and JS: -->


<!-- Initialize the plugin: -->
<script type="text/javascript">
//alert("send report ...xxx");
$(function(){
	//alert("send report ...");
	$("#sendTo").multiselect({
		enableFiltering : true,
		filterPlaceholder : 'Tìm kiếm',
		maxHeight : 200,
		buttonWidth : '170px',
		nonSelectedText : 'Chọn email',
		nSelectedText : 'Được chọn',
		includeSelectAllOption : true,
		allSelectedText : 'Chọn tất cả',
		selectAllText : 'Tất cả',
		selectAllValue : 'ALL'
	});		
	//alert("send report ...yyy");	
}); 

</script>
<title>Gửi báo cáo công việc</title>
</head>
<body>
	<a href="${url}/"><button class="btn btn-lg btn-primary btn-sm">
			Quay lại dach sách công việc</button></a>
	<form:form action="sendReport" modelAttribute="sendReportForm"
		enctype="multipart/form-data" method="POST" >
		<br />
		<input class="btn btn-lg btn-primary btn-sm" type="submit"
			value="Gửi báo cáo" /> &nbsp;
		<br />	<br />
		<form:input type="hidden" path="fileName" value="${sendReportForm.fileName}" />
		<form:input type="hidden" path="subject" value="${sendReportForm.subject}" />
		<table class="table table-bordered table-hover">
			<tr>
				<td nowrap="nowrap" title="Nhập chính xác địa chỉ email của người nhận ví dụ: bcsidigroup@gmail.com, các email cách nhau bằng dấu ; ">Gửi tới:(*)</td>
				<td>
					<form:select path="sendTo" multiple="multiple" class="form-control animated"> 
						<form:options items="${employeeEmailMap}" />
					</form:select>
					
<%-- 				<td><form:input path="sendTo" required="required"
						class="form-control animated" /></td> --%>
			</tr>
<%-- 			<tr>
				<td nowrap="nowrap" title="Phần này khi hoàn thiên chức năng phân quyền sẽ không cần nữa hệ thống sẽ tự động sác định được người gửi ...">Người gửi:</td>
				<td><form:input path="sendFrom" 
						class="form-control animated" /></td>
			</tr> --%>
			<tr>
				<td>Tiêu đề:</td>
				<td><form:input path="subject" class="form-control animated" /></td>
			</tr>
			<tr>
				<td>Nội dung:</td>
				<td>
					<table class="table table-striped">
						<tr>
							<th nowrap="nowrap">Mã việc</th>
							<th>Tên việc</th>
							<th>Người làm</th>
							<th nowrap="nowrap">Trạng thái</th>
							<th nowrap="nowrap">Thời gian ước lượng</th>
							<th nowrap="nowrap">Thời gian đã làm</th>
							<th nowrap="nowrap">Cập nhật gần nhất</th>
							<th nowrap="nowrap">Ngày phải xong</th>
							<th nowrap="nowrap">Nhận xét đánh giá</th>
						</tr>
						<c:forEach var="task" items="${tasks}">
							<tr>
								<td>${task.taskId}</td>
								<td>${task.taskName}</td>
								<c:if test="${task.ownedBy == 0}">
									<td>Chưa giao cho ai</td>
								</c:if>
								<c:if test="${task.ownedBy > 0}">
									<td nowrap="nowrap">${task.ownerName}</td>
								</c:if>
								<td>${task.status}</td>
								<td>${task.estimate} ${task.estimateTimeType}</td>
								<td>${task.timeSpent} ${task.timeSpentType}</td>								
								<td>${task.updateTS}</td>
								<td>${task.dueDate}</td>
								<td>${task.reviewComment}</td>
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
		</table>
		<input class="btn btn-lg btn-primary btn-sm" type="submit"
			value="Gửi báo cáo" /> &nbsp;
	</form:form>
</body>
</html>