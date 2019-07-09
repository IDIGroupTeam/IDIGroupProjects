<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<script type="text/javascript">
function xacNhanXoaNghiepVu(maNv){
	BootstrapDialog.confirm({
		title : 'Xác nhận',
		message : 'Bạn muốn xóa nghiệp vụ này không ?<br/>Mã nghiệp vụ: '+maNv,
		type : 'type-info',
		closable : true,
		draggable : true,
		btnCancelLabel : 'Không',
		btnOKLabel : 'Có',
		callback : function(result) {
			if (result) {
				$("#mainFinanceForm").attr(
						"action", "${url}/congviec/nghiepvu/xoa/"+maNv);
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


<h4>Danh sách nghiệp vụ</h4>
<div class="pull-right">
	<a href="${url}/congviec/nghiepvu/taomoi" class="btn btn-info btn-sm">
		<span class="glyphicon glyphicon-plus"></span> Tạo mới
	</a>
</div>
<br />
<br />

<div class="table-responsive">
	<table class="table table-bordered table-hover">
		<thead>
			<tr>
				<th class="text-center align-middle">Mã</th>
				<th class="text-center align-middle">Tên nghiệp vụ</th>
				<th class="text-center">Mô tả</th>
				<th class="text-center align-middle" style="width: 100px;">Lĩnh
					vực</th>
				<th class="text-center align-middle" style="width: 110px;">Độ
					khó</th>
				<th class="text-center align-middle" style="width: 90px;"></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${nghiepVuDs}" var="nghiepVu" varStatus="status">
				<tr>
					<td>${nghiepVu.maNv}</td>
					<td>${nghiepVu.tenNv}</td>
					<td>${nghiepVu.moTa}</td>
					<td>${nghiepVu.linhVuc.tenLv}</td>
					<td><c:forEach begin="1" end="${nghiepVu.doKho}">
							<span class="glyphicon glyphicon-star" style="color: blue;"></span>
						</c:forEach> <c:choose>
							<c:when test="${nghiepVu.doKho<5}">
								<c:forEach begin="${nghiepVu.doKho+1}" end="5">
									<span class="glyphicon glyphicon-star-empty"
										style="color: blue;"></span>
								</c:forEach>
							</c:when>
						</c:choose></td>
					<td><div class="btn-group btn-group-sm">
							<a href="${url}/congviec/nghiepvu/sua/${nghiepVu.maNv}"
								class="btn" title="Sửa"> <span
								class="glyphicon glyphicon-edit"></span>
							</a><a href="${url}/congviec/nghiepvu/xoa/${nghiepVu.maNv}"
								class="btn" title="Xóa"
								onclick="return xacNhanXoaNghiepVu(${nghiepVu.maNv});"> <span
								class="glyphicon glyphicon-remove"></span>
							</a>
						</div></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>