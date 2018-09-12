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
		$("#mainFinanceForm").attr("action", "${url}/khachhang/luu");
		$("#mainFinanceForm").attr("method", "POST");

		$("#luuNut").click(function() {
			$("#mainFinanceForm").submit();
		});
	});
</script>

<h4>TẠO MỚI KHÁCH HÀNG</h4>

<hr />

<div class="row form-group">
	<label class="control-label col-sm-2" for="tenKh">Tên khách
		hàng (*)</label>
	<div class="col-sm-4">
		<form:input path="tenKh" cssClass="form-control"
			placeholder="Tên khách hàng" />
		<br />
		<form:errors path="tenKh" cssClass="error" />
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="khKh">Mã khách hàng
		(*)</label>
	<div class="col-sm-4">
		<form:input path="khKh" cssClass="form-control"
			placeholder="Mã khách hàng" />
		<br />
		<form:errors path="khKh" cssClass="error" />
	</div>

	<label class="control-label col-sm-2" for="maThue">Mã số thuế</label>
	<div class="col-sm-4">
		<form:input path="maThue" cssClass="form-control"
			placeholder="Mã số thuế" />
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="diaChi">Địa chỉ</label>
	<div class="col-sm-4">
		<form:textarea path="diaChi" rows="2" cols="2" cssClass="form-control"
			placeholder="Địa chỉ" />
	</div>
	<label class="control-label col-sm-2" for="email">Email</label>
	<div class="col-sm-4">
		<form:input path="email" cssClass="form-control" placeholder="Email" />
		<br />
		<form:errors path="email" cssClass="error" />
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="sdt">Số điện thoại</label>
	<div class="col-sm-4">
		<form:input path="sdt" cssClass="form-control"
			placeholder="Số điện thoại" />
	</div>

	<label class="control-label col-sm-2" for="webSite">Website</label>
	<div class="col-sm-4">
		<form:input path="webSite" cssClass="form-control"
			placeholder="Website" />
	</div>
</div>

<hr />

<div class="row form-group">
	<div class="col-sm-12">
		<a href="${url}/khachhang/danhsach" class="btn btn-info btn-sm">Hủy</a>
		<button id="luuNut" type="submit" class="btn btn-info btn-sm">Lưu</button>
	</div>
</div>

