<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://cewolf.sourceforge.net/taglib/cewolf.tld"
	prefix="cewolf"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<jsp:useBean id="cashConversionCycleBarChart"
	type="com.idi.finance.charts.KpiBarChart" scope="request" />
<jsp:useBean id="cashConversionCycleLineChart"
	type="com.idi.finance.charts.KpiLineChart" scope="request" />
<jsp:useBean id="cashConversionCycleChartProcessor"
	type="com.idi.finance.charts.KpiChartProcessor" scope="request" />

<div class="text-center">
	<cewolf:overlaidchart id="cashConversionCycleChart"
		title="Chu kỳ luân chuyển tiền theo giá trị sổ sách"
		yaxislabel="Giá trị" xaxislabel="Năm ${year}" xaxistype="date"
		yaxistype="number" type="overlaidxy" showlegend="true">
		<cewolf:colorpaint color="#99CCFF" />

		<cewolf:plot type="xyshapesandlines">
			<cewolf:data>
				<cewolf:producer id="cashConversionCycleLineChart" />
			</cewolf:data>
		</cewolf:plot>

		<cewolf:plot type="xyverticalbar">
			<cewolf:data>
				<cewolf:producer id="cashConversionCycleBarChart" />
			</cewolf:data>
		</cewolf:plot>

		<cewolf:chartpostprocessor id="cashConversionCycleChartProcessor">
			<cewolf:param name="fontSize" value="16"></cewolf:param>
		</cewolf:chartpostprocessor>
	</cewolf:overlaidchart>
	<cewolf:img chartid="cashConversionCycleChart" height="400" width="820"
		renderer="/cewolf">
		<cewolf:map tooltipgeneratorid="cashConversionCycleBarChart"></cewolf:map>
		<cewolf:map tooltipgeneratorid="cashConversionCycleLineChart"></cewolf:map>
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
				<c:forEach items="${cashConversionCycles}" var="cashConversionCycle">
					<th>Tháng <fmt:formatDate
							value="${cashConversionCycle.value.period}" pattern="M"
							type="Date" dateStyle="SHORT" />
					</th>
				</c:forEach>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>Số liệu</td>
				<c:forEach items="${cashConversionCycles}" var="cashConversionCycle">
					<td><fmt:formatNumber
							value="${cashConversionCycle.value.value}" maxFractionDigits="3"
							minFractionDigits="3"></fmt:formatNumber></td>
				</c:forEach>
			</tr>
			<tr>
				<td>Tiêu chuẩn</td>
				<c:forEach items="${cashConversionCycles}" var="cashConversionCycle">
					<td><c:out value="${cashConversionCycle.value.threshold}"></c:out></td>
				</c:forEach>
			</tr>
			<tr>
				<td>Đánh giá</td>
				<c:forEach items="${cashConversionCycles}" var="cashConversionCycle">
					<td><c:choose>
							<c:when test="${cashConversionCycle.value.evaluate == 1}">Tốt</c:when>
							<c:when test="${cashConversionCycle.value.evaluate == -1}">Không tốt</c:when>
							<c:otherwise></c:otherwise>
						</c:choose></td>
				</c:forEach>
			</tr>
			<tr>
				<td>Chu kỳ hoạt động của doanh nghiệp theo giá trị sổ sách</td>
				<c:forEach items="${cashConversionCycles}" var="cashConversionCycle">
					<td><fmt:formatNumber
							value="${cashConversionCycle.value.operatingCycle}"
							maxFractionDigits="0" minFractionDigits="0"></fmt:formatNumber></td>
				</c:forEach>
			</tr>
			<tr>
				<td>Kỳ phải trả bình quân</td>
				<c:forEach items="${cashConversionCycles}" var="cashConversionCycle">
					<td><fmt:formatNumber
							value="${cashConversionCycle.value.avgPaymentPeriod}"
							maxFractionDigits="0" minFractionDigits="0"></fmt:formatNumber></td>
				</c:forEach>
			</tr>
		</tbody>
	</table>
</div>
