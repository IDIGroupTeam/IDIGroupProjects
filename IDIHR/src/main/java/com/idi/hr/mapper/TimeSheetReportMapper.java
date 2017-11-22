package com.idi.hr.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.idi.hr.bean.TimeSheetReport;

public class TimeSheetReportMapper implements RowMapper<TimeSheetReport> {

	public TimeSheetReport mapRow(ResultSet rs, int nowNum) throws SQLException {

		int employeeId = rs.getInt("EMPLOYEE_ID");
		String quataLeave = rs.getString("QUATA_LEAVE");
		String restQuata = rs.getString("REST_QUATA");
		String leaveUsed = rs.getString("LEAVE_USED");
		String leaveRemain = rs.getString("LEAVE_REMAIN");
		String seniority = rs.getString("SENIORITY");

		return new TimeSheetReport(employeeId, seniority, quataLeave, restQuata, leaveUsed, leaveRemain);
	}

}
