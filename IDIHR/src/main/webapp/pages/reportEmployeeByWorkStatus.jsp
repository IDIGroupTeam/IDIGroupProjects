<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
<title>Danh sách nhân viên</title>
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
</head>
<body>
	<div class="table-responsive">
		<a href="${pageContext.request.contextPath}/"><button
				class="btn btn-lg btn-primary btn-sm">Quay lại danh sách NV</button></a> <br /> <br />
		<table>
			<tr>
				<td width="40%">
					<table height="402">
						<tr>
							<th>Trạng thái LĐ</th>
							<th align="center">Số nhân viên</th>
						</tr>
						<c:forEach var="memberWorkStatus" items="${memberWorkStatus}">
							<tr>
								<td>${memberWorkStatus.key}</td>
								<td align="center">${memberWorkStatus.value}</td>
							</tr>
						</c:forEach>
					</table>
				</td>
				<td width="60%">
					<table>
						<tr>
							<td><img src="${url}${chart}" alt="charts"
								width="750" height="400" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>		
	</div>
</body>
</html>