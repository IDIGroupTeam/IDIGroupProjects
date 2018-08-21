<%@page import="com.idi.finance.bean.taikhoan.LoaiTaiKhoan"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<style>
<!--
.sub-content {
	padding-top: 1px;
}
-->
</style>

<script type="text/javascript">
	function xacNhanXoa(maKyKt){
		BootstrapDialog.confirm({
			title : 'Xác nhận',
			message : 'Bạn muốn xóa kỳ kế toán này không ?<br/>Mã kỳ kế toán: '+maKyKt,
			type : 'type-info',
			closable : true,
			draggable : true,
			btnCancelLabel : 'Không',
			btnOKLabel : 'Có',
			callback : function(result) {
				if (result) {
					$("#mainFinanceForm").attr(
							"action", "${url}/kyketoan/xoa/"+maKyKt);
					$("#mainFinanceForm").attr(
							"method", "GET");
					$("#mainFinanceForm").submit();
				}
			}
		});
	
		return false;
	}

	//Shorthand for $( document ).ready()
	$(function() {
		// Khởi tạo action/method cho mainFinanceForm form
		$("#mainFinanceForm").attr("action", "${url}/kyketoan/luu");
		$("#mainFinanceForm").attr("method", "POST");

	});
</script>

<h4>Xem kỳ kế toán chi tiết</h4>
<hr />

<div class="row form-group">
	<label class="control-label col-sm-2" for="tenKyKt">Tên kỳ kế
		toán (*)</label>
	<div class="col-sm-2">${kyKeToan.tenKyKt}</div>

	<label class="control-label col-sm-2" for="batDau">Bắt đầu</label>
	<div class="col-sm-2">
		<fmt:formatDate value="${kyKeToan.batDau}" pattern="dd/M/yyyy"
			type="Date" dateStyle="SHORT" />
	</div>

	<label class="control-label col-sm-2" for="ketThuc">Kết thúc</label>
	<div class="col-sm-2">
		<fmt:formatDate value="${kyKeToan.ketThuc}" pattern="dd/M/yyyy"
			type="Date" dateStyle="SHORT" />
	</div>
</div>

<div class="row form-group">
	<div class="col-sm-12">
		<a href="${url}/kyketoan/danhsach" class="btn btn-info btn-sm">Danh
			sách kỳ kế toán</a>
		<c:choose>
			<c:when test="${kyKeToan.trangThai==1}">
				<a href="${url}/kyketoan/chuyensodu/${kyKeToan.maKyKt}"
					class="btn btn-info btn-sm"
					title="Chuyển số dư cuối kỳ trước sang số dư đầu kỳ hiện tại">Chuyển
					số dư từ kỳ trước</a>
			</c:when>
		</c:choose>
		<c:choose>
			<c:when test="${kyKeToan.trangThai==1}">
				<a href="#" class="btn btn-info btn-sm disabled"
					title="Nhập số dư đầu kỳ">Nhập số dư đầu kỳ</a>
			</c:when>
		</c:choose>
		<c:choose>
			<c:when test="${kyKeToan.trangThai==1}">
				<a href="#" class="btn btn-info btn-sm disabled"
					title="Xuất số dư cuối kỳ">Xuất số dư cuối kỳ</a>
			</c:when>
		</c:choose>
		<c:choose>
			<c:when test="${kyKeToan.macDinh!=1}">
				<a href="${url}/kyketoan/macdinh/${kyKeToan.maKyKt}"
					class="btn btn-info btn-sm" title="Đặt mặc định"
					onclick="return xacNhanMacDinh(${kyKeToan.maKyKt});">Mặc định</span>
				</a>
			</c:when>
		</c:choose>
		<c:choose>
			<c:when test="${kyKeToan.trangThai==1}">
				<a href="${url}/kyketoan/dong/${kyKeToan.maKyKt}"
					class="btn btn-info btn-sm" title="Đóng kỳ"
					onclick="return xacNhanDong(${kyKeToan.maKyKt});">Đóng kỳ</a>
			</c:when>
			<c:otherwise>
				<a href="${url}/kyketoan/mo/${kyKeToan.maKyKt}"
					class="btn btn-info btn-sm" title="Mở kỳ"
					onclick="return xacNhanMo(${kyKeToan.maKyKt});">Mở kỳ</a>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${kyKeToan.trangThai==1}">
				<a href="${url}/kyketoan/sua/${kyKeToan.maKyKt}"
					class="btn btn-info btn-sm" title="Sửa"> Sửa </a>
				<a href="${url}/kyketoan/xoa/${kyKeToan.maKyKt}"
					class="btn btn-info btn-sm" title="Xóa"
					onclick="return xacNhanXoa(${kyKeToan.maKyKt});">Xóa</a>
			</c:when>
		</c:choose>

	</div>
