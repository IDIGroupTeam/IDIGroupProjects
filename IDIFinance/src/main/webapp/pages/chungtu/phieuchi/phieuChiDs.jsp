<%@page import="com.idi.finance.bean.chungtu.DoiTuong"%>
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

	});
</script>

<h4>Danh mục phiếu chi</h4>
<p>
	<i>Ghi có vào các tài khoản tiền mặt: 111, 1111, ...</i>
</p>

<div class="pull-right">
	<i>(*): Mặc định là tiền VND</i>&nbsp;&nbsp;&nbsp;&nbsp; <a
		href="${url}/taomoiphieuchi" class="btn btn-info btn-sm"> <span
		class="glyphicon glyphicon-plus"></span> Tạo mới
	</a>
</div>
<br />
<br />

<div class="table-responsive">
	<table class="table table-bordered table-hover">
		<thead>
			<tr>
				<th class="text-center" colspan="2">Phiếu chi</th>
				<th class="text-center" rowspan="2">Lý do</th>
				<th class="text-center" rowspan="2">Tổng số tiền (*)</th>
				<th class="text-center" rowspan="2">Đối tượng nhận</th>
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
			<c:forEach items="${phieuChiDs}" var="phieuChi" varStatus="status">
				<tr>
					<td class="text-center" style="width: 50px;"><fmt:formatDate
							value="${phieuChi.ngayHt}" pattern="dd/M/yyyy" type="Date"
							dateStyle="SHORT" /></td>
					<td class="text-center" style="width: 50px;">${phieuChi.loaiCt}${phieuChi.soCt}</td>
					<td><a href="${url}/xemphieuchi/${phieuChi.maCt}">${phieuChi.lyDo}</a></td>
					<td align="right"><fmt:formatNumber value="${phieuChi.soTien.giaTri}"
							maxFractionDigits="2"></fmt:formatNumber></td>
					<td><c:choose>
							<c:when
								test="${phieuChi.doiTuong.loaiDt == DoiTuong.KHACH_VANG_LAI}">
								${phieuChi.doiTuong.nguoiNop}
							</c:when>
							<c:otherwise>${phieuChi.doiTuong.tenDt}
							</c:otherwise>
						</c:choose></td>
					<td>${phieuChi.doiTuong.diaChi}</td>
					<td>${phieuChi.doiTuong.maThue}</td>
					<td><c:choose>
							<c:when test="${phieuChi.doiTuong.loaiDt == DoiTuong.NHAN_VIEN}">
								Nhân viên
							</c:when>
							<c:when test="${phieuChi.doiTuong.loaiDt == DoiTuong.KHACH_HANG}">
								Khách hàng
							</c:when>
							<c:when
								test="${phieuChi.doiTuong.loaiDt == DoiTuong.NHA_CUNG_CAP}">
								Nhà cung cấp
							</c:when>
							<c:when
								test="${phieuChi.doiTuong.loaiDt == DoiTuong.KHACH_VANG_LAI}">
								Khách vãng lai
							</c:when>
						</c:choose></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>