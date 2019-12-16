<%@page import="com.idi.finance.bean.taikhoan.LoaiTaiKhoan"%>
<%@page import="com.idi.finance.bean.kyketoan.KyKeToan"%>
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

<div class="row">
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

<div class="row">
	<div class="col-sm-12">
		<a href="${url}/kyketoan/danhsach" class="btn btn-info btn-sm">Danh
			sách kỳ kế toán</a>
		<c:choose>
			<c:when test="${kyKeToan.trangThai==KyKeToan.MO}">
				<a href="${url}/kyketoan/soduky/chuyen/${kyKeToan.maKyKt}"
					class="btn btn-info btn-sm"
					title="Chuyển số dư cuối kỳ trước sang số dư đầu kỳ hiện tại">Chuyển
					số dư từ kỳ trước</a>
				<a href="${url}/kyketoan/soduky/nhap/${kyKeToan.maKyKt}"
					class="btn btn-info btn-sm" title="Nhập số dư đầu kỳ">Nhập số
					dư đầu kỳ</a>
				<a href="#" class="btn btn-info btn-sm disabled"
					title="Kiểm tra dữ liệu trước khi xuất số dư cuối kỳ">Kiểm tra
					dữ liệu</a>
				<a href="${url}/kyketoan/soduky/xuat/${kyKeToan.maKyKt}"
					class="btn btn-info btn-sm" title="Xuất số dư cuối kỳ">Xuất số
					dư cuối kỳ</a>
			</c:when>
		</c:choose>
		<c:choose>
			<c:when test="${kyKeToan.macDinh!=KyKeToan.MAC_DINH}">
				<a href="${url}/kyketoan/macdinh/${kyKeToan.maKyKt}"
					class="btn btn-info btn-sm" title="Đặt mặc định"
					onclick="return xacNhanMacDinh(${kyKeToan.maKyKt});">Mặc định </a>
			</c:when>
		</c:choose>
		<c:choose>
			<c:when test="${kyKeToan.trangThai==KyKeToan.MO}">
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
		<%-- <c:choose>
			<c:when test="${kyKeToan.trangThai==KyKeToan.MO}">
				<a href="${url}/kyketoan/sua/${kyKeToan.maKyKt}"
					class="btn btn-info btn-sm" title="Sửa"> Sửa </a>
				<a href="${url}/kyketoan/xoa/${kyKeToan.maKyKt}"
					class="btn btn-info btn-sm" title="Xóa"
					onclick="return xacNhanXoa(${kyKeToan.maKyKt});">Xóa</a>
			</c:when>
		</c:choose> --%>
	</div>
