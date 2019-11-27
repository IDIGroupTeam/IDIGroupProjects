<%@page import="com.idi.finance.bean.taikhoan.LoaiTaiKhoan"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<c:set var="parentIdRes" value="${parentId}" scope="page" />
<c:choose>
	<c:when test="${not empty bais and bais.size()>0}">
		<c:forEach items="${bais}" var="bai">
			<tr id="${parentIdRes}_${bai.assetCode}">
				<c:choose>
					<c:when test="${not empty bai.childs and bai.childs.size()>0}">
						<td>${bai.assetCode}&nbsp;-&nbsp;<span
							class="cell-editable dis-removable">${bai.assetName}</span></td>
					</c:when>
					<c:when
						test="${not empty bai.taiKhoanDs and bai.taiKhoanDs.size()>0}">
						<td>${bai.assetCode}&nbsp;-&nbsp;<span
							class="cell-editable dis-removable">${bai.assetName}</span></td>
					</c:when>
					<c:otherwise>
						<td>${bai.assetCode}&nbsp;-&nbsp;<span class="cell-editable">${bai.assetName}</span></td>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${bai.soDu==LoaiTaiKhoan.NO}">
						<td>Nợ</td>
					</c:when>
					<c:otherwise>
						<td>Có</td>
					</c:otherwise>
				</c:choose>
				<td></td>
			</tr>

			<c:set var="parentId" value="${parentIdRes}_${bai.assetCode}"
				scope="request" />
			<c:set var="assetCode" value="${bai.assetCode}" scope="request" />
			<c:set var="taiKhoanDs" value="${bai.taiKhoanDs}" scope="request" />
			<c:set var="bais" value="${bai.childs}" scope="request" />
			<jsp:include page="balanceSheetCodeDsCon.jsp" />
		</c:forEach>
	</c:when>
	<c:when test="${not empty taiKhoanDs and taiKhoanDs.size()>0}">
		<c:forEach items="${taiKhoanDs}" var="taiKhoan">
			<tr id="${parentIdRes}_${taiKhoan.maTk}"
				data-assetcode="${assetCode}" data-matk="${taiKhoan.maTk}">
				<td><span class="cell-editable" data-type="combobox"
					data-field="matk" data-matkgoc="${taiKhoan.maTkGoc}">${taiKhoan.maTenTk}</span></td>
				<c:choose>
					<c:when test="${taiKhoan.soDuGiaTri==LoaiTaiKhoan.NO}">
						<td>Nợ</td>
					</c:when>
					<c:otherwise>
						<td>Có</td>
					</c:otherwise>
				</c:choose>
				<td>${taiKhoan.maTkGoc}</td>
			</tr>
		</c:forEach>
	</c:when>
</c:choose>