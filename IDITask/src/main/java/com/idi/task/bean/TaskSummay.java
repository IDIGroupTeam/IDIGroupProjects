package com.idi.task.bean;

import java.io.Serializable;

public class TaskSummay implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6492129116286723629L;

	private String employeeName;
	private int taskTotal;
	private int taskNew;
	private int taskInprogess;
	private int taskinvalid;	
	private int taskStoped;
	private int taskReviewing;
	private int taskCompleted;
	
	public TaskSummay() {

	}

	public TaskSummay(String employeeName, int taskTotal, int taskNew, int taskInprogess, int taskinvalid,
			int taskStoped, int taskReviewing, int taskCompleted) {
		
		this.employeeName = employeeName;
		this.taskTotal = taskTotal;
		this.taskNew = taskNew;
		this.taskInprogess = taskInprogess;
		this.taskinvalid = taskinvalid;
		this.taskStoped = taskStoped;
		this.taskReviewing = taskReviewing;
		this.taskCompleted = taskCompleted;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public int getTaskTotal() {
		return taskTotal;
	}

	public void setTaskTotal(int taskTotal) {
		this.taskTotal = taskTotal;
	}

	public int getTaskNew() {
		return taskNew;
	}

	public void setTaskNew(int taskNew) {
		this.taskNew = taskNew;
	}

	public int getTaskInprogess() {
		return taskInprogess;
	}

	public void setTaskInprogess(int taskInprogess) {
		this.taskInprogess = taskInprogess;
	}

	public int getTaskinvalid() {
		return taskinvalid;
	}

	public void setTaskinvalid(int taskinvalid) {
		this.taskinvalid = taskinvalid;
	}

	public int getTaskStoped() {
		return taskStoped;
	}

	public void setTaskStoped(int taskStoped) {
		this.taskStoped = taskStoped;
	}

	public int getTaskReviewing() {
		return taskReviewing;
	}

	public void setTaskReviewing(int taskReviewing) {
		this.taskReviewing = taskReviewing;
	}

	public int getTaskCompleted() {
		return taskCompleted;
	}

	public void setTaskCompleted(int taskCompleted) {
		this.taskCompleted = taskCompleted;
	}

}
