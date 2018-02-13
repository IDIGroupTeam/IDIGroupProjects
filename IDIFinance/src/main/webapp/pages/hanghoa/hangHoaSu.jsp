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
		$("#mainFinanceForm").attr("action", "${url}/hanghoa/luu");
		$("#mainFinanceForm").attr("method", "POST");

		$("#submitBt").click(function() {
			$("#mainFinanceForm").submit();
		});
	});
</script>

<h4>Sửa hàng hóa</h4>
<br />

<form:hidden path="maHh" />
<div class="row form-group">
	<label class="control-label col-sm-2" for="tenHh">Tên hàng
		hóa:(*)</label>
	<div class="col-sm-4">
		<form:input path="tenHh" placeholder="Tên hàng hóa"
			cssClass="form-control" />
		<form:errors path="tenHh" cssClass="error"></form:errors>
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="nhomHh.maNhomHh">Nhóm
		hàng hóa:(*)</label>
	<div class="col-sm-4">
		<form:select path="nhomHh.maNhomHh" cssClass="form-control">
			<form:option value="0" label=""></form:option>
			<form:options items="${nhomHangHoaDs}" itemValue="maNhomHh"
				itemLabel="tenNhomHh"></form:options>
		</form:select>
	</div>

	<label class="control-label col-sm-2" for="donVi.maDv">Đơn vị
		tính:</label>
	<div class="col-sm-4">
		<form:select path="donVi.maDv" cssClass="form-control">
			<form:option value="0" label=""></form:option>
			<form:options items="${donViDs}" itemValue="maDv" itemLabel="tenDv"></form:options>
		</form:select>
	</div>
</div>

<div class="row form-group">
	<div class="col-sm-2">
		<a href="${url}/hanghoa/danhsach" class="btn btn-info btn-sm">Hủy</a>
		<button id="submitBt" type="submit" class="btn btn-info btn-sm">Lưu
			thay đổi</button>
	</div>
</div>
