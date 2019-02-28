<%@page import="com.idi.finance.bean.taikhoan.LoaiTaiKhoan"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<script type="text/javascript">
	//Shorthand for $( document ).ready()
	$(function() {
		// Khởi tạo action/method cho mainFinanceForm form
		$("#mainFinanceForm").attr("action", "${url}/kyketoan/luu");
		$("#mainFinanceForm").attr("method", "POST");

		$("#submitBt").click(function() {
			$("#mainFinanceForm").submit();
		});

		$(".datetime").datetimepicker({
			language : 'vi',
			todayBtn : 1,
			autoclose : 1,
			todayHighlight : 1,
			startView : 2,
			minView : 2,
			forceParse : 0,
			pickerPosition : "bottom-left"
		});
	});
</script>

<h4>Tạo mới kỳ kế toán</h4>
<hr />

<div class="row form-group">
	<label class="control-label col-sm-2" for="tenKyKt">Tên kỳ kế
		toán (*)</label>
	<div class="col-sm-2">
		<form:input path="tenKyKt" class="form-control" />
		<form:errors path="tenKyKt" cssClass="error"></form:errors>
	</div>

	<label class="control-label col-sm-2" for="batDau">Bắt đầu (*)</label>
	<div class="col-sm-2">
		<div class="input-group date datetime smallform">
			<form:input path="batDau" class="form-control" />
			<span class="input-group-addon"><span
				class="glyphicon glyphicon-calendar"></span></span>
		</div>
		<form:errors path="batDau" cssClass="error"></form:errors>
	</div>

	<label class="control-label col-sm-2" for="ketThuc">Kết thúc (*)</label>
	<div class="col-sm-2">
		<div class="input-group date datetime smallform">
			<form:input path="ketThuc" class="form-control" />
			<span class="input-group-addon"><span
				class="glyphicon glyphicon-calendar"></span></span>
		</div>
		<form:errors path="ketThuc" cssClass="error"></form:errors>
	</div>
</div>

<div class="row form-group">
	<div class="col-sm-12">
		<label class="control-label">Lưu ý:</label>
		<ul>
			<li>Các thông tin trên khi được tạo ra sẽ không thể thay đổi,
				vậy hãy điền thông tin cần thận trước khi ấn nút lưu</li>
			<li>Khoảng thời gian của kỳ kế toán sắp tạo không được trùng với
				khoảng thời gian của các kỳ kế toán trước đó trong hệ thống</li>
		</ul>
	</div>
</div>

<div class="row form-group">
	<div class="col-sm-2">
		<a href="${url}/kyketoan/danhsach" class="btn btn-info btn-sm">Hủy</a>
		<button id="submitBt" type="submit" class="btn btn-info btn-sm">Tạo
			mới</button>
	</div>
</div>
