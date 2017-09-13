<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="panel panel-default">
	<div class="panel-heading">
		<h4>Nghiệp vụ</h4>
	</div>
	<div class="panel-body">
		<ul class="nav nav-pills nav-stacked">
			<li><a href="/IDIFinance">Quản lý tài chính</a></li>
			<li><a id="hr" href="/IDIHR">Quản lý nhân sự</a>			
				<%-- <c:import url="/pages/layouts/subnav_hr.jsp"></c:import>	 --%> 		
			</li>
			<li><a href="#">Quản lý khách hàng</a></li>
			<li><a href="#">Quản lý kho</a></li>
			<li><a href="#">Quản lý nguồn vốn</a></li>
		</ul>
	</div>
</div>
