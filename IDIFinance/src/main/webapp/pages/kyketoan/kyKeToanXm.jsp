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
					số dư</a>
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
		<li class="active"><a data-toggle="tab" href="#tienMat">Tài
				khoản tiền mặt</a></li>
		<li><a data-toggle="tab" href="#tienGui">Tài khoản gửi ngân
				hàng</a></li>
		<li><a data-toggle="tab" href="#ccdv">Tài khoản CCDV, vật tư,
				hàng hóa</a></li>
		<li><a data-toggle="tab" href="#dt">Tài khoản theo đối tượng</a></li>
	</ul>

	<div class="tab-content table-responsive sub-content">
		<div id="tienMat" class="tab-pane fade in active">
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
					<c:forEach items="${tienMatDs}" var="tienMat">
						<tr>
							<td>${tienMat.loaiTaiKhoan.maTk}</td>
							<td>${tienMat.loaiTaiKhoan.tenTk}</td>
							<td class="text-right"><fmt:formatNumber
									value="${tienMat.noDauKy}" maxFractionDigits="2"></fmt:formatNumber></td>
							<td class="text-right"><fmt:formatNumber
									value="${tienMat.coDauKy}" maxFractionDigits="2"></fmt:formatNumber></td>
							<td class="text-right"><fmt:formatNumber
									value="${tienMat.noCuoiKy}" maxFractionDigits="2"></fmt:formatNumber></td>
							<td class="text-right"><fmt:formatNumber
									value="${tienMat.coCuoiKy}" maxFractionDigits="2"></fmt:formatNumber></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="tienGui" class="tab-pane fade">
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
					<c:forEach items="${tienGuiDs}" var="tienGui">
						<tr>
							<td>${tienGui.loaiTaiKhoan.maTk}</td>
							<td>${tienGui.loaiTaiKhoan.tenTk}</td>
							<td class="text-right"><fmt:formatNumber
									value="${tienGui.noDauKy}" maxFractionDigits="2"></fmt:formatNumber></td>
							<td class="text-right"><fmt:formatNumber
									value="${tienGui.coDauKy}" maxFractionDigits="2"></fmt:formatNumber></td>
							<td class="text-right"><fmt:formatNumber
									value="${tienGui.noCuoiKy}" maxFractionDigits="2"></fmt:formatNumber></td>
							<td class="text-right"><fmt:formatNumber
									value="${tienGui.coCuoiKy}" maxFractionDigits="2"></fmt:formatNumber></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="ccdv" class="tab-pane fade">
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
		<div id="dt" class="tab-pane fade">
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