package com.idi.hr.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.RowMapper;

import com.idi.hr.bean.WorkingDay;

public class WorkingDayMapper implements RowMapper<WorkingDay> {

	public WorkingDay mapRow(ResultSet rs, int nowNum) throws SQLException {
		String month = rs.getString("MONTH");
		Float workDayOfMonth = rs.getFloat("WORK_DAY");
		String forCompany = rs.getString("FOR_COMPANY");
		String updateId = rs.getString("UPDATE_ID");
		Timestamp updateTs = rs.getTimestamp("UPDATE_TS");
		String comment = rs.getString("COMMENT");

		return new WorkingDay(month, workDayOfMonth, forCompany, updateId, updateTs, comment);
	}
}
