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
		$("#mainFinanceForm").attr("action", "${url}/congviec/nghiepvu/luu");
		$("#mainFinanceForm").attr("method", "POST");

		$("#submitBt").click(function() {
			$("#mainFinanceForm").submit();
		});
	});
</script>

<h4>NGHIỆP VỤ</h4>
<hr />

<div class="row form-group">
	<label class="control-label col-sm-2" for="tenNv">Tên nghiệp
		vụ:(*)</label>
	<div class="col-sm-4">
		<form:input path="tenNv" placeholder="Tên nghiệp vụ"
			cssClass="form-control" />
		<form:errors path="tenNv" cssClass="error"></form:errors>
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="moTa">Mô tả:</label>
	<div class="col-sm-4">
		<form:textarea path="moTa" cssClass="form-control" placeholder="Mô tả" />
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="linhVuc.maLv">Mô tả:</label>
	<div class="col-sm-4">
		<form:select path="linhVuc.maLv" cssClass="form-control">
			<form:options items="${linhVucDs}" itemValue="maLv" itemLabel="tenLv" />
		</form:select>
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="doKho">Độ khó:(*)</label>
	<div class="col-sm-4">
		<form:input path="doKho" cssClass="form-control" placeholder="Độ khó" />
		<form:errors path="doKho" cssClass="error"></form:errors>
	</div>
</div>

<div class="row form-group">
	<div class="col-sm-2">
		<a href="${url}/congviec/nghiepvu/danhsach"
			class="btn btn-info btn-sm">Hủy</a>
		<button id="submitBt" type="submit" class="btn btn-info btn-sm">Tạo
			mới</button>
	</div>
</div>
