<%@page import="com.idi.finance.bean.bctc.KyKeToanCon"%>
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

		function docKyKeToanDs(kyKeToanDsStr) {
			kyKeToanDsStr = $.trim(kyKeToanDsStr);
			kyKeToanDsStr = kyKeToanDsStr
					.substring(1, kyKeToanDsStr.length - 1);

			var kyKeToanDsTmpl = kyKeToanDsStr.split(", ");
			var kyKeToanDs = new Array();
			for (var i = 0; i < kyKeToanDsTmpl.length; i++) {
				var kyKeToan = new Object();
				var kyKeToanTmpl = kyKeToanDsTmpl[i].split(" ");
				kyKeToan.maKyKt = $.trim(kyKeToanTmpl[0]);
				kyKeToan.batDau = $.trim(kyKeToanTmpl[1]);
				kyKeToan.ketThuc = $.trim(kyKeToanTmpl[2]);
				kyKeToanDs[i] = kyKeToan;
			}

			return kyKeToanDs;
		}

		var kyKeToanDsStr = '${kyKeToanDs}';
		var kyKeToanDs = docKyKeToanDs(kyKeToanDsStr);

		$("#kyKeToan\\.maKyKt").change(
				function() {
					var kyKeToan;
					var id = $(this).val();

					for (var i = 0; i < kyKeToanDs.length; i++) {
						if (kyKeToanDs[i].maKyKt == id) {
							kyKeToan = kyKeToanDs[i];
							break;
						}
					}

					$('.datetime').datetimepicker('setStartDate',
							kyKeToan.batDau);
					$('.datetime').datetimepicker('setEndDate',
							kyKeToan.ketThuc);

					var homNay = new Date();
					var batDau = new Date(kyKeToan.batDau);
					var ketThuc = new Date(kyKeToan.ketThuc);

					if (homNay.getTime() >= batDau.getTime()
							&& homNay.getTime() <= ketThuc.getTime()) {
						y = homNay.getFullYear();
						m = homNay.getMonth();

						batDau = new Date();
						batDau.setFullYear(y, m, 1);
						ketThuc = new Date();
						ketThuc.setFullYear(y, m + 1, 0);
					}

					$("#dau").val(
							batDau.getDate() + "/" + (batDau.getMonth() + 1)
									+ "/" + batDau.getFullYear());
					$("#cuoi").val(
							ketThuc.getDate() + "/" + (ketThuc.getMonth() + 1)
									+ "/" + ketThuc.getFullYear());

					$('.datetime').datetimepicker('update');
				});

		$(".datetime").datetimepicker({
			language : 'vi',
			todayBtn : 1,
			autoclose : 1,
			todayHighlight : 1,
			startView : 2,
			minView : 2,
			forceParse : 0,
			pickerPosition : "top-left",
			startDate : '${mainFinanceForm.kyKeToan.batDau}',
			endDate : '${mainFinanceForm.kyKeToan.ketThuc}'
		});

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

		$("#updateBSBut").click(function() {
			$("#mainFinanceForm").attr("action", "${url}/bctc/cdkt/capnhat");
			$("#mainFinanceForm").attr("method", "POST");
			$("#mainFinanceForm").submit();
		});
	});
</script>

<form:hidden id="first" path="first" />
<form:hidden path="assetPeriods" />

<div class="panel panel-default with-nav-tabs">
	<div class="panel-heading">
		<h4>Tìm kiếm</h4>
	</div>
	<div class="panel-body">
		<div class="form-group">
			<label for="dau">Kỳ:</label>
			<div class="input-group smallform pull-right">
				<form:select path="kyKeToan.maKyKt" class="form-control"
					cssStyle="width: 180px;">
					<form:options items="${kyKeToanDs}" itemLabel="tenKyKt"
						itemValue="maKyKt" />
				</form:select>
			</div>
		</div>

		<div class="form-group">
			<label for="assetCodes">Mã số</label>
			<div class="input-group smallform pull-right">
				<form:select id="assetCodes" path="assetCodes" multiple="multiple"
					class="form-control">
					<c:forEach items="${assetCodes}" var="assetCode">
						<form:option value="${assetCode}">${assetCode}</form:option>
					</c:forEach>
				</form:select>
			</div>
		</div>
	</div>
	<div class="panel-footer">
		<button id="submitBut" type="button" class="btn btn-info btn-sm">Tìm
			kiếm</button>
		&nbsp;
		<button id="updateBSBut" type="button" class="btn btn-info btn-sm">Cập
			nhật bảng CDKT</button>
	</div>
</div>