
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
		$("#mainFinanceForm").attr("action", "${url}/chungtu/ktth/luu");
		$("#mainFinanceForm").attr("method", "POST");

		$("#submitBt").click(
				function() {
					// Giải pháp tạm thời hack validate
					$("input[id$='\\.nhomDk']").each(
							function() {
								var parent = $(this).parents("tr");
								var noSoTien = parent
										.find("[name$='no\\.soTien']");
								var coSoTien = parent
										.find("[name$='co\\.soTien']");

								console.log("truoc", "no", noSoTien.val(),
										"co", coSoTien.val());
								if (noSoTien.val() != ''
										|| coSoTien.val() != '') {
									if (noSoTien.val() == '') {
										noSoTien.val(0);
									} else if (coSoTien.val() == '') {
										coSoTien.val(0);
									}
								}
								console.log("sau", "no", noSoTien.val(), "co",
										coSoTien.val());
							});

					$("#mainFinanceForm").submit();
				});

		var soDongTk = '${mainFinanceForm.taiKhoanKtthDs.size()}';
		var selectedRow = soDongTk - 1;
		var tongGiaTriNo = 0;
		var tongGiaTriCo = 0;
		var loaiTien = null;
		var thapPhan = 2;

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
				if (tien.maLt == 'VND' || tien.maLt == 'VANG') {
					thapPhan = 2;
				} else {
					thapPhan = 2;
				}
			}
		}

		function capNhatTong() {
			// Tính tổng các tài khoản nợ
			tongGiaTriNo = 0;
			tongGiaTriCo = 0;
			$("input[id^='taiKhoanKtthDs'][id$='\\.no\\.soTien']").each(
					function() {
						var giaTriTt = $.trim($(this).val());
						var giaTriSo = giaTriTt.replace(/,/g, "");

						if (giaTriSo != '' && !isNaN(giaTriSo)) {
							tongGiaTriNo += parseFloat(giaTriSo);
						}
					});

			$("input[id^='taiKhoanKtthDs'][id$='\\.co\\.soTien']").each(
					function() {
						var giaTriTt = $.trim($(this).val());
						var giaTriSo = giaTriTt.replace(/,/g, "");

						if (giaTriSo != '' && !isNaN(giaTriSo)) {
							tongGiaTriCo += parseFloat(giaTriSo);
						}
					});
			console.log("capNhatTong", "no", tongGiaTriNo, "co", tongGiaTriCo);

			// Cập nhật các vị trí txt nợ
			capNhatTongTxt();
		}

		function capNhatTongTxt() {
			var tongGiaTri = tongGiaTriNo > tongGiaTriCo ? tongGiaTriNo
					: tongGiaTriCo;
			console.log("tongGiaTri", tongGiaTri, "No", tongGiaTriNo, "Co",
					tongGiaTriCo, "tygia", loaiTien.banRa, "vnd", tongGiaTri
							* loaiTien.banRa);

			$("#soTien\\.giaTriTxt").html(
					accounting
							.formatNumber(tongGiaTri * loaiTien.banRa, thapPhan, ",")
							+ " VND");

			$("#no\\.soTien\\.giaTriTxt").html(
					accounting.formatNumber(tongGiaTriNo, thapPhan, ",") + " "
							+ loaiTien.maLt);

			$("#co\\.soTien\\.giaTriTxt").html(
					accounting.formatNumber(tongGiaTriCo, thapPhan, ",") + " "
							+ loaiTien.maLt);
		}

		function dangKySuKien() {
			$("input[id^='taiKhoanKtthDs'][id$='\\.no\\.soTien']").change(
					function() {
						var parent = $(this).parents("tr");
						var giaTri = $.trim($(this).val());
						var giaTriSo = giaTri.replace(/,/g, "");

						console.log("dangKySuKien", "Nợ", giaTriSo);
						if (giaTriSo > 0) {
							parent.find("input[id$='co\\.soTien']").val("");
							parent.find("[name$='co\\.soTien']").val(0);
							parent.find("input[id$='\\.soDu']").val("-1");
						}

						capNhatTong();
					});
			$("input[id^='taiKhoanKtthDs'][id$='\\.co\\.soTien']").change(
					function() {
						var parent = $(this).parents("tr");
						var giaTri = $.trim($(this).val());
						var giaTriSo = giaTri.replace(/,/g, "");

						console.log("dangKySuKien", "Có", giaTriSo);
						if (giaTriSo > 0) {
							parent.find("input[id$='no\\.soTien']").val("");
							parent.find("[name$='no\\.soTien']").val(0);
							parent.find("input[id$='\\.soDu']").val("1");
						}

						capNhatTong();
					});

			$("[id$='doiTuong\\.khoaDt']").change(function() {
				var tr = $(this).parents("tr");
				var khoaDt = $(this).val();
				console.log("khoaDt", khoaDt);
				try {
					var objs = khoaDt.split("_");
					tr.find("[id$='\\.loaiDt']").val(objs[0]);
					tr.find("[id$='\\.maDt']").val(objs[1]);
				} catch (e) {
					tr.find("[id$='\\.loaiDt']").val(0);
					tr.find("[id$='\\.maDt']").val(0);
				}
			});

			$("input[id^='taiKhoanKtthDs']").click(function() {
				var tr = $(this).parents("tr");
				var table = tr.parents("table");
				table.find("tr.active").removeClass("active");
				tr.addClass("active");

				var curRow = selectedRow;
				var newRow = tr.prop("id");
				selectedRow = newRow;
			});
			$("select[id^='taiKhoanKtthDs']").click(function() {
				var tr = $(this).parents("tr");
				var tbody = tr.parent();
				tbody.find("tr.active").removeClass("active");
				tr.addClass("active");

				var curRow = selectedRow;
				var newRow = tr.prop("id");
				selectedRow = newRow;
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

		$("#themTk").click(
				function() {
					// Thêm dòng nghiệp vụ
					var id = soDongTk - 1;
					var newId = soDongTk;
					var currentTr = $("tr#" + id);

					var newTr = "<tr>" + $(currentTr).html() + "</tr>";
					var pat = new RegExp("\\[" + id + "\\]", "g");
					var pat1 = new RegExp("Ds" + id, "g");
					newTr = newTr.replace(pat, "[" + newId + "]");
					newTr = newTr.replace(pat1, "Ds" + newId);

					// Thêm dòng mới và tăng số dòng
					$(newTr).insertAfter($(currentTr)).prop("id", newId);
					soDongTk++;

					// Làm mới nội dung
					var newLn = $("#" + newId);
					var taiKhoanObj = newLn.find("[id$='\\.maTk']");
					var nvktObj = newLn.find("[id$='\\.maNvkt']");
					var noSoTienObj = newLn.find("[id$='no\\.soTien']");
					var coSoTienObj = newLn.find("[id$='co\\.soTien']");
					var doiTuongObj = newLn.find("[id$='doiTuong\\.khoaDt']");

					newLn.find(".combobox-container").remove();
					taiKhoanObj.prop("name", "taiKhoanKtthDs[" + newId
							+ "].loaiTaiKhoan.maTk");
					taiKhoanObj.val("");
					taiKhoanObj.combobox();

					doiTuongObj.prop("name", "taiKhoanKtthDs[" + newId
							+ "].doiTuong.khoaDt");
					doiTuongObj.val("");
					doiTuongObj.combobox();

					newLn.find("[id$='\\.nhomDk']").val("");
					newLn.find("[id$='\\.lyDo']").val("");
					nvktObj.val("0");

					noSoTienObj.val("");
					noSoTienObj.number(true, thapPhan);
					coSoTienObj.val("");
					coSoTienObj.number(true, thapPhan);

					newLn.find(".error").remove();

					// Đăng ký sự kiện thay đổi
					dangKySuKien();

					$("#xoaTk").removeClass("disabled");
				});

		$("#xoaTk").click(function() {
			var tbody = $("tr#" + selectedRow).parent();
			$("tr#" + selectedRow).remove();
			var next = selectedRow * 1 + 1;
			for (var i = next; i < soDongTk; i++) {
				try {
					var tr = $("tr#" + i);
					var j = i - 1;

					tr.find("[id^='taiKhoanKtthDs']").each(function() {
						var id = $(this).prop("id");
						var pat = new RegExp("" + i, "g");
						id = id.replace(pat, j + "");
						$(this).prop("id", id);
					});

					tr.find("[name^='taiKhoanKtthDs']").each(function() {
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
			$("tr#" + selectedRow).addClass("active");

			// Cập nhật giá trị tổng nợ
			capNhatTong();

			if (soDongTk == 1) {
				$("#xoaTk").addClass("disabled");
			}
		});

		$("#lyDo").change(function() {
			for (i = 0; i < soDongTk; i++) {
				$("#taiKhoanKtthDs" + i + "\\.lyDo").val($(this).val());
			}
		});

		$("#loaiTien\\.maLt").change(
				function() {
					// Thay đổi loại tiền
					for (i = 0; i < loaiTienDs.length; i++) {
						if (loaiTienDs[i].maLt == this.value) {
							loaiTien = loaiTienDs[i];
							if (loaiTien.maLt == 'VND'
									|| loaiTien.maLt == 'VANG') {
								thapPhan = 2;
							} else {
								thapPhan = 2;
							}
							break;
						}
					}

					// Cập nhật tỷ giá
					$("#loaiTien\\.banRa").val(loaiTien.banRa);
					$("input[id$='\\.soTien']").unbind(
							'keydown.format keyup.format paste.format');
					$("input[id$='\\.soTien']").number(true, thapPhan);

					capNhatTongTxt();
				});

		$("#loaiTien\\.banRa").change(function() {
			loaiTien.banRa = $(this).val();

			capNhatTongTxt();
		});

		function khoiTao() {
			if (soDongTk > 1) {
				$("#xoaTk").removeClass("disabled");
			}

			$("input[id$='\\.nhomDk']").each(function() {
				var nhomDk = $(this).val();
				if (nhomDk == "0") {
					$(this).val("");
				}
			});

			$("select[id$='\\.doiTuong\\.khoaDt']").each(function() {
				var maTk = $(this).val();
				$(this).find("option[value=0]").remove();
				if (maTk == "0") {
					$(this).val("");
				}
				$(this).combobox();
			});

			$("select[id$='\\.loaiTaiKhoan\\.maTk']").each(function() {
				var maTk = $(this).val();
				$(this).find("option[value=0]").remove();
				if (maTk == "0") {
					$(this).val("");
				}
				$(this).combobox();
			});

			$("input[id$='\\.soTien']").number(true, thapPhan);
			$("#loaiTien\\.banRa").number(true, thapPhan);

			$("tr#" + selectedRow).addClass("active");

			// Đăng ký sự kiện thay đổi
			dangKySuKien();

			// Cập nhật tổng giá trị
			capNhatTong();
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

<h4>PHIẾU KẾ TOÁN TỔNG HỢP</h4>
<hr />
<form:hidden path="loaiCt" />
<form:hidden path="maCt" />
<form:hidden path="nghiepVu" />

<div class="row form-group">
	<label class="control-label col-sm-2" for="soCt">Số phiếu dự
		kiến (*):</label>
	<div class="col-sm-2">
		<form:input path="soCt" class="form-control" />
		<form:errors path="soCt" cssClass="error" />
	</div>
	<div class="col-sm-2">
		<button id="goiYBt" type="button" class="btn btn-info btn-sm">Gợi
			ý</button>
	</div>

	<label class="control-label col-sm-2" for=ngayLap>Ngày lập
		phiếu (*):</label>
	<div class="col-sm-4">
		<div class="input-group date datetime smallform">
			<form:input path="ngayLap" class="form-control" />
			<span class="input-group-addon"><span
				class="glyphicon glyphicon-calendar"></span></span>
		</div>
		<form:errors path="ngayLap" cssClass="error" />
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="lyDo">Lý do:(*)</label>
	<div class="col-sm-4">
		<form:textarea path="lyDo" placeholder="Lý do" cssClass="form-control" />
		<br />
		<form:errors path="lyDo" cssClass="error" />
	</div>

	<label class="control-label col-sm-2" for=ngayHt>Ngày hạch toán
		(*):</label>
	<div class="col-sm-4">
		<div class="input-group date datetime smallform">
			<form:input path="ngayHt" class="form-control" />
			<span class="input-group-addon"><span
				class="glyphicon glyphicon-calendar"></span></span>
		</div>
		<form:errors path="ngayHt" cssClass="error" />
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
			<form:input path="ngayTt" class="form-control" />
			<span class="input-group-addon"><span
				class="glyphicon glyphicon-calendar"></span></span>
		</div>
		<form:errors path="ngayTt" cssClass="error" />
	</div>
</div>

<div class="row form-group"></div>

<div class="table-responsive row form-group">
	<label class="control-label col-sm-2">Định khoản</label>
	<table id="taiKhoanTbl"
		class="table table-bordered table-hover text-center dinhkhoan">
		<thead>
			<tr>
				<th class="text-center">Tài khoản</th>
				<th class="text-center" style="width: 100px;">Nợ</th>
				<th class="text-center" style="width: 100px;">Có</th>
				<th class="text-center">Lý do</th>
				<th class="text-center">Đối tượng</th>
				<th class="text-center" style="width: 50px;">Nhóm</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${mainFinanceForm.taiKhoanKtthDs}"
				varStatus="status">
				<tr id="${status.index}">
					<td class="text-left" style="width: 120px;"><form:select
							cssClass="form-control"
							path="taiKhoanKtthDs[${status.index}].loaiTaiKhoan.maTk"
							multiple="false">
							<form:option value="0"></form:option>
							<form:options items="${loaiTaiKhoanDs}" itemValue="maTk"
								itemLabel="maTenTk" />
						</form:select> <form:hidden path="taiKhoanKtthDs[${status.index}].maNvkt" /> <form:hidden
							path="taiKhoanKtthDs[${status.index}].soDu" /> <form:errors
							path="taiKhoanKtthDs[${status.index}].loaiTaiKhoan.maTk"
							cssClass="error" /></td>
					<td style="width: 100px;"><form:input
							cssClass="text-right form-control"
							path="taiKhoanKtthDs[${status.index}].no.soTien" placeholder="0" />
						<form:errors path="taiKhoanKtthDs[${status.index}].no.soTien"
							cssClass="error" /></td>
					<td style="width: 100px;"><form:input
							cssClass="text-right form-control"
							path="taiKhoanKtthDs[${status.index}].co.soTien" placeholder="0" />
						<form:errors path="taiKhoanKtthDs[${status.index}].co.soTien"
							cssClass="error" /></td>
					<td><form:input cssClass="form-control"
							path="taiKhoanKtthDs[${status.index}].lyDo" placeholder="Lý do" />
						<form:errors path="taiKhoanKtthDs[${status.index}].lyDo"
							cssClass="error" /></td>
					<td class="text-left" style="width: 120px;"><form:select
							cssClass="form-control"
							path="taiKhoanKtthDs[${status.index}].doiTuong.khoaDt"
							multiple="false">
							<form:option value="0"></form:option>
							<form:options items="${doiTuongDs}" itemLabel="tenDt"
								itemValue="khoaDt" />
						</form:select> <form:hidden path="taiKhoanKtthDs[${status.index}].doiTuong.maDt" />
						<form:hidden
							path="taiKhoanKtthDs[${status.index}].doiTuong.loaiDt" /> <form:errors
							path="taiKhoanKtthDs[${status.index}].doiTuong.khoaDt"
							cssClass="error"></form:errors></td>
					<td style="width: 50px;"><form:input cssClass="form-control"
							path="taiKhoanKtthDs[${status.index}].nhomDk" placeholder="0" />
						<form:errors path="taiKhoanKtthDs[${status.index}].nhomDk"
							cssClass="error" /></td>
				</tr>
			</c:forEach>
			<tr>
				<td class="text-left" style="width: 120px;"><b>Tổng tiền:</b></td>
				<td class="text-right" style="width: 100px;"><span
					id="no.soTien.giaTriTxt"> <fmt:formatNumber
							value="${mainFinanceForm.soTien.soTien}"></fmt:formatNumber>
						&nbsp;VND
				</span></td>
				<td class="text-right" style="width: 100px;"><span
					id="co.soTien.giaTriTxt"> <fmt:formatNumber
							value="${mainFinanceForm.soTien.soTien}"></fmt:formatNumber>
						&nbsp;VND
				</span></td>
				<td class="text-left"><b>Quy đổi:</b></td>
				<td class="text-right" style="width: 120px;"><form:hidden
						path="soTien.giaTri" /> <span id="soTien.giaTriTxt"> <fmt:formatNumber
							value="${mainFinanceForm.soTien.giaTri}"></fmt:formatNumber>
						&nbsp;VND
				</span></td>
				<td style="width: 50px;"></td>
			</tr>
			<tr>
				<td colspan="6">
					<button id="themTk" type="button" class="btn btn-info btn-sm"
						title="Thêm nghiệp vụ kế toán">
						<span class="glyphicon glyphicon-plus"></span> Thêm
					</button>
					<button id="xoaTk" type="button"
						class="btn btn-info btn-sm disabled" title="Xóa nghiệp vụ kế toán">
						<span class="glyphicon glyphicon-plus"></span> Xóa
					</button>
				</td>
			</tr>
		</tbody>
	</table>
</div>

<div class="row form-group">
	<div class="col-sm-12">
		<a href="${url}/chungtu/ktth/danhsach" class="btn btn-info btn-sm">Danh
			sách kế toán tổng hợp</a> <a href="${url}/chungtu/ktth/danhsach"
			class="btn btn-info btn-sm">Hủy</a>
		<button id="submitBt" type="button" class="btn btn-info btn-sm">Lưu</button>
	</div>
</div>
