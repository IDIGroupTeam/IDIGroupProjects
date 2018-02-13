<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<c:choose>
	<c:when test="${not empty nhomHangHoaConDs and not empty doSau}">
		<c:forEach items="${nhomHangHoaConDs}" var="nhomHangHoaCon">
			<tr>
				<%-- <td>${nhomHangHoaCon.maNhomHh}</td> --%>
				<c:forEach begin="1" end="${nhomHangHoaCon.muc-1}">
					<td></td>
				</c:forEach>
				<td>${nhomHangHoaCon.tenNhomHh}</td>
				<c:forEach begin="${nhomHangHoaCon.muc+1}" end="${doSau-1}">
					<td></td>
				</c:forEach>
				<td><div class="btn-group btn-group-sm">
						<a href="${url}/hanghoa/nhom/sua/${nhomHangHoaCon.maNhomHh}"
							class="btn" title="Sửa"> <span
							class="glyphicon glyphicon-edit"></span>
						</a><a href="${url}/hanghoa/nhom/xoa/${nhomHangHoaCon.maNhomHh}"
							class="btn" title="Sửa"
							onclick="return xacNhanXoa(${nhomHangHoaCon.maNhomHh});"> <span
							class="glyphicon glyphicon-remove"></span>
						</a>
					</div></td>
			</tr>

			<c:set var="doSau" value="${doSau}" scope="request" />
			<c:set var="nhomHangHoaConDs" value="${nhomHangHoaCon.nhomHhDs}"
				scope="request" />
			<jsp:include page="nhomHangHoaConDs.jsp" />
		</c:forEach>
	</c:when>
</c:choose>
