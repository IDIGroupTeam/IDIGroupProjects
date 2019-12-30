<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<style>
<!--
.sub-content {
	padding-top: 1px;
}
-->
</style>

<script type="text/javascript">
	//Shorthand for $( document ).ready()
	$(function() {
	});
</script>

<div>
	<span class="pull-left heading4">Báo cáo tài chính</span> <a
		href="${url}/bctc/danhsach" class="btn btn-info btn-sm pull-right">Danh
		sách báo cáo tài chính</a>
</div>
<br />
<hr />

<div class="row form-group">
	<label class="control-label col-sm-2" for="tieuDe">Tiêu đề:</label>
	<div class="col-sm-2">${bctc.tieuDe}</div>
	<label class="control-label col-sm-2" for="nguoiLap">Người lập:</label>
	<div class="col-sm-2">${bctc.nguoiLap}</div>
	<label class="control-label col-sm-2" for="giamDoc">Giám đốc:</label>
	<div class="col-sm-2">${bctc.giamDoc}</div>
</div>
<div class="row form-group">
	<label class="control-label col-sm-2" for="soCt">Bắt đầu:</label>
	<div class="col-sm-2">
		<fmt:formatDate value="${bctc.batDau}" pattern="dd/M/yyyy" type="Date"
			dateStyle="SHORT" />
	</div>
	<label class="control-label col-sm-2" for="soCt">Kết thúc:</label>
	<div class="col-sm-2">
		<fmt:formatDate value="${bctc.ketThuc}" pattern="dd/M/yyyy"
			type="Date" dateStyle="SHORT" />
	</div>
	<label class="control-label col-sm-2" for="soCt">Ngày lập:</label>
	<div class="col-sm-2">
		<fmt:formatDate value="${bctc.ngayLap}" pattern="dd/M/yyyy"
			type="Date" dateStyle="SHORT" />
	</div>
