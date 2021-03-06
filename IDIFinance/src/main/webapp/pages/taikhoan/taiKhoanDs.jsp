<%@page import="com.idi.finance.bean.taikhoan.LoaiTaiKhoan"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<script type="text/javascript">
	function xacNhanXoaTaiKhoan(maTk){
		BootstrapDialog.confirm({
			title : 'Xác nhận',
			message : 'Bạn muốn xóa tài khoản này không ?<br/>Mã tài khoản: '+maTk,
			type : 'type-info',
			closable : true,
			draggable : true,
			btnCancelLabel : 'Không',
			btnOKLabel : 'Có',
			callback : function(result) {
				if (result) {
					$("#mainFinanceForm").attr(
							"action", "${url}/taikhoan/xoa/"+maTk);
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

<h4>Danh mục tài khoản kế toán</h4>
<div class="pull-right">
	<a href="${url}/taikhoan/taomoi" class="btn btn-info btn-sm"> <span
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
				<th>Mã tài khoản (Cấp 1)</th>
				<th>Mã tài khoản (Cấp 2)</th>
				<th>Mã tài khoản (Cấp 3)</th>
				<th>Tên tài khoản</th>
				<th>Số dư</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${taiKhoanDs}" var="taiKhoan" varStatus="status">
				<tr>

					<%-- <td>${status.index+1}</td> --%>
					<td><c:if test="${fn:length(taiKhoan.maTk) <= 3}">
							<b>${taiKhoan.maTk}</b>
						</c:if></td>
					<td><c:if test="${fn:length(taiKhoan.maTk)==4}">${taiKhoan.maTk}</c:if></td>
					<td><c:if test="${fn:length(taiKhoan.maTk)>4}">${taiKhoan.maTk}</c:if></td>
					<td><c:choose>
							<c:when test="${fn:length(taiKhoan.maTk) <= 3}">
								<b>${taiKhoan.tenTk}</b>
							</c:when>
							<c:otherwise>
							${taiKhoan.tenTk}
						</c:otherwise>
						</c:choose></td>
					<td><c:choose>
							<c:when test="${taiKhoan.soDu==LoaiTaiKhoan.NO}">Nợ</c:when>
							<c:otherwise>Có</c:otherwise>
						</c:choose></td>
					<td><div class="btn-group btn-group-sm">
							<a href="${url}/taikhoan/sua/${taiKhoan.maTk}" class="btn"
								title="Sửa"> <span class="glyphicon glyphicon-edit"></span>
							</a><a href="${url}/taikhoan/xoa/${taiKhoan.maTk}" class="btn"
								title="Sửa" onclick="return xacNhanXoaTaiKhoan(${taiKhoan.maTk});">
								<span class="glyphicon glyphicon-remove"></span>
							</a>
						</div></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
