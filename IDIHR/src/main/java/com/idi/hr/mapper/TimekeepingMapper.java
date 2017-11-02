package com.idi.hr.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.idi.hr.bean.Timekeeping;

public class TimekeepingMapper implements RowMapper<Timekeeping> {

	public Timekeeping mapRow(ResultSet rs, int nowNum) throws SQLException {

		int employeeId = rs.getInt("EMPLOYEE_ID");
		String employeeName = rs.getString("FULL_NAME");
		String joinDate = rs.getString("JOIN_DATE");
		Date date = rs.getDate("DATE");
		String leaveType = rs.getString("LEAVE_TYPE");
		String overtimeType = rs.getString("OVERTIME_TYPE");
		int notTimekeeping = rs.getInt("NOT_TIMEKEEPING");
		String overTimeValue = rs.getString("OVETIME_VALUE");
		String comment = rs.getString("COMMENT");
		String comeLateM = rs.getString("COME_LATE_M");
		String leaveSoonM = rs.getString("LEAVE_SOON_M");
		String comeLateA = rs.getString("COME_LATE_A");
		String leaveSoonA = rs.getString("LEAVE_SOON_A");
		String quataLeave = rs.getString("QUATA_LEAVE");
		String restQuata = rs.getString("REST_QUATA");
		String leaveUsed = rs.getString("LEAVE_USED");
		String leaveRemain = rs.getString("LEAVE_REMAIN");
		
		return new Timekeeping(employeeId, employeeName, joinDate, date, leaveType,
				overtimeType, notTimekeeping, overTimeValue, comment, //String seniority,
				comeLateM, leaveSoonM, comeLateA, leaveSoonA, quataLeave,
				restQuata, leaveUsed, leaveRemain);
	}

}
