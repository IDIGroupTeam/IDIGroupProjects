<%@page import="com.idi.finance.bean.cdkt.KyKeToanCon"%>
<%@page import="com.idi.finance.bean.chungtu.ChungTu"%>
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
		$("#mainFinanceForm").addClass("form-horizontal");
		$("#submitBut").click(function() {
			$("#mainFinanceForm").prop("method", "POST");
			$("#mainFinanceForm").submit();
		});
	});
</script>

<div class="panel panel-default with-nav-tabs">
	<div class="panel-heading">
		<h4>Tìm kiếm</h4>
	</div>
	<div class="panel-body">
		<div class="form-group">
			<label for="taiKhoan">Tài khoản:</label>
			<div class="input-group date smallform pull-right">
				<form:select id="taiKhoan" path="taiKhoan" multiple="false"
					class="form-control">
					<form:options items="${loaiTaiKhoanDs}" itemValue="maTk"
						itemLabel="maTenTk" />
				</form:select>
			</div>
		</div>

		<div class="form-group">
			<label for="loaiKy">Kỳ:</label>
			<form:select path="loaiKy" multiple="false"
				class="form-control smallform pull-right">
				<form:option value="${KyKeToanCon.WEEK}">Tuần</form:option>
				<form:option value="${KyKeToanCon.MONTH}">Tháng</form:option>
				<form:option value="${KyKeToanCon.QUARTER}">Quý</form:option>
				<form:option value="${KyKeToanCon.YEAR}">Năm</form:option>
			</form:select>
		</div>

		<div class="form-group">
			<label for="dau">Từ:</label>
			<div class="input-group date datetime smallform pull-right">
				<form:input path="dau" class="form-control" readonly="true" />
				<span class="input-group-addon"><span
					class="glyphicon glyphicon-calendar"></span></span>
			</div>
		</div>

		<div class="form-group">
			<label for="cuoi">Đến:</label>
			<div class="input-group date datetime smallform pull-right">
				<form:input path="cuoi" class="form-control" readonly="true" />
				<span class="input-group-addon"><span
					class="glyphicon glyphicon-calendar"></span></span>
			</div>
		</div>

		<script type="text/javascript">
			$(".datetime").datetimepicker({
				language : 'vi',
				todayBtn : 1,
				autoclose : 1,
				todayHighlight : 1,
				startView : 2,
				minView : 2,
				forceParse : 0,
				pickerPosition : "top-left"
			});
		</script>
	</div>
	<div class="panel-footer">
		<button id="submitBut" type="button" class="btn btn-info btn-sm">Tìm
			kiếm</button>
	</div>
</div>