<%@page import="com.idi.finance.bean.taikhoan.LoaiTaiKhoan"%>
<%@page import="com.idi.finance.bean.kyketoan.KyKeToan"%>
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
		$("#KyKeToanBut").click(
				function() {
					$("#mainFinanceForm").attr("method", "POST");
					$("#mainFinanceForm")
							.attr("enctype", "multipart/form-data");

					$("#mainFinanceForm").attr("action",
							"${url}/kyketoan/soduky/luusoduky");
					$("#mainFinanceForm").submit();
				});
	});
</script>

<h4>Nhập số dư kỳ kế toán</h4>
<span>Chọn file excel dữ liệu</span>
<br />
<form:hidden path="kyKeToan.maKyKt" />
<span style="color: red;">${comment}</span>
<br />
<input name="soDuKyFile" type="file" />
<br />

<a href="${url}/kyketoan/xem/${mainFinanceForm.kyKeToan.maKyKt}"
	class="btn btn-info btn-sm">Hủy</a>

<input id="KyKeToanBut" class="btn btn-info btn-sm" type="submit"
	value="Cập nhật" />
