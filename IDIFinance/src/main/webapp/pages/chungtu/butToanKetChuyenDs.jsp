<%@page import="com.idi.finance.bean.chungtu.DoiTuong"%>
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

<h4>Danh mục bút toán kết chuyển</h4>
<p>
	<i></i>
</p>

<div class="pull-right">
	<i>(*): Mặc định là tiền VND</i>&nbsp;&nbsp;&nbsp;&nbsp; <a
		href="${url}/taomoibtkc" class="btn btn-info btn-sm"> <span
		class="glyphicon glyphicon-plus"></span> Tạo mới
	</a>
</div>
<br />
<br />

<div class="table-responsive">
	<table class="table table-bordered table-hover">
		<thead>
			<tr>
				<th class="text-center" colspan="2">Bút toán</th>
				<th class="text-center" rowspan="2">Lý do</th>
				<th class="text-center" rowspan="2">Tổng số tiền (*)</th>
				<th class="text-center" rowspan="2">Đối tượng</th>
			</tr>
			<tr>
				<th class="text-center">Ngày bút toán</th>
				<th class="text-center">Số</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${butToanKetChuyenDs}" var="butToanKetChuyen"
				varStatus="status">
				<tr>
					<td class="text-center" style="width: 50px;"><fmt:formatDate
							value="${butToanKetChuyen.ngayHt}" pattern="dd/M/yyyy"
							type="Date" dateStyle="SHORT" /></td>
					<td class="text-center" style="width: 50px;">${butToanKetChuyen.loaiCt}${butToanKetChuyen.soCt}</td>
					<td><a href="${url}/xemktth/${butToanKetChuyen.maCt}">${butToanKetChuyen.lyDo}</a></td>
					<td align="right"><fmt:formatNumber
							value="${butToanKetChuyen.soTien.giaTri}" maxFractionDigits="2"></fmt:formatNumber></td>
					<td><c:choose>
							<c:when
								test="${butToanKetChuyen.doiTuong.loaiDt == DoiTuong.KHACH_VANG_LAI}">
								${butToanKetChuyen.doiTuong.nguoiNop}
							</c:when>
							<c:otherwise>${butToanKetChuyen.doiTuong.tenDt}
							</c:otherwise>
						</c:choose></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>