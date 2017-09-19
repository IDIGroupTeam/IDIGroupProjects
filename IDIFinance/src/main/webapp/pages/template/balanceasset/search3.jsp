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
		$("#assetsCodes").click(function() {
			var value = $("#assetsCodes").prop('checked');
			$("input[id^=assetsCodes]").prop('checked', value);
		});
		
		$("input[id^=assetsCodes.]").click(function() {
			if($("#assetsCodes").prop('checked')){
				
			}
			
			$("input[id^=assetsCodes]").prop('checked', value);
		});
		
		$("#assetsPeriods").click(function() {
			var value = $("#assetsPeriods").prop('checked');
			$("input[id^=assetsPeriods]").prop('checked', value);
		});
	});
</script>

<%-- <form:form action="${url}/candoiketoan" class="form-horizontal"> --%>
<div class="panel panel-default with-nav-tabs">
	<div class="panel-heading">
		<h4>Tìm kiếm</h4>
	</div>
	<div class="panel-body">
		<div>
			<label class="control-label" for="sel1">Mã số:</label>
			<div class="searchbox" style="max-height: 100px;">
				<div class="checkbox">
					<form:checkbox id="assetsCodes" path="assetsCodes" value="ALL" />
					Tất cả
				</div>
				<c:forEach items="${assetsCodes}" var="assetsCode">
					<div class="checkbox">
						<label><form:checkbox path="assetsCodes"
								value="${assetsCode}" />${assetsCode}</label>
					</div>
				</c:forEach>
			</div>

			<br /> <label class="control-label" for="sel1">Thời gian:</label>
			<div class="searchbox" style="max-height: 100px;">
				<div class="checkbox">
					<form:checkbox id="assetsPeriods" path="assetsPeriods" value="ALL" />
					Tất cả
				</div>
				<c:forEach items="${assetsPeriods}" var="assetsPeriod">
					<div class="checkbox">
						<label><form:checkbox path="assetsPeriods"
								value="${assetsPeriods}" />Tháng <fmt:formatDate
								value="${assetsPeriod}" pattern="M/yyyy" type="Date"
								dateStyle="SHORT" /></label>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
	<div class="panel-footer">
		<button type="submit" class="btn btn-info btn-sm">Tìm kiếm</button>
	</div>
</div>
<%-- </form:form> --%>
