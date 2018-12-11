<%@page import="com.idi.finance.bean.CauHinh"%>
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
		$(".table").cellEditable({
			beforeSave : function(key, data) {
				return "maCh=" + key + "&giaTri=" + data;
				;
			},
			urlSave : "${url}/cauhinh/capnhat",
			removable : false
		});
	});
</script>

<h4>Cấu hình chung</h4>

<div class="table-responsive">
	<table class="table table-bordered table-hover">
		<thead>
			<tr>
				<!-- <th class="text-center">Mã</th> -->
				<th class="text-center" style="width: 300px;">Tên</th>
				<th class="text-center">Giá trị</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${cauHinhChungDs}" var="cauHinh">
				<tr>
					<%-- <td>${cauHinh.ma}</td> --%>
					<td style="width: 300px;">${cauHinh.ten}</td>
					<td class="cell-editable" data="${cauHinh.ma}">${cauHinh.giaTri}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<h4>Cấu hình tài khoản các chứng từ</h4>
<div class="table-responsive">
	<table class="table table-bordered table-hover">
		<thead>
			<tr>
				<!-- <th class="text-center">Mã</th> -->
				<th class="text-center" style="width: 300px;">Tên</th>
				<th class="text-center">Giá trị <br />
				<i>(Các tài khoản cách nhau dấu chấm phẩy)</i>
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${cauHinhTkDs}" var="cauHinh">
				<tr>
					<%-- <td>${cauHinh.ma}</td> --%>
					<td style="width: 300px;">${cauHinh.ten}</td>
					<td class="cell-editable" data="${cauHinh.ma}">${cauHinh.giaTri}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>


