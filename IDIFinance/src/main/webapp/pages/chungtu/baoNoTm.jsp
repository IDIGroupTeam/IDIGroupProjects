<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<script type="text/javascript">
	//Shorthand for $( document ).ready()
	$(function() {
		// Khởi tạo action/method cho mainFinanceForm form

	});
</script>

<h4>BÁO NỢ</h4>

<hr />

<div class="row form-group">
	<label class="control-label col-sm-2" for="tenDt">Đối tượng
		nộp:</label>
	<div class="col-sm-4">
		<input type="text" class="form-control" id="tenDt"
			placeholder="Họ và tên">
	</div>

	<label class="control-label col-sm-2" for="maThue">Mã số thuế:</label>
	<div class="col-sm-4">
		<input type="text" class="form-control" id="maThue"
			placeholder="Mã số thuế">
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="diaChi">Địa chỉ:</label>
	<div class="col-sm-4">
		<textarea rows="2" cols="2" class="form-control" id="diaChi"
			placeholder="Địa chỉ"></textarea>
	</div>
	<label class="control-label col-sm-2" for="daiDien">Người nộp:
	</label>
	<div class="col-sm-4">
		<input type="text" class="form-control" id="daiDien"
			placeholder="Người nộp">
	</div>
</div>
<hr />
<div class="row form-group">
	<label class="control-label col-sm-2" for="soTien">Số tiền:</label>
	<div class="col-sm-4">
		<input type="text" class="form-control" id="soTien" placeholder="0.0" />
	</div>

	<label class="control-label col-sm-2" for="lyDo">Lý do:</label>
	<div class="col-sm-4">
		<textarea rows="2" cols="2" class="form-control" id="lyDo"
			placeholder="Lý do"></textarea>
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="maNt">Ngoại tệ</label>
	<div class="col-sm-4">
		<input type="text" class="form-control" id="maNt" placeholder="0.0" />
	</div>

	<label class="control-label col-sm-2" for="tyGia">Tỷ giá</label>
	<div class="col-sm-4">
		<input type="text" class="form-control" id="tyGia" placeholder="1.0" />
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="giaTri">Thành tiền:</label>
	<div class="col-sm-4">
		<input type="text" class="form-control" id="giaTri" placeholder="0.0" />
	</div>
</div>
<hr />

<div class="row form-group">
	<label class="control-label col-sm-2">Định khoản</label>
	<table class="table table-bordered table-hover">
		<thead>
			<tr>
				<th>Tài khoản</th>
				<th>Nợ/Có</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
			</tr>
		</tbody>
	</table>
</div>

<div class="row form-group">
	<div class="col-sm-2">
		<a href="${url}/danhsachbaono" class="btn btn-info btn-sm">Hủy</a>
		<button type="submit" class="btn btn-info btn-sm">Tạo mới</button>
	</div>
</div>
