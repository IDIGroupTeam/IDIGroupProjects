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
		$("#mainFinanceForm").attr("action", "${url}/chungtu/baoco/luu");
		$("#mainFinanceForm").attr("method", "POST");

		$("#submitBt").click(function() {
			$("#mainFinanceForm").submit();
		});

		var soDongTk = '${mainFinanceForm.soTkLonNhat}';
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

		function capNhapTongTien() {
			var tongGiaTri = 0;
			$("input[id^='taiKhoanCoDs'][id$='\\.soTien\\.soTien']").each(
					function() {
						var giaTriTt = $.trim($(this).val());

						if (giaTriTt != '' && !isNaN(giaTriTt)) {
							tongGiaTri += parseFloat(giaTriTt);
						}
					});

			$("#taiKhoanNoDs0\\.soTien\\.soTien").val(tongGiaTri);
			$("#taiKhoanNoDs0\\.soTien\\.soTienTxt").html(
					accounting.formatNumber(tongGiaTri, 0, ","));

			var tyGia = $.trim($("#loaiTien\\.banRa").val());
			$("#soTien\\.giaTriTxt").html(
					accounting.formatNumber(tongGiaTri * tyGia, 0, ",")
							+ " VND");
		}

		function capNhatTongTienTxt() {
			// Quy ra tiền Việt Nam
			var tongGiaTri = $("#taiKhoanNoDs0\\.soTien\\.soTien").val();
			$("#soTien\\.giaTriTxt").html(
					accounting
							.formatNumber(tongGiaTri * loaiTien.banRa, 0, ",")
							+ " VND");
		}

		function thayDoiDuLieu() {
			var giaTri = $.trim($(this).val());
			if (giaTri != '' && !isNaN(giaTri)) {
				$(this).val(parseFloat(giaTri));
			}

			capNhapTongTien();
		}

		$("#themTkCo").click(
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

					$(newTr).insertBefore($(currentTr)).prop("id", newId);

					$("#"+newId).find(".combobox-container").remove();
					$("#taiKhoanCoDs" + newId + "\\.loaiTaiKhoan\\.maTk").prop("name", "taiKhoanCoDs["+newId+"].loaiTaiKhoan.maTk");
					$("#taiKhoanCoDs" + newId + "\\.loaiTaiKhoan\\.maTk").val("0");
					$("#taiKhoanCoDs" + newId + "\\.loaiTaiKhoan\\.maTk").combobox();
					$("#taiKhoanCoDs" + newId + "\\.soTien\\.soTien").val("0");
					$("#taiKhoanCoDs" + newId + "\\.soTien\\.soTien").prop("placeholder", "0");
					$("#taiKhoanCoDs" + newId + "\\.lyDo").prop("placeholder", "Lý do");
					$("#taiKhoanCoDs" + newId + "\\.soTien\\.soTien\\.errors").remove();
					$("#taiKhoanCoDs" + newId + "\\.loaiTaiKhoan\\.maTk\\.errors").remove();

					$("#taiKhoanNoDs" + newId + "\\.loaiTaiKhoan\\.maTk").remove();
					$("#taiKhoanNoDs" + newId + "\\.soDu").remove();
					$("#taiKhoanNoDs" + newId + "\\.soTien\\.soTien").remove();
					$("#taiKhoanNoDs" + newId + "\\.soTien\\.soTienTxt").remove();
					$("#taiKhoanNoDs" + newId + "\\.lyDo").remove();

					$("#xoaTkCo").removeClass("disabled");

					$("input[id^='taiKhoanCoDs'][id$='\\.soTien\\.soTien']").change(thayDoiDuLieu);
				});

		$("#xoaTkCo").click(function() {
			var removedTr = $(this).parent().parent().prev();
			var id = $(removedTr).prop("id");
			$(removedTr).remove();

			if (id == 1) {
				$("#xoaTkCo").addClass("disabled");
			}

			capNhapTongTien();
		});

		$("#lyDo").change(function() {
			$("#taiKhoanNoDs0\\.lyDo").val($(this).val());
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

			$("input[id^='taiKhoanCoDs'][id$='\\.soTien\\.soTien']").change(
					thayDoiDuLieu);
			$("select[id^='taiKhoanCoDs'][id$='\\.loaiTaiKhoan\\.maTk']").combobox();
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

<h4>BÁO CÓ</h4>
<hr />
<form:hidden path="loaiCt" />
<div class="row form-group">
	<label class="control-label col-sm-2" for="soCt">Số báo có dự
		kiến:</label>
	<div class="col-sm-4">
		${mainFinanceForm.loaiCt}${mainFinanceForm.soCt}
		<form:hidden path="soCt" />
	</div>

	<label class="control-label col-sm-2" for=ngayLap>Ngày lập báo
		có:</label>
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
			<form:input path="ngayHt" class="form-control" />
			<span class="input-group-addon"><span
				class="glyphicon glyphicon-calendar"></span></span>
		</div>
		<form:errors path="ngayHt" cssClass="error" />
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

<div class="table-responsive row form-group">
	<label class="control-label col-sm-2">Định khoản</label>
	<table id="taiKhoanTbl"
		class="table table-bordered table-hover text-center dinhkhoan">
		<thead>
			<tr>
				<th class="text-center" colspan="2">Nợ</th>
				<th class="text-center" colspan="3">Có</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th class="text-center"><b>Tài khoản</b></th>
				<th class="text-center"><b>Giá trị</b></th>
				<th class="text-center"><b>Tài khoản</b></th>
				<th class="text-center"><b>Giá trị</b></th>
				<th class="text-center"><b>Lý do</b></th>
			</tr>
			<c:forEach begin="0" end="${mainFinanceForm.soTkLonNhat-1}"
				varStatus="status">
				<tr id="${status.index}">
					<!-- Phần ghi Nợ -->
					<c:choose>
						<c:when
							test="${status.index < mainFinanceForm.taiKhoanNoDs.size()}">
							<td><form:select cssClass="form-control"
									path="taiKhoanNoDs[${status.index}].loaiTaiKhoan.maTk"
									multiple="false">
									<form:options items="${loaiTaiKhoanTgnhDs}" itemValue="maTk"
										itemLabel="maTenTk" />
								</form:select> <form:hidden path="taiKhoanNoDs[${status.index}].soDu" /></td>
							<td><span id="taiKhoanNoDs${status.index}.soTien.soTienTxt"><fmt:formatNumber
										value="${mainFinanceForm.taiKhoanNoDs[status.index].soTien.soTien}"></fmt:formatNumber>
									${mainFinanceForm.loaiTien.maLt}</span> <form:hidden
									path="taiKhoanNoDs[${status.index}].soTien.soTien" /> <form:hidden
									path="taiKhoanNoDs[${status.index}].lyDo" /></td>
						</c:when>
						<c:otherwise>
							<td></td>
							<td></td>
						</c:otherwise>
					</c:choose>

					<!-- Phần ghi Có -->
					<td class="text-left"><form:select cssClass="form-control"
							path="taiKhoanCoDs[${status.index}].loaiTaiKhoan.maTk"
							multiple="false">
							<form:option value="0">Tài khoản</form:option>
							<form:options items="${loaiTaiKhoanDs}" itemValue="maTk"
								itemLabel="maTenTk" />
						</form:select> <form:hidden path="taiKhoanCoDs[${status.index}].soDu" /> <form:errors
							path="taiKhoanCoDs[${status.index}].loaiTaiKhoan.maTk"
							cssClass="error" /></td>
					<td><form:input cssClass="form-control"
							path="taiKhoanCoDs[${status.index}].soTien.soTien"
							placeholder="0.0" /> <form:errors
							path="taiKhoanCoDs[${status.index}].soTien.soTien"
							cssClass="error" /></td>
					<td><form:input cssClass="form-control"
							path="taiKhoanCoDs[${status.index}].lyDo" placeholder="Lý do" /></td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="5">
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
		<a href="${url}/chungtu/baoco/danhsach" class="btn btn-info btn-sm">Hủy</a>
		<button id="submitBt" type="submit" class="btn btn-info btn-sm">Tạo
			mới</button>
	</div>
</div>