<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://cewolf.sourceforge.net/taglib/cewolf.tld"
	prefix="cewolf"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<jsp:useBean id="currentRatioBarChart"
	type="com.idi.finance.charts.KpiBarChart" scope="request" />
<jsp:useBean id="currentRatioLineChart"
	type="com.idi.finance.charts.KpiLineChart" scope="request" />
<jsp:useBean id="currentRatioChartProcessor"
	type="com.idi.finance.charts.KpiChartProcessor" scope="request" />

<div class="text-center">
	<cewolf:overlaidchart id="currentRatiosChart"
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
	</cewolf:overlaidchart>
	<cewolf:img chartid="currentRatiosChart" height="400" width="820"
		renderer="/cewolf">
		<cewolf:map tooltipgeneratorid="currentRatioBarChart"></cewolf:map>
		<cewolf:map tooltipgeneratorid="currentRatioLineChart"></cewolf:map>
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
				<c:forEach items="${currentRatios}" var="currentRatio">
					<th>Tháng <fmt:formatDate value="${currentRatio.value.period}"
							pattern="M" type="Date" dateStyle="SHORT" />
					</th>
				</c:forEach>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>Số liệu</td>
				<c:forEach items="${currentRatios}" var="currentRatio">
					<td><fmt:formatNumber value="${currentRatio.value.value}"
							maxFractionDigits="3" minFractionDigits="3"></fmt:formatNumber></td>
				</c:forEach>
			</tr>
			<tr>
				<td>Tiêu chuẩn</td>
				<c:forEach items="${currentRatios}" var="currentRatio">
					<td><c:out value="${currentRatio.value.threshold}"></c:out></td>
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
							maxFractionDigits="0" minFractionDigits="0"></fmt:formatNumber></td>
				</c:forEach>
			</tr>
		</tbody>
	</table>
</div>
