package com.idi.task.login.dao.impl;

import java.util.List;

//import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.idi.task.common.PropertiesManager;
import com.idi.task.login.bean.RoleBean;
import com.idi.task.login.dao.RoleDAO;

public class RoleDAOImpl implements RoleDAO {

//	private static final Logger  logger = Logger.getLogger(RoleDAOImpl.class);
	
 	private JdbcTemplate jdbcTmpl;
    
   
	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	PropertiesManager properties = new PropertiesManager("task.properties");

	@Override
	public List<RoleBean> getAllRole() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoleBean getRoleByRoleID(int roleID) {
		// TODO Auto-generated method stub
		return null;
	}

}
