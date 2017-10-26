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
<link rel="stylesheet" href="${url}/public/css/style.css" />

<script src="${url}/public/js/jquery.min.js"></script>
<script src="${url}/public/js/bootstrap.min.js"></script>
<script src="${url}/public/js/bootstrap-dialog.min.js"></script>
<script src="${url}/public/js/bootstrap-multiselect.js"></script>
<script src="${url}/public/js/jquery.bootcomplete.js"></script>
<script src="${url}/public/js/jquery.tabledit.js"></script>
<script src="${url}/public/js/script.js"></script>

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
							<ul class="nav nav-tabs nav-pills nav-justified">
								<li class="dropdown"><a class="dropdown-toggle"
									data-toggle="dropdown" href="#">Biểu đồ KPI<span
										class="caret"></span>
								</a>
									<ul class="dropdown-menu">
										<c:forEach items="${kpiGroups}" var="kpiGroup">
											<li id="tab${kpiGroup.groupId}"><a
												href="${url}/bieudo/${kpiGroup.groupId}">${kpiGroup.groupName}</a></li>
										</c:forEach>
										<li id="tabQLBD"><a href="${url}/quanlybieudo">Quản
												lý biểu đồ</a></li>
									</ul></li>
								<li class="dropdown"><a class="dropdown-toggle"
									data-toggle="dropdown" href="#">Chứng từ kế toán<span
										class="caret"></span>
								</a>
									<ul class="dropdown-menu">
										<li id="tabCTPT"><a href="${url}/danhsachphieuthu">Phiếu
												thu</a></li>
										<li id="tabCTPC"><a href="${url}/danhsachphieuchi">Phiếu
												chi</a></li>
										<li id="tabCTBN"><a href="${url}/danhsachbaono">Báo
												nợ</a></li>
										<li id="tabCTBC"><a href="${url}/danhsachbaoco">Báo
												có</a></li>
									</ul></li>
								<li class="dropdown"><a class="dropdown-toggle"
									data-toggle="dropdown" href="#">Sổ kế toán<span
										class="caret"></span>
								</a>
									<ul class="dropdown-menu">
										<li id="tabSKTNKC"><a href="${url}/soketoan/nhatkychung">Nhật
												ký chung</a></li>
										<li id="tabSKTSC"><a href="${url}/soketoan/socai">Sổ
												cái</a></li>
										<li id="tabSKTSTM"><a href="${url}/soketoan/sotienmat">Sổ
												tiền mặt</a></li>
										<li id="tabSKTSCN"><a href="${url}/soketoan/socongno">Sổ
												công nợ</a></li>
										<li><a href="#">Quản lý sổ</a></li>
									</ul></li>
								<li class="dropdown"><a class="dropdown-toggle"
									data-toggle="dropdown" href="#">Báo cáo tài chính<span
										class="caret"></span>
								</a>
									<ul class="dropdown-menu">
										<li id="tabBCDKT"><a href="${url}/candoiketoan">Bảng
												cân đối kế toán</a></li>
										<li><a href="#">Bảng cân đối phát sinh</a></li>
										<li><a href="#">Bảng kết quả SXKD</a></li>
										<li><a href="#">Bảng lưu chuyển tiền tệ</a></li>

									</ul></li>
								<li class="dropdown"><a class="dropdown-toggle"
									data-toggle="dropdown" href="#">Quản trị<span class="caret"></span>
								</a>
									<ul class="dropdown-menu">
										<li id="tabDSNT"><a href="#">Danh sách ngoại tệ</a></li>
										<li id="tabDMTK"><a href="${url}/danhsachtaikhoan">Danh
												sách tài khoản</a></li>

										<li id="tabDSNCC"><a href="${url}/danhsachnhacungcap">Danh
												sách nhà cung cấp</a></li>
										<li id="tabDSKH"><a href="${url}/danhsachkhachhang">Danh
												sách khách hàng</a></li>

										<li id="tabCNDL"><a href="${url}/capnhatdulieu">Cập
												nhật dữ liệu</a></li>
									</ul></li>
							</ul>
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