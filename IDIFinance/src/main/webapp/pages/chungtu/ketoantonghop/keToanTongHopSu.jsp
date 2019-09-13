
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

		$("#submitBt").click(function() {
			// Giải pháp tạm thời hack validate
			$("input[id$='\\.nhomDk']").each(function() {
				var parent = $(this).parents("tr");
				var noSoTien = parent.find("[name$='no\\.soTien']");
				var noGiaTri = parent.find("[name$='no\\.giaTri']");
				var coSoTien = parent.find("[name$='co\\.soTien']");
				var coGiaTri = parent.find("[name$='co\\.giaTri']");

				console.log("truoc no", noSoTien.val(), noGiaTri.val());
				console.log("truoc co", coSoTien.val(), coGiaTri.val());

				if (noSoTien.val() == '') {
					noSoTien.val(0);
				}
				if (noGiaTri.val() == '') {
					noGiaTri.val(0);
				}
				if (coSoTien.val() == '') {
					coSoTien.val(0);
				}
				if (coGiaTri.val() == '') {
					coGiaTri.val(0);
				}

				console.log("sau no", noSoTien.val(), noGiaTri.val());
				console.log("sau co", coSoTien.val(), coGiaTri.val());
			});

			$("#mainFinanceForm").submit();
		});

		var soDongTk = '${mainFinanceForm.taiKhoanKtthDs.size()}';
		var selectedRow = soDongTk - 1;
		var tongSoTienNo = '${mainFinanceForm.soTien.soTien}' * 1;
		var tongGiaTriNo = '${mainFinanceForm.soTien.giaTri}' * 1;
		var tongSoTienCo = '${mainFinanceForm.soTien.soTien}' * 1;
		var tongGiaTriCo = '${mainFinanceForm.soTien.giaTri}' * 1;
		var loaiTien = null;
		var thapPhan = 0;

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

			var tongSoTienNoTxt = accounting.formatNumber(tongSoTienNo,
					thapPhan, ",");
			$("#no\\.tongSoTienTxt")
					.html(tongSoTienNoTxt + " " + loaiTien.maLt);

			var tongSoTienCoTxt = accounting.formatNumber(tongSoTienCo,
					thapPhan, ",");
			$("#co\\.tongSoTienTxt")
					.html(tongSoTienCoTxt + " " + loaiTien.maLt);

			console.log("hienThiTongTien done");
		}

		function hienThiTongTienVnd() {
			console.log("hienThiTongTienVnd");

			var tongGiaTriNoTxt = accounting.formatNumber(tongGiaTriNo, 0, ",");
			$("#no\\.tongGiaTriTxt").html(tongGiaTriNoTxt + " VND");

			var tongGiaTriCoTxt = accounting.formatNumber(tongGiaTriCo, 0, ",");
			$("#co\\.tongGiaTriTxt").html(tongGiaTriCoTxt + " VND");

			console.log("hienThiTongTienVnd done");
		}

		function capNhatTongTien() {
			console.log("capNhatTongTien soTien");
			tongSoTienNo = 0;
			$("[id$='\\.no\\.soTien']").each(function() {
				try {
					var giaTri = $(this).val();
					giaTri = giaTri.replace(/,/g, "");
					console.log("tongSoTienNo", tongSoTienNo, " - ", giaTri);

					tongSoTienNo += giaTri * 1;
				} catch (e) {
					console.log("capNhatTongTien loi soTien", e);
				}
			});
			console.log("capNhatTongTien soTien tongSoTienNo", tongSoTienNo);

			tongSoTienCo = 0;
			$("[id$='\\.co\\.soTien']").each(function() {
				try {
					var giaTri = $(this).val();
					giaTri = giaTri.replace(/,/g, "");
					console.log("tongSoTienCo", tongSoTienCo, " - ", giaTri);

					tongSoTienCo += giaTri * 1;
				} catch (e) {
					console.log("capNhatTongTien loi soTien", e);
				}
			});
			console.log("capNhatTongTien soTien tongSoTienCo", tongSoTienCo);

			hienThiTongTien();
		}

		function capNhatTongTienVnd() {
			console.log("capNhatTongTienVnd giaTri");
			tongGiaTriNo = 0;
			$("[id$='\\.no\\.giaTri']").each(function() {
				try {
					var giaTri = $(this).val();
					giaTri = giaTri.replace(/,/g, "");
					console.log("tongGiaTriNo", tongGiaTriNo, " - ", giaTri);

					tongGiaTriNo += giaTri * 1;
				} catch (e) {
					console.log("capNhatTongTienVnd loi giaTri", e);
				}
			});
			console.log("capNhatTongTienVnd giaTri tongGiaTriNo", tongGiaTriNo);

			tongGiaTriCo = 0;
			$("[id$='\\.co\\.giaTri']").each(function() {
				try {
					var giaTri = $(this).val();
					giaTri = giaTri.replace(/,/g, "");
					console.log("tongGiaTriCo", tongGiaTriCo, " - ", giaTri);

					tongGiaTriCo += giaTri * 1;
				} catch (e) {
					console.log("capNhatTongTienVnd loi giaTri", e);
				}
			});
			console.log("capNhatTongTienVnd giaTri tongGiaTriCo", tongGiaTriCo);

			hienThiTongTienVnd();
		}

		function hienThiTongTienDongVnd(dong) {
			if (dong == null) {
				return;
			}
			console.log("hienThiTongTienDongVnd");

			var giaTriNo = $(dong).find("[id$='\\.no\\.giaTri']").val();
			giaTriNo = accounting.formatNumber(giaTriNo, 0, ",");
			var giaTriNoTxt = giaTriNo + " VND";
			$(dong).find("[id$='\\.no\\.giaTriTxt']").html(giaTriNoTxt);

			console.log("hienThiTongTienDongVnd giaTriNo", giaTriNo);

			var giaTriCo = $(dong).find("[id$='\\.co\\.giaTri']").val();
			giaTriCo = accounting.formatNumber(giaTriCo, 0, ",");
			var giaTriCoTxt = giaTriCo + " VND";
			$(dong).find("[id$='\\.co\\.giaTriTxt']").html(giaTriCoTxt);

			console.log("hienThiTongTienDongVnd giaTriCo", giaTriCo);

			console.log("hienThiTongTienDongVnd done");
		}

		function capNhatTongTienTyGiaDong(dong) {
			if (dong == null) {
				return;
			}
			console.log("capNhatTongTienTyGiaDong", dong);
			console.log("tygia", loaiTien.banRa);

			var soTienNo = $(dong).find("[id$='\\.no\\.soTien']").val();
			var giaTriNo = soTienNo * loaiTien.banRa;
			$(dong).find("[id$='\\.no\\.giaTri']").val(giaTriNo);
			console.log("soTienNo", soTienNo);
			console.log("giaTriNo", giaTriNo);

			var soTienCo = $(dong).find("[id$='\\.co\\.soTien']").val();
			var giaTriCo = soTienCo * loaiTien.banRa;
			$(dong).find("[id$='\\.co\\.giaTri']").val(giaTriCo);
			console.log("soTienCo", soTienCo);
			console.log("giaTriCo", giaTriCo);

			hienThiTongTienDongVnd(dong);
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

			var soTienNo = $(dong).find("[id$='\\.no\\.soTien']").val();
			if (soTienNo == 0)
				$(dong).find("[id$='\\.no\\.soTien']").val("");

			var soTienCo = $(dong).find("[id$='\\.co\\.soTien']").val();
			if (soTienCo == 0)
				$(dong).find("[id$='\\.co\\.soTien']").val("");

			var giaTriNo = $(dong).find("[id$='\\.no\\.giaTri']").val();
			giaTriNo = giaTriNo.replace(/,/g, "");
			$(dong).find("[id$='\\.no\\.giaTri']").val(giaTriNo);
			if (giaTriNo == 0)
				$(dong).find("[id$='\\.no\\.giaTri']").val("");

			var giaTriCo = $(dong).find("[id$='\\.co\\.giaTri']").val();
			giaTriCo = giaTriCo.replace(/,/g, "");
			$(dong).find("[id$='\\.co\\.giaTri']").val(giaTriCo);
			if (giaTriCo == 0)
				$(dong).find("[id$='\\.co\\.giaTri']").val("");

			hienThiTongTienDongVnd(dong);
			khoiTaoDongTien(dong);

			$(dong).find("[id$='\\.no\\.soTien']").change(function() {
				console.log("Thay doi", "no");
				var soTien = $(this).val();
				var giaTri = soTien * loaiTien.banRa;
				$(dong).find("[id$='\\.no\\.giaTri']").val(giaTri);

				console.log("soTien", soTien);
				console.log("tygia", loaiTien.banRa);
				console.log("giaTri", giaTri);

				if (soTien > 0) {
					dong.find("[id$='co\\.soTien']").val("");
					dong.find("[id$='co\\.giaTri']").val(0);
					dong.find("[id$='\\.soDu']").val("-1");
				}

				// Cap nhat hien thi dong
				hienThiTongTienDongVnd(dong);

				// Cap nhat du lieu
				capNhatTongTien();
				capNhatTongTienVnd();
			});

			$(dong).find("[id$='\\.co\\.soTien']").change(function() {
				console.log("Thay doi", "co");
				var soTien = $(this).val();
				var giaTri = soTien * loaiTien.banRa;
				$(dong).find("[id$='\\.co\\.giaTri']").val(giaTri);

				console.log("soTien", soTien);
				console.log("tygia", loaiTien.banRa);
				console.log("giaTri", giaTri);

				console.log("dangKySuKien", "Có", soTien);
				if (soTien > 0) {
					dong.find("[id$='no\\.soTien']").val("");
					dong.find("[id$='no\\.giaTri']").val(0);
					dong.find("[id$='\\.soDu']").val("1");
				}

				// Cap nhat hien thi dong
				hienThiTongTienDongVnd(dong);

				// Cap nhat du lieu
				capNhatTongTien();
				capNhatTongTienVnd();
			});

			$(dong).find("[id$='doiTuong\\.khoaDt']").change(function() {
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

			$(dong).find("select").each(function() {
				var value = $(this).val();
				$(this).find("option[value=0]").remove();
				if (value == "0") {
					$(this).val("");
				}
				$(this).combobox();
			});

			$(dong).find("td, input").click(function() {
				var tr = $(this).parents("tr");
				var table = tr.parents("table");
				table.find("tr.active").removeClass("active");
				tr.addClass("active");

				var curRow = selectedRow;
				var newRow = tr.prop("id");
				selectedRow = newRow;
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
					console.log("newTr", newTr);

					// Thêm dòng mới và tăng số dòng
					$(newTr).insertAfter($(currentTr)).prop("id", newId);
					soDongTk++;

					// Làm mới nội dung
					var newLn = $("#" + newId);
					newLn.find(".combobox-container").remove();

					var taiKhoanObj = newLn.find("[id$='\\.maTk']");
					taiKhoanObj.prop("name", "taiKhoanKtthDs[" + newId
							+ "].loaiTaiKhoan.maTk");
					taiKhoanObj.val("");

					var doiTuongObj = newLn.find("[id$='doiTuong\\.khoaDt']");
					doiTuongObj.prop("name", "taiKhoanKtthDs[" + newId
							+ "].doiTuong.khoaDt");
					doiTuongObj.val("");

					newLn.find("[id$='\\.nhomDk']").val(0);
					newLn.find("[id$='\\.lyDo']").val("");
					newLn.find("[id$='\\.soTien']").val("");
					newLn.find("[id$='\\.giaTri']").val("");
					newLn.find("[id$='\\.giaTriTxt']").html("");

					khoiTaoDong(newLn);

					newLn.find(".error").remove();

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
			capNhatTongTien();
			capNhatTongTienVnd();

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
								thapPhan = 0;
							} else {
								thapPhan = 2;
							}
							break;
						}
					}

					// Cập nhật tỷ giá
					$("input[id$='\\.banRa']").unbind(
							'keydown.format keyup.format paste.format');
					$("#loaiTien\\.banRa").number(true, thapPhan);
					$("#loaiTien\\.banRa").val(loaiTien.banRa);

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
				$("#xoaTk").removeClass("disabled");
			}

			$("#loaiTien\\.banRa").number(true, thapPhan);
			loaiTien.banRa = $("#loaiTien\\.banRa").val();

			// Đăng ký sự kiện thay đổi cho các dòng
			for (var i = 0; i < soDongTk; i++) {
				khoiTaoDong($("tr#" + i));
			}

			$("tr#" + selectedRow).addClass("active");

			// Cập nhật tổng giá trị
			capNhatTongTien();
			capNhatTongTienVnd();
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
				<th class="text-center" rowspan="2" style="width: 200px;">Lý do</th>
				<th class="text-center" rowspan="2">Tài khoản</th>
				<th class="text-center" colspan="2" style="width: 120px;">Nợ</th>
				<th class="text-center" colspan="2" style="width: 120px;">Có</th>
				<th class="text-center" rowspan="2" style="width: 150px;">Đối
					tượng</th>
				<th class="text-center" rowspan="2" style="width: 50px;">Nhóm</th>
			</tr>
			<tr>
				<th class="text-center" style="width: 120px;">Số tiền</th>
				<th class="text-center" style="width: 120px;">Quy đổi</th>
				<th class="text-center" style="width: 120px;">Số tiền</th>
				<th class="text-center" style="width: 120px;">Quy đổi</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${mainFinanceForm.taiKhoanKtthDs}"
				varStatus="status">
				<tr id="${status.index}">
					<td class="text-left" style="width: 200px;"><form:input
							cssClass="form-control"
							path="taiKhoanKtthDs[${status.index}].lyDo" placeholder="Lý do" />
						<form:errors path="taiKhoanKtthDs[${status.index}].lyDo"
							cssClass="error" /></td>
					<td class="text-left"><form:select cssClass="form-control"
							path="taiKhoanKtthDs[${status.index}].loaiTaiKhoan.maTk"
							multiple="false">
							<form:option value="0"></form:option>
							<form:options items="${loaiTaiKhoanDs}" itemValue="maTk"
								itemLabel="maTenTk" />
						</form:select> <form:hidden path="taiKhoanKtthDs[${status.index}].soDu" /> <form:errors
							path="taiKhoanKtthDs[${status.index}].loaiTaiKhoan.maTk"
							cssClass="error" /></td>
					<td style="width: 120px;"><form:input
							cssClass="text-right form-control"
							path="taiKhoanKtthDs[${status.index}].no.soTien" placeholder="0" />
						<form:errors path="taiKhoanKtthDs[${status.index}].no.soTien"
							cssClass="error" /></td>
					<td class="text-right" style="width: 120px;"><form:hidden
							path="taiKhoanKtthDs[${status.index}].no.giaTri" /><span
						id="taiKhoanKtthDs[${status.index}].no.giaTriTxt"></span></td>
					<td style="width: 120px;"><form:input
							cssClass="text-right form-control"
							path="taiKhoanKtthDs[${status.index}].co.soTien" placeholder="0" />
						<form:errors path="taiKhoanKtthDs[${status.index}].co.soTien"
							cssClass="error" /></td>
					<td class="text-right" style="width: 120px;"><form:hidden
							path="taiKhoanKtthDs[${status.index}].co.giaTri" /><span
						id="taiKhoanKtthDs[${status.index}].co.giaTriTxt"></span></td>
					<td class="text-left" style="width: 150px;"><form:select
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
				<td class="text-left" style="width: 200px;"><b>Tổng tiền:</b></td>
				<td></td>
				<td class="text-right" style="width: 120px;"><span
					id="no.tongSoTienTxt"><fmt:formatNumber
							value="${mainFinanceForm.soTien.soTien}"></fmt:formatNumber>
						&nbsp;${mainFinanceForm.loaiTien.maLt}</span></td>
				<td class="text-right" style="width: 120px;"><span
					id="no.tongGiaTriTxt"><fmt:formatNumber
							value="${mainFinanceForm.soTien.giaTri}"></fmt:formatNumber>
						&nbsp;VND</span></td>
				<td class="text-right" class="text-right" style="width: 120px;"><span
					id="co.tongSoTienTxt"><fmt:formatNumber
							value="${mainFinanceForm.soTien.soTien}"></fmt:formatNumber>
						&nbsp;${mainFinanceForm.loaiTien.maLt}</span></td>
				<td class="text-right" class="text-right" style="width: 120px;"><span
					id="co.tongGiaTriTxt"><fmt:formatNumber
							value="${mainFinanceForm.soTien.giaTri}"></fmt:formatNumber>
						&nbsp;VND</span></td>
				<td style="width: 150px;"></td>
				<td style="width: 50px;"></td>
			</tr>
			<tr>
				<td colspan="8">
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
		<a href="${url}/chungtu/ktth/xem/${mainFinanceForm.maCt}"
			class="btn btn-info btn-sm">Hủy</a>
		<button id="submitBt" type="button" class="btn btn-info btn-sm">Lưu</button>
	</div>
</div>
