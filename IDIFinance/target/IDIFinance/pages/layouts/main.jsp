<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<decorator:head />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${url}/public/css/bootstrap.min.css" />
<link rel="stylesheet" href="${url}/public/css/style.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="${url}/public/js/bootstrap.min.js"></script>

<title><decorator:title default="Tập đoàn IDI" /></title>

</head>
<body>
	<div class="container">
		<%@ include file="header.jsp"%>

		<div class="container-fluid">
			<div class="row content">
				<div class="col-sm-3 sidenav" style="padding: 2px;">
					<%@ include file="navigator.jsp"%>
				</div>
				<div class="col-sm-9" style="padding: 2px;">
					<decorator:body />
				</div>
			</div>
		</div>

		<%@ include file="footer.jsp"%>
	</div>
</body>
</html>