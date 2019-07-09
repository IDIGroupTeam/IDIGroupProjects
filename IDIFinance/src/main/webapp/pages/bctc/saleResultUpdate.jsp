<%@page import="com.idi.finance.bean.bctc.KyKeToanCon"%>
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
		// Khởi tạo action/method cho mainFinanceForm form
		$("#mainFinanceForm").attr("action", "${url}/bctc/kqhdkd/capnhat/luu");
		$("#mainFinanceForm").attr("method", "POST");

	});
</script>

<h4>Sinh bảng kết quả hoạt động kinh doanh</h4>
<hr />

<div class="row">
	<div class="col-sm-2">
		<b>Kỳ kế toán:</b>
	</div>
	<div class="col-sm-4">${mainFinanceForm.kyKeToan.tenKyKt}</div>
	<div class="col-sm-6">
		<b>Chú ý:</b><i>Trước khi sinh bảng kết quả hoạt động kinh doanh,
			cần chạy các <a href="${url}/chungtu/ketchuyen/danhsach"
			target="_blank">bút toán kết chuyển</a> trong thời gian tương ứng
		</i>
	</div>
</div>
<div class="row">
	<div class="col-sm-2">
		<b>Tháng:</b>
	</div>
	<div class="col-sm-4">
		<form:select path="assetPeriods" multiple="false"
			cssClass="form-control">
			<form:options items="${assetPeriods}" />
		</form:select>
	</div>
	<div class="col-sm-6"></div>
</div>

<hr />
<div>
	<a href="${url}/bctc/kqhdkd/danhsach" class="btn btn-info btn-sm">
		Hủy </a>
	<button type="submit" class="btn btn-info btn-sm">Sinh Bảng
		KQHDKD</button>
</div>