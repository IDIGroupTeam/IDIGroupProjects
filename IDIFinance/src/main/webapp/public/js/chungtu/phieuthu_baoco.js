$(function() {
	$("#submitBt").click(function() {
		$("#mainFinanceForm").submit();
	});

	var soDongTk = $("#soTkLonNhat").val() * 1;
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
		var tongSoTien = $("#taiKhoanNoDs0\\.soTien\\.soTien").val();

		tongSoTien = accounting.formatNumber(tongSoTien, thapPhan, ",");
		$("#soTien\\.tongSoTienTxt").html(tongSoTien + " " + loaiTien.maLt);
		console.log("hienThiTongTien done");
	}

	function hienThiTongTienVn() {
		console.log("hienThiTongTienVn");
		var tongGiaTri = $("#taiKhoanNoDs0\\.soTien\\.giaTri").val();

		tongGiaTri = accounting.formatNumber(tongGiaTri, 0, ",");
		$("#soTien\\.tongGiaTriTxt").html(tongGiaTri + " VND");
		console.log("hienThiTongTienVn done");
	}

	function capNhatTongTien() {
		console.log("capNhatTongTien soTien");
		var tongSoTien = 0;
		$("[id^='taiKhoanCoDs'][id$='\\.soTien']").each(function() {
			try {
				var giaTri = $(this).val();
				console.log("tongSoTien", tongSoTien, "soTien", giaTri);

				tongSoTien += giaTri * 1;
			} catch (e) {
				console.log("capNhatTongTien loi soTien", e);
			}
		});
		console.log("capNhatTongTien soTien", tongSoTien);
		// tongSoTien = accounting.formatNumber(tongSoTien, thapPhan, ",");
		// tongSoTien = tongSoTien.replace(/,/g, "");
		$("#taiKhoanNoDs0\\.soTien\\.soTien").val(tongSoTien);

		hienThiTongTien();
	}

	function capNhatTongTienVnd() {
		console.log("capNhatTongTienVnd giaTri");
		var tongGiaTri = 0;
		$("[id^='taiKhoanCoDs'][id$='\\.giaTri']").each(function() {
			try {
				var giaTri = $(this).val();
				console.log("tongGiaTri", tongGiaTri, "giaTri", giaTri);

				tongGiaTri += giaTri * 1;
			} catch (e) {
				console.log("capNhatTongTienVnd loi giaTri", e);
			}
		});
		console.log("capNhatTongTienVnd giaTri", tongGiaTri);
		// tongGiaTri = accounting.formatNumber(tongGiaTri, 0, ",");
		// tongGiaTri = tongGiaTri.replace(/,/g, "");
		$("#taiKhoanNoDs0\\.soTien\\.giaTri").val(tongGiaTri);

		hienThiTongTienVn();
	}

	function hienThiTongTienDongVn(dong) {
		if (dong == null) {
			return;
		}
		console.log("hienThiTongTienDongVn");

		var giaTri = $(dong).find("[id$='\\.giaTri']").val();
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

		var soTien = $(dong).find("[id$='\\.soTien']").val();
		console.log("soTien", soTien);
		console.log("tygia", loaiTien.banRa);
		var giaTri = soTien * loaiTien.banRa;
		// giaTri = accounting.formatNumber(giaTri, 0, ",");
		// giaTri = giaTri.replace(/,/g, "");
		$(dong).find("[id$='\\.giaTri']").val(giaTri);
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
		var soTien = $(dong).find("[id$='\\.soTien']").val();
		if (soTien == 0)
			$(dong).find("[id$='\\.soTien']").val("");

		var giaTri = $(dong).find("[id$='\\.giaTri']").val();
		giaTri = giaTri.replace(/,/g, "");
		$(dong).find("[id$='\\.giaTri']").val(giaTri);
		if (giaTri == 0)
			$(dong).find("[id$='\\.giaTri']").val("");

		hienThiTongTienDongVn(dong);
		khoiTaoDongTien(dong);

		// Dang ky su kien
		$(dong).find("[id$='\\.soTien']").change(function() {
			var soTien = $(this).val();
			console.log("soTien", soTien);
			console.log("tygia", loaiTien.banRa);
			var giaTri = soTien * loaiTien.banRa;
			// giaTri = accounting.formatNumber(giaTri, 0, ",");
			// giaTri = giaTri.replace(/,/g, "");
			$(dong).find("[id$='\\.giaTri']").val(giaTri);
			console.log("giaTri", giaTri);

			// Cap nhat hien thi dong
			hienThiTongTienDongVn(dong);

			// Cap nhat du lieu
			capNhatTongTien();
			capNhatTongTienVnd();
		});

		$(dong).find("[id$='\\.maTk']").each(function() {
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

				$(newTr).insertBefore($(currentTr).prev()).prop("id", newId);
				soDongTk++;

				var newLn = $("#" + newId);

				var taiKhoanObj = newLn.find("[id$='\\.maTk']");
				newLn.find(".combobox-container").remove();
				taiKhoanObj.prop("name", "taiKhoanCoDs[" + newId
						+ "].loaiTaiKhoan.maTk");
				taiKhoanObj.val("");

				newLn.find("[id$='\\.lyDo']").val("");
				newLn.find("[id$='\\.soTien']").val("");
				newLn.find("[id$='\\.giaTri']").val("");
				newLn.find("[id$='\\.soTien\\.giaTriTxt']").html("");

				khoiTaoDong(newLn);

				newLn.find("[id$='\\.errors']").remove();

				$("#xoaTkCo").removeClass("disabled");
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

				tr.find("[id^='taiKhoanCoDs']").each(function() {
					var id = $(this).prop("id");
					var pat = new RegExp("" + i, "g");
					id = id.replace(pat, j + "");
					$(this).prop("id", id);
				});

				tr.find("[name^='taiKhoanCoDs']").each(function() {
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
			$("#xoaTkCo").addClass("disabled");
		}
	});

	$("#lyDo").change(function() {
		$("#taiKhoanNoDs0\\.lyDo").val($(this).val());
		for (i = 0; i < soDongTk; i++) {
			$("#taiKhoanCoDs" + i + "\\.lyDo").val($(this).val());
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
			$("#xoaTkCo").removeClass("disabled");
		}

		$("#loaiTien\\.banRa").number(true, thapPhan);
		loaiTien.banRa = $("#loaiTien\\.banRa").val();

		// Đăng ký sự kiện thay đổi cho các dòng
		for (var i = 0; i < soDongTk; i++) {
			khoiTaoDong($("tr#" + i));
		}

		$("tr#" + selectedRow).addClass("active");

		var giaTri = $("#taiKhoanNoDs0\\.soTien\\.giaTri").val();
		giaTri = giaTri.replace(/,/g, "");
		$("#taiKhoanNoDs0\\.soTien\\.giaTri").val(giaTri);

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