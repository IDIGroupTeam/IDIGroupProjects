<%@page import="com.idi.finance.bean.kyketoan.KyKeToan"%>

<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<script type="text/javascript">
	function xacNhanXoa(maKyKt) {
		BootstrapDialog.confirm({
			title : 'Xác nhận',
			message : 'Bạn muốn xóa kỳ kế toán này không ?<br/>Mã kỳ kế toán: '
					+ maKyKt,
			type : 'type-info',
			closable : true,
			draggable : true,
			btnCancelLabel : 'Không',
			btnOKLabel : 'Có',
			callback : function(result) {
				if (result) {
					$("#mainFinanceForm").attr("action",
							"${url}/kyketoan/xoa/" + maKyKt);
					$("#mainFinanceForm").attr("method", "GET");
					$("#mainFinanceForm").submit();
				}
			}
		});

		return false;
	}

	function xacNhanDong(maKyKt) {
		BootstrapDialog
				.confirm({
					title : 'Xác nhận',
					message : 'Bạn muốn đóng kỳ kế toán này không ?<br/>Mã kỳ kế toán: '
							+ maKyKt,
					type : 'type-info',
					closable : true,
					draggable : true,
					btnCancelLabel : 'Không',
					btnOKLabel : 'Có',
					callback : function(result) {
						if (result) {
							$("#mainFinanceForm").attr("action",
									"${url}/kyketoan/dong/" + maKyKt);
							$("#mainFinanceForm").attr("method", "GET");
							$("#mainFinanceForm").submit();
						}
					}
				});

		return false;
	}

	function xacNhanMo(maKyKt) {
		BootstrapDialog.confirm({
			title : 'Xác nhận',
			message : 'Bạn muốn mở kỳ kế toán này không ?<br/>Mã kỳ kế toán: '
					+ maKyKt,
			type : 'type-info',
			closable : true,
			draggable : true,
			btnCancelLabel : 'Không',
			btnOKLabel : 'Có',
			callback : function(result) {
				if (result) {
					$("#mainFinanceForm").attr("action",
							"${url}/kyketoan/mo/" + maKyKt);
					$("#mainFinanceForm").attr("method", "GET");
					$("#mainFinanceForm").submit();
				}
			}
		});

		return false;
	}

	function xacNhanMacDinh(maKyKt) {
		BootstrapDialog
				.confirm({
					title : 'Xác nhận',
					message : 'Bạn muốn đặt mặc định kỳ kế toán này không ?<br/>Mã kỳ kế toán: '
							+ maKyKt,
					type : 'type-info',
					closable : true,
					draggable : true,
					btnCancelLabel : 'Không',
					btnOKLabel : 'Có',
					callback : function(result) {
						if (result) {
							$("#mainFinanceForm").attr("action",
									"${url}/kyketoan/macdinh/" + maKyKt);
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
		$("#mainFinanceForm").attr("action", "${url}/diachi/danhsach");
		$("#mainFinanceForm").attr("method", "POST");

	});
</script>

<h4>Danh sách kỳ kế toán</h4>

<div class="pull-right">
	<a href="${url}/kyketoan/taomoi" class="btn btn-info btn-sm"> <span
		class="glyphicon glyphicon-plus"></span> Tạo mới
	</a>
</div>
<br />
<br />

<div class="table-responsive">
	<table class="table table-bordered table-hover">
		<thead>
			<tr>
				<th>Tên kỳ kế toán</th>
				<th>Bắt đầu</th>
				<th>Kế thúc</th>
				<th>Trạng thái</th>
				<th>Mặc định</th>
				<!-- <th></th> -->
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${kyKeToanDs}" var="kyKeToan">
				<tr>
					<td><a href="${url}/kyketoan/xem/${kyKeToan.maKyKt}">${kyKeToan.tenKyKt}</a></td>
					<td><fmt:formatDate value="${kyKeToan.batDau}"
							pattern="dd/M/yyyy" type="Date" dateStyle="SHORT" /></td>
					<td><fmt:formatDate value="${kyKeToan.ketThuc}"
							pattern="dd/M/yyyy" type="Date" dateStyle="SHORT" /></td>
					<td><c:choose>
							<c:when test="${kyKeToan.trangThai==KyKeToan.MO}">
								<span class="glyphicon glyphicon-ok" style="color: blue;"
									title="Mở"></span>
							</c:when>
							<c:otherwise>
								<span class="glyphicon glyphicon-remove" style="color: red;"
									title="Đóng"></span>
							</c:otherwise>
						</c:choose></td>
					<td><c:choose>
							<c:when test="${kyKeToan.macDinh==KyKeToan.MAC_DINH}">
								<span class="glyphicon glyphicon-ok" style="color: blue;"></span>
							</c:when>
						</c:choose></td>
				</tr>
			</c:forEach>

		</tbody>
	</table>
</div>

