<%@page import="com.idi.finance.bean.hanghoa.HangHoa"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<script type="text/javascript">
function xacNhanXoa(maDv){
	BootstrapDialog.confirm({
		title : 'Xác nhận',
		message : 'Bạn muốn xóa hàng hóa này không ?<br/>Mã hàng hóa: '+maDv,
		type : 'type-info',
		closable : true,
		draggable : true,
		btnCancelLabel : 'Không',
		btnOKLabel : 'Có',
		callback : function(result) {
			if (result) {
				$("#mainFinanceForm").attr(
						"action", "${url}/hanghoa/xoa/"+maDv);
				$("#mainFinanceForm").attr(
						"method", "GET");
				$("#mainFinanceForm").submit();
			}
		}
	});

	return false;
}

//Shorthand for $( document ).ready()
$(function() {
	// Khởi tạo action/method cho mainFinanceForm form
	
});
</script>

<h4>Chi tiết hàng hóa</h4>
<hr />

<div class="row form-group">
	<label class="control-label col-sm-2" for="tenHh">Tên hàng hóa:</label>
	<div class="col-sm-4">${hangHoa.tenHh}</div>

	<label class="control-label col-sm-2" for="kyHieuHh">Mã hàng
		hóa:</label>
	<div class="col-sm-4">${hangHoa.kyHieuHh}</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="donVi.maDv">Đơn vị
		tính:</label>
	<div class="col-sm-4">${hangHoa.donVi.tenDv}</div>

	<label class="control-label col-sm-2" for="nhomHh.maNhomHh">Nhóm
		hàng hóa:</label>
	<div class="col-sm-4">${hangHoa.nhomHh.tenNhomHh}</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="khoMd.maKho">Kho mặc
		định:</label>
	<div class="col-sm-4">${hangHoa.khoMd.tenKho}</div>

	<label class="control-label col-sm-2" for="tinhChat">Tính chất</label>
	<div class="col-sm-4">
		<c:choose>
			<c:when test="${hangHoa.tinhChat == HangHoa.TINH_CHAT_VTHH}">
								Vật tư hàng hóa
							</c:when>
			<c:when test="${hangHoa.tinhChat == HangHoa.TINH_CHAT_DV}">
								Dịch vụ
							</c:when>
			<c:when test="${hangHoa.tinhChat == HangHoa.TINH_CHAT_TP}">
								Thành phẩm
							</c:when>
		</c:choose>
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="tkKhoMd.maTk">Tài
		khoản kho:</label>
	<div class="col-sm-4">${hangHoa.tkKhoMd.maTk}</div>

	<label class="control-label col-sm-2" for="thoiHanBh">Thời hạn
		bảo hành:</label>
	<div class="col-sm-4">${hangHoa.thoiHanBh}&nbsp;tháng</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="tkDoanhThuMd.maTk">Tài
		khoản doanh thu:</label>
	<div class="col-sm-4">${hangHoa.tkDoanhThuMd.maTk}</div>

	<label class="control-label col-sm-2" for="tyLeCktmMd">Tỷ lệ
		chiết khấu:</label>
	<div class="col-sm-4">
		<fmt:formatNumber value="${hangHoa.tyLeCktmMd}" maxFractionDigits="2"></fmt:formatNumber>
		%
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="tkChiPhiMd.maTk">Tài
		khoản chi phí:</label>
	<div class="col-sm-4">${hangHoa.tkChiPhiMd.maTk}</div>

	<label class="control-label col-sm-2" for="thueSuatGtgtMd">Thuế
		xuất GTGT:</label>
	<div class="col-sm-4">
		<fmt:formatNumber value="${hangHoa.thueSuatGtgtMd}"
			maxFractionDigits="2"></fmt:formatNumber>
		%
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="tkChietKhauMd.maTk">Tài
		khoản chiết khấu:</label>
	<div class="col-sm-4">${hangHoa.tkChietKhauMd.maTk}</div>

	<label class="control-label col-sm-2" for="thueSuatXkMd">Thuế
		xuất xuất khẩu:</label>
	<div class="col-sm-4">
		<fmt:formatNumber value="${hangHoa.thueSuatXkMd}"
			maxFractionDigits="2"></fmt:formatNumber>
		%
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="tkGiamGiaMd.maTk">Tài
		khoản giảm giá:</label>
	<div class="col-sm-4">${hangHoa.tkGiamGiaMd.maTk}</div>

	<label class="control-label col-sm-2" for="thueSuatNkMd">Thuế
		suất nhập khẩu:</label>
	<div class="col-sm-4">
		<fmt:formatNumber value="${hangHoa.thueSuatNkMd}"
			maxFractionDigits="2"></fmt:formatNumber>
		%
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="tkTraLaiMd.maTk">Tài
		khoản trả lại:</label>
	<div class="col-sm-4">${hangHoa.tkTraLaiMd.maTk}</div>

	<label class="control-label col-sm-2" for="thueSuatTtdbMd">Thuế
		suất TTDB:</label>
	<div class="col-sm-4">
		<fmt:formatNumber value="${hangHoa.thueSuatTtdbMd}"
			maxFractionDigits="2"></fmt:formatNumber>
		%
	</div>
</div>

<div class="row form-group">
	<div class="col-sm-12">
		<a href="${url}/hanghoa/danhsach" class="btn btn-info btn-sm">Danh
			sách hàng hóa</a> <a href="${url}/hanghoa/sua/${hangHoa.maHh}"
			class="btn btn-info btn-sm" title="Sửa">Sửa</a>
		<c:if test="${hangHoa.xoa}">
			<a href="${url}/hanghoa/xoa/${hangHoa.maHh}"
				class="btn btn-info btn-sm" title="Xóa"
				onclick="return xacNhanXoa(${hangHoa.maHh});">Xóa</span>
			</a>
		</c:if>
	</div>
</div>
