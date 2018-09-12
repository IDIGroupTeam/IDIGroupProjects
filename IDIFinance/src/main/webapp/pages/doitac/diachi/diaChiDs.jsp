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
		// Khởi tạo action/method cho mainFinanceForm form
		$("#mainFinanceForm").attr("action", "${url}/diachi/danhsach");
		$("#mainFinanceForm").attr("method", "POST");

		$("button[id^=page]").each(function(i, el) {
			$(this).click(function() {
				if($(this).text()!=${mainFinanceForm.pageIndex}){
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
			$("#pageIndex").val(${mainFinanceForm.pageIndex-1});
			$("#mainFinanceForm").submit();
		});
		
		$("#nextPage").click(function(){
			$("#pageIndex").val(${mainFinanceForm.pageIndex+1});
			$("#mainFinanceForm").submit();	
		});
		
		$("#lastPage").click(function(){
			$("#pageIndex").val(${mainFinanceForm.totalPages});
			$("#mainFinanceForm").submit();	
		});
		
		$("#numberRecordsOfPage").change(function(){
			$("#pageIndex").val(1);
			$("#totalPages").val(0);
			$("#totalRecords").val(0);
			$("#mainFinanceForm").submit();
		});
	});
</script>

<h4>Danh sách thành phố, quận, phường</h4>

<div class="table-responsive">
	<table class="table">
		<tr>
			<td><span>Tìm được ${mainFinanceForm.totalRecords} bản
					ghi</span></td>
			<td><span>Tổng số trang: ${mainFinanceForm.totalPages}</span></td>
			<td><form:hidden id="pageIndex" path="pageIndex" /> <form:hidden
					id="totalPages" path="totalPages" /> <form:hidden
					id="totalRecords" path="totalRecords" /></td>
			<td>Trang:</td>
			<td>
				<div class="btn-group btn-group-md">
					<c:choose>
						<c:when test="${mainFinanceForm.pageIndex==1}">
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
						<c:when test="${mainFinanceForm.totalPages<=3}">
							<c:forEach begin="1" end="${mainFinanceForm.totalPages}"
								varStatus="status">
								<c:choose>
									<c:when test="${status.index==mainFinanceForm.pageIndex}">
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
								<c:when test="${mainFinanceForm.pageIndex==1}">
									<button id="page1" type="button" class="btn btn-default active">1</button>
									<button id="page2" type="button" class="btn btn-default">2</button>
									<button id="page3" type="button" class="btn btn-default">3</button>
								</c:when>
								<c:when
									test="${mainFinanceForm.pageIndex==mainFinanceForm.totalPages}">
									<button id="page${mainFinanceForm.totalPages-2}" type="button"
										class="btn btn-default">${mainFinanceForm.totalPages-2}</button>
									<button id="page${mainFinanceForm.totalPages-1}" type="button"
										class="btn btn-default">${mainFinanceForm.totalPages-1}</button>
									<button id="page${mainFinanceForm.totalPages}" type="button"
										class="btn btn-default active">${mainFinanceForm.totalPages}</button>
								</c:when>
								<c:otherwise>
									<c:forEach begin="${mainFinanceForm.pageIndex - 1}"
										end="${mainFinanceForm.pageIndex + 1}" varStatus="status">
										<c:choose>
											<c:when test="${status.index==mainFinanceForm.pageIndex}">
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
						<c:when
							test="${mainFinanceForm.pageIndex==mainFinanceForm.totalPages}">
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

	<table class="table table-bordered table-hover">
		<thead>
			<tr>
				<th>Miền</th>
				<th>Mã thành phố</th>
				<th>Thành phố/Tỉnh</th>
				<th>Mã quận</th>
				<th>Quận/Huyện</th>
				<th>Mã phường</th>
				<th>Phường/Xã</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${vungDiaChiDs}" var="mien">
				<c:choose>
					<c:when
						test="${not empty mien.vungDiaChiDs and mien.vungDiaChiDs.size()>0 }">
						<c:forEach items="${mien.vungDiaChiDs}" var="thanhPho">
							<c:choose>
								<c:when
									test="${not empty thanhPho.vungDiaChiDs and thanhPho.vungDiaChiDs.size()>0 }">
									<c:forEach items="${thanhPho.vungDiaChiDs}" var="quan">
										<c:choose>
											<c:when
												test="${not empty quan.vungDiaChiDs and quan.vungDiaChiDs.size()>0 }">
												<c:forEach items="${quan.vungDiaChiDs}" var="phuong">
													<tr>
														<td>${mien.cap.tenCapDc}&nbsp;${mien.tenDc}</td>
														<td>${thanhPho.maDc}</td>
														<td>${thanhPho.cap.tenCapDc}&nbsp;${thanhPho.tenDc}</td>
														<td>${quan.maDc}</td>
														<td>${quan.cap.tenCapDc}&nbsp;${quan.tenDc}</td>
														<td>${phuong.maDc}</td>
														<td>${phuong.cap.tenCapDc}&nbsp;${phuong.tenDc}</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr>
													<td>${mien.cap.tenCapDc}&nbsp;${mien.tenDc}</td>
													<td>${thanhPho.maDc}</td>
													<td>${thanhPho.cap.tenCapDc}&nbsp;${thanhPho.tenDc}</td>
													<td>${quan.maDc}</td>
													<td>${quan.cap.tenCapDc}&nbsp;${quan.tenDc}</td>
													<td></td>
													<td></td>
												</tr>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td>${mien.cap.tenCapDc}&nbsp;${mien.tenDc}</td>
										<td>${thanhPho.maDc}</td>
										<td>${thanhPho.cap.tenCapDc}&nbsp;${thanhPho.tenDc}</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td>${mien.cap.tenCapDc}&nbsp;${mien.tenDc}</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</tbody>
	</table>
</div>
