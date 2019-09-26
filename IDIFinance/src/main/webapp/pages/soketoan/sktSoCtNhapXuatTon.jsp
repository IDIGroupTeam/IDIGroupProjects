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

<h4>Sổ chi tiết nhập xuất tồn</h4>
<div class="row">
	<div class="col-sm-4 text-left">
		<i>156 - <c:forEach items="${mainFinanceForm.khoDs}" var="kho">${kho.tenKho}, </c:forEach>
			- ${mainFinanceForm.hangHoa.tenHh}
		</i>
	</div>
	<div class="col-sm-4 text-center">
		<i>Từ <fmt:formatDate value="${mainFinanceForm.dau}"
				pattern="dd/M/yyyy" type="Date" dateStyle="SHORT" /> đến <fmt:formatDate
				value="${mainFinanceForm.cuoi}" pattern="dd/M/yyyy" type="Date"
				dateStyle="SHORT" /></i>
	</div>
	<div class="col-sm-4 text-right">
		<i>(*): Mặc định là tiền VND</i>
	</div>
</div>

<div class="table-responsive">
	<table class="table table-bordered table-hover">
		<thead>
			<tr>
				<th colspan="2" class="text-center">Chứng từ</th>
				<th rowspan="2" class="text-center">Diễn dải</th>
				<th rowspan="2" class="text-center">Tài khoản <br />đối ứng
				</th>
				<th rowspan="2" class="text-center">Đơn giá/ <br />Giá vốn
				</th>
				<th colspan="2" class="text-center">Nhập trong kỳ</th>
				<th colspan="2" class="text-center">Xuất trong kỳ</th>
				<th colspan="2" class="text-center">Tồn cuối kỳ</th>
				<!-- <th rowspan="2" class="text-center">Ghi chú</th> -->
			</tr>
			<tr>
				<th class="text-center">Số CT</th>
				<th class="text-center">Ngày HT</th>
				<th class="text-center">Số lượng</th>
				<th class="text-center">Thành tiền</th>
				<th class="text-center">Số lượng</th>
				<th class="text-center">Thành tiền</th>
				<th class="text-center">Số lượng</th>
				<th class="text-center">Thành tiền</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${duLieuKeToanDs}" var="duLieuKeToan">
				<%-- <tr>
					<td colspan="11"><b>Kỳ <fmt:formatDate
								value="${duLieuKeToan.kyKeToan.dau}" pattern="dd/M/yyyy"></fmt:formatDate>
							- <fmt:formatDate value="${duLieuKeToan.kyKeToan.cuoi}"
								pattern="dd/M/yyyy"></fmt:formatDate></b></td>
				</tr> --%>
				<tr>
					<td></td>
					<td></td>
					<td><b>Số dư đầu kỳ</b></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td><b><fmt:formatNumber
								value="${duLieuKeToan.soLuongDuDauKy}" type="NUMBER"
								maxFractionDigits="0"></fmt:formatNumber></b></td>
					<td class="text-right"><b><fmt:formatNumber
								value="${duLieuKeToan.soDuDauKy}" type="NUMBER"
								maxFractionDigits="0"></fmt:formatNumber></b></td>
					<!-- <td></td> -->
				</tr>
				<c:forEach items="${duLieuKeToan.nghiepVuKeToanDs}"
					var="nghiepVuKeToan">
					<tr>
						<td><fmt:formatDate value="${nghiepVuKeToan.chungTu.ngayHt}"
								pattern="dd/M/yyyy" type="Date" dateStyle="SHORT" /></td>
						<td><c:choose>
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
									<td><c:choose>
											<c:when
												test="${nghiepVuKeToan.chungTu.loaiCt==ChungTu.CHUNG_TU_MUA_HANG}">
												<fmt:formatNumber
													value="${nghiepVuKeToan.hangHoa.donGia.giaTri}"
													type="NUMBER" maxFractionDigits="0"></fmt:formatNumber>
											</c:when>
											<c:otherwise>
												<fmt:formatNumber
													value="${nghiepVuKeToan.hangHoa.giaKho.giaTri}"
													type="NUMBER" maxFractionDigits="0"></fmt:formatNumber>
											</c:otherwise>
										</c:choose></td>
									<td><fmt:formatNumber
											value="${nghiepVuKeToan.hangHoa.soLuong}" type="NUMBER"
											maxFractionDigits="0"></fmt:formatNumber></td>
									<td class="text-right"><fmt:formatNumber
											value="${nghiepVuKeToan.taiKhoanCo.soTien.soTien*nghiepVuKeToan.chungTu.loaiTien.banRa}"
											type="NUMBER" maxFractionDigits="0"></fmt:formatNumber></td>
									<td>0</td>
									<td class="text-right">0</td>
								</c:if>
								<c:if
									test="${nghiepVuKeToan.taiKhoanCo.loaiTaiKhoan.maTk.trim().length() >= mainFinanceForm.taiKhoan.trim().length()
										&& nghiepVuKeToan.taiKhoanCo.loaiTaiKhoan.maTk.trim().substring(0, mainFinanceForm.taiKhoan.trim().length()).equals(mainFinanceForm.taiKhoan.trim())}">
									<td>${nghiepVuKeToan.taiKhoanNo.loaiTaiKhoan.maTk}</td>
									<td><c:choose>
											<c:when
												test="${nghiepVuKeToan.chungTu.loaiCt==ChungTu.CHUNG_TU_MUA_HANG}">
												<fmt:formatNumber
													value="${nghiepVuKeToan.hangHoa.donGia.giaTri}"
													type="NUMBER" maxFractionDigits="0"></fmt:formatNumber>
											</c:when>
											<c:otherwise>
												<fmt:formatNumber
													value="${nghiepVuKeToan.hangHoa.giaKho.giaTri}"
													type="NUMBER" maxFractionDigits="0"></fmt:formatNumber>
											</c:otherwise>
										</c:choose></td>
									<td>0</td>
									<td class="text-right">0</td>
									<td><fmt:formatNumber
											value="${nghiepVuKeToan.hangHoa.soLuong}" type="NUMBER"
											maxFractionDigits="0"></fmt:formatNumber></td>
									<td class="text-right"><fmt:formatNumber
											value="${nghiepVuKeToan.taiKhoanCo.soTien.soTien*nghiepVuKeToan.chungTu.loaiTien.banRa}"
											type="NUMBER" maxFractionDigits="0"></fmt:formatNumber></td>
								</c:if>
								<td><fmt:formatNumber value="${nghiepVuKeToan.slTon}"
										type="NUMBER" maxFractionDigits="0"></fmt:formatNumber></td>
								<td class="text-right"><fmt:formatNumber
										value="${nghiepVuKeToan.ton}" type="NUMBER"
										maxFractionDigits="0"></fmt:formatNumber></td>
								<!-- <td></td> -->
							</c:when>
							<c:when
								test="${nghiepVuKeToan.taiKhoanCo.soTien.soTien > nghiepVuKeToan.taiKhoanNo.soTien.soTien}">
								<td>${nghiepVuKeToan.taiKhoanNo.lyDo}</td>
								<c:if
									test="${nghiepVuKeToan.taiKhoanNo.loaiTaiKhoan.maTk.trim().length() >= mainFinanceForm.taiKhoan.trim().length()
										&& nghiepVuKeToan.taiKhoanNo.loaiTaiKhoan.maTk.trim().substring(0, mainFinanceForm.taiKhoan.trim().length()).equals(mainFinanceForm.taiKhoan.trim())}">
									<td>${nghiepVuKeToan.taiKhoanCo.loaiTaiKhoan.maTk}</td>
									<td><c:choose>
											<c:when
												test="${nghiepVuKeToan.chungTu.loaiCt==ChungTu.CHUNG_TU_MUA_HANG}">
												<fmt:formatNumber
													value="${nghiepVuKeToan.hangHoa.donGia.giaTri}"
													type="NUMBER" maxFractionDigits="0"></fmt:formatNumber>
											</c:when>
											<c:otherwise>
												<fmt:formatNumber
													value="${nghiepVuKeToan.hangHoa.giaKho.giaTri}"
													type="NUMBER" maxFractionDigits="0"></fmt:formatNumber>
											</c:otherwise>
										</c:choose></td>
									<td><fmt:formatNumber
											value="${nghiepVuKeToan.hangHoa.soLuong}" type="NUMBER"
											maxFractionDigits="0"></fmt:formatNumber></td>
									<td class="text-right"><fmt:formatNumber
											value="${nghiepVuKeToan.taiKhoanNo.soTien.soTien*nghiepVuKeToan.chungTu.loaiTien.banRa}"
											type="NUMBER" maxFractionDigits="0"></fmt:formatNumber></td>
									<td>0</td>
									<td class="text-right">0</td>
								</c:if>
								<c:if
									test="${nghiepVuKeToan.taiKhoanCo.loaiTaiKhoan.maTk.trim().length() >= mainFinanceForm.taiKhoan.trim().length()
										&& nghiepVuKeToan.taiKhoanCo.loaiTaiKhoan.maTk.trim().substring(0, mainFinanceForm.taiKhoan.trim().length()).equals(mainFinanceForm.taiKhoan.trim())}">
									<td>${nghiepVuKeToan.taiKhoanNo.loaiTaiKhoan.maTk}</td>
									<td><c:choose>
											<c:when
												test="${nghiepVuKeToan.chungTu.loaiCt==ChungTu.CHUNG_TU_MUA_HANG}">
												<fmt:formatNumber
													value="${nghiepVuKeToan.hangHoa.donGia.giaTri}"
													type="NUMBER" maxFractionDigits="0"></fmt:formatNumber>
											</c:when>
											<c:otherwise>
												<fmt:formatNumber
													value="${nghiepVuKeToan.hangHoa.giaKho.giaTri}"
													type="NUMBER" maxFractionDigits="0"></fmt:formatNumber>
											</c:otherwise>
										</c:choose></td>
									<td>0</td>
									<td class="text-right">0</td>
									<td><fmt:formatNumber
											value="${nghiepVuKeToan.hangHoa.soLuong}" type="NUMBER"
											maxFractionDigits="0"></fmt:formatNumber></td>
									<td class="text-right"><fmt:formatNumber
											value="${nghiepVuKeToan.taiKhoanNo.soTien.soTien*nghiepVuKeToan.chungTu.loaiTien.banRa}"
											type="NUMBER" maxFractionDigits="0"></fmt:formatNumber></td>
								</c:if>
								<td><fmt:formatNumber value="${nghiepVuKeToan.slTon}"
										type="NUMBER" maxFractionDigits="0"></fmt:formatNumber></td>
								<td class="text-right"><fmt:formatNumber
										value="${nghiepVuKeToan.ton}" type="NUMBER"
										maxFractionDigits="0"></fmt:formatNumber></td>
								<!-- <td></td> -->
							</c:when>
						</c:choose>
					</tr>
				</c:forEach>
				<tr>
					<td></td>
					<td></td>
					<td><b>Tổng phát sinh/Số dư cuối kỳ</b></td>
					<td></td>
					<td></td>
					<td><b><fmt:formatNumber
								value="${duLieuKeToan.soLuongNhapPhatSinh}" type="NUMBER"
								maxFractionDigits="0"></fmt:formatNumber></b></td>
					<td class="text-right"><b><fmt:formatNumber
								value="${duLieuKeToan.tongNoPhatSinh}" type="NUMBER"
								maxFractionDigits="0"></fmt:formatNumber></b></td>
					<td><b><fmt:formatNumber
								value="${duLieuKeToan.soLuongXuatPhatSinh}" type="NUMBER"
								maxFractionDigits="0"></fmt:formatNumber></b></td>
					<td class="text-right"><b><fmt:formatNumber
								value="${duLieuKeToan.tongCoPhatSinh}" type="NUMBER"
								maxFractionDigits="0"></fmt:formatNumber></b></td>
					<td><b><fmt:formatNumber
								value="${duLieuKeToan.soLuongDuCuoiKy}" type="NUMBER"
								maxFractionDigits="0"></fmt:formatNumber></b></td>
					<td class="text-right"><b><fmt:formatNumber
								value="${duLieuKeToan.soDuCuoiKy}" type="NUMBER"
								maxFractionDigits="0"></fmt:formatNumber></b></td>
					<!-- <td></td> -->
				</tr>
				<%-- <tr>
					<td></td>
					<td></td>
					<td><b>Số dư cuối kỳ</b></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td><b><fmt:formatNumber
								value="${duLieuKeToan.soLuongDuCuoiKy}" type="NUMBER" maxFractionDigits="0"></fmt:formatNumber></b></td>
					<td class="text-right"><b><fmt:formatNumber
								value="${duLieuKeToan.soDuCuoiKy}" type="NUMBER" maxFractionDigits="0"></fmt:formatNumber></b></td>
					<td></td>
				</tr> --%>
			</c:forEach>
		</tbody>
	</table>
</div>