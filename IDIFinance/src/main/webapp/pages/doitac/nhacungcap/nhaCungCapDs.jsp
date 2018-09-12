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

<h4>Danh sách nhà cung cấp</h4>

<div class="pull-right">
	<a href="${url}/nhacungcap/taomoi" class="btn btn-info btn-sm"> <span
		class="glyphicon glyphicon-plus"></span> Tạo mới
	</a>
</div>
<br />
<br />

<div class="table-responsive">
	<table class="table table-bordered table-hover">
		<thead>
			<tr>
				<th>Mã</th>
				<th>Tên nhà cung cấp</th>
				<th>Mã số thuế</th>
				<th>Đại chỉ</th>
				<th>Số điện thoại</th>
				<!-- <th>Email</th> -->
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${nhaCungCapDs}" var="nhaCungCap"
				varStatus="status">
				<tr>
					<td>${nhaCungCap.khNcc}</td>
					<td><a href="${url}/nhacungcap/xem/${nhaCungCap.maNcc}">${nhaCungCap.tenNcc}</a></td>
					<td>${nhaCungCap.maThue}</td>
					<td>${nhaCungCap.diaChi}</td>
					<td>${nhaCungCap.sdt}</td>
					<%-- <td>${nhaCungCap.email}</td> --%>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
