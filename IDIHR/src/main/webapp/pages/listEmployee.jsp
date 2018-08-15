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
		// Khởi tạo action/method cho employeeForm form
		$("#employeeForm").attr("action", "${url}/");
		$("#employeeForm").attr("method", "POST");		
		$("button[id^=page]").each(function(i, el) {
			$(this).click(function() {
				if($(this).text()!=${employeeForm.pageIndex}){
					$("#pageIndex").val($(this).text());
					$("#employeeForm").submit();
				}
			});
		});
		
		$("#firstPage").click(function(){
			$("#pageIndex").val(1);
			$("#employeeForm").submit();
		});
		
		$("#previousPage").click(function(){
			$("#pageIndex").val(${employeeForm.pageIndex-1});
			$("#employeeForm").submit();
		});
		
		$("#nextPage").click(function(){
			$("#pageIndex").val(${employeeForm.pageIndex+1});
			$("#employeeForm").submit();	
		});
		
		$("#lastPage").click(function(){
			$("#pageIndex").val(${employeeForm.totalPages});
			$("#employeeForm").submit();	
		});
		
		$("#numberRecordsOfPage").change(function(){
			$("#pageIndex").val(1);
			$("#totalPages").val(0);
			$("#totalRecords").val(0);
			$("#employeeForm").submit();
		});	

	});
</script>

<title>Danh sách nhân viên</title>
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
	<a href="${url}/insertEmployee"><button
			class="btn btn-primary btn-sm">Thêm mới nhân viên</button></a>
	<a href="${url}/listEmployeeBirth?quarter=${quarter}"><button
			class="btn btn-primary btn-sm">Danh sách nhân viên sinh nhật quý</button></a>		
	<a href="${url}/workStatusReport"><button
			class="btn btn-primary btn-sm">Thống kê trạng thái LĐ</button></a>				
	<br />
	<br />
	<form:form modelAttribute="employeeForm" method="POST">
	<table class="table">
		<tr>
			<td style="color: purple;"><i>Nhập thông tin nhân viên muốn tìm kiếm: Tên/Email/Account/Mã NV/Mã phòng/Mã chức vụ/trạng thái LĐ </i></td>
			<td align="center">
				<form:input path="searchValue" required="required" class="form-control animated"/>
			</td>
			<td>
				<input class="btn btn-lg btn-primary btn-sm" type="submit" value="Tìm" />
			</td>	
		</tr>
	</table>
	
			<table class="table">
		<tr>
			<td><span>Tổng số ${employeeForm.totalRecords} nhân viên</span></td>
			<td><span>Số trang: ${employeeForm.totalPages}</span></td>
			<form:hidden path="pageIndex" /> 
			<form:hidden path="totalPages" /> 
			<form:hidden path="totalRecords" />			
			<td>Trang:</td>
			<td>
				<div class="btn-group btn-group-md">
					<c:choose>
						<c:when test="${employeeForm.pageIndex==1}">
							<button id="firstPage" type="button" class="btn btn-default disabled">Đầu</button>
							<button id="previousPage" type="button"	class="btn btn-default disabled">Trước</button>
						</c:when>
						<c:otherwise>
							<button id="firstPage" type="button" class="btn btn-default">Đầu</button>
							<button id="previousPage" type="button" class="btn btn-default">Trước</button>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${employeeForm.totalPages<=3}">
							<c:forEach begin="1" end="${employeeForm.totalPages}"
								varStatus="status">
								<c:choose>
									<c:when test="${status.index==employeeForm.pageIndex}">
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
								<c:when test="${employeeForm.pageIndex==1}">
									<button id="page1" type="button" class="btn btn-default active">1</button>
									<button id="page2" type="button" class="btn btn-default">2</button>
									<button id="page3" type="button" class="btn btn-default">3</button>
								</c:when>
								<c:when
									test="${employeeForm.pageIndex==employeeForm.totalPages}">
									<button id="page${employeeForm.totalPages-2}" type="button"
										class="btn btn-default">${employeeForm.totalPages-2}</button>
									<button id="page${employeeForm.totalPages-1}" type="button"
										class="btn btn-default">${employeeForm.totalPages-1}</button>
									<button id="page${employeeForm.totalPages}" type="button"
										class="btn btn-default active">${employeeForm.totalPages}</button>
								</c:when>
								<c:otherwise>
									<c:forEach begin="${employeeForm.pageIndex - 1}"
										end="${employeeForm.pageIndex + 1}" varStatus="status">
										<c:choose>
											<c:when test="${status.index==employeeForm.pageIndex}">
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
							test="${employeeForm.pageIndex==employeeForm.totalPages}">
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
	<c:if test="${search}">
		<a href="${url}"><button class="btn btn-primary btn-sm">Hiển thị tất cả nhân viên</button></a> <br/> <br/>
	</c:if>
	<div class="table-responsive">
		<c:if test="${not empty message}">
			<div class="alert alert-success">${message}</div>
		</c:if>
		<table class="table table-striped">
			<tr>
				<th nowrap="nowrap">Mã NV</th>
				<th>Họ tên</th>
				<th>Account</th>
				<th>Email</th>
				<th nowrap="nowrap">Chức vụ</th>
				<th>Phòng</th>
				<th>Trạng thái</th>
				<!-- th nowrap="nowrap">Giới tính</th-->
				<th>Số đt</th>
				<th nowrap="nowrap">Chi tiết</th>
				<th>Sửa</th>
			</tr>
			<c:forEach var="employee" items="${employees}">
				<tr>
					<td>${employee.employeeId}</td>
					<td nowrap="nowrap">${employee.fullName}</td>
					<td>${employee.loginAccount}</td>
					<td>${employee.email}</td>
					<td>${employee.jobTitle}</td>
					<td>${employee.department}</td>
					<td nowrap="nowrap">${employee.statusName}</td>
					<!-- td>${employee.gender}</td-->
					<td>${employee.phoneNo}</td>
					<td><a href="/IDIHR/viewEmployee?employeeId=${employee.employeeId}">Xem</a></td>
					<td><a href="/IDIHR/editEmployee?employeeId=${employee.employeeId}">Sửa</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>