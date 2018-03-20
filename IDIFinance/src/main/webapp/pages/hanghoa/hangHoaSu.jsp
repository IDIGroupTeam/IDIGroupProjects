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
		$("#mainFinanceForm").attr("action", "${url}/hanghoa/luu");
		$("#mainFinanceForm").attr("method", "POST");

		$("#submitBt").click(function() {
			$("#mainFinanceForm").submit();
		});
		
		$('#tkKhoMd\\.maTk').combobox({
			bsVersion: '4'
		});
		$('#tkDoanhThuMd\\.maTk').combobox({
			bsVersion: '4'
		});
		$('#tkChiPhiMd\\.maTk').combobox({
			bsVersion: '4'
		});
	});
</script>

<h4>Sửa hàng hóa</h4>
<br />

<form:hidden path="maHh" />
<div class="row form-group">
	<label class="control-label col-sm-2" for="tinhChat">Tính chất</label>
	<div class="col-sm-4">
		<form:select path="tinhChat" cssClass="form-control">
			<form:option value="1" label="Vật tư hàng hóa"></form:option>
			<form:option value="2" label="Dịch vụ"></form:option>
			<form:option value="3" label="Thành phẩm"></form:option>
		</form:select>
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="tenHh">Tên hàng
		hóa:(*)</label>
	<div class="col-sm-4">
		<form:input path="tenHh" placeholder="Tên hàng hóa"
			cssClass="form-control" />
		<form:errors path="tenHh" cssClass="error"></form:errors>
	</div>

	<label class="control-label col-sm-2" for="kyHieuHh">Mã hàng
		hóa:(*)</label>
	<div class="col-sm-4">
		<form:input path="kyHieuHh" placeholder="Mã hàng hóa"
			cssClass="form-control" />
		<form:errors path="kyHieuHh" cssClass="error"></form:errors>
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="nhomHh.maNhomHh">Nhóm
		hàng hóa:</label>
	<div class="col-sm-4">
		<form:select path="nhomHh.maNhomHh" cssClass="form-control">
			<form:option value="0" label=""></form:option>
			<form:options items="${nhomHangHoaDs}" itemValue="maNhomHh"
				itemLabel="tenNhomHh"></form:options>
		</form:select>
		<form:errors path="nhomHh.maNhomHh" cssClass="error"></form:errors>
	</div>

	<label class="control-label col-sm-2" for="donVi.maDv">Đơn vị
		tính:</label>
	<div class="col-sm-4">
		<form:select path="donVi.maDv" cssClass="form-control">
			<form:option value="0" label=""></form:option>
			<form:options items="${donViDs}" itemValue="maDv" itemLabel="tenDv"></form:options>
		</form:select>
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="khoMd.maKho">Kho mặc
		định:</label>
	<div class="col-sm-4">
		<form:select path="khoMd.maKho" cssClass="form-control">
			<form:option value="0" label=""></form:option>
			<form:options items="${khoBaiDs}" itemValue="maKho"
				itemLabel="tenKho"></form:options>
		</form:select>
	</div>

	<label class="control-label col-sm-2" for="tkKhoMd.maTk">Tài
		khoản kho:</label>
	<div class="col-sm-4">
		<form:select path="tkKhoMd.maTk" cssClass="form-control">
			<form:option value="" label=""></form:option>
			<form:options items="${loaiTaiKhoanDs}" itemValue="maTk"
				itemLabel="maTenTk"></form:options>
		</form:select>
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="tkDoanhThuMd.maTk">Tài
		khoản doanh thu:</label>
	<div class="col-sm-4">
		<form:select path="tkDoanhThuMd.maTk" cssClass="form-control">
			<form:option value="" label=""></form:option>
			<form:options items="${loaiTaiKhoanDs}" itemValue="maTk"
				itemLabel="maTenTk"></form:options>
		</form:select>
	</div>

	<label class="control-label col-sm-2" for="tkChiPhiMd.maTk">Tài
		khoản chi phí:</label>
	<div class="col-sm-4">
		<form:select path="tkChiPhiMd.maTk" cssClass="form-control">
			<form:option value="" label=""></form:option>
			<form:options items="${loaiTaiKhoanDs}" itemValue="maTk"
				itemLabel="maTenTk"></form:options>
		</form:select>
	</div>
</div>

<div class="row form-group">
	<div class="col-sm-2">
		<a href="${url}/hanghoa/danhsach" class="btn btn-info btn-sm">Hủy</a>
		<button id="submitBt" type="submit" class="btn btn-info btn-sm">Lưu
			thay đổi</button>
	</div>
</div>
