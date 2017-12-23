package com.idi.hr.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.idi.hr.bean.LeaveType;

public class LeaveTypeMapper implements RowMapper<LeaveType> {

	public LeaveType mapRow(ResultSet rs, int nowNum) throws SQLException {
		String leaveId = rs.getString("LEAVE_ID");
		String leaveName = rs.getString("LEAVE_NAME");
		String description = rs.getString("COMMENT");

		return new LeaveType(leaveId, leaveName, description);
	}

}
