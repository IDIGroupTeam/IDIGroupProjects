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
		// Khởi tạo action/method cho mainFinanceForm form
		$("#mainFinanceForm").attr("method", "POST");
		$("#mainFinanceForm").attr("enctype", "multipart/form-data");

		$("#vungBut").click(function() {
			$("#mainFinanceForm").attr("action", "${url}/diachi/luucapnhat");
			$("#mainFinanceForm").submit();
		});
	});
</script>

<h4>Cập nhật danh sách vùng miền</h4>
<span>Chọn file dữ liệu tài chính excel muốn cập nhật</span>
<br />

<span style="color: red;">${comment}</span>
<br />
<input name="vungFile" type="file" />
<br />
<input id="vungBut" class="btn btn-info btn-sm" type="submit"
	value="Cập nhật" />
