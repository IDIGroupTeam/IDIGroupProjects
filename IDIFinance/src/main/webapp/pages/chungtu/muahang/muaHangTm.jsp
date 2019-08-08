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
		$("#mainFinanceForm").attr("action", "${url}/chungtu/muahang/luu");
		$("#mainFinanceForm").attr("method", "POST");

		$("#submitBt").click(function() {
			$("#mainFinanceForm").submit();
		});

		var soDongTk = '${mainFinanceForm.soHangHoa}';
		var currentTr = 0;
		var hangTienDong = "";
		var khoDong = "";
		var thueDong = "";
		var chiPhiDong = "";
		var ktthDong = "";
		var patt = new RegExp("\\[" + (soDongTk - 1) + "\\]", "g");
		var patt1 = new RegExp("Ds" + (soDongTk - 1), "g");

		var loaiTien = null;
		var url = "${url}/chungtu/nhacungcap/";
		var loaiDt = 3;
		var thapPhan = 2;

		// Đăng ký autocomplete
		var autocomplete = $('#doiTuong\\.tenDt').bootcomplete({
			url : url,
			minLength : 1,
			idFieldName : "doiTuong\\.maDt",
			preprocess : function(json) {
				var jsonTmpl = new Array(json.length);
				if (loaiDt == 1) {
					$.each(json, function(i, j) {
						j.id = j.employeedId;
						j.label = j.fullName;
						jsonTmpl[i] = j;
					});
				} else if (loaiDt == 2) {
					$.each(json, function(i, j) {
						j.id = j.maKh;
						j.label = j.tenKh;
						jsonTmpl[i] = j;
					});
				} else if (loaiDt == 3) {
					$.each(json, function(i, j) {
						j.id = j.maNcc;
						j.label = j.tenNcc;
						jsonTmpl[i] = j;
					});
				}

				return jsonTmpl;
			},
			selectResult : function(selectedData) {
				if (selectedData) {
					if (loaiDt == 1) {
						$('#doiTuong\\.maThue').val("");
						$('#doiTuong\\.diaChi').val(selectedData.department);
						$('#doiTuong\\.nguoiNop').val(selectedData.fullName);
					} else if (loaiDt == 2) {
						$('#doiTuong\\.maThue').val(selectedData.maThue);
						$('#doiTuong\\.diaChi').val(selectedData.diaChi);
						$('#doiTuong\\.nguoiNop').val(selectedData.tenKh);
					} else if (loaiDt == 3) {
						$('#doiTuong\\.maThue').val(selectedData.maThue);
						$('#doiTuong\\.diaChi').val(selectedData.diaChi);
						$('#doiTuong\\.nguoiNop').val(selectedData.tenNcc);
					}
				}
			}
		});

		// Thay đổi url cho autocomplete
		$('#doiTuong\\.loaiDt').change(function() {
			cleanForm();
			loaiDt = this.value;
			if (loaiDt == 1) {
				url = "${url}/chungtu/nhanvien/";
			} else if (loaiDt == 2) {
				url = "${url}/chungtu/khachhang/";
			} else if (loaiDt == 3) {
				url = "${url}/chungtu/nhacungcap/";
			} else {
				// Là khách vãng lai, chỉ cần điền họ và tên người nộp tiền
				$("#doiTuong\\.maDt").val("1");
				$("#doiTuong\\.tenDt").val("Khách vãng lai");

				$("#doiTuong\\.nguoiNop").val("Khách vãng lai");
				url = "";
			}

			autocomplete.changeUrl(url);
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
					thapPhan = 2;
				} else {
					thapPhan = 2;
				}
			}
		}

		function tongTienHangHoa(id) {
			// TÍNH TỔNG TIỀN
			var tongTien = 0;
			var soLuong = $.trim($("#hangHoaDs" + id + "\\.soLuong").val())
			var gia = $.trim($("#hangHoaDs" + id + "\\.donGia\\.soTien").val())

			if (soLuong != '' && !isNaN(soLuong) && gia != '' && !isNaN(gia)) {
				tongTien = soLuong * gia;
			}

			// CẬP NHẬT HIỂN THỊ
			var tongTienVn = tongTien * loaiTien.banRa;
			tongTienVn = accounting.formatNumber(tongTienVn, thapPhan, ",")
					+ " VND";
			console.log("tongTienHangHoa id", id, "soLuong", soLuong, "gia",
					gia, "loaiTien.banRa", loaiTien.banRa, "tongTien",
					tongTien, "tongTienVn", tongTienVn);

			// Hiển thị tổng tiền ở tab hàng tiền
			var tongTxt = accounting.formatNumber(tongTien, thapPhan, ",")
					+ " " + loaiTien.maLt;

			// Hiển thị tổng tiền ở tab thuế
			$("#hangHoaDs" + id + "\\.thue\\.tongTien").html(tongTxt);

			if (loaiTien.maLt != "VND") {
				tongTxt += "<br/>" + tongTienVn;
			}
			$("#hangHoaDs" + id + "\\.hangTien\\.tongTien").html(tongTxt);

			return tongTien;
		}

		function tongThueHangHoa(id) {
			// Tổng tiền hàng hóa
			var ketQua = new Object();
			var tong = tongTienHangHoa(id) * loaiTien.banRa;
			var tongTien = tong;

			// Cập nhật tiền thuế nhập khẩu
			try {
				var thueSuatNk = $("#hangHoaDs" + id + "\\.thueSuatNk").val();
				if (!isNaN(thueSuatNk)) {
					if (thueSuatNk > 0) {
						var thue = tong * thueSuatNk / 100;
						$("#hangHoaDs" + id + "\\.tkThueNk\\.soTien\\.giaTri")
								.val(thue);
					}
				}

				var thueNk = $(
						"#hangHoaDs" + id + "\\.tkThueNk\\.soTien\\.giaTri")
						.val();
				if (!isNaN(thueNk)) {
					tong += eval(thueNk);
					tongTien += eval(thueNk);
				}
			} catch (e) {
				console.log("tongThueHangHoa tkThueNk error", e);
			}

			// Cập nhật tiền thuế ttdb
			try {
				var thueSuatTtdb = $("#hangHoaDs" + id + "\\.thueSuatTtdb")
						.val();
				if (!isNaN(thueSuatTtdb)) {
					if (thueSuatTtdb > 0) {
						var thue = tong * thueSuatTtdb / 100;
						$("#hangHoaDs" + id + "\\.tkThueTtdb\\.soTien\\.giaTri")
								.val(thue);
					}
				}

				var thueTtdb = $(
						"#hangHoaDs" + id + "\\.tkThueTtdb\\.soTien\\.giaTri")
						.val();
				if (!isNaN(thueTtdb)) {
					tong += eval(thueTtdb);
					tongTien += eval(thueTtdb);
				}
			} catch (e) {
				console.log("tongThueHangHoa tkThueTtdb error", e);
			}

			// Tính tiền thuế giá trị gia tăng
			try {
				var thueSuatGtgt = $("#hangHoaDs" + id + "\\.thueSuatGtgt")
						.val();
				if (!isNaN(thueSuatGtgt)) {
					if (thueSuatGtgt > 0) {
						var thue = tong * thueSuatGtgt / 100;
						$("#hangHoaDs" + id + "\\.tkThueGtgt\\.soTien\\.giaTri")
								.val(thue);
					}
				}

				var tinhChatCt = $("#tinhChatCt").val();
				var maTk = $(
						"#hangHoaDs" + id
								+ "\\.tkThueGtgt\\.loaiTaiKhoan\\.maTk").val();
				var thueGtgt = $(
						"#hangHoaDs" + id + "\\.tkThueGtgt\\.soTien\\.giaTri")
						.val();
				if (tinhChatCt == 2 || ((tinhChatCt != 2) && (maTk == '0'))) {
					// Đây là trường hợp tính thuế GTGT theo phương pháp trực tiếp, 
					// Tiền thuế GTGT được cộng vào giá nhập kho

					// Nếu là mua hàng nước ngoài
					// và tính theo phương pháp khấu trừ thì lập một phiếu kế toán tổng hợp riêng, 
					// form này sẽ để trắng các cột liên quan đến thuế GTGT

					// Hoặc
					// Nếu là mua trong nước
					// Tính theo phương pháp khấu trừ thì phải chọn mã tài khoản

					if (!isNaN(thueGtgt)) {
						tong += eval(thueGtgt);
					}
				}

				if (!isNaN(thueGtgt)) {
					tongTien += eval(thueGtgt);
				}
			} catch (e) {
				console.log("tongThueHangHoa tkThueGtgt error", e);
			}

			ketQua.thue = tong;
			ketQua.tien = tongTien;
			console.log("tongThueHangHoa id", id, "thue", ketQua.thue, "tien",
					ketQua.tien);
			return ketQua;
		}

		function capNhatTongTienHangHoa(i) {
			// VỚI MỖI LOẠI HÀNG HÓA 
			// CẬP NHẬT HÀNG TIỀN
			// Thực hiện việc này trong tongThueHangHoa

			// CẬP NHẬT THUẾ
			// Kết quả trả về là tổng hàng tiền và tổng thuế
			console.log("capNhatTongTienHangHoa id", i);
			var tong = tongThueHangHoa(i);

			// CẬP NHẬT CHI PHÍ

			// CẬP NHẬT GIÁ NHẬP KHO: tổng hàng tiền, tổng thuế, tổng chi phí
			try {
				var tongGiaKho = tong.thue / loaiTien.banRa;
				var tongGiaKhoVn = tong.thue;
				var tongGiaKhoTxt = "";

				var soLuong = $.trim($("#hangHoaDs" + i + "\\.soLuong").val());
				var giaKho = tongGiaKho / soLuong;
				var giaKhoVn = tongGiaKhoVn / soLuong;
				var giaKhoTxt = "";

				var tongCongNo = tong.tien / loaiTien.banRa;
				var tongCongNoVn = tong.tien;
				var tongCongNoTxt = "";

				if (!isNaN(giaKho)) {
					$("#hangHoaDs" + i + "\\.giaKho\\.soTien").val(giaKho);
				} else {
					$("#hangHoaDs" + i + "\\.giaKho\\.soTien").val(0);
				}

				if (loaiTien.maLt != "VND") {
					tongGiaKhoTxt = accounting.formatNumber(tongGiaKho,
							thapPhan, ",")
							+ " " + loaiTien.maLt;
					tongGiaKhoTxt = tongGiaKhoTxt
							+ "<br/>"
							+ accounting.formatNumber(tongGiaKhoVn, thapPhan,
									",") + " VND";

					giaKhoTxt = accounting.formatNumber(giaKho, thapPhan, ",")
							+ " " + loaiTien.maLt;
					giaKhoTxt = giaKhoTxt + "<br/>"
							+ accounting.formatNumber(giaKhoVn, thapPhan, ",")
							+ " VND";

					tongCongNoTxt = accounting.formatNumber(tongCongNo,
							thapPhan, ",")
							+ " " + loaiTien.maLt;
					tongCongNoTxt = tongCongNoTxt
							+ "<br/>"
							+ accounting.formatNumber(tongCongNoVn, thapPhan,
									",") + " VND";
				} else {
					tongGiaKhoTxt = accounting.formatNumber(tongGiaKhoVn,
							thapPhan, ",")
							+ " VND";

					giaKhoTxt = accounting
							.formatNumber(giaKhoVn, thapPhan, ",")
							+ " VND";

					tongCongNoTxt = accounting.formatNumber(tongCongNoVn,
							thapPhan, ",")
							+ " VND";
				}

				$("#hangHoaDs" + i + "\\.giaKho\\.soTienTxt").html(giaKhoTxt);

				$("#hangHoaDs" + i + "\\.giaKho\\.tongSoTienTxt").html(
						tongGiaKhoTxt);

				$("#hangHoaDs" + i + "\\.tongCongNoTxt").html(tongCongNoTxt);
			} catch (e) {
				console.log("capNhatTongTienHangHoa error", e);
			}
		}

		function capNhapTongTienChungTu() {
			var tongTienChungTu = 0;

			console.log("capNhapTongTienChungTu", tongTienChungTu, "soDongTk",
					soDongTk);
			for (i = 0; i < soDongTk; i++) {
				var giaKho = $("#hangHoaDs" + i + "\\.giaKho\\.soTien").val();
				var soLuong = $.trim($("#hangHoaDs" + i + "\\.soLuong").val());

				if (!isNaN(giaKho) && !isNaN(soLuong)) {
					tongTienChungTu += eval(giaKho) * eval(soLuong)
							* loaiTien.banRa;
				}

				console.log("capNhapTongTienChungTu", tongTienChungTu,
						"giaKho", giaKho, "soLuong", soLuong, "loaiTien.banRa",
						loaiTien.banRa);
			}

			// Sau đó cập nhật tổng tiền cả chứng từ
			$("#soTien\\.giaTriTxt").html(
					accounting.formatNumber(tongTienChungTu, thapPhan, ",")
							+ " VND");
		}

		$("#themHh").click(
				function() {
					// Thêm dòng mới vào tab hàng tiền
					hangTienDongMoi = hangTienDong.replace(patt, "[" + soDongTk
							+ "]");
					hangTienDongMoi = hangTienDongMoi.replace(patt1, "Ds"
							+ soDongTk);
					$(hangTienDongMoi).appendTo($("#hangTienTbl")).prop("id",
							"hangTien" + soDongTk);

					// Thêm dòng mới vào tab kho
					khoDongMoi = khoDong.replace(patt, "[" + soDongTk + "]");
					khoDongMoi = khoDongMoi.replace(patt1, "Ds" + soDongTk);
					$(khoDongMoi).appendTo($("#khoTbl")).prop("id",
							"kho" + soDongTk);

					// Thêm dòng mới vào tab thuế
					thueDongMoi = thueDong.replace(patt, "[" + soDongTk + "]");
					thueDongMoi = thueDongMoi.replace(patt1, "Ds" + soDongTk);
					$(thueDongMoi).appendTo($("#thueTbl")).prop("id",
							"thue" + soDongTk);

					// Thêm dòng mới vào tab chi phí
					chiPhiDongMoi = chiPhiDong.replace(patt, "[" + soDongTk
							+ "]");
					chiPhiDongMoi = chiPhiDongMoi.replace(patt1, "Ds"
							+ soDongTk);
					$(chiPhiDongMoi).appendTo($("#chiPhiTbl")).prop("id",
							"chiPhi" + soDongTk);

					// Thêm dòng mới vào tab ktth nếu có
					ktthDongMoi = ktthDong.replace(patt, "[" + soDongTk + "]");
					ktthDongMoi = ktthDongMoi.replace(patt1, "Ds" + soDongTk);
					$(ktthDongMoi).appendTo($("#ktthTbl")).prop("id",
							"ktth" + soDongTk);

					dangKy(soDongTk);

					soDongTk++;

					$("#xoaHh").removeClass("disabled");
				});

		$("#xoaHh").click(function() {
			soDongTk--;

			$("#hangTien" + soDongTk).remove();
			$("#kho" + soDongTk).remove();
			$("#thue" + soDongTk).remove();
			$("#chiPhi" + soDongTk).remove();
			$("#ktth" + soDongTk).remove();

			if (soDongTk == 1) {
				$("#xoaHh").addClass("disabled");
			}

			capNhapTongTienChungTu();
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
					$("input[id$='\\.giaTri']").unbind(
							'keydown.format keyup.format paste.format');

					for (i = 0; i < soDongTk; i++) {
						dangKyTien(i);
						capNhatTongTienHangHoa(i);
					}
					capNhapTongTienChungTu();
				});

		$("#loaiTien\\.banRa").change(function() {
			loaiTien.banRa = $(this).val();

			for (i = 0; i < soDongTk; i++) {
				capNhatTongTienHangHoa(i);
			}
			capNhapTongTienChungTu();
		});

		function dangKyTien(id) {
			$("input[id^='hangHoaDs" + id + "'][id$='soTien']").number(true,
					thapPhan);
			$("input[id^='hangHoaDs" + id + "'][id$='giaTri']").number(true,
					thapPhan);
		}

		function dangKy(id) {
			dangKyTien(id);

			var tenHh = $("#hangHoaDs" + id + "\\.tenHh").val();
			$("#hangHoaDs" + id + "\\.hangTien\\.tenHhTxt").text(tenHh);
			$("#hangHoaDs" + id + "\\.kho\\.tenHhTxt").text(tenHh);
			$("#hangHoaDs" + id + "\\.thue\\.tenHhTxt").text(tenHh);
			$("#hangHoaDs" + id + "\\.chiPhi\\.tenHhTxt").text(tenHh);

			// Đăng ký thay đổi số lượng
			$("#hangHoaDs" + id + "\\.soLuong").change(function() {
				var value = $(this).val();
				if (isNaN(value)) {
					value = 0;
				}
				$(this).val(value * 1);

				capNhatTongTienHangHoa(id);
				capNhapTongTienChungTu();
			});

			// Đăng ký thay đổi đơn giá
			$("#hangHoaDs" + id + "\\.donGia\\.soTien").change(function() {
				var value = $(this).val();
				if (isNaN(value)) {
					value = 0;
				}
				$(this).val(value * 1);

				capNhatTongTienHangHoa(id);
				capNhapTongTienChungTu();
			});

			// Thuế suất nhập khẩu thay đổi
			$("#hangHoaDs" + id + "\\.thueSuatNk").change(function() {
				var value = $(this).val();
				if (isNaN(value)) {
					value = 0;
				}
				$(this).val(value * 1);

				capNhatTongTienHangHoa(id);
				capNhapTongTienChungTu();
			});

			// Thuế suất nhập khẩu thay đổi
			$("#hangHoaDs" + id + "\\.tkThueNk\\.soTien\\.giaTri").change(
					function() {
						$("#hangHoaDs" + id + "\\.thueSuatNk").val(0);

						var value = $(this).val();
						if (isNaN(value)) {
							value = 0;
						}
						$(this).val(value * 1);

						capNhatTongTienHangHoa(id);
						capNhapTongTienChungTu();
					});

			// Thuế suất tiêu thụ đặc biệt thay đổi
			$("#hangHoaDs" + id + "\\.thueSuatTtdb").change(function() {
				var value = $(this).val();
				if (isNaN(value)) {
					value = 0;
				}
				$(this).val(value * 1);

				capNhatTongTienHangHoa(id);
				capNhapTongTienChungTu();
			});

			// Thuế suất nhập khẩu thay đổi
			$("#hangHoaDs" + id + "\\.tkThueTtdb\\.soTien\\.giaTri").change(
					function() {
						$("#hangHoaDs" + id + "\\.thueSuatTtdb").val(0);

						var value = $(this).val();
						if (isNaN(value)) {
							value = 0;
						}
						$(this).val(value * 1);

						capNhatTongTienHangHoa(id);
						capNhapTongTienChungTu();
					});

			// Thuế suất giá trị gia tăng thay đổi
			$("#hangHoaDs" + id + "\\.thueSuatGtgt").change(function() {
				var value = $(this).val();
				if (isNaN(value)) {
					value = 0;
				}
				$(this).val(value * 1);

				capNhatTongTienHangHoa(id);
				capNhapTongTienChungTu();
			});

			$("#hangHoaDs" + id + "\\.tkThueGtgt\\.soTien\\.giaTri").change(
					function() {
						$("#hangHoaDs" + id + "\\.thueSuatGtgt").val(0);

						var value = $(this).val();
						if (isNaN(value)) {
							value = 0;
						}
						$(this).val(value * 1);

						capNhatTongTienHangHoa(id);
						capNhapTongTienChungTu();
					});

			$("#hangHoaDs" + id + "\\.tkThueGtgt\\.loaiTaiKhoan\\.maTk")
					.change(function() {
						// Thay đổi cách tính thuế gtgt trực tiếp hoặc khấu trừ
						capNhatTongTienHangHoa(id);
						capNhapTongTienChungTu();
					});

			$(
					"#hangHoaDs" + id
							+ "\\.nvktDs0\\.taiKhoanNo\\.loaiTaiKhoan\\.maTk")
					.combobox();
			$(
					"#hangHoaDs" + id
							+ "\\.nvktDs0\\.taiKhoanCo\\.loaiTaiKhoan\\.maTk")
					.combobox();

			// Đăng ký chọn hàng hóa
			var kyHieuHh = $("#hangHoaDs" + id + "\\.kyHieuHh").val();
			if (kyHieuHh == '') {
				$("#hangHoaDs" + id + "\\.maHh").val("");
			}
			$("#hangHoaDs" + id + "\\.maHh").combobox();
			$("#hangHoaDs" + id + "\\.maHh")
					.change(
							function() {
								$
										.ajax({
											url : "${url}/chungtu/hanghoa",
											data : 'maHh=' + $(this).val(),
											dataType : "json",
											type : "POST",
											success : function(hangHoa) {
												$(
														"#hangHoaDs" + id
																+ "\\.kyHieuHh")
														.val(hangHoa.kyHieuHh);

												$(
														"#hangHoaDs" + id
																+ "\\.tenHh")
														.val(hangHoa.tenHh);
												$(
														"#hangHoaDs"
																+ id
																+ "\\.hangTien\\.tenHhTxt")
														.text(hangHoa.tenHh);
												$(
														"#hangHoaDs"
																+ id
																+ "\\.kho\\.tenHhTxt")
														.text(hangHoa.tenHh);
												$(
														"#hangHoaDs"
																+ id
																+ "\\.thue\\.tenHhTxt")
														.text(hangHoa.tenHh);
												$(
														"#hangHoaDs"
																+ id
																+ "\\.chiPhi\\.tenHhTxt")
														.text(hangHoa.tenHh);

												$(
														"#hangHoaDs"
																+ id
																+ "\\.donVi\\.maDv")
														.val(hangHoa.donVi.maDv);
												$(
														"#hangHoaDs"
																+ id
																+ "\\.donVi\\.tenDv")
														.val(
																hangHoa.donVi.tenDv);
												$(
														"#hangHoaDs"
																+ id
																+ "\\.donVi\\.tenDvTxt")
														.text(
																hangHoa.donVi.tenDv);
												$(
														"#hangHoaDs"
																+ id
																+ "\\.tkKho\\.loaiTaiKhoan\\.maTk")
														.val(
																hangHoa.tkKhoMd.maTk);
												if (hangHoa.khoMd != null) {
													$(
															"#hangHoaDs"
																	+ id
																	+ "\\.kho\\.maKho")
															.val(
																	hangHoa.khoMd.maKho);
												}

												$(
														"#hangHoaDs" + id
																+ "\\.soLuong")
														.val(0);
												$(
														"#hangHoaDs"
																+ id
																+ "\\.donGia\\.soTien")
														.val(0);
												// Các thuế suất cũng đưa về 0 ?

												capNhatTongTienHangHoa(id);
												capNhapTongTienChungTu();
											},
											error : function(data) {
												$(
														"#hangHoaDs" + id
																+ "\\.kyHieuHh")
														.val("");

												$(
														"#hangHoaDs" + id
																+ "\\.tenHh")
														.val("");
												$("#hangHoaDs"
														+ id
														+ "\\.hangTien\\.tenHhTxt")
												$(
														"#hangHoaDs"
																+ id
																+ "\\.kho\\.tenHhTxt")
														.text("");
												$(
														"#hangHoaDs"
																+ id
																+ "\\.thue\\.tenHhTxt")
														.text("");
												$(
														"#hangHoaDs"
																+ id
																+ "\\.chiPhi\\.tenHhTxt")
														.text("");

												$(
														"#hangHoaDs"
																+ id
																+ "\\.donVi\\.maDv")
														.val("");
												$(
														"#hangHoaDs"
																+ id
																+ "\\.donVi\\.tenDv")
														.val("");
												$(
														"#hangHoaDs"
																+ id
																+ "\\.donVi\\.tenDvTxt")
														.text("");
												$(
														"#hangHoaDs"
																+ id
																+ "\\.kho\\.maKho")
														.val("");
												$(
														"#hangHoaDs"
																+ id
																+ "\\.tkKho\\.loaiTaiKhoan\\.maTk")
														.val("");

												$(
														"#hangHoaDs" + id
																+ "\\.soLuong")
														.val(0);
												$(
														"#hangHoaDs"
																+ id
																+ "\\.donGia\\.soTien")
														.val(0);

												// Cập nhật tab thuế
												$(
														"#hangHoaDs"
																+ id
																+ "\\.thueSuatNk")
														.val(0);
												$(
														"#hangHoaDs"
																+ id
																+ "\\.tkThueNk\\.loaiTaiKhoan\\.maTk")
														.val("");
												$(
														"#hangHoaDs"
																+ id
																+ "\\.tkThueNk\\.soTien\\.giaTri")
														.val(0);
												$(
														"#hangHoaDs"
																+ id
																+ "\\.thueSuatTtdb")
														.val(0);
												$(
														"#hangHoaDs"
																+ id
																+ "\\.tkThueTtdb\\.loaiTaiKhoan\\.maTk")
														.val("");
												$(
														"#hangHoaDs"
																+ id
																+ "\\.tkThueTtdb\\.soTien\\.giaTri")
														.val(0);
												$(
														"#hangHoaDs"
																+ id
																+ "\\.thueSuatGtgt")
														.val(0);
												$(
														"#hangHoaDs"
																+ id
																+ "\\.tkThueGtgt\\.loaiTaiKhoan\\.maTk")
														.val("");
												$(
														"#hangHoaDs"
																+ id
																+ "\\.tkThueGtgt\\.soTien\\.giaTri")
														.val(0);

												capNhatTongTienHangHoa(id);
												capNhapTongTienChungTu();
											}
										})
							});

			$("#lyDo").change(function() {
				//$("#nvktDs" + id + "\\.taiKhoanNo\\.lyDo").val($(this).val());
			});
		}

		function khoiTao() {
			// Lấy các dòng mẫu sạch
			hangTienDong = $("#hangTien" + (soDongTk - 1)).html();
			hangTienDong = "<tr>" + hangTienDong + "</tr>";

			khoDong = $("#kho" + (soDongTk - 1)).html();
			khoDong = "<tr>" + khoDong + "</tr>";

			thueDong = $("#thue" + (soDongTk - 1)).html();
			thueDong = "<tr>" + thueDong + "</tr>";

			chiPhiDong = $("#chiPhi" + (soDongTk - 1)).html();
			chiPhiDong = "<tr>" + chiPhiDong + "</tr>";

			ktthDong = $("#ktth" + (soDongTk - 1)).html();
			ktthDong = "<tr>" + ktthDong + "</tr>";

			console.log("soDongTk", soDongTk);
			if (soDongTk > 1) {
				$("#hangTien" + (soDongTk - 1)).remove();
				$("#kho" + (soDongTk - 1)).remove();
				$("#thue" + (soDongTk - 1)).remove();
				$("#chiPhi" + (soDongTk - 1)).remove();
				$("#ktth" + (soDongTk - 1)).remove();
				soDongTk--;
			}

			if (soDongTk > 1) {
				$("#xoaHh").removeClass("disabled");
			}

			$("#loaiTien\\.banRa").number(true, thapPhan);
			loaiTien.banRa = $("#loaiTien\\.banRa").val();

			// Đăng ký sự kiện cho các dòng
			for (i = 0; i < soDongTk; i++) {
				dangKy(i);
				capNhatTongTienHangHoa(i);
			}
			capNhapTongTienChungTu();
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
	});
