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
		$("#mainFinanceForm").attr("action", "${url}/hanghoa/donvi/luu");
		$("#mainFinanceForm").attr("method", "POST");

		$("#submitBt").click(function() {
			$("#mainFinanceForm").submit();
		});
	});
</script>

<h4>Thêm mới đơn vị hàng hóa</h4>
<hr />

<div class="row form-group">
	<label class="control-label col-sm-2" for="tenDv">Tên đơn
		vị:(*)</label>
	<div class="col-sm-4">
		<form:input path="tenDv" placeholder="Tên đơn vị"
			cssClass="form-control" />
		<form:errors path="tenDv" cssClass="error"></form:errors>
	</div>

	<label class="control-label col-sm-2" for="moTa">Mô tả:</label>
	<div class="col-sm-4">
		<form:textarea path="moTa" cssClass="form-control" placeholder="Mô tả" />
	</div>
</div>

<div class="row form-group">
	<div class="col-sm-2">
		<a href="${url}/hanghoa/donvi/danhsach" class="btn btn-info btn-sm">Hủy</a>
		<button id="submitBt" type="submit" class="btn btn-info btn-sm">Tạo
			mới</button>
	</div>
</div>
