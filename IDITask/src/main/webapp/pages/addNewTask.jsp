<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- Initialize the plugin: -->
<script src="${url}/public/js/jquery.min.js"></script>
<script src="${url}/public/js/bootstrap-datetimepicker.min.js"></script>
<script src="${url}/public/js/bootstrap-datetimepicker.vi.js"></script>
<script src="${url}/public/js/bootstrap-autosize.js"></script>
<script type="text/javascript">
	var $j = jQuery.noConflict();
	$j(function() {		
		$j(".datetime").datetimepicker({
			//language : 'vi',
			format : 'dd/mm/yyyy',
			todayBtn : 1,
			autoclose : 1,
			todayHighlight : 1,
			startView : 2,
			minView : 2,
			forceParse : 0,
			pickerPosition : "bottom-left"
		});
		
		$j("#area")
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
											$j("#ownedBy").html(ownedBySel);
											
											secondOwnedSel = "<option value='0'>Chưa ai backup</option>";
											for (i = 0; i < obj.length; i++) {
												secondOwnedSel += "<option value='" + obj[i].employeeId + "'>"
														+ obj[i].fullName
														+ ", chức vụ: "
														+ obj[i].jobTitle
														+ "</option>";
											}
											$j("#secondOwned").html(secondOwnedSel);
											
											verifyBySel = "<option value='0'>Chưa ai giám sát</option>";
											for (i = 0; i < obj.length; i++) {
												verifyBySel += "<option value='" + obj[i].employeeId + "'>"
														+ obj[i].fullName
														+ ", chức vụ: "
														+ obj[i].jobTitle
														+ "</option>";
											}
											$j("#verifyBy").html(verifyBySel);
										}
									});

						});
	});

  	$j(function(){
		$j('.normal').autosize();		
		$j('.animatedArea').autosize({append: "\n"});
	});
  	
  	$j(function () {
           $j('#plannedFor').datetimepicker({
               viewMode: 'years'
           });
       });
