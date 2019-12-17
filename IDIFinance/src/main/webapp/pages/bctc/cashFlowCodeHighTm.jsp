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
				"${url}/bctc/luuchuyentt/chitieu/capcao/them");
		$("#mainFinanceForm").attr("method", "POST");

		function init() {
			$("#submitBt").click(function() {
				$("#mainFinanceForm").submit();
			});

			$("#parent\\.assetCode").combobox();
		}
		init();
	});
</script>

<h4>TẠO MỚI MỐI QUAN HỆ CHỈ TIÊU LƯU CHUYỂN TIỀN TỆ - TÀI KHOẢN</h4>
<hr />

<form:hidden path="type" />
<div class="row form-group">
	<label class="control-label col-sm-3" for="parent.assetCode">Mã
		chỉ tiêu LCTT cấp trên</label>
	<div class="col-sm-4">
		<form:select cssClass="form-control" path="parent.assetCode">
			<form:option value="" label=""></form:option>
			<form:options items="${baiDs}" itemLabel="assetCodeName"
				itemValue="assetCode" />
		</form:select>
		<br />
		<form:errors path="parent.assetCode" cssClass="error" />
	</div>
	<i class="col-sm-5">Mã chỉ tiêu LCTT cấp trên</i>
</div>

<div class="row form-group">
	<label class="control-label col-sm-3" for="assetCode">Mã chỉ
		tiêu CDKT (*)</label>
	<div class="col-sm-4">
		<form:input cssClass="form-control" path="assetCode" />
		<br />
		<form:errors path="assetCode" cssClass="error" />
	</div>
	<i class="col-sm-5">Mã chỉ tiêu LCTT</i>
</div>

<div class="row form-group">
	<label class="control-label col-sm-3" for="assetName">Tên chỉ
		tiêu CDKT (*)</label>
	<div class="col-sm-4">
		<form:input cssClass="form-control" path="assetName" />
		<br />
		<form:errors path="assetName" cssClass="error" />
	</div>
	<i class="col-sm-5">Tên chỉ tiêu LCTT</i>
</div>

<%-- <div class="row form-group">
	<label class="control-label col-sm-3" for="soDu">Số dư</label>
	<div class="col-sm-4">
		<form:select path="soDu" cssClass="form-control" placeholder="Số dư">
			<form:option value="${LoaiTaiKhoan.NO}" label="Nợ" />
			<form:option value="${LoaiTaiKhoan.CO}" label="Có" />
		</form:select>
	</div>
	<i class="col-sm-5">Chọn nợ hoặc có</i>
</div> --%>

<div class="row form-group">
	<label class="control-label col-sm-3" for="rule">Công thức</label>
	<div class="col-sm-4">
		<form:input cssClass="form-control" path="rule" />
	</div>
	<i class="col-sm-5">Công thức tính chỉ tiêu từ các chỉ tiêu con</i>
</div>

<div class="row form-group">
	<div class="col-sm-12">
		<a href="${url}/bctc/luuchuyentt/chitieu/danhsach"
			class="btn btn-info btn-sm">Hủy</a>
		<button id="submitBt" type="submit" class="btn btn-info btn-sm">Tạo
			mới</button>
	</div>
</div>