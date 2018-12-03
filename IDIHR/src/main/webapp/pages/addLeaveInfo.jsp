<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Tập đoàn IDI - Chấm công</title>
<style>
.error-message {
	color: red;
	font-size: 90%;
	font-style: italic;
}
</style>

<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"/>
<link href="https://rawgit.com/danielfarrell/bootstrap-combobox/master/css/bootstrap-combobox.css" rel="stylesheet"/>

</head>

<body>
<%-- <script src="${url}/public/js/jquery-3.3.1.js"></script> --%>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="${url}/public/js/bootstrap-combobox.js"></script>
<script>
/*     var $x = jQuery.noConflict(true);
    alert($x.fn.jquery);
	$(function() {
		//alert("xxxx");
		$('#employeeId').on('change', function(){
			if($(this).val()){
			  console.log("You selected: "+$(this).val());
		      alert('Value is ' + $(this).val());
		  }
		});
		
		$('#leaveType').combobox();
		$('#employeeId').combobox();
		//alert("yyyy");
	}); */
	
/* 	$(function() {
	$('.combobox').on('click', function(){
	  var option = $(this).find('option:selected');
		if(option.length){
	    alert('Value is ' + option.val());
	  }
	})
	}); */
</script> 
<!-- 
<script src="https://rawgit.com/danielfarrell/bootstrap-combobox/master/js/bootstrap-combobox.js"></script>

<script src="${url}/public/js/jquery.min.js"></script>
<script src="${url}/public/js/bootstrap.min.js"></script>
<script src="${url}/public/js/bootstrap-combobox.js"></script>

<link href="${url}/public/js/bootstrap.min.css" rel="stylesheet" /> 

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js" type="text/javascript"></script>
<script>
    var $i = jQuery.noConflict();
    alert($i.fn.jquery);
</script> 

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script>
    var $j = jQuery.noConflict();
    alert($j.fn.jquery);
</script> 

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
<script>
    var $k = jQuery.noConflict();
    alert($k.fn.jquery);
</script> 
-->

<a href="${pageContext.request.contextPath}/timekeeping/leaveInfo"><button class="btn btn-lg btn-primary btn-sm">Quay lại danh sách chấm công phát sinh</button></a>
<br/><br/>
	<form:form modelAttribute="leaveInfoForm" method="POST"	action="insertLeaveInfo">		
		<table class="table">
			<tr>
				<td>
					<form:errors path="employeeId" class="error-message" />
					<form:errors path="date" class="error-message" />
					<form:errors path="toDate" class="error-message" />
					<form:errors path="leaveType" class="error-message" />
					<form:errors path="timeValue" class="error-message" />
					<form:errors path="comment" class="error-message" />
					<form:errors path="toDateInvalid" class="error-message" />
					<form:errors path="overLeave" class="error-message" />
					<form:errors path="duplicate" class="error-message" />
				</td>			
			</tr>
		</table>
		<table class="table table-bordered">
			<tbody>
				<tr>
					<td colspan="2" nowrap="nowrap" bgcolor="#F6CED8">Thêm thông tin ngày nghỉ phép, nghỉ ốm, công tác, làm thêm, đi học ....</td>
				</tr>
				<tr>
					<td bgcolor="#FBEFF2">Chọn nhân viên(*):</td>
					<td>						
					<form:select path="employeeId"  class="form-control animated">
							<!-- option>Nhập hoặc chọn nhân viên</option> -->
							<form:options items="${employeeMap}" />
						</form:select></td>						
				</tr>
				<tr>
					<td bgcolor="#FBEFF2">Chọn loại(*):</td>
					<td>
						<form:select path="leaveType" class="form-control animated">
							<form:options items="${leaveTypeMap}" />								
						</form:select>
					</td>
				</tr>
				<tr>
					<td bgcolor="#FBEFF2">Chọn ngày/Từ ngày(*):						
					</td>
					<td><%-- <div class="form-group">
							<div class="input-group date datetime smallform">
								<form:input path="date" class="form-control" readonly="true" />
								<span class="input-group-addon"><span
									class="glyphicon glyphicon-calendar"></span></span>
							</div>
						</div> --%>
						<form:input path="date" type="date" required="required" class="form-control animated"/>
					</td>
				</tr>	
				<tr>
					<td bgcolor="#FBEFF2">Đến ngày(chỉ chọn khi nghỉ/công tác/học tập nhiều ngày):</td>
					<td><form:input path="toDate" type="date" class="form-control animated"/></td>
				</tr>			
				<tr>	
					<td bgcolor="#FBEFF2">Điền số giờ với những t/h tính thời gian như cv bên ngoài/ làm ngoài giờ (*):</td>					
					<td><form:input path="timeValue" step="0.5" type="number" min="0" max="24" class="form-control animated"  required="required"/> (Mặc định để 0.0 => cả ngày = 8h, nửa ngày = 4h)</td>
				</tr>
				<tr>
					<td bgcolor="#FBEFF2">Ghi chú:</td>
					<td colspan="3"><form:textarea path="comment" cols="64" class="form-control animated"/></td>
				<tr>
<%-- 					<tr>	
						<td>${timekeeping.leaveUsed}</td>
						<td>${timekeeping.leaveRemain}</td>
					<tr/> --%>
			</tbody>
		</table>
		<input class="btn btn-lg btn-primary btn-sm" type="submit" value="Lưu" /> 
	</form:form>	
</body>
</html>