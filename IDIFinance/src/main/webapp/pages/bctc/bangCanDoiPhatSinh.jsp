<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<script type="text/javascript">
	// Shorthand for $( document ).ready()
	$(function() {

	});
</script>

<h4>Bảng cân đối phát sinh</h4>
<p>
	<i>Từ <fmt:formatDate value="${mainFinanceForm.dau}"
			pattern="dd/M/yyyy" type="Date" dateStyle="SHORT" /> đến <fmt:formatDate
			value="${mainFinanceForm.cuoi}" pattern="dd/M/yyyy" type="Date"
			dateStyle="SHORT" /></i><i class="pull-right">(*): Đơn vị:
		VND.&nbsp;&nbsp;&nbsp;&nbsp;</i>
</p>


<div class="table-responsive">
	<table id="balanceAssetTbl" class="table table-bordered table-hover">
		<thead>
			<tr>
				<th class="text-center" rowspan="2">Mã TK</th>
				<th class="text-center" rowspan="2">Tên tài khoản</th>
				<th class="text-center" colspan="2">Số dư đầu kỳ (*)</th>
				<th class="text-center" colspan="2">Phát sinh trong kỳ (*)</th>
				<th class="text-center" colspan="2">Số dư cuối kỳ (*)</th>
			</tr>
			<tr>
				<th class="text-center" style="width: 130px;">Nợ</th>
				<th class="text-center" style="width: 130px;">Có</th>
				<th class="text-center" style="width: 130px;">Nợ</th>
				<th class="text-center" style="width: 130px;">Có</th>
				<th class="text-center" style="width: 130px;">Nợ</th>
				<th class="text-center" style="width: 130px;">Có</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${duLieuKeToanMap}" var="duLieuKeToan">
				<tr>
					<th></th>
					<th colspan="7">Kỳ <fmt:formatDate
							value="${duLieuKeToan.key.dau}" pattern="dd/M/yyyy" /> - <fmt:formatDate
							value="${duLieuKeToan.key.cuoi}" pattern="dd/M/yyyy" /></th>
				</tr>
				<c:forEach items="${duLieuKeToan.value.duLieuKeToanDs}"
					var="duLieuKeToanCon">
					<tr style="font-weight: bold;">
						<td><a
							href="${url}/soketoan/socai/${duLieuKeToanCon.loaiTaiKhoan.maTk}/${mainFinanceForm.kyKeToan.maKyKt}/<fmt:formatDate
							value='${duLieuKeToan.key.dau}' pattern='dd_MM_yyyy' />/<fmt:formatDate
							value='${duLieuKeToan.key.cuoi}' pattern='dd_MM_yyyy' />"
							target="_blank">${duLieuKeToanCon.loaiTaiKhoan.maTk}</a></td>
						<td>${duLieuKeToanCon.loaiTaiKhoan.tenTk}</td>
						<c:choose>
							<c:when test="${duLieuKeToanCon.loaiTaiKhoan.luongTinh}">
								<td class="text-right"><fmt:formatNumber
										value="${duLieuKeToanCon.noDauKy}" type="NUMBER"
										maxFractionDigits="0"></fmt:formatNumber></td>
								<td class="text-right"><fmt:formatNumber
										value="${duLieuKeToanCon.coDauKy}" type="NUMBER"
										maxFractionDigits="0"></fmt:formatNumber></td>
							</c:when>
							<c:when
								test="${!duLieuKeToanCon.loaiTaiKhoan.luongTinh && duLieuKeToanCon.soDuDauKy>=0}">
								<td class="text-right"><fmt:formatNumber
										value="${duLieuKeToanCon.soDuDauKy}" type="NUMBER"
										maxFractionDigits="0"></fmt:formatNumber></td>
								<td class="text-right">0</td>
							</c:when>
							<c:when
								test="${!duLieuKeToanCon.loaiTaiKhoan.luongTinh && duLieuKeToanCon.soDuDauKy<0}">
								<td class="text-right">0</td>
								<td class="text-right"><fmt:formatNumber
										value="${0-duLieuKeToanCon.soDuDauKy}" type="NUMBER"
										maxFractionDigits="0"></fmt:formatNumber></td>
							</c:when>
						</c:choose>
						<td class="text-right"><fmt:formatNumber
								value="${duLieuKeToanCon.tongNoPhatSinh}" type="NUMBER"
								maxFractionDigits="0"></fmt:formatNumber></td>
						<td class="text-right"><fmt:formatNumber
								value="${duLieuKeToanCon.tongCoPhatSinh}" type="NUMBER"
								maxFractionDigits="0"></fmt:formatNumber></td>
						<c:choose>
							<c:when test="${duLieuKeToanCon.loaiTaiKhoan.luongTinh}">
								<td class="text-right"><fmt:formatNumber
										value="${duLieuKeToanCon.noCuoiKy}" type="NUMBER"
										maxFractionDigits="0"></fmt:formatNumber></td>
								<td class="text-right"><fmt:formatNumber
										value="${duLieuKeToanCon.coCuoiKy}" type="NUMBER"
										maxFractionDigits="0"></fmt:formatNumber></td>
							</c:when>
							<c:when
								test="${!duLieuKeToanCon.loaiTaiKhoan.luongTinh && duLieuKeToanCon.soDuCuoiKy>=0}">
								<td class="text-right"><fmt:formatNumber
										value="${duLieuKeToanCon.soDuCuoiKy}" type="NUMBER"
										maxFractionDigits="0"></fmt:formatNumber></td>
								<td class="text-right">0</td>
							</c:when>
							<c:when
								test="${!duLieuKeToanCon.loaiTaiKhoan.luongTinh && duLieuKeToanCon.soDuCuoiKy<0}">
								<td class="text-right">0</td>
								<td class="text-right"><fmt:formatNumber
										value="${0-duLieuKeToanCon.soDuCuoiKy}" type="NUMBER"
										maxFractionDigits="0"></fmt:formatNumber></td>
							</c:when>
						</c:choose>
					</tr>
					<c:set var="duLieuKeToanDs"
						value="${duLieuKeToanCon.duLieuKeToanDs}" scope="request" />
					<jsp:include page="bangCanDoiPhatSinhCon.jsp" />
				</c:forEach>
				<tr style="font-weight: bold;">
					<td></td>
					<td>Tổng</td>
					<td class="text-right"><fmt:formatNumber
							value="${duLieuKeToan.value.noDauKy}" type="NUMBER"
							maxFractionDigits="0"></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber
							value="${duLieuKeToan.value.coDauKy}" type="NUMBER"
							maxFractionDigits="0"></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber
							value="${duLieuKeToan.value.tongNoPhatSinh}" type="NUMBER"
							maxFractionDigits="0"></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber
							value="${duLieuKeToan.value.tongCoPhatSinh}" type="NUMBER"
							maxFractionDigits="0"></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber
							value="${duLieuKeToan.value.noCuoiKy}" type="NUMBER"
							maxFractionDigits="0"></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber
							value="${duLieuKeToan.value.coCuoiKy}" type="NUMBER"
							maxFractionDigits="0"></fmt:formatNumber></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>