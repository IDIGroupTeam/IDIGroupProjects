package com.idi.hr.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.idi.hr.bean.Timekeeping;

public class TimekeepingMapper implements RowMapper<Timekeeping> {

	public Timekeeping mapRow(ResultSet rs, int nowNum) throws SQLException {

		int employeeId = rs.getInt("EMPLOYEE_ID");
		String employeeName = rs.getString("FULL_NAME");
		Date date = rs.getDate("DATE");
		String timeIn = rs.getString("TIME_IN");
		String timeOut = rs.getString("TIME_OUT");
		String workedTime = rs.getString("WORKED_TIME");
		String comment = rs.getString("COMMENT");
		String department = rs.getString("DEPARTMENT");
		String title = rs.getString("JOB_TITLE");
		String comeLateM = rs.getString("COME_LATE_M");
		String leaveSoonM = rs.getString("LEAVE_SOON_M");
		String comeLateA = rs.getString("COME_LATE_A");
		String leaveSoonA = rs.getString("LEAVE_SOON_A");

		return new Timekeeping(employeeId, employeeName, date, timeIn, timeOut, workedTime, comment, department, title, comeLateM,
				leaveSoonM, comeLateA, leaveSoonA);
	}

}
