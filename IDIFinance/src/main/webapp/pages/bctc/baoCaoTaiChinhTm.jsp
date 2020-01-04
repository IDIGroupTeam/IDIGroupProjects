<%@page import="com.idi.finance.bean.kyketoan.KyKeToan"%>
<%@page import="com.idi.finance.bean.bctc.BaoCaoTaiChinhCon"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<script type="text/javascript">
	//Shorthand for $( document ).ready()
	$(function() {
		// Khởi tạo action/method cho mainFinanceForm form
		$("#mainFinanceForm").attr("action", "${url}/bctc/luu");
		$("#mainFinanceForm").attr("method", "POST");

		$("#submitBt").click(function() {
			$("#mainFinanceForm").submit();
		});

		$(".datetime").datetimepicker({
			language : 'vi',
			todayBtn : 1,
			autoclose : 1,
			todayHighlight : 1,
			startView : 2,
			minView : 2,
			forceParse : 0,
			pickerPosition : "bottom-left",
			startDate : '${mainFinanceForm.kyKeToan.batDau}',
			endDate : '${mainFinanceForm.kyKeToan.ketThuc}'
		});
	});
</script>

<h4>Tạo mới báo cáo tài chính</h4>
<p>
	<i><b>Kỳ kế toán:</b> ${mainFinanceForm.kyKeToan.tenKyKt}</i>
</p>

<fmt:formatDate value="${homNay}" pattern="dd/M/yyyy" type="Date"
	dateStyle="SHORT" var="homNay" />

<form:hidden path="kyKeToan.maKyKt" />
<div class="row">
	<div class="col-sm-5">
		<div class="row form-group">
			<label class="control-label col-sm-4">Tiêu đề (*):</label>
			<div class="col-sm-8">
				<form:input path="tieuDe" cssClass="form-control" />
				<form:errors path="tieuDe" cssClass="error" />
			</div>
		</div>
		<div class="row form-group">
			<label class="control-label col-sm-4">Người lập (*):</label>
			<div class="col-sm-8">
				<form:input path="nguoiLap" cssClass="form-control" />
				<form:errors path="nguoiLap" cssClass="error" />
			</div>
		</div>
		<div class="row form-group">
			<label class="control-label col-sm-4">Giám đốc (*):</label>
			<div class="col-sm-8">
				<form:input path="giamDoc" cssClass="form-control" />
				<form:errors path="giamDoc" cssClass="error" />
			</div>
		</div>
		<div class="row form-group">
			<div class="col-sm-12">
				<a href="${url}/bctc/danhsach" class="btn btn-info btn-sm">Hủy</a>
				<button id="submitBt" type="button" class="btn btn-info btn-sm">Lưu</button>
			</div>
		</div>
	</div>
	<div class="col-sm-5">
		<div class="row form-group">
			<label class="control-label col-sm-4">Bắt đầu (*):</label>
			<div class="col-sm-8">
				<div class="input-group date datetime smallform">
					<form:input path="batDau" class="form-control"
						placeholder="${homNay}" autocomplete="off" />
					<span class="input-group-addon"><span
						class="glyphicon glyphicon-calendar"></span></span>
				</div>
				<form:errors path="batDau" cssClass="error" />
			</div>
		</div>
		<div class="row form-group">
			<label class="control-label col-sm-4">Kết thúc (*):</label>
			<div class="col-sm-8">
				<div class="input-group date datetime smallform">
					<form:input path="ketThuc" class="form-control"
						placeholder="${homNay}" autocomplete="off" />
					<span class="input-group-addon"><span
						class="glyphicon glyphicon-calendar"></span></span>
				</div>
				<form:errors path="ketThuc" cssClass="error" />
			</div>
		</div>
		<div class="row form-group">
			<label class="control-label col-sm-4">Ngày lập:</label>
			<div class="col-sm-8">${homNay}</div>
			<form:hidden path="ngayLap" />
		</div>
	</div>
	<div class="col-sm-2">
		<div class="row">
			<div class="col-sm-12 checkbox">
				<label><form:checkbox path="bctcDs[0].loaiBctc"
						value="${BaoCaoTaiChinhCon.LOAI_CDKT}" /> Bảng cân đối kế toán</label>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12 checkbox">
				<label><form:checkbox path="bctcDs[1].loaiBctc"
						value="${BaoCaoTaiChinhCon.LOAI_KQHDKD}" /> Bảng kết quả HĐKD</label>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12 checkbox">
				<label><form:checkbox path="bctcDs[2].loaiBctc"
						value="${BaoCaoTaiChinhCon.LOAI_LCTT}" /> Bảng lưu chuyển tiền tệ</label>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12 checkbox">
				<label><form:checkbox path="bctcDs[3].loaiBctc"
						value="${BaoCaoTaiChinhCon.LOAI_CDPS}" /> Bảng cân đối phát sinh</label>
			</div>
		</div>
		<form:errors path="bctcDs" cssClass="error" />
	</div>
</div>