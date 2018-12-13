package com.idi.task.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class Task implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7643742167911420180L;
	
	private int taskId;
	private String taskName;
	private int createdBy;
	private int ownedBy;
	private String ownerName;
	private int secondOwned;
	private String subscriber;
	private String related;
	private int verifyBy;
	private int updateId; // auto not edit show only 
	private Timestamp updateTS;
	private int resolvedBy; // auto not edit show only when completed
	private Timestamp creationDate;
	private String dueDate;
	private Timestamp resolutionDate; // auto not edit show only when completed
	private String type;
	private String area; // viec cua phong kt , cntt, ns, ...
	private String priority;
	private String status;
	private String plannedFor;
	private String timeSpent;
	private String month;
	private String year;
	private String timeSpentType;
	private String estimate;
	private String estimateTimeType;
	private String description;
	private String reviewComment;

	public Task() {

	}

	public Task(int taskId, String taskName, int createdBy, int ownedBy, String ownerName, int secondOwned, String subscriber,
			String related, int verifyBy, int updateId, Timestamp updateTS,	int resolvedBy, Timestamp creationDate, String dueDate, 
			Timestamp resolutionDate, String type, String area, String priority, String status, String plannedFor, 
			String timeSpent, String estimate, String timeSpentType, String estimateTimeType, String description, String reviewComment) {

		this.taskId = taskId;
		this.taskName = taskName;
		this.createdBy = createdBy;
		this.ownedBy = ownedBy;
		this.ownerName = ownerName;
		this.secondOwned = secondOwned;
		this.subscriber = subscriber;
		this.related = related;
		this.verifyBy = verifyBy;
		this.updateId = updateId;
		this.updateTS = updateTS;
		this.resolvedBy = resolvedBy; 
		this.creationDate = creationDate;
		this.dueDate = dueDate;
		this.resolutionDate = resolutionDate; 
		this.type = type;
		this.area = area;
		this.priority = priority;
		this.status = status;
		this.plannedFor = plannedFor;
		this.timeSpent = timeSpent;
		this.estimate = estimate; 	
		this.timeSpentType = timeSpentType;
		this.estimateTimeType = estimateTimeType;
		this.description = description;
		this.reviewComment = reviewComment;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public void setOwnedBy(int ownedBy) {
		this.ownedBy = ownedBy;
	}

	public int getOwnedBy() {
		return ownedBy;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public int getSecondOwned() {
		return secondOwned;
	}

	public void setSecondOwned(int secondOwned) {
		this.secondOwned = secondOwned;
	}
	public String getSubscriber() {
		return subscriber;
	}

	public void setSubscriber(String subscriber) {
		this.subscriber = subscriber;
	}

	public String getRelated() {
		return related;
	}

	public void setRelated(String related) {
		this.related = related;
	}

	public int getVerifyBy() {
		return verifyBy;
	}

	public void setVerifyBy(int verifyBy) {
		this.verifyBy = verifyBy;
	}

	public int getUpdateId() {
		return updateId;
	}

	public void setUpdateId(int updateId) {
		this.updateId = updateId;
	}

	public Timestamp getUpdateTS() {
		return updateTS;
	}

	public void setUpdateTS(Timestamp updateTS) {
		this.updateTS = updateTS;
	}

	public int getResolvedBy() {
		return resolvedBy;
	}

	public void setResolvedBy(int resolvedBy) {
		this.resolvedBy = resolvedBy;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public Timestamp getResolutionDate() {
		return resolutionDate;
	}

	public void setResolutionDate(Timestamp resolutionDate) {
		this.resolutionDate = resolutionDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPlannedFor() {
		return plannedFor;
	}

	public void setPlannedFor(String plannedFor) {
		this.plannedFor = plannedFor;
	}

	public String getTimeSpent() {
		return timeSpent;
	}

	public void setTimeSpent(String timeSpent) {
		this.timeSpent = timeSpent;
	}

	public String getEstimate() {
		return estimate;
	}

	public void setEstimate(String estimate) {
		this.estimate = estimate;
	}

	public String getTimeSpentType() {
		return timeSpentType;
	}

	public void setTimeSpentType(String timeSpentType) {
		this.timeSpentType = timeSpentType;
	}

	public String getEstimateTimeType() {
		return estimateTimeType;
	}

	public void setEstimateTimeType(String estimateTimeType) {
		this.estimateTimeType = estimateTimeType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReviewComment() {
		return reviewComment;
	}

	public void setReviewComment(String reviewComment) {
		this.reviewComment = reviewComment;
	}

}
