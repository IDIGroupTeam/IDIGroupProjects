<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<script type="text/javascript">
	//Shorthand for $( document ).ready()
	$(function() {
		// Khởi tạo action/method cho mainFinanceForm form
		$("#xoaNut")
				.click(
						function() {
							xoa = $(this).attr("href");
							BootstrapDialog
									.confirm({
										title : 'Xác nhận',
										message : 'Bạn muốn xóa khách hàng này không ?<br/>Mã: ${khachHang.maKh}<br /> Tên: ${khachHang.tenKh} <br /> Địa chỉ: ${khachHang.diaChi}',
										type : 'type-info',
										closable : true,
										draggable : true,
										btnCancelLabel : 'Không',
										btnOKLabel : 'Có',
										callback : function(result) {
											if (result) {
												$("#mainFinanceForm").attr(
														"action", xoa);
												$("#mainFinanceForm").attr(
														"method", "GET");
												$("#mainFinanceForm").submit();
											}
										}
									});

							return false;
						});
	});
</script>

<h4>CHI TIẾT KHÁCH HÀNG</h4>

<hr />

<div class="row form-group">
	<label class="control-label col-sm-2" for="tenKh">Tên khách
		hàng</label>
	<div class="col-sm-4">${khachHang.tenKh}</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="khKh">Mã khách hàng</label>
	<div class="col-sm-4">${khachHang.khKh}</div>

	<label class="control-label col-sm-2" for="maThue">Mã số thuế</label>
	<div class="col-sm-4">${khachHang.maThue}</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="diaChi">Địa chỉ</label>
	<div class="col-sm-4">${khachHang.diaChi}</div>
	<label class="control-label col-sm-2" for="email">Email</label>
	<div class="col-sm-4">${khachHang.email}</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="sdt">Số điện thoại</label>
	<div class="col-sm-4">${khachHang.sdt}</div>

	<label class="control-label col-sm-2" for="webSite">Website</label>
	<div class="col-sm-4">${khachHang.webSite}</div>
</div>

<hr />

<div class="row form-group">
	<div class="col-sm-12">
		<a href="${url}/khachhang/danhsach" class="btn btn-info btn-sm">Danh
			sách khách hàng</a>
		<c:if test="${khachHang.xoa}">
			<a id="xoaNut" href="${url}/khachhang/xoa/${khachHang.maKh}"
				class="btn btn-info btn-sm">Xóa</a>
		</c:if>
		<a href="${url}/khachhang/sua/${khachHang.maKh}"
			class="btn btn-info btn-sm">Sửa</a>
	</div>
</div>