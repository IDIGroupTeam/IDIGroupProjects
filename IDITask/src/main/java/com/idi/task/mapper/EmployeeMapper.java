package com.idi.task.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.idi.task.bean.EmployeeInfo;

public class EmployeeMapper implements RowMapper<EmployeeInfo> {

	@Override
	public EmployeeInfo mapRow(ResultSet rs, int nowNum) throws SQLException {

		int employeeId = rs.getInt("EMPLOYEE_ID");
		String loginAccount = rs.getString("LOGIN_ACCOUNT");
		String fullName = rs.getString("FULL_NAME");		
		String jobTitle = rs.getString("JOB_TITLE");
		String department = rs.getString("DEPARTMENT");
		String departmentName = rs.getString("D.DEPARTMENT_NAME");
		String phoneNo = rs.getString("PHONE_NO");
		String email = rs.getString("EMAIL");
		String workStatus = rs.getString("WORK_STATUS");

		return new EmployeeInfo(employeeId, loginAccount, fullName, jobTitle, 
				department, departmentName, phoneNo, email,	workStatus);
	}
}
