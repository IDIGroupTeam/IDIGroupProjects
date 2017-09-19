<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="${url}/public/css/bootstrap.min.css" />
<link rel="stylesheet" href="${url}/public/css/paneltab.css" />
<link rel="stylesheet"
	href="${url}/public/css/bootstrap-multiselect.css" />
<link rel="stylesheet" href="${url}/public/css/style.css" />

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="${url}/public/js/bootstrap.min.js"></script>
<script src="${url}/public/js/bootstrap-multiselect.js"></script>

<title>Tập đoàn IDI - <tiles:getAsString name="title" /></title>
<script type="text/javascript">
	// Shorthand for $( document ).ready()
	$(function() {
		var currentAction = "${action}";
		var currentTab = "${tab}";

		$("#kpiList")
				.on(
						"change",
						function() {
							var urlContextPath = "${url}";
							var selectChart = document
									.getElementById("kpiList");
							if (selectChart != null) {
								var selected = selectChart.selectedIndex;
								var action = document
										.getElementsByTagName("option")[selected].value;
								window.open(urlContextPath + "/" + action,
										"_self");
							}
						});

		var selectChart = document.getElementById("kpiList");
		if (selectChart != null) {
			var selected = selectChart.selectedIndex;
			var options = document.getElementsByTagName("option");
			for (i = 0; i < options.length; i++) {
				if (options[i].value == currentAction) {
					selectChart.selectedIndex = i;
					break;
				}
			}
		}

		$("li").removeClass("active");
		$("#" + currentTab).addClass("active");
	});
</script>
</head>
<body>
	<tiles:insertAttribute name="header" />

	<div class="container-fluid">
		<div class="row content">
			<div class="col-sm-2 sidenav" style="padding: 2px;">
				<tiles:insertAttribute name="navigator" />
				<tiles:insertAttribute name="search" />
			</div>
			<div class="col-sm-10" style="padding: 2px;">
				<div class="panel panel-default with-nav-tabs">
					<div class="panel-heading">
						<ul class="nav nav-tabs nav-pills nav-justified">
							<li id="tabKNTT"><a href="kntttucthoi">Khả năng thanh
									toán</a></li>
							<li id="tabKNHD"><a href="vqkhoanphaithu">Khả năng hoạt
									động</a></li>
							<li id="tabKNSL"><a href="hssdtongtaisan">Khả năng sinh
									lời</a></li>
							<li id="tabKNCDN"><a href="hesono">Khả năng cân đối nợ</a></li>
							<li id="tabBCDKT"><a href="bangcandoiketoan">Bảng cân
									đối kế toán</a></li>
							<li id="tabCNDL"><a href="capnhat">Cập nhật dữ liệu</a></li>
						</ul>
					</div>
					<div class="panel-body tab-content">
						<div class="tab-pane active">
							<tiles:insertAttribute name="kpilist" />
							<tiles:insertAttribute name="body" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<tiles:insertAttribute name="footer" />
</body>
</html>