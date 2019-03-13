package com.idi.task.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.idi.task.bean.TaskSummay;

public class TaskSummayMapper implements RowMapper<TaskSummay> {

	@Override
	public TaskSummay mapRow(ResultSet rs, int nowNum) throws SQLException {

		String employeeName = rs.getString("FULL_NAME");
		int taskTotal = rs.getInt("TOTAL");
		int taskNew = rs.getInt("NEW");
		int taskInprogess = rs.getInt("INPROGRESS");
		int taskinvalid = rs.getInt("INVALID");
		int taskStoped = rs.getInt("STOPED");
		int taskReviewing = rs.getInt("REVIEWING");
		int taskCompleted = rs.getInt("COMPLETED");

		return new TaskSummay(employeeName, taskTotal, taskNew, taskInprogess, taskinvalid, taskStoped, taskReviewing, taskCompleted);
	}
}
