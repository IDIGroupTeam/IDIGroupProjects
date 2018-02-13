<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<script type="text/javascript">
	function xacNhanXoa(maDv){
		BootstrapDialog.confirm({
			title : 'Xác nhận',
			message : 'Bạn muốn xóa hàng hóa này không ?<br/>Mã hàng hóa: '+maDv,
			type : 'type-info',
			closable : true,
			draggable : true,
			btnCancelLabel : 'Không',
			btnOKLabel : 'Có',
			callback : function(result) {
				if (result) {
					$("#mainFinanceForm").attr(
							"action", "${url}/hanghoa/xoa/"+maDv);
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

<h4>Danh sách hàng hóa</h4>
<div class="pull-right">
	<a href="${url}/hanghoa/taomoi" class="btn btn-info btn-sm"> <span
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
				<th style="width: 80px;">Mã hàng hóa</th>
				<th>Tên hàng hóa</th>
				<th>Đơn vị</th>
				<th>Nhóm</th>
				<th>Giá bán</th>
				<th>Giá mua</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${hangHoaDs}" var="hangHoa" varStatus="status">
				<tr>
					<td>${hangHoa.maHh}</td>
					<td>${hangHoa.tenHh}</td>
					<td>${hangHoa.donVi.tenDv}</td>
					<td>${hangHoa.nhomHh.tenNhomHh}</td>
					<th></th>
					<th></th>
					<td><div class="btn-group btn-group-sm">
							<a href="${url}/hanghoa/sua/${hangHoa.maHh}" class="btn"
								title="Sửa"> <span class="glyphicon glyphicon-edit"></span>
							</a><a href="${url}/hanghoa/xoa/${hangHoa.maHh}" class="btn"
								title="Sửa" onclick="return xacNhanXoa(${hangHoa.maHh});"> <span
								class="glyphicon glyphicon-remove"></span>
							</a>
						</div></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
