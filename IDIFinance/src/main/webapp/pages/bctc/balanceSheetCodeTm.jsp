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
		$("#mainFinanceForm").attr("action", "${url}/bctc/cdkt/chitieu/them");
		$("#mainFinanceForm").attr("method", "POST");

		$("#submitBt").click(function() {
			$("#mainFinanceForm").submit();
		});

		$("#assetCode").combobox();
		$("#assetCode").change(
				function() {
					assetCode = $(this).val();
					param = "assetCode=" + assetCode;
					$.ajax({
						url : "${url}/bctc/cdkt/chitieu/danhsach/capduoi",
						data : param,
						dataType : "json",
						type : "POST",
						success : function(baiDs) {
							if (baiDs == null) {
								return;
							}

							html = "<option value=''></option>";
							for (var i = 0; i < baiDs.length; i++) {
								bai = baiDs[i];
								htmlTmpl = "<option value='"+bai.assetCode+"'>"
										+ bai.assetCodeName + "</option>";
								html += htmlTmpl;
							}

							$("#childs0\\.assetCode").html(html);
						},
						error : function(data) {
							html = "<option value=''></option>";
							$("#childs0]\\.assetCode").html(html);
						}
					});
				});

		$("#childs0\\.taiKhoanDs0\\.maTkGoc").combobox();
		$("#childs0\\.taiKhoanDs0\\.maTkGoc").change(
				function() {
					maTk = $(this).val();
					if (maTk == "") {
						html = "<option value=''></option>";
						$("#childs0\\.taiKhoanDs0\\.maTk").html(html);
						return;
					}
					param = "maTk=" + maTk;
					$.ajax({
						url : "${url}/taikhoan/danhsach/capduoi",
						data : param,
						dataType : "json",
						type : "POST",
						success : function(loaiTaiKhoanDs) {
							if (loaiTaiKhoanDs == null) {
								return;
							}

							html = "<option value=''></option>";
							for (var i = 0; i < loaiTaiKhoanDs.length; i++) {
								taiKhoan = loaiTaiKhoanDs[i];
								htmlTmpl = "<option value='"+taiKhoan.maTk+"'>"
										+ taiKhoan.maTenTk + "</option>";
								html += htmlTmpl;
							}

							$("#childs0\\.taiKhoanDs0\\.maTk").html(html);
						},
						error : function(data) {
							html = "<option value=''></option>";
							$("#childs0\\.taiKhoanDs0\\.maTk").html(html);
						}
					});
				});
	});
</script>

<h4>TẠO MỚI MỐI QUAN HỆ CHỈ TIÊU CÂN ĐỐI KẾ TOÁN - TÀI KHOẢN</h4>
<hr />

<div class="row form-group">
	<label class="control-label col-sm-3" for="assetCode">Mã chỉ
		tiêu CDKT cấp 4 (*)</label>
	<div class="col-sm-4">
		<form:select cssClass="form-control" path="assetCode">
			<form:option value="" label=""></form:option>
			<form:options items="${baiDs}" itemLabel="assetCodeName"
				itemValue="assetCode" />
		</form:select>
		<br />
		<form:errors path="assetCode" cssClass="error" />
	</div>
	<i class="col-sm-5">Mã chỉ tiêu CĐKT cấp 4</i>
</div>

<div class="row form-group">
	<label class="control-label col-sm-3" for="childs[0].assetCode">Mã
		chỉ tiêu CDKT cấp 5</label>
	<div class="col-sm-4">
		<form:select cssClass="form-control" path="childs[0].assetCode">
			<form:option value="" label=""></form:option>
		</form:select>
	</div>
	<i class="col-sm-5">Mã chỉ tiêu CĐKT cấp 5</i>
</div>

<div class="row form-group">
	<label class="control-label col-sm-3"
		for="childs[0].taiKhoanDs[0].maTkGoc">Mã tài khoản kế toán gốc
		(*)</label>
	<div class="col-sm-4">
		<form:select cssClass="form-control"
			path="childs[0].taiKhoanDs[0].maTkGoc">
			<form:option value="" label=""></form:option>
			<form:options items="${loaiTaiKhoanDs}" itemLabel="maTenTk"
				itemValue="maTk" />
		</form:select>
		<br />
		<form:errors path="childs[0].taiKhoanDs[0].maTkGoc" cssClass="error" />
	</div>
	<i class="col-sm-5">Mã tài khoản kế toán gốc theo quy định của
		thông tư 200</i>
</div>

<div class="row form-group">
	<label class="control-label col-sm-3"
		for="childs[0].taiKhoanDs[0].maTk">Mã tài khoản kế toán (*)</label>
	<div class="col-sm-4">
		<form:select cssClass="form-control"
			path="childs[0].taiKhoanDs[0].maTk">
			<form:option value="" label=""></form:option>
		</form:select>
		<br />
		<form:errors path="childs[0].taiKhoanDs[0].maTk" cssClass="error" />
	</div>
	<i class="col-sm-5">Mã tài khoản kế toán thực dùng</i>
</div>

<div class="row form-group">
	<label class="control-label col-sm-3"
		for="childs[0].taiKhoanDs[0].soDuGiaTri">Số dư</label>
	<div class="col-sm-4">
		<form:select path="childs[0].taiKhoanDs[0].soDuGiaTri"
			cssClass="form-control" placeholder="Số dư">
			<form:option value="${LoaiTaiKhoan.NO}" label="Nợ" />
			<form:option value="${LoaiTaiKhoan.CO}" label="Có" />
		</form:select>
	</div>
	<i class="col-sm-5">Chọn nợ hoặc có</i>
</div>

<div class="row form-group">
	<div class="col-sm-12">
		<a href="${url}/bctc/cdkt/chitieu/danhsach"
			class="btn btn-info btn-sm">Hủy</a>
		<button id="submitBt" type="submit" class="btn btn-info btn-sm">Tạo
			mới</button>
	</div>
</div>