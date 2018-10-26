package com.idi.task.login.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.idi.task.login.bean.UserBean;

public class UserMapper implements RowMapper<UserBean> {

	@Override
	public UserBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		int id = rs.getInt("id");
		String username = rs.getString("username");
		String password=rs.getString("password");
		int userID =rs.getInt("UserID");
		int enabled =rs.getInt("ENABLED");
		
		return new UserBean(id,username,password, userID, enabled);
	}

}
