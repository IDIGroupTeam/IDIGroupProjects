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

<h4>Danh mục báo nợ</h4>
<p>
	<i>Ghi có vào các tài khoản tiền gửi ngân hàng: 112, 1121, ...</i>
</p>

<div class="pull-right">
	<i>(*): Mặc định là tiền VND</i>&nbsp;&nbsp;&nbsp;&nbsp; <a
		href="${url}/taomoibaono" class="btn btn-info btn-sm"> <span
		class="glyphicon glyphicon-plus"></span> Tạo mới
	</a>
</div>
<br />
<br />

<div class="table-responsive">
	<table class="table table-bordered table-hover">
		<thead>
			<tr>
				<th class="text-center" colspan="2">Báo nợ</th>
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
			<c:forEach items="${baoNoDs}" var="baoNo" varStatus="status">
				<tr>
					<td class="text-center" style="width: 50px;"><fmt:formatDate
							value="${baoNo.ngayHt}" pattern="dd/M/yyyy" type="Date"
							dateStyle="SHORT" /></td>
					<td class="text-center" style="width: 50px;">${baoNo.loaiCt}${baoNo.soCt}</td>
					<td><a href="${url}/xembaono/${baoNo.maCt}">${baoNo.lyDo}</a></td>
					<td align="right"><fmt:formatNumber value="${baoNo.soTien.giaTri}"
							maxFractionDigits="2"></fmt:formatNumber></td>
					<td><c:choose>
							<c:when
								test="${baoNo.doiTuong.loaiDt == DoiTuong.KHACH_VANG_LAI}">
								${baoNo.doiTuong.nguoiNop}
							</c:when>
							<c:otherwise>${baoNo.doiTuong.tenDt}
							</c:otherwise>
						</c:choose></td>
					<td>${baoNo.doiTuong.diaChi}</td>
					<td>${baoNo.doiTuong.maThue}</td>
					<td><c:choose>
							<c:when test="${baoNo.doiTuong.loaiDt == DoiTuong.NHAN_VIEN}">
								Nhân viên
							</c:when>
							<c:when test="${baoNo.doiTuong.loaiDt == DoiTuong.KHACH_HANG}">
								Khách hàng
							</c:when>
							<c:when test="${baoNo.doiTuong.loaiDt == DoiTuong.NHA_CUNG_CAP}">
								Nhà cung cấp
							</c:when>
							<c:when
								test="${baoNo.doiTuong.loaiDt == DoiTuong.KHACH_VANG_LAI}">
								Khách vãng lai
							</c:when>
						</c:choose></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>