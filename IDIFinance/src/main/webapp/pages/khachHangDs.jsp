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

<h4>Danh sách khách hàng</h4>

<div class="pull-right">
	<a href="${url}/taomoikhachhang" class="btn btn-info btn-sm"> <span
		class="glyphicon glyphicon-plus"></span> Tạo mới
	</a>
</div>
<br />
<br />

<div class="table-responsive">
	<table class="table table-bordered table-hover">
		<thead>
			<tr>
				<!-- <th>STT</th> -->
				<th>Mã</th>
				<th>Tên khách hàng</th>
				<th>Mã số thuế</th>
				<th>Đại chỉ</th>
				<th>Số điện thoại</th>
				<!-- <th>Email</th> -->
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${khachhangDs}" var="khachhang" varStatus="status">
				<tr>
					<%-- <td>${status.index+1}</td> --%>
					<td>${khachhang.maKh}</td>
					<td><a href="${url}/xemkhachhang/${khachhang.maKh}">${khachhang.tenKh}</a></td>
					<td>${khachhang.maThue}</td>
					<td>${khachhang.diaChi}</td>
					<td>${khachhang.sdt}</td>
					<%-- <td>${khachhang.email}</td> --%>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
