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
		$("#mainFinanceForm").attr("action",
				"${url}/bctc/luuchuyentt/chitieu/them");
		$("#mainFinanceForm").attr("method", "POST");

		function init() {
			$("#submitBt").click(function() {
				$("#mainFinanceForm").submit();
			});

			$("#assetCode").combobox();
			$("#taiKhoanDs0\\.maTk").combobox();
			$("#taiKhoanDs0\\.doiUng\\.maTk").combobox();
		}
		init();
	});
</script>

<h4>TẠO MỚI MỐI QUAN HỆ CHỈ TIÊU LƯU CHUYỂN TIỀN TỆ - TÀI KHOẢN</h4>
<hr />

<form:hidden path="type"/>
<div class="row form-group">
	<label class="control-label col-sm-3" for="assetCode">Mã chỉ
		tiêu LCTT (*)</label>
	<div class="col-sm-4">
		<form:select cssClass="form-control" path="assetCode">
			<form:option value="" label=""></form:option>
			<form:options items="${baiDs}" itemLabel="assetCodeName"
				itemValue="assetCode" />
		</form:select>
		<br />
		<form:errors path="assetCode" cssClass="error" />
	</div>
	<i class="col-sm-5">Mã chỉ tiêu LCTT</i>
</div>

<div class="row form-group">
	<label class="control-label col-sm-3" for="taiKhoanDs[0].maTk">Mã
		tài khoản kế toán (*)</label>
	<div class="col-sm-4">
		<form:select cssClass="form-control" path="taiKhoanDs[0].maTk">
			<form:option value="" label=""></form:option>
			<form:options items="${loaiTaiKhoanDs}" itemLabel="maTenTk"
				itemValue="maTk" />
		</form:select>
		<br />
		<form:errors path="taiKhoanDs[0].maTk" cssClass="error" />
	</div>
	<i class="col-sm-5">Mã tài khoản kế toán thực dùng</i>
</div>

<div class="row form-group">
	<label class="control-label col-sm-3" for="taiKhoanDs[0].soDuGiaTri">Số
		dư</label>
	<div class="col-sm-4">
		<form:select path="taiKhoanDs[0].soDuGiaTri" cssClass="form-control"
			placeholder="Số dư">
			<form:option value="${LoaiTaiKhoan.NO}" label="Nợ" />
			<form:option value="${LoaiTaiKhoan.CO}" label="Có" />
		</form:select>
	</div>
	<i class="col-sm-5">Chọn nợ hoặc có</i>
</div>

<div class="row form-group">
	<label class="control-label col-sm-3" for="taiKhoanDs[0].doiUng.maTk">Mã
		tài khoản kế toán đối ứng (*)</label>
	<div class="col-sm-4">
		<form:select cssClass="form-control" path="taiKhoanDs[0].doiUng.maTk">
			<form:option value="" label=""></form:option>
			<form:options items="${loaiTaiKhoanDs}" itemLabel="maTenTk"
				itemValue="maTk" />
		</form:select>
		<br />
		<form:errors path="taiKhoanDs[0].doiUng.maTk" cssClass="error" />
	</div>
	<i class="col-sm-5">Mã tài khoản kế toán đối ứng</i>
</div>

<div class="row form-group">
	<div class="col-sm-12">
		<a href="${url}/bctc/luuchuyentt/chitieu/danhsach"
			class="btn btn-info btn-sm">Hủy</a>
		<button id="submitBt" type="submit" class="btn btn-info btn-sm">Tạo
			mới</button>
	</div>
</div>