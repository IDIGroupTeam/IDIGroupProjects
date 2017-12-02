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
										message : 'Bạn muốn xóa báo có này không ?<br/><b>Số báo có:</b> ${chungTu.soCt}<br /> <b>Ngày lập:</b> '
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

<h4>BÁO CÓ</h4>
<hr />
<div class="row form-group">
	<label class="control-label col-sm-2" for="soCt">Số báo có:</label>
	<div class="col-sm-4">${chungTu.soCt}</div>

	<label class="control-label col-sm-2" for=ngayLap>Ngày lập báo
		có:</label>
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
	<label class="control-label col-sm-2" for="soTien.soTien">Số
		tiền:</label>
	<div class="col-sm-4">
		<fmt:formatNumber value="${chungTu.soTien.soTien}"
			maxFractionDigits="2"></fmt:formatNumber>
	</div>

	<label class="control-label col-sm-2" for="soTien.tien.maLt">Loại
		tiền</label>
	<div class="col-sm-4">${chungTu.soTien.tien.tenLt}</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="soTien.giaTri">Thành
		tiền:</label>
	<div class="col-sm-4">
		<fmt:formatNumber value="${chungTu.soTien.giaTri}"
			maxFractionDigits="2"></fmt:formatNumber>
		VND
	</div>

	<label class="control-label col-sm-2" for="soTien.tien.banRa">Tỷ
		giá:</label>
	<div class="col-sm-4">${chungTu.soTien.tien.banRa}&nbsp;VND</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="lyDo">Lý do:</label>
	<div class="col-sm-4">${chungTu.lyDo}</div>

	<label class="control-label col-sm-2" for="kemTheo">Kèm theo <br />số
		chứng từ gốc:
	</label>
	<div class="col-sm-4">${chungTu.kemTheo}</div>
</div>


<div class="table-responsive row form-group">
	<label class="control-label col-sm-2">Định khoản</label>
	<table id="taiKhoanTbl"
		class="table table-bordered table-hover text-center dinhkhoan">
		<thead>
			<tr>
				<th class="text-center" colspan="2">Nợ</th>
				<th class="text-center" colspan="3">Có</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th class="text-center"><b>Tài khoản</b></th>
				<th class="text-center"><b>Giá trị</b></th>
				<th class="text-center"><b>Tài khoản</b></th>
				<th class="text-center"><b>Giá trị</b></th>
				<th class="text-center"><b>Lý do</b></th>
			</tr>
			<c:forEach begin="0" end="${chungTu.soTkLonNhat-1}"
				varStatus="status">
				<tr id="${status.index}">
					<!-- Phần ghi Nợ -->
					<c:choose>
						<c:when test="${status.index < chungTu.taiKhoanNoDs.size()}">
							<td>${chungTu.taiKhoanNoDs[status.index].taiKhoan.maTk}-${chungTu.taiKhoanNoDs[status.index].taiKhoan.tenTk}</td>
							<td><fmt:formatNumber
									value="${chungTu.taiKhoanNoDs[status.index].soTien}"
									maxFractionDigits="2"></fmt:formatNumber></td>
						</c:when>
						<c:otherwise>
							<td></td>
							<td></td>
						</c:otherwise>
					</c:choose>

					<!-- Phần ghi Có -->
					<td>${chungTu.taiKhoanCoDs[status.index].taiKhoan.maTk}-${chungTu.taiKhoanCoDs[status.index].taiKhoan.tenTk}</td>
					<td><fmt:formatNumber
							value="${chungTu.taiKhoanCoDs[status.index].soTien}"
							maxFractionDigits="2"></fmt:formatNumber></td>
					<td>${chungTu.taiKhoanCoDs[status.index].lyDo}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<div class="row form-group">
	<div class="col-sm-4">
		<a href="${url}/danhsachbaoco" class="btn btn-info btn-sm">Danh
			sách báo có</a> <a id="xoaNut" href="${url}/xoabaoco/${chungTu.maCt}"
			class="btn btn-info btn-sm">Xóa</a> <a
			href="${url}/suabaoco/${chungTu.maCt}" class="btn btn-info btn-sm">Sửa</a>
	</div>
</div>