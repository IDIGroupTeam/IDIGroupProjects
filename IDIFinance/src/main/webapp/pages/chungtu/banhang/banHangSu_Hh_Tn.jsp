<%@page import="com.idi.finance.bean.taikhoan.LoaiTaiKhoan"%>
<%@page import="com.idi.finance.bean.chungtu.DoiTuong"%>
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
	<li><a data-toggle="tab" href="#giaVon">Giá vốn</a></li>
	<li><a data-toggle="tab" href="#thue">Thuế</a></li>
</ul>
<div class="tab-content table-responsive sub-content">
	<div id="hangTien" class="tab-pane fade in active">
		<table class="table table-bordered table-hover text-center hanghoa"
			id="hangTienTbl">
			<thead>
				<tr>
					<th class="text-center">Mã vật tư, hàng hóa</th>
					<th class="text-center">Vật tư, hàng hóa</th>
					<th class="text-center">Đơn vị tính</th>
					<th class="text-center">Số lượng</th>
					<th class="text-center">Giá bán</th>
					<th class="text-center">Thành tiền</th>
					<th class="text-center">TK công nợ (Nợ)</th>
					<th class="text-center">TK Doanh thu (Có)</th>
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
								path="hangHoaDs[${status.index}].soLuong" cssClass="error" /></td>
						<td><form:input cssClass="form-control"
								path="hangHoaDs[${status.index}].donGia.soTien" /> <form:errors
								path="hangHoaDs[${status.index}].donGia.soTien" cssClass="error" /></td>
						<td class="text-right" style="width: 180px;"><span
							id="hangHoaDs${status.index}.hangTien.tongTien"></span></td>
						<td><input type="hidden"
							name="hangHoaDs[${status.index}].tkThanhtoan.soDu" value="-1" />
							<form:hidden path="hangHoaDs[${status.index}].tkThanhtoan.maNvkt" />
							<form:select cssClass="form-control"
								path="hangHoaDs[${status.index}].tkThanhtoan.loaiTaiKhoan.maTk">
								<form:option value="" label=""></form:option>
								<form:option value="1111" label="1111 - Tiền Việt Nam"></form:option>
								<form:option value="1112" label="1112 - Ngoại tệ"></form:option>
								<form:option value="1121" label="1121 - Tiền Việt Nam"></form:option>
								<form:option value="1122" label="1122 - Ngoại tệ"></form:option>
								<form:option value="131" label="131 - Phải thu khách hàng"></form:option>
								<form:option value="131" label="131 - Phải thu khách hàng"></form:option>
								<form:option value="1311"
									label="1311 - Phải thu khách hàng - ngắn hạn"></form:option>
								<form:option value="1312"
									label="1312- Phải thu khách hàng - dài hạn"></form:option>
							</form:select> <form:errors
								path="hangHoaDs[${status.index}].tkThanhtoan.loaiTaiKhoan.maTk"
								cssClass="error" /></td>
						<td><input type="hidden"
							name="hangHoaDs[${status.index}].tkDoanhThu.soDu" value="1" /> <form:hidden
								path="hangHoaDs[${status.index}].tkDoanhThu.maNvkt" /> <form:select
								cssClass="form-control"
								path="hangHoaDs[${status.index}].tkDoanhThu.loaiTaiKhoan.maTk">
								<form:option value="" label=""></form:option>
								<form:option value="5111" label="5111 - Doanh thu bán hàng hóa"></form:option>
							</form:select> <form:errors
								path="hangHoaDs[${status.index}].tkDoanhThu.loaiTaiKhoan.maTk"
								cssClass="error" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div id="giaVon" class="tab-pane fade">
		<table class="table table-bordered table-hover text-center hanghoa"
			id="chiPhiTbl">
			<thead>
				<tr>
					<th class="text-center">Vật tư, hàng hóa</th>
					<th class="text-center">Đơn vị tính</th>
					<th class="text-center">Số lượng</th>
					<th class="text-center">Giá vốn</th>
					<th class="text-center">Thành tiền</th>
					<th class="text-center">TK Giá vốn (Nợ)</th>
					<th class="text-center">TK Kho (Có)</th>
					<th class="text-center">Kho</th>
					<th class="text-center">Lô</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${mainFinanceForm.hangHoaDs}" var="hangHoa"
					varStatus="status">
					<tr id="chiPhi${status.index}">
						<td style="width: 250px;"><span
							id="hangHoaDs${status.index}.giaVon.tenHhTxt"></span></td>
						<td><span id="hangHoaDs${status.index}.giaVon.tenDvTxt"></span></td>
						<td><span id="hangHoaDs${status.index}.giaVon.soLuongTxt"></span></td>
						<td><form:select cssClass="form-control"
								path="hangHoaDs[${status.index}].giaKho.maGia">
								<form:option value="0" label=""></form:option>
								<form:options items="${hangHoa.donGiaDs}" itemValue="maGia"
									itemLabel="donGia.soTien" cssClass="giaVon" />
							</form:select> <form:errors path="hangHoaDs[${status.index}].giaKho.maGia"
								cssClass="error" /> <form:hidden
								path="hangHoaDs[${status.index}].giaKho.soTien" /></td>
						<td><span id="hangHoaDs${status.index}.giaVon.thanhTienTxt"></span></td>
						<td><input type="hidden"
							name="hangHoaDs[${status.index}].tkGiaVon.soDu" value="-1" /> <form:hidden
								path="hangHoaDs[${status.index}].tkGiaVon.maNvkt" /> <form:select
								cssClass="form-control"
								path="hangHoaDs[${status.index}].tkGiaVon.loaiTaiKhoan.maTk">
								<form:option value="" label=""></form:option>
								<form:option value="632" label="632 - Giá vốn bán hàng"></form:option>
							</form:select> <form:errors
								path="hangHoaDs[${status.index}].tkGiaVon.loaiTaiKhoan.maTk"
								cssClass="error" /></td>
						<td><input type="hidden"
							name="hangHoaDs[${status.index}].tkKho.soDu" value="1" /> <form:hidden
								path="hangHoaDs[${status.index}].tkKho.maNvkt" /> <form:select
								cssClass="form-control"
								path="hangHoaDs[${status.index}].tkKho.loaiTaiKhoan.maTk">
								<form:option value="" label=""></form:option>
								<form:option value="152" label="152 - Nguyên liệu, vật liệu"></form:option>
								<form:option value="156" label="156 - Hàng hóa"></form:option>
								<form:option value="1561" label="1561 - Giá mua hàng hóa"></form:option>
							</form:select> <form:errors
								path="hangHoaDs[${status.index}].tkKho.loaiTaiKhoan.maTk"
								cssClass="error" /></td>
						<td style="width: 200px;"><form:select
								cssClass="form-control"
								path="hangHoaDs[${status.index}].kho.maKho">
								<form:option value="0" label=""></form:option>
								<form:options items="${khoBaiDs}" itemValue="maKho"
									itemLabel="tenKho" />
							</form:select> <form:errors path="hangHoaDs[${status.index}].kho.maKho"
								cssClass="error" /></td>
						<td></td>
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
					<th class="text-center" colspan="3">Thuế giá trị gia tăng</th>
				</tr>
				<tr>
					<th class="text-center">Thuế suất (%)</th>
					<th class="text-center">Tiền thuế (VND)</th>
					<th class="text-center">Tài khoản (Có)</th>
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
								path="hangHoaDs[${status.index}].thueSuatGtgt" /></td>
						<td><form:input cssClass="form-control"
								path="hangHoaDs[${status.index}].tkThueGtgt.soTien.soTien" /> <input
							type="hidden" name="hangHoaDs[${status.index}].tkThueGtgt.soDu"
							value="1" /></td>
						<td><form:select cssClass="form-control"
								path="hangHoaDs[${status.index}].tkThueGtgt.loaiTaiKhoan.maTk">
								<form:option value="" label=""></form:option>
								<form:option value="33312" label="33311 - Thuế GTGT đầu ra"></form:option>
							</form:select> <form:errors
								path="hangHoaDs[${status.index}].tkThueGtgt.loaiTaiKhoan.maTk"
								cssClass="error" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
