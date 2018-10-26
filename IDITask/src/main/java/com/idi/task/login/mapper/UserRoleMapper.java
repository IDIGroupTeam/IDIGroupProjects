package com.idi.task.login.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.idi.task.login.bean.UserRoleBean;

public class UserRoleMapper implements RowMapper<UserRoleBean> {

	@Override
	public UserRoleBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		 int idUser  =rs.getInt("idUser");
		 int employeeID = rs.getInt("UserID");
		 int roleID =rs.getInt("RoleID");
		 int secondRoleID=rs.getInt("SecondRoleID");
		 int thirdRoleID=rs.getInt("ThirdRoleID");
		 String userRole =rs.getString("USER_ROLE");
		 
		 return new UserRoleBean (idUser,employeeID, roleID, secondRoleID, thirdRoleID, userRole);
	}

}
