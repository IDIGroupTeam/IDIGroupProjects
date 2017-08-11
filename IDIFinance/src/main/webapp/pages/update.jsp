<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Tập đoàn IDI - Cập nhật dữ liệu tài chính</title>
</head>
<body>
	<div class="panel panel-default">
		<div class="panel-heading">
			<h4>Cập nhật dữ liệu tài chính</h4>
		</div>
		<div class="panel-body">
			<p>Chọn file dữ liệu tài chính excel muốn cập nhật</p>
			<form method="POST" action="save" enctype="multipart/form-data">
				<span style="color: red;">${comment}</span><br /> <input
					name="file" type="file" /> <br /> <input
					class="btn btn-info btn-sm" type="submit" value="Cập nhật" />
			</form>
		</div>
	</div>
</body>
</html>