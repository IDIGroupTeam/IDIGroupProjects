<%@page import="com.idi.finance.bean.taikhoan.LoaiTaiKhoan"%>
<%@page import="com.idi.finance.bean.doituong.DoiTuong"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<script type="text/javascript">
	//Shorthand for $( document ).ready()
	$(function() {
		// Khởi tạo action/method cho mainFinanceForm form
		$("#mainFinanceForm").attr("action", "${url}/chungtu/baono/luu");
		$("#mainFinanceForm").attr("method", "POST");

		$("#submitBt").click(function() {
			$("#mainFinanceForm").submit();
		});

		var soDongTk = '${mainFinanceForm.soTkLonNhat}';
		var loaiTien = null;
		var url = "${url}/chungtu/doituong";
		var selectedRow = soDongTk - 1;

		// Đăng ký autocomplete
		var autocomplete = $('#doiTuong\\.tenDt').bootcomplete({
			url : url,
			minLength : 1,
			idFieldName : "doiTuong\\.khoaDt",
			preprocess : function(json) {
				var jsonTmpl = new Array(json.length);

				$.each(json, function(i, j) {
					j.id = j.khoaDt;
					j.label = j.tenDt;
					jsonTmpl[i] = j;
				});

				return jsonTmpl;
			},
			selectResult : function(selectedData) {
				if (selectedData) {
					$('#doiTuong\\.maDt').val(selectedData.maDt);
					$('#doiTuong\\.loaiDt').val(selectedData.loaiDt);
					$('#doiTuong\\.maThue').val(selectedData.maThue);
					$('#doiTuong\\.diaChi').val(selectedData.diaChi);
					$('#doiTuong\\.nguoiNop').val(selectedData.tenDt);
				}
			}
		});

		// Khởi tạo danh sách các loại tiền
		var loaiTienDsStr = "${loaiTienDs}";
		loaiTienDsStr = loaiTienDsStr.substring(1, loaiTienDsStr.length - 1);
		loaiTienDsStr = $.trim(loaiTienDsStr);
		var loaiTienDsTmpl = loaiTienDsStr.split(",");
		var loaiTienDs = new Array(loaiTienDsTmpl.length);
		for (i = 0; i < loaiTienDsTmpl.length; i++) {
			var tienTmpl = $.trim(loaiTienDsTmpl[i]);
			tienTmpl = loaiTienDsTmpl[i].split("-");

			var tien = new Object();
			tien.maLt = $.trim(tienTmpl[0]);
			tien.tenLt = $.trim(tienTmpl[1]);
			tien.banRa = $.trim(tienTmpl[2]);
			loaiTienDs[i] = tien;

			if (tien.maLt == $("#loaiTien\\.maLt").val()) {
				loaiTien = tien;
			}
		}

		function capNhapTongTien() {
			var tongGiaTri = 0;
			$("input[id^='taiKhoanNoDs'][id$='\\.soTien\\.soTien']").each(
					function() {
						var giaTri = $.trim($(this).val());
						var giaTriSo = giaTri.replace(/,/g, "");

						var tr = $(this).parents("tr");
						tr.find("[name$='\\.soTien\\.soTien']").val(giaTriSo);

						if (giaTriSo != '' && !isNaN(giaTriSo)) {
							tongGiaTri += parseFloat(giaTriSo);
						}
					});

			$("#taiKhoanCoDs0\\.soTien\\.soTien").val(tongGiaTri);
			$("#taiKhoanCoDs0\\.soTien\\.soTienTxt").html(
					accounting.formatNumber(tongGiaTri, 0, ","));

			var tyGia = $.trim($("#loaiTien\\.banRa").val());
			$("#soTien\\.giaTriTxt").html(
					accounting.formatNumber(tongGiaTri, 0, ",") + " "
							+ loaiTien.maLt);
			$("#soTien\\.giaTriQdTxt").html(
					accounting.formatNumber(tongGiaTri * tyGia, 0, ",")
							+ " VND");
		}

		function capNhatTongTienTxt() {
			var tyGia = $.trim($("#loaiTien\\.banRa").val());
			// Quy ra tiền Việt Nam
			var tongGiaTri = $("#taiKhoanCoDs0\\.soTien\\.soTien").val();
			$("#soTien\\.giaTriTxt").html(
					accounting.formatNumber(tongGiaTri, 0, ",") + " "
							+ loaiTien.maLt);
			$("#soTien\\.giaTriQdTxt").html(
					accounting.formatNumber(tongGiaTri * tyGia, 0, ",")
							+ " VND");
		}

		function dangKySuKien() {
			$("input[id^='taiKhoanNoDs'][id$='\\.soTien\\.soTien']").change(
					function() {
						var giaTri = $.trim($(this).val());
						if (giaTri != '' && !isNaN(giaTri)) {
							$(this).val(parseFloat(giaTri));
						}

						capNhapTongTien();
					});

			$("input[id^='taiKhoanNoDs']").click(function() {
				var tr = $(this).parents("tr");
				var tbody = tr.parent();
				tbody.find("tr.active").removeClass("active");
				tr.addClass("active");

				var curRow = selectedRow;
				var newRow = tr.prop("id");
				selectedRow = newRow;
				console.log("selectedRow", "current", curRow, "new", newRow);
			});
			$("select[id^='taiKhoanNoDs']").click(function() {
				var tr = $(this).parents("tr");
				var tbody = tr.parent();
				tbody.find("tr.active").removeClass("active");
				tr.addClass("active");

				var curRow = selectedRow;
				var newRow = tr.prop("id");
				selectedRow = newRow;
				console.log("selectedRow", "current", curRow, "new", newRow);
			});
			$("#goiYBt").click(function() {
				var loaiCt = $("#loaiCt").val();
				var param = "loaiCt=" + loaiCt;
				$.ajax({
					url : "${url}/chungtu/sochungtu",
					data : param,
					dataType : "text",
					type : "GET",
					success : function(soCt) {
						soCt = soCt * 1 + 1;
						$("#soCt").val(soCt);
					},
					error : function(error) {
						console.log("Error", error);
					}
				});
			});
		}

		$("#themTkCo").click(
				function() {
					var currentTr = $(this).parent().parent();
					var prevTr = $(currentTr).prev().prev();
					var prevId = $(prevTr).prop("id");
					var newId = ++prevId;
					--prevId;

					var newTr = "<tr>" + $(prevTr).html() + "</tr>";
					var pat = new RegExp("\\[" + prevId + "\\]", "g");
					var pat1 = new RegExp("Ds" + prevId, "g");
					newTr = newTr.replace(pat, "[" + newId + "]");
					newTr = newTr.replace(pat1, "Ds" + newId);

					$(newTr).insertBefore($(currentTr).prev())
							.prop("id", newId);
					soDongTk++;

					var newTr = $("#" + newId);
					var taiKhoanObj = newTr.find("[id$='\\.maTk']");
					var soTienObj = newTr.find("[id$='\\.soTien']");

					newTr.find(".combobox-container").remove();
					taiKhoanObj.prop("name", "taiKhoanNoDs[" + newId
							+ "].loaiTaiKhoan.maTk");
					taiKhoanObj.val("");
					taiKhoanObj.combobox();

					newTr.find("[name$='\\.soTien\\.soTien']").remove();
					soTienObj.prop("name", "taiKhoanNoDs[" + newId
							+ "].soTien.soTien");
					soTienObj.val("0");
					soTienObj.maskx({
						maskxTo : 'simpleMoneyTo',
						maskxFrom : 'simpleMoneyFrom'
					});

					newTr.find("[id$='\\.errors']").remove();
					$("#xoaTkCo").removeClass("disabled");

					dangKySuKien();
				});

		$("#xoaTkCo").click(function() {
			var tbody = $("tr#" + selectedRow).parent();
			$("tr#" + selectedRow).remove();
			console.log("selectedRow", selectedRow, "soDongTk", soDongTk);
			var next = selectedRow * 1 + 1;
			for (var i = next; i < soDongTk; i++) {
				try {
					var tr = $("tr#" + i);
					var j = i - 1;

					tr.find("[id^='taiKhoanNoDs']").each(function() {
						var id = $(this).prop("id");
						var pat = new RegExp("" + i, "g");
						id = id.replace(pat, j + "");
						$(this).prop("id", id);
					});

					tr.find("[name^='taiKhoanNoDs']").each(function() {
						var name = $(this).prop("name");
						var pat = new RegExp("" + i, "g");
						name = name.replace(pat, j + "");
						$(this).prop("name", name);
					});

					tr.prop("id", j);
				} catch (e) {
					console.log("error", e);
				}
			}

			soDongTk--;
			selectedRow = selectedRow == soDongTk ? soDongTk - 1 : selectedRow;
			console.log("selectedRow", selectedRow, "soDongTk", soDongTk);
			$("tr#" + selectedRow).addClass("active");

			// Cập nhật giá trị tổng tiền
			capNhapTongTien();

			if (soDongTk == 1) {
				$("#xoaTkCo").addClass("disabled");
			}
		});

		$("#lyDo").change(function() {
			$("#taiKhoanCoDs0\\.lyDo").val($(this).val());
			for (i = 0; i < soDongTk; i++) {
				$("#taiKhoanNoDs" + i + "\\.lyDo").val($(this).val());
			}
		});

		$("#loaiTien\\.maLt").change(function() {
			// Thay đổi loại tiền
			for (i = 0; i < loaiTienDs.length; i++) {
				if (loaiTienDs[i].maLt == this.value) {
					loaiTien = loaiTienDs[i];
					break;
				}
			}

			// Cập nhật tỷ giá
			$("#loaiTien\\.banRa").val(loaiTien.banRa);
			capNhatTongTienTxt()
		});

		$("#loaiTien\\.banRa").change(function() {
			loaiTien.banRa = $(this).val();
			capNhatTongTienTxt();
		});

		function khoiTao() {
			if (soDongTk > 1) {
				$("#xoaTkCo").removeClass("disabled");
			}

			$("select[id^='taiKhoanNoDs'][id$='\\.loaiTaiKhoan\\.maTk']").each(
					function() {
						var maTkCo = $(this).val();
						$(this).find("option[value=0]").remove();
						if (maTkCo == "0") {
							$(this).val("");
						}
						$(this).combobox();
					});

			$("input[id^='taiKhoanNoDs'][id$='\\.soTien\\.soTien']").each(
					function() {
						console.log("tien", $(this).val());
						$(this).maskx({
							maskxTo : 'simpleMoneyTo',
							maskxFrom : 'simpleMoneyFrom'
						});
					});

			$("tr#" + selectedRow).addClass("active");

			// Đăng ký sự kiện thay đổi
			dangKySuKien();

			capNhatTongTienTxt();
		}
		khoiTao();

		$(".datetime").datetimepicker({
			language : 'vi',
			todayBtn : 1,
			autoclose : 1,
			todayHighlight : 1,
			startView : 2,
			minView : 2,
			forceParse : 0,
			pickerPosition : "bottom-left"
		});
	});
