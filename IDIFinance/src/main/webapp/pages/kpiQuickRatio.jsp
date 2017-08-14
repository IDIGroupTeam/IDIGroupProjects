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

<jsp:useBean id="quickRatioBarChart"
	type="com.idi.finance.charts.quickratio.QuickRatioBarChart"
	scope="request" />
<jsp:useBean id="quickRatioLineChart"
	type="com.idi.finance.charts.quickratio.QuickRatioLineChart"
	scope="request" />
<jsp:useBean id="quickRatioChartProcessor"
	type="com.idi.finance.charts.quickratio.QuickRatioChartProcessor"
	scope="request" />
</head>
<body>
	<div class="text-center">
		<cewolf:overlaidchart id="quickRatiosChart"
			title="Khả năng thanh toán nhanh" yaxislabel="Giá trị"
			xaxislabel="Năm ${year}" xaxistype="date" yaxistype="number"
			type="overlaidxy" showlegend="true">
			<cewolf:colorpaint color="#99CCFF" />

			<cewolf:plot type="xyshapesandlines">
				<cewolf:data>
					<cewolf:producer id="quickRatioLineChart" />
				</cewolf:data>
			</cewolf:plot>

			<cewolf:plot type="xyverticalbar">
				<cewolf:data>
					<cewolf:producer id="quickRatioBarChart" />
				</cewolf:data>
			</cewolf:plot>

			<cewolf:chartpostprocessor id="quickRatioChartProcessor">
				<cewolf:param name="fontSize" value="16"></cewolf:param>
			</cewolf:chartpostprocessor>
		</cewolf:overlaidchart>
		<cewolf:img chartid="quickRatiosChart" height="400" width="820"
			renderer="/cewolf">
			<cewolf:map tooltipgeneratorid="quickRatioBarChart"></cewolf:map>
			<cewolf:map tooltipgeneratorid="quickRatioLineChart"></cewolf:map>
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
					<c:forEach items="${quickRatios}" var="quickRatio">
						<th>Tháng <fmt:formatDate value="${quickRatio.value.period}"
								pattern="M" type="Date" dateStyle="SHORT" />
						</th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Số liệu</td>
					<c:forEach items="${quickRatios}" var="quickRatio">
						<td><fmt:formatNumber value="${quickRatio.value.value}"
								maxFractionDigits="3" minFractionDigits="3"></fmt:formatNumber>
						</td>
					</c:forEach>
				</tr>
				<tr>
					<td>Tiêu chuẩn</td>
					<c:forEach items="${quickRatios}" var="quickRatio">
						<td><c:out value="${quickRatio.value.thresold}"></c:out></td>
					</c:forEach>
				</tr>
				<tr>
					<td>Đánh giá</td>
					<c:forEach items="${quickRatios}" var="quickRatio">
						<td><c:choose>
								<c:when test="${quickRatio.value.evaluate == 1}">Tốt</c:when>
								<c:when test="${quickRatio.value.evaluate == -1}">Không tốt</c:when>
								<c:otherwise></c:otherwise>
							</c:choose></td>
					</c:forEach>
				</tr>
				<tr>
					<td>Tài sản ngắn hạn</td>
					<c:forEach items="${quickRatios}" var="quickRatio">
						<td><fmt:formatNumber
								value="${quickRatio.value.currentAsset}" maxFractionDigits="0"
								minFractionDigits="0"></fmt:formatNumber></td>
					</c:forEach>
				</tr>
				<tr>
					<td>Hàng tồn kho</td>
					<c:forEach items="${quickRatios}" var="quickRatio">
						<td><fmt:formatNumber value="${quickRatio.value.inventory}"
								maxFractionDigits="0" minFractionDigits="0"></fmt:formatNumber>
						</td>
					</c:forEach>
				</tr>
				<tr>
					<td>Nợ ngắn hạn</td>
					<c:forEach items="${quickRatios}" var="quickRatio">
						<td><fmt:formatNumber
								value="${quickRatio.value.currentLiability}"
								maxFractionDigits="0" minFractionDigits="0"></fmt:formatNumber>
						</td>
					</c:forEach>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>