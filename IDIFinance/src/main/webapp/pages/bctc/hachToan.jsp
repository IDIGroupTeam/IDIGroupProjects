<%@page import="com.idi.finance.bean.taikhoan.LoaiTaiKhoan"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<script type="text/javascript">
	// Shorthand for $( document ).ready()
	$(function() {
		$("#submitBt").click(function() {
			$("#mainFinanceForm").attr("action", "${url}/bctc/cdkt/capnhat");
			$("#mainFinanceForm").attr("method", "POST");

			$("#mainFinanceForm").submit();
		});
	});
</script>

<h4>Cập nhật bảng cân đối kế toán: Hạch toán</h4>
<p>
	<i>Từ <fmt:formatDate value="${mainFinanceForm.dau}"
			pattern="dd/M/yyyy" type="Date" dateStyle="SHORT" /> đến <fmt:formatDate
			value="${mainFinanceForm.cuoi}" pattern="dd/M/yyyy" type="Date"
			dateStyle="SHORT" /></i>
</p>

<div class="pull-right">
	<i>(*): Mặc định là tiền VND</i>
</div>

<div class="table-responsive form-group">
	<form:hidden path="ky" />
	<form:hidden path="dau" />
	<form:hidden path="cuoi" />
	<table class="table table-bordered table-hover">
		<thead>
			<tr>
				<th class="text-center" colspan="2">Chứng từ</th>
				<th class="text-center" rowspan="2">Diễn dải</th>
				<th class="text-center" rowspan="2">Tài khoản</th>
				<th class="text-center" colspan="2">Số tiền (*)</th>
				<th class="text-center" rowspan="2">Chỉ tiêu CĐKT</th>
			</tr>
			<tr>
				<th class="text-center" style="width: 100px;">Ngày ghi sổ</th>
				<th class="text-center" style="width: 50px;">Số</th>
				<th class="text-center">Nợ</th>
				<th class="text-center">Có</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${mainFinanceForm.taiKhoanDs}" var="taiKhoan"
				varStatus="status">

				<tr>
					<td><fmt:formatDate value="${taiKhoan.chungTu.ngayHt}"
							pattern="dd/M/yyyy" type="Date" dateStyle="SHORT" /></td>
					<td>${taiKhoan.chungTu.loaiCt}${taiKhoan.chungTu.soCt}</td>
					<td>${taiKhoan.lyDo}</td>
					<td>${taiKhoan.loaiTaiKhoan.maTk}</td>
					<c:choose>
						<c:when test="${taiKhoan.soDu==LoaiTaiKhoan.NO}">
							<td><fmt:formatNumber
									value="${taiKhoan.soTien.soTien*taiKhoan.chungTu.loaiTien.banRa}"
									maxFractionDigits="0"></fmt:formatNumber></td>
							<td></td>
						</c:when>
						<c:when test="${taiKhoan.soDu==LoaiTaiKhoan.CO}">
							<td></td>
							<td><fmt:formatNumber
									value="${taiKhoan.soTien.soTien*taiKhoan.chungTu.loaiTien.banRa}"
									maxFractionDigits="0"></fmt:formatNumber></td>
						</c:when>
						<c:otherwise>
							<td></td>
							<td></td>
						</c:otherwise>
					</c:choose>
					<td><form:select
							path="taiKhoanDs[${status.index}].bai.assetCode"
							class="form-control">
							<form:option value=""></form:option>
							<form:options items="${cdktMap[taiKhoan.loaiTaiKhoan.maTk]}"
								itemLabel="assetCodeName" itemValue="assetCode" />
						</form:select> <form:hidden path="taiKhoanDs[${status.index}].chungTu.maCt" />
						<form:hidden path="taiKhoanDs[${status.index}].loaiTaiKhoan.maTk" />
						<form:hidden path="taiKhoanDs[${status.index}].soDu" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<div class="row form-group">
	<div class="col-sm-4">
		<a href="${url}/bctc/cdkt/danhsach" class="btn btn-info btn-sm">Hủy</a>
		<button id="submitBt" type="submit" class="btn btn-info btn-sm">Lưu
			và Cập nhật bảng cân đối kế toán</button>
	</div>
</div>