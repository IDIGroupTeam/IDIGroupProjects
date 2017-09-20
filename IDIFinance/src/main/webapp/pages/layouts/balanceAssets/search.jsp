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
	});
</script>

<div class="panel panel-default with-nav-tabs">
	<div class="panel-heading">
		<h4>Tìm kiếm</h4>
	</div>
	<div class="panel-body">
		<form:hidden id="first" path="first" />

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
						pattern="M/yyyy" type="Date" dateStyle="SHORT" />
					<form:option value="${assetPeriodTmpl}">
						Tháng ${assetPeriodTmpl}
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
				nonSelectedText : 'Chọn tháng',
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