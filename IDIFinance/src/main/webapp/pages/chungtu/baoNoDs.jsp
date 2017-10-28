<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

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

<h4>Danh mục báo nợ</h4>

<div class="pull-right">
	<a href="${url}/taomoibaono" class="btn btn-info btn-sm"> <span
		class="glyphicon glyphicon-plus"></span> Tạo mới
	</a>
</div>
<br/><br/>

<div class="row">
	<table class="table table-bordered table-hover">
		<thead>
			<tr>
				<th>Số phiếu thu</th>
				<th>Ngày lập</th>
				<th>Lý do</th>
				<th>Đối tượng nhận</th>
				<th>Số tiền</th>
				<th>Tài khoản</th>
				<th>Chỉnh sửa</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${baoNoDs}" var="baoNo" varStatus="status">
				<tr>
					<td>${baoNo.soCt}</td>
					<td><fmt:formatDate value="${baoNo.ngayLap}"
							pattern="dd/M/yyyy" type="Date" dateStyle="SHORT" /></td>
					<td>${baoNo.lyDo}</td>
					<td>${baoNo.doiTuong.tenDt}</td>
					<td><fmt:formatNumber value="${baoNo.soTien.giaTri}"
							maxFractionDigits="2"></fmt:formatNumber></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>