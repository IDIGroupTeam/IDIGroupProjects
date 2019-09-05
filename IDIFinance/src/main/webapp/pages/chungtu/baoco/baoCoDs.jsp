<%@page import="com.idi.finance.bean.kyketoan.KyKeToan"%>
<%@page import="com.idi.finance.bean.doituong.DoiTuong"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<script type="text/javascript">
	//Shorthand for $( document ).ready()
	$(function() {
		// Khởi tạo action/method cho mainFinanceForm form
		$('#dataTable').DataTable({
			ordering : false,
			language : vi
		});
	});
</script>

<h4>Danh mục báo có</h4>
<p>
	<i>Ghi nợ vào các tài khoản tiền gửi ngân hàng: 112, 1121, ...</i>
</p>

<c:choose>
	<c:when test="${kyKeToan.trangThai== KyKeToan.DONG}">
		<div class="pull-left">
			<i>Kỳ kế toán hiện tại bị đóng, bạn chỉ xem, không thể thêm mới
				hoặc sửa dữ liệu.</i>
		</div>
		<div class="pull-right">
			<i>(*): Mặc định là tiền VND</i>&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
	</c:when>
	<c:otherwise>
		<div class="pull-right">
			<i>(*): Mặc định là tiền VND</i>&nbsp;&nbsp;&nbsp;&nbsp;
			<c:choose>
				<c:when test="${kyKeToan.trangThai!= KyKeToan.DONG}">
					<a href="${url}/chungtu/baoco/taomoi" class="btn btn-info btn-sm">
						<span class="glyphicon glyphicon-plus"></span> Tạo mới
					</a>
				</c:when>
			</c:choose>
		</div>
	</c:otherwise>
</c:choose>
<br />
<br />
<div class="table-responsive">
	<table id="dataTable" class="table table-bordered table-hover">
		<thead>
			<tr>
				<th class="text-center" colspan="2">Báo có</th>
				<th class="text-center" rowspan="2">Lý do</th>
				<th class="text-center" rowspan="2">Tổng số tiền (*)</th>
				<th class="text-center" rowspan="2">Đối tượng</th>
				<th class="text-center" rowspan="2">Địa chỉ</th>
				<th class="text-center" rowspan="2">Mã số thuế</th>
				<th class="text-center" rowspan="2">Loại đối tượng</th>
			</tr>
			<tr>
				<th class="text-center">Ngày ghi sổ</th>
				<th class="text-center">Số</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${baoCoDs}" var="baoCo" varStatus="status">
				<tr>
					<td class="text-center" style="width: 50px;"><fmt:formatDate
							value="${baoCo.ngayHt}" pattern="dd/M/yyyy" type="Date"
							dateStyle="SHORT" /></td>
					<td class="text-center" style="width: 50px;">${baoCo.loaiCt}${baoCo.soCt}</td>
					<td><a href="${url}/chungtu/baoco/xem/${baoCo.maCt}">${baoCo.lyDo}</a></td>
					<td align="right"><fmt:formatNumber
							value="${baoCo.soTien.giaTri}" maxFractionDigits="0"></fmt:formatNumber></td>
					<td><c:choose>
							<c:when
								test="${baoCo.doiTuong.loaiDt == DoiTuong.KHACH_VANG_LAI}">
								${baoCo.doiTuong.nguoiNop}
							</c:when>
							<c:otherwise>${baoCo.doiTuong.tenDt}
							</c:otherwise>
						</c:choose></td>
					<td>${baoCo.doiTuong.diaChi}</td>
					<td>${baoCo.doiTuong.maThue}</td>
					<td><c:choose>
							<c:when test="${baoCo.doiTuong.loaiDt == DoiTuong.NHAN_VIEN}">
								Nhân viên
							</c:when>
							<c:when test="${baoCo.doiTuong.loaiDt == DoiTuong.KHACH_HANG}">
								Khách hàng
							</c:when>
							<c:when test="${baoCo.doiTuong.loaiDt == DoiTuong.NHA_CUNG_CAP}">
								Nhà cung cấp
							</c:when>
							<c:when
								test="${baoCo.doiTuong.loaiDt == DoiTuong.KHACH_VANG_LAI}">
								Khách vãng lai
							</c:when>
						</c:choose></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>