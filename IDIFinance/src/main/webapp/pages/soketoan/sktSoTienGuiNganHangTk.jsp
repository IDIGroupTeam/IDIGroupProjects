<%@page import="com.idi.finance.bean.chungtu.ChungTu"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<script type="text/javascript">
	//Shorthand for $( document ).ready()
	$(function() {
		$("#mainFinanceForm").addClass("form-horizontal");
		$("#submitBut").click(function() {
			$("#mainFinanceForm").prop("method", "POST");
			$("#mainFinanceForm").submit();
		});
	});
</script>

<div class="panel panel-default with-nav-tabs">
	<div class="panel-heading">
		<h4>Tìm kiếm</h4>
	</div>
	<div class="panel-body">
		<div class="form-group">
			<label for="loaiCts">Chứng từ:</label>
			<form:select id="loaiCts" path="loaiCts" multiple="multiple"
				class="form-control">
				<form:option value="${ChungTu.CHUNG_TU_PHIEU_THU}">Phiếu thu</form:option>
				<form:option value="${ChungTu.CHUNG_TU_PHIEU_CHI}">Phiếu chi</form:option>
				<form:option value="${ChungTu.CHUNG_TU_BAO_NO}">Báo nợ</form:option>
				<form:option value="${ChungTu.CHUNG_TU_BAO_CO}">Báo co</form:option>
			</form:select>
		</div>

		<div class="form-group">
			<label for="dau">Từ:</label>
			<div class="input-group date datetime smallform pull-right">
				<form:input path="dau" class="form-control" readonly="true" />
				<span class="input-group-addon"><span
					class="glyphicon glyphicon-calendar"></span></span>
			</div>
		</div>

		<div class="form-group">
			<label for="cuoi">Đến:</label>
			<div class="input-group date datetime smallform pull-right">
				<form:input path="cuoi" class="form-control" readonly="true" />
				<span class="input-group-addon"><span
					class="glyphicon glyphicon-calendar"></span></span>
			</div>
		</div>

		<script type="text/javascript">
			//Khởi tạo ngày sau khi 2 select trên được load đủ nội dung
			$("#loaiCts").multiselect({
				maxHeight : 200,
				buttonWidth : '170px',
				nonSelectedText : 'Chứng từ',
				nSelectedText : 'Được chọn',
				includeSelectAllOption : true,
				allSelectedText : 'Tất cả',
				selectAllText : 'Tất cả',
				selectAllValue : '${ChungTu.TAT_CA}'
			});

			$(".datetime").datetimepicker({
				language : 'vi',
				todayBtn : 1,
				autoclose : 1,
				todayHighlight : 1,
				startView : 2,
				minView : 2,
				forceParse : 0,
				pickerPosition : "top-left"
			});
		</script>
	</div>
	<div class="panel-footer">
		<button id="submitBut" type="button" class="btn btn-info btn-sm">Tìm
			kiếm</button>
	</div>
</div>