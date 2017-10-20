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
		$("#mainFinanceForm").attr("action", "${url}/luutaomoiphieuthu");
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
			idFieldName : "doiTuong.maDt",
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
			loaiDt = this.value;
			if (loaiDt == 1) {
				url = "${url}/chungtu/nhanvien/";
			} else if (loaiDt == 2) {
				url = "${url}/chungtu/khachhang/";
			} else if (loaiDt == 3) {
				url = "${url}/chungtu/nhacungcap/";
			} else {
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

			document.getElementById("taiKhoanDs0.soTien").value = document
					.getElementById("soTien.soTien").value;
		}

	});
</script>

<h4>PHIẾU THU</h4>
<hr />
<div class="row form-group">
	<label class="control-label col-sm-2" for="soCt">Số phiếu thu
		dự kiến:</label>
	<div class="col-sm-4">
		${mainFinanceForm.soCt}
		<form:hidden path="soCt" />
	</div>

	<label class="control-label col-sm-2" for=ngayLap>Ngày lập
		phiếu thu:</label>
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
			<form:option value="1" label="Nhân viên"></form:option>
			<form:option value="2" label="Khách hàng"></form:option>
			<form:option value="3" label="Nhà cung cấp"></form:option>
			<form:option value="4" label="Khách vãng lai"></form:option>
		</form:select>
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="doiTuong.tenDt">Đối
		tượng nộp:(*)</label>
	<div class="col-sm-4">
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
		<p id="soTien.giaTriTxt">${mainFinanceForm.soTien.giaTri}</p>
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
	</div>
</div>


<div class="row form-group">
	<label class="control-label col-sm-2">Định khoản</label>
	<table id="taiKhoanTbl"
		class="table table-bordered table-hover text-center dinhkhoan">
		<thead>
			<tr>
				<th class="text-center" colspan="2">Nợ</th>
				<th class="text-center" colspan="2">Có</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th class="text-center"><b>Tài khoản</b></th>
				<th class="text-center"><b>Giá trị</b></th>
				<th class="text-center"><b>Tài khoản</b></th>
				<th class="text-center"><b>Giá trị</b></th>
			</tr>
			<c:forEach items="${mainFinanceForm.taiKhoanDs}" var="taiKhoan"
				varStatus="status">
				<c:choose>
					<c:when test="${taiKhoan.ghiNo == 0}">
						<tr id="${status.index}">
							<td><form:select cssClass="form-control"
									path="taiKhoanDs[${status.index}].taiKhoan.maTk"
									multiple="false">
									<form:options items="${loaiTaiKhoanTmDs}" itemValue="maTk"
										itemLabel="maTenTk" />
								</form:select> <form:hidden path="taiKhoanDs[${status.index}].ghiNo" /></td>
							<td><form:input cssClass="form-control"
									path="taiKhoanDs[${status.index}].soTien" placeholder="0.0" /></td>
							<td></td>
							<td></td>
						</tr>
					</c:when>
					<c:when test="${taiKhoan.ghiNo == 1}">
						<tr id="${status.index}">
							<td></td>
							<td></td>
							<td><form:select cssClass="form-control"
									path="taiKhoanDs[${status.index}].taiKhoan.maTk"
									multiple="false">
									<form:option value="0">Tài khoản</form:option>
									<form:options items="${loaiTaiKhoanDs}" itemValue="maTk"
										itemLabel="maTenTk" />
								</form:select> <form:hidden path="taiKhoanDs[${status.index}].ghiNo" /></td>
							<td><form:input cssClass="form-control"
									path="taiKhoanDs[${status.index}].soTien" placeholder="0.0" /></td>
						</tr>
					</c:when>
				</c:choose>
			</c:forEach>
		</tbody>
	</table>
</div>

<div class="row form-group">
	<div class="col-sm-2">
		<a href="${url}/danhsachphieuthu" class="btn btn-info btn-sm">Hủy</a>
		<button id="submitBt" type="submit" class="btn btn-info btn-sm">Tạo
			mới</button>
	</div>
</div>


