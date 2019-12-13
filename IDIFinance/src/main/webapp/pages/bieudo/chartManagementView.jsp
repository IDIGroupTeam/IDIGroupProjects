<%@page import="com.idi.finance.hangso.KpiChartType"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://cewolf.sourceforge.net/taglib/cewolf.tld"
	prefix="cewolf"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<h4>Biểu đồ KPI</h4>
<hr />

<div class="row form-group">
	<label class="control-label col-sm-2" for="chartTitle">Biểu đồ</label>
	<div class="col-sm-4">${kpiChart.chartTitle}</div>

	<label class="control-label col-sm-2" for="kpiGroup.groupName">Nhóm</label>
	<div class="col-sm-4">${kpiChart.kpiGroup.groupName}</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="chartTitle">Hiển thị</label>
	<div class="col-sm-4">
		<c:choose>
			<c:when test="${kpiChart.homeFlag}">
				<span class="glyphicon glyphicon-ok" style="color: green;"></span>
			</c:when>
		</c:choose>
	</div>

	<label class="control-label col-sm-2" for="kpiGroup.groupName">Ngưỡng</label>
	<div class="col-sm-4">${kpiChart.threshold}</div>
</div>

<div class="table-responsive row form-group">
	<label class="control-label col-sm-12">Các đường chỉ tiêu KPI</label>

	<table id="dataTable" class="table table-bordered table-hover">
		<thead>
			<tr>
				<th class="text-center">STT</th>
				<th class="text-center">Chỉ tiêu</th>
				<th class="text-center">Công thức tính</th>
				<th class="text-center">Loại đường</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${kpiChart.kpiMeasures}" var="kpiMeasure"
				varStatus="status">
				<tr>
					<td>${status.index+1}</td>
					<td>${kpiMeasure.measureName}</td>
					<td>${kpiMeasure.expression}</td>
					<td class="text-center"><c:forEach
							items="${KpiChartType.values()}" var="kpiChartType">
							<c:if test="${kpiChartType.value== kpiMeasure.typeChart}">
								${kpiChartType.name}
							</c:if>
						</c:forEach></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<div class="row form-group">
	<div class="col-sm-12">
		<a href="${url}/quanly/bieudo" class="btn btn-info btn-sm">Danh
			sách biểu đồ</a> <a href="${url}/quanly/bieudo"
			class="btn btn-info btn-sm">Sửa</a> <a href="${url}/quanly/bieudo"
			class="btn btn-info btn-sm">Sao chép</a> <a
			href="${url}/quanly/bieudo" class="btn btn-info btn-sm">Tạo mới</a>
	</div>
</div>