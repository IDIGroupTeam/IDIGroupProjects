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
		$("#mainFinanceForm").attr("action", "${url}/nganhang/taikhoan/luu");
		$("#mainFinanceForm").attr("method", "POST");

		$("#luuNut").click(function() {
			$("#mainFinanceForm").submit();
		});
	});
</script>

<h4>SỬA TÀI KHOẢN NGÂN HÀNG</h4>
<hr />

<form:hidden path="maTk" />
<div class="row form-group">
	<label class="control-label col-sm-2" for="soTk">Số tài khoản
		(*)</label>
	<div class="col-sm-4">
		<form:input path="soTk" cssClass="form-control"
			placeholder="Số tài khoản" />
		<form:errors path="soTk" cssClass="error" />
	</div>

	<label class="control-label col-sm-2" for="chuTk">Chủ tài khoản</label>
	<div class="col-sm-4">
		<form:input path="chuTk" cssClass="form-control"
			placeholder="Chủ tài khoản" />
		<form:errors path="chuTk" cssClass="error" />
	</div>
</div>

<div class="row">
	<div class="col-sm-6">
		<div class="row form-group">
			<label class="control-label col-sm-4" for="nganHang.maNh">Ngân
				hàng (*)</label>
			<div class="col-sm-8">
				<form:select path="nganHang.maNh" cssClass="form-control">
					<form:option value="0">----- Ngân hàng -----</form:option>
					<form:options items="${nganHangDs}" itemLabel="tenVt"
						itemValue="maNh" />
				</form:select>
				<form:errors path="nganHang.maNh" cssClass="error" />
			</div>
		</div>

		<div class="row form-group">
			<label class="control-label col-sm-4" for="chiNhanh">Chi
				nhánh</label>
			<div class="col-sm-8">
				<form:input path="chiNhanh" cssClass="form-control"
					placeholder="Chi nhánh" />
			</div>
		</div>
	</div>

	<div class="form-group">
		<label class="control-label col-sm-2" for="diaChiCn">Địa chỉ
			chi nhánh</label>
		<div class="col-sm-4">
			<form:textarea path="diaChiCn" rows="2" cols="2"
				cssClass="form-control" placeholder="Địa chỉ" />
		</div>
	</div>
</div>
<hr />

<div class="row">
	<div class="col-sm-12">
		<a href="${url}/nganhang/taikhoan/danhsach"
			class="btn btn-info btn-sm">Hủy</a>
		<button id="luuNut" type="submit" class="btn btn-info btn-sm">Lưu</button>
	</div>
</div>

