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
		$("#mainFinanceForm").attr("action", "${url}/chungtu/ktth/luu");
		$("#mainFinanceForm").attr("method", "POST");

		$("#submitBt").click(function() {
			$("#mainFinanceForm").submit();
		});

		var soDongTk = '${mainFinanceForm.soTkLonNhat}';
		var soDongTkNo = '${mainFinanceForm.taiKhoanNoDs.size()}';
		var soDongTkCo = '${mainFinanceForm.taiKhoanCoDs.size()}';
		var tongGiaTriNo = 0;
		var tongGiaTriCo = 0;

		var loaiTien = null;
		var url = "${url}/chungtu/nhanvien/";
		var loaiDt = 1;

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

		function capNhatTongNoTxt() {
			$("#soTien\\.soTienNo").html(
					accounting.formatNumber(tongGiaTriNo, 0, ","));
			$("#soTien\\.giaTriTxt").html(
					accounting.formatNumber(tongGiaTriNo * loaiTien.banRa, 0,
							",")
							+ " VND");
		}

		function capNhatTongNo() {
			// Tính tổng các tài khoản nợ
			tongGiaTriNo = 0;
			$("input[id^='taiKhoanNoDs'][id$='\\.soTien\\.soTien']").each(
					function() {
						var giaTriTt = $.trim($(this).val());

						if (giaTriTt != '' && !isNaN(giaTriTt)) {
							tongGiaTriNo += parseFloat(giaTriTt);
						}
					});

			// Cập nhật các vị trí txt nợ
			capNhatTongNoTxt();
		}

		function capNhatTongCoTxt() {
			$("#soTien\\.soTienCo").html(
					accounting.formatNumber(tongGiaTriCo, 0, ","));
		}

		function capNhatTongCo() {
			// Tính tổng các tài khoản có
			tongGiaTriCo = 0;
			$("input[id^='taiKhoanCoDs'][id$='\\.soTien\\.soTien']").each(
					function() {
						var giaTriTt = $.trim($(this).val());

						if (giaTriTt != '' && !isNaN(giaTriTt)) {
							tongGiaTriCo += parseFloat(giaTriTt);
						}
					});

			// Cập nhật các vị trí txt có
			capNhatTongCoTxt();
		}

		function dangKyThayDoiNo() {
			$("input[id^='taiKhoanNoDs'][id$='\\.soTien\\.soTien']").change(
					function() {
						var giaTri = $.trim($(this).val());
						if (giaTri != '' && !isNaN(giaTri)) {
							$(this).val(parseFloat(giaTri));
						}

						capNhatTongNo();
					});
		}

		function dangKyThayDoiCo() {
			$("input[id^='taiKhoanCoDs'][id$='\\.soTien\\.soTien']").change(
					function() {
						var giaTri = $.trim($(this).val());
						if (giaTri != '' && !isNaN(giaTri)) {
							$(this).val(parseFloat(giaTri));
						}

						capNhatTongCo();
					});
		}

		function themTkNo(id, targetTr) {
			// Clone từ dòng tài khoản nợ gần nhất
			lyDo = $("#taiKhoanNoDs" + id + "\\.lyDo").clone();
			maTk = $("#taiKhoanNoDs" + id + "\\.loaiTaiKhoan\\.maTk").clone();
			soDu = $("#taiKhoanNoDs" + id + "\\.soDu").clone();
			maNvkt = $("#taiKhoanNoDs" + id + "\\.maNvkt").clone();
			soTien = $("#taiKhoanNoDs" + id + "\\.soTien\\.soTien").clone();

			// Chèn vào tr mới nhất
			targetTd = $(targetTr).children("td").first();
			$(lyDo).appendTo($(targetTd));
			$(maTk).appendTo($(targetTd).next());
			$(soDu).appendTo($(targetTd).next());
			$(maNvkt).appendTo($(targetTd).next());
			$(soTien).appendTo($(targetTd).next().next());

			// Đổi id và name
			id++;
			$(lyDo).prop("id", "taiKhoanNoDs" + id + ".lyDo");
			$(lyDo).prop("name", "taiKhoanNoDs[" + id + "].lyDo");
			$(maTk).prop("id", "taiKhoanNoDs" + id + ".loaiTaiKhoan.maTk");
			$(maTk).prop("name", "taiKhoanNoDs[" + id + "].loaiTaiKhoan.maTk");
			$(soDu).prop("id", "taiKhoanNoDs" + id + ".soDu");
			$(soDu).prop("name", "taiKhoanNoDs[" + id + "].soDu");
			$(maNvkt).prop("id", "taiKhoanNoDs" + id + ".maNvkt");
			$(maNvkt).prop("name", "taiKhoanNoDs[" + id + "].maNvkt");
			$(soTien).prop("id", "taiKhoanNoDs" + id + ".soTien.soTien");
			$(soTien).prop("name", "taiKhoanNoDs[" + id + "].soTien.soTien");

			// Làm sạch dữ liệu
			$(maTk).val("0");
			$(maNvkt).val("0");
			$(soTien).val("0");
		}

		function themTkCo(id, targetTr) {
			// Clone từ dòng tài khoản nợ gần nhất
			lyDo = $("#taiKhoanCoDs" + id + "\\.lyDo").clone();
			maTk = $("#taiKhoanCoDs" + id + "\\.loaiTaiKhoan\\.maTk").clone();
			soDu = $("#taiKhoanCoDs" + id + "\\.soDu").clone();
			maNvkt = $("#taiKhoanCoDs" + id + "\\.maNvkt").clone();
			soTien = $("#taiKhoanCoDs" + id + "\\.soTien\\.soTien").clone();

			// Chèn vào tr mới nhất
			targetTd = $(targetTr).children("td").first().next().next().next();
			$(lyDo).appendTo($(targetTd));
			$(maTk).appendTo($(targetTd).next());
			$(soDu).appendTo($(targetTd).next());
			$(maNvkt).appendTo($(targetTd).next());
			$(soTien).appendTo($(targetTd).next().next());

			// Đổi id và name
			id++;
			$(lyDo).prop("id", "taiKhoanCoDs" + id + ".lyDo");
			$(lyDo).prop("name", "taiKhoanCoDs[" + id + "].lyDo");
			$(maTk).prop("id", "taiKhoanCoDs" + id + ".loaiTaiKhoan.maTk");
			$(maTk).prop("name", "taiKhoanCoDs[" + id + "].loaiTaiKhoan.maTk");
			$(soDu).prop("id", "taiKhoanCoDs" + id + ".soDu");
			$(soDu).prop("name", "taiKhoanCoDs[" + id + "].soDu");
			$(maNvkt).prop("id", "taiKhoanCoDs" + id + ".maNvkt");
			$(maNvkt).prop("name", "taiKhoanCoDs[" + id + "].maNvkt");
			$(soTien).prop("id", "taiKhoanCoDs" + id + ".soTien.soTien");
			$(soTien).prop("name", "taiKhoanCoDs[" + id + "].soTien.soTien");

			// Làm sạch dữ liệu
			$(maTk).val("0");
			$(maNvkt).val("0");
			$(soTien).val("0");
		}

		function xoaTkNo(id) {
			var targetTr = $("tr#" + id);
			var td = $(targetTr).children("td").first();
			$(td).html("");
			$(td).next().html("");
			$(td).next().next().html("");
		}

		function xoaTkCo(id) {
			var targetTr = $("tr#" + id);
			var td = $(targetTr).children("td").first().next().next().next();
			$(td).html("");
			$(td).next().html("");
			$(td).next().next().html("");
		}

		$("#themTkNo").click(function() {
			// Thêm giao diện tài khoản nợ
			var targetTr;
			var id = soDongTkNo - 1;
			if (soDongTkNo == soDongTk) {
				// Thêm dòng mới
				var currentTr = $("tr#" + id);
				targetTr = currentTr.clone();

				// Làm sạch dòng targetTr
				$(targetTr).find("td").each(function() {
					$(this).html("");
				});

				// Đưa dòng mới vào tài liệu
				$(targetTr).insertAfter($(currentTr)).prop("id", soDongTkNo);

				// Tăng số dòng
				soDongTk++;
			} else {
				// Chèn tài khoản nợ vào dòng sẵn có
				targetTr = $("tr#" + soDongTkNo);
			}

			// Thêm nội dùng vào dòng mới
			themTkNo(id, targetTr);

			// Đăng ký sự kiện thay đổi
			dangKyThayDoiNo();

			soDongTkNo++;

			$("#xoaTkNo").removeClass("disabled");
		});

		$("#themTkCo").click(function() {
			// Thêm giao diện tài khoản có
			var targetTr;
			var id = soDongTkCo - 1;
			if (soDongTkCo == soDongTk) {
				// Thêm dòng mới
				var currentTr = $("tr#" + id);
				targetTr = currentTr.clone();

				// Làm sạch dòng targetTr
				$(targetTr).find("td").each(function() {
					$(this).html("");
				});

				// Đưa dòng mới vào tài liệu
				$(targetTr).insertAfter($(currentTr)).prop("id", soDongTkCo);

				// Tăng số dòng
				soDongTk++;
			} else {
				// Chèn tài khoản nợ vào dòng sẵn có
				targetTr = $("tr#" + soDongTkCo);
			}

			// Thêm nội dùng vào dòng mới
			themTkCo(id, targetTr);

			// Đăng ký sự kiện thay đổi
			dangKyThayDoiCo()

			soDongTkCo++;

			$("#xoaTkCo").removeClass("disabled");
		});

		$("#xoaTkNo").click(function() {
			// Xóa giao diện tài khoản nợ
			xoaTkNo(soDongTkNo - 1);

			// Xóa dòng nếu ko còn tài khoản bên nợ hay bên có
			if (soDongTkNo == soDongTk && soDongTkCo < soDongTk) {
				$("tr#" + (soDongTk - 1)).remove();
				soDongTk--;
			}

			// Cập nhật giá trị tổng nợ
			capNhatTongNo();

			if (soDongTkNo == 2) {
				$("#xoaTkNo").addClass("disabled");
			}

			soDongTkNo--;
		});

		$("#xoaTkCo").click(function() {
			// Xóa giao diện tài khoản có
			xoaTkCo(soDongTkCo - 1);

			// Xóa dòng nếu ko còn tài khoản bên nợ hay bên có
			if (soDongTkCo == soDongTk && soDongTkNo < soDongTk) {
				$("tr#" + (soDongTk - 1)).remove();
				soDongTk--;
			}

			// Cập nhật giá trị tổng có
			capNhatTongCo();

			if (soDongTkCo == 2) {
				$("#xoaTkCo").addClass("disabled");
			}

			soDongTkCo--;
			//soDongTk--;
		});

		$("#lyDo").change(function() {
			for (i = 0; i < soDongTk; i++) {
				$("#taiKhoanNoDs" + i + "\\.lyDo").val($(this).val());
			}

			for (i = 0; i < soDongTk; i++) {
				$("#taiKhoanCoDs" + i + "\\.lyDo").val($(this).val());
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

			capNhatTongNoTxt();
			capNhatTongCoTxt();
		});

		$("#loaiTien\\.banRa").change(function() {
			loaiTien.banRa = $(this).val();

			capNhatTongNoTxt();
			capNhatTongCoTxt();
		});

		function khoiTao() {
			if (soDongTkNo > 1) {
				$("#xoaTkNo").removeClass("disabled");
			}
			if (soDongTkCo > 1) {
				$("#xoaTkCo").removeClass("disabled");
			}

			// Đăng ký sự kiện thay đổi
			dangKyThayDoiNo();
			dangKyThayDoiCo();

			// Cập nhật tổng giá trị
			capNhatTongNo();
			capNhatTongCo();
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
<form:hidden path="maCt" />
<form:hidden path="loaiCt" />
<div class="row form-group">
	<label class="control-label col-sm-2" for="soCt">Số phiếu dự
		kiến:</label>
	<div class="col-sm-4">
		${mainFinanceForm.loaiCt}${mainFinanceForm.soCt}
		<form:hidden path="soCt" />
	</div>

	<label class="control-label col-sm-2" for=ngayLap>Ngày lập
		phiếu:</label>
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
			<form:option value="${DoiTuong.NHAN_VIEN}" label="Nhân viên"></form:option>
			<form:option value="${DoiTuong.KHACH_HANG}" label="Khách hàng"></form:option>
			<form:option value="${DoiTuong.NHA_CUNG_CAP}" label="Nhà cung cấp"></form:option>
			<form:option value="${DoiTuong.KHACH_VANG_LAI}"
				label="Khách vãng lai"></form:option>
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
		tượng nộp:(*)</label>
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
		nộp: </label>
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
		<form:hidden path="soTien.giaTri" />
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

<div class="table-responsive row form-group">
	<label class="control-label col-sm-2">Định khoản</label>
	<table id="taiKhoanTbl"
		class="table table-bordered table-hover text-center dinhkhoan">
		<thead>
			<tr>
				<th class="text-center" colspan="3">Nợ</th>
				<th class="text-center" colspan="3">Có</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th class="text-center"><b>Lý do</b></th>
				<th class="text-center"><b>Tài khoản</b></th>
				<th class="text-center"><b>Giá trị</b></th>
				<th class="text-center"><b>Lý do</b></th>
				<th class="text-center"><b>Tài khoản</b></th>
				<th class="text-center"><b>Giá trị</b></th>
			</tr>
			<c:forEach begin="0" end="${mainFinanceForm.soTkLonNhat-1}"
				varStatus="status">
				<tr id="${status.index}">
					<!-- Phần ghi Nợ -->
					<c:choose>
						<c:when
							test="${status.index < mainFinanceForm.taiKhoanNoDs.size()}">
							<td><form:input cssClass="form-control"
									path="taiKhoanNoDs[${status.index}].lyDo" placeholder="Lý do" /></td>
							<td><form:select cssClass="form-control"
									path="taiKhoanNoDs[${status.index}].loaiTaiKhoan.maTk"
									multiple="false">
									<form:option value="0">Tài khoản</form:option>
									<form:options items="${loaiTaiKhoanDs}" itemValue="maTk"
										itemLabel="maTenTk" />
								</form:select> <form:hidden path="taiKhoanNoDs[${status.index}].soDu" /> <form:hidden
									path="taiKhoanNoDs[${status.index}].maNvkt" /> <form:errors
									path="taiKhoanNoDs[${status.index}].loaiTaiKhoan.maTk"
									cssClass="error" /></td>
							<td><form:input cssClass="form-control"
									path="taiKhoanNoDs[${status.index}].soTien.soTien"
									placeholder="0.0" /> <form:errors
									path="taiKhoanNoDs[${status.index}].soTien.soTien"
									cssClass="error" /></td>

						</c:when>
						<c:otherwise>
							<td></td>
							<td></td>
							<td></td>
						</c:otherwise>
					</c:choose>

					<!-- Phần ghi Có -->
					<c:choose>
						<c:when
							test="${status.index < mainFinanceForm.taiKhoanCoDs.size()}">
							<td><form:input cssClass="form-control"
									path="taiKhoanCoDs[${status.index}].lyDo" placeholder="Lý do" /></td>
							<td><form:select cssClass="form-control"
									path="taiKhoanCoDs[${status.index}].loaiTaiKhoan.maTk"
									multiple="false">
									<form:option value="0">Tài khoản</form:option>
									<form:options items="${loaiTaiKhoanDs}" itemValue="maTk"
										itemLabel="maTenTk" />
								</form:select> <form:hidden path="taiKhoanCoDs[${status.index}].soDu" /> <form:hidden
									path="taiKhoanCoDs[${status.index}].maNvkt" /> <form:errors
									path="taiKhoanCoDs[${status.index}].loaiTaiKhoan.maTk"
									cssClass="error" /></td>
							<td><form:input cssClass="form-control"
									path="taiKhoanCoDs[${status.index}].soTien.soTien"
									placeholder="0.0" /> <form:errors
									path="taiKhoanCoDs[${status.index}].soTien.soTien"
									cssClass="error" /></td>
						</c:when>
						<c:otherwise>
							<td></td>
							<td></td>
							<td></td>
						</c:otherwise>
					</c:choose>
				</tr>
			</c:forEach>
			<tr>
				<th colspan="3">Tổng nợ: <span id="soTien.soTienNo"
					class="pull-right"></span> <form:errors path="soTien.soTien"
						cssClass="error" /></th>
				<th colspan="3">Tổng có: <span id="soTien.soTienCo"
					class="pull-right"></span></th>
			</tr>
			<tr>
				<td colspan="3">
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
	<div class="col-sm-2">
		<a href="${url}/chungtu/ktth/xem/${mainFinanceForm.maCt}"
			class="btn btn-info btn-sm">Hủy</a>
		<button id="submitBt" type="submit" class="btn btn-info btn-sm">Lưu
			thay đổi</button>
	</div>
</div>