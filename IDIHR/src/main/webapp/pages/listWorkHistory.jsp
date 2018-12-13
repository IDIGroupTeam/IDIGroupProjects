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
		// Khởi tạo action/method cho workHistoryForm form
		$("#workHistoryForm").attr("action", "${url}/workHistory/");
		$("#workHistoryForm").attr("method", "POST");		
		$("button[id^=page]").each(function(i, el) {
			$(this).click(function() {
				if($(this).text()!=${workHistoryForm.pageIndex}){
					$("#pageIndex").val($(this).text());
					$("#workHistoryForm").submit();
				}
			});
		});
		
		$("#firstPage").click(function(){
			$("#pageIndex").val(1);
			$("#workHistoryForm").submit();
		});
		
		$("#previousPage").click(function(){
			$("#pageIndex").val(${workHistoryForm.pageIndex-1});
			$("#workHistoryForm").submit();
		});
		
		$("#nextPage").click(function(){
			$("#pageIndex").val(${workHistoryForm.pageIndex+1});
			$("#workHistoryForm").submit();	
		});
		
		$("#lastPage").click(function(){
			$("#pageIndex").val(${workHistoryForm.totalPages});
			$("#workHistoryForm").submit();	
		});
		
		$("#numberRecordsOfPage").change(function(){
			$("#pageIndex").val(1);
			$("#totalPages").val(0);
			$("#totalRecords").val(0);
			$("#workHistoryForm").submit();
		});	

	});
</script>
<title>Lịch sử công tác của nhân viên</title>
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
<script type="text/javascript">
	function ConfirmDelete() {
	  return confirm("Bạn có chắc chắn muốn xóa không?");
	}
</script>
</head>
<body>
	<a href="${pageContext.request.contextPath}/workHistory/addWorkHistory"><button
			class="btn btn-primary btn-sm">Thêm mới </button></a>
	<br />
	<br />
	
	<form:form modelAttribute="workHistoryForm" method="POST">

		<table class="table">
			<tr>
				<td><span>Tổng số ${workHistoryForm.totalRecords} bản ghi</span></td>
				<td><span>Số trang: ${workHistoryForm.totalPages}</span></td>
				<form:hidden path="pageIndex" /> 
				<form:hidden path="totalPages" /> 
				<form:hidden path="totalRecords" />			
				<td>Trang:</td>
				<td>
					<div class="btn-group btn-group-md">
						<c:choose>
							<c:when test="${workHistoryForm.pageIndex==1}">
								<button id="firstPage" type="button" class="btn btn-default disabled">Đầu</button>
								<button id="previousPage" type="button"	class="btn btn-default disabled">Trước</button>
							</c:when>
							<c:otherwise>
								<button id="firstPage" type="button" class="btn btn-default">Đầu</button>
								<button id="previousPage" type="button" class="btn btn-default">Trước</button>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${workHistoryForm.totalPages<=3}">
								<c:forEach begin="1" end="${workHistoryForm.totalPages}"
									varStatus="status">
									<c:choose>
										<c:when test="${status.index==workHistoryForm.pageIndex}">
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
									<c:when test="${workHistoryForm.pageIndex==1}">
										<button id="page1" type="button" class="btn btn-default active">1</button>
										<button id="page2" type="button" class="btn btn-default">2</button>
										<button id="page3" type="button" class="btn btn-default">3</button>
									</c:when>
									<c:when
										test="${workHistoryForm.pageIndex==workHistoryForm.totalPages}">
										<button id="page${workHistoryForm.totalPages-2}" type="button"
											class="btn btn-default">${workHistoryForm.totalPages-2}</button>
										<button id="page${workHistoryForm.totalPages-1}" type="button"
											class="btn btn-default">${workHistoryForm.totalPages-1}</button>
										<button id="page${workHistoryForm.totalPages}" type="button"
											class="btn btn-default active">${workHistoryForm.totalPages}</button>
									</c:when>
									<c:otherwise>
										<c:forEach begin="${workHistoryForm.pageIndex - 1}"
											end="${workHistoryForm.pageIndex + 1}" varStatus="status">
											<c:choose>
												<c:when test="${status.index==workHistoryForm.pageIndex}">
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
								test="${workHistoryForm.pageIndex==workHistoryForm.totalPages}">
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
				<th>Từ ngày</th>
				<th>Đến ngày</th>				
				<th>Chức vụ</th>
				<th>phòng</th>
				<th>Công ty</th>
				<th>Lương</th>
				<th>Thành tích</th>
				<th>Nhận xét</th>								
				<!-- <th>Lịch sử từng NV</th>	 -->			
				<th>Sửa</th>
				<th>Xóa</th>
			</tr>
			<c:forEach var="workHistory" items="${workHistorys}">
				<tr>
					<td>${workHistory.employeeId}</td>
					<td>${workHistory.employeeName}</td>
					<td>${workHistory.fromDate}</td>
					<td>${workHistory.toDate}</td>					
					<td>${workHistory.title}</td>
					<td>${workHistory.department}</td>
					<td>${workHistory.company}</td>
					<td>${workHistory.salary}</td>
					<td>${workHistory.achievement}</td>
					<td>${workHistory.appraise}</td>
					
					<%-- <td><a href="listWorkHistorysByEmployee?employeeId=${workHistory.employeeId}">Xem</a></td> --%>
					<td><a href="editWorkHistory?employeeId=${workHistory.employeeId}&fromDate=${workHistory.fromDate}">Sửa</a></td>
					<td><a href="deleteWorkHistory?employeeId=${workHistory.employeeId}&fromDate=${workHistory.fromDate}" Onclick="return ConfirmDelete()">Xóa</a></td>
				</tr>
			</c:forEach>
		</table>
		<c:if test="${not empty message}">
			<div class="alert alert-success">${message}</div>
		</c:if>
	</div>
</body>
</html>