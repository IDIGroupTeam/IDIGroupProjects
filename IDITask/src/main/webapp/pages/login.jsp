<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html>
<head><title>Login</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
 <div class="container" >  
   <h1 style="color:blue"><b>Bạn đang truy cập vào Module: ${pageContext.request.contextPath}</b></h1>
     <!-- /login?error=true -->
     <c:if test="${param.error == 'true'}">
         <!-- <div style="color:red;margin:10px 0px;">  -->
         <div class="alert alert-danger" >
                Truy cập bị lỗi !!!<br />
                Bạn nên xem lại tên truy cập hay mật khẩu! </br>
                Lý do bị lỗi :  ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
                 
         </div>
    </c:if>
   <div style="color:blue"> 
	   <h3>Nhập Tài khoản và Mật khẩu:</h3>  
	   <h2>${mesage}</h2>  
   </div>   
   <form name='f' action="<c:url value='j_spring_security_login' />" method='POST'>
	    <div class="col-sm-7"  >
		    <div class="form-group">
		        <label for="taikhoan">Tài khoản:</label>
		        <input type='text' class="form-control"  placeholder="Xin điền tên tài khoản " name='username' >
		    </div>
		     <div class="form-group">
		        <label for="pwd">Mật khẩu::</label>
		        <input type='password' class="form-control"  placeholder="Xin điền mật khẩu " name='password' >
		    </div>
		    <div class="checkbox" >
		       <label><input type="checkbox" name="remember-me">Ghi nhớ nội dung này</label>
		    </div>
		  <div align="center">  
		   	  <button type="submit" class="btn btn-primary" > Đồng ý </button>
           </div>  
	    </div>
	     <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
      
  </form>
 </div>  
</body>
</html>