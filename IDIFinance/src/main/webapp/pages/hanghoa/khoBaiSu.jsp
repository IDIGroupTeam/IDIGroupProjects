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
		$("#mainFinanceForm").attr("action", "${url}/hanghoa/kho/luu");
		$("#mainFinanceForm").attr("method", "POST");

		$("#submitBt").click(function() {
			$("#mainFinanceForm").submit();
		});
	});
</script>

<h4>Sửa kho</h4>
<br />

<form:hidden path="maKho" />
<div class="row form-group">
	<label class="control-label col-sm-2" for="kyHieuKho">Mã kho:(*)</label>
	<div class="col-sm-4">
		<form:input path="kyHieuKho" placeholder="Mã kho"
			cssClass="form-control" />
		<form:errors path="kyHieuKho" cssClass="error"></form:errors>
	</div>

	<label class="control-label col-sm-2" for="tenKho">Tên kho:(*)</label>
	<div class="col-sm-4">
		<form:input path="tenKho" placeholder="Tên kho"
			cssClass="form-control" />
		<form:errors path="tenKho" cssClass="error"></form:errors>
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="diaChi">Địa chỉ:(*)</label>
	<div class="col-sm-4">
		<form:textarea path="diaChi" placeholder="Địa chỉ"
			cssClass="form-control" />
		<form:errors path="diaChi" cssClass="error"></form:errors>
	</div>

	<label class="control-label col-sm-2" for="moTa">Mô tả:</label>
	<div class="col-sm-4">
		<form:textarea path="moTa" placeholder="Mô tả" cssClass="form-control" />
		<form:errors path="moTa" cssClass="error"></form:errors>
	</div>
</div>

<div class="row form-group">
	<div class="col-sm-2">
		<a href="${url}/hanghoa/kho/danhsach" class="btn btn-info btn-sm">Hủy</a>
		<button id="submitBt" type="submit" class="btn btn-info btn-sm">Lưu
			thay đổi</button>
	</div>
</div>