</script>

<style>
<!--
.sub-content {
	padding-top: 1px;
}
-->
</style>

<c:choose>
	<c:when test="${mainFinanceForm.tinhChatCt==2}">
		<h4>TẠO MỚI CHỨNG TỪ MUA HÀNG NƯỚC NGOÀI</h4>
	</c:when>
	<c:when test="${mainFinanceForm.tinhChatCt==3}">
		<h4>TẠO MỚI CHỨNG TỪ MUA DỊCH VỤ TRONG NƯỚC</h4>
	</c:when>
	<c:otherwise>
		<h4>TẠO MỚI CHỨNG TỪ MUA HÀNG TRONG NƯỚC</h4>
	</c:otherwise>
</c:choose>
<hr />
<form:hidden path="loaiCt" />
<form:hidden path="tinhChatCt" />
<form:hidden path="chieu" />
<form:hidden path="nghiepVu" />
<div class="row form-group">
	<label class="control-label col-sm-2" for="soCt">Số dự chứng từ
		kiến:</label>
	<%-- <div class="col-sm-4">
		${mainFinanceForm.loaiCt}${mainFinanceForm.soCt}
		<form:hidden path="soCt" />
	</div> --%>
	<div class="col-sm-2">
		<form:input path="soCt" class="form-control" />
		<form:errors path="soCt" cssClass="error" />
	</div>
	<div class="col-sm-2">
		<button id="goiYBt" type="button" class="btn btn-info btn-sm">Gợi
			ý</button>
	</div>

	<label class="control-label col-sm-2" for=ngayLap>Ngày lập
		chứng từ:</label>
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
	<label class="control-label col-sm-2" for="doiTuong.loaiDt">Loại
		đối tượng:</label>
	<div class="col-sm-4">
		<form:select path="doiTuong.loaiDt" cssClass="form-control"
			placeholder="Loại đối tượng">
			<form:option value="${DoiTuong.NHA_CUNG_CAP}" label="Nhà cung cấp"></form:option>
		</form:select>
	</div>

	<label class="control-label col-sm-2" for=ngayHt>Ngày hạch
		toán:</label>
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
	<label class="control-label col-sm-2" for="doiTuong.tenDt">Đối
		tượng:(*)</label>
	<div class="col-sm-4">
		<form:hidden path="doiTuong.maDt" />
		<form:input path="doiTuong.tenDt" placeholder="Họ và tên"
			cssClass="form-control" />
		<br />
		<form:errors path="doiTuong.tenDt" cssClass="error" />
	</div>

	<label class="control-label col-sm-2" for="doiTuong.maThue">Mã
		số thuế:</label>
	<div class="col-sm-4">
		<form:input path="doiTuong.maThue" placeholder="Mã số thuế"
			cssClass="form-control" />
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="doiTuong.diaChi">Địa
		chỉ:</label>
	<div class="col-sm-4">
		<form:textarea path="doiTuong.diaChi" placeholder="Địa chỉ"
			cssClass="form-control" />
	</div>

	<label class="control-label col-sm-2" for="doiTuong.nguoiNop">Người
		giao: </label>
	<div class="col-sm-4">
		<form:input path="doiTuong.nguoiNop" placeholder="Người nộp"
			cssClass="form-control" />
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="lyDo">Lý do:(*)</label>
	<div class="col-sm-4">
		<form:textarea path="lyDo" placeholder="Lý do" cssClass="form-control" />
		<br />
		<form:errors path="lyDo" cssClass="error" />
	</div>

	<label class="control-label col-sm-2" for="kemTheo">Kèm theo <br />số
		chứng từ gốc:
	</label>
	<div class="col-sm-4">
		<form:input path="kemTheo" placeholder="0" cssClass="form-control" />
		<br />
		<form:errors path="kemTheo" cssClass="error" />
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
	<label class="control-label col-sm-2" for="soTien.giaTri">Thành
		tiền:</label>
	<div class="col-sm-4">
		<p id="soTien.giaTriTxt">
			<fmt:formatNumber value="${mainFinanceForm.soTien.giaTri}"></fmt:formatNumber>
			&nbsp;VND
		</p>
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

