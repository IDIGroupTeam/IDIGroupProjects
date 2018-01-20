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
			<li><a href="/IDIHome">Trang chủ</a></li>
			<li><a href="/IDIHome/tintuc">Tin tức</a></li>
			<li><a href="/IDIHome/lienhe">Liên hệ</a></li>
			<li><a href="/IDIHome/vechungtoi">Về chúng tôi</a></li>
			<li><a href="#" class="login"><span
					class="glyphicon glyphicon-log-out"></span> Đăng Xuất </a></li>
		</ul>
		<div class="topnav navbar-right" id="myTopnav"></div>

	</div>
<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
  <form class="form-inline navbar-right" action="#">
    <input class="form-control mr-sm-2" type="text" placeholder="Xin điền thông tin " >
    <button class="btn btn-success" type="submit">Tìm kiếm</button>
  </form>
</nav>

	
</nav>