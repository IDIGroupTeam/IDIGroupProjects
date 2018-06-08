<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>

<title>Gửi Báo cáo công việc</title>
<style>
table {
	font-family: arial, sans-serif;
	border-collapse: collapse;
	width: 100%;
}

td, th {
	border: 1px solid #E8E3E3;
	text-align: left;
	padding: 8px;
}

tr:nth-child(even) {
	background-color: #E8E3E3;
}
</style>
</head>
<body>
	<a href="${url}/"><button class="btn btn-lg btn-primary btn-sm"> Quay lại dach sách công việc</button></a>
	<a href="${url}/prepareReport"><button
			class="btn btn-primary btn-sm">Lựa chọn lại thông tin cần
			báo cáo</button></a>
	<br />
	<br />	
	<div class="alert alert-success">
		Báo cáo công việc đã được gửi thành công ... 
	</div>	
</body>
</html>