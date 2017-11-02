<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<script type="text/javascript">
	//Shorthand for $( document ).ready()
	$(function() {
		// Khởi tạo action/method cho mainFinanceForm form
		$("#mainFinanceForm").attr("method", "POST");
		$("#mainFinanceForm").attr("enctype", "multipart/form-data");

		$("#balanceAssetBut").click(function() {
			$("#mainFinanceForm").attr("action", "${url}/luutrudulieu");
			$("#mainFinanceForm").submit();
		});

		$("#taiKhoanBut").click(function() {
			$("#mainFinanceForm").attr("action", "${url}/luuTaiKhoan");
			$("#mainFinanceForm").submit();
		});
	});
</script>

<h4>Cập nhật bảng cân đối kế toán, danh sách biểu đồ KPI</h4>
<span>Chọn file dữ liệu tài chính excel muốn cập nhật</span>
<br />

<span style="color: red;">${balanceAssetComment}</span>
<br />
<input name="balanceAssetFile" type="file" />
<br />
<input id="balanceAssetBut" class="btn btn-info btn-sm" type="submit"
	value="Cập nhật" />

<hr>

<h4>Cập nhật danh mục tài khoản</h4>
<span>Chọn file dữ liệu tài chính excel muốn cập nhật</span>
<br />
<span style="color: red;">${taiKhoanComment}</span>
<br />
<input name="taiKhoanFile" type="file" />
<br />
<input id="taiKhoanBut" class="btn btn-info btn-sm" type="submit"
	value="Cập nhật" />


