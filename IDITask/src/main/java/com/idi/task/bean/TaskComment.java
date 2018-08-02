package com.idi.task.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class TaskComment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8373133429412316296L;

	private int commentIndex;
	private int taskId;
	private int commentedBy;
	private String commentedByName;
	private Timestamp commentTime;
	private String content;

	public TaskComment() {
		
	}
	
	public TaskComment(int commentIndex, int taskId, int commentedBy, String commentedByName, Timestamp commentTime, String content) {
		this.commentIndex = commentIndex;
		this.taskId = taskId;
		this.commentedBy = commentedBy;
		this.commentedByName = commentedByName;
		this.commentTime = commentTime;
		this.content = content;
	}
	
	public int getCommentIndex() {
		return commentIndex;
	}

	public void setCommentIndex(int commentIndex) {
		this.commentIndex = commentIndex;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public int getCommentedBy() {
		return commentedBy;
	}

	public void setCommentedBy(int commentedBy) {
		this.commentedBy = commentedBy;
	}

	public String getCommentedByName() {
		return commentedByName;
	}

	public void setCommentedByName(String commentedByName) {
		this.commentedByName = commentedByName;
	}

	public Timestamp getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Timestamp commentTime) {
		this.commentTime = commentTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
