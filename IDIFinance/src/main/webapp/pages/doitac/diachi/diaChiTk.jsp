<%@page import="com.idi.finance.bean.diachi.VungDiaChi"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<script type="text/javascript">
	//Shorthand for $( document ).ready()
	$(function() {
		var tc = "${VungDiaChi.TC}";
		;
		$("#mainFinanceForm").addClass("form-horizontal");
		$("#submitBut").click(function() {
			$("#pageIndex").val(1);
			$("#totalPages").val(0);
			$("#totalRecords").val(0);

			$("#mainFinanceForm").attr("action", "${url}/diachi/danhsach");
			$("#mainFinanceForm").prop("method", "POST");
			$("#mainFinanceForm").submit();
		});

		$("#mien")
				.change(
						function() {
							val = $(this).val();
							var url = "";

							if (val == tc) {
								url = "${url}/diachi/thanhpho";
							} else {
								url = "${url}/diachi/vung";
							}

							// Lấy dữ liệu của thành phố
							$
									.ajax({
										dataType : "json",
										url : url,
										data : {
											maVung : val
										},
										success : function(obj) {
											thanhPhoSel = "<option value='"+tc+"'>Tất cả</option>";
											for (i = 0; i < obj.length; i++) {
												thanhPhoSel += "<option value='"+obj[i].maDc+"'>"
														+ obj[i].cap.tenCapDc
														+ " "
														+ obj[i].tenDc
														+ "</option>";
											}
											$("#thanhPho").html(thanhPhoSel);
										}
									});

							// Xóa dữ liệu quận
							quanSel = "<option value='"+tc+"'>Tất cả</option>";
							$("#quan").html(quanSel);
						});

		$("#thanhPho")
				.change(
						function() {
							// Lấy dữ liệu của quận
							val = $(this).val();

							if (val == 'TC') {
								quanSel = "<option value='"+tc+"'>Tất cả</option>";
								$("#quan").html(quanSel);
							} else {
								// Lấy dữ liệu của quận
								$
										.ajax({
											dataType : "json",
											url : "${url}/diachi/vung",
											data : {
												maVung : val
											},
											success : function(obj) {
												quanSel = "<option value='"+tc+"'>Tất cả</option>";
												for (i = 0; i < obj.length; i++) {
													quanSel += "<option value='"+obj[i].maDc+"'>"
															+ obj[i].cap.tenCapDc
															+ " "
															+ obj[i].tenDc
															+ "</option>";
												}
												$("#quan").html(quanSel);
											}
										});
							}
						});
	});
</script>

<div class="panel panel-default with-nav-tabs">
	<div class="panel-heading">
		<h4>Tìm kiếm</h4>
	</div>
	<div class="panel-body">
		<div class="form-group">
			<label for="mien">Miền:</label>
			<div class="input-group date smallform pull-right">
				<form:select path="mien" cssClass="form-control" multiple="false">
					<form:option value="${VungDiaChi.TC}">Tất cả</form:option>
					<c:forEach items="${mienDs}" var="mien">
						<form:option value="${mien.maDc}">${mien.cap.tenCapDc}&nbsp;${mien.tenDc}</form:option>
					</c:forEach>
				</form:select>
			</div>
		</div>

		<div class="form-group">
			<label for="thanhPho">Thành phố:</label>
			<div class="input-group date smallform pull-right">
				<form:select path="thanhPho" cssClass="form-control"
					multiple="false">
					<form:option value="${VungDiaChi.TC}">Tất cả</form:option>
					<c:forEach items="${thanhPhoDs}" var="thanhPho">
						<form:option value="${thanhPho.maDc}">${thanhPho.cap.tenCapDc}&nbsp;${thanhPho.tenDc}</form:option>
					</c:forEach>
				</form:select>
			</div>
		</div>

		<div class="form-group">
			<label for="quan">Quận:</label>
			<div class="input-group date smallform pull-right">
				<form:select path="quan" cssClass="form-control" multiple="false">
					<form:option value="${VungDiaChi.TC}">Tất cả</form:option>
					<c:forEach items="${quanDs}" var="quan">
						<form:option value="${quan.maDc}">${quan.cap.tenCapDc}&nbsp;${quan.tenDc}</form:option>
					</c:forEach>
				</form:select>
			</div>
		</div>
	</div>
	<div class="panel-footer">
		<button id="submitBut" type="button" class="btn btn-info btn-sm">Tìm
			kiếm</button>
	</div>
</div>