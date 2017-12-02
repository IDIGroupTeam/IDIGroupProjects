<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<form:form action="${url}/bieudo/${kpiGroup.groupId}"
	modelAttribute="TkKpiChartForm" method="POST">
	<div class="panel panel-default with-nav-tabs">
		<div class="panel-heading">
			<h4>Chọn biểu đồ KPI</h4>
		</div>
		<div class="panel-body">
			<div class="form-group">
				<label for="sel1">${kpiGroup.groupName}:</label>
			</div>
			<div class="searchbox">
				<c:forEach items="${kpiCharts}" var="kpiChart">
					<div class="checkbox">
						<label><form:checkbox path="kipCharts"
								value="${kpiChart.chartId}" />${kpiChart.chartTitle}</label>
					</div>
				</c:forEach>
			</div>
		</div>
		<div class="panel-footer">
			<button type="submit" class="btn btn-info btn-sm">Vẽ biểu đồ</button>
		</div>
	</div>
</form:form>
