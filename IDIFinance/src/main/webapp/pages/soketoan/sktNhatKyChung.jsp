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
					<td rowspan="${chungTu.soTkLonNhat+1}" class="text-center"
						style="width: 100px;"><fmt:formatDate
							value="${chungTu.ngayHt}" pattern="dd/M/yyyy" type="Date"
							dateStyle="SHORT" /></td>
					<td rowspan="${chungTu.soTkLonNhat+1}" class="text-center"
						style="width: 50px;">${chungTu.loaiCt}${chungTu.soCt}</td>
					<td>${chungTu.lyDo}</td>
					<c:choose>
						<c:when
							test="${chungTu.loaiCt==ChungTu.CHUNG_TU_PHIEU_THU || chungTu.loaiCt==ChungTu.CHUNG_TU_BAO_CO}">
							<td>${chungTu.taiKhoanNoDs[0].taiKhoan.maTk}</td>
							<td></td>
						</c:when>
						<c:when
							test="${chungTu.loaiCt==ChungTu.CHUNG_TU_PHIEU_CHI || chungTu.loaiCt==ChungTu.CHUNG_TU_BAO_NO}">
							<td></td>
							<td>${chungTu.taiKhoanCoDs[0].taiKhoan.maTk}</td>
						</c:when>
						<c:otherwise>
							<td></td>
							<td></td>
						</c:otherwise>
					</c:choose>
					<td><fmt:formatNumber value="${chungTu.soTien.giaTri}"
							maxFractionDigits="2"></fmt:formatNumber></td>
					<td rowspan="${chungTu.soTkLonNhat+1}"></td>
					<td rowspan="${chungTu.soTkLonNhat+1}">${chungTu.doiTuong.maDt}</td>
					<td rowspan="${chungTu.soTkLonNhat+1}"><fmt:formatDate
							value="${chungTu.ngayLap}" pattern="dd/M/yyyy" type="Date"
							dateStyle="SHORT" /></td>
					<td rowspan="${chungTu.soTkLonNhat+1}"><c:choose>
							<c:when
								test="${chungTu.doiTuong.loaiDt == DoiTuong.KHACH_VANG_LAI}">
								${chungTu.doiTuong.nguoiNop}
							</c:when>
							<c:otherwise>${chungTu.doiTuong.tenDt}
							</c:otherwise>
						</c:choose></td>
					<td rowspan="${chungTu.soTkLonNhat+1}">${chungTu.doiTuong.diaChi}</td>
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
										<td>${chungTu.taiKhoanCoDs[status.index].taiKhoan.maTk}</td>
										<td><fmt:formatNumber
												value="${chungTu.taiKhoanCoDs[status.index].soTien}"
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
										<td>${chungTu.taiKhoanNoDs[status.index].taiKhoan.maTk}</td>
										<td></td>
										<td><fmt:formatNumber
												value="${chungTu.taiKhoanNoDs[status.index].soTien}"
												maxFractionDigits="2"></fmt:formatNumber></td>
									</tr>
								</c:forEach>
							</c:when>
						</c:choose>
					</c:when>
				</c:choose>
			</c:forEach>
		</tbody>
	</table>
</div>