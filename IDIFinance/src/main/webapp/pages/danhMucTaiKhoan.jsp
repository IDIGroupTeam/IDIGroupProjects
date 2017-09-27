<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<script type="text/javascript">
	//Shorthand for $( document ).ready()
	$(function() {
		// Khởi tạo action/method cho mainFinanceForm form

	});
</script>

<h4>Danh mục tài khoản kế toán</h4>

<table class="table table-bordered table-hover">
	<thead>
		<tr>
			<th>STT</th>
			<th>Mã tài khoản (Cấp 1)</th>
			<th>Mã tài khoản (Cấp 2)</th>
			<th>Tên tài khoản</th>
			<!-- <th>Số dư</th> -->
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${taiKhoanDm}" var="taiKhoan" varStatus="status">
			<tr>

				<td>${status.index+1}</td>
				<td><c:if test="${fn:length(taiKhoan.maTk) <= 3}">${taiKhoan.maTk}</c:if></td>
				<td><c:if test="${fn:length(taiKhoan.maTk) > 3}">${taiKhoan.maTk}</c:if></td>
				<td><c:choose>
						<c:when test="${fn:length(taiKhoan.maTk) <= 3}">
							<b>${taiKhoan.tenTk}</b>
						</c:when>
						<c:otherwise>
							${taiKhoan.tenTk}
						</c:otherwise>
					</c:choose></td>
				<%-- <td>${taiKhoan.soDu}</td> --%>
			</tr>
		</c:forEach>
	</tbody>
</table>

