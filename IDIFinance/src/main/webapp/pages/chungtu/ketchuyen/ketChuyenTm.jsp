<%@page import="com.idi.finance.bean.taikhoan.LoaiTaiKhoan"%><%@ page
	language="java" contentType="text/html; charset=utf-8"
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
		$("#mainFinanceForm").attr("action", "${url}/chungtu/ketchuyen/luu");
		$("#mainFinanceForm").attr("method", "POST");

		$("#submitBt").click(function() {
			$("#mainFinanceForm").submit();
		});

		function dangKy() {
			$("#tamThoi").click(function() {
				$(":checkbox").prop("checked", $(this).prop("checked"));
			});

			$("input[name^='kcbtDs'][name$='chon']").click(function() {
				allChecked = true;
				$("input[name^='kcbtDs'][name$='chon']").each(function() {
					if (!$(this).prop("checked")) {
						allChecked = false;
					}
				});
				$("#tamThoi").prop("checked", allChecked);
			});
		}

		function khoiTao() {
			dangKy();
		}
		khoiTao();
	});
</script>

<h4>KẾT CHUYỂN</h4>
<hr />
<form:hidden path="loaiCt" />
<form:hidden path="tinhChatCt" />
<form:hidden path="ngayLap" />

<div class="row form-group">
	<label class="control-label col-sm-2" for="soCt">Số kết chuyển
		dự kiến:</label>
	<div class="col-sm-4">
		${mainFinanceForm.loaiCt}${mainFinanceForm.soCt}
		<form:hidden path="soCt" />
	</div>

	<label class="control-label col-sm-2" for=ngayHt>Ngày kết
		chuyển:</label>
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
	<label class="control-label col-sm-2" for="lyDo">Tên kết
		chuyển:(*)</label>
	<div class="col-sm-4">
		<form:input path="lyDo" placeholder="Tên kết chuyển"
			cssClass="form-control" />
		<br />
		<form:errors path="lyDo" cssClass="error" />
	</div>
</div>

<form:errors path="kcbtDs" cssClass="error" />
<div class="table-responsive row form-group">
	<table id="ketChuyenTbl"
		class="table table-bordered table-hover text-center dinhkhoan">
		<thead>
			<tr>
				<th style="width: 10px;"><input id="tamThoi" type="checkbox"
					class="form-control" checked="checked"></th>
				<th class="text-center" style="width: 10px;">Thứ tự</th>
				<th class="text-left">Bút toán kết chuyển</th>
				<th class="text-center" style="width: 100px;">Tài khoản nợ</th>
				<th class="text-center" style="width: 100px;">Tài khoản có</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${not empty mainFinanceForm.kcbtDs}">
					<c:forEach begin="0" end="${mainFinanceForm.kcbtDs.size()-1}"
						varStatus="status">
						<tr id="ketChuyen${status.index}">
							<td style="width: 10px;"><form:checkbox
									cssClass="form-control" path="kcbtDs[${status.index}].chon" />
								<form:hidden path="kcbtDs[${status.index}].maKc" /> <form:hidden
									path="kcbtDs[${status.index}].tenKc" /> <form:hidden
									path="kcbtDs[${status.index}].thuTu" /> <form:hidden
									path="kcbtDs[${status.index}].congThuc" /></td>
							<td style="width: 10px;">${mainFinanceForm.kcbtDs[status.index].thuTu}</td>
							<td class="text-left">${mainFinanceForm.kcbtDs[status.index].tenKc}</td>
							<td style="width: 100px;">
								${mainFinanceForm.kcbtDs[status.index].taiKhoanNo.loaiTaiKhoan.maTk}
								<form:hidden
									path="kcbtDs[${status.index}].taiKhoanNo.loaiTaiKhoan.maTk" />
								<form:hidden path="kcbtDs[${status.index}].taiKhoanNo.soDu" />
							</td>
							<td style="width: 100px;">
								${mainFinanceForm.kcbtDs[status.index].taiKhoanCo.loaiTaiKhoan.maTk}
								<form:hidden
									path="kcbtDs[${status.index}].taiKhoanCo.loaiTaiKhoan.maTk" />
								<form:hidden path="kcbtDs[${status.index}].taiKhoanCo.soDu" />
							</td>
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
			class="btn btn-info btn-sm">Hủy</a>
		<button id="submitBt" type="submit" class="btn btn-info btn-sm">Lưu</button>
	</div>
</div>
