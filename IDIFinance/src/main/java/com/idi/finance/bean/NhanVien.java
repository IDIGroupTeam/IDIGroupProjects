package com.idi.finance.bean;

public class NhanVien {
	private int employeedId;
	private String fullName;
	private String department;

	public int getEmployeedId() {
		return employeedId;
	}

	public void setEmployeedId(int employeedId) {
		this.employeedId = employeedId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Override
	public String toString() {
		String out = employeedId + "  " + fullName + " " + department;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}
