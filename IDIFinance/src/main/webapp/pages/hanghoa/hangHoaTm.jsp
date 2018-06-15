<%@page import="com.idi.finance.bean.hanghoa.HangHoa"%>
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

		$('#tkKhoMd\\.maTk').combobox();
		$('#tkDoanhThuMd\\.maTk').combobox();
		$('#tkChiPhiMd\\.maTk').combobox();
		$('#tkChietKhauMd\\.maTk').combobox();
		$('#tkGiamGiaMd\\.maTk').combobox();
		$('#tkTraLaiMd\\.maTk').combobox();
	});
</script>

<h4>Thêm mới hàng hóa</h4>
<hr />

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
	<label class="control-label col-sm-2" for="donVi.maDv">Đơn vị
		tính (*):</label>
	<div class="col-sm-4">
		<form:select path="donVi.maDv" cssClass="form-control">
			<form:option value="0" label=""></form:option>
			<form:options items="${donViDs}" itemValue="maDv" itemLabel="tenDv"></form:options>
		</form:select>
		<form:errors path="donVi.maDv" cssClass="error"></form:errors>
	</div>

	<label class="control-label col-sm-2" for="nhomHh.maNhomHh">Nhóm
		hàng hóa (*):</label>
	<div class="col-sm-4">
		<form:select path="nhomHh.maNhomHh" cssClass="form-control">
			<form:option value="0" label=""></form:option>
			<form:options items="${nhomHangHoaDs}" itemValue="maNhomHh"
				itemLabel="tenNhomHh"></form:options>
		</form:select>
		<form:errors path="nhomHh.maNhomHh" cssClass="error"></form:errors>
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

	<label class="control-label col-sm-2" for="tinhChat">Tính chất</label>
	<div class="col-sm-4">
		<form:select path="tinhChat" cssClass="form-control">
			<form:option value="${HangHoa.TINH_CHAT_VTHH}"
				label="Vật tư hàng hóa"></form:option>
			<form:option value="${HangHoa.TINH_CHAT_DV}" label="Dịch vụ"></form:option>
			<form:option value="${HangHoa.TINH_CHAT_TP}" label="Thành phẩm"></form:option>
		</form:select>
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="tkKhoMd.maTk">Tài
		khoản kho:</label>
	<div class="col-sm-4">
		<form:select path="tkKhoMd.maTk" cssClass="form-control">
			<form:option value="" label=""></form:option>
			<form:options items="${loaiTaiKhoanDs}" itemValue="maTk"
				itemLabel="maTenTk"></form:options>
		</form:select>
	</div>

	<label class="control-label col-sm-2" for="thoiHanBh">Thời hạn
		bảo hành:</label>
	<div class="col-sm-4">
		<form:input path="thoiHanBh" placeholder="0" cssClass="form-control" />
		<form:errors path="thoiHanBh" cssClass="error"></form:errors>
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

	<label class="control-label col-sm-2" for="tyLeCktmMd">Tỷ lệ
		chiết khấu:</label>
	<div class="col-sm-4">
		<form:input path="tyLeCktmMd" cssClass="form-control" placeholder="0" />
		<form:errors path="tyLeCktmMd" cssClass="error"></form:errors>
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="tkChiPhiMd.maTk">Tài
		khoản chi phí:</label>
	<div class="col-sm-4">
		<form:select path="tkChiPhiMd.maTk" cssClass="form-control">
			<form:option value="" label=""></form:option>
			<form:options items="${loaiTaiKhoanDs}" itemValue="maTk"
				itemLabel="maTenTk"></form:options>
		</form:select>
	</div>

	<label class="control-label col-sm-2" for="thueSuatGtgtMd">Thuế
		xuất GTGT:</label>
	<div class="col-sm-4">
		<form:input path="thueSuatGtgtMd" cssClass="form-control"
			placeholder="0" />
		<form:errors path="thueSuatGtgtMd" cssClass="error"></form:errors>
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="tkChietKhauMd.maTk">Tài
		khoản chiết khấu:</label>
	<div class="col-sm-4">
		<form:select path="tkChietKhauMd.maTk" cssClass="form-control">
			<form:option value="" label=""></form:option>
			<form:options items="${loaiTaiKhoanDs}" itemValue="maTk"
				itemLabel="maTenTk"></form:options>
		</form:select>
	</div>

	<label class="control-label col-sm-2" for="thueSuatXkMd">Thuế
		xuất xuất khẩu:</label>
	<div class="col-sm-4">
		<form:input path="thueSuatXkMd" cssClass="form-control"
			placeholder="0" />
		<form:errors path="thueSuatXkMd" cssClass="error"></form:errors>
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="tkGiamGiaMd.maTk">Tài
		khoản giảm giá:</label>
	<div class="col-sm-4">
		<form:select path="tkGiamGiaMd.maTk" cssClass="form-control">
			<form:option value="" label=""></form:option>
			<form:options items="${loaiTaiKhoanDs}" itemValue="maTk"
				itemLabel="maTenTk"></form:options>
		</form:select>
	</div>

	<label class="control-label col-sm-2" for="thueSuatNkMd">Thuế
		suất nhập khẩu:</label>
	<div class="col-sm-4">
		<form:input path="thueSuatNkMd" cssClass="form-control"
			placeholder="0" />
		<form:errors path="thueSuatNkMd" cssClass="error"></form:errors>
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="tkTraLaiMd.maTk">Tài
		khoản trả lại:</label>
	<div class="col-sm-4">
		<form:select path="tkTraLaiMd.maTk" cssClass="form-control">
			<form:option value="" label=""></form:option>
			<form:options items="${loaiTaiKhoanDs}" itemValue="maTk"
				itemLabel="maTenTk"></form:options>
		</form:select>
	</div>

	<label class="control-label col-sm-2" for="thueSuatTtdbMd">Thuế
		suất TTDB:</label>
	<div class="col-sm-4">
		<form:input path="thueSuatTtdbMd" cssClass="form-control"
			placeholder="0" />
		<form:errors path="thueSuatTtdbMd" cssClass="error"></form:errors>
	</div>
</div>

<div class="row form-group">
	<div class="col-sm-2">
		<a href="${url}/hanghoa/danhsach" class="btn btn-info btn-sm">Hủy</a>
		<button id="submitBt" type="submit" class="btn btn-info btn-sm">Tạo
			mới</button>
	</div>
</div>
