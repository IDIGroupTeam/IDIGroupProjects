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
										message : 'Bạn muốn xóa phiếu kế toán thổng hợp này không ?<br/><b>Số phiếu:</b> ${chungTu.soCt}<br /> <b>Ngày lập:</b> '
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
	<span class="pull-left heading4">PHIẾU KẾ TOÁN TỔNG HỢP</span>
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
	<label class="control-label col-sm-2" for="lyDo">Lý do:</label>
	<div class="col-sm-4">${chungTu.lyDo}</div>

	<label class="control-label col-sm-2" for=ngayHt>Ngày hạch
		toán:</label>
	<div class="col-sm-4">
		<span id="ngayHt"><fmt:formatDate value="${chungTu.ngayHt}"
				pattern="dd/M/yyyy" type="Date" dateStyle="SHORT" /></span>
	</div>
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
	<label class="control-label col-sm-2" for="kemTheo">Kèm theo <br />số
		chứng từ gốc:
	</label>
	<div class="col-sm-4">${chungTu.kemTheo}</div>

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
				<th class="text-center" rowspan="2">Tài khoản</th>
				<th class="text-center" rowspan="2" style="width: 100px;">Nợ</th>
				<th class="text-center" rowspan="2" style="width: 100px;">Có</th>
				<th class="text-center" rowspan="2">Lý do</th>
				<th class="text-center" colspan="2">Đối tượng</th>
				<th class="text-center" rowspan="2" style="width: 50px;">Nhóm</th>
			</tr>
			<tr>
				<th class="text-center" style="width: 100px;">Tên</th>
				<th class="text-center" style="width: 100px;">Loại</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${chungTu.taiKhoanKtthDs}" var="taiKhoanKtth"
				varStatus="status">
				<tr>
					<td class="text-left">${taiKhoanKtth.loaiTaiKhoan.maTenTk}</td>
					<c:choose>
						<c:when test="${taiKhoanKtth.soDu == LoaiTaiKhoan.NO}">
							<td class="text-right" style="width: 100px;"><fmt:formatNumber
									value="${taiKhoanKtth.no.soTien}" maxFractionDigits="2"></fmt:formatNumber></td>
							<td class="text-right" style="width: 100px;">0</td>
						</c:when>
						<c:otherwise>
							<td class="text-right" style="width: 100px;">0</td>
							<td class="text-right" style="width: 100px;"><fmt:formatNumber
									value="${taiKhoanKtth.co.soTien}" maxFractionDigits="2"></fmt:formatNumber></td>
						</c:otherwise>
					</c:choose>
					<td class="text-left">${taiKhoanKtth.lyDo}</td>
					<td class="text-left" style="width: 100px;">${taiKhoanKtth.doiTuong.tenDt}</td>
					<td class="text-left" style="width: 100px;"><c:choose>
							<c:when
								test="${taiKhoanKtth.doiTuong.loaiDt == DoiTuong.NHAN_VIEN}">
								Nhân viên
							</c:when>
							<c:when
								test="${taiKhoanKtth.doiTuong.loaiDt == DoiTuong.KHACH_HANG}">
								Khách hàng
							</c:when>
							<c:when
								test="${taiKhoanKtth.doiTuong.loaiDt == DoiTuong.NHA_CUNG_CAP}">
								Nhà cung cấp
							</c:when>
							<c:when
								test="${taiKhoanKtth.doiTuong.loaiDt == DoiTuong.KHACH_VANG_LAI}">
								Khách vãng lai
							</c:when>
						</c:choose></td>
					<td style="width: 50px;">${taiKhoanKtth.nhomDk}</td>
				</tr>
			</c:forEach>
			<tr>
				<td class="text-left"><b>Thành tiền:</b></td>
				<td class="text-right" colspan="2" style="width: 200px;"><fmt:formatNumber
						value="${chungTu.soTien.soTien}" maxFractionDigits="2"></fmt:formatNumber>
					${chungTu.loaiTien.maLt}</td>
				<td class="text-left"><b>Quy đổi:</b></td>
				<td class="text-right" colspan="2" style="width: 200px;"><fmt:formatNumber
						value="${chungTu.soTien.giaTri}" maxFractionDigits="2"></fmt:formatNumber>
					VND</td>
				<td style="width: 50px;"></td>
			</tr>
		</tbody>
	</table>
</div>

<div class="row form-group">
	<div class="col-sm-12">
		<a href="${url}/chungtu/ktth/danhsach" class="btn btn-info btn-sm">Danh
			sách phiếu kế toán tổng hợp</a>

		<c:choose>
			<c:when
				test="${kyKeToan!=null && kyKeToan.trangThai!= KyKeToan.DONG}">
				<a href="${url}/chungtu/ktth/sua/${chungTu.maCt}"
					class="btn btn-info btn-sm">Sửa</a>
				<a href="${url}/chungtu/ktth/pdf/${chungTu.maCt}" target="_blank"
					class="btn btn-info btn-sm">In</a>
				<%-- <a id="xoaNut" href="${url}/chungtu/ktth/xoa/${chungTu.maCt}"
					class="btn btn-info btn-sm">Xóa</a> --%>
				<a href="${url}/chungtu/ktth/saochep/${chungTu.maCt}"
					class="btn btn-info btn-sm">Sao chép</a>
				<a href="${url}/chungtu/ktth/taomoi" class="btn btn-info btn-sm">Tạo
					mới</a>
			</c:when>
		</c:choose>
	</div>
</div>