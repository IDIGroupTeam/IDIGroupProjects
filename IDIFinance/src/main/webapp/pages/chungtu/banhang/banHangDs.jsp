<%@page import="com.idi.finance.bean.doituong.DoiTuong"%>
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

<h4>Danh mục chứng từ bán hàng</h4>
<p>
	<!-- <i>Ghi nợ vào các tài khoản tiền mặt: 111, 1111, ...</i> -->
</p>

<c:choose>
	<c:when test="${kyKeToan.trangThai==KyKeToan.DONG}">
		<div class="pull-left">
			<i>Kỳ kế toán hiện tại bị đóng, bạn chỉ xem, không thể thêm mới
				hoặc sửa dữ liệu.</i>
		</div>
		<div class="pull-right"></div>
	</c:when>
	<c:otherwise>
		<div class="pull-left">
			<c:if test="${not empty message}">
				<span class="text-danger"><i>${message}</i></span>
			</c:if>
		</div>
		<div class="pull-right">
			<c:choose>
				<c:when test="${kyKeToan.trangThai!= KyKeToan.DONG}">
					<%-- <a
						href="${url}/chungtu/banhang/taomoi" class="btn btn-info btn-sm">
						<span class="glyphicon glyphicon-plus"></span> Tạo mới
					</a> --%>
					<a href="${url}/chungtu/banhang/taomoi/1"
						class="btn btn-info btn-sm"> <span
						class="glyphicon glyphicon-plus"></span> Bán hàng trong nước
					</a>
					<a href="${url}/chungtu/banhang/taomoi/2"
						class="btn btn-info btn-sm"> <span
						class="glyphicon glyphicon-plus"></span> Xuất khẩu hàng hóa
					</a>
					<%-- <a href="${url}/chungtu/banhang/taomoi/3" class="btn btn-info btn-sm">
						<span class="glyphicon glyphicon-plus"></span> Cung cấp dịch vụ trong
						nước
					</a> --%>
					<%-- <a href="${url}/chungtu/banhang/taomoi/3" class="btn btn-info btn-sm">
						<span class="glyphicon glyphicon-plus"></span> Cung cấp dịch vụ ra nước ngoài
					</a> --%>
				</c:when>
			</c:choose>
			<a
				href="${url}/chungtu/banhang/danhsach/pdf/<fmt:formatDate
							value='${mainFinanceForm.dau}' pattern='dd_MM_yyyy' />/<fmt:formatDate
							value='${mainFinanceForm.cuoi}' pattern='dd_MM_yyyy' />"
				class="btn btn-info btn-sm"> <span
				class="glyphicon glyphicon-download"></span> Pdf
			</a>
		</div>
	</c:otherwise>
</c:choose>
<br />
<br />

<div class="table-responsive">
	<table id="dataTable" class="table table-bordered table-hover">
		<thead>
			<tr>
				<th class="text-center" colspan="2">Phiếu nhập kho</th>
				<th class="text-center" rowspan="2">Lý do</th>
				<th class="text-center" rowspan="2">Tổng số tiền<br> (VND)
				</th>
				<th class="text-center" rowspan="2">Đối tượng</th>
				<th class="text-center" rowspan="2">Địa chỉ</th>
				<th class="text-center" rowspan="2">Mã số thuế</th>
				<th class="text-center" rowspan="2">Loại đối tượng</th>
			</tr>
			<tr>
				<th class="text-center">Ngày ghi sổ</th>
				<th class="text-center">Số</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${banHangDs}" var="banHang" varStatus="status">
				<tr>
					<td class="text-center" style="width: 50px;"><fmt:formatDate
							value="${banHang.ngayHt}" pattern="dd/M/yyyy" type="Date"
							dateStyle="SHORT" /></td>
					<td class="text-center" style="width: 50px;">${banHang.loaiCt}${banHang.soCt}</td>
					<td><a href="${url}/chungtu/banhang/xem/${banHang.maCt}">${banHang.lyDo}</a></td>
					<td align="right"><fmt:formatNumber
							value="${banHang.soTien.giaTri}" maxFractionDigits="0"></fmt:formatNumber></td>
					<td><c:choose>
							<c:when
								test="${banHang.doiTuong.loaiDt == DoiTuong.KHACH_VANG_LAI}">
								${banHang.doiTuong.nguoiNop}
							</c:when>
							<c:otherwise>${banHang.doiTuong.tenDt}
							</c:otherwise>
						</c:choose></td>
					<td>${banHang.doiTuong.diaChi}</td>
					<td>${banHang.doiTuong.maThue}</td>
					<td><c:choose>
							<c:when test="${banHang.doiTuong.loaiDt == DoiTuong.NHAN_VIEN}">
								Nhân viên
							</c:when>
							<c:when test="${banHang.doiTuong.loaiDt == DoiTuong.KHACH_HANG}">
								Khách hàng
							</c:when>
							<c:when
								test="${banHang.doiTuong.loaiDt == DoiTuong.NHA_CUNG_CAP}">
								Nhà cung cấp
							</c:when>
							<c:when
								test="${banHang.doiTuong.loaiDt == DoiTuong.KHACH_VANG_LAI}">
								Khách vãng lai
							</c:when>
						</c:choose></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>