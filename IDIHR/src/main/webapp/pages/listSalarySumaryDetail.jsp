<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
<!-- Initialize the plugin: -->
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
<script type="text/javascript">
	$(function() {
		// Khởi tạo action/method cho salaryForm form
		$("#salaryForm").attr("action", "${url}/salary/generateSalaryReport");
		$("#salaryForm").attr("method", "POST");		
		$("button[id^=page]").each(function(i, el) {
			$(this).click(function() {
				if($(this).text()!=${salaryForm.pageIndex}){
					$("#pageIndex").val($(this).text());
					$("#salaryForm").submit();
				}
			});
		});
		
		$("#firstPage").click(function(){
			$("#pageIndex").val(1);
			$("#salaryForm").submit();
		});
		
		$("#previousPage").click(function(){
			$("#pageIndex").val(${salaryForm.pageIndex-1});
			$("#salaryForm").submit();
		});
		
		$("#nextPage").click(function(){
			$("#pageIndex").val(${salaryForm.pageIndex+1});
			$("#salaryForm").submit();	
		});
		
		$("#lastPage").click(function(){
			$("#pageIndex").val(${salaryForm.totalPages});
			$("#salaryForm").submit();	
		});
		
		$("#numberRecordsOfPage").change(function(){
			$("#pageIndex").val(1);
			$("#totalPages").val(0);
			$("#totalRecords").val(0);
			$("#salaryForm").submit();
		});	

	});
</script>

