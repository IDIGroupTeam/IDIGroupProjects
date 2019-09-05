<%@page import="com.idi.finance.bean.kyketoan.KyKeToan"%>
<%@page import="com.idi.finance.bean.taikhoan.LoaiTaiKhoan"%>
<%@page import="com.idi.finance.bean.doituong.DoiTuong"%>
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
		var ngayLap = $.trim($("#ngayLap").html());
		$("#xoaNut")
				.click(
						function() {
							xoa = $(this).attr("href");
							BootstrapDialog
									.confirm({
										title : 'Xác nhận',
										message : 'Bạn muốn xóa phiếu chi này không ?<br/><b>Số phiếu chi:</b> ${chungTu.soCt}<br /> <b>Ngày lập:</b> '
												+ ngayLap
												+ ' <br /> <b>Lý do:</b> ${chungTu.lyDo}',
										type : 'type-info',
										closable : true,
										draggable : true,
										btnCancelLabel : 'Không',
										btnOKLabel : 'Có',
										callback : function(result) {
											if (result) {
												$("#mainFinanceForm").attr(
														"action", xoa);
												$("#mainFinanceForm").attr(
														"method", "GET");
												$("#mainFinanceForm").submit();
											}
										}
									});

							return false;
						});
	});
</script>

<div>
	<span class="pull-left heading4">PHIẾU CHI</span>
</div>
<br />
<hr />

<div class="row form-group">
	<label class="control-label col-sm-2" for="soCt">Số phiếu chi:</label>
	<div class="col-sm-4">${chungTu.loaiCt}${chungTu.soCt}</div>

	<label class="control-label col-sm-2" for=ngayLap>Ngày lập
		phiếu chi:</label>
	<div class="col-sm-4">
		<span id="ngayLap"><fmt:formatDate value="${chungTu.ngayLap}"
				pattern="dd/M/yyyy" type="Date" dateStyle="SHORT" /></span>
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="doiTuong.loaiDt">Loại
		đối tượng:</label>
	<div class="col-sm-4">
		<c:choose>
			<c:when test="${chungTu.doiTuong.loaiDt == DoiTuong.NHAN_VIEN}">
								Nhân viên
							</c:when>
			<c:when test="${chungTu.doiTuong.loaiDt == DoiTuong.KHACH_HANG}">
								Khách hàng
							</c:when>
			<c:when test="${chungTu.doiTuong.loaiDt == DoiTuong.NHA_CUNG_CAP}">
								Nhà cung cấp
							</c:when>
			<c:when test="${chungTu.doiTuong.loaiDt == DoiTuong.KHACH_VANG_LAI}">
								Khách vãng lai
							</c:when>
		</c:choose>
	</div>

	<label class="control-label col-sm-2" for=ngayHt>Ngày hạch
		toán:</label>
	<div class="col-sm-4">
		<span id="ngayHt"><fmt:formatDate value="${chungTu.ngayHt}"
				pattern="dd/M/yyyy" type="Date" dateStyle="SHORT" /></span>
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="doiTuong.tenDt">Đối
		tượng:</label>
	<div class="col-sm-4">${chungTu.doiTuong.tenDt}</div>

	<label class="control-label col-sm-2" for="doiTuong.maThue">Mã
		số thuế:</label>
	<div class="col-sm-4">${chungTu.doiTuong.maThue}</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="doiTuong.diaChi">Địa
		chỉ:</label>
	<div class="col-sm-4">${chungTu.doiTuong.diaChi}</div>

	<label class="control-label col-sm-2" for="doiTuong.nguoiNop">Người
		nộp: </label>
	<div class="col-sm-4">${chungTu.doiTuong.nguoiNop}</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="lyDo">Lý do:</label>
	<div class="col-sm-4">${chungTu.lyDo}</div>

	<label class="control-label col-sm-2" for="kemTheo">Kèm theo <br />số
		chứng từ gốc:
	</label>
	<div class="col-sm-4">${chungTu.kemTheo}</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="lyDo">Loại tiền:</label>
	<div class="col-sm-4">${chungTu.loaiTien.tenLt}</div>

	<label class="control-label col-sm-2" for="kemTheo">Tỷ giá </label>
	<div class="col-sm-4">
		<fmt:formatNumber value="${chungTu.loaiTien.banRa}"
			maxFractionDigits="2"></fmt:formatNumber>
		VND
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2"
		for=taiKhoanNoDs[0].loaiTaiKhoan.maTk>Tài khoản có</label>
	<div class="col-sm-4">
		${chungTu.taiKhoanCoDs[0].loaiTaiKhoan.maTk} -
		${chungTu.taiKhoanCoDs[0].loaiTaiKhoan.tenTk}</div>

	<label class="control-label col-sm-2" for=ngayHt>Ngày thanh
		toán:</label>
	<div class="col-sm-4">
		<span id="ngayTt"><fmt:formatDate value="${chungTu.ngayTt}"
				pattern="dd/M/yyyy" type="Date" dateStyle="SHORT" /></span>
	</div>
