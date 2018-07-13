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
				<th class="text-center" colspan="2">Tài khoản</th>
				<th class="text-center" rowspan="2">Số tiền (*)</th>
				<th class="text-center" rowspan="2">Ghi chú</th>
				<th class="text-center" rowspan="2">Mã khách hàng</th>
				<th class="text-center" rowspan="2">Ngày thu/chi</th>
				<th class="text-center" colspan="2">Thông tin khách hàng</th>
			</tr>
			<tr>
				<th class="text-center">Ngày ghi sổ</th>
				<th class="text-center">Số</th>
				<th class="text-center">Nợ</th>
				<th class="text-center">Có</th>
				<th class="text-center">Người nhận - nộp tiền</th>
				<th class="text-center">Địa chỉ</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${chungTuDs}" var="chungTu" varStatus="status">
				<tr>
					<td rowspan="${chungTu.soDongNkc}" class="text-center"
						style="width: 90px;"><fmt:formatDate
							value="${chungTu.ngayHt}" pattern="dd/M/yyyy" type="Date"
							dateStyle="SHORT" /></td>
					<td rowspan="${chungTu.soDongNkc}" class="text-center"
						style="width: 50px;"><c:choose>
							<c:when test="${chungTu.loaiCt==ChungTu.CHUNG_TU_PHIEU_THU}">
								<a href="${url}/chungtu/phieuthu/xem/${chungTu.maCt}"
									target="_blank">${chungTu.loaiCt}${chungTu.soCt}</a>
							</c:when>
							<c:when test="${chungTu.loaiCt==ChungTu.CHUNG_TU_PHIEU_CHI}">
								<a href="${url}/chungtu/phieuchi/xem/${chungTu.maCt}"
									target="_blank">${chungTu.loaiCt}${chungTu.soCt}</a>
							</c:when>
							<c:when test="${chungTu.loaiCt==ChungTu.CHUNG_TU_BAO_NO}">
								<a href="${url}/chungtu/baono/xem/${chungTu.maCt}"
									target="_blank">${chungTu.loaiCt}${chungTu.soCt}</a>
							</c:when>
							<c:when test="${ chungTu.loaiCt==ChungTu.CHUNG_TU_BAO_CO}">
								<a href="${url}/chungtu/baoco/xem/${chungTu.maCt}"
									target="_blank">${chungTu.loaiCt}${chungTu.soCt}</a>
							</c:when>
							<c:when test="${chungTu.loaiCt==ChungTu.CHUNG_TU_KT_TH}">
								<a href="${url}/chungtu/ktth/xem/${chungTu.maCt}"
									target="_blank">${chungTu.loaiCt}${chungTu.soCt}</a>
							</c:when>
							<c:when test="${chungTu.loaiCt==ChungTu.CHUNG_TU_MUA_HANG}">
								<a href="${url}/chungtu/muahang/xem/${chungTu.maCt}"
									target="_blank">${chungTu.loaiCt}${chungTu.soCt}</a>
							</c:when>
							<c:when test="${chungTu.loaiCt==ChungTu.CHUNG_TU_BAN_HANG}">
								<a href="${url}/chungtu/banhang/xem/${chungTu.maCt}"
									target="_blank">${chungTu.loaiCt}${chungTu.soCt}</a>
							</c:when>
							<c:when test="${chungTu.loaiCt==ChungTu.CHUNG_TU_KET_CHUYEN}">
								<a href="${url}/chungtu/ketchuyen/xem/${chungTu.maCt}"
									target="_blank">${chungTu.loaiCt}${chungTu.soCt}</a>
							</c:when>
							<c:otherwise>${chungTu.loaiCt}${chungTu.soCt}</c:otherwise>
						</c:choose></td>
					<td style="color: blue;">${chungTu.lyDo}</td>
					<c:choose>
						<c:when
							test="${chungTu.loaiCt==ChungTu.CHUNG_TU_PHIEU_THU || chungTu.loaiCt==ChungTu.CHUNG_TU_BAO_CO}">
							<td>${chungTu.taiKhoanNoDs[0].loaiTaiKhoan.maTk}</td>
							<td></td>
							<td align="right" style="color: blue;"><fmt:formatNumber
									value="${chungTu.soTien.giaTri}" maxFractionDigits="2"></fmt:formatNumber></td>
						</c:when>
						<c:when
							test="${chungTu.loaiCt==ChungTu.CHUNG_TU_PHIEU_CHI || chungTu.loaiCt==ChungTu.CHUNG_TU_BAO_NO}">
							<td></td>
							<td>${chungTu.taiKhoanCoDs[0].loaiTaiKhoan.maTk}</td>
							<td align="right" style="color: blue;"><fmt:formatNumber
									value="${chungTu.soTien.giaTri}" maxFractionDigits="2"></fmt:formatNumber></td>
						</c:when>
						<c:when test="${chungTu.loaiCt==ChungTu.CHUNG_TU_MUA_HANG}">
							<td>${chungTu.tkKho.loaiTaiKhoan.maTk}</td>
							<td></td>
							<td align="right" style="color: blue;"><fmt:formatNumber
									value="${chungTu.tkKho.soTien.giaTri}" maxFractionDigits="2"></fmt:formatNumber></td>
						</c:when>
						<c:when test="${ chungTu.loaiCt==ChungTu.CHUNG_TU_BAN_HANG}">
							<td>${chungTu.tkGiaVon.loaiTaiKhoan.maTk}</td>
							<td>${chungTu.tkKho.loaiTaiKhoan.maTk}</td>
							<td align="right" style="color: blue;"><fmt:formatNumber
									value="${chungTu.tkKho.soTien.giaTri}" maxFractionDigits="2"></fmt:formatNumber></td>
						</c:when>
						<c:otherwise>
							<td></td>
							<td></td>
							<td align="right" style="color: blue;"></td>
						</c:otherwise>
					</c:choose>
					<td rowspan="${chungTu.soDongNkc}"></td>
					<td rowspan="${chungTu.soDongNkc}">${chungTu.doiTuong.maDt}</td>
					<td rowspan="${chungTu.soDongNkc}"><fmt:formatDate
							value="${chungTu.ngayLap}" pattern="dd/M/yyyy" type="Date"
							dateStyle="SHORT" /></td>
					<td rowspan="${chungTu.soDongNkc}"><c:choose>
							<c:when
								test="${chungTu.doiTuong.loaiDt == DoiTuong.KHACH_VANG_LAI}">
								${chungTu.doiTuong.nguoiNop}
							</c:when>
							<c:otherwise>${chungTu.doiTuong.tenDt}
							</c:otherwise>
						</c:choose></td>
					<td rowspan="${chungTu.soDongNkc}">${chungTu.doiTuong.diaChi}</td>
				</tr>
				<c:choose>
					<c:when
						test="${chungTu.loaiCt==ChungTu.CHUNG_TU_PHIEU_THU || chungTu.loaiCt==ChungTu.CHUNG_TU_BAO_CO}">
						<c:choose>
							<c:when test="${chungTu.soTkLonNhat>0}">
								<c:forEach begin="0" end="${chungTu.soTkLonNhat-1}"
									varStatus="status">
									<tr>
										<td align="right">${chungTu.taiKhoanCoDs[status.index].lyDo}</td>
										<td></td>
										<td>${chungTu.taiKhoanCoDs[status.index].loaiTaiKhoan.maTk}</td>
										<td align="right"><fmt:formatNumber
												value="${chungTu.taiKhoanCoDs[status.index].soTien.soTien*chungTu.loaiTien.banRa}"
												maxFractionDigits="2"></fmt:formatNumber></td>
									</tr>
								</c:forEach>
							</c:when>
						</c:choose>
					</c:when>
					<c:when
						test="${chungTu.loaiCt==ChungTu.CHUNG_TU_PHIEU_CHI || chungTu.loaiCt==ChungTu.CHUNG_TU_BAO_NO}">
						<c:choose>
							<c:when test="${chungTu.soTkLonNhat>0}">
								<c:forEach begin="0" end="${chungTu.soTkLonNhat-1}"
									varStatus="status">
									<tr>
										<td align="right">${chungTu.taiKhoanNoDs[status.index].lyDo}</td>
										<td>${chungTu.taiKhoanNoDs[status.index].loaiTaiKhoan.maTk}</td>
										<td></td>
										<td align="right"><fmt:formatNumber
												value="${chungTu.taiKhoanNoDs[status.index].soTien.soTien*chungTu.loaiTien.banRa}"
												maxFractionDigits="2"></fmt:formatNumber></td>
									</tr>
								</c:forEach>
							</c:when>
						</c:choose>
					</c:when>
					<c:when test="${chungTu.loaiCt==ChungTu.CHUNG_TU_KT_TH}">
						<c:forEach begin="0" end="${chungTu.taiKhoanNoDs.size()-1}"
							varStatus="status">
							<tr>
								<td align="right">${chungTu.taiKhoanNoDs[status.index].lyDo}</td>
								<td>${chungTu.taiKhoanNoDs[status.index].loaiTaiKhoan.maTk}</td>
								<td></td>
								<td align="right"><fmt:formatNumber
										value="${chungTu.taiKhoanNoDs[status.index].soTien.soTien*chungTu.loaiTien.banRa}"
										maxFractionDigits="2"></fmt:formatNumber></td>
							</tr>
						</c:forEach>
						<c:forEach begin="0" end="${chungTu.taiKhoanCoDs.size()-1}"
							varStatus="status">
							<tr>
								<td align="right">${chungTu.taiKhoanCoDs[status.index].lyDo}</td>
								<td></td>
								<td>${chungTu.taiKhoanCoDs[status.index].loaiTaiKhoan.maTk}</td>
								<td align="right"><fmt:formatNumber
										value="${chungTu.taiKhoanCoDs[status.index].soTien.soTien*chungTu.loaiTien.banRa}"
										maxFractionDigits="2"></fmt:formatNumber></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:when test="${chungTu.loaiCt==ChungTu.CHUNG_TU_MUA_HANG}">
						<tr>
							<td></td>
							<td></td>
							<td>${chungTu.tkThanhtoan.loaiTaiKhoan.maTk}</td>
							<td align="right" style="color: blue;"><fmt:formatNumber
									value="${chungTu.tkThanhtoan.soTien.giaTri}"
									maxFractionDigits="2"></fmt:formatNumber></td>
						</tr>
						<c:if
							test="${not empty chungTu.tkThueNk and not empty chungTu.tkThueNk.soTien and chungTu.tkThueNk.soTien.soTien>0}">
							<tr>
								<td></td>
								<td></td>
								<td>${chungTu.tkThueNk.loaiTaiKhoan.maTk}</td>
								<td align="right" style="color: blue;"><fmt:formatNumber
										value="${chungTu.tkThueNk.soTien.giaTri}"
										maxFractionDigits="2"></fmt:formatNumber></td>
							</tr>
						</c:if>
						<c:if
							test="${not empty chungTu.tkThueTtdb and not empty chungTu.tkThueTtdb.soTien and chungTu.tkThueTtdb.soTien.soTien>0}">
							<tr>
								<td></td>
								<td></td>
								<td>${chungTu.tkThueTtdb.loaiTaiKhoan.maTk}</td>
								<td align="right" style="color: blue;"><fmt:formatNumber
										value="${chungTu.tkThueTtdb.soTien.giaTri}"
										maxFractionDigits="2"></fmt:formatNumber></td>
							</tr>
						</c:if>
						<c:if
							test="${not empty chungTu.tkThueGtgt and not empty chungTu.tkThueGtgt.soTien and chungTu.tkThueGtgt.soTien.soTien>0}">
							<tr>
								<td></td>
								<td>${chungTu.tkThueGtgt.loaiTaiKhoan.maTk}</td>
								<td></td>
								<td align="right" style="color: blue;"><fmt:formatNumber
										value="${chungTu.tkThueGtgt.soTien.giaTri}"
										maxFractionDigits="2"></fmt:formatNumber></td>
							</tr>
						</c:if>
					</c:when>
					<c:when test="${chungTu.loaiCt==ChungTu.CHUNG_TU_BAN_HANG}">
						<tr>
							<td></td>
							<td>${chungTu.tkThanhtoan.loaiTaiKhoan.maTk}</td>
							<td></td>
							<td align="right" style="color: blue;"><fmt:formatNumber
									value="${chungTu.tkThanhtoan.soTien.giaTri}"
									maxFractionDigits="2"></fmt:formatNumber></td>
						</tr>
						<c:if
							test="${not empty chungTu.tkDoanhThu and not empty chungTu.tkDoanhThu.soTien and chungTu.tkDoanhThu.soTien.soTien>0}">
							<tr>
								<td></td>
								<td></td>
								<td>${chungTu.tkDoanhThu.loaiTaiKhoan.maTk}</td>
								<td align="right" style="color: blue;"><fmt:formatNumber
										value="${chungTu.tkDoanhThu.soTien.giaTri}"
										maxFractionDigits="2"></fmt:formatNumber></td>
							</tr>
						</c:if>
						<c:if
							test="${not empty chungTu.tkThueXk and not empty chungTu.tkThueXk.soTien and chungTu.tkThueXk.soTien.soTien>0}">
							<tr>
								<td></td>
								<td></td>
								<td>${chungTu.tkThueXk.loaiTaiKhoan.maTk}</td>
								<td align="right" style="color: blue;"><fmt:formatNumber
										value="${chungTu.tkThueXk.soTien.giaTri}"
										maxFractionDigits="2"></fmt:formatNumber></td>
							</tr>
						</c:if>
						<c:if
							test="${not empty chungTu.tkThueGtgt and not empty chungTu.tkThueGtgt.soTien and chungTu.tkThueGtgt.soTien.soTien>0}">
							<tr>
								<td></td>
								<td></td>
								<td>${chungTu.tkThueGtgt.loaiTaiKhoan.maTk}</td>
								<td align="right" style="color: blue;"><fmt:formatNumber
										value="${chungTu.tkThueGtgt.soTien.giaTri}"
										maxFractionDigits="2"></fmt:formatNumber></td>
							</tr>
						</c:if>
					</c:when>
					<c:when test="${chungTu.loaiCt==ChungTu.CHUNG_TU_KET_CHUYEN}">
						<c:forEach items="${chungTu.kcbtDs}" var="kcbt">
							<c:if
								test="${kcbt.taiKhoanNo.soTien.soTien>0 and kcbt.taiKhoanCo.soTien.soTien>0}">
								<tr>
									<td></td>
									<td>${kcbt.taiKhoanNo.loaiTaiKhoan.maTk}</td>
									<td>${kcbt.taiKhoanCo.loaiTaiKhoan.maTk}</td>
									<td align="right" style="color: blue;"><fmt:formatNumber
											value="${kcbt.taiKhoanNo.soTien.giaTri}"
											maxFractionDigits="2"></fmt:formatNumber></td>
								</tr>
							</c:if>
						</c:forEach>
					</c:when>
				</c:choose>
			</c:forEach>
		</tbody>
	</table>
</div>