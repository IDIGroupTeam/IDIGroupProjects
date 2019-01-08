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
		$("#dept")
			.change(
				function() {
					
					// Lấy dữ liệu của phòng
					val = $(this).val();
						$							
						.ajax({						
							dataType : "json",
							url : "${url}/timekeeping/selection",
							data : {							
								dept : val
							},
							success : function(obj) {							
								eIdSel = "<option value='0'>Tất cả nhân viên</option>";
								for (i = 0; i < obj.length; i++) {
									eIdSel += "<option value='" + obj[i].employeeId + "'>"
											+ "Mã NV " + obj[i].employeeId +", "
											+ obj[i].fullName
											+ ", "
											+ obj[i].jobTitle
											+ "</option>";
								}
								$("#eId").html(eIdSel);
							}
						});
					
				});
	});

	function ConfirmDelete() {
	  return confirm("Bạn có chắc chắn muốn xóa không?");
	}
	
	$(function() {
		// Khởi tạo action/method cho leaveInfoForm form
		$("#leaveInfoForm").attr("action", "${url}/timekeeping/leaveInfo");
		$("#leaveInfoForm").attr("method", "POST");		
		$("button[id^=page]").each(function(i, el) {
			$(this).click(function() {
				if($(this).text()!=${leaveInfoForm.pageIndex}){
					$("#pageIndex").val($(this).text());
					$("#leaveInfoForm").submit();
				}
			});
		});
		
		$("#firstPage").click(function(){
			$("#pageIndex").val(1);
			$("#leaveInfoForm").submit();
		});
		
		$("#previousPage").click(function(){
			$("#pageIndex").val(${leaveInfoForm.pageIndex-1});
			$("#leaveInfoForm").submit();
		});
		
		$("#nextPage").click(function(){
			$("#pageIndex").val(${leaveInfoForm.pageIndex+1});
			$("#leaveInfoForm").submit();	
		});
		
		$("#lastPage").click(function(){
			$("#pageIndex").val(${leaveInfoForm.totalPages});
			$("#leaveInfoForm").submit();	
		});
		
		$("#numberRecordsOfPage").change(function(){
			$("#pageIndex").val(1);
			$("#totalPages").val(0);
			$("#totalRecords").val(0);
			$("#leaveInfoForm").submit();
		});	

	});

