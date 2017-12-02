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
		$("#mainFinanceForm").attr("action", "${url}/luutaomoiktth");
		$("#mainFinanceForm").attr("method", "POST");

		$("#submitBt").click(function() {
			$("#mainFinanceForm").submit();
		});

		var loaiTien = null;

		function cleanForm() {

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
			document.getElementById("taiKhoanNoDs0.soTien").value = document
					.getElementById("soTien.soTien").value;
		}

		$("#themTk").click(
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

					$("#taiKhoanCoDs" + newId + "\\.taiKhoan\\.maTk").val("0");
					$("#taiKhoanCoDs" + newId + "\\.soTien").val("0.0");
					$("#taiKhoanCoDs" + newId + "\\.soTien").prop(
							"placeholder", "0.0");
					$("#taiKhoanCoDs" + newId + "\\.lyDo").val("");
					$("#taiKhoanCoDs" + newId + "\\.lyDo").prop("placeholder",
							"Lý do");
					$("#taiKhoanCoDs" + newId + "\\.soTien\\.errors").remove();
					$("#taiKhoanCoDs" + newId + "\\.taiKhoan\\.maTk\\.errors")
							.remove();

					$("#xoaTk").removeClass("disabled");
				});

		$("#xoaTk").click(function() {
			var removedTr = $(this).parent().parent().prev();
			var id = $(removedTr).prop("id");
			$(removedTr).remove();

			if (id == 1) {
				$("#xoaTk").addClass("disabled");
			}
		});

		soDongTk = '${mainFinanceForm.soTkLonNhat}';
		if (soDongTk > 1) {
			$("#xoaTk").removeClass("disabled");
		}
	});
</script>

<h4>PHIẾU KẾ TOÁN TỔNG HỢP</h4>
<hr />
<form:hidden path="loaiCt" />
<div class="row form-group">
	<label class="control-label col-sm-2" for="soCt">Số phiếu KTTH
		dự kiến:</label>
	<div class="col-sm-4">
		${mainFinanceForm.soCt}
		<form:hidden path="soCt" />
	</div>
</div>


<div class="row form-group">
	<label class="control-label col-sm-2" for=ngayLap>Ngày lập
		phiếu KTTH:</label>
	<div class="col-sm-4">
		<fmt:formatDate value="${mainFinanceForm.ngayLap}" pattern="dd/M/yyyy"
			type="Date" dateStyle="SHORT" />
		<form:hidden path="ngayLap" />
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
	<label class="control-label col-sm-2" for="soTien.tien.maLt">Loại
		tiền</label>
	<div class="col-sm-4">
		<form:select path="soTien.tien.maLt" cssClass="form-control">
			<form:options items="${loaiTienDs}" itemValue="maLt"
				itemLabel="tenLt" />
		</form:select>
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
</div>


<div class="table-responsive row form-group">
	<label class="control-label col-sm-2">Định khoản</label>
	<table id="taiKhoanTbl"
		class="table table-bordered table-hover text-center dinhkhoan">
		<thead>
			<tr>
				<th class="text-center" colspan="4">Nợ</th>
				<th class="text-center" colspan="4">Có</th>
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
				<th class="text-center"><b>Lý do</b></th>
				<th class="text-center"><b>Ghi chú</b></th>
			</tr>
			<c:forEach begin="0" end="${mainFinanceForm.soTkLonNhat-1}"
				varStatus="status">
				<tr id="${status.index}">
					<!-- Phần ghi Nợ -->
					<c:choose>
						<c:when
							test="${status.index < mainFinanceForm.taiKhoanNoDs.size()}">
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
									cssClass="error" />
								<form:errors path="taiKhoanNoDs[${status.index}].soTien"
									cssClass="error" /></td>
						</c:when>
						<c:otherwise>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</c:otherwise>
					</c:choose>

					<!-- Phần ghi Có -->
					<td><form:select cssClass="form-control"
							path="taiKhoanCoDs[${status.index}].taiKhoan.maTk"
							multiple="false">
							<form:option value="0">Tài khoản</form:option>
							<form:options items="${loaiTaiKhoanDs}" itemValue="maTk"
								itemLabel="maTenTk" />
						</form:select> <form:hidden path="taiKhoanCoDs[${status.index}].ghiNo" /></td>
					<td><form:input cssClass="form-control"
							path="taiKhoanCoDs[${status.index}].soTien" placeholder="0.0" /></td>
					<td><form:input cssClass="form-control"
							path="taiKhoanCoDs[${status.index}].lyDo" placeholder="Lý do" /></td>
					<td><form:errors
							path="taiKhoanCoDs[${status.index}].taiKhoan.maTk"
							cssClass="error" /> <form:errors
							path="taiKhoanCoDs[${status.index}].soTien" cssClass="error" /></td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="8">
					<button id="themTk" type="button" class="btn btn-info btn-sm"
						title="Thêm tài khoản ghi có">
						<span class="glyphicon glyphicon-plus"></span> Thêm
					</button>
					<button id="xoaTk" type="button"
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
		<a href="${url}/danhsachktth" class="btn btn-info btn-sm">Hủy</a>
		<button id="submitBt" type="submit" class="btn btn-info btn-sm">Tạo
			mới</button>
	</div>
</div>