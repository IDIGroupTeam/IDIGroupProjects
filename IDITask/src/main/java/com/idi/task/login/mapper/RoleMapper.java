package com.idi.task.login.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.idi.task.login.bean.RoleBean;

public class RoleMapper implements RowMapper<RoleBean> {

	@Override
	public RoleBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		 int roleID = rs.getInt("RoleID");
		  String roleName= rs.getString("RoleName");
		 
		return new RoleBean(roleID, roleName);
	}

}
