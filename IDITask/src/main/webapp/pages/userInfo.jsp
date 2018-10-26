<%@page session="false"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html>
<head>
<title>${title}</title>
</head>
<body>
  <div class="container" >
    <div class="alert alert-success">
	    <h1><b><u>Thông báo : ${message}</u></b></h1> <br>
	    <h2>Tên tài khoản: ${userName}</h2>
	    <h2>Mã Nhân Viên: ${maNV}</h2>
	    <h2>Mã IDUSER: ${iduser}</h2>
	    <h2>Mã của Quyền truy cập: ${RoleID}</h2>
	    <h2>Quyền truy cập: ${UserRole}</h2>
	  </div>  
   </div>
</body>
</html>