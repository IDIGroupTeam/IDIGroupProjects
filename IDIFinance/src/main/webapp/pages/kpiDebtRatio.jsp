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
<jsp:useBean id="debtRatioBarChart"
	type="com.idi.finance.charts.debtratio.DebtRatioBarChart"
	scope="request" />
<jsp:useBean id="debtRatioLineChart"
	type="com.idi.finance.charts.debtratio.DebtRatioLineChart"
	scope="request" />
<jsp:useBean id="debtRatioChartProcessor"
	type="com.idi.finance.charts.debtratio.DebtRatioChartProcessor"
	scope="request" />
</head>
<body>
	<div class="text-center">
		<cewolf:overlaidchart id="debtRatiosChart" title="Hệ số nợ"
			yaxislabel="Giá trị" xaxislabel="Năm ${year}" xaxistype="date"
			yaxistype="number" type="overlaidxy" showlegend="true">
			<cewolf:colorpaint color="#99CCFF" />

			<cewolf:plot type="xyshapesandlines">
				<cewolf:data>
					<cewolf:producer id="debtRatioLineChart" />
				</cewolf:data>
			</cewolf:plot>

			<cewolf:plot type="xyverticalbar">
				<cewolf:data>
					<cewolf:producer id="debtRatioBarChart" />
				</cewolf:data>
			</cewolf:plot>

			<cewolf:chartpostprocessor id="debtRatioChartProcessor">
				<cewolf:param name="fontSize" value="16"></cewolf:param>
			</cewolf:chartpostprocessor>
		</cewolf:overlaidchart>
		<cewolf:img chartid="debtRatiosChart" height="400" width="820"
			renderer="/cewolf">
			<cewolf:map tooltipgeneratorid="debtRatioBarChart"></cewolf:map>
			<cewolf:map tooltipgeneratorid="debtRatioLineChart"></cewolf:map>
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
					<c:forEach items="${debtRatios}" var="debtRatio">
						<th>Tháng <fmt:formatDate value="${debtRatio.value.period}"
								pattern="M" type="Date" dateStyle="SHORT" />
						</th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Số liệu</td>
					<c:forEach items="${debtRatios}" var="debtRatio">
						<td><fmt:formatNumber value="${debtRatio.value.value}"
								maxFractionDigits="3" minFractionDigits="3"></fmt:formatNumber>
						</td>
					</c:forEach>
				</tr>
				<tr>
					<td>Tiêu chuẩn</td>
					<c:forEach items="${debtRatios}" var="debtRatio">
						<td><c:out value="${debtRatio.value.thresold}"></c:out></td>
					</c:forEach>
				</tr>
				<tr>
					<td>Đánh giá</td>
					<c:forEach items="${debtRatios}" var="debtRatio">
						<td><c:choose>
								<c:when test="${debtRatio.value.evaluate == 1}">Tốt</c:when>
								<c:when test="${debtRatio.value.evaluate == -1}">Không tốt</c:when>
								<c:otherwise></c:otherwise>
							</c:choose></td>
					</c:forEach>
				</tr>
				<tr>
					<td>Tổng nợ</td>
					<c:forEach items="${debtRatios}" var="debtRatio">
						<td><fmt:formatNumber value="${debtRatio.value.totalDebt}"
								maxFractionDigits="0" minFractionDigits="0"></fmt:formatNumber></td>
					</c:forEach>
				</tr>
				<tr>
					<td>Tổng tài sản</td>
					<c:forEach items="${debtRatios}" var="debtRatio">
						<td><fmt:formatNumber value="${debtRatio.value.totalAsset}"
								maxFractionDigits="0" minFractionDigits="0"></fmt:formatNumber>
						</td>
					</c:forEach>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>