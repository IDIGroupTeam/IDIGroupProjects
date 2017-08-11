<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
	
</script>
<title>Tập đoàn IDI</title>
</head>
<body>
	<table width="100%">
		<tr>
			<td align="left" width="40%">
				<div class="form-group">
					<label for="sel1">Chỉ số KPI</label> <select name="kpiList"
						class="form-control" id="sel1">
						<option value="1">Khả năng thanh toán tức thời</option>
						<option value="2">Khả năng thanh toán nhanh</option>
						<option value="3">Khả năng thanh toán thành tiền</option>
						<option value="4">Vòng quay khoản phải thu</option>
						<option value="5">Vòng quay hàng tồn kho theo giá thị trường</option>
						<option value="6">Vòng quay hàng tồn kho theo giá trị sổ sách</option>
					</select>
				</div>
			</td>
			<td align="right"><a href="${url}/update"
				title="Cập nhật dữ liệu"><span
					class="glyphicon glyphicon-pencil btn-lg"></span></a></td>
		</tr>
	</table>
</body>
</html>