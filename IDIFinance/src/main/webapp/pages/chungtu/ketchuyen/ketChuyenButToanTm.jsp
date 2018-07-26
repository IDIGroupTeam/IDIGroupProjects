<%@page import="com.idi.finance.bean.taikhoan.LoaiTaiKhoan"%>
<%@page import="com.idi.finance.bean.chungtu.KetChuyenButToan"%>
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
		$("#mainFinanceForm").attr("action",
				"${url}/chungtu/ketchuyen/buttoan/luu");
		$("#mainFinanceForm").attr("method", "POST");

		$("#submitBt").click(function() {
			$("#mainFinanceForm").submit();
		});

		$("select[id^='taiKhoan']").combobox();
	});
</script>

<h4>Bút toán kết chuyển tự động</h4>
<hr />

<div class="row form-group">
	<label class="control-label col-sm-2" for="tenKc">Tên kết
		chuyển (*)</label>
	<div class="col-sm-4">
		<form:input path="tenKc" class="form-control" />
		<br />
		<form:errors path="tenKc" cssClass="error" />
	</div>

	<label class="control-label col-sm-2" for=congThuc>Công thức
		(*)</label>
	<div class="col-sm-4">
		<form:input path="congThuc" class="form-control" />
		<br />
		<form:errors path="congThuc" cssClass="error" />
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2"
		for="taiKhoanNo.loaiTaiKhoan.maTk">Tài khoản nợ (*)</label>
	<div class="col-sm-4">
		<form:select path="taiKhoanNo.loaiTaiKhoan.maTk" class="form-control">
			<form:options items="${loaiTaiKhoanDs}" itemValue="maTk"
				itemLabel="maTenTk" />
		</form:select>
		<br />
		<form:errors path="taiKhoanNo.loaiTaiKhoan.maTk" cssClass="error" />
	</div>

	<label class="control-label col-sm-2"
		for="taiKhoanNo.loaiTaiKhoan.maTk">Tài khoản có (*)</label>
	<div class="col-sm-4">
		<form:select path="taiKhoanCo.loaiTaiKhoan.maTk" class="form-control">
			<form:options items="${loaiTaiKhoanDs}" itemValue="maTk"
				itemLabel="maTenTk" />
		</form:select>
		<br />
		<form:errors path="taiKhoanCo.loaiTaiKhoan.maTk" cssClass="error" />
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="thuTu">Thứ tự (*)</label>
	<div class="col-sm-4">
		<form:input path="thuTu" class="form-control" />
		<br />
		<form:errors path="thuTu" cssClass="error" />
	</div>

	<label class="control-label col-sm-2" for="loaiKc">Phân loại
		(*)</label>
	<div class="col-sm-4">
		<form:select path="loaiKc" class="form-control">
			<form:option value="${KetChuyenButToan.KCBT_CUOI_KY}">Kết chuyển cuối kỳ</form:option>
			<%-- <form:option value="${KetChuyenButToan.KCBT_DAU_KY}">Kết chuyển đầu kỳ</form:option> --%>
		</form:select>
		<br />
		<form:errors path="loaiKc" cssClass="error" />
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="moTa">Mô tả</label>
	<div class="col-sm-4">
		<form:textarea path="moTa" class="form-control" />
	</div>
</div>

<div class="row form-group">
	<div class="col-sm-12">
		<a href="${url}/chungtu/ketchuyen/buttoan/danhsach"
			class="btn btn-info btn-sm">Hủy</a>
		<button id="submitBt" type="submit" class="btn btn-info btn-sm">Tạo
			mới</button>
	</div>
</div>