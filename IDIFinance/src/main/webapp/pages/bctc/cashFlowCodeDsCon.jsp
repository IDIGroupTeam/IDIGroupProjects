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
			<tr id="${parentIdRes}_${bai.assetCode}" data-name="chiTieuCao"
				data-remove-url="${url}/bctc/luuchuyentt/chitieu/capcao/xoa"
				data-asset-code="${bai.assetCode}">
				<c:choose>
					<c:when test="${not empty bai.childs and bai.childs.size()>0}">
						<td>${bai.assetCode}&nbsp;-&nbsp;<span
							class="cell-editable dis-removable" data-field="assetName">${bai.assetName}</span></td>
					</c:when>
					<c:when
						test="${not empty bai.taiKhoanDs and bai.taiKhoanDs.size()>0}">
						<td>${bai.assetCode}&nbsp;-&nbsp;<span
							class="cell-editable dis-removable" data-field="assetName">${bai.assetName}</span></td>
					</c:when>
					<c:otherwise>
						<td>${bai.assetCode}&nbsp;-&nbsp;<span class="cell-editable"
							data-field="assetName">${bai.assetName}</span></td>
					</c:otherwise>
				</c:choose>
				<td></td>
				<td></td>
			</tr>

			<c:set var="parentId" value="${parentIdRes}_${bai.assetCode}"
				scope="request" />
			<c:set var="assetCode" value="${bai.assetCode}" scope="request" />
			<c:set var="taiKhoanDs" value="${bai.taiKhoanDs}" scope="request" />
			<c:set var="bais" value="${bai.childs}" scope="request" />
			<jsp:include page="cashFlowCodeDsCon.jsp" />
		</c:forEach>
	</c:when>
	<c:when test="${not empty taiKhoanDs and taiKhoanDs.size()>0}">
		<c:forEach items="${taiKhoanDs}" var="taiKhoan">
			<tr id="${parentIdRes}_${taiKhoan.maTk}-${taiKhoan.doiUng.maTk}"
				data-name="chiTieuThap"
				data-remove-url="${url}/bctc/luuchuyentt/chitieu/capthap/xoa"
				data-asset-code="${assetCode}" data-ma-tk="${taiKhoan.maTk}"
				data-so-du="${taiKhoan.soDu}"
				data-ma-tk-du="${taiKhoan.doiUng.maTk}">
				<td><span class="cell-editable dis-editable" data-field="maTk">${taiKhoan.maTenTk}</span></td>
				<c:choose>
					<c:when test="${taiKhoan.soDu==LoaiTaiKhoan.NO}">
						<td>Nợ</td>
					</c:when>
					<c:otherwise>
						<td>Có</td>
					</c:otherwise>
				</c:choose>
				<td>${taiKhoan.doiUng.maTk}</td>
			</tr>
		</c:forEach>
	</c:when>
</c:choose>