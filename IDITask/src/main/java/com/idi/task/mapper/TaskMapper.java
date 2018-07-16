package com.idi.task.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Date;

import org.springframework.jdbc.core.RowMapper;

import com.idi.task.bean.Task;

public class TaskMapper implements RowMapper<Task> {

	public Task mapRow(ResultSet rs, int nowNum) throws SQLException {

		int taskId = rs.getInt("TASK_ID");
		String taskName = rs.getString("TASK_NAME");
		int createdBy = rs.getInt("CREATED_BY");
		int ownedBy = rs.getInt("OWNED_BY");
		String ownerName = rs.getString("FULL_NAME");
		int secondOwned = rs.getInt("SECOND_OWNER");
		String subscriber = rs.getString("SUBSCRIBER");
		String related = rs.getString("RELATED");
		int verifyBy = rs.getInt("VERIFY_BY");
		int updateId = rs.getInt("UPDATE_ID");
		Timestamp updateTS = rs.getTimestamp("UPDATE_TS");
		int resolvedBy = rs.getInt("RESOLVED_BY"); // auto not edit show only when completed
		Timestamp creationDate = rs.getTimestamp("CREATION_DATE");
		Date dueDate = rs.getDate("DUE_DATE");
		Timestamp resolutionDate = rs.getTimestamp("RESOLUTION_DATE"); // auto not edit show only when completed
		String type = rs.getString("TYPE");
		String area = rs.getString("AREA"); // viec cua phong kt , cntt hoac ns, ...
		String priority = rs.getString("PRIORITY");
		String status = rs.getString("STATUS");
		String plannedFor = rs.getString("PLANED_FOR");
		String timeSpent = rs.getString("TIME_SPENT");
		String timeSpentType = rs.getString("TIME_SPENT_TYPE"); //h/d/w/m
		String estimate = rs.getString("ESTIMATE");
		String estimateTimeType = rs.getString("ESTIMATE_TIME_TYPE");//h/d/w/m
		String description = rs.getString("DESCRIPTION");
		String reviewComment = rs.getString("REVIEW_COMMENT");

		return new Task(taskId, taskName, createdBy, ownedBy, ownerName, secondOwned, subscriber, related, verifyBy,
				updateId, updateTS, resolvedBy, creationDate, dueDate, resolutionDate, type, area, priority, status, 
				plannedFor, timeSpent, estimate, timeSpentType, estimateTimeType, description, reviewComment);
	}

}
