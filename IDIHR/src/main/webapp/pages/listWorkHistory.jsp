<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
<title>Lịch sử công tác của nhân viên</title>
<style>
table {
    font-family: arial, sans-serif;
    border-collapse: collapse;
    width: 100%;
}

td, th {
    border: 1px solid #E8E3E3;
    text-align: left;
    padding: 8px;
}

tr:nth-child(even) {
    background-color: #E8E3E3;
}
</style>
<script type="text/javascript">
	function ConfirmDelete() {
	  return confirm("Bạn có chắc chắn muốn xóa không?");
	}
</script>
</head>
<body>
	<a href="${pageContext.request.contextPath}/workHistory/addWorkHistory"><button
			class="btn btn-primary btn-sm">Thêm mới </button></a>
	<br />
	<br />
	<div class="table-responsive">
		<table class="table table-striped">
			<tr>
				<th>Mã NV</th>
				<th>Từ ngày</th>
				<th>Đến ngày</th>				
				<th>Chức vụ</th>
				<th>phòng</th>
				<th>Công ty</th>
				<th>Lương</th>
				<th>Thành tích</th>
				<th>Nhận xét</th>	
							
				<!-- <th>Lịch sử từng NV</th>	 -->			
				<th>Sửa</th>
				<th>Xóa</th>
			</tr>
			<c:forEach var="workHistory" items="${workHistorys}">
				<tr>
					<td>${workHistory.employeeId}</td>
					<td>${workHistory.fromDate}</td>
					<td>${workHistory.toDate}</td>					
					<td>${workHistory.title}</td>
					<td>${workHistory.department}</td>
					<td>${workHistory.company}</td>
					<td>${workHistory.salary}</td>
					<td>${workHistory.achievement}</td>
					<td>${workHistory.appraise}</td>
					
					<%-- <td><a href="listWorkHistorysByEmployee?employeeId=${workHistory.employeeId}">Xem</a></td> --%>
					<td><a href="editWorkHistory?employeeId=${workHistory.employeeId}&fromDate=${workHistory.fromDate}">Sửa</a></td>
					<td><a href="deleteWorkHistory?employeeId=${workHistory.employeeId}&fromDate=${workHistory.fromDate}" Onclick="return ConfirmDelete()">Xóa</a></td>
				</tr>
			</c:forEach>
		</table>
		<c:if test="${not empty message}">
			<div class="alert alert-success">${message}</div>
		</c:if>
	</div>
</body>
</html>