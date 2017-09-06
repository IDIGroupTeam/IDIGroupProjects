<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<script type="text/javascript">
	// Shorthand for $( document ).ready()
	$(function() {
		$("#assetsCodeSelect")
				.multiselect(
						{
							includeSelectAllOption : true,
							maxHeight : 200,
							selectAllText : 'Tất cả',
							nonSelectedText : 'Tìm kiếm theo mã số',
							buttonClass : '',
							dropRight : true,
							templates : {
								button : '<span class="multiselect dropdown-toggle glyphicon glyphicon-filter" data-toggle="dropdown"></span>'
							},
							onChange : function(option, checked, select) {
								//alert('Changed option ' + $(option).val() + '.');
							},
							onDropdownHidden : function(event) {
								//alert('Dropdown closed.');
							}
						});

		$("#assetsPeriodSelect")
				.multiselect(
						{
							includeSelectAllOption : true,
							maxHeight : 200,
							selectAllText : 'Tất cả',
							nonSelectedText : 'Tìm kiếm theo tháng',
							buttonClass : '',
							dropRight : true,
							templates : {
								button : '<span class="multiselect dropdown-toggle glyphicon glyphicon-filter" data-toggle="dropdown"></span>'
							},
							onChange : function(option, checked, select) {
								//alert('Changed option ' + $(option).val() + '.');
							},
							onDropdownHidden : function(event) {
								//alert('Dropdown closed.');
							}
						});
	});
</script>
<h4>Bảng cân đối kế toán</h4>

<form:form action="bangcandoiketoan" modelAttribute="balanceSheetForm">
	<table class="table table-bordered table-hover">
		<thead>
			<tr>
				<th>Tài sản</th>
				<th>Mã số <select id="assetsCodeSelect" name="assetsCodes[]"
					multiple="multiple">
						<c:forEach items="${assetsCodes}" var="assetsCode">
							<option value="${assetsCode}">${assetsCode }</option>
						</c:forEach>
				</select>
				</th>
				<th>Thuyết minh</th>
				<th>Số cuối kỳ</th>
				<th>Số đầu kỳ</th>
				<th>Mức thay đổi</th>
				<th>Thời gian <select id="assetsPeriodSelect"
					name="assetsPeriods[]" multiple="multiple">
						<c:forEach items="${assetsPeriods}" var="assetsPeriod">
							<option value="${assetsPeriod}">Tháng <fmt:formatDate value="${assetsPeriod}"
							pattern="M/yyyy" type="Date" dateStyle="SHORT" /></option>
						</c:forEach>
				</select>
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${bss}" var="bs">
				<tr>
					<td>${bs.assetsName}</td>
					<td>${bs.assetsCode}</td>
					<td>${bs.note}</td>
					<td><c:if test="${bs.endValue!=0}">
							<fmt:formatNumber value="${bs.endValue}" type="NUMBER"
								maxFractionDigits="1"></fmt:formatNumber>
						</c:if></td>
					<td><c:if test="${bs.startValue!=0}">
							<fmt:formatNumber value="${bs.startValue}" type="NUMBER"
								maxFractionDigits="1"></fmt:formatNumber>
						</c:if></td>
					<td><c:if test="${bs.changedRatio!=0}">
							<fmt:formatNumber value="${bs.changedRatio}" type="PERCENT"
								maxFractionDigits="1" maxIntegerDigits="3"></fmt:formatNumber>
						</c:if></td>
					<td>Tháng <fmt:formatDate value="${bs.assetsPeriod}"
							pattern="M/yyyy" type="Date" dateStyle="SHORT" />
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="form-group">
		<table style="width: 100%;">
			<tr>
				<td><ul class="pagination">
						<li class="page-item"><a class="page-link" href="#">Trước</a></li>
						<li class="page-item"><a class="page-link" href="#">Đầu</a></li>
						<li class="page-item"><a class="page-link" href="#">1</a></li>
						<li class="page-item"><a class="page-link" href="#">2</a></li>
						<li class="page-item"><a class="page-link" href="#">3</a></li>
						<li class="page-item"><a class="page-link" href="#">4</a></li>
						<li class="page-item"><a class="page-link" href="#">Cuối</a></li>
						<li class="page-item"><a class="page-link" href="#">Sau</a></li>
					</ul></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td><span>Số dòng trong một trang</span></td>
				<td><select id="numberOfPage" name="numberOfPage"
					class="form-control">
						<option value="25">25</option>
						<option value="25">50</option>
						<option value="25">100</option>
						<option value="25">200</option>
				</select></td>
			</tr>
		</table>
	</div>
</form:form>