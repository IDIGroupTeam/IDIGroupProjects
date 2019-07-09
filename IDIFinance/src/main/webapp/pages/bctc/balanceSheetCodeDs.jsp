<%@page import="com.idi.finance.bean.taikhoan.LoaiTaiKhoan"%>
<%@page import="com.idi.finance.utils.PropCont"%>
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
				"${url}/bctc/cdkt/chitieu/danhsach");
		$("#mainFinanceForm").attr("method", "POST");

		$('#treeTable').treeTable();

		$('#treeTable').cellEditable({
			beforeLoad : function(key, cellDatas) {
				try {
					return "maTk=" + cellDatas.matkgoc;
				} catch (e) {
					console.log("beforeLoad", e)
				}
				return "";
			},
			urlLoad : "${url}/taikhoan/danhsach/capduoi",
			afterLoad : function(data) {
				if (data == null)
					return null;

				var list = new Array();
				for (var i = 0; i < data.length; i++) {
					var obj = new Object();
					obj.value = data[i].maTk;
					obj.label = data[i].maTenTk;
					list[i] = obj;
				}
				return list;
			},
			beforeSave : function(key, cells) {
				try {
					var res = "assetCode=" + key.assetcode;
					res += "&maTkCu=" + key.matk;
					res += "&maTk=" + cells[0].value;

					return res;
				} catch (e) {
				}
				return "";
			},
			urlSave : "${url}/bctc/cdkt/chitieu/capnhat",
			beforeRemove : function(key, cells) {
				try {
					return "assetCode=" + key.assetcode + "&maTk=" + key.matk;
				} catch (e) {
				}

				return "";
			},
			urlRemove : "${url}/bctc/cdkt/chitieu/xoa",
			afterRemove : function(data) {
				try {
					window.location.href = "${url}/bctc/cdkt/chitieu/danhsach";
				} catch (e) {
					// alert(e);
				}
			}
		});
	});
</script>

<h4>Danh sách các chỉ tiêu của bảng cân đối kế toán</h4>

<div>
	<span><i>${props.getCauHinh(PropCont.CHE_DO_KE_TOAN).giaTri}</i></span> <a
		href="${url}/bctc/cdkt/chitieu/taomoi"
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
				<th class="text-center">Tài khoản gốc</th>
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
				<jsp:include page="balanceSheetCodeDsCon.jsp" />
			</c:forEach>
		</tbody>
	</table>
</div>