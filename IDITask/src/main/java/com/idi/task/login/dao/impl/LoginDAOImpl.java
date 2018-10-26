package com.idi.task.login.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;

import com.idi.task.common.PropertiesManager;
import com.idi.task.login.bean.UserLogin;
import com.idi.task.login.dao.LoginDAO;

public class LoginDAOImpl implements LoginDAO {
//	private static final Logger logger  = Logger.getLogger(LoginDAOImpl.class);
	
    private JdbcTemplate jdbcTmpl;
    
   
	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	PropertiesManager properties = new PropertiesManager("task.properties");
	
	@Override
	public int userID(String userName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public UserLogin getUserLogin(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

}
