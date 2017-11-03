<%@page import="com.idi.finance.bean.chungtu.ChungTu"%>
<%@page import="com.idi.finance.bean.chungtu.DoiTuong"%>
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

<h4>Sổ tiền mặt</h4>
<p>
	<i>Tài khoản tiền gửi ngân hàng 112, 1121 ... </i>
</p>

<div class="pull-right">
	<i>(*): Mặc định là tiền VND</i>
</div>

<div class="row">
	<table class="table table-bordered table-hover">
		<thead>
			<tr>
				<th class="text-center" colspan="2">Chứng từ</th>
				<th class="text-center" rowspan="2">Diễn dải</th>
				<th class="text-center" rowspan="2" style="width: 100px;">Tài
					khoản đối ứng</th>
				<th class="text-center" colspan="2">Số tiền (*)</th>
				<th class="text-center" rowspan="2" style="width: 100px;">Ghi
					chú</th>
			</tr>
			<tr>
				<th class="text-center" style="width: 100px;">Ngày ghi sổ</th>
				<th class="text-center" style="width: 50px;">Số</th>
				<th class="text-center">Nợ</th>
				<th class="text-center">Có</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td></td>
				<td></td>
				<td><b>Số dư đầu kỳ</b></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<c:forEach items="${nghiepVuKeToanDs}" var="nghiepVuKeToan"
				varStatus="status">
				<tr>
					<td><fmt:formatDate value="${nghiepVuKeToan.chungTu.ngayHt}"
							pattern="dd/M/yyyy" type="Date" dateStyle="SHORT" /></td>
					<td>${nghiepVuKeToan.chungTu.loaiCt}${nghiepVuKeToan.chungTu.soCt}</td>
					<c:choose>
						<c:when
							test="${nghiepVuKeToan.chungTu.loaiCt==ChungTu.CHUNG_TU_BAO_CO}">
							<td>${nghiepVuKeToan.taiKhoanCo.lyDo}</td>
							<td>${nghiepVuKeToan.taiKhoanCo.taiKhoan.maTk}</td>
							<td><fmt:formatNumber
									value="${nghiepVuKeToan.taiKhoanCo.soTien*nghiepVuKeToan.chungTu.soTien.tien.banRa}"
									maxFractionDigits="2"></fmt:formatNumber></td>
							<td></td>
							<td></td>
						</c:when>
						<c:when
							test="${nghiepVuKeToan.chungTu.loaiCt==ChungTu.CHUNG_TU_BAO_NO}">
							<td>${nghiepVuKeToan.taiKhoanNo.lyDo}</td>
							<td>${nghiepVuKeToan.taiKhoanNo.taiKhoan.maTk}</td>
							<td></td>
							<td><fmt:formatNumber
									value="${nghiepVuKeToan.taiKhoanNo.soTien*nghiepVuKeToan.chungTu.soTien.tien.banRa}"
									maxFractionDigits="2"></fmt:formatNumber></td>
							<td></td>
						</c:when>
						<c:otherwise>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</c:otherwise>
					</c:choose>
				</tr>
			</c:forEach>
			<tr>
				<td></td>
				<td></td>
				<td><b>Tổng phát sinh</b></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td><b>Số dư cuối kỳ</b></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</tbody>
	</table>
</div>