<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="${url}"><img height="80px"
				src="${url}/public/images/IDI-logo.png" /></a>
		</div>

		<ul class="nav navbar-nav navbar-right">
			<li><a href="#" class="login"><span
					class="glyphicon glyphicon-log-out"></span> Đăng Xuất </a></li>
		</ul>

		<!-- <form class="navbar-form navbar-right">
			<div class="input-group">
				<input type="text" class="form-control" placeholder="Tìm kiếm">
				<div class="input-group-btn">
					<button class="btn btn-default" type="submit">
						<i class="glyphicon glyphicon-search"></i>
					</button>
				</div>
			</div>
		</form> -->
	</div>
</nav>