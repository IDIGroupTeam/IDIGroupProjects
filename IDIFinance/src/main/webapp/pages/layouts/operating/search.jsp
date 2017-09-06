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
				<label for="sel1">Các biểu đồ KPI - Khả năng hoạt động:</label>
			</div>
			<div class="searchbox">
				<div class="checkbox">
					<label><input name="kipChart" type="checkbox"> Vòng
						quay khoản phải thu</label>
				</div>
				<div class="checkbox">
					<label><input name="kipChart" type="checkbox"> Kỳ
						thu tiền bình quân</label>
				</div>
				<div class="checkbox">
					<label><input name="kipChart" type="checkbox"> Vòng
						quay hàng tồn kho theo giá trị sổ sách</label>
				</div>
				<div class="checkbox">
					<label><input name="kipChart" type="checkbox"> Vòng
						quay hàng tồn kho theo giá thị trường</label>
				</div>
				<div class="checkbox">
					<label><input name="kipChart" type="checkbox"> Số
						ngày tồn kho bình quân theo giá trị sổ sách</label>
				</div>
				<div class="checkbox">
					<label><input name="kipChart" type="checkbox"> Số
						ngày hàng tồn kho bình quân theo giá thị trường</label>
				</div>
				<div class="checkbox">
					<label><input name="kipChart" type="checkbox"> Chu
						kỳ hoạt động của doanh nghiệp theo giá trị sổ sách</label>
				</div>
				<div class="checkbox">
					<label><input name="kipChart" type="checkbox"> Chu
						kỳ hoạt động của doanh nghiệp theo giá thị trường</label>
				</div>
				<div class="checkbox">
					<label><input name="kipChart" type="checkbox"> Vòng
						quay khoản phải trả</label>
				</div>
				<div class="checkbox">
					<label><input name="kipChart" type="checkbox"> Kỳ
						phải trả bình quân</label>
				</div>
				<div class="checkbox">
					<label><input name="kipChart" type="checkbox"> Chu
						kỳ luân chuyển tiền theo giá trị sổ sách</label>
				</div>
				<div class="checkbox">
					<label><input name="kipChart" type="checkbox"> Chu
						kỳ luân chuyển tiền theo giá trị thị trường</label>
				</div>
				<div class="checkbox">
					<label><input name="kipChart" type="checkbox"> Khả
						năng thanh toán lãi vay</label>
				</div>
			</div>
		</div>
		<div class="panel-footer">
			<button type="submit" class="btn btn-info btn-sm">Vẽ biểu đồ</button>
		</div>
	</div>
</form:form>