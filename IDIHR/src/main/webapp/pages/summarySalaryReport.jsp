<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<title>Tập đoàn IDI - Quản lý tiềm lương</title>
<style>
.error-message {
	color: red;
	font-size: 90%;
	font-style: italic;
}
</style>
</head>
	<body>
		<a href="${pageContext.request.contextPath}/salary/prepareSummarySalary"><button class="btn btn-primary btn-sm">Quay	lại lựa chọn thông tin cần thống kê</button></a>
		<br/><br/>
		<table class="table table-bordered">
			<tr>
				<td bgcolor="#E6E6E6">Tổng tiền lương thực trả cho nhân viên:</td>
				<td colspan="3"><fmt:formatNumber value="${salaryReport.finalSalary}" /> đ</td>
			</tr>
			<tr><td colspan="4"></td></tr>
			<tr><td colspan="4" bgcolor="#E6E6E6"><i>Trong đó chi tiết gồm</i></td></tr>
			<tr>	
				<td bgcolor="#FAFAFA">Tổng tiền thưởng:</td>
				<td><fmt:formatNumber value="${salaryReport.bounus}" /> đ</td>
				<td bgcolor="#FAFAFA">Tổng tiền nhân viên đã tạm ứng:</td>
				<td><fmt:formatNumber value="${salaryReport.advancePayed}" /> đ</td>
			</tr>
			<tr>					
				<td bgcolor="#FAFAFA">Tổng tiền trợ cấp/lương chách nhiệm:</td>
				<td><fmt:formatNumber value="${salaryReport.subsidize}" /> đ</td>
				<td bgcolor="#FAFAFA">Tổng tiền thuế thu nhập cá nhân:</td>
				<td><fmt:formatNumber value="${salaryReport.taxPersonal}" /> đ</td>
			</tr>
			<tr>					
				<td colspan="2"></td>

				<td bgcolor="#FAFAFA">Tổng tiền nộp bảo hiểm xã hội của nhân viên:</td>
				<td><fmt:formatNumber value="${salaryReport.payedInsurance}" /> đ</td>
			</tr>
			<tr><td colspan="4"></td></tr>
			<tr>					
				<td bgcolor="#E6E6E6">Tổng lương làm thêm ngoài giờ:</td>
				<td colspan="3"><fmt:formatNumber value="${salaryReport.overTimeSalary}" /> đ</td>
			</tr>	
			<tr><td colspan="4" bgcolor="#FAFAFA"><i>Trong đó chi tiết gồm</i></td></tr>	
			<tr>					
				<td bgcolor="#FAFAFA">Tổng giờ làm ngoài giờ ngày thường:</td>
				<td colspan="3"><fmt:formatNumber value="${salaryReport.overTimeN}" /> giờ</td>
			</tr>
			<tr>					
				<td bgcolor="#FAFAFA">Tổng giờ làm ngoài giờ ngày cuối tuần:</td>
				<td colspan="3"><fmt:formatNumber value="${salaryReport.overTimeW}" /> giờ</td>
			</tr>		
			<tr>					
				<td bgcolor="#FAFAFA">Tổng giờ làm ngoài giờ ngày lễ:</td>
				<td colspan="3"><fmt:formatNumber value="${salaryReport.overTimeH}" /> giờ</td>
			</tr>				
		</table>		
	</body>
</html>