<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<h4>Chọn file dữ liệu tài chính excel muốn cập nhật</h4>
<form method="POST" action="luutrudulieu" enctype="multipart/form-data">
	<span style="color: red;">${comment}</span><br /> <input name="file"
		type="file" /> <br /> <input class="btn btn-info btn-sm"
		type="submit" value="Cập nhật" />
</form>
