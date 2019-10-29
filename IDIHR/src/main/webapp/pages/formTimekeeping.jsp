<%@page import="com.idi.hr.common.PropertiesManager"%>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Tập đoàn IDI - Dữ liệu chấm công nhân viên</title>

<style>
.error-message {
	color: red;
	font-size: 90%;
	font-style: italic;
}
</style>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<script src="${url}/public/js/jquery.min.js"></script>
<script src="${url}/public/js/bootstrap-datetimepicker.min.js"></script>
<script src="${url}/public/js/bootstrap-datetimepicker.vi.js"></script>


<script type="text/javascript">
	var $j = jQuery.noConflict();
	$j(function() {
		//alert($j.fn.jquery);
		
		/* 		// Wait for window load
		 $(window).load(function(){
		 // Animate loader off screen
		 //$("button").click(function(){
		 $(".loader").fadeOut("slow");
		 //});	
		 }); */

		/* 		$j('#comment').editable({
		 type : 'text',
		 pk : 1,
		 name : 'comment',
		 url : '${url}/timekeeping/ghichu',
		 title : 'thêm ghi chú'
		 }); */

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
		 
		$j("#dept")
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
														+ "Mã NV "
														+ obj[i].employeeId
														+ ", "
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
	/* 
	 function dragStart(event, id) {

	 var el;
	 var x, y;

	 // If an element id was given, find it. Otherwise use the element being
	 // clicked on.

	 if (id)
	 dragObj.elNode = document.getElementById(id);
	 else {
	 if (browser.isIE)
	 dragObj.elNode = window.event.srcElement;
	 if (browser.isNS)
	 dragObj.elNode = event.target;

	 // If this is a text node, use its parent element.

	 if (dragObj.elNode.nodeType == 3)
	 dragObj.elNode = dragObj.elNode.parentNode;
	 }

	 // Get cursor position with respect to the page.

	 if (browser.isIE) {
	 x = window.event.clientX + document.documentElement.scrollLeft
	 + document.body.scrollLeft;
	 y = window.event.clientY + document.documentElement.scrollTop
	 + document.body.scrollTop;
	 }
	 if (browser.isNS) {
	 x = event.clientX + window.scrollX;
	 y = event.clientY + window.scrollY;
	 }

	 // Save starting positions of cursor and element.

	 dragObj.cursorStartX = x;
	 dragObj.cursorStartY = y;
	 dragObj.elStartLeft = dragObj.elNode.offsetLeft; //parseInt(dragObj.elNode.style.left, 10);
	 dragObj.elStartTop = dragObj.elNode.offsetTop; //parseInt(dragObj.elNode.style.top,  10);

	 if (isNaN(dragObj.elStartLeft))
	 dragObj.elStartLeft = x;
	 if (isNaN(dragObj.elStartTop))
	 dragObj.elStartTop = y;

	 // Update element's z-index.

	 dragObj.elNode.style.zIndex = ++dragObj.zIndex;

	 // Capture mousemove and mouseup events on the page.

	 if (browser.isIE) {
	 document.attachEvent("onmousemove", dragGo);
	 document.attachEvent("onmouseup", dragStop);
	 window.event.cancelBubble = true;
	 window.event.returnValue = false;
	 }
	 if (browser.isNS) {
	 document.addEventListener("mousemove", dragGo, true);
	 document.addEventListener("mouseup", dragStop, true);
	 event.preventDefault();
	 }
	 }

	 function dragGo(event) {

	 var x, y;

	 // Get cursor position with respect to the page.

	 if (browser.isIE) {
	 x = window.event.clientX + document.documentElement.scrollLeft
	 + document.body.scrollLeft;
	 y = window.event.clientY + document.documentElement.scrollTop
	 + document.body.scrollTop;
	 }
	 if (browser.isNS) {
	 x = event.clientX + window.scrollX;
	 y = event.clientY + window.scrollY;
	 }

	 // Move drag element by the same amount the cursor has moved.

	 dragObj.elNode.style.left = (dragObj.elStartLeft + x - dragObj.cursorStartX)
	 + "px";
	 dragObj.elNode.style.top = (dragObj.elStartTop + y - dragObj.cursorStartY)
	 + "px";

	 if (browser.isIE) {
	 window.event.cancelBubble = true;
	 window.event.returnValue = false;
	 }
	 if (browser.isNS)
	 event.preventDefault();
	 }

	 function dragStop(event) {

	 // Stop capturing mousemove and mouseup events.

	 if (browser.isIE) {
	 document.detachEvent("onmousemove", dragGo);
	 document.detachEvent("onmouseup", dragStop);
	 }
	 if (browser.isNS) {
	 document.removeEventListener("mousemove", dragGo, true);
	 document.removeEventListener("mouseup", dragStop, true);
	 }
	 } */
</script>

</head>
<body>
	<%
		//	PropertiesManager hr = new PropertiesManager("hr.properties");
		//	int timeInMorning = Integer.parseInt(hr.getProperty("TIME_CHECK_IN_MORNING"));
		//	int timeOutMorning = Integer.parseInt(hr.getProperty("TIME_CHECK_OUT_MORNING"));
		//	int timeInAfternoon = Integer.parseInt(hr.getProperty("TIME_CHECK_IN_AFTERNOON"));
		//	int timeOutAfternoon = Integer.parseInt(hr.getProperty("TIME_CHECK_OUT_AFTERNOON"));
	%>
	<a href="${url}/timekeeping/prepareGenerateLeaveReport">
		<button class="btn btn-lg btn-primary btn-sm">Xuất báo cáo</button>
	</a>
	<a href="${url}/timekeeping/leaveInfo">
		<button class="btn btn-lg btn-primary btn-sm">Chấm công phát
			sinh</button>
	</a>
	<a href="${url}/timekeeping/listWorkingDay">
		<button class="btn btn-lg btn-primary btn-sm">Định nghĩa số
			ngày công chuẩn</button>
	</a>
	<br />
	<br />
	<form:form action="listByDate" modelAttribute="leaveInfoForm"
		method="POST">
		<table class="table">
			<tr>
				<td>Chọn xem từ ngày:(*) &nbsp;
					<div class="input-group date datetime">
						<form:input path="date" class="form-control"
							placeholder="dd/mm/yyyy" autocomplete="off" />
						<span class="input-group-addon"><span
							class="glyphicon glyphicon-calendar"></span></span>
					</div> <%-- <form:input path="date"
						type="date" required="required" class="form-control animated" /> --%>
				</td>
				<td>Đến ngày:(*) &nbsp;
					<div class="input-group date datetime">
						<form:input path="toDate" class="form-control"
							placeholder="dd/mm/yyyy" autocomplete="off" />
						<span class="input-group-addon"><span
							class="glyphicon glyphicon-calendar"></span></span>
					</div>		
				<%-- <form:input path="toDate" type="date"
						required="required" class="form-control animated" /> --%>
				</td>
				<td>Phòng: &nbsp; <form:select path="dept"
						class="form-control animated">
						<form:option value="all" label="Tất cả phòng ban"></form:option>
						<form:options items="${departmentMap}" var="dept" />
					</form:select>
				</td>
				<td>Nhân viên: &nbsp; <form:select path="eId"
						class="form-control animated">
						<form:option value="0" label="Tất cả nhân viên"></form:option>
						<form:options items="${employeeMap}" var="eId" />
					</form:select>
				</td>
				<td>&nbsp;&nbsp;<input class="btn btn-lg btn-primary btn-sm"
					type="submit" value="Xem danh sách" /></td>
			</tr>
		</table>
		<c:if test="${not empty message}">
			<div class="alert alert-success">${message}</div>
		</c:if>
	</form:form>

	<c:if test="${empty message}">
		<a
			href="${url}/timekeeping/exportToExcel?date=${leaveInfoForm.date}&toDate=${leaveInfoForm.toDate}&dept=${leaveInfoForm.dept}&eId=${leaveInfoForm.eId}">
			<button class="btn btn-lg btn-primary btn-sm">Export dữ liệu ra file excel</button>
		</a>
		<br />
		<br />
	</c:if>
	<%-- 
	<div class="loader">
		<img src="${url}/public/images/loading1.gif" align="middle" vspace="5"
			alt="Import dữ liệu" />
	</div> --%>
	<%-- 	<div id="waitingBox" class="msgBox"
		onmousedown="dragStart(event, 'waitingBox')" style="cursor: move">
		<div class="shadow"></div>
		<div class="pane"></div>
		<div class="titlebar">
			<img border="0" src="${url}/public/images/close.gif" width="10"
				height="10" onclick="closeWaitingBox()" title="Close" alt="Close"
				style="cursor: pointer" />
		</div>
		<div class="content" style="cursor: move">
			<div style="cursor: move">Đang cập nhật ...</div>
			<div style="width: 196px; align: center;">
				<img src="${url}/public/images/wait1.gif" align="middle" vspace="5"
					alt="Wait" />
			</div>
		</div>
	</div> --%>
	<form:form action="updateData" modelAttribute="timekeepingForm"
		method="POST" enctype="multipart/form-data">
		<table class="table">
			<tr>
				<td><b><i>Cập nhật dữ liệu chấm công từ file excel:</i></b></td>
				<td align="left"><input class="btn btn-lg btn-primary btn-sm"
					name="timeKeepingFile" type="file" /></td>
				<td align="right">
					<button class="btn btn-lg btn-primary btn-sm">Cập nhật</button>
					&nbsp; &nbsp;
				</td>

			</tr>
		</table>
		<c:if test="${not empty comment}">
			<div class="alert alert-info">${comment}</div>
		</c:if>
		<br />
		<div class="table-responsive">
			<b style="color: blue;">Chấm công phát sinh</b>
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
				</tr>
				<c:forEach var="leaveInfo" items="${leaveInfos}">
					<tr style="font-size: 10">
						<td>${leaveInfo.employeeId}</td>
						<td>${leaveInfo.employeeName}</td>
						<td>${leaveInfo.department}</td>
						<td>${leaveInfo.title}</td>
						<td><fmt:formatDate pattern="dd/MM/yyyy" value="${leaveInfo.date}" /></td>
						<c:if test="${leaveInfo.timeValue == 4}">
							<td>${leaveInfo.leaveName}nửangày</td>
						</c:if>
						<c:if test="${leaveInfo.timeValue != '4'}">
							<td>${leaveInfo.leaveName}</td>
						</c:if>
						<td>${leaveInfo.timeValue}</td>
						<td>${leaveInfo.comment}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<b style="color: blue;">Dữ liệu từ máy chấm công</b>
		<table class="table table-bordered">
			<tr>
				<th>Mã NV</th>
				<th>Họ tên</th>
				<th>Bộ phận</th>
				<th>Chức vụ</th>
				<th>Ngày</th>
				<th>Giờ vào</th>
				<th>Giờ ra</th>
				<th>Tổng tgian</th>
				<th>ĐM sáng</th>
				<th>ĐM chiều</th>
				<th>VS sáng</th>
				<th>VS chiều</th>
				<th>Ghi chú</th>
			</tr>
			<c:forEach var="timekeeping" items="${timekeepings}">
				<tr style="font-size: 10">
					<td>${timekeeping.employeeId}</td>
					<td nowrap="nowrap">${timekeeping.employeeName}</td>
					<td>${timekeeping.department}</td>
					<td>${timekeeping.title}</td>
					<td nowrap="nowrap">
						<fmt:formatDate pattern="dd/MM/yyyy" value="${timekeeping.date}" />
					</td>
					<c:if test="${not empty timekeeping.timeIn}">
						<td>${timekeeping.timeIn}</td>
					</c:if>
					<c:if test="${empty timekeeping.timeIn}">
						<td bgcolor="EDC7D0">${timekeeping.timeIn}</td>
					</c:if>
					<c:if test="${not empty timekeeping.timeOut}">
						<td>${timekeeping.timeOut}</td>
					</c:if>
					<c:if test="${empty timekeeping.timeOut}">
						<td bgcolor="EDC7D0">${timekeeping.timeOut}</td>
					</c:if>
					<td>${timekeeping.workedTime}</td>
					<c:if test="${not empty timekeeping.comeLateM}">
						<td bgcolor="#F5F6CE">${timekeeping.comeLateM}'</td>
						<%-- 						<c:if test="${timekeeping.comeLateM >= 30}">
							<td bgcolor="#F5F6CE"><fmt:formatNumber value="${timekeeping.comeLateM/60 -1}" pattern="0" />h<fmt:formatNumber value="${timekeeping.comeLateM%60}" pattern="00"/></td>
						</c:if>
						<c:if test="${timekeeping.comeLateM < 30}">
							<td bgcolor="#F5F6CE"><fmt:formatNumber value="${timekeeping.comeLateM/60}" pattern="0" />h<fmt:formatNumber value="${timekeeping.comeLateM%60}" pattern="00"/></td>
						</c:if> --%>
					</c:if>
					<c:if test="${empty timekeeping.comeLateM}">
						<td>${timekeeping.comeLateM}</td>
					</c:if>
					<c:if test="${not empty timekeeping.comeLateA}">
						<%-- 						<c:if test="${timekeeping.comeLateA >= 60}">
							<td bgcolor="#F5F6CE"><fmt:formatNumber value="${timekeeping.comeLateA/60 -1}" pattern="0" />h<fmt:formatNumber value="${timekeeping.comeLateA%60}" pattern="00"/></td>
						</c:if>
						<c:if test="${timekeeping.comeLateA < 60}">
							<td bgcolor="#F5F6CE"><fmt:formatNumber value="${timekeeping.comeLateA/60}" pattern="0" />h<fmt:formatNumber value="${timekeeping.comeLateA%60}" pattern="00"/></td>
						</c:if> --%>
						<td bgcolor="#F5F6CE">${timekeeping.comeLateA}'</td>
					</c:if>
					<c:if test="${empty timekeeping.comeLateA}">
						<td>${timekeeping.comeLateA}</td>
					</c:if>
					<c:if test="${not empty timekeeping.leaveSoonM}">
						<%-- 						<c:if test="${timekeeping.leaveSoonM >= 60}">
							<td bgcolor="#F5F6CE"><fmt:formatNumber value="${timekeeping.leaveSoonM/60 -1}" pattern="0" />h<fmt:formatNumber value="${timekeeping.leaveSoonM%60}" pattern="00"/></td>
						</c:if>
						<c:if test="${timekeeping.leaveSoonM < 60}">
							<td bgcolor="#F5F6CE"><fmt:formatNumber value="${timekeeping.leaveSoonM/60}" pattern="0" />h<fmt:formatNumber value="${timekeeping.leaveSoonM%60}" pattern="00"/></td>
						</c:if> --%>
						<td bgcolor="#F5F6CE">${timekeeping.leaveSoonM}'</td>
					</c:if>
					<c:if test="${empty timekeeping.leaveSoonM}">
						<td>${timekeeping.leaveSoonM}</td>
					</c:if>
					<c:if test="${not empty timekeeping.leaveSoonA}">
						<%-- 						<c:if test="${timekeeping.leaveSoonA >= 60}">
							<td bgcolor="#F5F6CE"><fmt:formatNumber value="${timekeeping.leaveSoonA/60 -1}" pattern="0" />h<fmt:formatNumber value="${timekeeping.leaveSoonA%60}" pattern="00"/></td>
						</c:if>
						<c:if test="${timekeeping.leaveSoonA < 60}">
							<td bgcolor="#F5F6CE"><fmt:formatNumber value="${timekeeping.leaveSoonA/60}" pattern="0" />h<fmt:formatNumber value="${timekeeping.leaveSoonA%60}" pattern="00"/></td>
						</c:if> --%>
						<td bgcolor="#F5F6CE">${timekeeping.leaveSoonA}'</td>
					</c:if>
					<c:if test="${empty timekeeping.leaveSoonA}">
						<td>${timekeeping.leaveSoonA}</td>
					</c:if>
					<c:if test="${not empty timekeeping.comment}">
						<td title="Nhấn vào để thêm ghi chú">
						<!-- <a href="#"> -->
								${timekeeping.comment}</td>
					</c:if>
 					<c:if test="${empty timekeeping.comment}">
						<td nowrap="nowrap" title="Nhấn vào để thêm ghi chú">
						<!-- <a href="#">  Thêm ghi chú-->
						</td>
					</c:if> 

				</tr>
			</c:forEach>
		</table>
	</form:form>
</body>
</html>