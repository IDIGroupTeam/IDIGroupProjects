<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<script type="text/javascript">
	function xacNhanXoa(maTk){
		BootstrapDialog.confirm({
			title : 'Xác nhận',
			message : 'Bạn muốn xóa tài khoản ngân hàng này không ?<br/>Mã tài khoản: '+maTk,
			type : 'type-info',
			closable : true,
			draggable : true,
			btnCancelLabel : 'Không',
			btnOKLabel : 'Có',
			callback : function(result) {
				if (result) {
					$("#mainFinanceForm").attr(
							"action", "${url}/nganhang/taikhoan/xoa/"+maTk);
					$("#mainFinanceForm").attr(
							"method", "GET");
					$("#mainFinanceForm").submit();
				}
			}
		});
	
		return false;
	}

	//Shorthand for $( document ).ready()
	$(function() {
		// Khởi tạo action/method cho mainFinanceForm form

	});
</script>

<h4>Danh sách tài khoản ngân hàng</h4>

<div class="pull-right">
	<a href="${url}/nganhang/taikhoan/taomoi" class="btn btn-info btn-sm">
		<span class="glyphicon glyphicon-plus"></span> Tạo mới
	</a>
</div>
<br />
<br />

<div class="table-responsive">
	<table class="table table-bordered table-hover">
		<thead>
			<tr>
				<th>Số tài khoản</th>
				<th>Ngân hàng</th>
				<th>Chi nhánh</th>
				<th>Địa chỉ chi nhánh</th>
				<th>Chủ tài khoản</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${nganHangTaiKhoanDs}" var="nganHangTaiKhoan">
				<tr>
					<td>${nganHangTaiKhoan.soTk}</td>
					<td>${nganHangTaiKhoan.nganHang.tenVt}-${nganHangTaiKhoan.nganHang.tenDd}</td>
					<td>${nganHangTaiKhoan.chiNhanh}</td>
					<td>${nganHangTaiKhoan.diaChiCn}</td>
					<td>${nganHangTaiKhoan.chuTk}</td>
					<td><div class="btn-group btn-group-sm">
							<a href="${url}/nganhang/taikhoan/sua/${nganHangTaiKhoan.maTk}"
								class="btn" title="Sửa"> <span
								class="glyphicon glyphicon-edit"></span>
							</a><a href="${url}/nganhang/taikhoan/xoa/${nganHangTaiKhoan.maTk}"
								class="btn" title="Xóa"
								onclick="return xacNhanXoa(${nganHangTaiKhoan.maTk});"> <span
								class="glyphicon glyphicon-remove"></span>
							</a>
						</div></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>