<%@page import="com.idi.finance.bean.taikhoan.LoaiTaiKhoan"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<script type="text/javascript">
	function xacNhanXoaLinhVuc(maLv){
		BootstrapDialog.confirm({
			title : 'Xác nhận',
			message : 'Bạn muốn xóa lĩnh vực này không ?<br/>Mã lĩnh vực: '+maLv,
			type : 'type-info',
			closable : true,
			draggable : true,
			btnCancelLabel : 'Không',
			btnOKLabel : 'Có',
			callback : function(result) {
				if (result) {
					$("#mainFinanceForm").attr(
							"action", "${url}/congviec/linhvuc/xoa/"+maLv);
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

<h4>Danh sách các lĩnh vực công việc</h4>
<div class="pull-right">
	<a href="${url}/congviec/linhvuc/taomoi" class="btn btn-info btn-sm">
		<span class="glyphicon glyphicon-plus"></span> Tạo mới
	</a>
</div>
<br />
<br />

<div class="table-responsive">
	<table class="table table-bordered table-hover">
		<thead>
			<tr>
				<!-- <th>STT</th> -->
				<th>Mã lĩnh vực</th>
				<th>Tên lĩnh vực</th>
				<th>Mô tả</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${linhVucDs}" var="linhVuc" varStatus="status">
				<tr>
					<td>${linhVuc.maLv}</td>
					<td>${linhVuc.tenLv}</td>
					<td>${linhVuc.moTa}</td>
					<td><div class="btn-group btn-group-sm">
							<a href="${url}/congviec/linhvuc/sua/${linhVuc.maLv}" class="btn"
								title="Sửa"> <span class="glyphicon glyphicon-edit"></span>
							</a><a href="${url}/congviec/linhvuc/xoa/${linhVuc.maLv}" class="btn"
								title="Xóa" onclick="return xacNhanXoaLinhVuc(${linhVuc.maLv});">
								<span class="glyphicon glyphicon-remove"></span>
							</a>
						</div></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
