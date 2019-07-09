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
										message : 'Bạn muốn xóa chứng từ bán hàng này không ?<br/><b>Số phiếu:</b> ${chungTu.soCt}<br /> <b>Ngày lập:</b> '
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

<style>
<!--
.sub-content {
	padding-top: 1px;
}
-->
</style>

<div>
	<span class="pull-left heading4">CHỨNG TỪ BÁN HÀNG</span>
	<%-- <div class="btn-group btn-group-sm pull-right">
		<a href="${url}/banhang/${chungTu.maCt}" class="btn btn-info btn-sm">
			<span class="glyphicon glyphicon-download"></span> Xuất
		</a>
	</div> --%>
</div>
<br />
<hr />

<div class="row form-group">
	<label class="control-label col-sm-2" for="soCt">Số phiếu:</label>
	<div class="col-sm-4">${chungTu.loaiCt}${chungTu.soCt}</div>

	<label class="control-label col-sm-2" for=ngayLap>Ngày lập
		phiếu:</label>
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
		tượng nộp:</label>
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
	<label class="control-label col-sm-2" for="soTien.giaTri">Thành
		tiền:</label>
	<div class="col-sm-4">
		<fmt:formatNumber value="${chungTu.soTien.giaTri}"
			maxFractionDigits="2"></fmt:formatNumber>
		VND
	</div>

	<label class="control-label col-sm-2" for=ngayHt>Ngày thanh
		toán:</label>
	<div class="col-sm-4">
		<span id="ngayTt"><fmt:formatDate value="${chungTu.ngayTt}"
				pattern="dd/M/yyyy" type="Date" dateStyle="SHORT" /></span>
	</div>
</div>

<label class="control-label">Danh sách hàng hóa, dịch vụ</label>
<div class="row">
	<c:choose>
		<c:when test="${chungTu.tinhChatCt==2}">
			<jsp:include page="banHangXm_Hh_Nn.jsp"></jsp:include>
		</c:when>
		<c:otherwise>
			<jsp:include page="banHangXm_Hh_Tn.jsp"></jsp:include>
		</c:otherwise>
	</c:choose>
	<br />
</div>

<div class="row form-group">
	<div class="col-sm-12">
		<a href="${url}/chungtu/banhang/danhsach" class="btn btn-info btn-sm">Danh
			sách chứng từ bán hàng</a>

		<c:choose>
			<c:when
				test="${kyKeToan!=null && kyKeToan.trangThai!= KyKeToan.DONG}">
				<a href="${url}/chungtu/banhang/sua/${chungTu.maCt}"
					class="btn btn-info btn-sm">Sửa</a>
				<a href="${url}/chungtu/banhang/pdf/${chungTu.maCt}"
					class="btn btn-info btn-sm">In</a>
				<a href="${url}/chungtu/banhang/pdf/${chungTu.maCt}"
					class="btn btn-info btn-sm"> Phiếu xuất kho </a>
				<a id="xoaNut" href="${url}/chungtu/banhang/xoa/${chungTu.maCt}"
					class="btn btn-info btn-sm">Xóa</a>
				<a href="${url}/chungtu/banhang/taomoi/${chungTu.tinhChatCt}"
					class="btn btn-info btn-sm">Tạo mới</a>
			</c:when>
		</c:choose>
	</div>
</div>