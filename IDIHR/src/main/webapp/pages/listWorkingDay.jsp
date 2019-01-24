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
		// Khởi tạo action/method cho pagingForm form
		$("#pagingForm").attr("action", "${url}/timekeeping/listWorkingDay");
		$("#pagingForm").attr("method", "POST");		
		$("button[id^=page]").each(function(i, el) {
			$(this).click(function() {
				if($(this).text()!=${pagingForm.pageIndex}){
					$("#pageIndex").val($(this).text());
					$("#pagingForm").submit();
				}
			});
		});
		
		$("#firstPage").click(function(){
			$("#pageIndex").val(1);
			$("#pagingForm").submit();
		});
		
		$("#previousPage").click(function(){
			$("#pageIndex").val(${pagingForm.pageIndex-1});
			$("#pagingForm").submit();
		});
		
		$("#nextPage").click(function(){
			$("#pageIndex").val(${pagingForm.pageIndex+1});
			$("#pagingForm").submit();	
		});
		
		$("#lastPage").click(function(){
			$("#pageIndex").val(${pagingForm.totalPages});
			$("#pagingForm").submit();	
		});
		
		$("#numberRecordsOfPage").change(function(){
			$("#pageIndex").val(1);
			$("#totalPages").val(0);
			$("#totalRecords").val(0);
			$("#pagingForm").submit();
		});	

	});
</script>

<title>Danh sách phòng ban</title>
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

	<a href="${url}/timekeeping/setWorkingDayForMonth"><button
			class="btn btn-primary btn-sm">Thêm tháng định nghĩa</button></a>
	<a href="${url}/timekeeping/"><button
			class="btn btn-primary btn-sm">Quay lại chấm công</button></a>
	<br />
	<br />

	<form:form modelAttribute="pagingForm" method="POST">
		<table class="table">
			<tr>
				<td><span>Tổng số ${pagingForm.totalRecords} bản ghi</span></td>
				<td><span>Số trang: ${pagingForm.totalPages}</span></td>
				<form:hidden path="pageIndex" />
				<form:hidden path="totalPages" />
				<form:hidden path="totalRecords" />
				<td>Trang:</td>
				<td>
					<div class="btn-group btn-group-md">
						<c:choose>
							<c:when test="${pagingForm.pageIndex==1}">
								<button id="firstPage" type="button"
									class="btn btn-default disabled">Đầu</button>
								<button id="previousPage" type="button"
									class="btn btn-default disabled">Trước</button>
							</c:when>
							<c:otherwise>
								<button id="firstPage" type="button" class="btn btn-default">Đầu</button>
								<button id="previousPage" type="button" class="btn btn-default">Trước</button>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${pagingForm.totalPages<=3}">
								<c:forEach begin="1" end="${pagingForm.totalPages}"
									varStatus="status">
									<c:choose>
										<c:when test="${status.index==pagingForm.pageIndex}">
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
									<c:when test="${pagingForm.pageIndex==1}">
										<button id="page1" type="button"
											class="btn btn-default active">1</button>
										<button id="page2" type="button" class="btn btn-default">2</button>
										<button id="page3" type="button" class="btn btn-default">3</button>
									</c:when>
									<c:when test="${pagingForm.pageIndex==pagingForm.totalPages}">
										<button id="page${pagingForm.totalPages-2}" type="button"
											class="btn btn-default">${pagingForm.totalPages-2}</button>
										<button id="page${pagingForm.totalPages-1}" type="button"
											class="btn btn-default">${pagingForm.totalPages-1}</button>
										<button id="page${pagingForm.totalPages}" type="button"
											class="btn btn-default active">${pagingForm.totalPages}</button>
									</c:when>
									<c:otherwise>
										<c:forEach begin="${pagingForm.pageIndex - 1}"
											end="${pagingForm.pageIndex + 1}" varStatus="status">
											<c:choose>
												<c:when test="${status.index==pagingForm.pageIndex}">
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
							<c:when test="${pagingForm.pageIndex==pagingForm.totalPages}">
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
				<td><form:select path="numberRecordsOfPage"
						class="form-control">
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
				<th>Tháng</th>
				<th>Số ngày làm việc chuẩn</th>
				<th>Áp dụng cho công ty</th>
				<th>Người định nghĩa</th>
				<th>Ngày định nghĩa</th>
				<th>Ghi chú</th>
				<th>Sửa thông tin</th>
			</tr>
			<c:forEach var="workingDay" items="${workingDays}">
				<tr>
					<td>${workingDay.month}</td>
					<td>${workingDay.workDayOfMonth}</td>
					<td>${workingDay.forCompany}</td>
					<td>${workingDay.updateId}</td>
					<td>${workingDay.updateTs}</td>
					<td>${workingDay.comment}</td>
					<td><a
						href="${url}/timekeeping/editWorkingDay?month=${workingDay.month}&forCompany=${workingDay.forCompany}">Sửa</a>
					</td>
				</tr>
			</c:forEach>
		</table>
		<c:if test="${not empty message}">
			<div class="alert alert-success">${message}</div>
		</c:if>
	</div>
</body>
</html>