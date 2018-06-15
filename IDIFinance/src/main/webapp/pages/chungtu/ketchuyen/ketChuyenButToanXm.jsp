<%@page import="com.idi.finance.bean.kyketoan.KyKeToan"%>
<%@page import="com.idi.finance.bean.taikhoan.LoaiTaiKhoan"%>
<%@page import="com.idi.finance.bean.chungtu.KetChuyenButToan"%>
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
		var ngayLap = $.trim($("#ngayLap").html());
		$("#xoaNut")
				.click(
						function() {
							xoa = $(this).attr("href");
							BootstrapDialog
									.confirm({
										title : 'Xác nhận',
										message : 'Bạn muốn xóa bút toán kết chuyển này không ?<br/><b>Mã số:</b> ${ketChuyenButToan.maKc}<br /> <b>Tên:</b> '
												+ ' ${ketChuyenButToan.tenKc}',
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

<div>
	<span class="pull-left heading4">BÚT TOÁN KẾ CHUYỂN TỰ ĐỘNG</span>
</div>
<br />
<hr />

<div class="row form-group">
	<label class="control-label col-sm-2" for="maKc">Mã số:</label>
	<div class="col-sm-4">${ketChuyenButToan.maKc}</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="tenKc">Tên kết
		chuyển</label>
	<div class="col-sm-4">${ketChuyenButToan.tenKc}</div>

	<label class="control-label col-sm-2" for="congThuc">Công thức</label>
	<div class="col-sm-4">${ketChuyenButToan.congThuc}</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2"
		for="taiKhoanNo.loaiTaiKhoan.maTk">Tài khoản nợ</label>
	<div class="col-sm-4">${ketChuyenButToan.taiKhoanNo.loaiTaiKhoan.maTenTk}</div>

	<label class="control-label col-sm-2"
		for="taiKhoanCo.loaiTaiKhoan.maTk">Tài khoản có</label>
	<div class="col-sm-4">${ketChuyenButToan.taiKhoanCo.loaiTaiKhoan.maTenTk}</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="thuTu">Thứ tự</label>
	<div class="col-sm-4">${ketChuyenButToan.thuTu}</div>

	<label class="control-label col-sm-2" for="loaiKc">Phần loại</label>
	<div class="col-sm-4">
		<c:choose>
			<c:when
				test="${ketChuyenButToan.loaiKc==KetChuyenButToan.KCBT_DAU_KY }">Kết chuyển đầu kỳ</c:when>
			<c:otherwise>Kết chuyển cuối kỳ</c:otherwise>
		</c:choose>
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="moTa">Mô tả</label>
	<div class="col-sm-4">${ketChuyenButToan.moTa}</div>
</div>

<div class="row form-group">
	<div class="col-sm-4">
		<a href="${url}/chungtu/ketchuyen/buttoan/danhsach"
			class="btn btn-info btn-sm">Danh sách kết chuyển tự động</a>

		<c:choose>
			<c:when
				test="${kyKeToan!=null && kyKeToan.trangThai!= KyKeToan.DONG}">
				<a id="xoaNut"
					href="${url}/chungtu/ketchuyen/buttoan/xoa/${ketChuyenButToan.maKc}"
					class="btn btn-info btn-sm">Xóa</a>
				<a
					href="${url}/chungtu/ketchuyen/buttoan/sua/${ketChuyenButToan.maKc}"
					class="btn btn-info btn-sm">Sửa</a>
				<a href="${url}/chungtu/ketchuyen/buttoan/taomoi"
					class="btn btn-info btn-sm">Tạo mới </a>
			</c:when>
		</c:choose>
	</div>
</div>