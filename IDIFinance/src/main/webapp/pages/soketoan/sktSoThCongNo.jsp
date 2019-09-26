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

<h4>Sổ tổng hợp công nợ ${fn:toLowerCase(loaiTaiKhoan.tenTk)}</h4>

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
				<th class="text-center" style="width: 100px;" rowspan="2">Mã
					đối tượng</th>
				<th class="text-center" rowspan="2">Tên đối tượng</th>
				<th class="text-center" rowspan="2">Địa chỉ</th>
				<th class="text-center" rowspan="2">Tài khoản</th>
				<th class="text-center" colspan="2">Đầu kỳ</th>
				<th class="text-center" colspan="2">Phát sinh</th>
				<th class="text-center" colspan="2">Cuối kỳ</th>
			</tr>
			<tr>
				<th class="text-center" style="width: 100px;">Nợ</th>
				<th class="text-center" style="width: 100px;">Có</th>
				<th class="text-center" style="width: 100px;">Nợ</th>
				<th class="text-center" style="width: 100px;">Có</th>
				<th class="text-center" style="width: 100px;">Nợ</th>
				<th class="text-center" style="width: 100px;">Có</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${duLieuKeToanDs}" var="duLieuKeToan">
				<tr>
					<td colspan="10"><b>Kỳ <fmt:formatDate
								value="${duLieuKeToan.kyKeToan.dau}" pattern="dd/M/yyyy"></fmt:formatDate>
							- <fmt:formatDate value="${duLieuKeToan.kyKeToan.cuoi}"
								pattern="dd/M/yyyy"></fmt:formatDate></b></td>
				</tr>
				<c:forEach items="${duLieuKeToan.duLieuKeToanDs}"
					var="duLieuKeToanCon">
					<tr>
						<td style="width: 100px;">${duLieuKeToanCon.doiTuong.maDt}</td>
						<td><a
							href="${url}/soketoan/socongno/chitiet/${mainFinanceForm.kyKeToan.maKyKt}/${mainFinanceForm.taiKhoan}/${duLieuKeToanCon.doiTuong.loaiDt}/${duLieuKeToanCon.doiTuong.maDt}/<fmt:formatDate value='${duLieuKeToan.kyKeToan.dau}' pattern='dd_MM_yyyy'/>/<fmt:formatDate value='${duLieuKeToan.kyKeToan.cuoi}' pattern='dd_MM_yyyy'/>"
							target="_blank">${duLieuKeToanCon.doiTuong.tenDt}</a></td>
						<td>${duLieuKeToanCon.doiTuong.diaChi}</td>
						<td>${duLieuKeToanCon.loaiTaiKhoan.maTk}</td>
						<td class="text-right" style="width: 100px;"><fmt:formatNumber
								value="${duLieuKeToanCon.noDauKy}" type="NUMBER"
								maxFractionDigits="0"></fmt:formatNumber></td>
						<td class="text-right" style="width: 100px;"><fmt:formatNumber
								value="${duLieuKeToanCon.coDauKy}" type="NUMBER"
								maxFractionDigits="0"></fmt:formatNumber></td>
						<td class="text-right" style="width: 100px;"><fmt:formatNumber
								value="${duLieuKeToanCon.tongNoPhatSinh}" type="NUMBER"
								maxFractionDigits="0"></fmt:formatNumber></td>
						<td class="text-right" style="width: 100px;"><fmt:formatNumber
								value="${duLieuKeToanCon.tongCoPhatSinh}" type="NUMBER"
								maxFractionDigits="0"></fmt:formatNumber></td>

						<c:choose>
							<c:when test="${loaiTaiKhoan.luongTinh}">
								<c:choose>
									<c:when test="${duLieuKeToanCon.soDuCuoiKy > 0}">
										<td class="text-right" style="width: 100px;"><fmt:formatNumber
												value="${duLieuKeToanCon.soDuCuoiKy}" type="NUMBER"
												maxFractionDigits="0"></fmt:formatNumber></td>
										<td class="text-right" style="width: 100px;">0</td>
									</c:when>
									<c:otherwise>
										<td class="text-right" style="width: 100px;">0</td>
										<td class="text-right" style="width: 100px;"><fmt:formatNumber
												value="${0-duLieuKeToanCon.soDuCuoiKy}" type="NUMBER"
												maxFractionDigits="0"></fmt:formatNumber></td>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${loaiTaiKhoan.soDu == LoaiTaiKhoan.NO}">
										<td class="text-right" style="width: 100px;"><fmt:formatNumber
												value="${duLieuKeToanCon.soDuCuoiKy}" type="NUMBER"
												maxFractionDigits="0"></fmt:formatNumber></td>
										<td class="text-right" style="width: 100px;">0</td>
									</c:when>
									<c:otherwise>
										<td class="text-right" style="width: 100px;">0</td>
										<td class="text-right" style="width: 100px;"><fmt:formatNumber
												value="${duLieuKeToanCon.soDuCuoiKy}" type="NUMBER"
												maxFractionDigits="0"></fmt:formatNumber></td>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</tr>
				</c:forEach>
			</c:forEach>
		</tbody>
	</table>
</div>