</script>

<h4>BÁO NỢ</h4>
<hr />
<form:hidden path="loaiCt" />
<form:hidden path="maCt" />
<form:hidden path="nghiepVu" />
<fmt:formatDate value="${homNay}" pattern="dd/M/yyyy" type="Date"
	dateStyle="SHORT" var="homNay" />

<div class="row form-group">
	<label class="control-label col-sm-2" for="soCt">Số báo nợ dự
		kiến (*):</label>
	<%-- <div class="col-sm-1">${mainFinanceForm.loaiCt}</div> --%>
	<div class="col-sm-2">
		<form:input path="soCt" class="form-control" />
		<form:errors path="soCt" cssClass="error" />
	</div>
	<div class="col-sm-2">
		<button id="goiYBt" type="button" class="btn btn-info btn-sm">Gợi
			ý</button>
	</div>

	<label class="control-label col-sm-2" for=ngayLap>Ngày lập báo
		nợ (*):</label>
	<div class="col-sm-4">
		<div class="input-group date datetime smallform">
			<form:input path="ngayLap" class="form-control"
				placeholder="${homNay}" />
			<span class="input-group-addon"><span
				class="glyphicon glyphicon-calendar"></span></span>
		</div>
		<form:errors path="ngayLap" cssClass="error" />
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="doiTuong.tenDt">Đối
		tượng:(*)</label>
	<div class="col-sm-4">
		<form:hidden path="doiTuong.khoaDt" />
		<form:hidden path="doiTuong.maDt" />
		<form:hidden path="doiTuong.loaiDt" />
		<form:input path="doiTuong.tenDt" placeholder="Họ và tên"
			cssClass="form-control" />
		<br />
		<form:errors path="doiTuong.tenDt" cssClass="error" />
	</div>

	<label class="control-label col-sm-2" for=ngayHt>Ngày hạch toán
		(*):</label>
	<div class="col-sm-4">
		<div class="input-group date datetime smallform">
			<form:input path="ngayHt" class="form-control"
				placeholder="${homNay}" />
			<span class="input-group-addon"><span
				class="glyphicon glyphicon-calendar"></span></span>
		</div>
		<form:errors path="ngayHt" cssClass="error" />
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="doiTuong.nguoiNop">Người
		nộp: </label>
	<div class="col-sm-4">
		<form:input path="doiTuong.nguoiNop" placeholder="Người nộp"
			cssClass="form-control" />
	</div>

	<label class="control-label col-sm-2" for="doiTuong.maThue">Mã
		số thuế:</label>
	<div class="col-sm-4">
		<form:input path="doiTuong.maThue" placeholder="Mã số thuế"
			cssClass="form-control" disabled="true" />
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="lyDo">Lý do:(*)</label>
	<div class="col-sm-4">
		<form:textarea path="lyDo" placeholder="Lý do" cssClass="form-control" />
		<br />
		<form:errors path="lyDo" cssClass="error" />
	</div>


	<label class="control-label col-sm-2" for="doiTuong.diaChi">Địa
		chỉ:</label>
	<div class="col-sm-4">
		<form:textarea path="doiTuong.diaChi" placeholder="Địa chỉ"
			cssClass="form-control" disabled="true" />
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="loaiTien.maLt">Loại
		tiền</label>
	<div class="col-sm-4">
		<form:select path="loaiTien.maLt" cssClass="form-control">
			<form:options items="${loaiTienDs}" itemValue="maLt"
				itemLabel="tenLt" />
		</form:select>
	</div>

	<label class="control-label col-sm-2" for="loaiTien.banRa">Tỷ
		giá:(*)</label>
	<div class="col-sm-4">
		<form:input path="loaiTien.banRa" placeholder="0.0"
			cssClass="form-control" />
		<br />
		<form:errors path="loaiTien.banRa" cssClass="error" />
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="kemTheo">Kèm theo <br />số
		chứng từ gốc:
	</label>
	<div class="col-sm-4">
		<form:input path="kemTheo" placeholder="0" cssClass="form-control" />
		<br />
		<form:errors path="kemTheo" cssClass="error" />
	</div>

	<label class="control-label col-sm-2" for=ngayTt>Ngày thanh
		toán</label>
	<div class="col-sm-4">
		<div class="input-group date datetime smallform">
			<form:input path="ngayTt" class="form-control"
				placeholder="${homNay}" />
			<span class="input-group-addon"><span
				class="glyphicon glyphicon-calendar"></span></span>
		</div>
		<form:errors path="ngayTt" cssClass="error" />
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2"
		for=taiKhoanCoDs[0].loaiTaiKhoan.maTk>Tài khoản có</label>
	<div class="col-sm-4">
		<form:select cssClass="form-control"
			path="taiKhoanCoDs[0].loaiTaiKhoan.maTk" multiple="false">
			<form:options items="${loaiTaiKhoanTgnhDs}" itemValue="maTk"
				itemLabel="maTenTk" />
		</form:select>
		<form:hidden path="taiKhoanCoDs[0].soDu" />
		<form:hidden path="taiKhoanCoDs[0].soTien.soTien" />
		<form:hidden path="taiKhoanCoDs[0].lyDo" />
		<form:errors path="taiKhoanCoDs[0].loaiTaiKhoan.maTk" cssClass="error" />
	</div>
