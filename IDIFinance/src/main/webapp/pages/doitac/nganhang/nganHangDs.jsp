<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<script type="text/javascript">
	function xacNhanXoa(maNh){
		BootstrapDialog.confirm({
			title : 'Xác nhận',
			message : 'Bạn muốn xóa ngân hàng này không ?<br/>Mã ngân hàng: '+maNh,
			type : 'type-info',
			closable : true,
			draggable : true,
			btnCancelLabel : 'Không',
			btnOKLabel : 'Có',
			callback : function(result) {
				if (result) {
					$("#mainFinanceForm").attr(
							"action", "${url}/nganhang/xoa/"+maNh);
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
		$('#dataTable').DataTable({
			ordering : false,
			language : vi
		});
	});
</script>

<h4>Danh sách ngân hàng</h4>

<div class="pull-right">
	<a href="${url}/nganhang/taomoi" class="btn btn-info btn-sm"> <span
		class="glyphicon glyphicon-plus"></span> Tạo mới
	</a>
</div>
<br />
<br />

<div class="table-responsive">
	<table id="dataTable" class="table table-bordered table-hover">
		<thead>
			<tr>
				<th>Tên viết tắt</th>
				<th>Tên đầy đủ</th>
				<th>Tên tiếng anh</th>
				<th>Địa chỉ</th>
				<!-- <th>Chú thích</th> -->
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${nganHangDs}" var="nganHang">
				<tr>
					<td>${nganHang.tenVt}</td>
					<td>${nganHang.tenDd}</td>
					<td>${nganHang.tenTa}</td>
					<td>${nganHang.diaChi}</td>
					<%-- <td>${nganHang.moTa}</td> --%>
					<td><div class="btn-group btn-group-sm">
							<a href="${url}/nganhang/sua/${nganHang.maNh}" class="btn"
								title="Sửa"> <span class="glyphicon glyphicon-edit"></span>
							</a><a href="${url}/nganhang/xoa/${nganHang.maNh}" class="btn"
								title="Xóa" onclick="return xacNhanXoa(${nganHang.maNh});">
								<span class="glyphicon glyphicon-remove"></span>
							</a>
						</div></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>