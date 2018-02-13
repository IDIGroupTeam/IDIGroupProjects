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
		$("#mainFinanceForm").attr("action", "${url}/hanghoa/nhom/luu");
		$("#mainFinanceForm").attr("method", "POST");

		$("#submitBt").click(function() {
			$("#mainFinanceForm").submit();
		});
	});
</script>

<h4>Thêm mới nhóm hàng hóa</h4>
<br />

<div class="row form-group">
	<label class="control-label col-sm-2" for="tenNhomHh">Tên nhóm
		hàng hóa:(*)</label>
	<div class="col-sm-4">
		<form:input path="tenNhomHh" placeholder="Tên nhóm hàng hóa"
			cssClass="form-control" />
		<form:errors path="tenNhomHh" cssClass="error"></form:errors>
	</div>

	<label class="control-label col-sm-2" for="nhomHh.maNhomHh">Nhóm
		cha:</label>
	<div class="col-sm-4">
		<form:select path="nhomHh.maNhomHh" cssClass="form-control">
			<form:option value="0" label=""></form:option>
			<form:options items="${nhomHangHoaDs}" itemValue="maNhomHh"
				itemLabel="tenNhomHh"></form:options>
		</form:select>
	</div>
</div>

<div class="row form-group">
	<div class="col-sm-2">
		<a href="${url}/hanghoa/nhom/danhsach" class="btn btn-info btn-sm">Hủy</a>
		<button id="submitBt" type="submit" class="btn btn-info btn-sm">Tạo
			mới</button>
	</div>
</div>