</script>
<title>Tập đoàn IDI - Quản lý công việc</title>
<style>
.error-message {
	color: red;
	font-size: 90%;
	font-style: italic;
}
</style>
</head>
<body>	
	<a href="${pageContext.request.contextPath}/"><button
		class="btn btn-lg btn-primary btn-sm">Danh sách tất cả
		công việc</button></a>
	<a href="${pageContext.request.contextPath}/listTasksOwner"><button
		class="btn btn-lg btn-primary btn-sm">Công việc của tôi</button></a><br/><br/>
	
	<form:form modelAttribute="taskForm" method="POST"
		action="insertNewTask" enctype="multipart/form-data">
		<!-- <input class="btn btn-lg btn-primary btn-sm" type="submit" value="Lưu"
			name="Lưu" />
		<br /> 
		<br /> -->
		<div class="table table-bordered">
			<table>
				<tr>
					<td><form:errors path="taskName" class="error-message" /></td>
					<td><form:errors path="dueDate" class="error-message" /></td>
				</tr>
			</table>
			<table class="table">
				<tbody>		
					<tr>
						<td bgcolor="#FAFAFA">Nhóm/loại công việc:(*)</td>
						<td colspan="3">
							<form:select path="type" class="form-control animated">
								<form:option value="" label="-Chọn nhóm/loại công việc-" />
								<form:options items="${taskCategoryMap}" />
							</form:select>
						</td>							
					</tr>
					<tr>
						<td bgcolor="#FAFAFA">Tên việc:(*)</td>
						<td colspan="3"><form:input path="taskName"
								class="form-control animated" required="required" size="110" />
						</td>							
					</tr>
					<tr>
						<td bgcolor="#FAFAFA">Công việc thuộc:</td>
						<td><form:select path="area" class="form-control animated">
								<form:option value="" label="-Phòng ban-" />
								<form:options items="${departmentMap}" />
							</form:select></td>
						<td bgcolor="#FAFAFA">Trạng thái:</td>	
						<td><form:select path="status"
								class="form-control animated">
								<form:option value="Mới" label="Mới" />
								<form:option value="Đang làm" label="Đang làm"
									style="color:green" />
								<form:option value="Tạm dừng" label="Tạm dừng"
									style="color:orange" />
								<form:option value="Hủy bỏ" label="Hủy bỏ" style="color:red" />
								<form:option value="Chờ đánh giá" label="Chờ đánh giá"
									style="color:navy" />
								<form:option value="Đã xong" label="Đã xong"
									style="color:blue" />
							</form:select>
						</td>							
					</tr>
					<tr>
						<td bgcolor="#FAFAFA">Độ ưu tiên:</td>
						<td><form:select path="priority"
								class="form-control animated">
								<form:option value="0" label="-Không chọn-" />
								<form:option value="1" label="1" style="color:red" />
								<form:option value="2" label="2" style="color:orange" />
								<form:option value="3" label="3" style="color:yellow" />
								<form:option value="4" label="4" />
								<form:option value="5" label="5" />
								<form:option value="6" label="6" />
								<form:option value="7" label="7" />
								<form:option value="8" label="8" />
								<form:option value="9" label="9" />
								<form:option value="10" label="10" />
							</form:select></td>
						<td bgcolor="#FAFAFA">Kế hoạch cho tháng:</td>
						<td><form:select path="month">
								<form:option value="0" label="Chọn tháng" />
								<form:option value="1" label="Tháng 1" />
								<form:option value="2" label="Tháng 2" />
								<form:option value="3" label="Tháng 3" />
								<form:option value="4" label="Tháng 4" />
								<form:option value="5" label="Tháng 5" />
								<form:option value="6" label="Tháng 6" />
								<form:option value="7" label="Tháng 7" />
								<form:option value="8" label="Tháng 8" />
								<form:option value="9" label="Tháng 9" />
								<form:option value="10" label="Tháng 10" />
								<form:option value="11" label="Tháng 11" />
								<form:option value="12" label="Tháng 12" />
							</form:select>
							 &nbsp;Năm&nbsp; 
							<jsp:useBean id="now" class="java.util.Date" />
							<fmt:formatDate var="year" value="${now}" pattern="yyyy" />
							<form:select path="year">
								<c:forEach begin="0" end="2" varStatus="loop">
									<c:set var="currentYear" value="${year + loop.index}" />
									<option value="${currentYear}"
										${form.yearReport == currentYear ? 'selected="selected"' : ''}>${currentYear}</option>
								</c:forEach>
							</form:select>
			            <!--    <form:input path="plannedFor" id="plannedFor" class="form-control"/>
			                 <span class="input-group-addon">
			                    <span class="glyphicon glyphicon-calendar">
			                    </span>
			                </span> -->
						</td>
						<%-- 	<td><form:input path="plannedFor" type="month"
								class="form-control animated" /></td> --%>						
					</tr>
					<tr>
						<td bgcolor="#FAFAFA">Giao cho:</td>
						<td><form:select path="ownedBy" class="form-control animated">
								<form:option value="0" label="Chưa giao cho ai" />
								<c:forEach items="${employeesList}" var="employee">
									<form:option value="${employee.employeeId}">${employee.fullName},&nbsp;${employee.jobTitle}</form:option>
								</c:forEach>
							</form:select></td>
						<td bgcolor="#FAFAFA">Thời gian kế hoạch:</td>
						<td><form:input path="estimate" type="number" step="0.25" min="0" value="0" />&nbsp;&nbsp;
							<form:select path="estimateTimeType">
								<%-- <form:option value="m" label="Phút" /> --%>
								<form:option value="h" label="Giờ" />
								<form:option value="d" label="Ngày" />
								<form:option value="w" label="Tuần" />
							</form:select></td>		
					</tr>
					<tr>
						<td bgcolor="#FAFAFA">Backup bởi:</td>
						<td><form:select path="secondOwned"
								class="form-control animated">
								<form:option value="0" label="Chưa ai backup" />
								<c:forEach items="${employeesList}" var="employee">
									<form:option value="${employee.employeeId}">${employee.fullName},&nbsp;${employee.jobTitle}</form:option>
								</c:forEach>
							</form:select></td>							
						<td bgcolor="#FAFAFA">Thời gian đã làm:</td>
						<td><form:input path="timeSpent" type="number" step="0.25" min="0" value="0" />&nbsp;&nbsp;
							<form:select path="timeSpentType">
								<%-- <form:option value="m" label="Phút" /> --%>
								<form:option value="h" label="Giờ" />
								<form:option value="d" label="Ngày" />
								<form:option value="w" label="Tuần" />
							</form:select></td>	
					</tr>
					<tr>
						<td bgcolor="#FAFAFA">Người giám sát:</td>
						<td><form:select path="verifyBy"
								class="form-control animated">
								<form:option value="0" label="Chưa ai giám sát" />
								<c:forEach items="${employeesList}" var="employee">
									<form:option value="${employee.employeeId}">${employee.fullName},&nbsp;${employee.jobTitle}</form:option>
								</c:forEach>
							</form:select></td>
						<td bgcolor="#FAFAFA">Ngày phải xong:</td>
						<td>
							<div class="input-group date datetime smallform">
								<form:input path="dueDate" class="form-control"
								 placeholder="dd/mm/yyyy" autocomplete="off" />
								<span class="input-group-addon"><span
									class="glyphicon glyphicon-calendar"></span></span>
							</div>	
						
<%-- 						<form:input path="dueDate" type="date"
								class="form-control animated" /> --%>
						</td>		
					</tr>
					<tr>
						<td bgcolor="#FAFAFA">Mô tả:</td>
						<td colspan="3"><form:textarea class="form-control animatedArea"
								path="description" cols="110" rows="3"
								placeholder='Mô tả về công việc' /></td>
					</tr>
				</tbody>
			</table>
		</div>
		<input class="btn btn-lg btn-primary btn-sm" type="submit" value="Lưu"
			name="Lưu" /> <br/><br/>
	</form:form>
<%-- 	<a href="${pageContext.request.contextPath}/"><button
			class="btn btn-lg btn-primary btn-sm">Quay lại danh sách
			công việc</button></a> --%>
</body>
</html>