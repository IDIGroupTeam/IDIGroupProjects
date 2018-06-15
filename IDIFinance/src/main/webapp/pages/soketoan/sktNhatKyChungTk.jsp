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
			$("#mainFinanceForm").prop("action", "${url}/soketoan/nhatkychung");
			$("#mainFinanceForm").prop("method", "POST");
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
			pickerPosition : "top-left",
			startDate : '${mainFinanceForm.kyKeToan.batDau}',
			endDate : '${mainFinanceForm.kyKeToan.ketThuc}'
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
				<form:option value="${ChungTu.CHUNG_TU_BAO_CO}">Báo có</form:option>
				<form:option value="${ChungTu.CHUNG_TU_KT_TH}">Phiếu kế toán tổng hợp</form:option>
				<form:option value="${ChungTu.CHUNG_TU_MUA_HANG}">Mua hàng</form:option>
				<form:option value="${ChungTu.CHUNG_TU_BAN_HANG}">Bán hàng</form:option>
			</form:select>
		</div>

		<div class="form-group">
			<label for="dau">Kỳ:</label>
			<div class="input-group smallform pull-right">
				<form:select path="kyKeToan.maKyKt" class="form-control">
					<form:options items="${kyKeToanDs}" itemLabel="tenKyKt"
						itemValue="maKyKt" />
				</form:select>
			</div>
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
	</div>
	<div class="panel-footer">
		<button id="submitBut" type="button" class="btn btn-info btn-sm">Tìm
			kiếm</button>
	</div>
</div>