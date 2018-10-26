package com.idi.task.login.dao.impl;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.idi.task.common.PropertiesManager;
import com.idi.task.login.bean.UserBean;
import com.idi.task.login.bean.UserLogin;
import com.idi.task.login.bean.UserRoleBean;
import com.idi.task.login.dao.UserRoleDAO;
import com.idi.task.login.mapper.UserMapper;
import com.idi.task.login.mapper.UserRoleMapper;

public class UserRoleDAOImpl implements UserRoleDAO {
	private static final Logger  logger = Logger.getLogger(UserRoleDAOImpl.class);
 	private JdbcTemplate jdbcTmpl;
    
   public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}
	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}
	
    public UserRoleDAOImpl() {
	   
    }
   
   public UserRoleDAOImpl (DataSource dataSource) {
	   this.jdbcTmpl = new JdbcTemplate(dataSource);
   }

	PropertiesManager properties = new PropertiesManager("task.properties");
	
	@Override
	public UserRoleBean getUserRoleByidUser(int idUser) {
		String sql = properties.getProperty("GET_USER_ROLE_BY_IDUSER").toString();
		logger.info("GET_USER_ROLE_BY_IDUSER query=" +sql);
		Object[] params = new Object[] { idUser };
		UserRoleBean userRole = new UserRoleBean();
		if (idUser > 0 ) {
			UserRoleMapper userRM  = new UserRoleMapper();
			userRole = jdbcTmpl.queryForObject(sql, params,  userRM );
		}
		
		return userRole;
	}
	
	@Override
	public UserLogin getAUserLoginFull(String username) {
		//String sql = properties.getProperty("GET_FULL_A_USER_BY_USERNAME").toString();
		//logger.info("GET_FULL_A_USER_BY_USERNAME query= " + sql);
	
		UserLogin usrLogin  = new  UserLogin();
		
		if (username != null && username.length() >0) {
			String sql1= properties.getProperty("GET_USER_BY_USERNAME").toString();
			logger.info("GET_USER_BY_USERNAME query=" + sql1);		
			Object[] params1 = new Object[] {username};
			UserBean usrB = jdbcTmpl.queryForObject(sql1, params1, new UserMapper());
			
			usrLogin.setId(usrB.getId());
			usrLogin.setEnable(usrB.getEnabled());
			usrLogin.setPassword(usrB.getPassword());
			usrLogin.setUsername(usrB.getUsername());
			usrLogin.setUserID(usrB.getUserID());
			
			if (usrB.getId() > 0) {
				
				String sql2 = properties.getProperty("GET_USER_ROLE_BY_IDUSER").toString();
				logger.info("GET_USER_ROLE_BY_IDUSER query=" + sql2);		
				Object[] params2 = new Object[] {usrB.getId()};	
				UserRoleBean usrRB  = jdbcTmpl.queryForObject(sql2, params2, new UserRoleMapper());
				
				usrLogin.setRoleID(usrRB.getRoleID());
				usrLogin.setSecondRoleID(usrRB.getSecondRoleID());
				usrLogin.setThirdRoleID(usrRB.getThirdRoleID());
				usrLogin.setUser_Role(usrRB.getUserRole());
			}
			
		}
		
		return usrLogin;
	}

}
