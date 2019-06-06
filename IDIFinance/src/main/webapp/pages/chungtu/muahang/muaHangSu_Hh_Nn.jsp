<%@page import="com.idi.finance.bean.taikhoan.LoaiTaiKhoan"%>
<%@page import="com.idi.finance.bean.doituong.DoiTuong"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<ul class="nav nav-tabs nav-pills nav-justified">
	<li class="active"><a data-toggle="tab" href="#hangTien">Hàng
			tiền</a></li>
	<li><a data-toggle="tab" href="#thue">Thuế</a></li>
	<li><a data-toggle="tab" href="#chiPhi">Chi phí</a></li>
</ul>
<div class="tab-content table-responsive sub-content"
	style="overflow-x: auto; overflow-y: none;">
	<div id="hangTien" class="tab-pane fade in active"
		style="width: 1700px;">
		<table class="table table-bordered table-hover text-center hanghoa"
			id="hangTienTbl">
			<thead>
				<tr>
					<th class="text-center">Mã vật tư, hàng hóa</th>
					<th class="text-center">Vật tư, hàng hóa</th>
					<th class="text-center">Đơn vị tính</th>
					<th class="text-center">Số lượng</th>
					<th class="text-center">Giá mua</th>
					<th class="text-center">Thành tiền</th>
					<th class="text-center">TK Kho (Nợ)</th>
					<th class="text-center">TK công nợ (Có)</th>
					<th class="text-center">Kho</th>
					<th class="text-center">Giá nhập kho</th>
					<th class="text-center">Tổng tiền<br />nhập kho
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${mainFinanceForm.hangHoaDs}" var="hangHoa"
					varStatus="status">
					<tr id="hangTien${status.index}">
						<td class="text-left" style="width: 200px;"><form:select
								path="hangHoaDs[${status.index}].maHh" cssClass="form-control">
								<form:option value="0" label=""></form:option>
								<form:options items="${khHangHoaDs}" itemValue="maHh"
									itemLabel="kyHieuHh" />
							</form:select> <form:errors path="hangHoaDs[${status.index}].maHh"
								cssClass="error" /></td>
						<td class="text-left" style="width: 230px;"><form:hidden
								path="hangHoaDs[${status.index}].kyHieuHh" /> <form:hidden
								path="hangHoaDs[${status.index}].tenHh" /> <span
							id="hangHoaDs${status.index}.hangTien.tenHhTxt">${hangHoa.tenHh}</span></td>
						<td><form:hidden path="hangHoaDs[${status.index}].donVi.maDv" />
							<form:hidden path="hangHoaDs[${status.index}].donVi.tenDv" /> <span
							id="hangHoaDs${status.index}.donVi.tenDvTxt">${hangHoa.donVi.tenDv}</span></td>
						<td><form:input cssClass="form-control"
								path="hangHoaDs[${status.index}].soLuong" /> <form:errors
								path="hangHoaDs[${status.index}].soLuong" cssClass="error" /> <form:hidden
								path="hangHoaDs[${status.index}].soLuongBanDau" /></td>
						<td><form:input cssClass="form-control"
								path="hangHoaDs[${status.index}].donGia.soTien" /> <form:errors
								path="hangHoaDs[${status.index}].donGia.soTien" cssClass="error" /></td>
						<td class="text-right" style="width: 180px;"><span
							id="hangHoaDs${status.index}.hangTien.tongTien"></span></td>
						<td><input type="hidden"
							name="hangHoaDs[${status.index}].tkKho.soDu" value="-1" /> <form:hidden
								path="hangHoaDs[${status.index}].tkKho.maNvkt" /> <form:select
								cssClass="form-control"
								path="hangHoaDs[${status.index}].tkKho.loaiTaiKhoan.maTk">
								<form:option value="" label=""></form:option>
								<form:options items="${loaiTaiKhoanKhoDs}" itemValue="maTk"
									itemLabel="maTenTk" />
							</form:select> <form:errors
								path="hangHoaDs[${status.index}].tkKho.loaiTaiKhoan.maTk"
								cssClass="error" /></td>
						<td><input type="hidden"
							name="hangHoaDs[${status.index}].tkThanhtoan.soDu" value="1" />
							<form:hidden path="hangHoaDs[${status.index}].tkThanhtoan.maNvkt" />
							<form:select cssClass="form-control"
								path="hangHoaDs[${status.index}].tkThanhtoan.loaiTaiKhoan.maTk">
								<form:option value="" label=""></form:option>
								<form:options items="${loaiTaiKhoanThanhToanDs}"
									itemValue="maTk" itemLabel="maTenTk" />
							</form:select> <form:errors
								path="hangHoaDs[${status.index}].tkThanhtoan.loaiTaiKhoan.maTk"
								cssClass="error" /></td>
						<td style="width: 200px;"><form:select
								cssClass="form-control"
								path="hangHoaDs[${status.index}].kho.maKho">
								<form:option value="0" label=""></form:option>
								<form:options items="${khoBaiDs}" itemValue="maKho"
									itemLabel="tenKho" />
							</form:select> <form:errors path="hangHoaDs[${status.index}].kho.maKho"
								cssClass="error" /></td>
						<td class="text-right" style="width: 180px;"><form:hidden
								path="hangHoaDs[${status.index}].giaKho.soTien" /> <span
							id="hangHoaDs${status.index}.giaKho.soTienTxt">${giaKho.soTien}</span></td>
						<td class="text-right"><span
							id="hangHoaDs${status.index}.giaKho.tongSoTienTxt">${giaKho.soTien}</span></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div id="thue" class="tab-pane fade">
		<table class="table table-bordered table-hover text-center hanghoa"
			id="thueTbl">
			<thead>
				<tr>
					<th class="text-center" rowspan="2">Vật tư, hàng hóa</th>
					<th class="text-center" rowspan="2">Giá tính thuế</th>
					<th class="text-center" colspan="3">Thuế nhập khẩu</th>
					<th class="text-center" colspan="3">Thuế tiêu thụ đặc biệt</th>
					<!-- <th class="text-center" colspan="3">Thuế giá trị gia tăng (Tính trực tiếp)</th> -->
				</tr>
				<tr>
					<th class="text-center">Thuế suất (%)</th>
					<th class="text-center">Tiền thuế (VND)</th>
					<th class="text-center">Tài khoản (Có)</th>
					<th class="text-center">Thuế suất (%)</th>
					<th class="text-center">Tiền thuế (VND)</th>
					<th class="text-center">Tài khoản (Có)</th>
					<!-- <th class="text-center">Thuế suất (%)</th>
					<th class="text-center">Tiền thuế (VND)</th>
					<th class="text-center">Tài khoản (Có)</th> -->
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${mainFinanceForm.hangHoaDs}" var="hangHoa"
					varStatus="status">
					<tr id="thue${status.index}">
						<td class="text-left" style="width: 220px;"><span
							id="hangHoaDs${status.index}.thue.tenHhTxt"></span></td>
						<td class="text-right" style="width: 180px;"><span
							id="hangHoaDs${status.index}.thue.tongTien"></span></td>
						<td><form:input cssClass="form-control"
								path="hangHoaDs[${status.index}].thueSuatNk" /></td>
						<td><form:input cssClass="form-control"
								path="hangHoaDs[${status.index}].tkThueNk.soTien.soTien" /> <input
							type="hidden" name="hangHoaDs[${status.index}].tkThueNk.soDu"
							value="1" /></td>
						<td><form:select cssClass="form-control"
								path="hangHoaDs[${status.index}].tkThueNk.loaiTaiKhoan.maTk">
								<form:option value="" label=""></form:option>
								<form:options items="${loaiTaiKhoanNkDs}" itemValue="maTk"
									itemLabel="maTenTk" />
							</form:select> <form:errors
								path="hangHoaDs[${status.index}].tkThueNk.loaiTaiKhoan.maTk"
								cssClass="error" /></td>
						<td><form:input cssClass="form-control"
								path="hangHoaDs[${status.index}].thueSuatTtdb" /></td>
						<td><form:input cssClass="form-control"
								path="hangHoaDs[${status.index}].tkThueTtdb.soTien.soTien" /> <input
							type="hidden" name="hangHoaDs[${status.index}].tkThueTtdb.soDu"
							value="1" /></td>
						<td><form:select cssClass="form-control"
								path="hangHoaDs[${status.index}].tkThueTtdb.loaiTaiKhoan.maTk">
								<form:option value="" label=""></form:option>
								<form:options items="${loaiTaiKhoanTtdbDs}" itemValue="maTk"
									itemLabel="maTenTk" />
							</form:select> <form:errors
								path="hangHoaDs[${status.index}].tkThueTtdb.loaiTaiKhoan.maTk"
								cssClass="error" /></td>
						<%-- <td><form:input cssClass="form-control"
								path="hangHoaDs[${status.index}].thueSuatGtgt" /></td>
						<td><form:input cssClass="form-control"
								path="hangHoaDs[${status.index}].tkThueGtgt.soTien.soTien" /> <input
							type="hidden" name="hangHoaDs[${status.index}].tkThueGtgt.soDu"
							value="1" /></td>
						<td><form:select cssClass="form-control"
								path="hangHoaDs[${status.index}].tkThueGtgt.loaiTaiKhoan.maTk">
								<form:option value="" label=""></form:option>
								<form:option value="33312"
									label="33312 - Thuế GTGT hàng nhập khẩu"></form:option>
							</form:select> <form:errors
								path="hangHoaDs[${status.index}].tkThueGtgt.loaiTaiKhoan.maTk"
								cssClass="error" /></td> --%>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div id="chiPhi" class="tab-pane fade">
		<table class="table table-bordered table-hover text-center hanghoa"
			id="chiPhiTbl">
			<thead>
				<tr>
					<th class="text-center">Vật tư, hàng hóa</th>
					<th class="text-center">Đơn vị tính</th>
					<th class="text-center">Số lượng</th>
					<th class="text-center">Giá mua</th>
					<th class="text-center">Thành tiền</th>
					<th class="text-center">Kho</th>
					<th class="text-center">TK Kho (Nợ)</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${mainFinanceForm.hangHoaDs}" var="hangHoa"
					varStatus="status">
					<tr id="chiPhi${status.index}">
						<td style="width: 250px;"><span
							id="hangHoaDs${status.index}.chiPhi.tenHhTxt"></span></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
