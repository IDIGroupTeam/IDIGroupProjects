package com.idi.task.login;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.idi.task.login.dao.impl.UserDAOImpl;

public class LoginUtils {
	 private static UserDAOImpl userDao=null;;
	 public void encode(String password) {

	        for(int i = 0; i < 10; i++) {

	            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	            String encodedPassword = passwordEncoder.encode(password);

	            System.out.println(encodedPassword);

	        }
	 }     
	 public static void main(String[] args) {
		 System.out.println("===BEBIN====");
		 
		
		 
		 System.out.println("employeeID = " + userDao.getEmployeeID("tuyenpx"));
		 System.out.println("===END====");
		 
	 }
}
