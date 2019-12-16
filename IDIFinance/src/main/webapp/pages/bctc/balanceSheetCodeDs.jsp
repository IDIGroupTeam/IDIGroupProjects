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
				"${url}/bctc/cdkt/chitieu/danhsach");
		$("#mainFinanceForm").attr("method", "POST");

		$('#treeTable').treeTable();

		$('#treeTable')
				.cellEditable(
						{
							beforeLoad : {
								maTk : function(data) {
									if (data == null)
										return null;

									var sendingData = new Object();
									sendingData.maTkGoc = data.maTkGoc;

									return sendingData;
								}
							},
							afterLoad : {
								maTk : function(data) {
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
								}
							},
							beforeSave : {
								chiTieuCao : function(data) {
									console.log("beforeSave chiTieuCao", data);
									var sendingData = new Object();
									sendingData.assetCode = data.assetCode;
									sendingData.assetName = data.assetName.value;

									return sendingData;
								},
								chiTieuThap : function(data) {
									console.log("beforeSave chiTieuThap", data);
									var sendingData = new Object();
									sendingData.assetCode = data.assetCode;

									var taiKhoanDs = new Array();
									var loaiTaiKhoan = new Object();
									loaiTaiKhoan.maTk = data.maTk.value;
									loaiTaiKhoan.maTkGoc = data.maTk.maTk;
									taiKhoanDs.push(loaiTaiKhoan);

									sendingData.taiKhoanDs = taiKhoanDs;
									return sendingData;
								}
							},
							beforeRemove : {
								chiTieuCao : function(data) {
									var sendingData = new Object();
									sendingData.assetCode = data.assetCode;

									return sendingData;
								},
								chiTieuThap : function(data) {
									var sendingData = new Object();
									sendingData.assetCode = data.assetCode;

									var taiKhoanDs = new Array();
									var loaiTaiKhoan = new Object();
									loaiTaiKhoan.maTk = data.maTk;
									taiKhoanDs.push(loaiTaiKhoan);

									sendingData.taiKhoanDs = taiKhoanDs;
									return sendingData;
								}
							},
							afterRemove : {
								chiTieuCao : function(data) {
									try {
										window.location.href = "${url}/bctc/cdkt/chitieu/danhsach";
									} catch (e) {
									}
								},
								chiTieuThap : function(data) {
									try {
										window.location.href = "${url}/bctc/cdkt/chitieu/danhsach";
									} catch (e) {
									}
								}
							}
						});
	});
</script>

<h4>Danh sách các chỉ tiêu của bảng cân đối kế toán</h4>
<span><i>${props.getCauHinh(PropCont.CHE_DO_KE_TOAN).giaTri}</i></span>

<div class="pull-right">
	<a href="${url}/bctc/cdkt/chitieu/capcao/taomoi"
		class="btn btn-info btn-sm"> <span
		class="glyphicon glyphicon-plus"></span> Tạo mới chỉ tiêu cấp cao
	</a> <a href="${url}/bctc/cdkt/chitieu/capthap/taomoi"
		class="btn btn-info btn-sm"> <span
		class="glyphicon glyphicon-plus"></span> Tạo mới chỉ tiêu cấp thấp
	</a>
</div>
<br />
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
				<tr id="${bai.assetCode}" data-asset-code="${bai.assetCode}"
					data-name="chiTieuCao"
					data-remove-url="${url}/bctc/cdkt/chitieu/capcao/xoa"
					data-save-url="${url}/bctc/cdkt/chitieu/capcao/capnhat">
					<c:choose>
						<c:when test="${not empty bai.childs and bai.childs.size()>0}">
							<td>${bai.assetCode}&nbsp;-&nbsp;<span
								class="cell-editable dis-removable" data-field="assetName">${bai.assetName}</span></td>
						</c:when>
						<c:when
							test="${not empty bai.taiKhoanDs and bai.taiKhoanDs.size()>0}">
							<td>${bai.assetCode}&nbsp;-&nbsp;<span
								class="cell-editable dis-removable" data-field="assetName">${bai.assetName}</span></td>
						</c:when>
						<c:otherwise>
							<td>${bai.assetCode}&nbsp;-&nbsp;<span class="cell-editable"
								data-field="assetName">${bai.assetName}</span></td>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${bai.soDu==LoaiTaiKhoan.NO}">
							<td>Nợ</td>
						</c:when>
						<c:otherwise>
							<td>Có</td>
						</c:otherwise>
					</c:choose>
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