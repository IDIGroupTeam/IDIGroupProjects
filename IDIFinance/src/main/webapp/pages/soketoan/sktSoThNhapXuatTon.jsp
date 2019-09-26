<%@page import="com.idi.finance.bean.chungtu.ChungTu"%>
<%@page import="com.idi.finance.bean.doituong.DoiTuong"%>
<%@page import="com.idi.finance.bean.taikhoan.LoaiTaiKhoan"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

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

<h4>Sổ tổng hợp nhập xuất tồn</h4>
<div class="row">
	<div class="col-sm-4 text-left">
		<i>156 - <c:forEach items="${mainFinanceForm.khoDs}" var="kho">${kho.tenKho}, </c:forEach></i>
	</div>
	<div class="col-sm-4 text-center">
		<i>Từ <fmt:formatDate value="${mainFinanceForm.dau}"
				pattern="dd/M/yyyy" type="Date" dateStyle="SHORT" /> đến <fmt:formatDate
				value="${mainFinanceForm.cuoi}" pattern="dd/M/yyyy" type="Date"
				dateStyle="SHORT" /></i>
	</div>
	<div class="col-sm-4 text-right">
		<i>(*): Mặc định là tiền VND</i>
	</div>
</div>

<div class="table-responsive">
	<table class="table table-bordered table-hover">
		<thead>
			<tr>
				<th rowspan="2" class="text-center">Mã VT</th>
				<th rowspan="2" class="text-center">Tên vật tư</th>
				<th rowspan="2" class="text-center">Đơn vị</th>
				<th colspan="2" class="text-center">Tồn đầu kỳ</th>
				<th colspan="2" class="text-center">Nhập trong kỳ</th>
				<th colspan="2" class="text-center">Xuất trong kỳ</th>
				<th colspan="2" class="text-center">Tồn cuối kỳ</th>
				<!-- <th rowspan="2" class="text-center">Ghi chú</th> -->
			</tr>
			<tr>
				<th class="text-center">Số lượng</th>
				<th class="text-center">Thành tiền (*)</th>
				<th class="text-center">Số lượng</th>
				<th class="text-center">Thành tiền (*)</th>
				<th class="text-center">Số lượng</th>
				<th class="text-center">Thành tiền (*)</th>
				<th class="text-center">Số lượng</th>
				<th class="text-center">Thành tiền (*)</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${duLieuKeToanDs}" var="duLieuKeToan">
				<%-- <tr>
					<td colspan="11"><b>Kỳ <fmt:formatDate
								value="${duLieuKeToan.kyKeToan.dau}" pattern="dd/M/yyyy"></fmt:formatDate>
							- <fmt:formatDate value="${duLieuKeToan.kyKeToan.cuoi}"
								pattern="dd/M/yyyy"></fmt:formatDate></b></td>
				</tr> --%>
				<c:choose>
					<c:when
						test="${not empty duLieuKeToan.duLieuKeToanDs and duLieuKeToan.duLieuKeToanDs.size()>0}">
						<c:forEach items="${duLieuKeToan.duLieuKeToanDs}"
							var="duLieuKeToanCon">
							<tr>
								<td>${duLieuKeToanCon.hangHoa.kyHieuHh}</td>
								<td><a
									href="${url}/soketoan/nhapxuatton/chitiet/${mainFinanceForm.kyKeToan.maKyKt}/${mainFinanceForm.taiKhoan}/${duLieuKeToanCon.hangHoa.maHh}/${khoStrDs}/<fmt:formatDate value='${duLieuKeToan.kyKeToan.dau}' pattern='dd_MM_yyyy'/>/<fmt:formatDate value='${duLieuKeToan.kyKeToan.cuoi}' pattern='dd_MM_yyyy'/>"
									target="_blank">${duLieuKeToanCon.hangHoa.tenHh}</a></td>
								<td>${duLieuKeToanCon.hangHoa.donVi.tenDv}</td>
								<td><fmt:formatNumber
										value="${duLieuKeToanCon.soLuongDuDauKy}" type="NUMBER"
										maxFractionDigits="0"></fmt:formatNumber></td>
								<td class="text-right"><fmt:formatNumber
										value="${duLieuKeToanCon.soDuDauKy}" type="NUMBER"
										maxFractionDigits="0"></fmt:formatNumber></td>
								<td><fmt:formatNumber
										value="${duLieuKeToanCon.soLuongNhapPhatSinh}" type="NUMBER"
										maxFractionDigits="0"></fmt:formatNumber></td>
								<td class="text-right"><fmt:formatNumber
										value="${duLieuKeToanCon.tongNoPhatSinh}" type="NUMBER"
										maxFractionDigits="0"></fmt:formatNumber></td>
								<td><fmt:formatNumber
										value="${duLieuKeToanCon.soLuongXuatPhatSinh}" type="NUMBER"
										maxFractionDigits="0"></fmt:formatNumber></td>
								<td class="text-right"><fmt:formatNumber
										value="${duLieuKeToanCon.tongCoPhatSinh}" type="NUMBER"
										maxFractionDigits="0"></fmt:formatNumber></td>
								<td><fmt:formatNumber
										value="${duLieuKeToanCon.soLuongDuCuoiKy}" type="NUMBER"
										maxFractionDigits="0"></fmt:formatNumber></td>
								<td class="text-right"><fmt:formatNumber
										value="${duLieuKeToanCon.soDuCuoiKy}" type="NUMBER"
										maxFractionDigits="0"></fmt:formatNumber></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="11">Không có dữ liệu</td>
						</tr>
					</c:otherwise>
				</c:choose>

			</c:forEach>
		</tbody>
	</table>
</div>