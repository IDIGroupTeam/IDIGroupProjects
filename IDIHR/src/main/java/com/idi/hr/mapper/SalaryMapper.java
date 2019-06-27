package com.idi.hr.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.idi.hr.bean.Salary;

public class SalaryMapper implements RowMapper<Salary> {

	public Salary mapRow(ResultSet rs, int nowNum) throws SQLException {
		int employeeId = rs.getInt("EMPLOYEE_ID");
		String fullName = rs.getString("FULL_NAME");
		String salary = rs.getString("SALARY");
		String moneyType = rs.getString("MONEY_TYPE");
		String bankNo = rs.getString("BANK_NO");
		String bankName = rs.getString("BANK_NAME");
		String bankBranch = rs.getString("BANK_BRANCH");
		String department = rs.getString("DEPARTMENT");
		String jobTitle = rs.getString("JOB_TITLE");
		String description = rs.getString("COMMENT");
		
		return new Salary(employeeId, fullName, salary, moneyType, bankNo, bankName, bankBranch, department, jobTitle, description);
	}
}
