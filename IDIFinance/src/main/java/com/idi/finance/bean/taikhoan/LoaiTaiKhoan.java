package com.idi.finance.bean.taikhoan;

public class LoaiTaiKhoan {
	private String maTk;
	private String tenTk;
	private String maTenTk;
	private String maTkCha;
	private int soDu;

	public String getMaTk() {
		return maTk;
	}

	public void setMaTk(String maTk) {
		this.maTk = maTk;
	}

	public String getTenTk() {
		return tenTk;
	}

	public void setTenTk(String tenTk) {
		this.tenTk = tenTk;
	}

	public String getMaTenTk() {
		return maTenTk;
	}

	public void setMaTenTk(String maTenTk) {
		this.maTenTk = maTenTk;
	}

	public String getMaTkCha() {
		return maTkCha;
	}

	public void setMaTkCha(String maTkCha) {
		this.maTkCha = maTkCha;
	}

	public int getSoDu() {
		return soDu;
	}

	public void setSoDu(int soDu) {
		this.soDu = soDu;
	}

	@Override
	public String toString() {
		String out = maTk + "  " + tenTk;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
}
