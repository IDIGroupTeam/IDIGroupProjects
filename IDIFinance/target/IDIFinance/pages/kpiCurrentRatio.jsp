<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://cewolf.sourceforge.net/taglib/cewolf.tld"
	prefix="cewolf"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<script type="text/javascript">
	
</script>
<title>Tập đoàn IDI</title>

<jsp:useBean id="currentRatioBarChart"
	type="com.idi.finance.charts.currentratio.CurrentRatioBarChart"
	scope="request" />
<jsp:useBean id="currentRatioLineChart"
	type="com.idi.finance.charts.currentratio.CurrentRatioLineChart"
	scope="request" />
<jsp:useBean id="currentRatioChartProcessor"
	type="com.idi.finance.charts.currentratio.CurrentRatioChartProcessor"
	scope="request" />
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
			<td align="right"><a href="${url}/capnhat"
				title="Cập nhật dữ liệu"><span
					class="glyphicon glyphicon-pencil btn-lg"></span></a></td>
		</tr>
		<tr>
			<td colspan="2"><cewolf:overlaidchart id="currentRatiosChart"
					title="Khả năng thanh toán tức thời" yaxislabel="Giá trị"
					xaxislabel="Năm ${year}" xaxistype="date" yaxistype="number"
					type="overlaidxy" showlegend="true">
					<cewolf:colorpaint color="#99CCFF" />

					<cewolf:plot type="xyshapesandlines">
						<cewolf:data>
							<cewolf:producer id="currentRatioLineChart" />
						</cewolf:data>
					</cewolf:plot>

					<cewolf:plot type="xyverticalbar">
						<cewolf:data>
							<cewolf:producer id="currentRatioBarChart" />
						</cewolf:data>
					</cewolf:plot>

					<cewolf:chartpostprocessor id="currentRatioChartProcessor">
						<cewolf:param name="fontSize" value="16"></cewolf:param>
					</cewolf:chartpostprocessor>
				</cewolf:overlaidchart> <cewolf:img chartid="currentRatiosChart" height="400" width="820"
					renderer="/cewolf">
					<cewolf:map tooltipgeneratorid="currentRatioBarChart"></cewolf:map>
				</cewolf:img></td>
		</tr>
	</table>
	<hr />
	<div class="table-responsive">
		<table width="100%" class="table table-bordered table-hover">
			<caption>Số liệu chi tiết</caption>
			<thead>
				<tr>
					<th></th>
					<c:forEach items="${currentRatios}" var="currentRatio">
						<th>Tháng <fmt:formatDate
								value="${currentRatio.value.period}" pattern="M" type="Date"
								dateStyle="SHORT" />
						</th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Số liệu</td>
					<c:forEach items="${currentRatios}" var="currentRatio">
						<td><fmt:formatNumber value="${currentRatio.value.value}"
								maxFractionDigits="3" minFractionDigits="3"></fmt:formatNumber>
						</td>
					</c:forEach>
				</tr>
				<tr>
					<td>Tiêu chuẩn</td>
					<c:forEach items="${currentRatios}" var="currentRatio">
						<td><c:out value="${currentRatio.value.thresold}"></c:out></td>
					</c:forEach>
				</tr>
				<tr>
					<td>Đánh giá</td>
					<c:forEach items="${currentRatios}" var="currentRatio">
						<td><c:choose>
								<c:when test="${currentRatio.value.evaluate == 1}">Tốt</c:when>
								<c:when test="${currentRatio.value.evaluate == -1}">Không tốt</c:when>
								<c:otherwise></c:otherwise>
							</c:choose></td>
					</c:forEach>
				</tr>
				<tr>
					<td>Tài sản ngắn hạn</td>
					<c:forEach items="${currentRatios}" var="currentRatio">
						<td><fmt:formatNumber
								value="${currentRatio.value.currentAsset}" maxFractionDigits="0"
								minFractionDigits="0"></fmt:formatNumber></td>
					</c:forEach>
				</tr>
				<tr>
					<td>Nợ ngắn hạn</td>
					<c:forEach items="${currentRatios}" var="currentRatio">
						<td><fmt:formatNumber
								value="${currentRatio.value.currentLiability}"
								maxFractionDigits="0" minFractionDigits="0"></fmt:formatNumber>
						</td>
					</c:forEach>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>