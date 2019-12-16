<%@page import="com.idi.finance.bean.kyketoan.KyKeToan"%>
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

<h4>Danh mục phiếu kế toán tổng hợp</h4>
<p>
	<i></i>
</p>

<c:choose>
	<c:when test="${kyKeToan.trangThai== KyKeToan.DONG}">
		<div class="pull-left">
			<i>Kỳ kế toán hiện tại bị đóng, bạn chỉ xem, không thể thêm mới
				hoặc sửa dữ liệu.</i>
		</div>
		<div class="pull-right">
			<i>(*): Mặc định là tiền VND</i>&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
	</c:when>
	<c:otherwise>
		<div class="pull-left">
			<c:if test="${not empty message}">
				<span class="text-danger"><i>${message}</i></span>
			</c:if>
		</div>
		<div class="pull-right">
			<i>(*): Mặc định là tiền VND</i>&nbsp;&nbsp;&nbsp;&nbsp;
			<c:choose>
				<c:when test="${kyKeToan.trangThai!= KyKeToan.DONG}">
					<a href="${url}/chungtu/ktth/taomoi" class="btn btn-info btn-sm">
						<span class="glyphicon glyphicon-plus"></span> Tạo mới
					</a>
				</c:when>
			</c:choose>
			<a
				href="${url}/chungtu/ktth/danhsach/pdf/<fmt:formatDate
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
				<th class="text-center" colspan="2">Phiếu KTTH</th>
				<th class="text-center" rowspan="2">Lý do</th>
				<th class="text-center" rowspan="2">Tổng số tiền (*)</th>
			</tr>
			<tr>
				<th class="text-center">Ngày ghi sổ</th>
				<th class="text-center">Số</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${keToanTongHopDs}" var="keToanTongHop"
				varStatus="status">
				<tr>
					<td class="text-center" style="width: 50px;"><fmt:formatDate
							value="${keToanTongHop.ngayHt}" pattern="dd/M/yyyy" type="Date"
							dateStyle="SHORT" /></td>
					<td class="text-center" style="width: 50px;">${keToanTongHop.loaiCt}${keToanTongHop.soCt}</td>
					<td><a href="${url}/chungtu/ktth/xem/${keToanTongHop.maCt}">${keToanTongHop.lyDo}</a></td>
					<td align="right"><fmt:formatNumber
							value="${keToanTongHop.soTien.giaTri}" maxFractionDigits="0"></fmt:formatNumber></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>