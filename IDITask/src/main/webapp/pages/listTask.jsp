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
	$("#area")
		.change(
			function() {
				// Lấy dữ liệu của phòng
				val = $(this).val();							
				$
					.ajax({
						dataType : "json",
						url : "${url}/selectionArea",
						data : {											
							area : val
						},
						success : function(obj) {
							ownedBySel = "<option value='0'>Chưa giao cho ai</option>";
							for (i = 0; i < obj.length; i++) {
								ownedBySel += "<option value='" + obj[i].employeeId + "'>"
										+ obj[i].fullName
										+ ", chức vụ: "
										+ obj[i].jobTitle
										+ "</option>";
							}
							$("#ownedBy").html(ownedBySel);
						}
					});

			});
	});

	$(function() {
		// Khởi tạo action/method cho taskForm form
		$("#taskForm").attr("action", "${url}/");
		$("#taskForm").attr("method", "POST");		
		$("button[id^=page]").each(function(i, el) {
			$(this).click(function() {
				if($(this).text()!=${taskForm.pageIndex}){
					$("#pageIndex").val($(this).text());
					$("#taskForm").submit();
				}
			});
		});
		
		$("#firstPage").click(function(){
			$("#pageIndex").val(1);
			$("#taskForm").submit();
		});
		
		$("#previousPage").click(function(){
			$("#pageIndex").val(${taskForm.pageIndex-1});
			$("#taskForm").submit();
		});
		
		$("#nextPage").click(function(){
			$("#pageIndex").val(${taskForm.pageIndex+1});
			$("#taskForm").submit();	
		});
		
		$("#lastPage").click(function(){
			$("#pageIndex").val(${taskForm.totalPages});
			$("#taskForm").submit();	
		});
		
		$("#numberRecordsOfPage").change(function(){
			$("#pageIndex").val(1);
			$("#totalPages").val(0);
			$("#totalRecords").val(0);
			$("#taskForm").submit();
		});	

	});
</script>

<title>Danh sách công việc</title>
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
	background-color: #efeded;
}
</style>
</head>
<body>
	<a href="${url}/addNewTask"><button class="btn btn-primary btn-sm">Tạo việc mới</button></a>
	<a href="${url}/prepareReport"><button class="btn btn-primary btn-sm">Thống kê/báo cáo</button></a>
	<br />
	<br />
	<form:form modelAttribute="taskForm" method="POST">
		<table class="table">
			<tr>
				<td nowrap="nowrap">Tìm theo phòng:</td>
				<td><form:select path="area" class="form-control animated">
						<form:option value="" label="- Chọn tất cả phòng ban -" />
						<form:options items="${departmentMap}" />
					</form:select>
				</td>
				<td nowrap="nowrap">Tìm theo người làm:</td>
				<td><form:select path="ownedBy" class="form-control animated">
						<form:option value="0" label="- Chọn tất cả người làm -" />
						<c:forEach items="${employeesList}" var="employee">
							<form:option value="${employee.employeeId}">${employee.fullName},&nbsp;${employee.jobTitle}</form:option>
						</c:forEach>
					</form:select>
				</td>
				<td nowrap="nowrap">Tìm theo trạng thái:</td>
				<td><form:select path="status" class="form-control animated">
						<form:option value="" label="- Chọn tất cả trạng thái công việc -" />
						<c:forEach items="${statusList}" var="status">
							<form:option value="${status}">${status}</form:option>
						</c:forEach>
					</form:select>
				</td>
			</tr>
			<tr>
				<td colspan="2" nowrap="nowrap"><i>Tìm công việc theo: Mã
						việc/Tên việc/Người được giao/Trạng thái công việc/Mã phòng/Kế hoạch cho tháng </i>
				</td>
				<td align="center" colspan="2"><form:input path="searchValue" class="form-control"/>
				</td>
				<td colspan="2" align="right"> 
					<input id="search" class="btn btn-lg btn-primary btn-sm" type="submit" value="Tìm kiếm" align="right" />
				</td>
			</tr>
		</table>
	
		<table class="table">
		<tr>
			<td><span>Tổng số ${taskForm.totalRecords} công việc</span></td>
			<td><span>Số trang: ${taskForm.totalPages}</span></td>
			<form:hidden path="pageIndex" /> 
			<form:hidden path="totalPages" /> 
			<form:hidden path="totalRecords" />			
			<td>Trang:</td>
			<td>
				<div class="btn-group btn-group-md">
					<c:choose>
						<c:when test="${taskForm.pageIndex==1}">
							<button id="firstPage" type="button" class="btn btn-default disabled">Đầu</button>
							<button id="previousPage" type="button"	class="btn btn-default disabled">Trước</button>
						</c:when>
						<c:otherwise>
							<button id="firstPage" type="button" class="btn btn-default">Đầu</button>
							<button id="previousPage" type="button" class="btn btn-default">Trước</button>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${taskForm.totalPages<=3}">
							<c:forEach begin="1" end="${taskForm.totalPages}"
								varStatus="status">
								<c:choose>
									<c:when test="${status.index==taskForm.pageIndex}">
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
								<c:when test="${taskForm.pageIndex==1}">
									<button id="page1" type="button" class="btn btn-default active">1</button>
									<button id="page2" type="button" class="btn btn-default">2</button>
									<button id="page3" type="button" class="btn btn-default">3</button>
								</c:when>
								<c:when
									test="${taskForm.pageIndex==taskForm.totalPages}">
									<button id="page${taskForm.totalPages-2}" type="button"
										class="btn btn-default">${taskForm.totalPages-2}</button>
									<button id="page${taskForm.totalPages-1}" type="button"
										class="btn btn-default">${taskForm.totalPages-1}</button>
									<button id="page${taskForm.totalPages}" type="button"
										class="btn btn-default active">${taskForm.totalPages}</button>
								</c:when>
								<c:otherwise>
									<c:forEach begin="${taskForm.pageIndex - 1}"
										end="${taskForm.pageIndex + 1}" varStatus="status">
										<c:choose>
											<c:when test="${status.index==taskForm.pageIndex}">
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
							test="${taskForm.pageIndex==taskForm.totalPages || taskForm.totalPages == 0}">
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
		<c:if test="${not empty message}">
			<div class="alert alert-success">${message}</div>
		</c:if>
		<table class="table table-striped">
			<tr>
				<th nowrap="nowrap">Mã cv</th>
				<th>Tên công việc</th>
				<th>Người làm</th>
				<th>Phòng</th>				
				<th>Trạng thái</th>
				<th nowrap="nowrap">Độ ưu tiên</th>
				<th nowrap="nowrap">Kế hoạch tháng</th>
				<th>Ngày cập nhật</th>
			</tr>
			<c:forEach var="task" items="${tasks}">
				<tr>
					<td>${task.taskId}</td>
					<td><a href="/IDITask/editTask?tab=1&taskId=${task.taskId}">${task.taskName}</a></td>					
					<c:if test="${task.ownedBy == 0}">
						<td nowrap="nowrap">Chưa giao cho ai</td>
					</c:if>
					<c:if test="${task.ownedBy > 0}">
						<td nowrap="nowrap">${task.ownerName}</td>
					</c:if>
					<td nowrap="nowrap">${task.area}</td>
					<td nowrap="nowrap">${task.status}</td>
					<td nowrap="nowrap">${task.priority}</td>
					<td>${task.plannedFor}</td>
					<td nowrap="nowrap">${task.updateTS}</td>
				</tr>
			</c:forEach>
		</table>
</body>
</html>