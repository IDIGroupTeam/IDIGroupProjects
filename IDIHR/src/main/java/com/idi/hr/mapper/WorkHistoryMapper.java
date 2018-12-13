package com.idi.hr.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.idi.hr.bean.WorkHistory;

public class WorkHistoryMapper implements RowMapper<WorkHistory> {

	public WorkHistory mapRow(ResultSet rs, int nowNum) throws SQLException {

		int employeeId = rs.getInt("EMPLOYEE_ID");
		String employeeName = rs.getString("FULL_NAME");
		String fromDate = rs.getString("FROM_DATE");
		String toDate = rs.getString("TO_DATE");
		String title = rs.getString("TITLE");
		String department = rs.getString("DEPARTMENT");
		String company = rs.getString("COMPANY");
		String salary = rs.getString("SALARY");
		String achievement = rs.getString("ACHIEVEMENT");
		String appraise = rs.getString("APPRAISE");

		return new WorkHistory(employeeId, employeeName, fromDate, toDate, title, department, company, salary, achievement, appraise);
	}

}
