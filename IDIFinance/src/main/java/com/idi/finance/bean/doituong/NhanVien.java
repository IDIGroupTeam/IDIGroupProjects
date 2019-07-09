package com.idi.finance.bean.doituong;

public class NhanVien {
	private int employeedId;
	private String khNv;
	private String fullName;
	private String department;

	public int getEmployeedId() {
		return employeedId;
	}

	public void setEmployeedId(int employeedId) {
		this.employeedId = employeedId;
	}

	public String getKhNv() {
		return khNv;
	}

	public void setKhNv(String khNv) {
		this.khNv = khNv;
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
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof NhanVien)) {
			return false;
		}

		NhanVien item = (NhanVien) obj;
		if (employeedId != item.getEmployeedId()) {
			return false;
		}

		return true;
	}
}
