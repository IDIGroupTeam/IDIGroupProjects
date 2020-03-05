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
			beforeSave : {
				cauHinh : function(data) {
					console.log("beforeSave cauHinh", data);

					var sendingData = new Object();
					sendingData.ma = data.ma;
					sendingData.giaTri = data.giaTri.value;

					return sendingData;
				}
			},
			removable : false
		});
	});
</script>


<style>
<!--
.sub-content {
	padding-top: 1px;
}
-->
</style>

<h4>Cấu hình</h4>

<ul class="nav nav-tabs nav-pills nav-justified">
	<li class="active"><a data-toggle="tab" href="#cauHinhCongTy">Công
			ty</a></li>
	<li><a data-toggle="tab" href="#cauHinhTkChungTu">Tài khoản
			các chứng từ</a></li>
	<li><a data-toggle="tab" href="#cauHinhKhac">Cấu hình khác</a></li>
</ul>

<div class="tab-content table-responsive sub-content">
	<div id="cauHinhCongTy" class="tab-pane fade in active">
		<table class="table table-bordered table-hover">
			<thead>
				<tr>
					<th class="text-center" style="width: 300px;">Tên</th>
					<th class="text-center">Giá trị</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${cauHinhCongTyDs}" var="cauHinh">
					<tr data-ma="${cauHinh.ma}" data-save-url="${url}/cauhinh/capnhat"
						data-name="cauHinh">
						<td style="width: 300px;">${cauHinh.ten}</td>
						<td class="cell-editable" data-field="giaTri">${cauHinh.giaTri}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<div id="cauHinhTkChungTu" class="tab-pane fade">
		<table class="table table-bordered table-hover">
			<thead>
				<tr>
					<th class="text-center" style="width: 300px;">Tên</th>
					<th class="text-center">Giá trị <br /> <i>(Các tài khoản
							cách nhau dấu chấm phẩy)</i>
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${cauHinhTkDs}" var="cauHinh">
					<tr data-ma="${cauHinh.ma}" data-save-url="${url}/cauhinh/capnhat"
						data-name="cauHinh">
						<td style="width: 300px;">${cauHinh.ten}</td>
						<td class="cell-editable" data-field="giaTri">${cauHinh.giaTri}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<div id="cauHinhKhac" class="tab-pane fade">
		<table class="table table-bordered table-hover">
			<thead>
				<tr>
					<th class="text-center" style="width: 300px;">Tên</th>
					<th class="text-center">Giá trị</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${cauHinhKhacDs}" var="cauHinh">
					<tr data-ma="${cauHinh.ma}" data-save-url="${url}/cauhinh/capnhat"
						data-name="cauHinh">
						<td style="width: 300px;">${cauHinh.ten}</td>
						<td class="cell-editable" data-field="giaTri">${cauHinh.giaTri}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>