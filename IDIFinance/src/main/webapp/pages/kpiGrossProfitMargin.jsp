<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://cewolf.sourceforge.net/taglib/cewolf.tld"
	prefix="cewolf"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<jsp:useBean id="grossProfitMarginBarChart"
	type="com.idi.finance.charts.KpiBarChart" scope="request" />
<jsp:useBean id="grossProfitMarginLineChart"
	type="com.idi.finance.charts.KpiLineChart" scope="request" />
<jsp:useBean id="grossProfitMarginChartProcessor"
	type="com.idi.finance.charts.KpiChartProcessor" scope="request" />

<div class="text-center">
	<cewolf:overlaidchart id="grossProfitMarginsChart"
		title="Tỷ suất lợi nhuận gộp (Lợi nhuận gộp biên)"
		yaxislabel="Giá trị" xaxislabel="Năm ${year}" xaxistype="date"
		yaxistype="number" type="overlaidxy" showlegend="true">
		<cewolf:colorpaint color="#99CCFF" />

		<cewolf:plot type="xyshapesandlines">
			<cewolf:data>
				<cewolf:producer id="grossProfitMarginLineChart" />
			</cewolf:data>
		</cewolf:plot>

		<cewolf:plot type="xyverticalbar">
			<cewolf:data>
				<cewolf:producer id="grossProfitMarginBarChart" />
			</cewolf:data>
		</cewolf:plot>

		<cewolf:chartpostprocessor id="grossProfitMarginChartProcessor">
			<cewolf:param name="fontSize" value="16"></cewolf:param>
		</cewolf:chartpostprocessor>
	</cewolf:overlaidchart>
	<cewolf:img chartid="grossProfitMarginsChart" height="400" width="820"
		renderer="/cewolf">
		<cewolf:map tooltipgeneratorid="grossProfitMarginBarChart"></cewolf:map>
		<cewolf:map tooltipgeneratorid="grossProfitMarginLineChart"></cewolf:map>
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
				<c:forEach items="${grossProfitMargins}" var="grossProfitMargin">
					<th>Tháng <fmt:formatDate
							value="${grossProfitMargin.value.period}" pattern="M" type="Date"
							dateStyle="SHORT" />
					</th>
				</c:forEach>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>Số liệu</td>
				<c:forEach items="${grossProfitMargins}" var="grossProfitMargin">
					<td><fmt:formatNumber value="${grossProfitMargin.value.value}"
							maxFractionDigits="3" minFractionDigits="3"></fmt:formatNumber></td>
				</c:forEach>
			</tr>
			<tr>
				<td>Tiêu chuẩn</td>
				<c:forEach items="${grossProfitMargins}" var="grossProfitMargin">
					<td><c:out value="${grossProfitMargin.value.threshold}"></c:out></td>
				</c:forEach>
			</tr>
			<tr>
				<td>Đánh giá</td>
				<c:forEach items="${grossProfitMargins}" var="grossProfitMargin">
					<td><c:choose>
							<c:when test="${grossProfitMargin.value.evaluate == 1}">Tốt</c:when>
							<c:when test="${grossProfitMargin.value.evaluate == -1}">Không tốt</c:when>
							<c:otherwise></c:otherwise>
						</c:choose></td>
				</c:forEach>
			</tr>
			<tr>
				<td>Lợi nhuận gộp</td>
				<c:forEach items="${grossProfitMargins}" var="grossProfitMargin">
					<td><fmt:formatNumber
							value="${grossProfitMargin.value.currentAsset}"
							maxFractionDigits="0" minFractionDigits="0"></fmt:formatNumber></td>
				</c:forEach>
			</tr>
			<tr>
				<td>Doanh thu thuần</td>
				<c:forEach items="${grossProfitMargins}" var="grossProfitMargin">
					<td><fmt:formatNumber
							value="${grossProfitMargin.value.currentLiability}"
							maxFractionDigits="0" minFractionDigits="0"></fmt:formatNumber></td>
				</c:forEach>
			</tr>
		</tbody>
	</table>
</div>