</script>
<title>Thông tin ngày nghỉ, công tác, làm thêm ...</title>
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
	<a href="${url}/timekeeping/addLeaveInfo"><button
			class="btn btn-lg btn-primary btn-sm">Thêm mới</button></a>
	<a href="${url}/timekeeping/"><button
			class="btn btn-lg btn-primary btn-sm">Quay lại thông tin chấm công</button></a>
	<br />	
	<br />
	<form:form action="leaveInfo" modelAttribute="leaveInfoForm" method="GET">
		<table class="table">
			<tr>
				<td>Chọn xem từ ngày:(*) &nbsp;<form:input path="date" type="date"
						required="required" class="form-control animated"/></td>
				<td>Đến ngày:(*) &nbsp;<form:input path="toDate" type="date"
						required="required" class="form-control animated"/></td>
				<td>Phòng: &nbsp;
					<form:select path="dept" class="form-control animated">
						<form:option value="all" label="Tất cả phòng ban"></form:option>
						<form:options items="${departmentMap}" var="dept"/>
					</form:select>
				</td>
				<td>Nhân viên: &nbsp;
					<form:select path="eId" class="form-control animated">
						<form:option value="0" label="Tất cả nhân viên"></form:option>
						<form:options items="${employeeMap}" var="eId"/>
					</form:select>
				</td>
				<td align="center">&nbsp;&nbsp;
					<input class="btn btn-lg btn-primary btn-sm" type="submit"
					value="Xem danh sách" />
				</td>
			</tr>
		</table>
			<br/>
			<c:if test="${not empty message}">
				<div class="alert alert-success">${message}</div>
			</c:if>
			
	</form:form>
	<br/>
	
		<form:form modelAttribute="leaveInfoForm" method="POST">
		<table class="table">
			<tr>
				<td><span>Tổng số ${leaveInfoForm.totalRecords} bản ghi</span></td>
				<td><span>Số trang: ${leaveInfoForm.totalPages}</span></td>
				<form:hidden path="pageIndex" /> 
				<form:hidden path="totalPages" /> 
				<form:hidden path="totalRecords" />			
				<td>Trang:</td>
				<td>
					<div class="btn-group btn-group-md">
						<c:choose>
							<c:when test="${leaveInfoForm.pageIndex<=1}">
								<button id="firstPage" type="button" class="btn btn-default disabled">Đầu</button>
								<button id="previousPage" type="button"	class="btn btn-default disabled">Trước</button>
							</c:when>
							<c:otherwise>
								<button id="firstPage" type="button" class="btn btn-default">Đầu</button>
								<button id="previousPage" type="button" class="btn btn-default">Trước</button>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${leaveInfoForm.totalPages<=3}">
								<c:forEach begin="1" end="${leaveInfoForm.totalPages}"
									varStatus="status">
									<c:choose>
										<c:when test="${status.index==leaveInfoForm.pageIndex}">
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
									<c:when test="${leaveInfoForm.pageIndex==1}">
										<button id="page1" type="button" class="btn btn-default active">1</button>
										<button id="page2" type="button" class="btn btn-default">2</button>
										<button id="page3" type="button" class="btn btn-default">3</button>
									</c:when>
									<c:when
										test="${leaveInfoForm.pageIndex==leaveInfoForm.totalPages}">
										<button id="page${leaveInfoForm.totalPages-2}" type="button"
											class="btn btn-default">${leaveInfoForm.totalPages-2}</button>
										<button id="page${leaveInfoForm.totalPages-1}" type="button"
											class="btn btn-default">${leaveInfoForm.totalPages-1}</button>
										<button id="page${leaveInfoForm.totalPages}" type="button"
											class="btn btn-default active">${leaveInfoForm.totalPages}</button>
									</c:when>
									<c:otherwise>
										<c:forEach begin="${leaveInfoForm.pageIndex - 1}"
											end="${leaveInfoForm.pageIndex + 1}" varStatus="status">
											<c:choose>
												<c:when test="${status.index==leaveInfoForm.pageIndex}">
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
								test="${leaveInfoForm.pageIndex==leaveInfoForm.totalPages}">
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
		<table class="table table-bordered">
			<tr>
				<th>Mã NV</th>
				<th>Họ tên</th>
				<th>Bộ phận</th>
				<th>Chức vụ</th>
				<th>Ngày</th>
				<th>Loại</th>
				<th>Số giờ/lần</th>
				<th>Ghi chú</th>

				<!-- <th>Sửa</th> -->
				<th style="color: red;">Xóa</th>
			</tr>
			<c:forEach var="leaveInfo" items="${leaveInfos}">
				<tr style="font-size: 10">
					<td>${leaveInfo.employeeId}</td>
					<td nowrap="nowrap">${leaveInfo.employeeName}</td>
					<td>${leaveInfo.department}</td>
					<td>${leaveInfo.title}</td>
					<td nowrap="nowrap">${leaveInfo.date}</td>
					<c:if test="${leaveInfo.timeValue == 4}">
						<td nowrap="nowrap">${leaveInfo.leaveName} nửa ngày</td>
					</c:if>
					<c:if test="${leaveInfo.timeValue != '4'}">
						<td nowrap="nowrap">${leaveInfo.leaveName}</td>
					</c:if>
					<td>${leaveInfo.timeValue}</td>
					<td>${leaveInfo.comment}</td>

					<%-- <td bgcolor="#F5F6CE"><a href="editLeaveInfo?employeeId=${leaveInfo.employeeId}&date=${leaveInfo.date}&leaveType=${leaveInfo.leaveType}">Sửa</a></td> --%>
					<td><a style="color: red;"
						href="deleteLeaveInfo?employeeId=${leaveInfo.employeeId}&date=${leaveInfo.date}&leaveType=${leaveInfo.leaveType}" Onclick="return ConfirmDelete()">Xóa</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>