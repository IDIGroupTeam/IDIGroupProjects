package com.idi.hr.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.idi.hr.bean.LeaveInfo;

public class LeaveInfoMapper implements RowMapper<LeaveInfo> {

	public LeaveInfo mapRow(ResultSet rs, int nowNum) throws SQLException {

		int employeeId = rs.getInt("EMPLOYEE_ID");
		Date date = rs.getDate("DATE");
		String leaveType = rs.getString("LEAVE_TYPE");
		String timeValue = rs.getString("TIME_VALUE");
		String comment = rs.getString("COMMENT");
		
		String employeeName = rs.getString("FULL_NAME");
		String department = rs.getString("DEPARTMENT");
		String title = rs.getString("JOB_TITLE");
		return new LeaveInfo(employeeId, date, leaveType, timeValue, comment, employeeName, department, title);
	}
}
