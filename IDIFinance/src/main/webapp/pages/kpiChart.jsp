<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://cewolf.sourceforge.net/taglib/cewolf.tld"
	prefix="cewolf"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<script type="text/javascript">
	$(function() {
		$('.collapse-button').click(
				function() {
					$(this).find('i').toggleClass('glyphicon-collapse-down')
							.toggleClass('glyphicon-collapse-up');
				});
	});
</script>

<h4>Biểu đồ KPI</h4>
<c:forEach items="${kpiGroup.kpiCharts}" var="kpiChart">
	<c:if test="${kpiChart.kpiMeasures==null }">
		<div class="text-center">
			<h4>${kpiChart.chartTitle}</h4>
			Biểu đồ đang được thiết kế, xin hãy xem lại sau.
		</div>
		<hr />
	</c:if>
	<c:if test="${kpiChart.kpiMeasures!=null }">
		<div class="text-center">
			<cewolf:overlaidchart id="${kpiChart.chartId}"
				title="${kpiChart.chartTitle}" yaxislabel="Giá trị"
				xaxislabel="Năm ${year}" xaxistype="date" yaxistype="number"
				type="overlaidxy" showlegend="true">
				<cewolf:colorpaint color="#99CCFF" />

				<cewolf:plot type="xyshapesandlines">
					<cewolf:data>
						<cewolf:producer id="KpiMeasureLineChart${kpiChart.chartId}" />
					</cewolf:data>
				</cewolf:plot>

				<c:forEach items="${kpiChart.kpiMeasures}" var="kpiMeasure">
					<cewolf:plot type="xyverticalbar">
						<cewolf:data>
							<cewolf:producer id="barChart${kpiMeasure.measureId}" />
						</cewolf:data>
						<cewolf:chartpostprocessor
							id="barProcessor${kpiMeasure.measureId}">
							<cewolf:param name="fontSize" value="16"></cewolf:param>
						</cewolf:chartpostprocessor>
					</cewolf:plot>
				</c:forEach>
			</cewolf:overlaidchart>
			<cewolf:img chartid="${kpiChart.chartId}" height="400" width="820"
				renderer="/cewolf">
				<c:forEach items="${kpiChart.kpiMeasures}" var="kpiMeasure">
					<cewolf:map tooltipgeneratorid="barChart${kpiMeasure.measureId}"></cewolf:map>
				</c:forEach>
				<cewolf:map
					tooltipgeneratorid="KpiMeasureLineChart${kpiChart.chartId}"></cewolf:map>
			</cewolf:img>
		</div>
		<br />
		<div>
			<button type="button" data-toggle="collapse"
				data-target="#detail${kpiChart.chartId}"
				style="border: none; outline: none; background: transparent; padding: 0px; margin: 0px; color: #428bca;"
				class="collapse-button btn pull-left btn-lg">
				<i class="glyphicon glyphicon-collapse-down"></i>
			</button>
			&nbsp; <i>Ẩn/Hiện dữ liệu chi tiết</i>
		</div>
		<div id="detail${kpiChart.chartId}"
			class="table-responsive small-block collapse">
			<div class="text-center">
				<h4>Số liệu chi tiết</h4>

				<i class="pull-left"> &nbsp; (*)<c:forEach
						items="${kpiChart.kpiMeasures}" var="kpiMeasure">
						${kpiMeasure.measureName}:&nbsp;${kpiMeasure.expression}
					</c:forEach></i>
				<c:if
					test="${kpiChart.operands!=null && kpiChart.operands.size() > 0}">
					<i class="pull-right">(**): Đơn vị: Triệu VND</i>
				</c:if>
			</div>
			<table class="table table-bordered table-hover">
				<thead>
					<tr>
						<th></th>
						<c:forEach items="${kpiChart.dates}" var="period">
							<th>Tháng <fmt:formatDate value="${period}" pattern="M"
									type="Date" dateStyle="SHORT" />
							</th>
						</c:forEach>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${kpiChart.kpiMeasures}" var="kpiMeasure">
						<tr>
							<td class="limited">${kpiMeasure.measureName}&nbsp;(*)</td>
							<c:forEach items="${kpiMeasure.values}" var="kpiMeasureValue">
								<td><fmt:formatNumber value="${kpiMeasureValue.value}"
										maxFractionDigits="3" minFractionDigits="3"></fmt:formatNumber>
								</td>
							</c:forEach>
						</tr>
						<tr>
							<td>Đánh giá</td>
							<c:forEach items="${kpiMeasure.evaluates}" var="evaluate">
								<c:choose>
									<c:when test="${evaluate.value == 1}">
										<td class="good">Tốt</td>
									</c:when>
									<c:when test="${evaluate.value == -1}">
										<td class="bad">Không tốt</td>
									</c:when>
									<c:otherwise>
										<td></td>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</tr>
					</c:forEach>
					<tr>
						<td>Tiêu chuẩn</td>
						<c:forEach items="${kpiChart.dates}" var="period">
							<td><c:out value="${kpiChart.threshold}"></c:out></td>
						</c:forEach>
					</tr>
					<c:forEach items="${kpiChart.kpis}" var="kpi">
						<tr>
							<td class="limited">${kpi.key.measureName}&nbsp;(${kpi.key.measureId})</td>
							<c:forEach items="${kpi.value}" var="kpiMeasure">
								<td><fmt:formatNumber value="${kpiMeasure.value}"
										maxFractionDigits="3" minFractionDigits="3"></fmt:formatNumber>
								</td>
							</c:forEach>
						</tr>
					</c:forEach>
					<c:forEach items="${kpiChart.operands}" var="operand">
						<tr>
							<td class="limited">${operand.key.assetName}&nbsp;(${operand.key.assetCode})&nbsp;(**)</td>
							<c:forEach items="${operand.value}" var="bai">
								<td class="limited"
									title='<fmt:formatNumber
										value="${bai.value}" maxFractionDigits="0"
										minFractionDigits="0"></fmt:formatNumber>'><fmt:formatNumber
										value="${bai.value/1000000}" maxFractionDigits="0"
										minFractionDigits="0"></fmt:formatNumber></td>
							</c:forEach>
						</tr>
					</c:forEach>
				</tbody>
				<tfoot>

				</tfoot>
			</table>
		</div>
		<hr />
	</c:if>
</c:forEach>