<div class="row">
	<label class="control-label col-sm-12">Danh sách hàng hóa, dịch
		vụ</label>
</div>
<div class="row">
	<c:choose>
		<c:when test="${mainFinanceForm.tinhChatCt==2}">
			<jsp:include page="muaHangSu_Hh_Nn.jsp"></jsp:include>
		</c:when>
		<c:when test="${mainFinanceForm.tinhChatCt==3}">
			<jsp:include page="muaHangSu_Dv_Tn.jsp"></jsp:include>
		</c:when>
		<c:otherwise>
			<jsp:include page="muaHangSu_Hh_Tn.jsp"></jsp:include>
		</c:otherwise>
	</c:choose>
</div>

<div class="row form-group" align="center">
	<button id="themHh" type="button" class="btn btn-info btn-sm"
		title="Thêm tài khoản ghi có">
		<span class="glyphicon glyphicon-plus"></span> Thêm
	</button>
	<button id="xoaHh" type="button" class="btn btn-info btn-sm disabled"
		title="Xóa tài khoản ghi có cuối cùng">
		<span class="glyphicon glyphicon-plus"></span> Xóa
	</button>
</div>

<div class="row form-group">
	<div class="col-sm-12">
		<a href="${url}/chungtu/muahang/danhsach" class="btn btn-info btn-sm">Hủy</a>
		<button id="submitBt" type="submit" class="btn btn-info btn-sm">Lưu</button>
	</div>
</div>
