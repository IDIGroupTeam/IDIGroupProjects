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
		// Khởi tạo action/method cho insuranceForm form
		$("#insuranceForm").attr("action", "${url}/insurance/");
		$("#insuranceForm").attr("method", "POST");		
		$("button[id^=page]").each(function(i, el) {
			$(this).click(function() {
				if($(this).text()!=${insuranceForm.pageIndex}){
					$("#pageIndex").val($(this).text());
					$("#insuranceForm").submit();
				}
			});
		});
		
		$("#firstPage").click(function(){
			$("#pageIndex").val(1);
			$("#insuranceForm").submit();
		});
		
		$("#previousPage").click(function(){
			$("#pageIndex").val(${insuranceForm.pageIndex-1});
			$("#insuranceForm").submit();
		});
		
		$("#nextPage").click(function(){
			$("#pageIndex").val(${insuranceForm.pageIndex+1});
			$("#insuranceForm").submit();	
		});
		
		$("#lastPage").click(function(){
			$("#pageIndex").val(${insuranceForm.totalPages});
			$("#insuranceForm").submit();	
		});
		
		$("#numberRecordsOfPage").change(function(){
			$("#pageIndex").val(1);
			$("#totalPages").val(0);
			$("#totalRecords").val(0);
			$("#insuranceForm").submit();
		});	

	});
</script>

<title>Danh sách BH của nhân viên</title>
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
	<a href="${pageContext.request.contextPath}/insurance/insertInsurance"><button
			class="btn btn-primary btn-sm">Thêm mới </button></a>
	<br />
	<br />
	
	<form:form modelAttribute="insuranceForm" method="POST">
		<table class="table">
			<tr>
				<td><span>Tổng số ${insuranceForm.totalRecords} bản ghi</span></td>
				<td><span>Số trang: ${insuranceForm.totalPages}</span></td>
				<form:hidden path="pageIndex" /> 
				<form:hidden path="totalPages" /> 
				<form:hidden path="totalRecords" />			
				<td>Trang:</td>
				<td>
					<div class="btn-group btn-group-md">
						<c:choose>
							<c:when test="${insuranceForm.pageIndex==1}">
								<button id="firstPage" type="button" class="btn btn-default disabled">Đầu</button>
								<button id="previousPage" type="button"	class="btn btn-default disabled">Trước</button>
							</c:when>
							<c:otherwise>
								<button id="firstPage" type="button" class="btn btn-default">Đầu</button>
								<button id="previousPage" type="button" class="btn btn-default">Trước</button>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${insuranceForm.totalPages<=3}">
								<c:forEach begin="1" end="${insuranceForm.totalPages}"
									varStatus="status">
									<c:choose>
										<c:when test="${status.index==insuranceForm.pageIndex}">
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
									<c:when test="${insuranceForm.pageIndex==1}">
										<button id="page1" type="button" class="btn btn-default active">1</button>
										<button id="page2" type="button" class="btn btn-default">2</button>
										<button id="page3" type="button" class="btn btn-default">3</button>
									</c:when>
									<c:when
										test="${insuranceForm.pageIndex==insuranceForm.totalPages}">
										<button id="page${insuranceForm.totalPages-2}" type="button"
											class="btn btn-default">${insuranceForm.totalPages-2}</button>
										<button id="page${insuranceForm.totalPages-1}" type="button"
											class="btn btn-default">${insuranceForm.totalPages-1}</button>
										<button id="page${insuranceForm.totalPages}" type="button"
											class="btn btn-default active">${insuranceForm.totalPages}</button>
									</c:when>
									<c:otherwise>
										<c:forEach begin="${insuranceForm.pageIndex - 1}"
											end="${insuranceForm.pageIndex + 1}" varStatus="status">
											<c:choose>
												<c:when test="${status.index==insuranceForm.pageIndex}">
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
								test="${insuranceForm.pageIndex==insuranceForm.totalPages}">
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
				<th>Số sổ BHXH</th>
				<th>Lương BH</th>				
				<th>Đóng tại</th>
				<th>Tình trạng</th>
				<th>Số thẻ BHYT</th>
				<th>Nơi ĐK khám bệnh</th>
				<th>Xem chi tiết</th>
				<th>Quá trình đóng</th>				
				<th>Sửa</th>
			</tr>
			<c:forEach var="insurance" items="${insurances}">
				<tr>
					<td>${insurance.employeeId}</td>
					<td>${insurance.employeeName}</td>
					<td>${insurance.socicalInsuNo}</td>
					<td><fmt:formatNumber value="${insurance.salarySocicalInsu.replaceAll(',', '')}" type="number"/></td>
					<td>${insurance.place}</td>
					<td>${insurance.status}</td>
					<td>${insurance.hInsuNo}</td>
					<td>${insurance.hInsuPlace}</td>
					<td><a href="viewInsurance?socicalInsuNo=${insurance.socicalInsuNo}">Xem chi tiết</a></td>
					<td><a href="listProcessInsurance?socicalInsuNo=${insurance.socicalInsuNo}&employeeId=${insurance.employeeId}">Quá trình đóng</a></td>
					<td><a href="editInsurance?socicalInsuNo=${insurance.socicalInsuNo}">Sửa</a></td>
				</tr>
			</c:forEach>
		</table>
		<c:if test="${not empty message}">
			<div class="alert alert-success">${message}</div>
		</c:if>
	</div>
</body>
</html>