</div>

<div class="table-responsive row form-group">
	<label class="control-label col-sm-2">Định khoản</label>
	<table id="taiKhoanTbl"
		class="table table-bordered table-hover text-center dinhkhoan">
		<thead>
			<tr>
				<th class="text-center">Tài khoản nợ</th>
				<th class="text-center">Giá trị</th>
				<th class="text-center">Lý do</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${mainFinanceForm.taiKhoanNoDs}" varStatus="status">
				<tr id="${status.index}">
					<td class="text-left"><form:select cssClass="form-control"
							path="taiKhoanNoDs[${status.index}].loaiTaiKhoan.maTk"
							multiple="false">
							<form:option value="0"></form:option>
							<form:options items="${loaiTaiKhoanDs}" itemValue="maTk"
								itemLabel="maTenTk" />
						</form:select> <form:hidden path="taiKhoanNoDs[${status.index}].soDu" /> <form:errors
							path="taiKhoanNoDs[${status.index}].loaiTaiKhoan.maTk"
							cssClass="error" /></td>
					<td class="text-left"><form:input
							cssClass="form-control text-right"
							path="taiKhoanNoDs[${status.index}].soTien.soTien"
							placeholder="0.0" /> <form:errors
							path="taiKhoanNoDs[${status.index}].soTien.soTien"
							cssClass="error" /></td>
					<td><form:input cssClass="form-control"
							path="taiKhoanNoDs[${status.index}].lyDo" placeholder="Lý do" /></td>
				</tr>
			</c:forEach>
			<tr>
				<td class="text-left"><b>Thành tiền:</b></td>
				<td class="text-right"><span id="soTien.giaTriTxt"> <fmt:formatNumber
							value="${mainFinanceForm.soTien.soTien}"></fmt:formatNumber>
						&nbsp;${mainFinanceForm.soTien.loaiTien.maLt}
				</span></td>
				<td class="text-right"><span id="soTien.giaTriQdTxt"> <fmt:formatNumber
							value="${mainFinanceForm.soTien.giaTri}"></fmt:formatNumber>
						&nbsp;VND
				</span></td>
			</tr>
			<tr>
				<td colspan="3">
					<button id="themTkCo" type="button" class="btn btn-info btn-sm"
						title="Thêm tài khoản ghi có">
						<span class="glyphicon glyphicon-plus"></span> Thêm
					</button>
					<button id="xoaTkCo" type="button"
						class="btn btn-info btn-sm disabled"
						title="Xóa tài khoản ghi có cuối cùng">
						<span class="glyphicon glyphicon-plus"></span> Xóa
					</button>
				</td>
			</tr>
		</tbody>
	</table>
</div>

<div class="row form-group">
	<div class="col-sm-12">
		<a href="${url}/chungtu/baono/danhsach" class="btn btn-info btn-sm">Hủy</a>
		<button id="submitBt" type="button" class="btn btn-info btn-sm">Lưu</button>
	</div>
</div>
