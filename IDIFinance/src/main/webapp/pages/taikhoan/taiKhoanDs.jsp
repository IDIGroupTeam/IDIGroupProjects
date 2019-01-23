<%@page import="com.idi.finance.bean.taikhoan.LoaiTaiKhoan"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<script type="text/javascript">
	function xacNhanXoaTaiKhoan(maTk) {
		BootstrapDialog.confirm({
			title : 'Xác nhận',
			message : 'Bạn muốn xóa tài khoản này không ?<br/>Mã tài khoản: '
					+ maTk,
			type : 'type-info',
			closable : true,
			draggable : true,
			btnCancelLabel : 'Không',
			btnOKLabel : 'Có',
			callback : function(result) {
				if (result) {
					$("#mainFinanceForm").attr("action",
							"${url}/taikhoan/xoa/" + maTk);
					$("#mainFinanceForm").attr("method", "GET");
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
				<th class="text-center">Mã tài khoản<br />(Cấp 1)
				</th>
				<th class="text-center">Mã tài khoản<br />(Cấp 2)
				</th>
				<th class="text-center">Mã tài khoản<br />(Cấp 3)
				</th>
				<th class="text-center">Tên tài khoản</th>
				<th class="text-center">Số dư</th>
				<th class="text-center">Lưỡng tính</th>
				<th class="text-center"></th>
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
					<td><c:choose>
							<c:when test="${taiKhoan.luongTinh==true}">
								<span class="glyphicon glyphicon-ok" style="color: blue;"></span>
							</c:when>
						</c:choose></td>
					<td><div class="btn-group btn-group-sm">
							<a href="${url}/taikhoan/sua/${taiKhoan.maTk}" class="btn"
								title="Sửa"> <span class="glyphicon glyphicon-edit"></span>
							</a>
							<%-- <a href="${url}/taikhoan/xoa/${taiKhoan.maTk}" class="btn"
								title="Xoá"
								onclick="return xacNhanXoaTaiKhoan(${taiKhoan.maTk});"> <span
								class="glyphicon glyphicon-remove"></span>
							</a> --%>
							<a href="${url}/taikhoan/taomoi" class="btn" title="Tạo mới">
								<span class="glyphicon glyphicon-plus"></span>
							</a>
						</div></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