</div>

<div class="table-responsive row form-group">
	<label class="control-label col-sm-2">Định khoản</label>
	<table id="taiKhoanTbl"
		class="table table-bordered table-hover text-center dinhkhoan">
		<thead>
			<tr>
				<th class="text-center">Lý do</th>
				<th class="text-center">Tài khoản nợ</th>
				<th class="text-center">Giá trị</th>
				<th class="text-center">Quy đổi</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${chungTu.taiKhoanNoDs}" var="taiKhoanNo"
				varStatus="status">
				<tr id="${status.index}">
					<td class="text-left">${taiKhoanNo.lyDo}</td>
					<td class="text-left">${taiKhoanNo.loaiTaiKhoan.maTk}-${taiKhoanNo.loaiTaiKhoan.tenTk}</td>
					<td class="text-right"><fmt:formatNumber
							value="${taiKhoanNo.soTien.soTien}" maxFractionDigits="2"></fmt:formatNumber>
						${taiKhoanNo.soTien.loaiTien.maLt}</td>
					<td class="text-right"><fmt:formatNumber
							value="${taiKhoanNo.soTien.giaTri}" maxFractionDigits="0"></fmt:formatNumber>
						VND</td>
				</tr>
			</c:forEach>
			<tr>
				<td class="text-left"><b>Thành tiền:</b></td>
				<td></td>
				<td class="text-right"><span id="soTien.giaTriTxt"> <fmt:formatNumber
							value="${chungTu.soTien.soTien}" maxFractionDigits="2"></fmt:formatNumber>
						${chungTu.loaiTien.maLt}
				</span></td>
				<td class="text-right"><span id="soTien.giaTriQdTxt"> <fmt:formatNumber
							value="${chungTu.soTien.giaTri}" maxFractionDigits="0"></fmt:formatNumber>
						VND
				</span></td>
			</tr>
		</tbody>
	</table>
</div>

<div class="row form-group">
	<div class="col-sm-12">
		<a href="${url}/chungtu/phieuchi/danhsach" class="btn btn-info btn-sm">Danh
			sách phiếu chi</a>

		<c:choose>
			<c:when
				test="${kyKeToan!=null && kyKeToan.trangThai!= KyKeToan.DONG}">
				<a href="${url}/chungtu/phieuchi/sua/${chungTu.maCt}"
					class="btn btn-info btn-sm">Sửa</a>
				<a href="${url}/chungtu/phieuchi/pdf/${chungTu.maCt}"
					target="_blank" class="btn btn-info btn-sm">In</a>
				<%-- <a id="xoaNut" href="${url}/chungtu/phieuchi/xoa/${chungTu.maCt}"
					class="btn btn-info btn-sm">Xóa</a> --%>
				<a href="${url}/chungtu/phieuchi/saochep/${chungTu.maCt}"
					class="btn btn-info btn-sm">Sao chép</a>
				<a href="${url}/chungtu/phieuchi/taomoi" class="btn btn-info btn-sm">Tạo
					mới</a>
			</c:when>
		</c:choose>
	</div>
</div>