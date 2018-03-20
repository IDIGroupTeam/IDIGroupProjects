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

		function cleanForm() {
			$("#doiTuong\\.maDt").val("0");
			$("#doiTuong\\.tenDt").val("");
			$("#doiTuong\\.maThue").val("");
			$("#doiTuong\\.diaChi").val("");
			$("#doiTuong\\.nguoiNop").val("");
		}

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
			var soLuong = $.trim($("#hangHoaDs" + id + "\\.soLuong").val())
			var gia = $.trim($("#hangHoaDs" + id + "\\.giaMua\\.soTien").val())

			if (soLuong != '' && !isNaN(soLuong) && gia != '' && !isNaN(gia)) {
				return soLuong * gia;
			}

			return 0;
		}

		function tongTienHangHoaTxt(id) {
			var tongGiaTri = tongTienHangHoa(id);
			$("#hangHoaDs" + id + "\\.tongTien").html(
					accounting.formatNumber(tongGiaTri, 0, ","));
		}

		function tongTien() {
			var tongGiaTri = 0;
			for (i = 0; i < soDongTk; i++) {
				tongGiaTri += tongTienHangHoa(i);
			}

			return tongGiaTri;
		}

		function capNhapTongTien() {
			var tongGiaTri = tongTien();

			var tyGia = $.trim($("#loaiTien\\.banRa").val());
			$("#soTien\\.giaTriTxt").html(
					accounting.formatNumber(tongGiaTri * tyGia, 0, ",")
							+ " VND");
		}

		$("#themHh")
				.click(
						function() {
							var currentTr = $(this).parent().parent();
							var prevTr = $(currentTr).prev();
							var prevId = $(prevTr).prop("id");
							var newId = ++prevId;
							--prevId;

							var newTr = "<tr>" + $(prevTr).html() + "</tr>";
							var pat = new RegExp("\\[" + prevId + "\\]", "g");
							var pat1 = new RegExp("Ds" + prevId, "g");
							newTr = newTr.replace(pat, "[" + newId + "]");
							newTr = newTr.replace(pat1, "Ds" + newId);

							$(newTr).insertBefore($(currentTr)).prop("id",
									newId);

							// Xóa các giá trị cũ
							$(
									"span[id^='hangHoaDs" + newId
											+ "'][id$='\\.errors']").text("");

							$(
									"input[id^='hangHoaDs" + newId
											+ "'][id$='\\.maHh']").val("");
							$(
									"input[id^='hangHoaDs" + newId
											+ "'][id$='\\.kyHieuHh']").val("");
							$(
									"input[id^='hangHoaDs" + newId
											+ "'][id$='\\.tenHh']").val("");

							$(
									"input[id^='hangHoaDs" + newId
											+ "'][id$='\\.donVi\\.maDv']").val(
									"");
							$(
									"input[id^='hangHoaDs" + newId
											+ "'][id$='\\.donVi\\.tenDv']")
									.val("");
							$("#hangHoaDs" + newId + "\\.donVi\\.tenDv").text(
									"");

							$(
									"input[id^='hangHoaDs" + newId
											+ "'][id$='\\.soLuong']").val("");
							$(
									"input[id^='hangHoaDs" + newId
											+ "'][id$='\\.giaMua\\.soTien']")
									.val("");
							$("#hangHoaDs" + newId + "\\.tongTien").text("");

							$(
									"select[id^='hangHoaDs" + newId
											+ "'][id$='\\.kho\\.maKho']").val(
									"");
							$(
									"select[id^='hangHoaDs"
											+ newId
											+ "'][id$='\\.tkKho\\.loaiTaiKhoan\\.maTk']")
									.val("");
							$(
									"select[id^='hangHoaDs"
											+ newId
											+ "'][id$='\\.tkThanhtoan\\.loaiTaiKhoan\\.maTk']")
									.val("");
							$(
									"select[id^='hangHoaDs"
											+ newId
											+ "'][id$='\\.tkDoanhThu\\.loaiTaiKhoan\\.maTk']")
									.val("");
							$(
									"select[id^='hangHoaDs"
											+ newId
											+ "'][id$='\\.tkChiPhi\\.loaiTaiKhoan\\.maTk']")
									.val("");

							soDongTk++;

							$("#xoaHh").removeClass("disabled");

							dangKy(newId);
						});

		$("#xoaHh").click(function() {
			var removedTr = $(this).parent().parent().prev();
			var id = $(removedTr).prop("id");
			$(removedTr).remove();
			soDongTk--;

			if (id == 1) {
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
			capNhapTongTien();
		});

		$("#loaiTien\\.banRa").change(function() {
			loaiTien.banRa = $(this).val();
			capNhapTongTien();
		});

		function dangKy(id) {
			// Đăng ký hàng hóa
			$("#hangHoaDs" + id + "\\.tenHh")
					.typeahead(
							{
								source : function(query, result) {
									$
											.ajax({
												url : "${url}/chungtu/hanghoa/danhsach",
												data : 'tuKhoa=' + query,
												dataType : "json",
												type : "POST",
												success : function(data) {
													result($
															.map(
																	data,
																	function(
																			item) {
																		var itemTmpl = new Object();
																		itemTmpl.id = item.maHh;
																		itemTmpl.name = item.tenHh;
																		return itemTmpl;
																	}));
												}
											})
								},
								afterSelect : function(data) {
									path = "${url}/chungtu/hanghoa";

									var id = this.$element.parent().parent()
											.attr("id");
									var maHh = data.id;

									$
											.ajax({
												dataType : "json",
												url : path,
												data : {
													maHh : maHh
												},
												success : function(hangHoa) {
													$(
															"#hangHoaDs" + id
																	+ "\\.maHh")
															.val(hangHoa.maHh);
													$(
															"#hangHoaDs"
																	+ id
																	+ "\\.kyHieuHh")
															.val(
																	hangHoa.kyHieuHh);
													$(
															"#hangHoaDs"
																	+ id
																	+ "\\.donVi\\.maDv")
															.val(
																	hangHoa.donVi.maDv);
													$(
															"#hangHoaDs"
																	+ id
																	+ "\\.donVi\\.tenDv")
															.val(
																	hangHoa.donVi.tenDv);
													$(
															"#hangHoaDs"
																	+ id
																	+ "\\.donVi\\.tenDv")
															.text(
																	hangHoa.donVi.tenDv);
													$(
															"#hangHoaDs"
																	+ id
																	+ "\\.kho\\.maKho")
															.val(
																	hangHoa.khoMd.maKho);
													$(
															"#hangHoaDs"
																	+ id
																	+ "\\.tkKho\\.loaiTaiKhoan\\.maTk")
															.val(
																	hangHoa.tkKhoMd.maTk);
													$(
															"#hangHoaDs"
																	+ id
																	+ "\\.tkDoanhThu\\.loaiTaiKhoan\\.maTk")
															.val(
																	hangHoa.tkDoanhThuMd.maTk);
													$(
															"#hangHoaDs"
																	+ id
																	+ "\\.tkChiPhi\\.loaiTaiKhoan\\.maTk")
															.val(
																	hangHoa.tkChiPhiMd.maTk);
												}
											});

									this.$element.val(data.name);
								},
								afterEmptySelect : function() {
									var id = this.$element.parent().parent()
											.attr("id");

									// clear all
									$("#hangHoaDs" + id + "\\.maHh").val("");
									$("#hangHoaDs" + id + "\\.kyHieuHh")
											.val("");
									$("#hangHoaDs" + id + "\\.donVi\\.maDv")
											.val("");
									$("#hangHoaDs" + id + "\\.donVi\\.tenDv")
											.text("");
									$("#hangHoaDs" + id + "\\.kho\\.maKho")
											.val("");
									$(
											"#hangHoaDs"
													+ id
													+ "\\.tkKho\\.loaiTaiKhoan\\.mTk")
											.val("");
								}
							});

			// Đăng ký thay đổi số lượng
			$("#hangHoaDs" + id + "\\.soLuong").change(function() {
				tongTienHangHoaTxt(id);

				capNhapTongTien();
			});

			// Đăng ký thay đổi đơn giá
			$("#hangHoaDs" + id + "\\.giaMua\\.soTien").change(function() {
				tongTienHangHoaTxt(id);

				capNhapTongTien();
			});
		}

		function khoiTao() {
			if (soDongTk > 1) {
				$("#xoaHh").removeClass("disabled");
			}

			for (i = 0; i < soDongTk; i++) {
				dangKy(i);
				tongTienHangHoaTxt(i);
			}
			capNhapTongTien();

			$("#thanhToan")
					.change(
							function() {
								$(
										"select[id^='hangHoaDs'][id$='\\.tkThanhtoan\\.loaiTaiKhoan\\.maTk']")
										.val($(this).val());
							});
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

<h4>TẠO MỚI CHỨNG TỪ BAN HÀNG</h4>
<hr />
<form:hidden path="loaiCt" />
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
</div>

<div class="row">
	<label class="control-label col-sm-6">Danh sách hàng hóa, dịch
		vụ</label>
	<div class="col-sm-3 form-group">
		<label class="control-label col-sm-6" for="thanhToan">Thanh
			toán:</label>
		<div class="col-sm-6">
			<form:select path="thanhToan" cssClass="form-control">
				<form:option value="0" label=""></form:option>
				<form:option value="111" label="111"></form:option>
				<form:option value="1111" label="1111"></form:option>
				<form:option value="1112" label="1112"></form:option>
				<form:option value="112" label="112"></form:option>
				<form:option value="1121" label="1121"></form:option>
				<form:option value="1122" label="1122"></form:option>
				<form:option value="131" label="131"></form:option>
			</form:select>
		</div>
	</div>
	<div class="col-sm-3 form-group">
		<label class="control-label col-sm-6" for="tkThue.loaiTaiKhoan.maTk">Thuế
			GTGT:</label>
		<div class="col-sm-6">
			<form:select path="tkThue.loaiTaiKhoan.maTk" cssClass="form-control">
				<form:option value="" label=""></form:option>
				<form:option value="3331" label="3331"></form:option>
				<form:option value="33311" label="33311"></form:option>
				<form:option value="33312" label="33312"></form:option>
			</form:select>
		</div>
	</div>
	<div class="table-responsive">
		<table id="taiKhoanTbl"
			class="table table-bordered table-hover text-center dinhkhoan">
			<thead>
				<tr>
					<th class="text-center">Hàng hóa, dịch vụ</th>
					<th class="text-center">Đơn vị tính</th>
					<th class="text-center">Số lượng</th>
					<th class="text-center">Đơn giá</th>
					<th class="text-center">Thành tiền</th>
					<th class="text-center">Kho</th>
					<th class="text-center">TK Kho (Có)</th>
					<th class="text-center">TK Thanh toán (Nợ)</th>
					<th class="text-center">TK Doanh thu (Có)</th>
					<th class="text-center">TK Giá vốn (Nợ)</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${mainFinanceForm.hangHoaDs}" var="hangHoa"
					varStatus="status">
					<tr id="${status.index}">
						<td class="text-left"><form:hidden
								path="hangHoaDs[${status.index}].maHh" /> <form:hidden
								path="hangHoaDs[${status.index}].kyHieuHh" /> <form:input
								path="hangHoaDs[${status.index}].tenHh" cssClass="form-control"
								autocomplete="off" /> <form:errors
								path="hangHoaDs[${status.index}].tenHh" cssClass="error" /></td>
						<td><form:hidden path="hangHoaDs[${status.index}].donVi.maDv" />
							<form:hidden path="hangHoaDs[${status.index}].donVi.tenDv" /> <span
							id="hangHoaDs${status.index}.donVi.tenDv">${hangHoa.donVi.tenDv}</span></td>
						<td><form:input cssClass="form-control"
								path="hangHoaDs[${status.index}].soLuong" /> <form:errors
								path="hangHoaDs[${status.index}].soLuong" cssClass="error" /></td>
						<td><form:input cssClass="form-control"
								path="hangHoaDs[${status.index}].giaMua.soTien" /> <form:errors
								path="hangHoaDs[${status.index}].giaMua.soTien" cssClass="error" /></td>
						<td><span id="hangHoaDs${status.index}.tongTien"></span></td>
						<td><form:select cssClass="form-control"
								path="hangHoaDs[${status.index}].kho.maKho">
								<form:option value="0" label=""></form:option>
								<form:options items="${khoBaiDs}" itemValue="maKho"
									itemLabel="tenKho" />
							</form:select> <form:errors path="hangHoaDs[${status.index}].kho.maKho"
								cssClass="error" /></td>
						<td><input type="hidden"
							name="hangHoaDs[${status.index}].tkKho.soDu" value="1" /> <form:hidden
								path="hangHoaDs[${status.index}].tkKho.maNvkt" /> <form:select
								cssClass="form-control"
								path="hangHoaDs[${status.index}].tkKho.loaiTaiKhoan.maTk">
								<form:option value="" label=""></form:option>
								<form:option value="156" label="156"></form:option>
								<form:option value="1561" label="1561"></form:option>
								<form:option value="1562" label="1562"></form:option>
							</form:select> <form:errors
								path="hangHoaDs[${status.index}].tkKho.loaiTaiKhoan.maTk"
								cssClass="error" /></td>
						<td><input type="hidden"
							name="hangHoaDs[${status.index}].tkThanhtoan.soDu" value="-1" />
							<form:hidden path="hangHoaDs[${status.index}].tkThanhtoan.maNvkt" />
							<form:select cssClass="form-control"
								path="hangHoaDs[${status.index}].tkThanhtoan.loaiTaiKhoan.maTk">
								<form:option value="" label=""></form:option>
								<form:option value="111" label="111"></form:option>
								<form:option value="1111" label="1111"></form:option>
								<form:option value="1112" label="1112"></form:option>
								<form:option value="112" label="112"></form:option>
								<form:option value="1121" label="1121"></form:option>
								<form:option value="1122" label="1122"></form:option>
								<form:option value="131" label="131"></form:option>
							</form:select> <form:errors
								path="hangHoaDs[${status.index}].tkThanhtoan.loaiTaiKhoan.maTk"
								cssClass="error" /></td>
						<td><input type="hidden"
							name="hangHoaDs[${status.index}].tkDoanhThu.soDu" value="1" /> <form:hidden
								path="hangHoaDs[${status.index}].tkDoanhThu.maNvkt" /> <form:select
								cssClass="form-control"
								path="hangHoaDs[${status.index}].tkDoanhThu.loaiTaiKhoan.maTk">
								<form:option value="" label=""></form:option>
								<form:option value="511" label="511"></form:option>
								<form:option value="5111" label="5111"></form:option>
								<form:option value="5112" label="5112"></form:option>
								<form:option value="5113" label="5113"></form:option>
							</form:select> <form:errors
								path="hangHoaDs[${status.index}].tkDoanhThu.loaiTaiKhoan.maTk"
								cssClass="error" /></td>
						<td><input type="hidden"
							name="hangHoaDs[${status.index}].tkChiPhi.soDu" value="-1" /> <form:hidden
								path="hangHoaDs[${status.index}].tkChiPhi.maNvkt" /> <form:select
								cssClass="form-control"
								path="hangHoaDs[${status.index}].tkChiPhi.loaiTaiKhoan.maTk">
								<form:option value="" label=""></form:option>
								<form:option value="632" label="632"></form:option>
							</form:select> <form:errors
								path="hangHoaDs[${status.index}].tkChiPhi.loaiTaiKhoan.maTk"
								cssClass="error" /></td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="8">
						<button id="themHh" type="button" class="btn btn-info btn-sm"
							title="Thêm tài khoản ghi có">
							<span class="glyphicon glyphicon-plus"></span> Thêm
						</button>
						<button id="xoaHh" type="button"
							class="btn btn-info btn-sm disabled"
							title="Xóa tài khoản ghi có cuối cùng">
							<span class="glyphicon glyphicon-plus"></span> Xóa
						</button>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>

<div class="row form-group">
	<div class="col-sm-2">
		<a href="${url}/chungtu/banhang/danhsach" class="btn btn-info btn-sm">Hủy</a>
		<button id="submitBt" type="submit" class="btn btn-info btn-sm">Tạo
			mới</button>
	</div>
</div>