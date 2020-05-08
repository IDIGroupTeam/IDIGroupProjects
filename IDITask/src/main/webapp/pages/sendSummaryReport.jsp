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

<style type="text/css">
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
</style>

<!-- Initialize the plugin: -->
<script type="text/javascript">
//alert("send report ...xxx");
$(function(){
	//alert("send report ...");
/* 	$("#sendTo").multiselect({
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
	});	 */	
	//alert("send report ...yyy");	
}); 

</script>
<title>Gửi thống kê khối lượng công việc</title>
</head>
<body>
	<a href="${url}/"><button class="btn btn-lg btn-primary btn-sm">
			Quay lại dach sách công việc</button></a>
	<a href="${url}/prepareReport?fDate=${reportForm.fromDate}&tDate=${reportForm.toDate}&eName=${tasks[0].ownerName}&dept=${reportForm.department}"><button
			class="btn btn-primary btn-sm">Quay lại lựa chọn thông tin cần
			thống kê khối lượng công việc</button></a>		
	<form:form action="sendSummaryReport" modelAttribute="sendReportForm"
		enctype="multipart/form-data" method="POST" >
		<br />
		<c:if test="${not empty message}">
			<div class="alert alert-success">${message}</div>
		</c:if>
		
		<c:if test="${not empty warning}">
			<div class="alert alert-warning">${warning}</div>
		</c:if>

		<form:input type="hidden" path="fileName" value="${sendReportForm.fileName}" />
		<%-- <form:input type="hidden" path="subject" value="${sendReportForm.subject}" /> --%>
		<table class="table table-bordered table-hover">
			<tr>
				<td nowrap="nowrap" title="Nhập chính xác địa chỉ email của người nhận ví dụ: bcsidigroup@gmail.com. Các email cách nhau bằng dấu phẩy ',' ">Gửi tới:(*)</td>
	<%-- 			<td>
					<form:select path="sendTo" multiple="multiple" class="form-control animated"> 
						<form:options items="${employeeEmailMap}" />
					</form:select> --%>
					
				<td><form:input path="sendTo" required="required"
						class="form-control animated" /></td>
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
				<td>Gửi file '${fileSave}</td>
			</tr>
		</table>
		<input class="btn btn-lg btn-primary btn-sm" type="submit"
			value="Gửi" /> &nbsp;
	</form:form>
</body>
</html>