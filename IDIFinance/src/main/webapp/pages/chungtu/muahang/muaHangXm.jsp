<%@page import="com.idi.finance.bean.taikhoan.LoaiTaiKhoan"%>
<%@page import="com.idi.finance.bean.chungtu.DoiTuong"%>
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
										message : 'Bạn muốn xóa chứng từ mua hàng này không ?<br/><b>Số phiếu:</b> ${chungTu.soCt}<br /> <b>Ngày lập:</b> '
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
	<span class="pull-left heading4">CHỨNG TỪ MUA HÀNG</span>
	<%-- <div class="btn-group btn-group-sm pull-right">
		<a href="${url}/muahang/${chungTu.maCt}" class="btn btn-info btn-sm">
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
</div>

<div class="row">
	<label class="control-label col-sm-9">Danh sách hàng hóa, dịch
		vụ</label>
	<div class="col-sm-3 form-group">
		<label class="control-label col-sm-6" for="tkThue.loaiTaiKhoan.maTk">Thuế
			GTGT:</label>
		<div class="col-sm-6">
			<%-- <form:select path="tkThue.loaiTaiKhoan.maTk" cssClass="form-control">
				<form:option value="" label=""></form:option>
				<form:option value="3331" label="3331"></form:option>
			</form:select> --%>
		</div>
	</div>
	<div class="table-responsive">
		<table id="taiKhoanTbl"
			class="table table-bordered table-hover text-center dinhkhoan">
			<thead>
				<tr>
					<th class="text-center">Hàng hóa, dịch vụ</th>
					<th class="text-center">Đơn vị tính</th>
					<th class="text-center">Số lượng</th>
					<th class="text-center">Đơn giá</th>
					<th class="text-center">Thành tiền</th>
					<th class="text-center">Kho</th>
					<th class="text-center">TK Kho (Nợ)</th>
					<th class="text-center">TK thanh toán (Có)</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${chungTu.hangHoaDs}" var="hangHoa"
					varStatus="status">
					<tr id="${status.index}">
						<td class="text-left">${hangHoa.tenHh}</td>
						<td>${hangHoa.donVi.tenDv}</td>
						<td><fmt:formatNumber value="${hangHoa.soLuong}"
								maxFractionDigits="2"></fmt:formatNumber></td>
						<td><fmt:formatNumber value="${hangHoa.giaMua.soTien}"
								maxFractionDigits="2"></fmt:formatNumber></td>
						<td><fmt:formatNumber
								value="${hangHoa.soLuong*hangHoa.giaMua.soTien}"
								maxFractionDigits="2"></fmt:formatNumber></td>
						<td>${hangHoa.kho.tenKho}</td>
						<td>${hangHoa.tkKho.loaiTaiKhoan.maTk}</td>
						<td>${hangHoa.tkThanhtoan.loaiTaiKhoan.maTk}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<div class="row form-group">
	<div class="col-sm-12">
		<a href="${url}/chungtu/muahang/danhsach" class="btn btn-info btn-sm">Danh
			sách chứng từ mua hàng</a> <a
			href="${url}/chungtu/muahang/pdf/${chungTu.maCt}"
			class="btn btn-info btn-sm"> Xuất Pdf </a> <a
			href="${url}/chungtu/muahang/pdf/${chungTu.maCt}"
			class="btn btn-info btn-sm"> Phiếu nhập kho </a> <a id="xoaNut"
			href="${url}/chungtu/muahang/xoa/${chungTu.maCt}"
			class="btn btn-info btn-sm">Xóa</a> <a
			href="${url}/chungtu/muahang/sua/${chungTu.maCt}"
			class="btn btn-info btn-sm">Sửa</a> <a
			href="${url}/chungtu/muahang/taomoi" class="btn btn-info btn-sm">Tạo
			mới</a>
	</div>
</div>