<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<form:form action="">
	<div class="panel panel-default with-nav-tabs">
		<div class="panel-heading">
			<h4>Chọn biểu đồ</h4>
		</div>
		<div class="panel-body">
			<div class="form-group">
				<label for="sel1">Các biểu đồ KPI - Khả năng sinh lời:</label>
			</div>
			<div class="searchbox">
				<div class="checkbox">
					<label><input name="kipChart" type="checkbox"> Hiệu
						suất sử dụng tổng tài sản (Vòng quay tổng tài sản)</label>
				</div>
				<div class="checkbox">
					<label><input name="kipChart" type="checkbox"> Hiệu
						suất sử dụng tài sản cố định</label>
				</div>
				<div class="checkbox">
					<label><input name="kipChart" type="checkbox"> Hiệu
						suất sử dụng trên vòng quay vốn lưu động</label>
				</div>
				<div class="checkbox">
					<label><input name="kipChart" type="checkbox"> Tỷ
						suất lợi nhuận gộp (Lợi nhuận gộp biên)</label>
				</div>
				<div class="checkbox">
					<label><input name="kipChart" type="checkbox"> Tỷ
						suất lợi nhuận ròng (Lợi nhuận ròng biên)</label>
				</div>
			</div>
		</div>
		<div class="panel-footer">
			<button type="submit" class="btn btn-info btn-sm">Vẽ biểu đồ</button>
		</div>
	</div>
</form:form>