</div>
<hr />
<div class="row form-group">
	<div class="col-sm-12">
		<ul class="nav nav-tabs nav-justified">
			<li class="active"><a data-toggle="tab" href="#bangCdkt"
				data-target="#bangCdkt, #cdktPdf">Bảng cân đối kế toán</a></li>
			<li><a data-toggle="tab" href="#bangKqhdkd"
				data-target="#bangKqhdkd, #kqhdkdPdf">Bảng kết quả HĐKD</a></li>
			<li><a data-toggle="tab" href="#bangLctt"
				data-target="#bangLctt, #lcttPdf">Bảng lưu chuyển tiền tệ</a></li>
			<li><a data-toggle="tab" href="#bangCdps"
				data-target="#bangCdps, #cdpsPdf">Bảng cân đối phát sinh</a></li>
			<li><div class="tab-content sub-content pull-right">
					<span id="cdpsPdf" class="fade tab-pane"><a
						href="${url}/bctc/cdps/pdf/${bctc.bangCdps.maBctcCon}"
						class="btn btn-info btn-sm" data-toggle="tooltip"
						title="Cân đối phát sinh"> <span
							class="glyphicon glyphicon-download"></span> Pdf
					</a></span><span id="lcttPdf" class="fade tab-pane"><a
						href="${url}/bctc/luuchuyentt/pdf/${bctc.bangLctt.maBctcCon}"
						class="btn btn-info btn-sm" data-toggle="tooltip"
						title="Lưu chuyển tiền tệ"> <span
							class="glyphicon glyphicon-download"></span> Pdf
					</a></span><span id="kqhdkdPdf" class="fade tab-pane"><a
						href="${url}/bctc/kqhdkd/pdf/${bctc.bangKqhdkd.maBctcCon}"
						class="btn btn-info btn-sm" data-toggle="tooltip"
						title="Kết quả hoạt động kinh doanh"> <span
							class="glyphicon glyphicon-download"></span> Pdf
					</a></span><span id="cdktPdf" class="fade in active tab-pane"><a
						href="${url}/bctc/cdkt/pdf/${bctc.bangCdkt.maBctcCon}"
						class="btn btn-info btn-sm" data-toggle="tooltip"
						title="Cân đối kế toán"> <span
							class="glyphicon glyphicon-download"></span> Pdf
					</a></span>
				</div></li>
		</ul>

		<div class="tab-content sub-content">
			<div id="bangCdkt" class="tab-pane fade in active table-responsive">
				<table class="table table-bordered table-hover">
					<thead>
						<tr>
							<th>STT</th>
							<th>Tài sản</th>
							<th>Mã số</th>
							<th>Thuyết minh</th>
							<th>Số đầu kỳ (*)</th>
							<th>Số cuối kỳ (*)</th>
							<th>Mức thay đổi</th>
							<th>Thời gian (**)</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${bctc.bangCdkt.chiTietDs}" var="bctcChiTiet"
							varStatus="status">
							<tr>
								<td>${status.index}</td>
								<td>${bctcChiTiet.asset.assetName}</td>
								<td>${bctcChiTiet.asset.assetCode}</td>
								<td></td>
								<td class="text-right"><fmt:formatNumber
										value="${bctcChiTiet.giaTriKyTruoc.giaTri}" type="NUMBER"
										maxFractionDigits="0"></fmt:formatNumber></td>
								<td class="text-right"><fmt:formatNumber
										value="${bctcChiTiet.giaTri.giaTri}" type="NUMBER"
										maxFractionDigits="0"></fmt:formatNumber></td>
								<td></td>
								<td></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="bangKqhdkd" class="tab-pane fade table-responsive">
				<table class="table table-bordered table-hover">
					<thead>
						<tr>
							<th>STT</th>
							<th>Tài sản</th>
							<th>Mã số</th>
							<th>Thuyết minh</th>
							<th>Số đầu kỳ (*)</th>
							<th>Số cuối kỳ (*)</th>
							<th>Mức thay đổi</th>
							<th>Thời gian (**)</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${bctc.bangKqhdkd.chiTietDs}" var="bctcChiTiet"
							varStatus="status">
							<tr>
								<td>${status.index}</td>
								<td>${bctcChiTiet.asset.assetName}</td>
								<td>${bctcChiTiet.asset.assetCode}</td>
								<td></td>
								<td class="text-right"><fmt:formatNumber
										value="${bctcChiTiet.giaTriKyTruoc.giaTri}" type="NUMBER"
										maxFractionDigits="0"></fmt:formatNumber></td>
								<td class="text-right"><fmt:formatNumber
										value="${bctcChiTiet.giaTri.giaTri}" type="NUMBER"
										maxFractionDigits="0"></fmt:formatNumber></td>
								<td></td>
								<td></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="bangLctt" class="tab-pane fade table-responsive">
				<table class="table table-bordered table-hover">
					<thead>
						<tr>
							<th>STT</th>
							<th>Tài sản</th>
							<th>Mã số</th>
							<th>Thuyết minh</th>
							<th>Số đầu kỳ (*)</th>
							<th>Số cuối kỳ (*)</th>
							<th>Mức thay đổi</th>
							<th>Thời gian (**)</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${bctc.bangLctt.chiTietDs}" var="bctcChiTiet"
							varStatus="status">
							<tr>
								<td>${status.index}</td>
								<td>${bctcChiTiet.asset.assetName}</td>
								<td>${bctcChiTiet.asset.assetCode}</td>
								<td></td>
								<td class="text-right"><fmt:formatNumber
										value="${bctcChiTiet.giaTriKyTruoc.giaTri}" type="NUMBER"
										maxFractionDigits="0"></fmt:formatNumber></td>
								<td class="text-right"><fmt:formatNumber
										value="${bctcChiTiet.giaTri.giaTri}" type="NUMBER"
										maxFractionDigits="0"></fmt:formatNumber></td>
								<td></td>
								<td></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="bangCdps" class="tab-pane fade table-responsive">
				<table class="table table-bordered table-hover">
					<thead>
						<tr>
							<th class="text-center" rowspan="2">Mã TK</th>
							<th class="text-center" rowspan="2">Tên tài khoản</th>
							<th class="text-center" colspan="2">Số dư đầu kỳ (*)</th>
							<th class="text-center" colspan="2">Phát sinh trong kỳ (*)</th>
							<th class="text-center" colspan="2">Số dư cuối kỳ (*)</th>
						</tr>
						<tr>
							<th class="text-center" style="width: 130px;">Nợ</th>
							<th class="text-center" style="width: 130px;">Có</th>
							<th class="text-center" style="width: 130px;">Nợ</th>
							<th class="text-center" style="width: 130px;">Có</th>
							<th class="text-center" style="width: 130px;">Nợ</th>
							<th class="text-center" style="width: 130px;">Có</th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>
		</div>
	</div>
</div>
