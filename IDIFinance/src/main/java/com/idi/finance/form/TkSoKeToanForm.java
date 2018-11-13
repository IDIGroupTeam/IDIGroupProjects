package com.idi.finance.form;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.idi.finance.bean.bctc.KyKeToanCon;
import com.idi.finance.bean.hanghoa.HangHoa;
import com.idi.finance.bean.hanghoa.KhoHang;
import com.idi.finance.bean.kyketoan.KyKeToan;

public class TkSoKeToanForm {
	private int loaiKy = KyKeToanCon.NAN;
	private String taiKhoan;
	private int maDt;
	private KyKeToan kyKeToan;
	private String[] doiTuongDs;
	private Date dau;
	private Date cuoi;
	private List<String> loaiCts;
	private int doSau = 2;
	private HangHoa hangHoa = new HangHoa();
	private KhoHang kho = new KhoHang();

	public int getLoaiKy() {
		return loaiKy;
	}

	public void setLoaiKy(int loaiKy) {
		this.loaiKy = loaiKy;
	}

	public String getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(String taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

	public int getMaDt() {
		return maDt;
	}

	public void setMaDt(int maDt) {
		this.maDt = maDt;
	}

	public KyKeToan getKyKeToan() {
		return kyKeToan;
	}

	public void setKyKeToan(KyKeToan kyKeToan) {
		this.kyKeToan = kyKeToan;
	}

	public String[] getDoiTuongDs() {
		return doiTuongDs;
	}

	public void setDoiTuongDs(String[] doiTuongDs) {
		this.doiTuongDs = doiTuongDs;
	}

	public Date getDau() {
		return dau;
	}

	public void setDau(Date dau) {
		this.dau = dau;
	}

	public Date getCuoi() {
		if (cuoi != null) {
			try {
				Calendar cal = Calendar.getInstance(Locale.FRANCE);
				cal.setTime(cuoi);

				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);

				cal.add(Calendar.DATE, 1);

				cal.setTimeInMillis(cal.getTimeInMillis() - 1);
				cuoi = cal.getTime();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return cuoi;
	}

	public void setCuoi(Date cuoi) {
		this.cuoi = cuoi;
	}

	public int getDoSau() {
		return doSau;
	}

	public void setDoSau(int doSau) {
		this.doSau = doSau;
	}

	public HangHoa getHangHoa() {
		return hangHoa;
	}

	public void setHangHoa(HangHoa hangHoa) {
		this.hangHoa = hangHoa;
	}

	public KhoHang getKho() {
		return kho;
	}

	public void setKho(KhoHang kho) {
		this.kho = kho;
	}

	public List<String> getLoaiCts() {
		return loaiCts;
	}

	public void setLoaiCts(List<String> loaiCts) {
		this.loaiCts = loaiCts;
	}

	public void themLoaiCt(String loaiCt) {
		if (loaiCt == null)
			return;

		loaiCt = loaiCt.trim();
		if (loaiCts == null)
			loaiCts = new ArrayList<String>();

		if (!loaiCts.contains(loaiCt)) {
			loaiCts.add(loaiCt);
		}
	}
}
