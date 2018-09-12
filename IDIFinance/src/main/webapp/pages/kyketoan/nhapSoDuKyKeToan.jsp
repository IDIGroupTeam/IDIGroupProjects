<%@page import="com.idi.finance.bean.taikhoan.LoaiTaiKhoan"%>
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
		$("#mainFinanceForm").attr("method", "POST");
		$("#mainFinanceForm").attr("enctype", "multipart/form-data");

		$("#KyKeToanBut").click(
				function() {
					$("#mainFinanceForm").attr("action",
							"${url}/kyketoan/soduky/luusoduky");
					$("#mainFinanceForm").submit();
				});
	});
</script>

<h4>Nhập số dư kỳ kế toán</h4>
<span>Chọn file excel dữ liệu</span>
<br />

<span style="color: red;">${balanceAssetComment}</span>
<br />
<input name="file" type="file" />
<br />
<input id="KyKeToanBut" class="btn btn-info btn-sm" type="submit"
	value="Cập nhật" />
