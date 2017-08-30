<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="decorator"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<decorator:head />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="${url}/public/css/bootstrap.min.css" />
<link rel="stylesheet" href="${url}/public/css/style.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="${url}/public/js/bootstrap.min.js"></script>

<title><decorator:title default="Tập đoàn IDI" /></title>
<script type="text/javascript">
	// Shorthand for $( document ).ready()
	$(function() {
		var currentAction = "${action}";
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
	});
</script>
</head>
<body>
	<!-- <div class="container"> -->
	<%@ include file="header.jsp"%>

	<div class="container-fluid">
		<div class="row content">
			<div class="col-sm-3 sidenav" style="padding: 2px;">
				<%@ include file="navigator.jsp"%>
			</div>
			<div class="col-sm-9" style="padding: 2px;">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4>Tài chính</h4>
					</div>
					<div class="panel-body">
						<table width="100%">
							<tr>
								<td align="left" width="40%">
									<div class="form-group">
										<label for="sel1">Chỉ số KPI</label> <select id="kpiList"
											class="form-control" id="sel1">
											<option value="kntttucthoi">Khả năng thanh toán tức thời</option>
											<option value="knttnhanh">Khả năng thanh toán nhanh</option>
											<option value="knttbangtien">Khả năng thanh toán bằng tiền</option>
											<option value="vqkhoanphaithu">Vòng quay khoản phải thu</option>
											<option value="kttienbinhquan">Kỳ thu tiền bình quân</option>
											<option value="vqhangtonkho_sosach">Vòng quay hàng tồn kho theo giá trị sổ sách</option>
											<option value="vqhangtonkho_thitruong">Vòng quay hàng tồn kho theo giá thị trường</option>
											<option value="sntonkhobinhquan_sosach">Số ngày tồn kho bình quân theo giá trị sổ sách</option>
											<option value="sntonkhobinhquan_thitruong">Số ngày hàng tồn kho  bình quân theo giá thị trường</option>
											<option value="chukyhoatdong_sosach">Chu kỳ hoạt động của doanh nghiệp theo giá trị sổ sách</option>
											<option value="chukyhoatdong_thitruong">Chu kỳ hoạt động của doanh nghiệp theo giá thị trường</option>
											<option value="vqkhoanphaitra">Vòng quay khoản phải trả</option>
											<option value="kyphaitrabinhquan">Kỳ phải trả bình quân</option>
											<option value="ckluanchuyentien_sosach">Chu kỳ luân chuyển tiền theo giá trị sổ sách</option>
											<option value="ckluanchuyentien_thitruong">Chu kỳ luân chuyển tiền theo giá trị thị trường</option>
											<option value="knttlaivay">Khả năng thanh toán lãi vay</option>
											<option value="hssdtongtaisan">Hiệu suất sử dụng tổng tài sản (Vòng quay tổng tài sản)</option>
											<option value="hssdtaisancodinh">Hiệu suất sử dụng tài sản cố định</option>
											<option value="hssdvonluudong">Hiệu suất sử dụng trên vòng quay vốn lưu động</option>
											<option value="tysuatloinhuangop">Tỷ suất lợi nhuận gộp (Lợi nhuận gộp biên)</option>
											<option value="tysuatloinhuanrong">Tỷ suất lợi nhuận ròng (Lợi nhuận ròng biên)</option>
											<option value="hesono">Hệ số nợ</option>
											<option value="donbaytaichinh">Đòn bẩy tài chính</option>
										</select>
									</div>
								</td>
								<td align="right"><a href="${url}/capnhat"
									title="Cập nhật dữ liệu"><span
										class="glyphicon glyphicon-pencil btn-lg"></span></a></td>
							</tr>
						</table>
						<hr />
						<decorator:body />
					</div>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="footer.jsp"%>
	<!-- </div> -->
</body>
</html>