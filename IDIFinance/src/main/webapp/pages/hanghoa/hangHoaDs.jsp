<%@page import="com.idi.finance.bean.hanghoa.HangHoa"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<script type="text/javascript">
	function xacNhanXoa(maDv) {
		BootstrapDialog.confirm({
			title : 'Xác nhận',
			message : 'Bạn muốn xóa hàng hóa này không ?<br/>Mã hàng hóa: '
					+ maDv,
			type : 'type-info',
			closable : true,
			draggable : true,
			btnCancelLabel : 'Không',
			btnOKLabel : 'Có',
			callback : function(result) {
				if (result) {
					$("#mainFinanceForm").attr("action",
							"${url}/hanghoa/xoa/" + maDv);
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
		$(document).ready(function() {
			$('#dataTable').DataTable({
				ordering : false
			});
		});
	});
</script>

<h4>Danh sách hàng hóa</h4>
<div class="pull-right">
	<a href="${url}/hanghoa/taomoi" class="btn btn-info btn-sm"> <span
		class="glyphicon glyphicon-plus"></span> Tạo mới
	</a>
</div>
<br />
<br />

<div class="table-responsive">
	<table id="dataTable" class="table table-bordered table-hover">
		<thead>
			<tr>
				<!-- <th>STT</th> -->
				<th style="width: 10px;">#</th>
				<th style="width: 80px;">Mã hàng hóa</th>
				<th>Tên hàng hóa</th>
				<th>Đơn vị</th>
				<th>Tính chất</th>
				<th>Nhóm</th>
				<th>Kho mặc định</th>
				<th>Tk kho</th>
				<th>Tk doanh thu</th>
				<th>Tk chi phí</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${hangHoaDs}" var="hangHoa" varStatus="status">
				<tr>
					<td>${status.index+1}</td>
					<td>${hangHoa.kyHieuHh}</td>
					<td><a href="${url}/hanghoa/xem/${hangHoa.maHh}">${hangHoa.tenHh}</a></td>
					<td>${hangHoa.donVi.tenDv}</td>
					<td><c:choose>
							<c:when test="${hangHoa.tinhChat == HangHoa.TINH_CHAT_VTHH}">
								Vật tư hàng hóa
							</c:when>
							<c:when test="${hangHoa.tinhChat == HangHoa.TINH_CHAT_DV}">
								Dịch vụ
							</c:when>
							<c:when test="${hangHoa.tinhChat == HangHoa.TINH_CHAT_TP}">
								Thành phẩm
							</c:when>
						</c:choose></td>
					<td>${hangHoa.nhomHh.tenNhomHh}</td>
					<td>${hangHoa.khoMd.tenKho}</td>
					<td>${hangHoa.tkKhoMd.maTk}</td>
					<td>${hangHoa.tkDoanhThuMd.maTk}</td>
					<td>${hangHoa.tkChiPhiMd.maTk}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