<title>Danh sách lương của nhân viên</title>
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
	<a href="${pageContext.request.contextPath}/salary/prepareSummarySalary"><button class="btn btn-primary btn-sm">Quay lại lựa chọn thông tin cần thống kê</button></a>
	<br />
	<br />
	
	<form:form modelAttribute="salaryForm" method="POST">
		<table class="table">
			<tr>
				<td><span>Tổng số ${salaryForm.totalRecords} bản ghi</span></td>
				<td><span>Số trang: ${salaryForm.totalPages}</span></td>
				<form:hidden path="pageIndex" /> 
				<form:hidden path="totalPages" /> 
				<form:hidden path="totalRecords" />			
				<form:hidden path="yearReport" />		
				<form:hidden path="monthReport" />						
				<td>Trang:</td>
				<td>
					<div class="btn-group btn-group-md">
						<c:choose>
							<c:when test="${salaryForm.pageIndex==1}">
								<button id="firstPage" type="button" class="btn btn-default disabled">Đầu</button>
								<button id="previousPage" type="button"	class="btn btn-default disabled">Trước</button>
							</c:when>
							<c:otherwise>
								<button id="firstPage" type="button" class="btn btn-default">Đầu</button>
								<button id="previousPage" type="button" class="btn btn-default">Trước</button>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${salaryForm.totalPages<=3}">
								<c:forEach begin="1" end="${salaryForm.totalPages}"
									varStatus="status">
									<c:choose>
										<c:when test="${status.index==salaryForm.pageIndex}">
											<button id="page${status.index}" type="button"
												class="btn btn-default active">${status.index}</button>
										</c:when>
										<c:otherwise>
											<button id="page${status.index}" type="button"
												class="btn btn-default">${status.index}</button>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${salaryForm.pageIndex==1}">
										<button id="page1" type="button" class="btn btn-default active">1</button>
										<button id="page2" type="button" class="btn btn-default">2</button>
										<button id="page3" type="button" class="btn btn-default">3</button>
									</c:when>
									<c:when
										test="${salaryForm.pageIndex==salaryForm.totalPages}">
										<button id="page${salaryForm.totalPages-2}" type="button"
											class="btn btn-default">${salaryForm.totalPages-2}</button>
										<button id="page${salaryForm.totalPages-1}" type="button"
											class="btn btn-default">${salaryForm.totalPages-1}</button>
										<button id="page${salaryForm.totalPages}" type="button"
											class="btn btn-default active">${salaryForm.totalPages}</button>
									</c:when>
									<c:otherwise>
										<c:forEach begin="${salaryForm.pageIndex - 1}"
											end="${salaryForm.pageIndex + 1}" varStatus="status">
											<c:choose>
												<c:when test="${status.index==salaryForm.pageIndex}">
													<button id="page${status.index}" type="button"
														class="btn btn-default active">${status.index}</button>
												</c:when>
												<c:otherwise>
													<button id="page${status.index}" type="button"
														class="btn btn-default">${status.index}</button>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when
								test="${salaryForm.pageIndex==salaryForm.totalPages}">
								<button id="nextPage" type="button"
									class="btn btn-default disabled">Sau</button>
								<button id="lastPage" type="button"
									class="btn btn-default disabled">Cuối</button>
							</c:when>
							<c:otherwise>
								<button id="nextPage" type="button" class="btn btn-default">Sau</button>
								<button id="lastPage" type="button" class="btn btn-default">Cuối</button>
							</c:otherwise>
						</c:choose>
					</div>
				</td>
				<td>Số bản ghi trong một trang:</td>
				<td><form:select path="numberRecordsOfPage" class="form-control">					
						<form:option value="25">25</form:option>
						<form:option value="50">50</form:option>
						<form:option value="100">100</form:option>
						<form:option value="200">200</form:option>
					</form:select></td>
			</tr>
		</table>	
	</form:form>
	
	<div class="table-responsive">		
		<table class="table table-striped">
			<tr>
				<th>Mã NV</th>
				<th>Họ tên</th>
				<th>Phòng</th>
				<th>Chức danh</th>				
				<th>Lương cơ bản</th>		
				<th>Lương thực nhận</th>		
				<th>Thưởng</th>
				<th>Trợ cấp/trách nhiệm</th>
				<th>Lương ngoài giờ</th>
				<th>Tạm ứng</th>
				<th>Thuế TNCN</th>
				<th>Đóng BHXH</th>				
			</tr>
			<c:if test="${not empty salaryReport}">
				<th colspan="5">Tổng cả năm: </th>		
				<th><fmt:formatNumber value="${salaryReport.finalSalary}" type="number"/></th>		
				<th><fmt:formatNumber value="${salaryReport.bounus}" type="number"/></th>
				<th><fmt:formatNumber value="${salaryReport.subsidize}" type="number"/></th>
				<th><fmt:formatNumber value="${salaryReport.overTimeSalary}" type="number"/></th>
				<th><fmt:formatNumber value="${salaryReport.advancePayed}" type="number"/></th>
				<th><fmt:formatNumber value="${salaryReport.taxPersonal}" type="number"/></th>
				<th><fmt:formatNumber value="${salaryReport.payedInsurance}" type="number"/></th>
			</c:if>
			<c:forEach var="salary" items="${salaryDetails}">
				<tr>
					<td>${salary.employeeId}</td>
					<td nowrap="nowrap">${salary.fullName}</td>
					<td>${salary.department}</td>					
					<td>${salary.jobTitle}</td>
					<td><fmt:formatNumber value="${salary.salary}" type="number"/> </td>					
					<td><fmt:formatNumber value="${salary.finalSalary}" type="number"/> </td>
					<td><fmt:formatNumber value="${salary.bounus}" type="number"/> </td>
					<td><fmt:formatNumber value="${salary.subsidize}" type="number"/> </td>
					<td><fmt:formatNumber value="${salary.overTimeSalary}" type="number"/> </td>
					<td><fmt:formatNumber value="${salary.advancePayed}" type="number"/> </td>
					<td><fmt:formatNumber value="${salary.taxPersonal}" type="number"/> </td>
					<td><fmt:formatNumber value="${salary.payedInsurance}" type="number"/> </td>
				</tr>
			</c:forEach>
		</table>
		<c:if test="${not empty message}">
			<div class="alert alert-success">${message}</div>
		</c:if>
	</div>
	<a href="${pageContext.request.contextPath}/salary/prepareSummarySalary"><button class="btn btn-primary btn-sm">Quay lại lựa chọn thông tin cần thống kê</button></a>
</body>
</html>