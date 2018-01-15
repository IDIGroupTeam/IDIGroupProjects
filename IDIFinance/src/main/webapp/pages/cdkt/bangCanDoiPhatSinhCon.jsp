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
				<td>${duLieuKeToan.loaiTaiKhoan.maTk}</td>
				<td>${duLieuKeToan.loaiTaiKhoan.tenTk}</td>
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
				<td class="text-right"><fmt:formatNumber
						value="${duLieuKeToan.tongNoPhatSinh}" type="NUMBER"></fmt:formatNumber></td>
				<td class="text-right"><fmt:formatNumber
						value="${duLieuKeToan.tongCoPhatSinh}" type="NUMBER"></fmt:formatNumber></td>
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
			</tr>
			<c:set var="duLieuKeToanDs" value="${duLieuKeToan.duLieuKeToanDs}"
				scope="request" />
			<jsp:include page="bangCanDoiPhatSinhCon.jsp" />
		</c:forEach>
	</c:when>
</c:choose>