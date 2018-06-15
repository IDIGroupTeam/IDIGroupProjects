<%@page import="com.idi.finance.bean.taikhoan.LoaiTaiKhoan"%>
<%@page import="com.idi.finance.bean.chungtu.DoiTuong"%>
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
		$("#mainFinanceForm").attr("action", "${url}/chungtu/banhang/luu");
		$("#mainFinanceForm").attr("method", "POST");

		$("#submitBt").click(function() {
			$("#mainFinanceForm").submit();
		});

		var soDongTk = '${mainFinanceForm.soHangHoa}';
		var currentTr = 0;
		var hangTienDong = "";
		var thueDong = "";
		var chiPhiDong = "";
		var patt = new RegExp("\\[" + (soDongTk - 1) + "\\]", "g");
		var patt1 = new RegExp("Ds" + (soDongTk - 1), "g");

		var loaiTien = null;
		var url = "${url}/chungtu/khachhang/";
		var loaiDt = 2;

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
			tongTienVn = accounting.formatNumber(tongTienVn, 2, ",") + " VND";

			// Hiển thị tổng tiền ở tab hàng tiền
			var tongTxt = accounting.formatNumber(tongTien, 2, ",") + " "
					+ loaiTien.maLt;
			if (loaiTien.maLt != "VND") {
				tongTxt += "<br/>" + tongTienVn;
			}
			$("#hangHoaDs" + id + "\\.hangTien\\.tongTien").html(tongTxt);

			// Hiển thị tổng tiền ở tab thuế
			$("#hangHoaDs" + id + "\\.thue\\.tongTien").html(tongTienVn);

			return tongTien;
		}

		function tongThueHangHoa(id) {
			// Tổng tiền hàng hóa VND
			var tong = tongTienHangHoa(id) * loaiTien.banRa;

			// Cập nhật tiền thuế nhập khẩu
			try {
				var thueSuatXk = $("#hangHoaDs" + id + "\\.thueSuatXk").val();
				if (!isNaN(thueSuatXk)) {
					if (thueSuatXk > 0) {
						var thue = tong * thueSuatXk / 100;
						$("#hangHoaDs" + id + "\\.tkThueXk\\.soTien\\.soTien")
								.val(tong * thueSuatXk / 100);
					}
				}

				var thueXk = $(
						"#hangHoaDs" + id + "\\.tkThueXk\\.soTien\\.soTien")
						.val();
				if (!isNaN(thueXk)) {
					tong += eval(thueXk);
				}
			} catch (e) {
				alert("nk " + e);
			}

			// Tính tiền thuế giá trị gia tăng
			try {
				var thueSuatGtgt = $("#hangHoaDs" + id + "\\.thueSuatGtgt")
						.val();
				if (!isNaN(thueSuatGtgt)) {
					if (thueSuatGtgt > 0) {
						var thue = tong * thueSuatGtgt / 100;
						$("#hangHoaDs" + id + "\\.tkThueGtgt\\.soTien\\.soTien")
								.val(thue);
					}
				}

				var tinhChatCt = $("#tinhChatCt").val();
				var maTk = $(
						"#hangHoaDs" + id
								+ "\\.tkThueGtgt\\.loaiTaiKhoan\\.maTk").val();
				if (tinhChatCt == 2 || ((tinhChatCt != 2) && (maTk == '0'))) {
					//Đây là trường hợp tính thuế GTGT theo phương pháp trực tiếp, 
					// Tiền thuế GTGT được cộng vào giá nhập kho

					// Nếu là bán hàng nước ngoài
					// và tính theo phương pháp khấu trừ thì lập một phiếu kế toán tổng hợp riêng, 
					// form này sẽ để trắng các cột liên quan đến thuế GTGT

					// Hoặc
					// Nếu là bán trong nước
					// Tính theo phương pháp khấu trừ thì phải chọn mã tài khoản

					var thueGtgt = $(
							"#hangHoaDs" + id
									+ "\\.tkThueGtgt\\.soTien\\.soTien").val();

					if (!isNaN(thueGtgt)) {
						tong += eval(thueGtgt);
					}
				}
			} catch (e) {

			}

			return tong;
		}

		function capNhatTongTienHangHoa(i) {
			// VỚI MỖI LOẠI HÀNG HÓA 
			// CẬP NHẬT HÀNG TIỀN
			// Thực hiện việc này trong tongThueHangHoa

			// CẬP NHẬT THUẾ
			// Kết quả trả về là tổng hàng tiền và tổng thuế
			var tong = tongThueHangHoa(i) / loaiTien.banRa;

			// CẬP NHẬT GIÁ VỐN
			soLuong = $("#hangHoaDs" + i + "\\.soLuong").val();
			$("#hangHoaDs" + i + "\\.giaVon\\.soLuongTxt").text(soLuong);
		}

		function capNhapTongTienChungTu() {
			var tongTienChungTu = 0;
			for (i = 0; i < soDongTk; i++) {
				var donGia = $("#hangHoaDs" + i + "\\.donGia\\.soTien").val();
				var soLuong = $.trim($("#hangHoaDs" + i + "\\.soLuong").val());

				if (!isNaN(donGia) && !isNaN(soLuong)) {
					tongTienChungTu += eval(donGia) * eval(soLuong)
							* loaiTien.banRa;
				}
			}

			// Sau đó cập nhật tổng tiền cả chứng từ
			$("#soTien\\.giaTriTxt").html(
					accounting.formatNumber(tongTienChungTu, 0, ",") + " VND");
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

					dangKy(soDongTk);

					soDongTk++;

					$("#xoaHh").removeClass("disabled");
				});

		$("#xoaHh").click(function() {
			soDongTk--;

			$("#hangTien" + soDongTk).remove();
			$("#thue" + soDongTk).remove();
			$("#chiPhi" + soDongTk).remove();

			if (soDongTk == 1) {
				$("#xoaHh").addClass("disabled");
			}

			capNhapTongTien();
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

			for (i = 0; i < soDongTk; i++) {
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

		function danhSachGiaVon(maHh, id) {
			if (maHh == null) {
				html = "<option value='0' class='giaVon'></option>";
				$("#hangHoaDs" + id + "\\.giaKho\\.maGia").html(html);
				return;
			}

			maKho = $("#hangHoaDs" + id + "\\.kho\\.maKho").val();

			var param = 'maHh=' + maHh;
			if (maKho != null && maKho != 0) {
				param += "&maKho=" + maKho;
			} else {
				param += "&maKho=0";
			}

			$
					.ajax({
						url : "${url}/chungtu/hanghoa/giavonds",
						data : param,
						dataType : "json",
						type : "POST",
						success : function(donGiaDs) {
							html = "<option value='0' class='giaVon'></option>";
							if (donGiaDs != null) {
								for (var i = 0; i < donGiaDs.length; i++) {
									donGia = donGiaDs[i];
									htmlTmpl = "<option value='"+donGia.maGia+"' class='giaVon'>"
											+ donGia.donGia.soTien
											+ "</option>";
									html += htmlTmpl;
								}
							}

							$("#hangHoaDs" + id + "\\.giaKho\\.maGia").html(
									html);
						},
						error : function(data) {
							html = "<option value='0'></option>";
							$("#hangHoaDs" + id + "\\.giaKho\\.maGia").html(
									html);
						}
					});
		}

		function dangKy(id) {
			var tenHh = $("#hangHoaDs" + id + "\\.tenHh").val();
			$("#hangHoaDs" + id + "\\.thue\\.tenHhTxt").text(tenHh);
			$("#hangHoaDs" + id + "\\.giaVon\\.tenHhTxt").text(tenHh);

			var donVi = $("#hangHoaDs" + id + "\\.donVi\\.tenDv").val();
			$("#hangHoaDs" + id + "\\.giaVon\\.tenDvTxt").text(donVi);

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

			// Đăng ký thay đổi đơn giá bán
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
			$("#hangHoaDs" + id + "\\.thueSuatXk").change(function() {
				var value = $(this).val();
				if (isNaN(value)) {
					value = 0;
				}
				$(this).val(value * 1);

				capNhatTongTienHangHoa(id);
				capNhapTongTienChungTu();
			});

			// Thuế suất nhập khẩu thay đổi
			$("#hangHoaDs" + id + "\\.tkThueXk\\.soTien\\.soTien").change(
					function() {
						$("#hangHoaDs" + id + "\\.thueSuatXk").val(0);

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

			$("#hangHoaDs" + id + "\\.tkThueGtgt\\.soTien\\.soTien").change(
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
						capNhatTongTienHangHoa(id);
						capNhapTongTienChungTu();
					});

			$("#hangHoaDs" + id + "\\.giaKho\\.maGia")
					.change(
							function() {
								var tongGiaVon = 0;
								maGia = $(this).val();
								var giaVon = $(
										"option[value='" + maGia
												+ "'][class='giaVon']").text();

								var soLuong = $
										.trim($(
												"#hangHoaDs" + id
														+ "\\.soLuong").val());

								var soLuong = $
										.trim($(
												"#hangHoaDs" + id
														+ "\\.soLuong").val())
								if (soLuong != '' && !isNaN(soLuong)
										&& giaVon != '' && !isNaN(giaVon)) {
									tongGiaVon = soLuong * giaVon;
								}

								$(
										"#hangHoaDs" + id
												+ "\\.giaVon\\.thanhTienTxt")
										.text(tongGiaVon);
								$("#hangHoaDs" + id + "\\.giaKho\\.soTien")
										.val(giaVon);
							});

			// Đăng ký chọn hàng hóa
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
																+ "\\.thue\\.tenHhTxt")
														.text(hangHoa.tenHh);
												$(
														"#hangHoaDs"
																+ id
																+ "\\.giaVon\\.tenHhTxt")
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
																+ "\\.giaVon\\.tenDvTxt")
														.text(
																hangHoa.donVi.tenDv);

												$(
														"#hangHoaDs"
																+ id
																+ "\\.tkKho\\.loaiTaiKhoan\\.maTk")
														.val(
																hangHoa.tkKhoMd.maTk);
												$(
														"#hangHoaDs"
																+ id
																+ "\\.kho\\.maKho")
														.val(
																hangHoa.khoMd.maKho);

												$(
														"#hangHoaDs" + id
																+ "\\.soLuong")
														.val(0);
												$(
														"#hangHoaDs"
																+ id
																+ "\\.donGia\\.soTien")
														.val(0);

												danhSachGiaVon(hangHoa.maHh, id);

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
												$(
														"#hangHoaDs"
																+ id
																+ "\\.hangTien\\.tenHhTxt")
														.text("");
												$(
														"#hangHoaDs"
																+ id
																+ "\\.thue\\.tenHhTxt")
														.text("");
												$(
														"#hangHoaDs"
																+ id
																+ "\\.giaVon\\.tenHhTxt")
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
																+ "\\.giaVon\\.tenDvTxt")
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
																+ "\\.thueSuatXk")
														.val(0);
												$(
														"#hangHoaDs"
																+ id
																+ "\\.tkThueXk\\.loaiTaiKhoan\\.maTk")
														.val("");
												$(
														"#hangHoaDs"
																+ id
																+ "\\.tkThueXk\\.soTien\\.soTien")
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
																+ "\\.tkThueGtgt\\.soTien\\.soTien")
														.val(0);

												$(
														"#hangHoaDs"
																+ id
																+ "\\.giaKho\\.maGia")
														.val(0);
												$(
														"#hangHoaDs"
																+ id
																+ "\\.tkGiaVon\\.loaiTaiKhoan.\\maTk")
														.val(0);

												$(
														"span[id^='hangHoaDs'][id$='errors']")
														.html("");

												danhSachGiaVon(null, id);

												capNhatTongTienHangHoa(id);
												capNhapTongTienChungTu();
											}
										})
							});
		}

		function khoiTao() {
			// Lấy các dòng mẫu sạch
			hangTienDong = $("#hangTien" + (soDongTk - 1)).html();
			hangTienDong = "<tr>" + hangTienDong + "</tr>";

			thueDong = $("#thue" + (soDongTk - 1)).html();
			thueDong = "<tr>" + thueDong + "</tr>";

			chiPhiDong = $("#chiPhi" + (soDongTk - 1)).html();
			chiPhiDong = "<tr>" + chiPhiDong + "</tr>";

			if (soDongTk > 1) {
				$("#hangTien" + (soDongTk - 1)).remove();
				$("#thue" + (soDongTk - 1)).remove();
				$("#chiPhi" + (soDongTk - 1)).remove();
				soDongTk--;
			}

			if (soDongTk > 1) {
				$("#xoaHh").removeClass("disabled");
			}

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
		<h4>TẠO MỚI CHỨNG TỪ XUẤT KHẨU HÀNG HÓA</h4>
	</c:when>
	<c:when test="${mainFinanceForm.tinhChatCt==3}">
		<h4>TẠO MỚI CHỨNG TỪ CUNG CẤP DỊCH VỤ TRONG NƯỚC</h4>
	</c:when>
	<c:otherwise>
		<h4>TẠO MỚI CHỨNG TỪ BÁN HÀNG TRONG NƯỚC</h4>
	</c:otherwise>
</c:choose>
<hr />
<form:hidden path="loaiCt" />
<form:hidden path="tinhChatCt" />
<form:hidden path="chieu" />
<div class="row form-group">
	<label class="control-label col-sm-2" for="soCt">Số dự chứng từ
		kiến:</label>
	<div class="col-sm-4">
		${mainFinanceForm.loaiCt}${mainFinanceForm.soCt}
		<form:hidden path="soCt" />
	</div>

	<label class="control-label col-sm-2" for=ngayLap>Ngày lập
		chứng từ:</label>
	<div class="col-sm-4">
		<div class="input-group date datetime smallform">
			<form:input path="ngayLap" class="form-control" readonly="true" />
			<span class="input-group-addon"><span
				class="glyphicon glyphicon-calendar"></span></span>
		</div>
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="doiTuong.loaiDt">Loại
		đối tượng:</label>
	<div class="col-sm-4">
		<form:select path="doiTuong.loaiDt" cssClass="form-control"
			placeholder="Loại đối tượng">
			<form:option value="${DoiTuong.KHACH_HANG}" label="Khách hàng"></form:option>
		</form:select>
	</div>

	<label class="control-label col-sm-2" for=ngayHt>Ngày hạch
		toán:</label>
	<div class="col-sm-4">
		<div class="input-group date datetime smallform">
			<form:input path="ngayHt" class="form-control" readonly="true" />
			<span class="input-group-addon"><span
				class="glyphicon glyphicon-calendar"></span></span>
		</div>
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
	
	<label class="control-label col-sm-2" for=ngayLap>Ngày thanh toán</label>
	<div class="col-sm-4">
		<div class="input-group date datetime smallform">
			<form:input path="ngayTt" class="form-control" />
			<span class="input-group-addon"><span
				class="glyphicon glyphicon-calendar"></span></span>
		</div>
	</div>
</div>

<label class="control-label">Danh sách hàng hóa, dịch vụ</label>
<div class="row">
	<c:choose>
		<c:when test="${mainFinanceForm.tinhChatCt==2}">
			<jsp:include page="banHangSu_Hh_Nn.jsp"></jsp:include>
		</c:when>
		<c:otherwise>
			<jsp:include page="banHangSu_Hh_Tn.jsp"></jsp:include>
		</c:otherwise>
	</c:choose>
	<br />
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
	<div class="col-sm-2">
		<a href="${url}/chungtu/banhang/danhsach" class="btn btn-info btn-sm">Hủy</a>
		<button id="submitBt" type="submit" class="btn btn-info btn-sm">Tạo
			mới</button>
	</div>
</div>