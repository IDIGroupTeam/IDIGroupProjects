package com.idi.task.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.idi.task.bean.Department;

public class DepartmentMapper implements RowMapper<Department> {

	public Department mapRow(ResultSet rs, int nowNum) throws SQLException {
		String departmentId = rs.getString("DEPARTMENT_ID");
		String departmentName = rs.getString("DEPARTMENT_NAME");
		String description = rs.getString("DESCRIPTION");

		return new Department(departmentId, departmentName, description);
	}

}
