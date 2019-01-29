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
		$("#mainFinanceForm").attr("action", "${url}/taikhoan/luutaomoitk");
		$("#mainFinanceForm").attr("method", "POST");

		$("#submitBt").click(function() {
			$("#mainFinanceForm").submit();
		});
		
		$("#maTkCha").combobox();
	});
</script>

<h4>TÀI KHOẢN</h4>
<hr />

<form:hidden path="new" />
<div class="row form-group">
	<label class="control-label col-sm-2" for="maTk">Mã tài khoản
		(*)</label>
	<div class="col-sm-4">
		<form:input path="maTk" class="form-control" />
		<form:errors path="maTk" cssClass="error"></form:errors>
	</div>
	<i class="col-sm-6">Mã tài khoản cấp dưới bao gồm mã tài khoản cấp
		trên sau đó là các ký tự 1, 2 ..., a, b ... Ví dụ, mã TK cấp trên 111,
		mã Tk cấp dưới 1111</i>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="maTkCha">Tài khoản
		cấp trên</label>
	<div class="col-sm-4">
		<form:select path="maTkCha" cssClass="form-control"
			placeholder="Loại tài khoản cha">
			<form:option value="" label=""></form:option>
			<form:options items="${taiKhoanDs}" itemLabel="maTenTk"
				itemValue="maTk" />
		</form:select>
		<form:errors path="maTkCha" cssClass="error"></form:errors>
	</div>
	<i class="col-sm-6">Chọn tài khoản cấp trên từ các các tài khoản
		trước đó</i>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="tenTk">Tên tài khoản
		(*)</label>
	<div class="col-sm-4">
		<form:input path="tenTk" class="form-control" />
		<form:errors path="tenTk" cssClass="error"></form:errors>
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="soDu">Số dư</label>
	<div class="col-sm-4">
		<form:select path="soDu" cssClass="form-control" placeholder="Số dư">
			<form:option value="${LoaiTaiKhoan.NO}" label="Nợ" />
			<form:option value="${LoaiTaiKhoan.CO}" label="Có" />
		</form:select>
		<form:errors path="soDu" cssClass="error"></form:errors>
	</div>
	<i class="col-sm-6">Chọn nợ hoặc có khi tài khoản tăng, số dư của
		tài khoản cấp dưới phải giống tài khoản cấp trên </i>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="soDu">Lưỡng tính</label>
	<div class="col-sm-4">
		<form:select path="luongTinh" cssClass="form-control"
			placeholder="Lưỡng tính">
			<form:option value="0" label="Không" />
			<form:option value="1" label="Có" />
		</form:select>
		<form:errors path="luongTinh" cssClass="error"></form:errors>
	</div>
	<i class="col-sm-6">Tài khoản lưỡng tính hay không, tính lưỡng tính
		của tài khoản cấp dưới phải giống tài khoản cấp trên</i>
</div>

<div class="row form-group">
	<div class="col-sm-2">
		<a href="${url}/taikhoan/danhsach" class="btn btn-info btn-sm">Hủy</a>
		<button id="submitBt" type="submit" class="btn btn-info btn-sm">Tạo
			mới</button>
	</div>
</div>