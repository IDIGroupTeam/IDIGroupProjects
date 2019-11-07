package com.idi.hr.bean;

import java.io.Serializable;
import java.text.ParseException;

import com.idi.hr.common.Utils;

public class ProcessInsurance implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4987687152953100642L;

	private String socicalInsuNo;
	private String salarySocicalInsu;	
	private String companyPay;
	private String fromDate;
	private String toDate;
	private String fDate;
	private String tDate;
	private String comment;

	public ProcessInsurance() {

	}

	public ProcessInsurance(String socicalInsuNo, String salarySocicalInsu, String companyPay, String fromDate, String toDate, String comment) {

		this.socicalInsuNo = socicalInsuNo;
		this.salarySocicalInsu = salarySocicalInsu;
		this.companyPay = companyPay;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.comment = comment;
	}

	public String getSalarySocicalInsu() {
		return salarySocicalInsu;
	}

	public void setSalarySocicalInsu(String salarySocicalInsu) {
		this.salarySocicalInsu = salarySocicalInsu;
	}

	public String getSocicalInsuNo() {
		return socicalInsuNo;
	}

	public void setSocicalInsuNo(String socicalInsuNo) {
		this.socicalInsuNo = socicalInsuNo;
	}

	public String getCompanyPay() {
		return companyPay;
	}

	public void setCompanyPay(String companyPay) {
		this.companyPay = companyPay;
	}
	
	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getfDate() throws ParseException {
		if(fromDate != null && fromDate.length() > 0 && fromDate.contains("-"))
			return Utils.convertDateToDisplay(fromDate);
		else
			return fDate;
	}

	public void setfDate(String fDate) throws ParseException {
		if(fromDate != null && fromDate.length() > 0 && fromDate.contains("/"))
			this.fDate = Utils.convertDateToStore(fromDate);
		else
			this.fDate = fDate;
	}

	public String gettDate() throws ParseException {
		if(toDate != null && toDate.length() > 0  && fromDate.contains("-"))
			return Utils.convertDateToDisplay(toDate);
		else
			return tDate;
	}

	public void settDate(String tDate) throws ParseException{
		if(toDate != null && toDate.length() > 0  && fromDate.contains("/"))
			this.tDate = Utils.convertDateToStore(toDate);
		else
			this.tDate = tDate;
	}

}
