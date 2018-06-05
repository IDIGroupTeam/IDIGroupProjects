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

<link rel="bootstrap-tab-panel"
	href="${url}/public/css/bootstrap-tab-panel.css" />
<link rel="bootstrap-autosize"
	href="${url}/public/css/bootstrap-autosize.css" />
<link rel="stylesheet" href="${url}/public/css/jquery-ui.css">
<link rel="stylesheet" href="${url}/public/css/style.css">

<!-- Initialize the plugin: -->
<script src="${url}/public/js/bootstrap-autosize.js"></script>
<script src="${url}/public/js/jquery-ui.js"></script>
<script src="${url}/public/js/bootstrap3-typeahead.min.js"></script>
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

											secondOwnedSel = "<option value='0'>Chưa ai backup</option>";
											for (i = 0; i < obj.length; i++) {
												secondOwnedSel += "<option value='" + obj[i].employeeId + "'>"
														+ obj[i].fullName
														+ ", chức vụ: "
														+ obj[i].jobTitle
														+ "</option>";
											}
											$("#secondOwned").html(
													secondOwnedSel);

											verifyBySel = "<option value='0'>Chưa ai giám sát</option>";
											for (i = 0; i < obj.length; i++) {
												verifyBySel += "<option value='" + obj[i].employeeId + "'>"
														+ obj[i].fullName
														+ ", chức vụ: "
														+ obj[i].jobTitle
														+ "</option>";
											}
											$("#verifyBy").html(verifyBySel);
										}
									});

						});
	});

	$(function() {
		$('.normal').autosize();
		$('.animatedArea').autosize({
			append : "\n"
		});
	});

	//$.get("/lookingTask", function(data){
	//  $("#name").typeahead({ source:data });
	//},'json');

	/*     Spring.addDecoration(new Spring.AjaxEventDecoration({                           
	 elementId: 'subcribersPopup',  
	 formId: 'taskForm',  
	 event: 'onclick',  
	 popup: true ,
	 params: {fragments: "taskForm"}
	 })); 
	
	 function openPopupPage() {  
	 window.location.href = '${url}/updateSubscriber&taskId=${taskForm.taskId}';  
	 } */

	/* 	function openPopup(url, name, width, height) {
	 window.open(url, name, "width=" + width + ",height=" + height
	 + ",status=no,toolbar=no,menubar=no,location=no");
	 } */

	/*      $(function() {
	 $( "#tabs" ).tabs();
	 }); */

	 function toggleAll(it) 
	 { 
	   oForm = it.form; 
	   oElements = oForm.elements['taskIds']; 
	   bChecked = it.checked;
	   if(oElements.length) 
	   { 
	     for(i = 0; i < oElements.length; i++) oElements[i].checked = bChecked;
	     var table = document.getElementById("RESULTS_TABLE");
	     var myTR = table.tBodies[0].getElementsByTagName("tr");
	     if (bChecked){
	         for (var i=0;i<myTR.length;i++) {
	             myTR[i].style.backgroundColor="";
	             myTR[i].className = 'checked';
	         }
	     }else{
	         for (var i=0;i<myTR.length;i++) {
	             myTR[i].style.backgroundColor="";
	             if (i%2) {
	                myTR[i].className = 'gray';
	             }else{
	                myTR[i].className = 'white';
	             }
	         }
	     }
	   } 
	 }  
	 
	function check(it) {
		var tr = it.parentNode.parentNode;
		tr.style.backgroundColor = "";
		if (it.checked) {
			tr.className = 'checked';
		} else {
			var index = getRowIndex(tr.id);
			if (index % 2) {
				tr.className = 'white';
			} else {
				tr.className = 'gray';
			}
		}
	}

	function validateForm() {
		//var len = form.elements.length;
		var bChecked = false;
		var elements = document.getElementsByTagName('input');
		var i = 0;
		var len = elements.length;

		for (var i = 0; i < len; i++) {
			var ele = elements[i];
			if (ele.type == "checkbox") {
				if (ele.checked) {
					bChecked = true;
					break;
				}
			}
		}

		if (!bChecked) {
			//alert('Please select at least one row.');
		}

		return bChecked;
	}
	
	function saveTasksRelated(){
	    if(!validateForm()){
	        alert('Hãy chọn it nhất một công việc liên quan trước khi lưu.');
	        return;
	    } else {
	       
	    }
	}
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
	&nbsp;&nbsp;&nbsp;
	<a href="${pageContext.request.contextPath}/">
		<button class="btn btn-lg btn-primary btn-sm">Quay lại danh
			sách công việc</button>
	</a>
	<br />
	<br />
	<div id="exTab2" class="container">
		<ul class="nav nav-tabs">
			<li class="${active1}"><a href="#1" data-toggle="tab">Thông
					tin chung</a></li>
			<li class="${active2}"><a href="#2" data-toggle="tab">Người
					liên quan</a></li>
			<li class="${active3}"><a href="#3" data-toggle="tab">Công
					việc liên quan</a></li>
		</ul>
		<div class="tab-content ">
			<div class="${tabActive1}" id="1">
				<br />
				<form:form modelAttribute="taskForm" method="POST"
					action="updateTask" enctype="multipart/form-data">
					<input class="btn btn-lg btn-primary btn-sm" type="submit"
						value="Lưu" name="Lưu" />
					<br />
					<br />
					<form:hidden path="taskId" />
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td bgcolor="#FAFAFA">Mã công việc:</td>
								<td>${taskForm.taskId}</td>
								<td align="right">Trạng thái của công việc:</td>
								<td><form:select path="status"
										class="form-control animated">
										<form:option value="Mới tạo" label="Mới tạo" />
										<form:option value="Đang làm" label="Đang làm"
											style="color:green" />
										<form:option value="Tạm dừng" label="Tạm dừng"
											style="color:orange" />
										<form:option value="Hủy bỏ" label="Hủy bỏ" style="color:red" />
										<form:option value="Chờ đánh giá" label="Chờ đánh giá"
											style="color:navy" />
										<form:option value="Đã xong" label="Đã xong"
											style="color:blue" />
									</form:select></td>
							</tr>
							<tr>
								<td bgcolor="#FAFAFA">Tên việc:(*)</td>
								<td colspan="3"><form:input path="taskName"
										required="required" size="110" class="form-control animated" /></td>
							</tr>
						</tbody>
					</table>

					<br />
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td bgcolor="#FAFAFA">Công việc thuộc phòng:</td>
								<td><form:select path="area" class="form-control animated">
										<form:option value="" label="-Phòng ban-" />
										<form:options items="${departmentMap}" />
									</form:select></td>
								<td bgcolor="#FAFAFA">Kế hoạch cho tháng:</td>
								<td><form:input path="plannedFor" type="Month"
										class="form-control animated" /></td>
							</tr>
							<tr>
								<td bgcolor="#FAFAFA">Độ ưu tiên:</td>
								<td><form:select path="priority"
										class="form-control animated">
										<form:option value="0" label="-Không chọn-" />
										<form:option value="1" label="1" />
										<form:option value="2" label="2" />
										<form:option value="3" label="3" />
										<form:option value="4" label="4" />
										<form:option value="5" label="5" />
										<form:option value="6" label="6" />
										<form:option value="7" label="7" />
										<form:option value="8" label="8" />
										<form:option value="9" label="9" />
										<form:option value="10" label="10" />
									</form:select></td>

								<td bgcolor="#FAFAFA">Thời gian ước lượng để làm:</td>
								<td><form:input path="estimate" type="number"
										class="col-xs-4" /> &nbsp; <form:select
										path="estimateTimeType">
										<form:option value="m" label="Phút" />
										<form:option value="h" label="Giờ" />
										<form:option value="d" label="Ngày" />
										<form:option value="w" label="Tuần" />
									</form:select></td>

							</tr>
							<tr>
								<td bgcolor="#FAFAFA"></td>
								<td></td>
								<td bgcolor="#FAFAFA">Thời gian đã làm:</td>
								<td><form:input path="timeSpent" type="number"
										class="col-xs-4" /> &nbsp; <form:select path="timeSpentType">
										<form:option value="m" label="Phút" />
										<form:option value="h" label="Giờ" />
										<form:option value="d" label="Ngày" />
										<form:option value="w" label="Tuần" />
									</form:select></td>
							</tr>
							<tr>
								<td bgcolor="#FAFAFA">Được tạo bởi:</td>
								<c:if test="${taskForm.createdBy == 0}">
									<td>(Cần xong phần phân quyền trước)</td>
								</c:if>
								<c:if test="${taskForm.createdBy > 0}">
									<td>${taskForm.createdBy}</td>
								</c:if>
								<td bgcolor="#FAFAFA">Ngày tạo:</td>
								<td>${taskForm.creationDate}</td>
							</tr>
							<tr>
								<td bgcolor="#FAFAFA">Giao cho:</td>
								<td><form:select path="ownedBy"
										class="form-control animated">
										<form:option value="0" label="Chưa giao cho ai" />
										<c:forEach items="${employeesList}" var="employee">
											<form:option value="${employee.employeeId}">${employee.fullName},&nbsp;${employee.jobTitle}</form:option>
										</c:forEach>
									</form:select></td>
								<td bgcolor="#FAFAFA">Ngày phải xong:</td>
								<td><form:input path="dueDate" type="date"
										class="form-control animated" /></td>
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
								<td bgcolor="#FAFAFA">Người giám sát:</td>
								<td><form:select path="verifyBy"
										class="form-control animated">
										<form:option value="0" label="Chưa chọn ai" />
										<c:forEach items="${employeesList}" var="employee">
											<form:option value="${employee.employeeId}">${employee.fullName},&nbsp;${employee.jobTitle}</form:option>
										</c:forEach>
									</form:select></td>
							</tr>
							<%--<tr>
								<td bgcolor="#FAFAFA">Những người liên quan:</td>
								<td colspan="2">truongnv, tuyen px, haitd, anhnv, xxx, yyy
								</td>
								<td align="left"><input
									class="btn btn-lg btn-primary btn-sm" type="button"
									value="Cập nhật danh sách"
									onclick="openPopup('${url}/updateSub?taskId=${taskForm.taskId}&_eventId=popup', 'Popup', 640, 480);" />
								</td>
							</tr> --%>
							<tr>
								<td bgcolor="#FAFAFA">Mô tả:</td>
								<td colspan="3"><form:textarea path="description"
										cols="110" class="form-control animatedArea" rows="3" /></td>
							</tr>
						</tbody>
					</table>
					<input class="btn btn-lg btn-primary btn-sm" type="submit"
						value="Lưu" name="Lưu" />
					<br />
					<br />
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td style="background: infobackground;"><b>Thảo luận </b></td>
							</tr>
							<tr>
								<td><form:textarea path="content"
										class="form-control animatedArea" rows="3" /></td>
							</tr>
							<c:forEach var="taskComment" items="${listComment}">
								<tr>
									<c:if test="${taskComment.commentedBy == 0}">
										<td>${taskComment.commentIndex}.&nbsp;<b
											style="color: blue;"> Cần xong phân quền mới có thông tin
												người comment</b> &nbsp;${taskComment.commentTime}
										</td>
									</c:if>
									<c:if test="${taskComment.commentedBy > 0}">
										<td>${taskComment.commentIndex}.&nbsp;${taskComment.commentedBy}&nbsp;${taskComment.commentTime}</td>
									</c:if>
								</tr>
								<tr>
									<td>${taskComment.content}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

				</form:form>
				<a href="${pageContext.request.contextPath}/"><button
						class="btn btn-lg btn-primary btn-sm">Quay lại danh sách
						công việc</button></a> <br />
			</div>
			<div class="${tabActive2}" id="2">
				<form:form modelAttribute="taskForm" method="POST"
					action="updateSub" enctype="multipart/form-data">
					<br />
					<form:hidden path="taskId" />
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td>Mã công việc: ${taskForm.taskId}</td>
								<td>Tên công việc: ${taskForm.taskName}</td>
							</tr>
						</tbody>
					</table>
					<table class="table table-bordered">
						<tr>
							<th>Người đang liên quan (chọn để loại bỏ)</th>
							<th>Chọn thêm người liên quan</th>
						</tr>
						<tr>
							<c:if test="${sub != null}">
								<td><form:select path="subscriber"
										class="form-control animated" multiple="multiple" size="5">
										<c:forEach items="${subscriberList}" var="subscriber">
											<form:option value="${subscriber.employeeId}">${subscriber.fullName},&nbsp;${subscriber.jobTitle}
										</form:option>
										</c:forEach>
									</form:select></td>
							</c:if>
							<c:if test="${sub == null}">
								<td><i>Chưa chọn ai</i></td>
							</c:if>
							<td><form:select path="forSubscriber"
									class="form-control animated" multiple="multiple" size="5">
									<c:forEach items="${employeesListS}" var="employeeS">
										<form:option value="${employeeS.employeeId}">${employeeS.fullName},&nbsp;${employeeS.jobTitle}
									</form:option>
									</c:forEach>
								</form:select></td>
						</tr>
					</table>
					<input class="btn btn-lg btn-primary btn-sm" type="submit"
						value="Lưu" name="Lưu" />
				</form:form>
			</div>
			<div class="${tabActive3}" id="3">
				<br />
				<form:form modelAttribute="taskForm" method="GET"
					action="editTask" enctype="multipart/form-data">
					<form:hidden path="taskId" />				
					<input type="hidden" name="tab" value="3">
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td>Mã công việc: ${taskForm.taskId}</td>
								<td>Tên công việc: ${taskForm.taskName}</td>
							</tr>
						</tbody>
					</table>
					<h4>Chọn để thêm công việc liên quan</h4>
						<div class="table-responsive">						
						<table id="RESULTS_TABLE" class="table table-bordered table-hover">
						<tr>
							<td>
								<c:if test="${tasksFound != null}">
									<button class="btn btn-lg btn-primary btn-sm" onclick="javascript:saveTasksRelated()">Lưu</button>
								</c:if>	
							</td>	
							<td colspan="3">Thêm công việc liên quan: Nhập mã/tên/người làm/trạng
								thái/mã phòng/kế hoạch cho tháng</td>							
							<td><form:input path="searchValue" required="required"
									class="form-control" />
							</td>			
							<td><input class="btn btn-lg btn-primary btn-sm"
								type="submit" value="Tìm" name="Tìm" /></td>	
						</tr>					
						<c:if test="${tasksFound != null}">
							<tr>
								<th>
						            <div style="visibility: visible"><label for="chkBoxAll"> Chọn tất</label></div>
						            &nbsp;<input type="checkbox" name="CHECK_ALL" onclick="toggleAll(this)" id="chkBoxAll"/>
						        </th>
								<th>Mã cv</th>
								<th>Tên công việc</th>
								<th>Người làm</th>
								<th>Trạng thái</th>
								<th>Ngày cập nhật gần nhất</th>
							</tr>
							<c:forEach var="task" items="${tasksFound}">
								<tr>
									<td nowrap="nowrap"><label
										for="check<c:out value='${index.count}'/>"></label> <input
										type="checkbox" id="check<c:out value='${index.count}'/>"
										name="taskIds" value="<c:out value='${task.taskId}'/>"
										onclick="javascript: check(this)" /></td>
									<td>${task.taskId}</td>
									<td><a
										href="/IDITask/editTask?tab=1&taskId=${task.taskId}">${task.taskName}</a></td>
									<c:if test="${task.ownedBy == 0}">
										<td>Chưa giao cho ai</td>
									</c:if>
									<c:if test="${task.ownedBy > 0}">
										<td>${task.ownerName}</td>
									</c:if>
									<td>${task.status}</td>
									<td>${task.updateTS}</td>
								</tr>
							</c:forEach>
						</c:if>
						</table>						
					</div>
					
				</form:form>
				<h4>Các công việc liên quan</h4>
				<table class="table table-bordered">
					<c:if test="${tasksRelated == null}">
						<tr>
							<td>Không có công việc liên quan
						</tr>
					</c:if>
					<c:if test="${tasksRelated != null}">
						<tr>
							<th>Bỏ lq</th>
							<th>Mã cv</th>
							<th>Tên công việc</th>
							<th>Người làm</th>
							<th>Trạng thái</th>
							<th>Ngày cập nhật gần nhất</th>							
						</tr>
						<c:forEach var="task" items="${tasksRelated}">
							<tr>
								<td><a href="/IDITask/removeTaskRelated?tab=3&taskIdRemove=${task.taskId}&taskId=${taskForm.taskId}"><button class="small" style="background-color: orange;">Bỏ</button></a></td>
								<td>${task.taskId}</td>
								<td><a href="/IDITask/editTask?tab=1&taskId=${task.taskId}">${task.taskName}</a></td>
								<c:if test="${task.ownedBy == 0}">
									<td>Chưa giao cho ai</td>
								</c:if>
								<c:if test="${task.ownedBy > 0}">
									<td>${task.ownerName}</td>
								</c:if>
								<td>${task.status}</td>
								<td>${task.updateTS}</td>								
							</tr>
						</c:forEach>
					</c:if>
				</table>

			</div>
		</div>
	</div>
	<!-- Bootstrap core JavaScript ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<!-- 	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script
		src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script> -->
</body>
</html>