<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://cewolf.sourceforge.net/taglib/cewolf.tld"
	prefix="cewolf"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<h4>Biểu đồ KPI</h4>

<div class="table-responsive">
	<table id="dataTable" class="table table-bordered table-hover">
		<thead>
			<tr>
				<th class="text-center">STT</th>
				<th class="text-center">Biểu đồ</th>
				<th class="text-center">Nhóm</th>
				<th class="text-center">Hiển thị</th>
				<th class="text-center">Ngưỡng</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${kpiCharts}" var="kpiChart" varStatus="status">
				<tr>
					<td>${status.index+1}</td>
					<td>${kpiChart.chartTitle}</td>
					<td>${kpiChart.kpiGroup.groupName}</td>
					<td><c:choose>
							<c:when test="${kpiChart.homeFlag}">
								<span class="glyphicon glyphicon-ok" style="color: green;"></span>
							</c:when>
						</c:choose></td>
					<td>${kpiChart.threshold}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>