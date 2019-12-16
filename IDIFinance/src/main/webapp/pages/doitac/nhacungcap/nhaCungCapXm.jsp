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
										message : 'Bạn muốn xóa khách hàng này không ?<br/>Mã: ${nhaCungCap.maNcc}<br /> Tên: ${nhaCungCap.tenNcc} <br /> Địa chỉ: ${nhaCungCap.diaChi}',
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

<h4>CHI TIẾT NHÀ CUNG CẤP</h4>

<hr />

<div class="row form-group">
	<label class="control-label col-sm-2" for="tenKh">Tên nhà cung
		cấp</label>
	<div class="col-sm-4">${nhaCungCap.tenNcc}</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="tenKh">Mã nhà cung
		cấp</label>
	<div class="col-sm-4">${nhaCungCap.khNcc}</div>
	<label class="control-label col-sm-2" for="maThue">Mã số thuế</label>
	<div class="col-sm-4">${nhaCungCap.maThue}</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="diaChi">Địa chỉ</label>
	<div class="col-sm-4">${nhaCungCap.diaChi}</div>
	<label class="control-label col-sm-2" for="email">Email</label>
	<div class="col-sm-4">${nhaCungCap.email}</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="sdt">Số điện thoại</label>
	<div class="col-sm-4">${nhaCungCap.sdt}</div>
	<label class="control-label col-sm-2" for="webSite">Website</label>
	<div class="col-sm-4">${nhaCungCap.webSite}</div>
</div>

<hr />

<div class="row form-group">
	<div class="col-sm-12">
		<a href="${url}/nhacungcap/danhsach" class="btn btn-info btn-sm">Danh
			sách nhà cung cấp</a>
		<c:if test="${nhaCungCap.xoa}">
			<a id="xoaNut" href="${url}/nhacungcap/xoa/${nhaCungCap.maNcc}"
				class="btn btn-info btn-sm">Xóa</a>
		</c:if>
		<a href="${url}/nhacungcap/sua/${nhaCungCap.maNcc}"
			class="btn btn-info btn-sm">Sửa</a>
	</div>
</div>

