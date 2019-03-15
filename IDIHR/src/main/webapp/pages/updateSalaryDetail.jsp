<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Tập đoàn IDI - Quản lý tiền lương</title>
<style>
.error-message {
	color: red;
	font-size: 90%;
	font-style: italic;
}
</style>
<!-- Initialize the plugin: -->
<script src="${url}/public/js/jquery.min.js"></script>
<script src="${url}/public/js/common.js"></script>
<script type="text/javascript">
	$(function() {
		moneyConvert("bounus");
		moneyConvert("advancePayed");
		moneyConvert("subsidize");
		moneyConvert("taxPersonal");
		moneyConvert("other");
		moneyConvert("arrears");
	});
</script>
</head>
<body>
	<a href="${pageContext.request.contextPath}/salary/listSalaryDetail?employeeId=${employeeId}">
	<button	class="btn btn-lg btn-primary btn-sm">Quay lại danh sách lương các tháng</button></a>
	<br/><br/>
	<form:form modelAttribute="salaryDetail" method="POST"
		action="updateSalaryDetail" enctype="multipart/form-data">
		<input class="btn btn-lg btn-primary btn-sm" type="submit" value="Sửa và Lưu" name="Sửa và Lưu" /><br/><br/>
		<div class="table table-bordered">
			<c:if test="${not empty workDayDefine}">
				<div class="alert alert-warning">
		     		${workDayDefine}
		    	</div>
		    </c:if>						
			<table class="table">
				<form:hidden path="employeeId"/>
				<form:hidden path="salary"/>
				<form:hidden path="fullName"/>
				<form:hidden path="month"/>
				<form:hidden path="phoneNo"/>
				<form:hidden path="department"/>
				<form:hidden path="jobTitle"/>
				<form:hidden path="year"/>
				<form:hidden path="bankNo"/>
				<form:hidden path="bankName"/>
				<form:hidden path="bankBranch"/>
				<form:hidden path="salaryInsurance"/>
				<tbody>
					<tr>
						<td colspan="6" nowrap="nowrap" bgcolor="#E6E6E6">Thông tin nhân viên</td>
					</tr>
					<tr>
						<td bgcolor="#FAFAFA">Họ tên:</td>
						<td>${salaryDetail.fullName}</td>
						
						<td bgcolor="#FAFAFA">Số điện thoại:</td>
						<td>${salaryDetail.phoneNo}</td>
						
						<td bgcolor="#FAFAFA">Tháng:</td>
						<td>${salaryDetail.month}</td>								
					</tr>
					<tr>
						<td bgcolor="#FAFAFA">Bộ phận:</td>
						<td>${salaryDetail.department}</td>
								
						<td bgcolor="#FAFAFA">Chức vụ:</td>
						<td>${salaryDetail.jobTitle}</td>
						
						<td bgcolor="#FAFAFA">Năm:</td>
						<td>${salaryDetail.year}</td>		
					</tr>
					<tr>
						<td colspan="6" nowrap="nowrap" bgcolor="#E6E6E6">Thông tin tài khoản ngân hàng</td>
					</tr>
					<tr>
						<td bgcolor="#FAFAFA">Số TK:</td>
						<td>${salaryDetail.bankNo}</td>
						
						<td bgcolor="#FAFAFA">Tên NH:</td>
						<td>${salaryDetail.bankName}</td>
						
						<td bgcolor="#FAFAFA">Chi nhánh:</td>
						<td>${salaryDetail.bankBranch}</td>
					</tr>
					<tr>
						<td bgcolor="#FAFAFA">Lương:</td>						
						<c:if test="${not empty salaryDetail.basicSalary}">
							<td>
								<fmt:formatNumber value="${salaryDetail.basicSalary}" /> đ
							</td>
						</c:if>
						<c:if test="${empty salaryDetail.basicSalary}">
							<td>
								<fmt:formatNumber value="${salaryDetail.salary}" /> đ
							</td>
						</c:if>							
						<td bgcolor="#FAFAFA" nowrap="nowrap">Lương BHXH:</td>
						<c:if test="${not empty salaryDetail.salaryInsurance}">
							<td><fmt:formatNumber value="${salaryDetail.salaryInsurance}" /> đ</td> 
						</c:if>
						<c:if test="${empty salaryDetail.salaryInsurance}">
							<td><i>Không tham gia BHXH</i></td> 
						</c:if>				
						<td></td><td></td>	 
					</tr>
					<tr>
						<td bgcolor="#FAFAFA" nowrap="nowrap">Hệ số hoàn thành cv (%):</td>
						<td><form:input path="workComplete" class="form-control bfh-number" min="0" max="999" type="number" value="100" size="4"/></td>
						<td bgcolor="#FAFAFA" nowrap="nowrap" title="Chỉ nhập số ngày nếu tháng đó không làm đủ cả tháng">Số ngày lv thực tế:</td>
						<td><form:input path="workedDay" class="form-control bfh-number" min="0.5" max="31" step="0.5" type="number" title="Chỉ nhập số ngày nếu tháng đó không làm đủ cả tháng"/></td>
						<td></td><td></td>
					</tr>
					<tr>
						<td colspan="4" nowrap="nowrap" bgcolor="#E6E6E6">Các khoản thưởng/phụ cấp/tăng ca/lễ/tết</td>
						<td colspan="2" nowrap="nowrap" bgcolor="#E6E6E6">Các khoản giảm trừ vào lương</td>
					</tr>
					<tr>
						<td bgcolor="#FAFAFA">Thưởng:</td>
						<td><form:input path="bounus" class="form-control animated" maxlength="12" /></td>
						<td></td>
						<td></td>
						<td bgcolor="#FAFAFA">Tạm ứng:</td>
						<td><form:input path="advancePayed" class="form-control animated" maxlength="12" /></td>						
					</tr>
					<tr>
						<td bgcolor="#FAFAFA">Trợ cấp độc hại/trách nhiệm:</td>
						<td><form:input path="subsidize" class="form-control animated" maxlength="12" /></td>
						<td></td>
						<td></td>
						<td bgcolor="#FAFAFA">Thuế TNCN:</td>
						<td><form:input path="taxPersonal" class="form-control animated" maxlength="12" /></td>						
					</tr>
					<tr>
						<td bgcolor="#FAFAFA" nowrap="nowrap" title="Yêu cầu định nghĩa số ngày công chuẩn cho tháng này trước">Làm thêm ngày thường (h):</td>
						<td><form:input path="overTimeN" maxlength="12" class="form-control animated" type="number" min="0"/></td>
						<td nowrap="nowrap"> x <fmt:formatNumber value="${salaryPerHour}" /> x 1.5</td>
						<td>= <fmt:formatNumber value="${salaryDetail.overTimeN*salaryPerHour*1.5}" /> đ</td>
						
						<td bgcolor="#FAFAFA" nowrap="nowrap" title="10.5% trong đó gồm: 8% cho hưu trí, 1% cho thất nghiệp và 1.5% y tế">Đóng BHXH(10.5%):</td>						
						<c:if test="${not empty salaryDetail.salaryInsurance}">
							<td><fmt:formatNumber value="${salaryDetail.salaryInsurance*10.5/100}" /> đ</td> 
						</c:if>
						<c:if test="${empty salaryDetail.salaryInsurance}">
							<td><i>Không tham gia BHXH</i></td> 
						</c:if>						
					</tr>
					<tr>
						<td bgcolor="#FAFAFA" nowrap="nowrap" title="Yêu cầu định nghĩa số ngày công chuẩn cho tháng này trước">Làm thêm cuối tuần (h):</td>
						<td><form:input path="overTimeW" maxlength="12" class="form-control animated" type="number" min="0"/></td>
						<td nowrap="nowrap"> x <fmt:formatNumber value="${salaryPerHour}" /> x 2</td>
						<td>= <fmt:formatNumber value="${salaryDetail.overTimeW*salaryPerHour*2}" /> đ</td>
						
						<td bgcolor="#FAFAFA" nowrap="nowrap">Truy thu</td>
						<td><form:input path="arrears" class="form-control animated" maxlength="12" /></td>						
					</tr>
					<tr>
						<td bgcolor="#FAFAFA" nowrap="nowrap" title="Yêu cầu định nghĩa số ngày công chuẩn cho tháng này trước">Làm thêm ngày lễ (h):</td>
						<td><form:input path="overTimeH" maxlength="12" class="form-control animated" type="number" min="0"/></td>
						<td nowrap="nowrap"> x <fmt:formatNumber value="${salaryPerHour}" /> x 3</td>
						<td>= <fmt:formatNumber value="${salaryDetail.overTimeH*salaryPerHour*3}" /> đ</td>
						
						<td bgcolor="#FAFAFA" nowrap="nowrap"></td>
						<td></td> 											
					</tr>
					<tr>
						<td bgcolor="#FAFAFA">Khác:</td>
						<td><form:input path="other" class="form-control animated" maxlength="12" /></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>						
					</tr>
					<tr>
						<td nowrap="nowrap" bgcolor="#E6E6E6">Tổng thu nhập</td>
						<td nowrap="nowrap" bgcolor="#E6E6E6" colspan="2"><i>							
							<c:if test="${not empty salaryDetail.basicSalary}">
								<fmt:formatNumber value="${salaryDetail.bounus.replaceAll(',', '') + salaryDetail.subsidize.replaceAll(',', '') + salaryDetail.overTimeH*salaryPerHour*3 + salaryDetail.overTimeW*salaryPerHour*2 + salaryDetail.overTimeN*salaryPerHour*1.5 + salaryDetail.other.replaceAll(',', '') + salaryDetail.basicSalary.replaceAll(',', '')}" /> đ
							</c:if>
							<c:if test="${empty salaryDetail.basicSalary}">
								<fmt:formatNumber value="${salaryDetail.bounus.replaceAll(',', '') + salaryDetail.subsidize.replaceAll(',', '') + salaryDetail.overTimeH*salaryPerHour*3 + salaryDetail.overTimeW*salaryPerHour*2 + salaryDetail.overTimeN*salaryPerHour*1.5 + salaryDetail.other.replaceAll(',', '') + salaryDetail.salary.replaceAll(',', '')}" /> đ
							</c:if>	
						</i></td>
						<td nowrap="nowrap" bgcolor="#E6E6E6">Tổng giảm trừ</td>
						<td nowrap="nowrap" bgcolor="#E6E6E6" colspan="2">
							<c:if test="${not empty salaryDetail.salaryInsurance}">
								<i><fmt:formatNumber value="${salaryDetail.salaryInsurance*10.5/100 + salaryDetail.taxPersonal.replaceAll(',', '') + salaryDetail.advancePayed.replaceAll(',', '') + salaryDetail.arrears.replaceAll(',', '')}" /> đ </i>
							</c:if>
							<c:if test="${empty salaryDetail.salaryInsurance}">
								<i><fmt:formatNumber value="${salaryDetail.taxPersonal.replaceAll(',', '') + salaryDetail.advancePayed.replaceAll(',', '') + salaryDetail.arrears.replaceAll(',', '')}" /> đ</i>
							</c:if>							
						</td>
					</tr>
					<tr>
						<td nowrap="nowrap"><b>Lương thực nhận:</b></td>
						<td><b><fmt:formatNumber value="${salaryDetail.finalSalary}" /></b> vnđ</td>
						<c:if test="${not empty salaryDetail.finalSalary}">
							<td nowrap="nowrap"><b>Trạng thái thanh toán:</b></td>
							<td align="center">
								<form:select path="payStatus" class="form-control animated">
									<form:option value="" label="--Chọn trạng thái--"/>
									<form:option value="Đã trả lương" label="Đã trả lương"></form:option>
									<form:option value="Chưa trả lương" label="Chưa trả lương"></form:option> 
								</form:select>
							</td>
						</c:if>
					</tr>
					<tr>
						<td bgcolor="#FAFAFA">Ghi chú:</td>
						<td colspan="5"><form:textarea path="desc" cols="100" class="form-control animated"/></td>
					</tr>
				</tbody>
			</table>
		</div>
		<input class="btn btn-lg btn-primary btn-sm" type="submit" value="Sửa và Lưu" name="Sửa và Lưu" /><br/>
	</form:form>
	<br/>
	<a href="${pageContext.request.contextPath}/salary/listSalaryDetail?employeeId=${employeeId}">
	<button	class="btn btn-lg btn-primary btn-sm">Quay lại danh sách lương các tháng</button></a>
</body>
</html>