package com.idi.task.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.idi.task.bean.TaskCategory;

public class TaskCategoryMapper implements RowMapper<TaskCategory> {

	@Override
	public TaskCategory mapRow(ResultSet rs, int nowNum) throws SQLException {

		String categoryId = rs.getString("CATEGORY_ID");
		String categoryName = rs.getString("CATEGORY_NAME");
		String active = rs.getString("ACTIVE");
		String des = rs.getString("DESCRIPTION");

		return new TaskCategory(categoryId, categoryName, active, des);
	}
}
