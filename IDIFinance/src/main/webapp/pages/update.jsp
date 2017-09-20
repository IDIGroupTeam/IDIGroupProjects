<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<script type="text/javascript">
	//Shorthand for $( document ).ready()
	$(function() {
		// Khởi tạo action/method cho mainFinanceForm form
		$("#mainFinanceForm").attr("action", "${url}/luutrudulieu");
		$("#mainFinanceForm").attr("method", "POST");
		$("#mainFinanceForm").attr("enctype", "multipart/form-data");
		
		$("#uploadBut").click(function() {
			$("#mainFinanceForm").submit();
		});
	});
</script>

<h4>Chọn file dữ liệu tài chính excel muốn cập nhật</h4>

<span style="color: red;">${comment}</span>
<br />
<input name="file" type="file" />
<br />
<input id="uploadBut" class="btn btn-info btn-sm" type="submit"
	value="Cập nhật" />

