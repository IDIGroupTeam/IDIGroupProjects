<%@page import="com.idi.finance.bean.chungtu.ChungTu"%>
<%@page import="com.idi.finance.bean.chungtu.DoiTuong"%>
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

<h4>Sổ tổng hợp công nợ phải thu/trả</h4>
<p>
	<i>Tài khoản ${mainFinanceForm.taiKhoan}. Từ <fmt:formatDate
			value="${mainFinanceForm.dau}" pattern="dd/M/yyyy" type="Date"
			dateStyle="SHORT" /> đến <fmt:formatDate
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
				<th class="text-center" colspan="2">Chứng từ</th>
				<th class="text-center" rowspan="2">Diễn dải</th>
				<th class="text-center" rowspan="2" style="width: 100px;">Tài
					khoản đối ứng</th>
				<th class="text-center" colspan="2">Số tiền (*)</th>
				<th class="text-center" rowspan="2" style="width: 100px;">Ghi
					chú</th>
			</tr>
			<tr>
				<th class="text-center" style="width: 100px;">Ngày ghi sổ</th>
				<th class="text-center" style="width: 50px;">Số</th>
				<th class="text-center">Nợ</th>
				<th class="text-center">Có</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td></td>
				<td></td>
				<td><b>Số dư đầu</b></td>
				<td></td>
				<c:choose>
					<c:when test="${soDuDau>=0}">
						<td class="text-right"><fmt:formatNumber value="${soDuDau}"
								type="NUMBER"></fmt:formatNumber></td>
						<td class="text-right">0</td>
					</c:when>
					<c:otherwise>
						<td class="text-right">0</td>
						<td class="text-right"><fmt:formatNumber value="${0-soDuDau}"
								type="NUMBER"></fmt:formatNumber></td>
					</c:otherwise>
				</c:choose>
				<td></td>
			</tr>
			<c:forEach items="${duLieuKeToanDs}" var="duLieuKeToan">
				<tr>
					<td></td>
					<td></td>
					<td colspan="5"><b>Kỳ <fmt:formatDate
								value="${duLieuKeToan.kyKeToan.dau}" pattern="dd/M/yyyy"></fmt:formatDate>
							- <fmt:formatDate value="${duLieuKeToan.kyKeToan.cuoi}"
								pattern="dd/M/yyyy"></fmt:formatDate></b></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td><i>Số dư đầu kỳ</i></td>
					<td></td>
					<c:choose>
						<c:when test="${duLieuKeToan.soDuDauKy>=0}">
							<td class="text-right"><fmt:formatNumber
									value="${duLieuKeToan.soDuDauKy}" type="NUMBER"></fmt:formatNumber></td>
							<td class="text-right">0</td>
						</c:when>
						<c:otherwise>
							<td class="text-right">0</td>
							<td class="text-right"><fmt:formatNumber
									value="${0-duLieuKeToan.soDuDauKy}" type="NUMBER"></fmt:formatNumber></td>
						</c:otherwise>
					</c:choose>
					<td></td>
				</tr>
				<c:forEach items="${duLieuKeToan.nghiepVuKeToanDs}"
					var="nghiepVuKeToan">
					<tr>
						<td><fmt:formatDate value="${nghiepVuKeToan.chungTu.ngayHt}"
								pattern="dd/M/yyyy" type="Date" dateStyle="SHORT" /></td>
						<td><c:choose>
								<c:when
									test="${nghiepVuKeToan.chungTu.loaiCt==ChungTu.CHUNG_TU_PHIEU_THU}">
									<a
										href="${url}/chungtu/phieuthu/xem/${nghiepVuKeToan.chungTu.maCt}">${nghiepVuKeToan.chungTu.loaiCt}${nghiepVuKeToan.chungTu.soCt}</a>
								</c:when>
								<c:when
									test="${nghiepVuKeToan.chungTu.loaiCt==ChungTu.CHUNG_TU_PHIEU_CHI}">
									<a
										href="${url}/chungtu/phieuchi/xem/${nghiepVuKeToan.chungTu.maCt}">${nghiepVuKeToan.chungTu.loaiCt}${nghiepVuKeToan.chungTu.soCt}</a>
								</c:when>
								<c:when
									test="${nghiepVuKeToan.chungTu.loaiCt==ChungTu.CHUNG_TU_BAO_NO}">
									<a
										href="${url}/chungtu/baono/xem/${nghiepVuKeToan.chungTu.maCt}">${nghiepVuKeToan.chungTu.loaiCt}${nghiepVuKeToan.chungTu.soCt}</a>
								</c:when>
								<c:when
									test="${nghiepVuKeToan.chungTu.loaiCt==ChungTu.CHUNG_TU_BAO_CO}">
									<a
										href="${url}/chungtu/baoco/xem/${nghiepVuKeToan.chungTu.maCt}">${nghiepVuKeToan.chungTu.loaiCt}${nghiepVuKeToan.chungTu.soCt}</a>
								</c:when>
								<c:when
									test="${nghiepVuKeToan.chungTu.loaiCt==ChungTu.CHUNG_TU_KT_TH}">
									<a
										href="${url}/chungtu/ktth/xem/${nghiepVuKeToan.chungTu.maCt}">${nghiepVuKeToan.chungTu.loaiCt}${nghiepVuKeToan.chungTu.soCt}</a>
								</c:when>
								<c:otherwise>${nghiepVuKeToan.chungTu.loaiCt}${chungTu.soCt}</c:otherwise>
							</c:choose></td>
						<c:choose>
							<c:when
								test="${nghiepVuKeToan.chungTu.loaiCt==ChungTu.CHUNG_TU_PHIEU_THU || nghiepVuKeToan.chungTu.loaiCt==ChungTu.CHUNG_TU_BAO_CO}">
								<td>${nghiepVuKeToan.taiKhoanCo.lyDo}</td>
								<td>${nghiepVuKeToan.taiKhoanCo.loaiTaiKhoan.maTk}</td>
								<td class="text-right"><fmt:formatNumber
										value="${nghiepVuKeToan.taiKhoanCo.soTien.soTien*nghiepVuKeToan.chungTu.loaiTien.banRa}"
										type="NUMBER"></fmt:formatNumber></td>
								<td class="text-right">0</td>
								<td></td>
							</c:when>
							<c:when
								test="${nghiepVuKeToan.chungTu.loaiCt==ChungTu.CHUNG_TU_PHIEU_CHI || nghiepVuKeToan.chungTu.loaiCt==ChungTu.CHUNG_TU_BAO_NO}">
								<td>${nghiepVuKeToan.taiKhoanNo.lyDo}</td>
								<td>${nghiepVuKeToan.taiKhoanNo.loaiTaiKhoan.maTk}</td>
								<td class="text-right">0</td>
								<td class="text-right"><fmt:formatNumber
										value="${nghiepVuKeToan.taiKhoanNo.soTien.soTien*nghiepVuKeToan.chungTu.loaiTien.banRa}"
										type="NUMBER"></fmt:formatNumber></td>
								<td></td>
							</c:when>
							<c:when
								test="${nghiepVuKeToan.chungTu.loaiCt==ChungTu.CHUNG_TU_KT_TH}">
								<c:choose>
									<c:when test="${not empty nghiepVuKeToan.taiKhoanNo}">
										<td>${nghiepVuKeToan.taiKhoanNo.lyDo}</td>
										<td>${nghiepVuKeToan.taiKhoanNo.loaiTaiKhoan.maTk}</td>
										<td class="text-right"><fmt:formatNumber
												value="${nghiepVuKeToan.taiKhoanNo.soTien.soTien*nghiepVuKeToan.chungTu.loaiTien.banRa}"
												type="NUMBER"></fmt:formatNumber></td>
										<td class="text-right">0</td>
										<td></td>
									</c:when>
									<c:otherwise>
										<td>${nghiepVuKeToan.taiKhoanCo.lyDo}</td>
										<td>${nghiepVuKeToan.taiKhoanCo.loaiTaiKhoan.maTk}</td>
										<td class="text-right">0</td>
										<td class="text-right"><fmt:formatNumber
												value="${nghiepVuKeToan.taiKhoanCo.soTien.soTien*nghiepVuKeToan.chungTu.loaiTien.banRa}"
												type="NUMBER"></fmt:formatNumber></td>
										<td></td>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</c:otherwise>
						</c:choose>
					</tr>
				</c:forEach>
				<tr>
					<td></td>
					<td></td>
					<td><i>Tổng phát sinh trong kỳ</i></td>
					<td></td>
					<td class="text-right"><fmt:formatNumber
							value="${duLieuKeToan.tongNoPhatSinh}" type="NUMBER"></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber
							value="${duLieuKeToan.tongCoPhatSinh}" type="NUMBER"></fmt:formatNumber></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td><i>Số dư cuối kỳ</i></td>
					<td></td>
					<c:choose>
						<c:when test="${duLieuKeToan.soDuCuoiKy>=0}">
							<td class="text-right"><fmt:formatNumber
									value="${duLieuKeToan.soDuCuoiKy}" type="NUMBER"></fmt:formatNumber></td>
							<td class="text-right">0</td>
						</c:when>
						<c:otherwise>
							<td class="text-right">0</td>
							<td class="text-right"><fmt:formatNumber
									value="${0-duLieuKeToan.soDuCuoiKy}" type="NUMBER"></fmt:formatNumber></td>
						</c:otherwise>
					</c:choose>
					<td></td>
				</tr>
			</c:forEach>
			<tr>
				<td></td>
				<td></td>
				<td><b>Tổng phát sinh</b></td>
				<td></td>
				<td class="text-right"><fmt:formatNumber value="${noPhatSinh}"
						type="NUMBER"></fmt:formatNumber></td>
				<td class="text-right"><fmt:formatNumber value="${coPhatSinh}"
						type="NUMBER"></fmt:formatNumber></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td><b>Số dư cuối</b></td>
				<td></td>
				<c:choose>
					<c:when test="${soDuCuoi>=0}">
						<td class="text-right"><fmt:formatNumber value="${soDuCuoi}"
								type="NUMBER"></fmt:formatNumber></td>
						<td class="text-right">0</td>
					</c:when>
					<c:otherwise>
						<td class="text-right">0</td>
						<td class="text-right"><fmt:formatNumber
								value="${0-soDuCuoi}" type="NUMBER"></fmt:formatNumber></td>
					</c:otherwise>
				</c:choose>
				<td></td>
			</tr>
		</tbody>
	</table>
</div>