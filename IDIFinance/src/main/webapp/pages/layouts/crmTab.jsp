<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<ul class="main-tab nav nav-tabs nav-pills nav-justified">
	<li id="tabDSKH"><a href="${url}/danhsachkhachhang">Danh sách
			khách hàng</a></li>
	<li id="tabDSNCC"><a href="${url}/danhsachnhacungcap">Danh
			sách nhà cung cấp</a></li>
	<li class="dropdown"><a class="dropdown-toggle"
		data-toggle="dropdown" href="#">Địa chỉ<span class="caret"></span>
	</a>
		<ul class="dropdown-menu">
			<li id="tabDCDSDC"><a href="${url}/diachi/danhsach">Danh
					sách vùng miền</a></li>
			<li id="tabDCCNDC"><a href="${url}/diachi/capnhat">Cập nhật
					vùng miền</a></li>
		</ul></li>
</ul>