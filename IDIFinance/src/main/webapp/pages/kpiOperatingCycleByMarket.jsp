<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://cewolf.sourceforge.net/taglib/cewolf.tld"
	prefix="cewolf"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<jsp:useBean id="operatingCycleBarChart"
	type="com.idi.finance.charts.KpiBarChart" scope="request" />
<jsp:useBean id="operatingCycleLineChart"
	type="com.idi.finance.charts.KpiLineChart" scope="request" />
<jsp:useBean id="operatingCycleChartProcessor"
	type="com.idi.finance.charts.KpiChartProcessor" scope="request" />

<div class="text-center">
	<cewolf:overlaidchart id="operatingCyclesChart"
		title="Chu kỳ hoạt động của doanh nghiệp theo giá trị thị trường"
		yaxislabel="Giá trị" xaxislabel="Năm ${year}" xaxistype="date"
		yaxistype="number" type="overlaidxy" showlegend="true">
		<cewolf:colorpaint color="#99CCFF" />

		<cewolf:plot type="xyshapesandlines">
			<cewolf:data>
				<cewolf:producer id="operatingCycleLineChart" />
			</cewolf:data>
		</cewolf:plot>

		<cewolf:plot type="xyverticalbar">
			<cewolf:data>
				<cewolf:producer id="operatingCycleBarChart" />
			</cewolf:data>
		</cewolf:plot>

		<cewolf:chartpostprocessor id="operatingCycleChartProcessor">
			<cewolf:param name="fontSize" value="16"></cewolf:param>
		</cewolf:chartpostprocessor>
	</cewolf:overlaidchart>
	<cewolf:img chartid="operatingCyclesChart" height="400" width="820"
		renderer="/cewolf">
		<cewolf:map tooltipgeneratorid="operatingCycleBarChart"></cewolf:map>
		<cewolf:map tooltipgeneratorid="operatingCycleLineChart"></cewolf:map>
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
				<c:forEach items="${operatingCycles}" var="operatingCycle">
					<th>Tháng <fmt:formatDate
							value="${operatingCycle.value.period}" pattern="M" type="Date"
							dateStyle="SHORT" />
					</th>
				</c:forEach>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>Số liệu</td>
				<c:forEach items="${operatingCycles}" var="operatingCycle">
					<td><fmt:formatNumber value="${operatingCycle.value.value}"
							maxFractionDigits="3" minFractionDigits="3"></fmt:formatNumber></td>
				</c:forEach>
			</tr>
			<tr>
				<td>Tiêu chuẩn</td>
				<c:forEach items="${operatingCycles}" var="operatingCycle">
					<td><c:out value="${operatingCycle.value.threshold}"></c:out></td>
				</c:forEach>
			</tr>
			<tr>
				<td>Đánh giá</td>
				<c:forEach items="${operatingCycles}" var="operatingCycle">
					<td><c:choose>
							<c:when test="${operatingCycle.value.evaluate == 1}">Tốt</c:when>
							<c:when test="${operatingCycle.value.evaluate == -1}">Không tốt</c:when>
							<c:otherwise></c:otherwise>
						</c:choose></td>
				</c:forEach>
			</tr>

			<tr>
				<td>Doanh thu thuần</td>
				<c:forEach items="${operatingCycles}" var="operatingCycle">
					<td><fmt:formatNumber
							value="${operatingCycle.value.totalOperatingRevenue}"
							maxFractionDigits="0" minFractionDigits="0"></fmt:formatNumber></td>
				</c:forEach>
			</tr>
			<tr>
				<td>Phải thu đầu kỳ</td>
				<c:forEach items="${operatingCycles}" var="operatingCycle">
					<td><fmt:formatNumber
							value="${operatingCycle.value.startReceivable}"
							maxFractionDigits="0" minFractionDigits="0"></fmt:formatNumber></td>
				</c:forEach>
			</tr>
			<tr>
				<td>Phải thu cuối kỳ</td>
				<c:forEach items="${operatingCycles}" var="operatingCycle">
					<td><fmt:formatNumber
							value="${operatingCycle.value.endReceivable}"
							maxFractionDigits="0" minFractionDigits="0"></fmt:formatNumber></td>
				</c:forEach>
			</tr>

			<tr>
				<td>Hàng tồn kho đầu kỳ</td>
				<c:forEach items="${operatingCycles}" var="operatingCycle">
					<td><fmt:formatNumber
							value="${operatingCycle.value.startInventory}"
							maxFractionDigits="0" minFractionDigits="0"></fmt:formatNumber></td>
				</c:forEach>
			</tr>
			<tr>
				<td>Hàng tồn kho cuối kỳ</td>
				<c:forEach items="${operatingCycles}" var="operatingCycle">
					<td><fmt:formatNumber
							value="${operatingCycle.value.endInventory}"
							maxFractionDigits="0" minFractionDigits="0"></fmt:formatNumber></td>
				</c:forEach>
			</tr>
		</tbody>
	</table>
</div>
