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

<h4>Cấu hình</h4>

<div class="table-responsive">
	<table class="table table-bordered table-hover">
		<thead>
			<tr>
				<!-- <th class="text-center">Mã</th> -->
				<th class="text-center" style="width: 200px;">Tên</th>
				<th class="text-center">Giá trị</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${cauHinhDs}" var="cauHinh">
				<tr>
					<%-- <td>${cauHinh.ma}</td> --%>
					<td style="width: 200px;">${cauHinh.ten}</td>
					<td class="cell-editable" data="${cauHinh.ma}">${cauHinh.giaTri}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

