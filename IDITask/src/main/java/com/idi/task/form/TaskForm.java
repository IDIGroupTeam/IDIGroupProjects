package com.idi.task.form;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.idi.task.bean.Task;

public class TaskForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -387100333072628335L;
	
	private int taskId;
	private String taskName;
	private int createdBy;
	private int ownedBy;
	private int secondOwned;
	private int verifyBy;
	private int updateId; // auto not edit show only 
	private Timestamp updateTS;
	private int resolvedBy; // auto not edit show only when completed
	private Timestamp creationDate;
	private Date dueDate;
	private Timestamp resolutionDate; // auto not edit show only when completed
	private String type;
	private String area; // viec cua phong kt , cntt, ns, ...
	private String priority;
	private String status;
	private String plannedFor;
	private String timeSpent;
	private String timeSpentType;
	private String estimate;
	private String estimateTimeType;
	private String description;
	
	//For comment
	private int commentIndex;
	private int commentedBy;
	private Timestamp commentTime;
	private String content;	
	
	private List<Task> listComment;

	// For searching
	private String searchValue;
	
	//Paging
	private int pageIndex;
	private int totalRecords;
	private int numberRecordsOfPage;
	private int totalPages;
	
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

	public int getOwnedBy() {
		return ownedBy;
	}

	public void setOwnedBy(int ownedBy) {
		this.ownedBy = ownedBy;
	}

	public int getSecondOwned() {
		return secondOwned;
	}

	public void setSecondOwned(int secondOwned) {
		this.secondOwned = secondOwned;
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

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
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

	public String getTimeSpentType() {
		return timeSpentType;
	}

	public void setTimeSpentType(String timeSpentType) {
		this.timeSpentType = timeSpentType;
	}

	public String getEstimate() {
		return estimate;
	}

	public void setEstimate(String estimate) {
		this.estimate = estimate;
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

	public int getCommentIndex() {
		return commentIndex;
	}

	public void setCommentIndex(int commentIndex) {
		this.commentIndex = commentIndex;
	}

	public int getCommentedBy() {
		return commentedBy;
	}

	public void setCommentedBy(int commentedBy) {
		this.commentedBy = commentedBy;
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

	public List<Task> getListComment() {
		return listComment;
	}

	public void setListComment(List<Task> listComment) {
		this.listComment = listComment;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getNumberRecordsOfPage() {
		return numberRecordsOfPage;
	}

	public void setNumberRecordsOfPage(int numberRecordsOfPage) {
		this.numberRecordsOfPage = numberRecordsOfPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}	
	
}
