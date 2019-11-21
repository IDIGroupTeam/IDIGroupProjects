<%@page import="com.idi.finance.bean.kyketoan.KyKeToan"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<h4>Báo cáo tài chính</h4>
<p>
	<i><b>Kỳ kế toán:</b> ${kyKeToan.tenKyKt}</i>
</p>

<c:choose>
	<c:when test="${kyKeToan.trangThai== KyKeToan.DONG}">
		<div class="pull-left">
			<i>Kỳ kế toán hiện tại bị đóng, bạn chỉ xem, không thể thêm mới
				hoặc sửa dữ liệu.</i>
		</div>
		<div class="pull-right"></div>
	</c:when>
	<c:otherwise>
		<div class="pull-left">
			<c:if test="${not empty message}">
				<span class="text-danger"><i>${message}</i></span>
			</c:if>
		</div>
		<div class="pull-right">
			<c:choose>
				<c:when test="${kyKeToan.trangThai!= KyKeToan.DONG}">
					<a href="${url}/bctc/taomoi" class="btn btn-info btn-sm"> <span
						class="glyphicon glyphicon-plus"></span> Tạo mới
					</a>
				</c:when>
			</c:choose>
		</div>
	</c:otherwise>
</c:choose>
<br />
<br />

<div class="table-responsive">
	<table id="dataTable" class="table table-bordered table-hover">
		<thead>
			<tr>
				<th class="text-center">Báo cáo tài chính</th>
				<th class="text-center">Từ</th>
				<th class="text-center">Đến</th>
				<th class="text-center">Người lập</th>
				<th class="text-center">Giám đốc</th>
				<th class="text-center">Ngày lập</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${bctcDs}" var="bctc" varStatus="status">
				<tr>
					<td><a href="${url}/bctc/xem/${bctc.maBctc}"
						class="text-primary">${bctc.tieuDe}</a></td>
					<td><fmt:formatDate value="${bctc.batDau}" pattern="dd/M/yyyy"
							type="Date" dateStyle="SHORT" /></td>
					<td><fmt:formatDate value="${bctc.ketThuc}"
							pattern="dd/M/yyyy" type="Date" dateStyle="SHORT" /></td>
					<td>${bctc.nguoiLap}</td>
					<td>${bctc.giamDoc}</td>
					<td>${bctc.ngayLap}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>