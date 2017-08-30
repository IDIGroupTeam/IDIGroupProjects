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
<jsp:useBean id="fixedAssetsTurnoverBarChart"
	type="com.idi.finance.charts.KpiBarChart" scope="request" />
<jsp:useBean id="fixedAssetsTurnoverLineChart"
	type="com.idi.finance.charts.KpiLineChart" scope="request" />
<jsp:useBean id="fixedAssetsTurnoverChartProcessor"
	type="com.idi.finance.charts.KpiChartProcessor" scope="request" />
</head>
<body>
	<div class="text-center">
		<cewolf:overlaidchart id="fixedAssetsTurnoversChart"
			title="Hiệu suất sử dụng tài sản cố định" yaxislabel="Giá trị"
			xaxislabel="Năm ${year}" xaxistype="date" yaxistype="number"
			type="overlaidxy" showlegend="true">
			<cewolf:colorpaint color="#99CCFF" />

			<cewolf:plot type="xyshapesandlines">
				<cewolf:data>
					<cewolf:producer id="fixedAssetsTurnoverLineChart" />
				</cewolf:data>
			</cewolf:plot>

			<cewolf:plot type="xyverticalbar">
				<cewolf:data>
					<cewolf:producer id="fixedAssetsTurnoverBarChart" />
				</cewolf:data>
			</cewolf:plot>

			<cewolf:chartpostprocessor id="fixedAssetsTurnoverChartProcessor">
				<cewolf:param name="fontSize" value="16"></cewolf:param>
			</cewolf:chartpostprocessor>
		</cewolf:overlaidchart>
		<cewolf:img chartid="fixedAssetsTurnoversChart" height="400"
			width="820" renderer="/cewolf">
			<cewolf:map tooltipgeneratorid="fixedAssetsTurnoverBarChart"></cewolf:map>
			<cewolf:map tooltipgeneratorid="fixedAssetsTurnoverLineChart"></cewolf:map>
		</cewolf:img>
	</div>
	<hr />
	<div class="table-responsive">
		<table width="100%" class="table table-bordered table-hover">
			<caption>
				<b>Số liệu chi tiết</b>
			</caption>
			<thead>
				<tr>
					<th></th>
					<c:forEach items="${fixedAssetsTurnovers}"
						var="fixedAssetsTurnover">
						<th>Tháng <fmt:formatDate
								value="${fixedAssetsTurnover.value.period}" pattern="M"
								type="Date" dateStyle="SHORT" />
						</th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Số liệu</td>
					<c:forEach items="${fixedAssetsTurnovers}"
						var="fixedAssetsTurnover">
						<td><fmt:formatNumber
								value="${fixedAssetsTurnover.value.value}" maxFractionDigits="3"
								minFractionDigits="3"></fmt:formatNumber></td>
					</c:forEach>
				</tr>
				<tr>
					<td>Tiêu chuẩn</td>
					<c:forEach items="${fixedAssetsTurnovers}"
						var="fixedAssetsTurnover">
						<td><c:out value="${fixedAssetsTurnover.value.threshold}"></c:out></td>
					</c:forEach>
				</tr>
				<tr>
					<td>Đánh giá</td>
					<c:forEach items="${fixedAssetsTurnovers}"
						var="fixedAssetsTurnover">
						<td><c:choose>
								<c:when test="${fixedAssetsTurnover.value.evaluate == 1}">Tốt</c:when>
								<c:when test="${fixedAssetsTurnover.value.evaluate == -1}">Không tốt</c:when>
								<c:otherwise></c:otherwise>
							</c:choose></td>
					</c:forEach>
				</tr>
				<tr>
					<td>Doanh thu thuần</td>
					<c:forEach items="${fixedAssetsTurnovers}"
						var="fixedAssetsTurnover">
						<td><fmt:formatNumber
								value="${fixedAssetsTurnover.value.totalOperatingRevenue}"
								maxFractionDigits="0" minFractionDigits="0"></fmt:formatNumber></td>
					</c:forEach>
				</tr>
				<tr>
					<td>Tài sản cố định đầu kỳ</td>
					<c:forEach items="${fixedAssetsTurnovers}"
						var="fixedAssetsTurnover">
						<td><fmt:formatNumber
								value="${fixedAssetsTurnover.value.startReceivable}"
								maxFractionDigits="0" minFractionDigits="0"></fmt:formatNumber>
						</td>
					</c:forEach>
				</tr>
				<tr>
					<td>Tài sản cố định cuối kỳ</td>
					<c:forEach items="${fixedAssetsTurnovers}"
						var="fixedAssetsTurnover">
						<td><fmt:formatNumber
								value="${fixedAssetsTurnover.value.endReceivable}"
								maxFractionDigits="0" minFractionDigits="0"></fmt:formatNumber>
						</td>
					</c:forEach>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>