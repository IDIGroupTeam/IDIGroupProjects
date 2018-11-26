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

		var kyKeToanDsStr = '${kyKeToanDs}';
		var kyKeToanDs = docKyKeToanDs(kyKeToanDsStr);

		$("#kyKeToan\\.maKyKt")
				.change(
						function() {
							var kyKeToan;
							var id = $(this).val();

							for (var i = 0; i < kyKeToanDs.length; i++) {
								if (kyKeToanDs[i].maKyKt == id) {
									kyKeToan = kyKeToanDs[i];
									break;
								}
							}

							var homNay = new Date();
							var batDau = new Date(kyKeToan.batDau);
							var ketThuc = new Date(kyKeToan.ketThuc);

							assetPeriods = "<select id='assetPeriods' name='assetPeriods' class='form-control' multiple='multiple'>";
							count = batDau;
							while (count.getTime() <= ketThuc.getTime()) {
								templ = count.getDate() + "/"
										+ (count.getMonth() + 1) + "/"
										+ count.getFullYear();

								if (homNay.getFullYear() == count.getFullYear()
										&& homNay.getMonth() == count
												.getMonth()) {
									assetPeriods += "<option value='"+templ+"' selected>"
											+ templ + "</option>";
								} else {
									assetPeriods += "<option value='"+templ+"'>"
											+ templ + "</option>";
								}

								count.setMonth(count.getMonth() + 1);
							}
							assetPeriods += "</select><input type='hidden' name='_assetPeriods' value='1'>";

							$("#assetPeriodsSpace").html(assetPeriods);
							$("#assetPeriods").multiselect({
								enableFiltering : true,
								filterPlaceholder : 'Tìm kiếm',
								maxHeight : 200,
								buttonWidth : '180px',
								nonSelectedText : 'Chọn tháng',
								nSelectedText : 'Được chọn',
								includeSelectAllOption : true,
								allSelectedText : 'Chọn tất cả',
								selectAllText : 'Tất cả',
								selectAllValue : 'ALL'
							});
						});

		$("#assetPeriods").multiselect({
			enableFiltering : true,
			filterPlaceholder : 'Tìm kiếm',
			maxHeight : 200,
			buttonWidth : '180px',
			nonSelectedText : 'Chọn tháng',
			nSelectedText : 'Được chọn',
			includeSelectAllOption : true,
			allSelectedText : 'Chọn tất cả',
			selectAllText : 'Tất cả',
			selectAllValue : 'ALL'
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

		$("#updateSRBut").click(function() {
			$("#mainFinanceForm").attr("action", "${url}/bctc/kqhdkd/capnhat");
			$("#mainFinanceForm").attr("method", "POST");
			$("#mainFinanceForm").submit();
		});
	});
</script>

<form:hidden id="first" path="first" />

<div class="panel panel-default with-nav-tabs">
	<div class="panel-heading">
		<h4>Tìm kiếm</h4>
	</div>
	<div class="panel-body">
		<div class="form-group">
			<label for="kyKeToan.maKyKt">Kỳ:</label>
			<div class="input-group smallform pull-right">
				<form:select path="kyKeToan.maKyKt" class="form-control"
					cssStyle="width: 180px;">
					<form:options items="${kyKeToanDs}" itemLabel="tenKyKt"
						itemValue="maKyKt" />
				</form:select>
			</div>
		</div>

		<div class="form-group">
			<label for="assetPeriods">Thời gian</label>
			<div id="assetPeriodsSpace" class="input-group smallform pull-right">
				<form:select path="assetPeriods" multiple="multiple"
					class="form-control">
					<form:options items="${assetPeriods}" />
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
		<button id="updateSRBut" type="button" class="btn btn-info btn-sm">Cập
			nhật bảng KQHDKD</button>
	</div>
</div>