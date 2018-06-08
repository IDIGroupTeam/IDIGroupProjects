package com.idi.task.form;

import java.io.Serializable;

public class SendReportForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9180916579447198829L;

	private String sendTo;
	private String subject;
	private String content;
	private String sendFrom;
	
	public String getSendTo() {
		return sendTo;
	}
	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSendFrom() {
		return sendFrom;
	}
	public void setSendFrom(String sendFrom) {
		this.sendFrom = sendFrom;
	}
	
	
}
