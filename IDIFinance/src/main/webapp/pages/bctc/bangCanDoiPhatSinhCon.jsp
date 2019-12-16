<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<c:choose>
	<c:when test="${not empty duLieuKeToanDs}">
		<c:forEach items="${duLieuKeToanDs}" var="duLieuKeToan">
			<tr>
				<td><a
					href="${url}/soketoan/socai/${duLieuKeToan.loaiTaiKhoan.maTk}/${mainFinanceForm.kyKeToan.maKyKt}/<fmt:formatDate
							value='${duLieuKeToan.kyKeToan.dau}' pattern='dd_MM_yyyy' />/<fmt:formatDate
							value='${duLieuKeToan.kyKeToan.cuoi}' pattern='dd_MM_yyyy' />"
					target="_blank">${duLieuKeToan.loaiTaiKhoan.maTk}</a></td>
				<td>${duLieuKeToan.loaiTaiKhoan.tenTk}</td>
				<c:choose>
					<c:when test="${duLieuKeToan.loaiTaiKhoan.luongTinh}">
						<td class="text-right"><fmt:formatNumber
								value="${duLieuKeToan.noDauKy}" type="NUMBER"
								maxFractionDigits="0"></fmt:formatNumber></td>
						<td class="text-right"><fmt:formatNumber
								value="${duLieuKeToan.coDauKy}" type="NUMBER"
								maxFractionDigits="0"></fmt:formatNumber></td>
					</c:when>
					<c:when
						test="${!duLieuKeToan.loaiTaiKhoan.luongTinh && duLieuKeToan.soDuDauKy>=0}">
						<td class="text-right"><fmt:formatNumber
								value="${duLieuKeToan.soDuDauKy}" type="NUMBER"
								maxFractionDigits="0"></fmt:formatNumber></td>
						<td class="text-right">0</td>
					</c:when>
					<c:when
						test="${!duLieuKeToan.loaiTaiKhoan.luongTinh && duLieuKeToan.soDuDauKy<0}">
						<td class="text-right">0</td>
						<td class="text-right"><fmt:formatNumber
								value="${0-duLieuKeToan.soDuDauKy}" type="NUMBER"
								maxFractionDigits="0"></fmt:formatNumber></td>
					</c:when>
				</c:choose>
				<td class="text-right"><fmt:formatNumber
						value="${duLieuKeToan.tongNoPhatSinh}" type="NUMBER"
						maxFractionDigits="0"></fmt:formatNumber></td>
				<td class="text-right"><fmt:formatNumber
						value="${duLieuKeToan.tongCoPhatSinh}" type="NUMBER"
						maxFractionDigits="0"></fmt:formatNumber></td>
				<c:choose>
					<c:when test="${duLieuKeToan.loaiTaiKhoan.luongTinh}">
						<td class="text-right"><fmt:formatNumber
								value="${duLieuKeToan.noCuoiKy}" type="NUMBER"
								maxFractionDigits="0"></fmt:formatNumber></td>
						<td class="text-right"><fmt:formatNumber
								value="${duLieuKeToan.coCuoiKy}" type="NUMBER"
								maxFractionDigits="0"></fmt:formatNumber></td>
					</c:when>
					<c:when
						test="${!duLieuKeToan.loaiTaiKhoan.luongTinh && duLieuKeToan.soDuCuoiKy>=0}">
						<td class="text-right"><fmt:formatNumber
								value="${duLieuKeToan.soDuCuoiKy}" type="NUMBER"
								maxFractionDigits="0"></fmt:formatNumber></td>
						<td class="text-right">0</td>
					</c:when>
					<c:when
						test="${!duLieuKeToan.loaiTaiKhoan.luongTinh && duLieuKeToan.soDuCuoiKy<0}">
						<td class="text-right">0</td>
						<td class="text-right"><fmt:formatNumber
								value="${0-duLieuKeToan.soDuCuoiKy}" type="NUMBER"
								maxFractionDigits="0"></fmt:formatNumber></td>
					</c:when>
				</c:choose>
			</tr>
			<c:set var="duLieuKeToanDs" value="${duLieuKeToan.duLieuKeToanDs}"
				scope="request" />
			<jsp:include page="bangCanDoiPhatSinhCon.jsp" />
		</c:forEach>
	</c:when>
</c:choose>