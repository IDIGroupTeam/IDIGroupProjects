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
						<td>${duLieuKeToanCon.loaiTaiKhoan.maTk}</td>
						<td>${duLieuKeToanCon.loaiTaiKhoan.tenTk}</td>
						<c:choose>
							<c:when test="${duLieuKeToanCon.soDuDauKy>=0}">
								<td class="text-right"><fmt:formatNumber
										value="${duLieuKeToanCon.soDuDauKy}" type="NUMBER"></fmt:formatNumber></td>
								<td class="text-right">0</td>
							</c:when>
							<c:otherwise>
								<td class="text-right">0</td>
								<td class="text-right"><fmt:formatNumber
										value="${0-duLieuKeToanCon.soDuDauKy}" type="NUMBER"></fmt:formatNumber></td>
							</c:otherwise>
						</c:choose>
						<td class="text-right"><fmt:formatNumber
								value="${duLieuKeToanCon.tongNoPhatSinh}" type="NUMBER"></fmt:formatNumber></td>
						<td class="text-right"><fmt:formatNumber
								value="${duLieuKeToanCon.tongCoPhatSinh}" type="NUMBER"></fmt:formatNumber></td>
						<c:choose>
							<c:when test="${duLieuKeToanCon.soDuCuoiKy>=0}">
								<td class="text-right"><fmt:formatNumber
										value="${duLieuKeToanCon.soDuCuoiKy}" type="NUMBER"></fmt:formatNumber></td>
								<td class="text-right">0</td>
							</c:when>
							<c:otherwise>
								<td class="text-right">0</td>
								<td class="text-right"><fmt:formatNumber
										value="${0-duLieuKeToanCon.soDuCuoiKy}" type="NUMBER"></fmt:formatNumber></td>
							</c:otherwise>
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
							value="${duLieuKeToan.value.noDauKy}" type="NUMBER"></fmt:formatNumber>
					</td>
					<td class="text-right"><fmt:formatNumber
							value="${duLieuKeToan.value.coDauKy}" type="NUMBER"></fmt:formatNumber>
					</td>
					<td class="text-right"><fmt:formatNumber
							value="${duLieuKeToan.value.tongNoPhatSinh}" type="NUMBER"></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber
							value="${duLieuKeToan.value.tongCoPhatSinh}" type="NUMBER"></fmt:formatNumber>
					</td>
					<td class="text-right"><fmt:formatNumber
							value="${duLieuKeToan.value.noCuoiKy}" type="NUMBER"></fmt:formatNumber>
					</td>
					<td class="text-right"><fmt:formatNumber
							value="${duLieuKeToan.value.coCuoiKy}" type="NUMBER"></fmt:formatNumber>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>