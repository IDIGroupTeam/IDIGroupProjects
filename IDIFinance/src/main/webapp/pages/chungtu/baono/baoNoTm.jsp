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
		var thapPhan = 0;

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
				if (tien.maLt == 'VND' || tien.maLt == 'VANG') {
					thapPhan = 0;
				} else {
					thapPhan = 2;
				}
			}
		}

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

		function hienThiTongTien() {
			console.log("hienThiTongTien");
			var tongSoTien = $(
					"input[id^='taiKhoanCoDs'][id$='\\.soTien\\.soTien']")
					.val();

			tongSoTien = accounting.formatNumber(tongSoTien, thapPhan, ",");
			$("#soTien\\.tongSoTienTxt").html(tongSoTien + " " + loaiTien.maLt);
			console.log("hienThiTongTien done");
		}

		function hienThiTongTienVn() {
			console.log("hienThiTongTienVn");
			var tongGiaTri = $(
					"input[id^='taiKhoanCoDs'][id$='\\.soTien\\.giaTri']")
					.val();

			tongGiaTri = accounting.formatNumber(tongGiaTri, 0, ",");
			$("#soTien\\.tongGiaTriTxt").html(tongGiaTri + " VND");
			console.log("hienThiTongTienVn done");
		}

		function capNhatTongTien() {
			console.log("capNhatTongTien soTien");
			var tongSoTien = 0;
			$("input[id^='taiKhoanNoDs'][id$='\\.soTien\\.soTien']").each(
					function() {
						try {
							var giaTri = $(this).val();
							console.log("tongSoTien", tongSoTien, "soTien",
									giaTri);

							tongSoTien += giaTri * 1;
						} catch (e) {
							console.log("capNhatTongTien loi soTien", e);
						}
					});
			tongSoTien = accounting.formatNumber(tongSoTien, thapPhan, ",");
			tongSoTien = tongSoTien.replace(/,/g, "");
			$("#taiKhoanCoDs0\\.soTien\\.soTien").val(tongSoTien);

			hienThiTongTien();
		}

		function capNhatTongTienVnd() {
			console.log("capNhatTongTienVnd giaTri");
			var tongGiaTri = 0;
			$("input[id^='taiKhoanNoDs'][id$='\\.soTien\\.giaTri']").each(
					function() {
						try {
							var giaTri = $(this).val();
							console.log("tongGiaTri", tongGiaTri, "giaTri",
									giaTri);

							tongGiaTri += giaTri * 1;
						} catch (e) {
							console.log("capNhatTongTienVnd loi giaTri", e);
						}
					});
			tongGiaTri = accounting.formatNumber(tongGiaTri, 0, ",");
			tongGiaTri = tongGiaTri.replace(/,/g, "");
			$("#taiKhoanCoDs0\\.soTien\\.giaTri").val(tongGiaTri);

			hienThiTongTienVn();
		}

		function hienThiTongTienDongVn(dong) {
			if (dong == null) {
				return;
			}
			console.log("hienThiTongTienDongVn");

			var giaTri = $(dong).find("[id$='\\.soTien\\.giaTri']").val();
			giaTri = accounting.formatNumber(giaTri, 0, ",");
			var giaTriTxt = giaTri + " VND";
			$(dong).find("[id$='\\.soTien\\.giaTriTxt']").html(giaTriTxt);
			console.log("hienThiTongTienDongVn done");
		}

		function capNhatTongTienTyGiaDong(dong) {
			if (dong == null) {
				return;
			}
			console.log("capNhatTongTienTyGiaDong", dong);

			var soTien = $(dong).find("[id$='\\.soTien\\.soTien']").val();
			console.log("soTien", soTien);
			console.log("tygia", loaiTien.banRa);
			var giaTri = soTien * loaiTien.banRa;
			giaTri = accounting.formatNumber(giaTri, 0, ",");
			giaTri = giaTri.replace(/,/g, "");
			$(dong).find("[id$='\\.soTien\\.giaTri']").val(giaTri);
			console.log("giaTri", giaTri);

			hienThiTongTienDongVn(dong);
		}

		function capNhatTongTienTyGia() {
			console.log("capNhatTongTienTyGia");

			// Cập nhật từng dòng dữ liệu
			for (var i = 0; i < soDongTk; i++) {
				capNhatTongTienTyGiaDong($("tr#" + i));
			}

			// Cập nhật hiển thị ngoại tệ
			hienThiTongTien();

			// Cập nhật vnd
			capNhatTongTienVnd();
		}

		function khoiTaoDongTien(dong) {
			if (dong == null) {
				return;
			}

			$(dong).find("[id$='\\.soTien']").unbind(
					'keydown.format keyup.format paste.format');
			$(dong).find("[id$='\\.soTien']").number(true, thapPhan);
		}

		function khoiTaoDong(dong) {
			if (dong == null) {
				return;
			}
			console.log("khoiTaoDong dong", $(dong).prop("id"));

			// Khoi tao gia tri mac dinh
			var soTien = $(dong).find("[id$='\\.soTien\\.soTien']").val();
			if (soTien == 0)
				$(dong).find("[id$='\\.soTien\\.soTien']").val("");

			var giaTri = $(dong).find("[id$='\\.soTien\\.giaTri']").val();
			giaTri = giaTri.replace(/,/g, "");
			$(dong).find("[id$='\\.soTien\\.giaTri']").val(giaTri);
			if (giaTri == 0)
				$(dong).find("[id$='\\.soTien\\.giaTri']").val("");

			hienThiTongTienDongVn(dong);
			khoiTaoDongTien(dong);

			// Dang ky su kien
			$(dong).find("[id$='\\.soTien\\.soTien']").change(function() {
				var soTien = $(this).val();
				console.log("soTien", soTien);
				console.log("tygia", loaiTien.banRa);
				var giaTri = soTien * loaiTien.banRa;
				giaTri = accounting.formatNumber(giaTri, 0, ",");
				giaTri = giaTri.replace(/,/g, "");
				$(dong).find("[id$='\\.soTien\\.giaTri']").val(giaTri);
				console.log("giaTri", giaTri);

				// Cap nhat hien thi dong
				hienThiTongTienDongVn(dong);

				// Cap nhat du lieu
				capNhatTongTien();
				capNhatTongTienVnd();
			});

			$(dong).find("[id$='\\.loaiTaiKhoan\\.maTk']").each(function() {
				var maTkCo = $(this).val();
				$(this).find("option[value=0]").remove();
				if (maTkCo == "0") {
					$(this).val("");
				}
				$(this).combobox();
			});

			$(dong).find(" input, td").click(function() {
				var tbody = $(dong).parent();
				tbody.find("tr.active").removeClass("active");
				dong.addClass("active");

				var curRow = selectedRow;
				var newRow = dong.prop("id");
				selectedRow = newRow;
				console.log("selectedRow", "current", curRow, "new", newRow);
			});
		}

		$("#themTkNo").click(
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

					var newLn = $("#" + newId);

					var taiKhoanObj = newLn.find("[id$='\\.maTk']");
					newLn.find(".combobox-container").remove();
					taiKhoanObj.prop("name", "taiKhoanNoDs[" + newId
							+ "].loaiTaiKhoan.maTk");
					taiKhoanObj.val("");

					newLn.find("[id$='\\.lyDo']").val("");
					newLn.find("[id$='\\.soTien\\.soTien']").val("");
					newLn.find("[id$='\\.soTien\\.giaTri']").val("");
					newLn.find("[id$='\\.soTien\\.giaTriTxt']").html("");

					khoiTaoDong(newLn);

					newLn.find("[id$='\\.errors']").remove();

					$("#xoaTkNo").removeClass("disabled");
				});

		$("#xoaTkNo").click(function() {
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
			capNhatTongTien();
			capNhatTongTienVnd();

			if (soDongTk == 1) {
				$("#xoaTkNo").addClass("disabled");
			}
		});

		$("#lyDo").change(function() {
			$("#taiKhoanCoDs0\\.lyDo").val($(this).val());
			for (i = 0; i < soDongTk; i++) {
				$("#taiKhoanNoDs" + i + "\\.lyDo").val($(this).val());
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
								thapPhan = 0;
							} else {
								thapPhan = 2;
							}
							break;
						}
					}

					// Cập nhật tỷ giá
					$("#loaiTien\\.banRa").val(loaiTien.banRa);
					$("input[id$='\\.banRa']").unbind(
							'keydown.format keyup.format paste.format');
					$("#loaiTien\\.banRa").number(true, thapPhan);

					for (var i = 0; i < soDongTk; i++) {
						khoiTaoDongTien($("tr#" + i));
					}

					capNhatTongTienTyGia();
				});

		$("#loaiTien\\.banRa").change(function() {
			loaiTien.banRa = $(this).val();
			capNhatTongTienTyGia();
		});

		function khoiTao() {
			if (soDongTk > 1) {
				$("#xoaTkNo").removeClass("disabled");
			}

			$("#loaiTien\\.banRa").number(true, thapPhan);
			loaiTien.banRa = $("#loaiTien\\.banRa").val();

			// Đăng ký sự kiện thay đổi cho các dòng
			for (var i = 0; i < soDongTk; i++) {
				khoiTaoDong($("tr#" + i));
			}

			$("tr#" + selectedRow).addClass("active");

			var giaTri = $("#taiKhoanCoDs0\\.soTien\\.giaTri").val();
			giaTri = giaTri.replace(/,/g, "");
			$("#taiKhoanCoDs0\\.soTien\\.giaTri").val(giaTri);

			hienThiTongTien();
			hienThiTongTienVn();
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
		<form:hidden path="taiKhoanCoDs[0].soTien.giaTri" />
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
				<th class="text-center">Lý do</th>
				<th class="text-center">Tài khoản nợ</th>
				<th class="text-center">Giá trị</th>
				<th class="text-center">Chuyển đổi VND</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${mainFinanceForm.taiKhoanNoDs}" varStatus="status">
				<tr id="${status.index}">
					<td><form:input cssClass="form-control"
							path="taiKhoanNoDs[${status.index}].lyDo" placeholder="Lý do" />
						<form:errors path="taiKhoanNoDs[${status.index}].lyDo"
							cssClass="error"></form:errors></td>
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
					<td class="text-right"><form:hidden
							cssClass="form-control text-right"
							path="taiKhoanNoDs[${status.index}].soTien.giaTri"
							placeholder="0.0" /><span
						id="taiKhoanNoDs[${status.index}].soTien.giaTriTxt"></span></td>
				</tr>
			</c:forEach>
			<tr>
				<td class="text-left"><b>Thành tiền:</b></td>
				<td></td>
				<td class="text-right"><span id="soTien.tongSoTienTxt">
						<fmt:formatNumber value="${mainFinanceForm.soTien.soTien}"></fmt:formatNumber>
						&nbsp;${mainFinanceForm.soTien.loaiTien.maLt}
				</span></td>
				<td class="text-right"><span id="soTien.tongGiaTriTxt">
						<fmt:formatNumber value="${mainFinanceForm.soTien.giaTri}"></fmt:formatNumber>
						&nbsp;VND
				</span></td>
			</tr>
			<tr>
				<td colspan="4">
					<button id="themTkNo" type="button" class="btn btn-info btn-sm"
						title="Thêm tài khoản ghi nợ">
						<span class="glyphicon glyphicon-plus"></span> Thêm
					</button>
					<button id="xoaTkNo" type="button"
						class="btn btn-info btn-sm disabled"
						title="Xóa tài khoản ghi nợ cuối cùng">
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
