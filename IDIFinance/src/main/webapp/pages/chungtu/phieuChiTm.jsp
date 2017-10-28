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
		$("#mainFinanceForm").attr("action", "${url}/luutaomoiphieuchi");
		$("#mainFinanceForm").attr("method", "POST");

		$("#submitBt").click(function() {
			$("#mainFinanceForm").submit();
		});

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
			//$("#soTien\\.soTien").val("0.0");
			//$("#lyDo").val("");
			//$("#kemTheo").val("0");
		}

		// Khởi tạo danh sách các loại tiền
		var loaiTienDsStr = "${loaiTienDs}";
		loaiTienDsStr = loaiTienDsStr.substring(1, loaiTienDsStr.length - 1);
		loaiTienDsStr = $.trim(loaiTienDsStr);
		var loaiTienDsTmpl = loaiTienDsStr.split(",");
		var loaiTienDs = new Array(loaiTienDsTmpl.length);
		for (i = 0; i < loaiTienDsTmpl.length; i++) {
			var loaiTien = $.trim(loaiTienDsTmpl[i]);
			var tienTmpl = loaiTienDsTmpl[i].split("-");

			var tien = new Object();
			tien.maLt = $.trim(tienTmpl[0]);
			tien.tenLt = $.trim(tienTmpl[1]);
			tien.banRa = $.trim(tienTmpl[2]);
			loaiTienDs[i] = tien;

			if (tien.maLt == "VND") {
				loaiTien = tien;
			}
		}

		// Xác định giá trị mặc định là tiền Việt Nam
		document.getElementById("soTien.tien.maLt").value = "VND";

		// Xác định các giá trị tương ứng khi loại tiền được người dùng thay đổi
		document.getElementById("soTien.tien.maLt").onchange = function() {
			// Thay đổi loại tiền
			for (i = 0; i < loaiTienDs.length; i++) {
				if (loaiTienDs[i].maLt == this.value) {
					loaiTien = loaiTienDs[i];
					break;
				}
			}

			// Cập nhật tỷ giá
			document.getElementById("soTien.tien.banRa").value = loaiTien.banRa;

			// Quy ra tiền Việt Nam
			document.getElementById("soTien.giaTri").value = document
					.getElementById("soTien.soTien").value
					* loaiTien.banRa;
			document.getElementById("soTien.giaTriTxt").innerHTML = document
					.getElementById("soTien.giaTri").value
					+ " VND";
		}

		// Khi giá trị tiền thay đổi, quy đổi ra tiền Việt Nam Đồng
		document.getElementById("soTien.soTien").onchange = function() {
			document.getElementById("soTien.giaTri").value = document
					.getElementById("soTien.soTien").value
					* loaiTien.banRa;
			document.getElementById("soTien.giaTriTxt").innerHTML = document
					.getElementById("soTien.giaTri").value
					+ " VND";

			document.getElementById("taiKhoanCoDs0.soTien").value = document
					.getElementById("soTien.soTien").value;
		}

		// Khi tỷ giá thay đổi, quy đổi ra tiền Việt Nam Đồng
		document.getElementById("soTien.tien.banRa").onchange = function() {
			loaiTien.banRa = $.trim(document
					.getElementById("soTien.tien.banRa").value);
			for (i = 0; i < loaiTienDs.length; i++) {
				if (loaiTienDs[i].maLt == loaiTien.maLt) {
					loaiTienDs[i].banRa = loaiTien.banRa;
					break;
				}
			}

			var soTien = $.trim(document.getElementById("soTien.soTien").value);
			document.getElementById("soTien.giaTri").value = soTien
					* loaiTien.banRa;
			document.getElementById("soTien.giaTriTxt").innerHTML = document
					.getElementById("soTien.giaTri").value
					+ " VND";
			document.getElementById("taiKhoanCoDs0.soTien").value = document
					.getElementById("soTien.soTien").value;
		}

		$("#themTkNo").click(
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

					$("#taiKhoanNoDs" + newId + "\\.taiKhoan\\.maTk").val("0");
					$("#taiKhoanNoDs" + newId + "\\.soTien").val("0.0");
					$("#taiKhoanNoDs" + newId + "\\.soTien").prop("placeholder", "0.0");
					$("#taiKhoanNoDs" + newId + "\\.lyDo").val("");
					$("#taiKhoanNoDs" + newId + "\\.lyDo").prop("placeholder", "Lý do");
					$("#taiKhoanNoDs" + newId + "\\.soTien\\.errors").remove();
					$("#taiKhoanNoDs" + newId + "\\.taiKhoan\\.maTk\\.errors").remove();

					$("#taiKhoanCoDs" + newId + "\\.taiKhoan\\.maTk").remove();
					$("#taiKhoanCoDs" + newId + "\\.ghiNo").remove();
					$("#taiKhoanCoDs" + newId + "\\.soTien").remove();
					$("#taiKhoanCoDs" + newId + "\\.soTien\\.errors").remove();

					$("#xoaTkNo").removeClass("disabled");
				});

		$("#xoaTkNo").click(function() {
			var removedTr = $(this).parent().parent().prev();
			var id = $(removedTr).prop("id");
			$(removedTr).remove();

			if (id == 1) {
				$("#xoaTkNo").addClass("disabled");
			}
		});

		soDongTk = '${mainFinanceForm.soTkLonNhat}';
		if (soDongTk > 1) {
			$("#xoaTkCo").removeClass("disabled");
		}
	});
