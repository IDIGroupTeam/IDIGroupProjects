<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="${url}/public/css/bootstrap.min.css" />
<link rel="stylesheet" href="${url}/public/css/paneltab.css" />
<link rel="stylesheet" href="${url}/public/css/bootstrap-dialog.min.css" />
<link rel="stylesheet"
	href="${url}/public/css/bootstrap-multiselect.css" />
<link rel="stylesheet" href="${url}/public/css/bootcomplete.css" />
<link rel="stylesheet" href="${url}/public/css/tabledit.css" />
<link rel="stylesheet"
	href="${url}/public/css/bootstrap-datetimepicker.min.css" />
<link rel="stylesheet" href="${url}/public/css/style.css" />

<script src="${url}/public/js/jquery.min.js"></script>
<script src="${url}/public/js/script.js"></script>
<script src="${url}/public/js/bootstrap.min.js"></script>
<script src="${url}/public/js/bootstrap-dialog.min.js"></script>
<script src="${url}/public/js/bootstrap-multiselect.js"></script>
<script src="${url}/public/js/jquery.bootcomplete.js"></script>
<script src="${url}/public/js/jquery.tabledit.js"></script>
<script src="${url}/public/js/jquery.treetable.js"></script>
<script src="${url}/public/js/bootstrap-datetimepicker.min.js"></script>
<script src="${url}/public/js/bootstrap-datetimepicker.vi.js"></script>
<script src="${url}/public/js/jquery.formatCurrency-1.4.0.js"></script>
<script src="${url}/public/js/jquery.formatCurrency.vi-VN.js"></script>
<script src="${url}/public/js/accounting.min.js"></script>

<title>Tập đoàn IDI - <tiles:getAsString name="title" /></title>
<script type="text/javascript">
	// Shorthand for $( document ).ready()
	$(function() {
		var currentTab = "${tab}";
		$(".nav-tabs li").removeClass("active");
		$("#" + currentTab).addClass("active");
		$("#" + currentTab).parent().parent().addClass("active");
	});
</script>
</head>
<body>
	<tiles:insertAttribute name="header" />
	<form:form id="mainFinanceForm" action="" method="GET"
		modelAttribute="mainFinanceForm" acceptCharset="UTF-8">
		<div class="container-fluid">
			<div class="row content">
				<div class="col-sm-2 sidenav" style="padding: 2px;">
					<tiles:insertAttribute name="navigator" />
					<tiles:insertAttribute name="search" />
				</div>
				<div class="col-sm-10" style="padding: 2px;">
					<div class="panel panel-default with-nav-tabs">
						<div class="panel-heading">
							<tiles:insertAttribute name="tabnav" />
						</div>
						<div class="panel-body tab-content">
							<div class="tab-pane active">
								<tiles:insertAttribute name="body" />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form:form>
	<tiles:insertAttribute name="footer" />
</body>
</html>