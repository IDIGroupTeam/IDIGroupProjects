<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<script type="text/javascript">
	function xacNhanXoa(maNhomHh){
		BootstrapDialog.confirm({
			title : 'Xác nhận',
			message : 'Bạn muốn xóa nhóm hàng hóa này không ?<br/>Mã nhóm hàng hóa: '+maNhomHh,
			type : 'type-info',
			closable : true,
			draggable : true,
			btnCancelLabel : 'Không',
			btnOKLabel : 'Có',
			callback : function(result) {
				if (result) {
					$("#mainFinanceForm").attr(
							"action", "${url}/hanghoa/nhom/xoa/"+maNhomHh);
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

<h4>Danh sách nhóm hàng hóa</h4>
<div class="pull-right">
	<a href="${url}/hanghoa/nhom/taomoi" class="btn btn-info btn-sm"> <span
		class="glyphicon glyphicon-plus"></span> Tạo mới
	</a>
</div>
<br />
<br />

<div class="table-responsive">
	<table class="table table-bordered table-hover">
		<thead>
			<tr>
				<th style="width: 100px;">Mã nhóm hàng hóa</th>
				<th colspan="${nhomHangHoa.doSau}">Tên nhóm hàng hóa</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${nhomHangHoa.nhomHhDs}" var="nhomHangHoaCon"
				varStatus="status">
				<tr>
					<td>${nhomHangHoaCon.kyHieuNhomHh}</td>
					<c:forEach begin="1" end="${nhomHangHoaCon.muc-1}">
						<td></td>
					</c:forEach>
					<td>${nhomHangHoaCon.tenNhomHh}</td>
					<c:forEach begin="${nhomHangHoaCon.muc+1}"
						end="${nhomHangHoa.doSau-1}">
						<td></td>
					</c:forEach>
					<td><div class="btn-group btn-group-sm">
							<a href="${url}/hanghoa/nhom/sua/${nhomHangHoaCon.maNhomHh}"
								class="btn" title="Sửa"> <span
								class="glyphicon glyphicon-edit"></span>
							</a>
							<c:if test="${nhomHangHoaCon.xoa}">
								<a href="${url}/hanghoa/nhom/xoa/${nhomHangHoaCon.maNhomHh}"
									class="btn" title="Sửa"
									onclick="return xacNhanXoa(${nhomHangHoaCon.maNhomHh});"> <span
									class="glyphicon glyphicon-remove"></span>
								</a>
							</c:if>
						</div></td>
				</tr>

				<c:set var="doSau" value="${nhomHangHoa.doSau}" scope="request" />
				<c:set var="nhomHangHoaConDs" value="${nhomHangHoaCon.nhomHhDs}"
					scope="request" />
				<jsp:include page="nhomHangHoaConDs.jsp" />
			</c:forEach>
		</tbody>
	</table>
</div>
