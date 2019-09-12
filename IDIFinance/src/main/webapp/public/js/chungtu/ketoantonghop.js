$(function() {
	$("#submitBt").click(function() {
		// Giải pháp tạm thời hack validate
		$("input[id$='\\.nhomDk']").each(function() {
			var parent = $(this).parents("tr");
			var noSoTien = parent.find("[name$='no\\.soTien']");
			var coSoTien = parent.find("[name$='co\\.soTien']");

			console.log("truoc", "no", noSoTien.val(), "co", coSoTien.val());
			if (noSoTien.val() != '' || coSoTien.val() != '') {
				if (noSoTien.val() == '') {
					noSoTien.val(0);
				} else if (coSoTien.val() == '') {
					coSoTien.val(0);
				}
			}
			console.log("sau", "no", noSoTien.val(), "co", coSoTien.val());
		});

		$("#mainFinanceForm").submit();
	});

	var soDongTk = $("#soTkLonNhat").val() * 1;
	var selectedRow = soDongTk - 1;
	var tongSoTienNo = 0;
	var tongGiaTriNo = 0;
	var tongSoTienCo = 0;
	var tongGiaTriCo = 0;
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

		var tongSoTienNoTxt = accounting.formatNumber(tongSoTienNo, thapPhan,
				",");
		$("#no\\.tongSoTienTxt").html(tongSoTienNoTxt + " " + loaiTien.maLt);

		var tongSoTienCoTxt = accounting.formatNumber(tongSoTienCo, thapPhan,
				",");
		$("#co\\.tongSoTienTxt").html(tongSoTienCoTxt + " " + loaiTien.maLt);

		console.log("hienThiTongTien done");
	}

	function hienThiTongTienVn() {
		console.log("hienThiTongTienVn");

		var tongGiaTriNoTxt = accounting.formatNumber(tongGiaTriNo, 0, ",");
		$("#no\\.tongGiaTriTxt").html(tongGiaTriNoTxt + " VND");

		var tongGiaTriCoTxt = accounting.formatNumber(tongGiaTriCo, 0, ",");
		$("#co\\.tongGiaTriTxt").html(tongGiaTriCoTxt + " VND");

		console.log("hienThiTongTienVn done");
	}

	function capNhatTongTien() {
		console.log("capNhatTongTien soTien");
		tongSoTienNo = 0;
		$("[id$='\\.no\\.soTien']").each(function() {
			try {
				var giaTri = $(this).val();
				console.log("tongSoTienNo", tongSoTienNo, " - ", giaTri);

				tongSoTienNo += giaTri * 1;
			} catch (e) {
				console.log("capNhatTongTien loi soTien", e);
			}
		});
		console.log("capNhatTongTien soTien tongSoTienNo", tongSoTienNo);
		// tongSoTienNo = accounting.formatNumber(tongSoTienNo, thapPhan, ",");
		// tongSoTienNo = tongSoTienNo.replace(/,/g, "");

		tongSoTienCo = 0;
		$("[id$='\\.co\\.soTien']").each(function() {
			try {
				var giaTri = $(this).val();
				console.log("tongSoTienCo", tongSoTienCo, " - ", giaTri);

				tongSoTienCo += giaTri * 1;
			} catch (e) {
				console.log("capNhatTongTien loi soTien", e);
			}
		});
		console.log("capNhatTongTien soTien tongSoTienCo", tongSoTienCo);
		// tongSoTienCo = accounting.formatNumber(tongSoTienCo, thapPhan, ",");
		// tongSoTienCo = tongSoTienCo.replace(/,/g, "");

		hienThiTongTien();
	}

	function capNhatTongTienVnd() {
		console.log("capNhatTongTienVnd giaTri");
		tongGiaTriNo = 0;
		$("[id$='\\.no\\.giaTri']").each(function() {
			try {
				var giaTri = $(this).val();
				console.log("tongGiaTriNo", tongGiaTriNo, " - ", giaTri);

				tongGiaTriNo += giaTri * 1;
			} catch (e) {
				console.log("capNhatTongTienVnd loi giaTri", e);
			}
		});
		console.log("capNhatTongTienVnd giaTri tongGiaTriNo", tongGiaTriNo);
		// tongGiaTriNo = accounting.formatNumber(tongGiaTriNo, 0, ",");
		// tongGiaTriNo = tongGiaTriNo.replace(/,/g, "");

		tongGiaTriCo = 0;
		$("[id$='\\.co\\.giaTri']").each(function() {
			try {
				var giaTri = $(this).val();
				console.log("tongGiaTriCo", tongGiaTriCo, " - ", giaTri);

				tongGiaTriCo += giaTri * 1;
			} catch (e) {
				console.log("capNhatTongTienVnd loi giaTri", e);
			}
		});
		console.log("capNhatTongTienVnd giaTri tongGiaTriCo", tongGiaTriCo);
		// tongGiaTriCo = accounting.formatNumber(tongGiaTriCo, 0, ",");
		// tongGiaTriCo = tongGiaTriCo.replace(/,/g, "");

		hienThiTongTienVn();
	}

	function hienThiTongTienDongVn(dong) {
		if (dong == null) {
			return;
		}
		console.log("hienThiTongTienDongVn");

		var giaTriNo = $(dong).find("[id$='\\.no\\.giaTri']").val();
		giaTriNo = accounting.formatNumber(giaTriNo, 0, ",");
		var giaTriNoTxt = giaTriNo + " VND";
		$(dong).find("[id$='\\.no\\.giaTriTxt']").html(giaTriNoTxt);

		var giaTriCo = $(dong).find("[id$='\\.co\\.giaTri']").val();
		giaTriCo = accounting.formatNumber(giaTriCo, 0, ",");
		var giaTriCoTxt = giaTriCo + " VND";
		$(dong).find("[id$='\\.co\\.giaTriTxt']").html(giaTriCoTxt);

		console.log("hienThiTongTienDongVn done");
	}

	function capNhatTongTienTyGiaDong(dong) {
		if (dong == null) {
			return;
		}
		console.log("capNhatTongTienTyGiaDong", dong);
		console.log("tygia", loaiTien.banRa);

		var soTienNo = $(dong).find("[id$='\\.no\\.soTien']").val();
		var giaTriNo = soTienNo * loaiTien.banRa;
		// giaTriNo = accounting.formatNumber(giaTriNo, 0, ",");
		// giaTriNo = giaTriNo.replace(/,/g, "");
		$(dong).find("[id$='\\.no\\.giaTri']").val(giaTriNo);
		console.log("soTienNo", soTienNo);
		console.log("giaTriNo", giaTriNo);

		var soTienCo = $(dong).find("[id$='\\.co\\.soTien']").val();
		var giaTriCo = soTienCo * loaiTien.banRa;
		// giaTriCo = accounting.formatNumber(giaTriCo, 0, ",");
		// giaTriCo = giaTriCo.replace(/,/g, "");
		$(dong).find("[id$='\\.co\\.giaTri']").val(giaTriCo);
		console.log("soTienCo", soTienCo);
		console.log("giaTriCo", giaTriCo);

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

		$(dong).find("input[id$='\\.nhomDk']").each(function() {
			var nhomDk = $(this).val();
			if (nhomDk == "0") {
				$(this).val("");
			}
		});

		hienThiTongTienDongVn(dong);
		khoiTaoDongTien(dong);

		$(dong).find("[id$='\\.no\\.soTien']").change(function() {
			console.log("Thay doi", "no");
			var soTien = $(this).val();
			var giaTri = soTien * loaiTien.banRa;
			// giaTri = accounting.formatNumber(giaTri, 0, ",");
			// giaTri = giaTri.replace(/,/g, "");
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
			hienThiTongTienDongVn(dong);

			// Cap nhat du lieu
			capNhatTongTien();
			capNhatTongTienVnd();
		});

		$(dong).find("[id$='\\.co\\.soTien']").change(function() {
			console.log("Thay doi", "co");
			var soTien = $(this).val();
			var giaTri = soTien * loaiTien.banRa;
			// giaTri = accounting.formatNumber(giaTri, 0, ",");
			// giaTri = giaTri.replace(/,/g, "");
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
			hienThiTongTienDongVn(dong);

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

				newLn.find("[id$='\\.nhomDk']").val("");
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
						if (loaiTien.maLt == 'VND' || loaiTien.maLt == 'VANG') {
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
		// hienThiTongTien();
		// hienThiTongTienVn();
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