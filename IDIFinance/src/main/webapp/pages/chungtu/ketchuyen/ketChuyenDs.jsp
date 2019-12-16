<%@page import="com.idi.finance.bean.kyketoan.KyKeToan"%>
<%@page import="com.idi.finance.bean.chungtu.KetChuyenButToan"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<script type="text/javascript">
	//Shorthand for $( document ).ready()
	$(function() {
		// Khởi tạo action/method cho mainFinanceForm form
		$('#dataTable').DataTable({
			ordering : false,
			language : vi
		});
	});
</script>

<h4>Danh sách kết chuyển</h4>

<c:choose>
	<c:when test="${kyKeToan.trangThai== KyKeToan.DONG}">
		<div class="pull-left">
			<i>Kỳ kế toán hiện tại bị đóng, bạn chỉ xem, không thể thêm mới
				hoặc sửa dữ liệu.</i>
		</div>
		<div class="pull-right">
			<a href="${url}/chungtu/ketchuyen/buttoan/danhsach"
				class="btn btn-info btn-sm"> Danh sách kết chuyển tự động </a>
		</div>
	</c:when>
	<c:otherwise>
		<div class="pull-right">
			<%-- <a
				href="${url}/chungtu/ketchuyen/taomoi/${KetChuyenButToan.KCBT_DAU_KY}"
				class="btn btn-info btn-sm"> <span
				class="glyphicon glyphicon-plus"></span> Tạo kết chuyển đầu kỳ
			</a> --%>
			<a
				href="${url}/chungtu/ketchuyen/taomoi/${KetChuyenButToan.KCBT_CUOI_KY}"
				class="btn btn-info btn-sm"> <span
				class="glyphicon glyphicon-plus"></span> Tạo kết chuyển cuối kỳ
			</a> <a href="${url}/chungtu/ketchuyen/buttoan/danhsach"
				class="btn btn-info btn-sm"> Danh sách kết chuyển tự động </a>
		</div>
	</c:otherwise>
</c:choose>
<br />
<br />

<div class="table-responsive">
	<table id="dataTable" class="table table-bordered table-hover">
		<thead>
			<tr>
				<th class="text-center" style="width: 100px;">Số kết chuyển</th>
				<th class="text-center">Tên kết chuyển</th>
				<th class="text-center">Ngày kết chuyển</th>
				<th class="text-center" style="width: 200px;">Loại kết chuyển</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${ketChuyenDs}" var="ketChuyen" varStatus="status">
				<tr>
					<td class="text-center" style="width: 100px;">${ketChuyen.loaiCt}${ketChuyen.soCt}</td>
					<td><a href="${url}/chungtu/ketchuyen/xem/${ketChuyen.maCt}">${ketChuyen.lyDo}</a></td>
					<td class="text-center"><fmt:formatDate
							value="${ketChuyen.ngayHt}" pattern="dd/M/yyyy" type="Date"
							dateStyle="SHORT" /></td>
					<td style="width: 200px;"><c:choose>
							<c:when
								test="${chungTu.tinhChatCt==KetChuyenButToan.KCBT_DAU_KY }">Kết chuyển đầu kỳ</c:when>
							<c:otherwise>Kết chuyển cuối kỳ</c:otherwise>
						</c:choose></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>