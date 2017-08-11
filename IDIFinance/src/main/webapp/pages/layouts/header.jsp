<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<div class="navbar navbar-default">
	<div class="navbar-header">
		<a class="navbar-brand" href="${url}"><img height="80px"
			src="${url}/public/images/IDI-logo.png" /></a>
	</div>
	<div class="page-header text-center">
		<h2>${mainTitle}</h2>
	</div>
</div>