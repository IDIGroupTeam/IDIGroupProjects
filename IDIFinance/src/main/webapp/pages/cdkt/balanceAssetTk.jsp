<%@page import="com.idi.finance.bean.cdkt.BalanceAssetData"%>
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
		$("#submitBut").click(function() {
			$("#pageIndex").val(1);
			$("#totalPages").val(0);
			$("#totalRecords").val(0);
			$("#mainFinanceForm").submit();
		});

		$("#updateBSBut").click(function() {
			$("#mainFinanceForm").attr("action", "${url}/cdkt/hachtoan");
			$("#mainFinanceForm").attr("method", "POST");

			$("#mainFinanceForm").submit();
		});
	});
</script>

<div class="panel panel-default with-nav-tabs">
	<div class="panel-heading">
		<h4>Tìm kiếm</h4>
	</div>
	<div class="panel-body">
		<form:hidden id="first" path="first" />

		<div class="form-group">
			<label for="periodType">Kỳ</label>
			<form:select path="periodType" multiple="false"
				class="form-control smallform" cssStyle="width: 180px;">
				<form:option value="${BalanceAssetData.PERIOD_TYPE_WEEK}">Tuần</form:option>
				<form:option value="${BalanceAssetData.PERIOD_TYPE_MONTH}">Tháng</form:option>
				<form:option value="${BalanceAssetData.PERIOD_TYPE_QUARTER}">Quý</form:option>
				<form:option value="${BalanceAssetData.PERIOD_TYPE_YEAR}">Năm</form:option>
			</form:select>
		</div>

		<div class="form-group">
			<label for="assetCodes">Mã số</label>
			<form:select id="assetCodes" path="assetCodes" multiple="multiple"
				class="form-control">
				<c:forEach items="${assetCodes}" var="assetCode">
					<form:option value="${assetCode}">${assetCode}</form:option>
				</c:forEach>
			</form:select>
		</div>

		<div class="form-group">
			<label for="assetPeriods">Tháng</label>
			<form:select id="assetPeriods" path="assetPeriods"
				multiple="multiple" class="form-control">
				<c:forEach items="${assetPeriods}" var="assetPeriod">
					<fmt:formatDate var="assetPeriodTmpl" value="${assetPeriod}"
						pattern="dd/M/yyyy" type="Date" dateStyle="SHORT" />
					<form:option value="${assetPeriodTmpl}">
						${assetPeriodTmpl}
					</form:option>
				</c:forEach>
			</form:select>
		</div>
		<script type="text/javascript">
			//Khởi tạo ngày sau khi 2 select trên được load đủ nội dung
			$("#assetCodes").multiselect({
				enableFiltering : true,
				filterPlaceholder : 'Tìm kiếm',
				maxHeight : 200,
				buttonWidth : '180px',
				nonSelectedText : 'Chọn mã số',
				nSelectedText : 'Được chọn',
				includeSelectAllOption : true,
				allSelectedText : 'Chọn tất cả',
				selectAllText : 'Tất cả',
				selectAllValue : 'ALL'
			});
			$("#assetPeriods").multiselect({
				enableFiltering : true,
				filterPlaceholder : 'Tìm kiếm',
				maxHeight : 200,
				buttonWidth : '180px',
				nonSelectedText : 'Chọn kỳ',
				nSelectedText : 'Được chọn',
				numberDisplayed : 2,
				includeSelectAllOption : true,
				allSelectedText : 'Chọn tất cả',
				selectAllText : 'Tất cả',
				selectAllValue : 'ALL'
			});
		</script>
	</div>
	<div class="panel-footer">
		<button id="submitBut" type="button" class="btn btn-info btn-sm">Tìm
			kiếm</button>
	</div>
</div>

<div class="panel panel-default with-nav-tabs">
	<div class="panel-heading">
		<h4>Cập nhật</h4>
	</div>
	<div class="panel-body">
		<form:hidden id="first" path="first" />

		<%-- <div class="form-group">
			<label for="ky">Kỳ</label>
			<form:select id="ky" path="ky" multiple="false"
				class="form-control smallform">
				<form:option value="${BalanceAssetData.PERIOD_TYPE_WEEK}">Tuần</form:option>
				<form:option value="${BalanceAssetData.PERIOD_TYPE_MONTH}">Tháng</form:option>
				<form:option value="${BalanceAssetData.PERIOD_TYPE_QUARTER}">Quý</form:option>
				<form:option value="${BalanceAssetData.PERIOD_TYPE_YEAR}">Năm</form:option>
			</form:select>
		</div> --%>

		<div class="form-group">
			<label for="dau">Từ:</label>
			<div class="input-group date datetime smallform">
				<form:input path="dau" class="form-control" readonly="true" />
				<span class="input-group-addon"><span
					class="glyphicon glyphicon-calendar"></span></span>
			</div>
		</div>

		<div class="form-group">
			<label for="cuoi">Đến:</label>
			<div class="input-group date datetime smallform">
				<form:input path="cuoi" class="form-control" readonly="true" />
				<span class="input-group-addon"><span
					class="glyphicon glyphicon-calendar"></span></span>
			</div>
		</div>

		<script type="text/javascript">
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
		<button id="updateBSBut" type="button" class="btn btn-info btn-sm">Cập
			nhật bảng cân đối kế toán</button>
	</div>
</div>