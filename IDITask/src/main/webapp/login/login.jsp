<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html>
<head><title>Login</title></head>
<body>
   <h1>Bạn đang truy cập vào Module: ${pageContext.request.contextPath} </h1>
     <!-- /login?error=true -->
     <c:if test="${param.error == 'true'}">
         <div style="color:red;margin:10px 0px;">
                Truy cập bị lỗi !!!<br />
                Bạn nên xem lại tên truy cập hay mật khẩu! </br>
                Lý do bị lỗi :  ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
                 
         </div>
    </c:if>
       
   <h3>Nhập Tài khoản và Mật khẩu:</h3>  
    <h2>${mesage}</h2>  
   <form name='f' action="<c:url value='j_spring_security_login' />" method='POST'>
      <table>
         <tr>
            <td>Tài khoản:</td>
            <td><input type='text' name='username' value=''></td>
         </tr>
         <tr>
            <td>Mật khẩu:</td>
            <td><input type='password' name='password' /></td>
         </tr>
         <tr>
			<td>Ghi nhớ:</td>
			<td><input type="checkbox" name="remember-me" /></td>
		</tr>
         
         <tr>
            <td />
            <td align="right"><input name="submit" type="submit" value=" Đồng ý " /></td>
         </tr>
      </table>
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
  </form>
</body>
</html>