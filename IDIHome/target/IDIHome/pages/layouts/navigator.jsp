<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<div class="panel panel-default">
	<div class="panel-heading">
		<h4>Nghiệp vụ</h4>
	</div>
	<div class="panel-body">
		<ul class="nav nav-pills nav-stacked">
			<li><a href="${url}">Tài chính</a></li>
			<li><a href="#">Nhân sự</a></li>
			<li><a href="#">Kinh doanh</a></li>
		</ul>
	</div>
</div>
