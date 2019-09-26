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

<h4>Sổ chi tiết công nợ ${fn:toLowerCase(loaiTaiKhoan.tenTk)}</h4>

<div>
	<span><i>Tài khoản ${mainFinanceForm.taiKhoan}. Từ <fmt:formatDate
				value="${mainFinanceForm.dau}" pattern="dd/M/yyyy" type="Date"
				dateStyle="SHORT" /> đến <fmt:formatDate
				value="${mainFinanceForm.cuoi}" pattern="dd/M/yyyy" type="Date"
				dateStyle="SHORT" /></i></span>
</div>


<div class="pull-right">
	<i>(*): Mặc định là tiền VND</i>
</div>

<div class="table-responsive">
	<table class="table table-bordered table-hover">
		<thead>
			<tr>
				<th class="text-center" colspan="2">Chứng từ</th>
				<th class="text-center" rowspan="2">Diễn dải</th>
				<th class="text-center" rowspan="2" style="width: 100px;">Tài
					khoản đối ứng</th>
				<th class="text-center" colspan="3">Số tiền (*)</th>
				<th class="text-center" rowspan="2" style="width: 100px;">Ghi
					chú</th>
			</tr>
			<tr>
				<th class="text-center" style="width: 100px;">Ngày ghi sổ</th>
				<th class="text-center" style="width: 50px;">Số</th>
				<th class="text-center">Nợ</th>
				<th class="text-center">Có</th>
				<th class="text-center">Tồn</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${duLieuKeToanDs}" var="duLieuKeToan">
				<%-- <tr>
					<td colspan="8"><b>Kỳ <fmt:formatDate
								value="${duLieuKeToan.kyKeToan.dau}" pattern="dd/M/yyyy"></fmt:formatDate>
							- <fmt:formatDate value="${duLieuKeToan.kyKeToan.cuoi}"
								pattern="dd/M/yyyy"></fmt:formatDate></b></td>
				</tr> --%>
				<c:forEach items="${duLieuKeToan.doiTuongDs}" var="doiTuong">
					<tr>
						<td colspan="8"><c:choose>
								<c:when test="${doiTuong.loaiDt==DoiTuong.KHACH_HANG}">
									<b>Khách hàng:&nbsp;${doiTuong.tenDt}</b>
								</c:when>
								<c:when test="${doiTuong.loaiDt==DoiTuong.NHA_CUNG_CAP}">
									<b>Nhà cung cấp:&nbsp;${doiTuong.tenDt}</b>
								</c:when>
								<c:when test="${doiTuong.loaiDt==DoiTuong.NHAN_VIEN}">
									<b>Nhân viên:&nbsp;${doiTuong.tenDt}</b>
								</c:when>
							</c:choose></td>
					</tr>
					<tr>
						<td></td>
						<td></td>
						<td><b>Số dư đầu kỳ</b></td>
						<td></td>
						<td class="text-right"><fmt:formatNumber
								value="${doiTuong.duLieuKeToan.noDauKy}" type="NUMBER"
								maxFractionDigits="0"></fmt:formatNumber></td>
						<td class="text-right"><fmt:formatNumber
								value="${doiTuong.duLieuKeToan.coDauKy}" type="NUMBER"
								maxFractionDigits="0"></fmt:formatNumber></td>
						<td></td>
						<td></td>
					</tr>
					<c:forEach items="${doiTuong.duLieuKeToan.nghiepVuKeToanDs}"
						var="nghiepVuKeToan">
						<tr>
							<td><fmt:formatDate value="${nghiepVuKeToan.chungTu.ngayHt}"
									pattern="dd/M/yyyy" type="Date" dateStyle="SHORT" /></td>
							<td><c:choose>
									<c:when
										test="${nghiepVuKeToan.chungTu.loaiCt==ChungTu.CHUNG_TU_PHIEU_THU}">
										<a
											href="${url}/chungtu/phieuthu/xem/${nghiepVuKeToan.chungTu.maCt}"
											target="_blank">${nghiepVuKeToan.chungTu.loaiCt}${nghiepVuKeToan.chungTu.soCt}</a>
									</c:when>
									<c:when
										test="${nghiepVuKeToan.chungTu.loaiCt==ChungTu.CHUNG_TU_PHIEU_CHI}">
										<a
											href="${url}/chungtu/phieuchi/xem/${nghiepVuKeToan.chungTu.maCt}"
											target="_blank">${nghiepVuKeToan.chungTu.loaiCt}${nghiepVuKeToan.chungTu.soCt}</a>
									</c:when>
									<c:when
										test="${nghiepVuKeToan.chungTu.loaiCt==ChungTu.CHUNG_TU_BAO_NO}">
										<a
											href="${url}/chungtu/baono/xem/${nghiepVuKeToan.chungTu.maCt}"
											target="_blank">${nghiepVuKeToan.chungTu.loaiCt}${nghiepVuKeToan.chungTu.soCt}</a>
									</c:when>
									<c:when
										test="${nghiepVuKeToan.chungTu.loaiCt==ChungTu.CHUNG_TU_BAO_CO}">
										<a
											href="${url}/chungtu/baoco/xem/${nghiepVuKeToan.chungTu.maCt}"
											target="_blank">${nghiepVuKeToan.chungTu.loaiCt}${nghiepVuKeToan.chungTu.soCt}</a>
									</c:when>
									<c:when
										test="${nghiepVuKeToan.chungTu.loaiCt==ChungTu.CHUNG_TU_KT_TH}">
										<a
											href="${url}/chungtu/ktth/xem/${nghiepVuKeToan.chungTu.maCt}"
											target="_blank">${nghiepVuKeToan.chungTu.loaiCt}${nghiepVuKeToan.chungTu.soCt}</a>
									</c:when>
									<c:when
										test="${nghiepVuKeToan.chungTu.loaiCt==ChungTu.CHUNG_TU_MUA_HANG}">
										<a
											href="${url}/chungtu/muahang/xem/${nghiepVuKeToan.chungTu.maCt}"
											target="_blank">${nghiepVuKeToan.chungTu.loaiCt}${nghiepVuKeToan.chungTu.soCt}</a>
									</c:when>
									<c:when
										test="${nghiepVuKeToan.chungTu.loaiCt==ChungTu.CHUNG_TU_BAN_HANG}">
										<a
											href="${url}/chungtu/banhang/xem/${nghiepVuKeToan.chungTu.maCt}"
											target="_blank">${nghiepVuKeToan.chungTu.loaiCt}${nghiepVuKeToan.chungTu.soCt}</a>
									</c:when>
									<c:when
										test="${nghiepVuKeToan.chungTu.loaiCt==ChungTu.CHUNG_TU_KET_CHUYEN}">
										<a
											href="${url}/chungtu/ketchuyen/xem/${nghiepVuKeToan.chungTu.maCt}"
											target="_blank">${nghiepVuKeToan.chungTu.loaiCt}${nghiepVuKeToan.chungTu.soCt}</a>
									</c:when>
									<c:otherwise>${nghiepVuKeToan.chungTu.loaiCt}${chungTu.soCt}</c:otherwise>
								</c:choose></td>
							<c:choose>
								<c:when
									test="${nghiepVuKeToan.taiKhoanNo.soTien.soTien >= nghiepVuKeToan.taiKhoanCo.soTien.soTien}">
									<td>${nghiepVuKeToan.taiKhoanCo.lyDo}</td>
									<c:if
										test="${nghiepVuKeToan.taiKhoanNo.loaiTaiKhoan.maTk.trim().length() >= mainFinanceForm.taiKhoan.trim().length()
										&& nghiepVuKeToan.taiKhoanNo.loaiTaiKhoan.maTk.trim().substring(0, mainFinanceForm.taiKhoan.trim().length()).equals(mainFinanceForm.taiKhoan.trim())}">
										<td>${nghiepVuKeToan.taiKhoanCo.loaiTaiKhoan.maTk}</td>
										<td class="text-right"><fmt:formatNumber
												value="${nghiepVuKeToan.taiKhoanCo.soTien.soTien*nghiepVuKeToan.chungTu.loaiTien.banRa}"
												type="NUMBER" maxFractionDigits="0"></fmt:formatNumber></td>
										<td class="text-right">0</td>
									</c:if>
									<c:if
										test="${nghiepVuKeToan.taiKhoanCo.loaiTaiKhoan.maTk.trim().length() >= mainFinanceForm.taiKhoan.trim().length()
										&& nghiepVuKeToan.taiKhoanCo.loaiTaiKhoan.maTk.trim().substring(0, mainFinanceForm.taiKhoan.trim().length()).equals(mainFinanceForm.taiKhoan.trim())}">
										<td>${nghiepVuKeToan.taiKhoanNo.loaiTaiKhoan.maTk}</td>
										<td class="text-right">0</td>
										<td class="text-right"><fmt:formatNumber
												value="${nghiepVuKeToan.taiKhoanCo.soTien.soTien*nghiepVuKeToan.chungTu.loaiTien.banRa}"
												type="NUMBER" maxFractionDigits="0"></fmt:formatNumber></td>
									</c:if>
									<td></td>
									<td></td>
								</c:when>
								<c:when
									test="${nghiepVuKeToan.taiKhoanCo.soTien.soTien > nghiepVuKeToan.taiKhoanNo.soTien.soTien}">
									<td>${nghiepVuKeToan.taiKhoanNo.lyDo}</td>
									<c:if
										test="${nghiepVuKeToan.taiKhoanNo.loaiTaiKhoan.maTk.trim().length() >= mainFinanceForm.taiKhoan.trim().length()
										&& nghiepVuKeToan.taiKhoanNo.loaiTaiKhoan.maTk.trim().substring(0, mainFinanceForm.taiKhoan.trim().length()).equals(mainFinanceForm.taiKhoan.trim())}">
										<td>${nghiepVuKeToan.taiKhoanCo.loaiTaiKhoan.maTk}</td>
										<td class="text-right"><fmt:formatNumber
												value="${nghiepVuKeToan.taiKhoanNo.soTien.soTien*nghiepVuKeToan.chungTu.loaiTien.banRa}"
												type="NUMBER" maxFractionDigits="0"></fmt:formatNumber></td>
										<td class="text-right">0</td>
									</c:if>
									<c:if
										test="${nghiepVuKeToan.taiKhoanCo.loaiTaiKhoan.maTk.trim().length() >= mainFinanceForm.taiKhoan.trim().length()
										&& nghiepVuKeToan.taiKhoanCo.loaiTaiKhoan.maTk.trim().substring(0, mainFinanceForm.taiKhoan.trim().length()).equals(mainFinanceForm.taiKhoan.trim())}">
										<td>${nghiepVuKeToan.taiKhoanNo.loaiTaiKhoan.maTk}</td>
										<td class="text-right">0</td>
										<td class="text-right"><fmt:formatNumber
												value="${nghiepVuKeToan.taiKhoanNo.soTien.soTien*nghiepVuKeToan.chungTu.loaiTien.banRa}"
												type="NUMBER" maxFractionDigits="0"></fmt:formatNumber></td>
									</c:if>
									<td></td>
									<td></td>
								</c:when>
							</c:choose>
						</tr>
					</c:forEach>
					<tr>
						<td></td>
						<td></td>
						<td><b>Tổng phát sinh trong kỳ</b></td>
						<td></td>
						<td class="text-right"><fmt:formatNumber
								value="${doiTuong.duLieuKeToan.tongNoPhatSinh}" type="NUMBER"
								maxFractionDigits="0"></fmt:formatNumber></td>
						<td class="text-right"><fmt:formatNumber
								value="${doiTuong.duLieuKeToan.tongCoPhatSinh}" type="NUMBER"
								maxFractionDigits="0"></fmt:formatNumber></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td></td>
						<td></td>
						<td><b>Số dư cuối kỳ</b></td>
						<td></td>
						<td class="text-right"><fmt:formatNumber
								value="${doiTuong.duLieuKeToan.noCuoiKy}" type="NUMBER"
								maxFractionDigits="0"></fmt:formatNumber></td>
						<td class="text-right"><fmt:formatNumber
								value="${doiTuong.duLieuKeToan.coCuoiKy}" type="NUMBER"
								maxFractionDigits="0"></fmt:formatNumber></td>
						<td></td>
						<td></td>
					</tr>
				</c:forEach>
			</c:forEach>
		</tbody>
	</table>
</div>