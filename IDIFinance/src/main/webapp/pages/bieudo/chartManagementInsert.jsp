<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<script type="text/javascript">
	//Shorthand for $( document ).ready()
	$(function() {
		// Khởi tạo action/method cho mainFinanceForm form
		$("#mainFinanceForm").attr("action", "${url}/quanly/bieudo/luu");
		$("#mainFinanceForm").attr("method", "POST");

		$("#submitBt").click(function() {
			$("#mainFinanceForm").submit();
		});
	});
</script>

<h4>Tạo mới biểu đồ KPI</h4>
<hr />

<div class="row form-group">
	<label class="control-label col-sm-2" for="chartTitle">Biểu đồ
		(*):</label>
	<div class="col-sm-4">
		<form:input path="chartTitle" placeholder="Biểu đồ"
			cssClass="form-control" />
		<form:errors path="chartTitle" cssClass="error" />
	</div>

	<label class="control-label col-sm-2" for="kpiGroup.groupId">Nhóm:
	</label>
	<div class="col-sm-4">
		<form:select path="kpiGroup.groupId" cssClass="form-control"
			placeholder="Nhóm">
			<form:options items="${kpiGroups}" itemValue="groupId"
				itemLabel="groupName" />
		</form:select>
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="threshold">Ngưỡng:</label>
	<div class="col-sm-4">
		<form:input path="threshold" placeholder="Ngưỡng"
			cssClass="form-control" />
	</div>

	<label class="control-label col-sm-2" for="homeFlag">Hiển thị:
	</label>
	<div class="col-sm-4">
		<form:select path="homeFlag" cssClass="form-control"
			placeholder="Hiển thị">
			<form:option value="true">Hiển thị</form:option>
			<form:option value="false">Ẩn</form:option>
		</form:select>
	</div>
</div>

<div class="row form-group">
	<label class="control-label col-sm-2" for="kpiMeasures[0].expression">Biểu
		thức:</label>
	<div class="col-sm-4">
		<form:input path="kpiMeasures[0].expression" placeholder="Biểu thức"
			cssClass="form-control" />
	</div>

	<i class="col-sm-6">Biểu thức tính cần tuân theo những quy luật
		nhất định</i>
</div>

<div class="row form-group">
	<div class="col-sm-12">
		<a href="${url}/quanly/bieudo" class="btn btn-info btn-sm">Hủy</a>
		<button id="submitBt" type="submit" class="btn btn-info btn-sm">Lưu</button>
	</div>
</div>
