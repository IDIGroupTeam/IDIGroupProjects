<%@page import="com.idi.finance.bean.chungtu.ChungTu"%>
<%@page import="com.idi.finance.bean.doituong.DoiTuong"%>
<%@page import="com.idi.finance.bean.taikhoan.LoaiTaiKhoan"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

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

<h4>Sổ nhật ký chung</h4>

<p>
	<i>Từ <fmt:formatDate value="${mainFinanceForm.dau}"
			pattern="dd/M/yyyy" type="Date" dateStyle="SHORT" /> đến <fmt:formatDate
			value="${mainFinanceForm.cuoi}" pattern="dd/M/yyyy" type="Date"
			dateStyle="SHORT" /></i>
</p>

<div class="pull-right">
	<i>(*): Mặc định là tiền VND</i>
</div>

<div class="table-responsive">
	<table class="table table-bordered table-hover">
		<thead>
			<tr>
				<th class="text-center" colspan="2">Phiếu thu</th>
				<th class="text-center" rowspan="2" style="width: 300px;">Diễn
					dải</th>
				<th class="text-center" rowspan="2">Tài khoản</th>
				<th class="text-center" colspan="2">Số tiền (*)</th>
				<th class="text-center" rowspan="2">Nhóm</th>
				<th class="text-center" rowspan="2">Ngày thu/chi</th>
				<th class="text-center" rowspan="2">Đối tượng</th>
				<th class="text-center" rowspan="2">Địa chỉ</th>
			</tr>
			<tr>
				<th class="text-center">Ngày ghi sổ</th>
				<th class="text-center">Số</th>
				<th class="text-center">Nợ</th>
				<th class="text-center">Có</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${taiKhoanDs}" var="taiKhoan">
				<tr>
					<td class="text-center"><fmt:formatDate
							value="${taiKhoan.chungTu.ngayHt}" pattern="dd/M/yyyy"
							type="Date" dateStyle="SHORT" /></td>
					<td class="text-center" style="width: 50px;"><c:choose>
							<c:when
								test="${taiKhoan.chungTu.loaiCt==ChungTu.CHUNG_TU_PHIEU_THU}">
								<a href="${url}/chungtu/phieuthu/xem/${taiKhoan.chungTu.maCt}"
									target="_blank">${taiKhoan.chungTu.loaiCt}${taiKhoan.chungTu.soCt}</a>
							</c:when>
							<c:when
								test="${taiKhoan.chungTu.loaiCt==ChungTu.CHUNG_TU_PHIEU_CHI}">
								<a href="${url}/chungtu/phieuchi/xem/${taiKhoan.chungTu.maCt}"
									target="_blank">${taiKhoan.chungTu.loaiCt}${taiKhoan.chungTu.soCt}</a>
							</c:when>
							<c:when
								test="${taiKhoan.chungTu.loaiCt==ChungTu.CHUNG_TU_BAO_NO}">
								<a href="${url}/chungtu/baono/xem/${taiKhoan.chungTu.maCt}"
									target="_blank">${taiKhoan.chungTu.loaiCt}${taiKhoan.chungTu.soCt}</a>
							</c:when>
							<c:when
								test="${ taiKhoan.chungTu.loaiCt==ChungTu.CHUNG_TU_BAO_CO}">
								<a href="${url}/chungtu/baoco/xem/${taiKhoan.chungTu.maCt}"
									target="_blank">${taiKhoan.chungTu.loaiCt}${taiKhoan.chungTu.soCt}</a>
							</c:when>
							<c:when test="${taiKhoan.chungTu.loaiCt==ChungTu.CHUNG_TU_KT_TH}">
								<a href="${url}/chungtu/ktth/xem/${taiKhoan.chungTu.maCt}"
									target="_blank">${taiKhoan.chungTu.loaiCt}${taiKhoan.chungTu.soCt}</a>
							</c:when>
							<c:when
								test="${taiKhoan.chungTu.loaiCt==ChungTu.CHUNG_TU_MUA_HANG}">
								<a href="${url}/chungtu/muahang/xem/${taiKhoan.chungTu.maCt}"
									target="_blank">${taiKhoan.chungTu.loaiCt}${taiKhoan.chungTu.soCt}</a>
							</c:when>
							<c:when
								test="${taiKhoan.chungTu.loaiCt==ChungTu.CHUNG_TU_BAN_HANG}">
								<a href="${url}/chungtu/banhang/xem/${taiKhoan.chungTu.maCt}"
									target="_blank">${taiKhoan.chungTu.loaiCt}${taiKhoan.chungTu.soCt}</a>
							</c:when>
							<c:when
								test="${taiKhoan.chungTu.loaiCt==ChungTu.CHUNG_TU_KET_CHUYEN}">
								<a href="${url}/chungtu/ketchuyen/xem/${taiKhoan.chungTu.maCt}"
									target="_blank">${taiKhoan.chungTu.loaiCt}${taiKhoan.chungTu.soCt}</a>
							</c:when>
							<c:otherwise>${taiKhoan.chungTu.loaiCt}${taiKhoan.chungTu.soCt}</c:otherwise>
						</c:choose></td>
					<td>${taiKhoan.chungTu.lyDo}</td>
					<td class="text-center">${taiKhoan.loaiTaiKhoan.maTk}</td>
					<c:choose>
						<c:when test="${taiKhoan.chungTu.loaiCt==ChungTu.CHUNG_TU_KT_TH}">
							<c:choose>
								<c:when test="${taiKhoan.soDu==LoaiTaiKhoan.NO}">
									<td class="text-right"><fmt:formatNumber
											value="${taiKhoan.no.giaTri}" maxFractionDigits="2"></fmt:formatNumber></td>
									<td class="text-right">0</td>
								</c:when>
								<c:otherwise>
									<td class="text-right">0</td>
									<td class="text-right"><fmt:formatNumber
											value="${taiKhoan.co.giaTri}" maxFractionDigits="2"></fmt:formatNumber></td>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${taiKhoan.soDu==LoaiTaiKhoan.NO}">
									<td class="text-right"><fmt:formatNumber
											value="${taiKhoan.soTien.giaTri}" maxFractionDigits="2"></fmt:formatNumber></td>
									<td class="text-right">0</td>
								</c:when>
								<c:otherwise>
									<td class="text-right">0</td>
									<td class="text-right"><fmt:formatNumber
											value="${taiKhoan.soTien.giaTri}" maxFractionDigits="2"></fmt:formatNumber></td>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
					<td class="text-center">${taiKhoan.nhomDk}</td>
					<td class="text-center"><fmt:formatDate
							value="${taiKhoan.chungTu.ngayLap}" pattern="dd/M/yyyy"
							type="Date" dateStyle="SHORT" /></td>
					<c:choose>
						<c:when test="${taiKhoan.chungTu.loaiCt==ChungTu.CHUNG_TU_KT_TH}">
							<td>${taiKhoan.doiTuong.tenDt}</td>
							<td>${taiKhoan.doiTuong.diaChi}</td>
						</c:when>
						<c:otherwise>
							<td>${taiKhoan.chungTu.doiTuong.tenDt}</td>
							<td>${taiKhoan.chungTu.doiTuong.diaChi}</td>
						</c:otherwise>
					</c:choose>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>