</div>
<hr />
<h4>Số dự đầu kỳ, cuối kỳ</h4>
<div>
	<ul class="nav nav-tabs nav-pills nav-justified">
		<li class="active"><a data-toggle="tab" href="#sdTaiKhoan">Số
				dư tài khoản</a></li>
		<li><a data-toggle="tab" href="#sdCongNoKH">Công nợ khách
				hàng</a></li>
		<li><a data-toggle="tab" href="#sdCongNoNCC">Công nợ NCC</a></li>
		<li><a data-toggle="tab" href="#sdCongNoNV">Công nợ nhân viên</a></li>
		<li><a data-toggle="tab" href="#sdTonKho">Tồn kho VTHH</a></li>
		<li><a data-toggle="tab" href="#sdCCDC">Công cụ, dụng cụ</a></li>
		<li><a data-toggle="tab" href="#sdTSCD">Tài sản cố định</a></li>
	</ul>

	<div class="tab-content table-responsive sub-content">
		<div id="sdTaiKhoan" class="tab-pane fade in active">
			<table class="table table-bordered table-hover">
				<thead>
					<tr>
						<th rowspan="2" class="text-center">Mã TK</th>
						<th rowspan="2" class="text-center">Tên TK</th>
						<th colspan="2" class="text-center">Đầu kỳ</th>
						<th colspan="2" class="text-center">Cuối kỳ</th>
					</tr>
					<tr>
						<th class="text-center" style="width: 120px;">Nợ</th>
						<th class="text-center" style="width: 120px;">Có</th>
						<th class="text-center" style="width: 120px;">Nợ</th>
						<th class="text-center" style="width: 120px;">Có</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${taiKhoanDs}" var="taiKhoan">
						<tr>
							<td>${taiKhoan.loaiTaiKhoan.maTk}</td>
							<td>${taiKhoan.loaiTaiKhoan.tenTk}</td>
							<td class="text-right" style="width: 120px;"><fmt:formatNumber
									value="${taiKhoan.noDauKy}" maxFractionDigits="2"></fmt:formatNumber></td>
							<td class="text-right" style="width: 120px;"><fmt:formatNumber
									value="${taiKhoan.coDauKy}" maxFractionDigits="2"></fmt:formatNumber></td>
							<td class="text-right" style="width: 120px;"><fmt:formatNumber
									value="${taiKhoan.noCuoiKy}" maxFractionDigits="2"></fmt:formatNumber></td>
							<td class="text-right" style="width: 120px;"><fmt:formatNumber
									value="${taiKhoan.coCuoiKy}" maxFractionDigits="2"></fmt:formatNumber></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="sdCongNoKH" class="tab-pane fade">
			<table class="table table-bordered table-hover">
				<thead>
					<tr>
						<th rowspan="2" class="text-center">Mã khách hàng</th>
						<th rowspan="2" class="text-center">Tên khách hàng</th>
						<th rowspan="2" class="text-center">Mã TK</th>
						<th rowspan="2" class="text-center">Tên TK</th>
						<th colspan="2" class="text-center">Đầu kỳ</th>
						<th colspan="2" class="text-center">Cuối kỳ</th>
					</tr>
					<tr>
						<th class="text-center" style="width: 120px;">Nợ</th>
						<th class="text-center" style="width: 120px;">Có</th>
						<th class="text-center" style="width: 120px;">Nợ</th>
						<th class="text-center" style="width: 120px;">Có</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${khachHangDs}" var="khachHang">
						<tr>
							<td>${khachHang.doiTuong.maDt}</td>
							<td>${khachHang.doiTuong.tenDt}</td>
							<td>${khachHang.loaiTaiKhoan.maTk}</td>
							<td>${khachHang.loaiTaiKhoan.tenTk}</td>
							<td class="text-right" style="width: 120px;"><fmt:formatNumber
									value="${khachHang.noDauKy}" maxFractionDigits="2"></fmt:formatNumber></td>
							<td class="text-right" style="width: 120px;"><fmt:formatNumber
									value="${khachHang.coDauKy}" maxFractionDigits="2"></fmt:formatNumber></td>
							<td class="text-right" style="width: 120px;"><fmt:formatNumber
									value="${khachHang.noCuoiKy}" maxFractionDigits="2"></fmt:formatNumber></td>
							<td class="text-right" style="width: 120px;"><fmt:formatNumber
									value="${khachHang.coCuoiKy}" maxFractionDigits="2"></fmt:formatNumber></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="sdCongNoNCC" class="tab-pane fade">
			<table class="table table-bordered table-hover">
				<thead>
					<tr>
						<th rowspan="2" class="text-center">Mã NCC</th>
						<th rowspan="2" class="text-center">Tên NCC</th>
						<th rowspan="2" class="text-center">Mã TK</th>
						<th rowspan="2" class="text-center">Tên TK</th>
						<th colspan="2" class="text-center">Đầu kỳ</th>
						<th colspan="2" class="text-center">Cuối kỳ</th>
					</tr>
					<tr>
						<th class="text-center" style="width: 120px;">Nợ</th>
						<th class="text-center" style="width: 120px;">Có</th>
						<th class="text-center" style="width: 120px;">Nợ</th>
						<th class="text-center" style="width: 120px;">Có</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${nhaCcDs}" var="nhaCc">
						<tr>
							<td>${nhaCc.doiTuong.maDt}</td>
							<td>${nhaCc.doiTuong.tenDt}</td>
							<td>${nhaCc.loaiTaiKhoan.maTk}</td>
							<td>${nhaCc.loaiTaiKhoan.tenTk}</td>
							<td class="text-right" style="width: 120px;"><fmt:formatNumber
									value="${nhaCc.noDauKy}" maxFractionDigits="2"></fmt:formatNumber></td>
							<td class="text-right" style="width: 120px;"><fmt:formatNumber
									value="${nhaCc.coDauKy}" maxFractionDigits="2"></fmt:formatNumber></td>
							<td class="text-right" style="width: 120px;"><fmt:formatNumber
									value="${nhaCc.noCuoiKy}" maxFractionDigits="2"></fmt:formatNumber></td>
							<td class="text-right" style="width: 120px;"><fmt:formatNumber
									value="${nhaCc.coCuoiKy}" maxFractionDigits="2"></fmt:formatNumber></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="sdCongNoNV" class="tab-pane fade">
			<table class="table table-bordered table-hover">
				<thead>
					<tr>
						<th rowspan="2" class="text-center">Mã NV</th>
						<th rowspan="2" class="text-center">Tên NV</th>
						<th rowspan="2" class="text-center">Mã TK</th>
						<th rowspan="2" class="text-center">Tên TK</th>
						<th colspan="2" class="text-center">Đầu kỳ</th>
						<th colspan="2" class="text-center">Cuối kỳ</th>
					</tr>
					<tr>
						<th class="text-center" style="width: 120px;">Nợ</th>
						<th class="text-center" style="width: 120px;">Có</th>
						<th class="text-center" style="width: 120px;">Nợ</th>
						<th class="text-center" style="width: 120px;">Có</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${nhanVienDs}" var="nhanVien">
						<tr>
							<td>${nhanVien.doiTuong.maDt}</td>
							<td>${nhanVien.doiTuong.tenDt}</td>
							<td>${nhanVien.loaiTaiKhoan.maTk}</td>
							<td>${nhanVien.loaiTaiKhoan.tenTk}</td>
							<td class="text-right" style="width: 120px;"><fmt:formatNumber
									value="${nhanVien.noDauKy}" maxFractionDigits="2"></fmt:formatNumber></td>
							<td class="text-right" style="width: 120px;"><fmt:formatNumber
									value="${nhanVien.coDauKy}" maxFractionDigits="2"></fmt:formatNumber></td>
							<td class="text-right" style="width: 120px;"><fmt:formatNumber
									value="${nhanVien.noCuoiKy}" maxFractionDigits="2"></fmt:formatNumber></td>
							<td class="text-right" style="width: 120px;"><fmt:formatNumber
									value="${nhanVien.coCuoiKy}" maxFractionDigits="2"></fmt:formatNumber></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="sdTonKho" class="tab-pane fade">
			<table class="table table-bordered table-hover">
				<thead>
					<tr>
						<th rowspan="2" class="text-center">Mã TK</th>
						<th rowspan="2" class="text-center">Tên TK</th>
						<th colspan="2" class="text-center">Đầu kỳ</th>
						<th colspan="2" class="text-center">Cuối kỳ</th>
					</tr>
					<tr>
						<th class="text-center">Nợ</th>
						<th class="text-center">Có</th>
						<th class="text-center">Nợ</th>
						<th class="text-center">Có</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div id="sdCCDC" class="tab-pane fade">
			<table class="table table-bordered table-hover">
				<thead>
					<tr>
						<th rowspan="2" class="text-center">Mã TK</th>
						<th rowspan="2" class="text-center">Tên TK</th>
						<th colspan="2" class="text-center">Đầu kỳ</th>
						<th colspan="2" class="text-center">Cuối kỳ</th>
					</tr>
					<tr>
						<th class="text-center">Nợ</th>
						<th class="text-center">Có</th>
						<th class="text-center">Nợ</th>
						<th class="text-center">Có</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div id="sdTSCD" class="tab-pane fade">
			<table class="table table-bordered table-hover">
				<thead>
					<tr>
						<th rowspan="2" class="text-center">Mã TK</th>
						<th rowspan="2" class="text-center">Tên TK</th>
						<th colspan="2" class="text-center">Đầu kỳ</th>
						<th colspan="2" class="text-center">Cuối kỳ</th>
					</tr>
					<tr>
						<th class="text-center">Nợ</th>
						<th class="text-center">Có</th>
						<th class="text-center">Nợ</th>
						<th class="text-center">Có</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</tbody>
			</table>
		</div>

	</div>
</div>
<hr />
<h4>Cấu hình khác</h4>
<div></div>