package com.idi.hr.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.idi.hr.bean.Timekeeping;

public class TimekeepingDataMapper implements RowMapper<Timekeeping> {

	public Timekeeping mapRow(ResultSet rs, int nowNum) throws SQLException {

		int employeeId = rs.getInt("EMPLOYEE_ID");
		String employeeName = rs.getString("EMPLOYEE_NAME");
		Date date = rs.getDate("DATE");
		String department = rs.getString("DEPARTMENT");
		String title = rs.getString("JOB_TITLE");
		String timeIn = rs.getString("TIME_IN");
		String timeOut = rs.getString("TIME_OUT");
		String comment = rs.getString("COMMENT");

		return new Timekeeping(employeeId, employeeName, date, department, title, timeIn, timeOut, comment);
	}
}
