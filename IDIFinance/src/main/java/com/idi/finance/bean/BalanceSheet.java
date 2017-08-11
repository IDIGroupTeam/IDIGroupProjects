package com.idi.finance.bean;

import java.util.Calendar;
import java.util.Date;

public class BalanceSheet {
	private int assetsId;
	private String assetsName;
	private String assetsCode;
	private String rule;
	private String note;
	private String description;
	private double assetsValue;
	private Date assetsPeriod;

	public int getAssetsId() {
		return assetsId;
	}

	public void setAssetsId(int assetsId) {
		this.assetsId = assetsId;
	}

	public String getAssetsName() {
		return assetsName;
	}

	public void setAssetsName(String assetsName) {
		this.assetsName = assetsName;
	}

	public String getAssetsCode() {
		return assetsCode;
	}

	public void setAssetsCode(String assetsCode) {
		this.assetsCode = assetsCode;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAssetsValue() {
		return assetsValue;
	}

	public void setAssetsValue(double assetsValue) {
		this.assetsValue = assetsValue;
	}

	public Date getAssetsPeriod() {
		return assetsPeriod;
	}

	public void setAssetsPeriod(Date assetsPeriod) {
		this.assetsPeriod = assetsPeriod;
	}

	@Override
	public String toString() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(assetsPeriod);
		String out = assetsName + "  " + rule + " " + assetsCode + "  " + note + " " + assetsValue + "  "
				+ cal.get(Calendar.MONTH);
		return out;
	}
}
