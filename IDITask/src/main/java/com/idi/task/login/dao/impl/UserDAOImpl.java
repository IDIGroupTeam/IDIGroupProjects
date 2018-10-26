package com.idi.task.login.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.idi.task.common.PropertiesManager;
import com.idi.task.login.bean.UserBean;
import com.idi.task.login.mapper.UserMapper;

public class UserDAOImpl extends JdbcDaoSupport {
		private static final Logger  logger = Logger.getLogger(UserDAOImpl.class);
	 	private JdbcTemplate jdbcTmpl;
	    
	 	public JdbcTemplate getJdbcTmpl() {
			return jdbcTmpl;
		}

		public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
			this.jdbcTmpl = jdbcTmpl;
		}

		@Autowired
		public UserDAOImpl(DataSource dataSource) {
			this.setDataSource(dataSource);
		}

		PropertiesManager properties = new PropertiesManager("task.properties");


		public UserBean getUser(String username) {
			String sql = properties.getProperty("GET_USER_BY_USERNAME").toString();
			UserBean userBean= new UserBean();
			logger.info("GET_USER_BY_USERNAME query: " + sql);
			Object[] params = new Object[] { username };
			if (username != null && username.length() >0 ) {
				UserMapper mapper = new UserMapper();
				userBean =(UserBean) jdbcTmpl.queryForObject(sql,params, mapper);
			}else {
			  logger.info("please check $username=" +username); 
			  userBean=null;
			}
			return userBean;
		}

		public int getEmployeeID(String username) {
			logger.info("in getEmployeeID(String username) ");
			String sql =properties.getProperty("GET_USER_BY_USERNAME").toString();
			Object[] params = new Object[] { username };
			int employeeID=0;
			if (username != null && username.length() > 0) {
				UserBean userBean= new UserBean();
				UserMapper mapper = new UserMapper();
				userBean =(UserBean) jdbcTmpl.queryForObject(sql,params, mapper);
				employeeID=userBean.getUserID();
				
			}else {
				  logger.info("please check $username=" +username); 
				  employeeID=0;
				}	
			return employeeID;
		}
		
		public void saveOrUpdate (UserBean userBean) {
			
			/**
			 * 
			 
			if (contact.getId() > 0) {
		        // update
		        String sql = "UPDATE contact SET name=?, email=?, address=?, "
		                    + "telephone=? WHERE contact_id=?";
		        jdbcTemplate.update(sql, contact.getName(), contact.getEmail(),
		                contact.getAddress(), contact.getTelephone(), contact.getId());
		    } else {
		        // insert
		        String sql = "INSERT INTO contact (name, email, address, telephone)"
		                    + " VALUES (?, ?, ?, ?)";
		        jdbcTemplate.update(sql, contact.getName(), contact.getEmail(),
		                contact.getAddress(), contact.getTelephone());
		    }
			 * 
			 */
		
		}

		public void delete(int userID) {
			
			String sql = "DELETE FROM user WHERE UserID=?";
		    jdbcTmpl.update(sql, userID);
		}

		public List<UserBean> listUser() {
			String sql ="SELECT  * FROM user";
			List<UserBean> listUser = jdbcTmpl.query(sql, new RowMapper<UserBean>() {
				
				@Override
				 public UserBean mapRow(ResultSet rs, int rowNum) throws SQLException{
					   UserBean aUser = new UserBean();
					   aUser.setId(rs.getInt("id"));
					   aUser.setUserID(rs.getInt("UserID"));
					   aUser.setEnabled(rs.getInt("enabled"));
					   aUser.setPassword(rs.getString("password"));
					   aUser.setUsername(rs.getString("username"));
					   
					   return aUser;
				}
 		    });
			return listUser;
		}	
		
	    public UserBean getAUserBean (String username) {
	    	 String sql = "SELECT  * FROM user WHERE username='" + username +"'";
		     logger.info("GET_USER_BY_USERNAME query: " + sql);
		     return jdbcTmpl.query(sql, new ResultSetExtractor<UserBean> () {
		    	 
		    	  //@Override
		    	  public UserBean extractData(ResultSet rs) throws SQLException {
		    		  if (rs.next()) {
		    			
		    			  UserBean user = new UserBean();
		    			  user.setUsername(rs.getString("username"));
		    			  user.setUsername(rs.getString("password"));
		    			  return user;
		    		  }
		    		  return null;
		    	  }
		     });
			
	    }
}
