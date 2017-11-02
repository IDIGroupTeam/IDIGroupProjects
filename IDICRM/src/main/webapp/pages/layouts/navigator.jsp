<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<div class="panel panel-default with-nav-tabs">
	<div class="panel-heading">
		<h4>Nghiệp vụ</h4>
	</div>
	<div class="panel-body">
		<ul class="nav nav-pills nav-stacked">
			<li><a href="/IDIFinance">Quản lý Tài chính & Kế toán</a></li>
			<li><a href="/IDIHR">Quản lý Nhân sự</a></li>
			<li class="active"><a href="/IDICRM">Quản lý Khách hàng</a></li>
			<li> <a href="${url}/thongbao">Quản lý Rượu -Bia</a></li>
			<li><a href="${url}/thongbao">Quản lý Nguồn vốn</a></li>
		</ul>
	</div>
</div>
