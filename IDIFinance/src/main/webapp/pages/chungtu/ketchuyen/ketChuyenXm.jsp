<%@page import="com.idi.finance.bean.kyketoan.KyKeToan"%>
<%@page import="com.idi.finance.bean.taikhoan.LoaiTaiKhoan"%>
<%@page import="com.idi.finance.bean.doituong.DoiTuong"%>
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
		var ngayLap = $.trim($("#ngayHt").html());
		$("#xoaNut")
				.click(
						function() {
							xoa = $(this).attr("href");
							BootstrapDialog
									.confirm({
										title : 'Xác nhận',
										message : 'Bạn muốn xóa kết chuyển này không ?<br/><b>Mã số:</b> ${chungTu.soCt}<br /> <b>Kết chuyển đến ngày:</b> '
												+ ngayLap
												+ ' <br /> <b>Lý do:</b> ${chungTu.lyDo}',
										type : 'type-info',
										closable : true,
										draggable : true,
										btnCancelLabel : 'Không',
										btnOKLabel : 'Có',
										callback : function(result) {
											if (result) {
												$("#mainFinanceForm").attr(
														"action", xoa);
												$("#mainFinanceForm").attr(
														"method", "GET");
												$("#mainFinanceForm").submit();
											}
										}
									});

							return false;
						});
	});
</script>

<div>
	<span class="pull-left heading4">KẾT CHUYỂN</span>
</div>
<br />
<hr />

<div class="row form-group">
	<label class="control-label col-sm-2" for="soCt">Số kết chuyển
		dự kiến:</label>
	<div class="col-sm-4">${chungTu.loaiCt}${chungTu.soCt}</div>

	<label class="control-label col-sm-2" for=ngayHt>Ngày kết
		chuyển:</label>
	<div class="col-sm-4">
		<span id="ngayHt"><fmt:formatDate value="${chungTu.ngayHt}"
				pattern="dd/M/yyyy" type="Date" dateStyle="SHORT" /></span>
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="lyDo">Tên kết
		chuyển:(*)</label>
	<div class="col-sm-4">${chungTu.lyDo}</div>
</div>
<div class="table-responsive row form-group">
	<table id="ketChuyenTbl"
		class="table table-bordered table-hover text-center dinhkhoan">
		<thead>
			<tr>
				<th class="text-center" style="width: 10px;">Thứ tự</th>
				<th class="text-left">Bút toán kết chuyển</th>
				<th class="text-center" style="width: 100px;">Tài khoản nợ</th>
				<th class="text-center" style="width: 100px;">Tài khoản có</th>
				<th class="text-center" style="width: 100px;">Số tiền</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${not empty chungTu.kcbtDs}">
					<c:forEach begin="0" end="${chungTu.kcbtDs.size()-1}"
						varStatus="status">
						<tr id="ketChuyen${status.index}">
							<td style="width: 10px;">${chungTu.kcbtDs[status.index].thuTu}</td>
							<td class="text-left">${chungTu.kcbtDs[status.index].tenKc}</td>
							<td style="width: 100px;">
								${chungTu.kcbtDs[status.index].taiKhoanNo.loaiTaiKhoan.maTk}</td>
							<td style="width: 100px;">
								${chungTu.kcbtDs[status.index].taiKhoanCo.loaiTaiKhoan.maTk}</td>
							<td style="width: 100px;" class="text-right"><fmt:formatNumber
									value="${chungTu.kcbtDs[status.index].taiKhoanNo.soTien.giaTri}"
									maxFractionDigits="2"></fmt:formatNumber></td>
						</tr>
					</c:forEach>
				</c:when>
			</c:choose>
		</tbody>
	</table>
</div>

<div class="row form-group">
	<div class="col-sm-12">
		<a href="${url}/chungtu/ketchuyen/danhsach"
			class="btn btn-info btn-sm">Danh sách kết chuyển</a>

		<c:choose>
			<c:when
				test="${kyKeToan!=null && kyKeToan.trangThai!= KyKeToan.DONG}">
				<a id="xoaNut" href="${url}/chungtu/ketchuyen/xoa/${chungTu.maCt}"
					class="btn btn-info btn-sm">Xóa</a>
				<a href="${url}/chungtu/ketchuyen/taomoi/${chungTu.tinhChatCt}"
					class="btn btn-info btn-sm">Tạo mới </a>
			</c:when>
		</c:choose>
	</div>
</div>