<%@page import="com.idi.finance.bean.taikhoan.LoaiTaiKhoan"%>
<%@page import="com.idi.finance.hangso.PropCont"%>
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
		// Khởi tạo action/method cho mainFinanceForm form
		$("#mainFinanceForm").attr("action",
				"${url}/bctc/luuchuyentt/chitieu/danhsach");
		$("#mainFinanceForm").attr("method", "POST");

		$('#treeTable').treeTable();

		$('#treeTable')
				.cellEditable(
						{
							beforeRemove : function(key, cells) {
								try {
									return "assetCode=" + key.assetcode
											+ "&maTk=" + key.matk + "&soDu="
											+ key.sodu + "&doiUngMaTk="
											+ key.doiungmatk;
								} catch (e) {
									console.log("beforeRemove", e);
								}

								return "";
							},
							urlRemove : "${url}/bctc/luuchuyentt/chitieu/xoa",
							afterRemove : function(data) {
								try {
									window.location.href = "${url}/bctc/luuchuyentt/chitieu/danhsach";
								} catch (e) {
									// alert(e);
								}
							}
						});
	});
</script>

<h4>Danh sách các chỉ tiêu của bảng lưu chuyển tiền tệ</h4>

<div>
	<span><i>${props.getCauHinh(PropCont.CHE_DO_KE_TOAN).giaTri}</i></span>
	<a href="${url}/bctc/luuchuyentt/chitieu/taomoi"
		class="btn btn-info btn-sm pull-right"> <span
		class="glyphicon glyphicon-plus"></span> Tạo mới
	</a>
</div>
<br />

<div class="table-responsive">
	<table id="treeTable" class="table table-bordered table-hover">
		<thead>
			<tr>
				<th class="text-center" rowspan="2">Chỉ tiêu</th>
				<th class="text-center" colspan="2">Tài khoản</th>
			</tr>
			<tr>
				<th class="text-center">Số dư</th>
				<th class="text-center">Tài khoản đối ứng</th>
			</tr>
		</thead>
		<tbody class="form-inline">
			<c:forEach items="${bais}" var="bai">
				<tr id="${bai.assetCode}">
					<td>${bai.assetCode}&nbsp;-&nbsp;<span
						class="cell-editable dis-editable dis-removable">${bai.assetName}</span></td>
					<td></td>
					<td></td>
				</tr>

				<c:set var="parentId" value="${bai.assetCode}" scope="request" />
				<c:set var="assetCode" value="${bai.assetCode}" scope="request" />
				<c:set var="taiKhoanDs" value="${bai.taiKhoanDs}" scope="request" />
				<c:set var="bais" value="${bai.childs}" scope="request" />
				<jsp:include page="cashFlowCodeDsCon.jsp" />
			</c:forEach>
		</tbody>
	</table>
</div>