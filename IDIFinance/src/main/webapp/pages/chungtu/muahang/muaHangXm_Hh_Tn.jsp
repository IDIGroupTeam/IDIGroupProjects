<%@page import="com.idi.finance.bean.taikhoan.LoaiTaiKhoan"%>
<%@page import="com.idi.finance.bean.chungtu.DoiTuong"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<ul class="nav nav-tabs nav-pills nav-justified">
	<li class="active"><a data-toggle="tab" href="#hangTien">Hàng
			tiền</a></li>
	<li><a data-toggle="tab" href="#thue">Thuế</a></li>
	<li><a data-toggle="tab" href="#chiPhi">Chi phí</a></li>
</ul>
<div class="tab-content table-responsive sub-content"
	style="overflow-x: auto; overflow-y: visiable;">
	<div id="hangTien" class="tab-pane fade in active"
		style="width: 1700px;">
		<table class="table table-bordered table-hover text-center hanghoa"
			id="hangTienTbl">
			<thead>
				<tr>
					<th class="text-center">Mã vật tư, hàng hóa</th>
					<th class="text-center">Vật tư, hàng hóa</th>
					<th class="text-center">Đơn vị tính</th>
					<th class="text-center">Số lượng</th>
					<th class="text-center">Giá mua</th>
					<th class="text-center">Thành tiền</th>
					<th class="text-center">TK Kho (Nợ)</th>
					<th class="text-center">TK công nợ (Có)</th>
					<th class="text-center">Kho</th>
					<th class="text-center">Lô</th>
					<th class="text-center">Giá nhập kho</th>
					<th class="text-center">Tổng tiền<br />nhập kho
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${chungTu.hangHoaDs}" var="hangHoa"
					varStatus="status">
					<tr id="hangTien${status.index}">
						<td>${hangHoa.kyHieuHh}</td>
						<td>${hangHoa.tenHh}</td>
						<td>${hangHoa.donVi.tenDv}</td>
						<td><fmt:formatNumber value="${hangHoa.soLuong}"
								maxFractionDigits="2"></fmt:formatNumber></td>
						<td class="text-right"><c:choose>
								<c:when test="${chungTu.loaiTien.maLt=='VND'}">
									<fmt:formatNumber value="${hangHoa.donGia.soTien}"
										maxFractionDigits="2"></fmt:formatNumber>
							&nbsp;${chungTu.loaiTien.maLt}</c:when>
								<c:otherwise>
									<fmt:formatNumber value="${hangHoa.donGia.soTien}"
										maxFractionDigits="2"></fmt:formatNumber>
							&nbsp;${chungTu.loaiTien.maLt} <br />
									<fmt:formatNumber
										value="${hangHoa.donGia.soTien * chungTu.loaiTien.banRa}"
										maxFractionDigits="2"></fmt:formatNumber>
							&nbsp;VND</c:otherwise>
							</c:choose></td>
						<td class="text-right"><c:choose>
								<c:when test="${chungTu.loaiTien.maLt=='VND'}">
									<fmt:formatNumber
										value="${hangHoa.soLuong*hangHoa.donGia.soTien}"
										maxFractionDigits="2"></fmt:formatNumber>
							&nbsp;${chungTu.loaiTien.maLt}</c:when>
								<c:otherwise>
									<fmt:formatNumber
										value="${hangHoa.soLuong*hangHoa.donGia.soTien}"
										maxFractionDigits="2"></fmt:formatNumber>
							&nbsp;${chungTu.loaiTien.maLt} <br />
									<fmt:formatNumber
										value="${hangHoa.soLuong*hangHoa.donGia.soTien * chungTu.loaiTien.banRa}"
										maxFractionDigits="2"></fmt:formatNumber>
							&nbsp;VND</c:otherwise>
							</c:choose></td>
						<td>${hangHoa.tkKho.loaiTaiKhoan.maTk}</td>
						<td>${hangHoa.tkThanhtoan.loaiTaiKhoan.maTk}</td>
						<td>${hangHoa.kho.tenKho}</td>
						<td></td>
						<td class="text-right"><c:choose>
								<c:when test="${chungTu.loaiTien.maLt=='VND'}">
									<fmt:formatNumber value="${hangHoa.giaKho.soTien}"
										maxFractionDigits="2"></fmt:formatNumber>
							&nbsp;${chungTu.loaiTien.maLt}</c:when>
								<c:otherwise>
									<fmt:formatNumber value="${hangHoa.giaKho.soTien}"
										maxFractionDigits="2"></fmt:formatNumber>
							&nbsp;${chungTu.loaiTien.maLt} <br />
									<fmt:formatNumber
										value="${hangHoa.giaKho.soTien * chungTu.loaiTien.banRa}"
										maxFractionDigits="2"></fmt:formatNumber>
							&nbsp;VND</c:otherwise>
							</c:choose></td>
						<td class="text-right"><c:choose>
								<c:when test="${chungTu.loaiTien.maLt=='VND'}">
									<fmt:formatNumber
										value="${hangHoa.soLuong*hangHoa.giaKho.soTien}"
										maxFractionDigits="2"></fmt:formatNumber>
							&nbsp;${chungTu.loaiTien.maLt}</c:when>
								<c:otherwise>
									<fmt:formatNumber
										value="${hangHoa.soLuong*hangHoa.giaKho.soTien}"
										maxFractionDigits="2"></fmt:formatNumber>
							&nbsp;${chungTu.loaiTien.maLt} <br />
									<fmt:formatNumber
										value="${hangHoa.soLuong*hangHoa.giaKho.soTien * chungTu.loaiTien.banRa}"
										maxFractionDigits="2"></fmt:formatNumber>
							&nbsp;VND</c:otherwise>
							</c:choose></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div id="thue" class="tab-pane fade">
		<table class="table table-bordered table-hover text-center hanghoa"
			id="thueTbl">
			<thead>
				<tr>
					<th class="text-center" rowspan="2">Vật tư, hàng hóa</th>
					<th class="text-center" rowspan="2">Giá tính thuế</th>
					<th class="text-center" colspan="3">Thuế giá trị gia tăng</th>
				</tr>
				<tr>
					<th class="text-center">Thuế suất (%)</th>
					<th class="text-center">Tiền thuế (VND)</th>
					<th class="text-center">Tài khoản (Nợ)</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${chungTu.hangHoaDs}" var="hangHoa"
					varStatus="status">
					<tr id="thue${status.index}">
						<td>${hangHoa.tenHh}</td>
						<td class="text-right"><fmt:formatNumber
								value="${hangHoa.soLuong*hangHoa.donGia.soTien*chungTu.loaiTien.banRa}"
								maxFractionDigits="2"></fmt:formatNumber> &nbsp;VND</td>
						<td>${hangHoa.thueSuatGtgt}&nbsp;%</td>
						<td><fmt:formatNumber
								value="${hangHoa.tkThueGtgt.soTien.soTien*chungTu.loaiTien.banRa}"
								maxFractionDigits="2"></fmt:formatNumber>&nbsp;VND</td>
						<td>${hangHoa.tkThueGtgt.loaiTaiKhoan.maTk}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div id="chiPhi" class="tab-pane fade">
		<table class="table table-bordered table-hover text-center hanghoa"
			id="chiPhiTbl">
			<thead>
				<tr>
					<th class="text-center">Vật tư, hàng hóa</th>
					<th class="text-center">Đơn vị tính</th>
					<th class="text-center">Số lượng</th>
					<th class="text-center">Giá mua</th>
					<th class="text-center">Thành tiền</th>
					<th class="text-center">Kho</th>
					<th class="text-center">TK Kho (Nợ)</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${chungTu.hangHoaDs}" var="hangHoa"
					varStatus="status">
					<tr id="chiPhi${status.index}">
						<td>${hangHoa.tenHh}</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
