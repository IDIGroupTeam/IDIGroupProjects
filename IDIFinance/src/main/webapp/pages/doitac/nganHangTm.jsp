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
		$("#mainFinanceForm").attr("action", "${url}/nganhang/luu");
		$("#mainFinanceForm").attr("method", "POST");

		$("#luuNut").click(function() {
			$("#mainFinanceForm").submit();
		});
	});
</script>

<h4>TẠO MỚI NGÂN HÀNG</h4>
<hr />

<div class="row">
	<div class="col-sm-6">
		<div class="row form-group">
			<label class="control-label col-sm-4" for="tenVt">Tên viết
				tắt (*)</label>
			<div class="col-sm-8">
				<form:input path="tenVt" cssClass="form-control"
					placeholder="Tên viết tắt" />
				<form:errors path="tenVt" cssClass="error" />
			</div>
		</div>
		<div class="row form-group">
			<label class="control-label col-sm-4" for="tenDd">Tên đầy đủ
				(*)</label>
			<div class="col-sm-8">
				<form:input path="tenDd" cssClass="form-control"
					placeholder="Tên đầy đủ" />
				<form:errors path="tenDd" cssClass="error" />
			</div>
		</div>
	</div>

	<div class="form-group">
		<label class="control-label col-sm-2" for="diaChi">Địa chỉ HSC</label>
		<div class="col-sm-4">
			<form:textarea path="diaChi" rows="2" cols="2"
				cssClass="form-control" placeholder="Địa chỉ" />
		</div>
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="tenTa">Tên tiếng anh</label>
	<div class="col-sm-4">
		<form:input path="tenTa" cssClass="form-control"
			placeholder="Tên tiếng anh" />
	</div>

	<label class="control-label col-sm-2" for="moTa">Chú thích</label>
	<div class="col-sm-4">
		<form:input path="moTa" cssClass="form-control"
			placeholder="Chú thích" />
	</div>
</div>
<hr />

<div class="row">
	<div class="col-sm-12">
		<a href="${url}/nganhang/danhsach" class="btn btn-info btn-sm">Hủy</a>
		<button id="luuNut" type="submit" class="btn btn-info btn-sm">Lưu</button>
	</div>
</div>

