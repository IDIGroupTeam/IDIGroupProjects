package com.idi.finance.bean.congviec;

public class NghiepVu {
	private int maNv;
	private String tenNv;
	private String moTa;
	private NghiepVu nvCha;
	private LinhVuc linhVuc;
	private int doKho;

	public int getMaNv() {
		return maNv;
	}

	public void setMaNv(int maNv) {
		this.maNv = maNv;
	}

	public String getTenNv() {
		return tenNv;
	}

	public void setTenNv(String tenNv) {
		this.tenNv = tenNv;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public NghiepVu getNvCha() {
		return nvCha;
	}

	public void setNvCha(NghiepVu nvCha) {
		this.nvCha = nvCha;
	}

	public LinhVuc getLinhVuc() {
		return linhVuc;
	}

	public void setLinhVuc(LinhVuc linhVuc) {
		this.linhVuc = linhVuc;
	}

	public int getDoKho() {
		return doKho;
	}

	public void setDoKho(int doKho) {
		this.doKho = doKho;
	}

	@Override
	public String toString() {
		String out = maNv + " " + tenNv;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}
