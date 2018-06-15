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

	});
</script>

<h4>Danh mục bút toán kết chuyển tự động</h4>

<c:choose>
	<c:when test="${kyKeToan.trangThai== KyKeToan.DONG}">
		<div class="pull-left">
			<i>Kỳ kế toán hiện tại bị đóng, bạn chỉ xem, không thể thêm mới
				hoặc sửa dữ liệu.</i>
		</div>
		<div class="pull-right">
			<a href="${url}/chungtu/ketchuyen/danhsach"
				class="btn btn-info btn-sm"> Danh sách kết chuyển </a>
		</div>
	</c:when>
	<c:otherwise>
		<div class="pull-right">
			<a href="${url}/chungtu/ketchuyen/danhsach"
				class="btn btn-info btn-sm"> Danh sách kết chuyển </a> <a
				href="${url}/chungtu/ketchuyen/buttoan/taomoi"
				class="btn btn-info btn-sm"> <span
				class="glyphicon glyphicon-plus"></span> Tạo mới
			</a>
		</div>
	</c:otherwise>
</c:choose>
<br />
<br />

<div class="table-responsive">
	<table class="table table-bordered table-hover">
		<thead>
			<tr>
				<!-- <th class="text-center">Mã kết chuyển</th> -->
				<th class="text-center">Tên kết chuyển</th>
				<th class="text-center">Tài khoản nợ</th>
				<th class="text-center">Tài khoản có</th>
				<th class="text-center">Công thức</th>
				<th class="text-center">Thứ tự</th>
				<th class="text-center">Phân loại</th>
				<!-- <th class="text-center">Mô tả</th> -->
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${ketChuyenButToanDs}" var="ketChuyenButToan"
				varStatus="status">
				<tr>
					<%-- <td class="text-center" style="width: 50px;">${ketChuyenButToan.maKc}</td> --%>
					<td style="width: 300px;"><a
						href="${url}/chungtu/ketchuyen/buttoan/xem/${ketChuyenButToan.maKc}">${ketChuyenButToan.tenKc}</a></td>
					<td  style="width: 300px;">${ketChuyenButToan.taiKhoanNo.loaiTaiKhoan.maTk}-
						${ketChuyenButToan.taiKhoanNo.loaiTaiKhoan.tenTk}</td>
					<td  style="width: 300px;">${ketChuyenButToan.taiKhoanCo.loaiTaiKhoan.maTk}-
						${ketChuyenButToan.taiKhoanCo.loaiTaiKhoan.tenTk}</td>
					<td>${ketChuyenButToan.congThuc}</td>
					<td>${ketChuyenButToan.thuTu}</td>
					<td><c:choose>
							<c:when
								test="${ketChuyenButToan.loaiKc==KetChuyenButToan.KCBT_DAU_KY }">Kết chuyển đầu kỳ</c:when>
							<c:otherwise>Kết chuyển cuối kỳ</c:otherwise>
						</c:choose></td>
					<%-- <td class="text-center">${ketChuyenButToan.moTa}</td> --%>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>