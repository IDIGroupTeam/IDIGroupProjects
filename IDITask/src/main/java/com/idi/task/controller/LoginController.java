package com.idi.task.controller;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.idi.task.login.bean.UserLogin;

//import com.idi.task.login.dao.impl.UserDAOImpl;
import com.idi.task.login.dao.impl.UserRoleDAOImpl;

@Controller
public class LoginController {

	 private static final Logger logger = Logger.getLogger(LoginController.class);
	 
	 @Autowired
	 private UserRoleDAOImpl userRoleDAO;
	
		@RequestMapping(value= {"/login"},method=RequestMethod.GET)
		 public String login(@RequestParam(value="error", required= false) final String error, Model model) {
				String strMsg = "Xin điền thông tin chi tiết của bạn để truy cập";
				model.addAttribute("message", strMsg);
				logger.info("====================================================");
				logger.info("In login controller: " + this.getClass().getName());
				if (error != null) {
					strMsg = strMsg + "/n" + "Bạn nên xem lại tên truy cập hay mật khẩu!";
					model.addAttribute ("message",strMsg );
				}
				return "login";
		  }
		 
		
		@RequestMapping(value = "/admin", method = RequestMethod.GET)
		public String adminPage(Model model) {
				logger.info("in admin controller");
			    return "admin";
		}
		
		@RequestMapping(value = "/logout", method = RequestMethod.GET)
		public String logoutSuccessfulPage(Model model, HttpServletRequest request, HttpServletResponse response) {
			    logger.info(" in logout controller");
				model.addAttribute("title", "Logout");
				model.addAttribute("message", "Dang Xuat");
				Authentication auth =SecurityContextHolder.getContext().getAuthentication();
				if (auth != null) {
					new SecurityContextLogoutHandler().logout(request, response, auth);
				}
			    return "/";
		}
		
		@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
		public String userInfo(Model model, Principal principal) {
			   logger.info("in userinfo controller ");
			    String userName = principal.getName();
			    String strPrin = principal.toString();
			    logger.info("User Name: "+ userName);
			    logger.info("principal: "+ strPrin);
			    model.addAttribute("message", "Thông tin chi tiết về tài khoản");
			    model.addAttribute("userName", userName);
			
				 logger.info(""  + getPrincipal());
				 /**
				 int employeeID =userDao.getEmployeeID(userName);
				 logger.info("EmployeeID is" + employeeID);
				 UserBean usrB =  new UserBean();
				 usrB = userDao.getUser(userName);
				 logger.info("ID in USER table  is" + usrB.getId());
				 UserRoleBean usrRB  = new  UserRoleBean();
				 usrRB=userRoleDAO.getUserRoleByidUser(usrB.getId());
				 logger.info("RoleID in USER_ROLE is " + usrRB.getRoleID() + " / " + usrRB.getUserRole());
				 **/
				 UserLogin userLogin =userRoleDAO.getAUserLoginFull(userName);
				 logger.info("EmployeeID is " + userLogin.getUserID() + " RoleID is " + userLogin.getRoleID());
				 model.addAttribute("maNV", userLogin.getUserID());
				 model.addAttribute("iduser", userLogin.getId());
				 model.addAttribute("RoleID",userLogin.getRoleID());
				 model.addAttribute("UserRole",userLogin.getUser_Role());
				
			     return "userInfo";
		}
		
		/**
		@RequestMapping( value ="/loginSuccess", method=RequestMethod.GET)
		public ModeAndView loginSuccess(HttpServletRequest request, HttpServletResponse respone) throws Exception {
				
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				UserLogin userLogin = (UserLogin) auth.getPrincipal();
				
				return null;
		}*/
		
		
		
		@RequestMapping(value = "/403", method = RequestMethod.GET)
		public String accessDenied(Model model, Principal principal) {
				logger.info( "in 403 controller->>" + principal.getName());
			    if (principal != null) {
			        model.addAttribute("message", "Hi " + principal.getName()
			                + "<br> Bạn không có quyền truy cập vào trang này!");
			    } else {
			        model.addAttribute("message", "Bạn không có quyền truy cập vào trang này!");
			    }
			    
			    return "403";
		 }
		
		 @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
		 public String accessDeniedPage(ModelMap model) {
		        model.addAttribute("user", getPrincipal());
		        return "accessDenied";
		 }

	    public String getPrincipal(){
		        String userName = null;
		        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		        if (principal instanceof UserDetails) {
		            userName = ((UserDetails)principal).getUsername();
		        } else {
		            userName = principal.toString();
		        }
		        return userName;
	    }
		  
}
