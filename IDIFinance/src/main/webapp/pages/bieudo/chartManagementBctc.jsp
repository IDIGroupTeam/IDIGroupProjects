<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://cewolf.sourceforge.net/taglib/cewolf.tld"
	prefix="cewolf"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<h4>Cấu hình kỳ - báo cáo tài chính</h4>

<div class="table-responsive">
	<table id="dataTable" class="table table-bordered table-hover">
		<thead>
			<tr>
				<th class="text-center">Tháng</th>
				<th class="text-center">Báo cáo tài chính</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${kpiKyBctcs}" var="kpiKyBctc" varStatus="status">
				<tr>
					<td>${kpiKyBctc.kpiKy.value}</td>
					<td><c:if test="${not empty kpiKyBctc.bctc}">${kpiKyBctc.bctc.tieuDe}</c:if>
					</td>
					<td></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>