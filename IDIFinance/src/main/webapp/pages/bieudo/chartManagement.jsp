<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://cewolf.sourceforge.net/taglib/cewolf.tld"
	prefix="cewolf"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<script type="text/javascript">
	//Shorthand for $( document ).ready()
	$(function() {
		$('#dataTable')
				.cellEditable(
						{
							beforeLoad : {
								homeFlag : function(data) {
								}
							},
							afterLoad : {
								homeFlag : function(data) {
									var list = new Array();

									var obj = new Object();
									obj.value = true;
									obj.label = "Hiển thị";
									list[0] = obj;

									obj = new Object();
									obj.value = false;
									obj.label = "Ẩn";
									list[1] = obj;

									return list;
								}
							},
							beforeSave : {
								kpiChart : function(data) {
									if (data == null) {
										return null;
									}

									var kpiMeasure = new Object();
									kpiMeasure.measureId = eval(data.expression.measureId);
									kpiMeasure.expression = data.expression.value;

									var kpiMeasures = new Array();
									kpiMeasures.push(kpiMeasure);

									var sendingData = new Object();
									sendingData.chartId = eval(data.chartId);
									sendingData.homeFlag = eval(data.homeFlag.value);
									sendingData.threshold = eval(data.threshold.value);
									sendingData.kpiMeasures = kpiMeasures;

									return sendingData;
								}
							},
							afterSave : {
								kpiChart : function(data) {
									if (data == null) {
										return null;
									}

									$(this)
											.find(".cell-editable")
											.each(
													function() {
														var cell = $(this);
														var cellData = $(cell)
																.data();

														if (cellData.field == "homeFlag") {
															html = '<span class="glyphicon glyphicon-ok" style="color: green;"></span>';
															if (data[cellData.field].value == 'false') {
																html = '';
															}
															$(cell).html(html);
														}
													});
								}
							},
							beforeRemove : {
								kpiChart : function(data) {
									if (data == null) {
										return null;
									}

									var sendingData = new Object();
									sendingData.chartId = data.chartId;

									return sendingData;
								}
							},
							afterRemove : {
								kpiChart : function(data) {
									try {
										window.location.href = "${url}/quanly/bieudo";
									} catch (e) {
									}
								},
							}
						});
	});
</script>

<h4>Quản lý biểu đồ KPI</h4>
<div class="pull-right">
	<a href="${url}/quanly/bieudo/taomoi" class="btn btn-info btn-sm">
		<span class="glyphicon glyphicon-plus"></span> Tạo mới biểu đồ
	</a>
</div>
<br />
<br />

<div class="table-responsive">
	<table id="dataTable" class="table table-bordered table-hover">
		<thead>
			<tr>
				<th class="text-center">STT</th>
				<th class="text-center">Biểu đồ</th>
				<th class="text-center">Nhóm</th>
				<th class="text-center">Công thức</th>
				<th class="text-center">Hiển thị</th>
				<th class="text-center">Ngưỡng</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${kpiCharts}" var="kpiChart" varStatus="status">
				<tr data-name="kpiChart" data-chart-id="${kpiChart.chartId}"
					data-save-url="${url}/quanly/bieudo/capnhat"
					data-remove-url="${url}//quanly/bieudo/xoa">
					<td>${status.index+1}</td>
					<%-- <td><a href="${url}/quanly/bieudo/xem/${kpiChart.chartId}"
						class="text-primary"> ${kpiChart.chartTitle}</a></td> --%>
					<td>${kpiChart.chartTitle}</td>
					<td>${kpiChart.kpiGroup.groupName}</td>
					<td><span class="cell-editable" data-field="expression"
						data-measure-id="${kpiChart.kpiMeasures.get(0).measureId}">${kpiChart.kpiMeasures.get(0).expression}</span></td>
					<td><span class="cell-editable" data-field="homeFlag"
						data-home-flag="${kpiChart.homeFlag}" data-type="combobox"><c:choose>
								<c:when test="${kpiChart.homeFlag}">
									<span class="glyphicon glyphicon-ok" style="color: green;"></span>
								</c:when>
							</c:choose></span></td>
					<td><span class="cell-editable" data-field="threshold">${kpiChart.threshold}</span></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>