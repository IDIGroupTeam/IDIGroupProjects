package com.idi.task.login;

//import java.util.ArrayList;
//import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

//import com.idi.task.login.bean.UserLogin;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

	// LogInService logInService; 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		UserLogin userLogin  = null;
//		List authorities =  new ArrayList();
		 
		if (!"".equals(username)) {
		    //	UserProfile userProfile = 
		}else {
			
		}
		return null;
	}

}
