<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="${url}"><img height="80px"
				src="/IDICRM/public/images/IDI-logo.png" /></a>
		</div>

		<ul class="nav navbar-nav navbar-right">
			<li><a href="/IDIHome">Home</a></li>
			<li><a href="#news">News</a></li>
			<li><a href="#contact">Contact</a></li>
			<li><a href="#about">About</a></li>
			<li><a href="#" class="login"><span
					class="glyphicon glyphicon-log-out"></span> Đăng Xuất </a></li>
		</ul>
		<div class="topnav navbar-right" id="myTopnav"></div>
       
	</div>
  <form class="form-inline navbar-right" action="#">
    <input class="form-control mr-sm-2" type="text" placeholder="Xin điền thông tin " >
    <button class="btn btn-success" type="submit">Tìm kiếm</button>
  </form>
</nav>