package com.idi.task.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.RowMapper;

import com.idi.task.bean.TaskComment;

public class TaskCommentMapper implements RowMapper<TaskComment> {

	public TaskComment mapRow(ResultSet rs, int nowNum) throws SQLException {

		int commentIndex = rs.getInt("COMMENT_INDEX");
		int taskId = rs.getInt("TASK_ID");
		int commentedBy = rs.getInt("COMMENTED_BY");
		Timestamp commentTime = rs.getTimestamp("COMMENT_TIME");
		String content = rs.getString("CONTENT");

		return new TaskComment(commentIndex, taskId, commentedBy, commentTime, content);
	}

}
