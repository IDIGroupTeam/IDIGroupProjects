<%@page import="com.idi.finance.bean.taikhoan.LoaiTaiKhoan"%>
<%@page import="com.idi.finance.bean.doituong.DoiTuong"%>
<%@page import="com.idi.finance.bean.LoaiTien"%>
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
	<li><a data-toggle="tab" href="#giaVon">Giá vốn</a></li>
	<li><a data-toggle="tab" href="#thue">Thuế</a></li>
</ul>
<div class="tab-content table-responsive sub-content">
	<div id="hangTien" class="tab-pane fade in active">
		<table class="table table-bordered table-hover text-center hanghoa"
			id="hangTienTbl">
			<thead>
				<tr>
					<th class="text-center">Mã vật tư, hàng hóa</th>
					<th class="text-center">Vật tư, hàng hóa</th>
					<th class="text-center">Đơn vị tính</th>
					<th class="text-center">Số lượng</th>
					<th class="text-center">Giá bán</th>
					<th class="text-center">Thành tiền</th>
					<th class="text-center">TK Doanh thu (Có)</th>
					<th class="text-center">TK công nợ (Nợ)</th>
					<th class="text-center">Tiền công nợ</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${chungTu.hangHoaDs}" var="hangHoa"
					varStatus="status">
					<tr id="hangTien${status.index}">
						<td class="text-left" style="width: 200px;">${hangHoa.kyHieuHh}</td>
						<td class="text-left" style="width: 230px;">${hangHoa.tenHh}</td>
						<td>${hangHoa.donVi.tenDv}</td>
						<td>${hangHoa.soLuong}</td>
						<td class="text-right"><c:choose>
								<c:when test="${chungTu.loaiTien.maLt eq LoaiTien.VND}">
									<fmt:formatNumber value="${hangHoa.donGia.soTien}"
										maxFractionDigits="0"></fmt:formatNumber>
							&nbsp;${chungTu.loaiTien.maLt}</c:when>
								<c:otherwise>
									<fmt:formatNumber value="${hangHoa.donGia.soTien}"
										maxFractionDigits="2"></fmt:formatNumber>
							&nbsp;${chungTu.loaiTien.maLt} <br />
									<fmt:formatNumber value="${hangHoa.donGia.giaTri}"
										maxFractionDigits="0"></fmt:formatNumber>
							&nbsp;VND</c:otherwise>
							</c:choose></td>
						<td class="text-right"><c:choose>
								<c:when test="${chungTu.loaiTien.maLt eq LoaiTien.VND}">
									<fmt:formatNumber
										value="${hangHoa.soLuong*hangHoa.donGia.soTien}"
										maxFractionDigits="0"></fmt:formatNumber>
							&nbsp;${chungTu.loaiTien.maLt}</c:when>
								<c:otherwise>
									<fmt:formatNumber
										value="${hangHoa.soLuong*hangHoa.donGia.soTien}"
										maxFractionDigits="2"></fmt:formatNumber>
							&nbsp;${chungTu.loaiTien.maLt} <br />
									<fmt:formatNumber
										value="${hangHoa.soLuong*hangHoa.donGia.giaTri}"
										maxFractionDigits="0"></fmt:formatNumber>
							&nbsp;VND</c:otherwise>
							</c:choose></td>
						<td>${hangHoa.tkDoanhThu.loaiTaiKhoan.maTk}</td>
						<td>${hangHoa.tkThanhtoan.loaiTaiKhoan.maTk}</td>
						<td class="text-right"><c:choose>
								<c:when test="${chungTu.loaiTien.maLt eq LoaiTien.VND}">
									<fmt:formatNumber value="${hangHoa.tkThanhtoan.soTien.soTien}"
										maxFractionDigits="0"></fmt:formatNumber>
							&nbsp;${chungTu.loaiTien.maLt}</c:when>
								<c:otherwise>
									<fmt:formatNumber value="${hangHoa.tkThanhtoan.soTien.soTien}"
										maxFractionDigits="2"></fmt:formatNumber>
							&nbsp;${chungTu.loaiTien.maLt} <br />
									<fmt:formatNumber value="${hangHoa.tkThanhtoan.soTien.giaTri}"
										maxFractionDigits="0"></fmt:formatNumber>
							&nbsp;VND</c:otherwise>
							</c:choose></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div id="giaVon" class="tab-pane fade">
		<table class="table table-bordered table-hover text-center hanghoa"
			id="chiPhiTbl">
			<thead>
				<tr>
					<th class="text-center">Vật tư, hàng hóa</th>
					<th class="text-center">Đơn vị tính</th>
					<th class="text-center">Số lượng</th>
					<th class="text-center">Giá vốn</th>
					<th class="text-center">Thành tiền</th>
					<th class="text-center">TK Giá vốn (Nợ)</th>
					<th class="text-center">TK Kho (Có)</th>
					<th class="text-center">Kho</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${chungTu.hangHoaDs}" var="hangHoa"
					varStatus="status">
					<tr id="chiPhi${status.index}">
						<td style="width: 250px;">${hangHoa.tenHh}</td>
						<td>${hangHoa.donVi.tenDv}</td>
						<td>${hangHoa.soLuong}</td>
						<td><fmt:formatNumber value="${hangHoa.giaKho.giaTri}"
								maxFractionDigits="0"></fmt:formatNumber> VND</td>
						<td><fmt:formatNumber
								value="${hangHoa.soLuong*hangHoa.giaKho.giaTri}"
								maxFractionDigits="0"></fmt:formatNumber> VND</td>
						<td>${hangHoa.tkGiaVon.loaiTaiKhoan.maTk}</td>
						<td>${hangHoa.tkKho.loaiTaiKhoan.maTk}</td>
						<td style="width: 200px;">${hangHoa.kho.tenKho}</td>
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
					<th class="text-center" colspan="3">Thuế xuất khẩu</th>
					<!-- <th class="text-center" colspan="3">Thuế giá trị gia tăng</th> -->
				</tr>
				<tr>
					<th class="text-center">Thuế suất (%)</th>
					<th class="text-center">Tiền thuế (VND)</th>
					<th class="text-center">Tài khoản (Có)</th>
					<!-- <th class="text-center">Thuế suất (%)</th>
					<th class="text-center">Tiền thuế (VND)</th>
					<th class="text-center">Tài khoản (Có)</th> -->
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${chungTu.hangHoaDs}" var="hangHoa"
					varStatus="status">
					<tr id="thue${status.index}">
						<td class="text-left" style="width: 220px;">${hangHoa.tenHh}</td>
						<td class="text-right" style="width: 180px;"><fmt:formatNumber
								value="${hangHoa.soLuong*hangHoa.donGia.giaTri}"
								maxFractionDigits="0"></fmt:formatNumber> VND</td>
						<td>${hangHoa.thueSuatXk}</td>
						<td><fmt:formatNumber
								value="${hangHoa.tkThueXk.soTien.giaTri}" maxFractionDigits="0"></fmt:formatNumber></td>
						<td>${hangHoa.tkThueXk.loaiTaiKhoan.maTk}</td>
						<%-- <td>${hangHoa.thueSuatGtgt}</td>
						<td><fmt:formatNumber
								value="${hangHoa.tkThueGtgt.soTien.giaTri}" maxFractionDigits="0"></fmt:formatNumber>
							VND</td>
						<td>${hangHoa.tkThueGtgt.loaiTaiKhoan.maTk}</td> --%>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