</div>
<hr />
<h4>Số dư đầu kỳ, cuối kỳ</h4>
<div class="row">
	<ul class="nav nav-tabs nav-pills nav-justified">
		<li class="active"><a data-toggle="tab" href="#sdTaiKhoan"
			data-target="#sdTaiKhoan">Số dư tài khoản</a></li>
		<li><a data-toggle="tab" href="#sdCongNoKH"
			data-target="#sdCongNoKH">Công nợ KH</a></li>
		<li><a data-toggle="tab" href="#sdCongNoNCC"
			data-target="#sdCongNoNCC">Công nợ NCC</a></li>
		<li><a data-toggle="tab" href="#sdCongNoNV"
			data-target="#sdCongNoNV">Công nợ nhân viên</a></li>
		<li><a data-toggle="tab" href="#sdTonKho" data-target="#sdTonKho">Tồn
				kho VTHH</a></li>
		<!-- <li><a data-toggle="tab" href="#sdCCDC">Công cụ, dụng cụ</a></li>
		<li><a data-toggle="tab" href="#sdTSCD">Tài sản cố định</a></li> -->
	</ul>

	<div class="tab-content sub-content">
		<div id="sdTaiKhoan" class="tab-pane fade in active table-responsive">
			<table class="table table-bordered table-hover">
				<thead>
					<tr>
						<th rowspan="3" class="text-center">Mã TK</th>
						<th rowspan="3" class="text-center">Tên TK</th>
						<th colspan="5" class="text-center">Đầu kỳ</th>
					</tr>
					<tr>
						<th class="text-center" colspan="2">Nợ</th>
						<th class="text-center" colspan="2">Có</th>
						<th class="text-center" rowspan="2" style="width: 50px;">Tiền</th>
					</tr>
					<tr>
						<th class="text-center" style="width: 100px;">VND</th>
						<th class="text-center" style="width: 100px;">Ngoại tệ</th>
						<th class="text-center" style="width: 100px;">VND</th>
						<th class="text-center" style="width: 100px;">Ngoại tệ</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${not empty taiKhoanDs and taiKhoanDs.size()>0}">
							<c:forEach items="${taiKhoanDs}" var="taiKhoan">
								<tr>
									<td>${taiKhoan.loaiTaiKhoan.maTk}</td>
									<td>${taiKhoan.loaiTaiKhoan.tenTk}</td>
									<td class="text-right" style="width: 120px;"><fmt:formatNumber
											value="${taiKhoan.noDauKy}" maxFractionDigits="2"></fmt:formatNumber></td>
									<td class="text-right"><fmt:formatNumber
											value="${taiKhoan.noDauKyNt}" maxFractionDigits="2"></fmt:formatNumber></td>
									<td class="text-right" style="width: 120px;"><fmt:formatNumber
											value="${taiKhoan.coDauKy}" maxFractionDigits="2"></fmt:formatNumber></td>
									<td class="text-right"><fmt:formatNumber
											value="${taiKhoan.coDauKyNt}" maxFractionDigits="2"></fmt:formatNumber></td>
									<td>${taiKhoan.loaiTien.maLt}</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>

			<c:choose>
				<c:when test="${kyKeToan.trangThai==KyKeToan.MO and kyKeToan.dau}">
					<div class="col-sm-12">
						<a class="btn btn-info btn-sm" title="Thêm số dư" href="#"
							data-toggle="modal" data-target="#sdTaiKhoanModal">Thêm & Sửa
							số dư </a>
					</div>
				</c:when>
			</c:choose>
		</div>
		<div id="sdCongNoKH" class="tab-pane fade table-responsive">
			<table class="table table-bordered table-hover">
				<thead>
					<tr>
						<th rowspan="3" class="text-center">Mã khách hàng</th>
						<th rowspan="3" class="text-center">Tên khách hàng</th>
						<th rowspan="3" class="text-center">Mã TK</th>
						<th rowspan="3" class="text-center">Tên TK</th>
						<th colspan="5" class="text-center">Đầu kỳ</th>
					</tr>
					<tr>
						<th class="text-center" colspan="2">Nợ</th>
						<th class="text-center" colspan="2">Có</th>
						<th class="text-center" rowspan="2" style="width: 50px;">Tiền</th>
					</tr>
					<tr>
						<th class="text-center" style="width: 100px;">VND</th>
						<th class="text-center" style="width: 100px;">Ngoại tệ</th>
						<th class="text-center" style="width: 100px;">VND</th>
						<th class="text-center" style="width: 100px;">Ngoại tệ</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${not empty khachHangDs and khachHangDs.size()>0}">
							<c:forEach items="${khachHangDs}" var="khachHang">
								<tr>
									<td>${khachHang.doiTuong.khDt}</td>
									<td>${khachHang.doiTuong.tenDt}</td>
									<td>${khachHang.loaiTaiKhoan.maTk}</td>
									<td>${khachHang.loaiTaiKhoan.tenTk}</td>
									<td class="text-right" style="width: 120px;"><fmt:formatNumber
											value="${khachHang.noDauKy}" maxFractionDigits="2"></fmt:formatNumber></td>
									<td class="text-right"><fmt:formatNumber
											value="${khachHang.noDauKyNt}" maxFractionDigits="2"></fmt:formatNumber></td>
									<td class="text-right" style="width: 120px;"><fmt:formatNumber
											value="${khachHang.coDauKy}" maxFractionDigits="2"></fmt:formatNumber></td>
									<td class="text-right"><fmt:formatNumber
											value="${khachHang.coDauKyNt}" maxFractionDigits="2"></fmt:formatNumber></td>
									<td>${khachHang.loaiTien.maLt}</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>

			<c:choose>
				<c:when test="${kyKeToan.trangThai==KyKeToan.MO and kyKeToan.dau}">
					<div class="col-sm-12">
						<a class="btn btn-info btn-sm" title="Thêm số dư" href="#"
							data-toggle="modal" data-target="#sdCongNoKHModal">Thêm & Sửa
							số dư CnKh </a>
					</div>
				</c:when>
			</c:choose>
		</div>
		<div id="sdCongNoNCC" class="tab-pane fade table-responsive">
			<table class="table table-bordered table-hover">
				<thead>
					<tr>
						<th rowspan="3" class="text-center">Mã NCC</th>
						<th rowspan="3" class="text-center">Tên NCC</th>
						<th rowspan="3" class="text-center">Mã TK</th>
						<th rowspan="3" class="text-center">Tên TK</th>
						<th colspan="5" class="text-center">Đầu kỳ</th>
					</tr>
					<tr>
						<th class="text-center" colspan="2">Nợ</th>
						<th class="text-center" colspan="2">Có</th>
						<th class="text-center" rowspan="2" style="width: 50px;">Tiền</th>
					</tr>
					<tr>
						<th class="text-center" style="width: 100px;">VND</th>
						<th class="text-center" style="width: 100px;">Ngoại tệ</th>
						<th class="text-center" style="width: 100px;">VND</th>
						<th class="text-center" style="width: 100px;">Ngoại tệ</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${not empty nhaCcDs and nhaCcDs.size()>0}">
							<c:forEach items="${nhaCcDs}" var="nhaCc">
								<tr>
									<td>${nhaCc.doiTuong.khDt}</td>
									<td>${nhaCc.doiTuong.tenDt}</td>
									<td>${nhaCc.loaiTaiKhoan.maTk}</td>
									<td>${nhaCc.loaiTaiKhoan.tenTk}</td>
									<td class="text-right" style="width: 120px;"><fmt:formatNumber
											value="${nhaCc.noDauKy}" maxFractionDigits="2"></fmt:formatNumber></td>
									<td class="text-right"><fmt:formatNumber
											value="${nhaCc.noDauKyNt}" maxFractionDigits="2"></fmt:formatNumber></td>
									<td class="text-right" style="width: 120px;"><fmt:formatNumber
											value="${nhaCc.coDauKy}" maxFractionDigits="2"></fmt:formatNumber></td>
									<td class="text-right"><fmt:formatNumber
											value="${nhaCc.coDauKyNt}" maxFractionDigits="2"></fmt:formatNumber></td>
									<td>${nhaCc.loaiTien.maLt}</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>

			<c:choose>
				<c:when test="${kyKeToan.trangThai==KyKeToan.MO and kyKeToan.dau}">
					<div class="col-sm-12">
						<a class="btn btn-info btn-sm" title="Thêm số dư" href="#"
							data-toggle="modal" data-target="#sdCongNoNCCModal">Thêm &
							Sửa số dư CnNcc</a>
					</div>
				</c:when>
			</c:choose>
		</div>
		<div id="sdCongNoNV" class="tab-pane fade table-responsive">
			<table class="table table-bordered table-hover">
				<thead>
					<tr>
						<th rowspan="3" class="text-center">Mã NV</th>
						<th rowspan="3" class="text-center">Tên NV</th>
						<th rowspan="3" class="text-center">Mã TK</th>
						<th rowspan="3" class="text-center">Tên TK</th>
						<th colspan="5" class="text-center">Đầu kỳ</th>
					</tr>
					<tr>
						<th class="text-center" colspan="2">Nợ</th>
						<th class="text-center" colspan="2">Có</th>
						<th class="text-center" rowspan="2" style="width: 50px;">Tiền</th>
					</tr>
					<tr>
						<th class="text-center" style="width: 100px;">VND</th>
						<th class="text-center" style="width: 100px;">Ngoại tệ</th>
						<th class="text-center" style="width: 100px;">VND</th>
						<th class="text-center" style="width: 100px;">Ngoại tệ</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${not empty nhanVienDs and nhanVienDs.size()>0}">
							<c:forEach items="${nhanVienDs}" var="nhanVien">
								<tr>
									<td>${nhanVien.doiTuong.maDt}</td>
									<td>${nhanVien.doiTuong.tenDt}</td>
									<td>${nhanVien.loaiTaiKhoan.maTk}</td>
									<td>${nhanVien.loaiTaiKhoan.tenTk}</td>
									<td class="text-right" style="width: 120px;"><fmt:formatNumber
											value="${nhanVien.noDauKy}" maxFractionDigits="2"></fmt:formatNumber></td>
									<td class="text-right"><fmt:formatNumber
											value="${nhanVien.noDauKyNt}" maxFractionDigits="2"></fmt:formatNumber></td>
									<td class="text-right" style="width: 120px;"><fmt:formatNumber
											value="${nhanVien.coDauKy}" maxFractionDigits="2"></fmt:formatNumber></td>
									<td class="text-right"><fmt:formatNumber
											value="${nhanVien.coDauKyNt}" maxFractionDigits="2"></fmt:formatNumber></td>
									<td>${nhanVien.loaiTien.maLt}</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>

			<c:choose>
				<c:when test="${kyKeToan.trangThai==KyKeToan.MO and kyKeToan.dau}">
					<div class="col-sm-12">
						<a class="btn btn-info btn-sm" title="Thêm số dư" href="#"
							data-toggle="modal" data-target="#sdCongNoNVModal">Thêm & Sửa
							số dư CnNv</a>
					</div>
				</c:when>
			</c:choose>
		</div>
		<div id="sdTonKho" class="tab-pane fade table-responsive">
			<table class="table table-bordered table-hover">
				<thead>
					<tr>
						<th colspan="2" class="text-center">Hàng hóa</th>
						<th colspan="2" class="text-center">Kho hàng</th>
						<th colspan="2" class="text-center">Tài khoản</th>
						<th colspan="7" class="text-center">Tồn đầu kỳ</th>
					</tr>
					<tr>
						<th rowspan="2" class="text-center" style="width: 50px;">Mã</th>
						<th rowspan="2" class="text-center">Tên</th>
						<th rowspan="2" class="text-center" style="width: 50px;">Mã</th>
						<th rowspan="2" class="text-center">Tên</th>
						<th rowspan="2" class="text-center" style="width: 50px;">Mã</th>
						<th rowspan="2" class="text-center">Tên</th>
						<th rowspan="2" class="text-center" style="width: 100px;">Số
							lượng</th>
						<th rowspan="2" class="text-center" style="width: 100px;">Đơn
							vị</th>
						<th colspan="2" class="text-center" style="width: 100px;">Nợ</th>
						<th colspan="2" class="text-center" style="width: 100px;">Có</th>
						<th rowspan="2" class="text-center" style="width: 50px;">Tiền</th>
					</tr>
					<tr>
						<th class="text-center" style="width: 100px;">VND</th>
						<th class="text-center" style="width: 100px;">Ngoại tệ</th>
						<th class="text-center" style="width: 100px;">VND</th>
						<th class="text-center" style="width: 100px;">Ngoại tệ</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${not empty tonKhoDs and tonKhoDs.size()>0}">
							<c:forEach items="${tonKhoDs}" var="tonKho">
								<tr>
									<td>${tonKho.hangHoa.kyHieuHh}</td>
									<td>${tonKho.hangHoa.tenHh}</td>
									<td>${tonKho.khoHang.kyHieuKho}</td>
									<td>${tonKho.khoHang.tenKho}</td>
									<td>${tonKho.loaiTaiKhoan.maTk}</td>
									<td>${tonKho.loaiTaiKhoan.tenTk}</td>
									<td>${tonKho.soLuong}</td>
									<td>${tonKho.hangHoa.donVi.tenDv}</td>
									<td class="text-right" style="width: 100px;"><fmt:formatNumber
											value="${tonKho.noDauKy}" maxFractionDigits="2"></fmt:formatNumber></td>
									<td class="text-right"><fmt:formatNumber
											value="${tonKho.noDauKyNt}" maxFractionDigits="2"></fmt:formatNumber></td>
									<td class="text-right" style="width: 100px;"><fmt:formatNumber
											value="${tonKho.coDauKy}" maxFractionDigits="2"></fmt:formatNumber></td>
									<td class="text-right"><fmt:formatNumber
											value="${tonKho.coDauKyNt}" maxFractionDigits="2"></fmt:formatNumber></td>
									<td>${tonKho.loaiTien.maLt}</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>

			<c:choose>
				<c:when test="${kyKeToan.trangThai==KyKeToan.MO and kyKeToan.dau}">
					<div class="col-sm-12">
						<a class="btn btn-info btn-sm" title="Thêm số dư" href="#"
							data-toggle="modal" data-target="#sdTonKhoModal">Thêm & Sửa
							số dư tồn kho VTHH</a>
					</div>
				</c:when>
			</c:choose>
		</div>
		<div id="sdCCDC" class="tab-pane fade table-responsive">
			<table class="table table-bordered table-hover">
				<thead>
					<tr>
						<th rowspan="2" class="text-center">Mã TK</th>
						<th rowspan="2" class="text-center">Tên TK</th>
						<th colspan="2" class="text-center">Đầu kỳ</th>
					</tr>
					<tr>
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
					</tr>
				</tbody>
			</table>
		</div>
		<div id="sdTSCD" class="tab-pane fade table-responsive">
			<table class="table table-bordered table-hover">
				<thead>
					<tr>
						<th rowspan="2" class="text-center">Mã TK</th>
						<th rowspan="2" class="text-center">Tên TK</th>
						<th colspan="2" class="text-center">Đầu kỳ</th>
					</tr>
					<tr>
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
					</tr>
				</tbody>
			</table>
		</div>

	</div>
</div>
<hr />
<!-- <h4>Cấu hình khác</h4>
<div class="row"></div> -->
