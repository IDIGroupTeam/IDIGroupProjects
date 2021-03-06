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
		$("#mainFinanceForm").attr("action", "${url}/candoiketoan");
		$("#mainFinanceForm").attr("method", "POST");

		$("button[id^=page]").each(function(i, el) {
			$(this).click(function() {
				if($(this).text()!=${pageIndex}){
					$("#pageIndex").val($(this).text());
					$("#mainFinanceForm").submit();
				}
			});
		});
		
		$("#firstPage").click(function(){
			$("#pageIndex").val(1);
			$("#mainFinanceForm").submit();
		});
		
		$("#previousPage").click(function(){
			$("#pageIndex").val(${pageIndex-1});
			$("#mainFinanceForm").submit();
		});
		
		$("#nextPage").click(function(){
			$("#pageIndex").val(${pageIndex+1});
			$("#mainFinanceForm").submit();	
		});
		
		$("#lastPage").click(function(){
			$("#pageIndex").val(${totalPages});
			$("#mainFinanceForm").submit();	
		});
		
		$("#numberRecordsOfPage").change(function(){
			$("#pageIndex").val(1);
			$("#totalPages").val(0);
			$("#totalRecords").val(0);
			$("#mainFinanceForm").submit();
		});
		
		/* $("#balanceAssetTbl").Tabledit({
			editButton: false,
            removeButton: false,
            deleteButton: false,
            hideIdentifier: true,
			columns: {
				identifier: [[2, "changedAssetCodes"],[7,"changedAssetPeriods"]],
				editable: [[4, "startValues"], [5, "endValues"]]
			}
		}); */
	});
</script>

<h4>Bảng cân đối kế toán</h4>

<div class="form-group">
	<table style="width: 100%;">
		<tr>
			<td><span>Tìm được ${totalRecords} bản ghi</span></td>
			<td><span>Tổng số trang: ${totalPages}</span></td>
			<td><form:hidden id="pageIndex" path="pageIndex" /> <form:hidden
					id="totalPages" path="totalPages" /> <form:hidden
					id="totalRecords" path="totalRecords" /></td>
			<td>Trang:</td>
			<td>
				<div class="btn-group btn-group-md">
					<c:choose>
						<c:when test="${pageIndex==1}">
							<button id="firstPage" type="button"
								class="btn btn-default disabled">Đầu</button>
							<button id="previousPage" type="button"
								class="btn btn-default disabled">Trước</button>
						</c:when>
						<c:otherwise>
							<button id="firstPage" type="button" class="btn btn-default">Đầu</button>
							<button id="previousPage" type="button" class="btn btn-default">Trước</button>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${totalPages<=3}">
							<c:forEach begin="1" end="${totalPages}" varStatus="status">
								<c:choose>
									<c:when test="${status.index==pageIndex}">
										<button id="page${status.index}" type="button"
											class="btn btn-default active">${status.index}</button>
									</c:when>
									<c:otherwise>
										<button id="page${status.index}" type="button"
											class="btn btn-default">${status.index}</button>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${pageIndex==1}">
									<button id="page1" type="button" class="btn btn-default active">1</button>
									<button id="page2" type="button" class="btn btn-default">2</button>
									<button id="page3" type="button" class="btn btn-default">3</button>
								</c:when>
								<c:when test="${pageIndex==totalPages}">
									<button id="page${totalPages-2}" type="button"
										class="btn btn-default">${totalPages-2}</button>
									<button id="page${totalPages-1}" type="button"
										class="btn btn-default">${totalPages-1}</button>
									<button id="page${totalPages}" type="button"
										class="btn btn-default active">${totalPages}</button>
								</c:when>
								<c:otherwise>
									<c:forEach begin="${pageIndex - 1}" end="${pageIndex + 1}"
										varStatus="status">
										<c:choose>
											<c:when test="${status.index==pageIndex}">
												<button id="page${status.index}" type="button"
													class="btn btn-default active">${status.index}</button>
											</c:when>
											<c:otherwise>
												<button id="page${status.index}" type="button"
													class="btn btn-default">${status.index}</button>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${pageIndex==totalPages}">
							<button id="nextPage" type="button"
								class="btn btn-default disabled">Sau</button>
							<button id="lastPage" type="button"
								class="btn btn-default disabled">Cuối</button>
						</c:when>
						<c:otherwise>
							<button id="nextPage" type="button" class="btn btn-default">Sau</button>
							<button id="lastPage" type="button" class="btn btn-default">Cuối</button>
						</c:otherwise>
					</c:choose>
				</div>
			</td>
			<td>Số bản ghi trong một trang:</td>
			<td><form:select id="numberRecordsOfPage"
					path="numberRecordsOfPage" class="form-control">
					<form:option value="25">25</form:option>
					<form:option value="50">50</form:option>
					<form:option value="100">100</form:option>
					<form:option value="200">200</form:option>
				</form:select></td>
		</tr>
	</table>
</div>

<i class="pull-right">(*): Đơn vị: VND</i>

<table id="balanceAssetTbl"
	class="table table-bordered table-hover balance-list">
	<thead>
		<tr>
			<th>STT</th>
			<th>Tài sản</th>
			<th>Mã số</th>
			<th>Thuyết minh</th>
			<th>Số đầu kỳ (*)</th>
			<th>Số cuối kỳ (*)</th>
			<th>Mức thay đổi</th>
			<th>Thời gian</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${bads}" var="bad" varStatus="status">
			<tr>
				<td>${(pageIndex-1)*numberRecordsOfPage+status.index+1}</td>
				<td>${bad.asset.assetName}</td>
				<td>${bad.asset.assetCode}</td>
				<td>${bad.asset.note}</td>
				<td><c:if test="${bad.startValue!=0}">
						<fmt:formatNumber value="${bad.startValue}" type="NUMBER"
							maxFractionDigits="1"></fmt:formatNumber>
					</c:if></td>
				<td><c:if test="${bad.endValue!=0}">
						<fmt:formatNumber value="${bad.endValue}" type="NUMBER"
							maxFractionDigits="1"></fmt:formatNumber>
					</c:if></td>
				<td><c:if test="${bad.changedRatio!=0}">
						<fmt:formatNumber value="${bad.changedRatio}" type="PERCENT"
							maxFractionDigits="1" maxIntegerDigits="3"></fmt:formatNumber>
					</c:if></td>
				<td>Tháng <fmt:formatDate value="${bad.period}"
						pattern="M/yyyy" type="Date" dateStyle="SHORT" />
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
