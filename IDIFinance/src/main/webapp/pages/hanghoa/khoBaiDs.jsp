<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<script type="text/javascript">
	function xacNhanXoa(maKho){
		BootstrapDialog.confirm({
			title : 'Xác nhận',
			message : 'Bạn muốn xóa kho này không ?<br/>Mã kho: '+maKho,
			type : 'type-info',
			closable : true,
			draggable : true,
			btnCancelLabel : 'Không',
			btnOKLabel : 'Có',
			callback : function(result) {
				if (result) {
					$("#mainFinanceForm").attr(
							"action", "${url}/hanghoa/kho/xoa/"+maKho);
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

<h4>Danh sách kho</h4>
<div class="pull-right">
	<a href="${url}/hanghoa/kho/taomoi" class="btn btn-info btn-sm"> <span
		class="glyphicon glyphicon-plus"></span> Tạo mới
	</a>
</div>
<br />
<br />

<div class="table-responsive">
	<table class="table table-bordered table-hover">
		<thead>
			<tr>
				<th style="width: 80px;">Mã kho</th>
				<th>Tên kho</th>
				<th>Địa chỉ</th>
				<!-- <th>Tài khoản kho</th> -->
				<th>Mô tả</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${khoBaiDs}" var="khoBai" varStatus="status">
				<tr>
					<td>${khoBai.kyHieuKho}</td>
					<td>${khoBai.tenKho}</td>
					<td>${khoBai.diaChi}</td>
					<!-- <td></td> -->
					<td>${khoBai.moTa}</td>
					<td><div class="btn-group btn-group-sm">
							<a href="${url}/hanghoa/kho/sua/${khoBai.maKho}" class="btn"
								title="Sửa"> <span class="glyphicon glyphicon-edit"></span>
							</a><a href="${url}/hanghoa/kho/xoa/${khoBai.maKho}" class="btn"
								title="Sửa" onclick="return xacNhanXoa(${khoBai.maKho});"> <span
								class="glyphicon glyphicon-remove"></span>
							</a>
						</div></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
