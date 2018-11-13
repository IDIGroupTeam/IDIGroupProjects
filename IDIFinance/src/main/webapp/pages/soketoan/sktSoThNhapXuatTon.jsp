<%@page import="com.idi.finance.bean.chungtu.ChungTu"%>
<%@page import="com.idi.finance.bean.chungtu.DoiTuong"%>
<%@page import="com.idi.finance.bean.taikhoan.LoaiTaiKhoan"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<script type="text/javascript">
	//Shorthand for $( document ).ready()
	$(function() {
		// Khởi tạo action/method cho mainFinanceForm form

	});
</script>

<h4>Sổ tổng hợp nhập xuất tồn</h4>
<p>
	<i>156 - Kho Cầu Giấy</i>
</p>

<div class="table-responsive">
	<table class="table table-bordered table-hover">
		<thead>
			<tr>
				<th rowspan="2" class="text-center">Mã VT</th>
				<th rowspan="2" class="text-center">Tên vật tư</th>
				<th rowspan="2" class="text-center">Đơn vị</th>
				<th colspan="2" class="text-center">Tồn đầu kỳ</th>
				<th colspan="2" class="text-center">Nhập trong kỳ</th>
				<th colspan="2" class="text-center">Xuất trong kỳ</th>
				<th colspan="2" class="text-center">Tồn cuối kỳ</th>
				<th rowspan="2" class="text-center">Ghi chú</th>
			</tr>
			<tr>
				<th class="text-center">Số lượng</th>
				<th class="text-center">Thành tiền</th>
				<th class="text-center">Số lượng</th>
				<th class="text-center">Thành tiền</th>
				<th class="text-center">Số lượng</th>
				<th class="text-center">Thành tiền</th>
				<th class="text-center">Số lượng</th>
				<th class="text-center">Thành tiền</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><a href="${url}/soketoan/nhapxuatton/chitiet">TOYOTA</a></td>
				<td>Xe Toyota</td>
				<td>Chiếc</td>
				<td>10</td>
				<td>20000</td>
				<td>2</td>
				<td>4000</td>
				<td>0</td>
				<td>0</td>
				<td>12</td>
				<td>24000</td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</tbody>
	</table>
</div>