</script>

<h4>PHIẾU CHI</h4>
<hr />
<form:hidden path="loaiCt" />
<div class="row form-group">
	<label class="control-label col-sm-2" for="soCt">Số phiếu chi
		dự kiến:</label>
	<div class="col-sm-4">
		${mainFinanceForm.soCt}
		<form:hidden path="soCt" />
	</div>

	<label class="control-label col-sm-2" for=ngayLap>Ngày lập
		phiếu chi:</label>
	<div class="col-sm-4">
		<fmt:formatDate value="${mainFinanceForm.ngayLap}" pattern="dd/M/yyyy"
			type="Date" dateStyle="SHORT" />
		<form:hidden path="ngayLap" />
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
		<fmt:formatDate value="${mainFinanceForm.ngayHt}" pattern="dd/M/yyyy"
			type="Date" dateStyle="SHORT" />
		<form:hidden path="ngayHt" />
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
<hr />
<div class="row form-group">
	<label class="control-label col-sm-2" for="soTien.soTien">Số
		tiền:(*)</label>
	<div class="col-sm-4">
		<form:input path="soTien.soTien" placeholder="0.0"
			cssClass="form-control" />
		<br />
		<form:errors path="soTien.soTien" cssClass="error" />
	</div>

	<label class="control-label col-sm-2" for="soTien.tien.maLt">Loại
		tiền</label>
	<div class="col-sm-4">
		<form:select path="soTien.tien.maLt" cssClass="form-control">
			<form:options items="${loaiTienDs}" itemValue="maLt"
				itemLabel="tenLt" />
		</form:select>
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
		(<i>Chuyển thành tiền Việt Nam Đồng</i>)
	</div>

	<label class="control-label col-sm-2" for="soTien.tien.banRa">Tỷ
		giá:(*)</label>
	<div class="col-sm-4">
		<form:input path="soTien.tien.banRa" placeholder="0.0"
			cssClass="form-control" />
		<br />
		<form:errors path="soTien.tien.banRa" cssClass="error" />
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
	<label class="control-label col-sm-2">Định khoản</label>
	<table id="taiKhoanTbl"
		class="table table-bordered table-hover text-center dinhkhoan">
		<thead>
			<tr>
				<th class="text-center" colspan="4">Nợ</th>
				<th class="text-center" colspan="3">Có</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th class="text-center"><b>Tài khoản</b></th>
				<th class="text-center"><b>Giá trị</b></th>
				<th class="text-center"><b>Lý do</b></th>
				<th class="text-center"><b>Ghi chú</b></th>
				<th class="text-center"><b>Tài khoản</b></th>
				<th class="text-center"><b>Giá trị</b></th>
				<th class="text-center"><b>Ghi chú</b></th>
			</tr>

			<c:forEach begin="0" end="${mainFinanceForm.soTkLonNhat-1}"
				varStatus="status">
				<tr id="${status.index}">
					<!-- Phần ghi Nợ -->
					<td><form:select cssClass="form-control"
							path="taiKhoanNoDs[${status.index}].taiKhoan.maTk"
							multiple="false">
							<form:option value="0">Tài khoản</form:option>
							<form:options items="${loaiTaiKhoanDs}" itemValue="maTk"
								itemLabel="maTenTk" />
						</form:select> <form:hidden path="taiKhoanNoDs[${status.index}].ghiNo" /></td>
					<td><form:input cssClass="form-control"
							path="taiKhoanNoDs[${status.index}].soTien" placeholder="0.0" /></td>
					<td><form:input cssClass="form-control"
							path="taiKhoanNoDs[${status.index}].lyDo" placeholder="Lý do" /></td>
					<td><form:errors
							path="taiKhoanNoDs[${status.index}].taiKhoan.maTk"
							cssClass="error" /> <form:errors
							path="taiKhoanNoDs[${status.index}].soTien" cssClass="error" /></td>

					<!-- Phần ghi Có -->
					<c:choose>
						<c:when
							test="${status.index < mainFinanceForm.taiKhoanCoDs.size()}">
							<td><form:select cssClass="form-control"
									path="taiKhoanCoDs[${status.index}].taiKhoan.maTk"
									multiple="false">
									<form:options items="${loaiTaiKhoanTmDs}" itemValue="maTk"
										itemLabel="maTenTk" />
								</form:select> <form:hidden path="taiKhoanCoDs[${status.index}].ghiNo" /></td>
							<td><form:input cssClass="form-control"
									path="taiKhoanCoDs[${status.index}].soTien" placeholder="0.0" /></td>
							<td><form:errors path="taiKhoanCoDs[${status.index}].soTien"
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
				<td colspan="7">
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
	<div class="col-sm-2">
		<a href="${url}/danhsachphieuchi" class="btn btn-info btn-sm">Hủy</a>
		<button id="submitBt" type="submit" class="btn btn-info btn-sm">Tạo
			mới</button>
	</div>
</div>