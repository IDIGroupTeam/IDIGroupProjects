package com.idi.finance.bean.chungtu;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.idi.finance.bean.LoaiTien;
import com.idi.finance.bean.hanghoa.HangHoa;
import com.idi.finance.bean.hanghoa.KhoHang;
import com.idi.finance.bean.kyketoan.KyKeToan;
import com.idi.finance.bean.soketoan.NghiepVuKeToan;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;

public class ChungTu {
	private static final Logger logger = Logger.getLogger(ChungTu.class);
	// Hằng số cho phần chứng từ: Phiếu thu, chi, báo có, báo nợ
	public static final String TAT_CA = "TAT_CA";
	public static final String CHUNG_TU_PHIEU_THU = "PT";
	public static final String CHUNG_TU_PHIEU_CHI = "PC";
	public static final String CHUNG_TU_BAO_NO = "BN";
	public static final String CHUNG_TU_BAO_CO = "BC";
	public static final String CHUNG_TU_KT_TH = "KTTH";
	public static final String CHUNG_TU_MUA_HANG = "MH";
	public static final String CHUNG_TU_BAN_HANG = "BH";
	public static final String CHUNG_TU_KET_CHUYEN = "KC";

	// Các hằng số dùng cho chứng từ mua và bán hàng
	public static final int HANG_HOA_TRONG_NUOC = 1;
	public static final int HANG_HOA_NUOC_NGOAI = 2;
	public static final int DICH_VU_TRONG_NUOC = 3;
	public static final int DICH_VU_NUOC_NGOAI = 4;

	// Chiều mua/ bán
	public static final int MUA = 1;
	public static final int BAN = -1;

	private int maCt;
	private int soCt;
	private String loaiCt;
	// Mỗi loại chứng từ có thể có thêm phân loại cụ thể
	private int tinhChatCt;
	private int chieu = MUA;
	private Date ngayLap;
	private Date ngayHt;
	private Date ngayTao;
	private Date ngaySua;
	private Date ngayTt;
	private LoaiTien loaiTien = new LoaiTien();
	private Tien soTien = new Tien();
	private String lyDo;
	private int kemTheo;
	private DoiTuong doiTuong;
	private List<TaiKhoan> taiKhoanNoDs;
	private List<TaiKhoan> taiKhoanCoDs;
	private List<NghiepVuKeToan> nvktDs;
	private List<KetChuyenButToan> kcbtDs;

	private TaiKhoan tkThue;
	private LoaiTaiKhoan thanhToan;
	private KhoHang khoBai;
	private List<HangHoa> hangHoaDs;
	private KyKeToan kyKeToan;

	public int getMaCt() {
		return maCt;
	}

	public void setMaCt(int maCt) {
		this.maCt = maCt;
	}

	public int getSoCt() {
		return soCt;
	}

	public void setSoCt(int soCt) {
		this.soCt = soCt;
	}

	public String getLoaiCt() {
		return loaiCt;
	}

	public void setLoaiCt(String loaiCt) {
		this.loaiCt = loaiCt;
	}

	public int getTinhChatCt() {
		return tinhChatCt;
	}

	public void setTinhChatCt(int tinhChatCt) {
		this.tinhChatCt = tinhChatCt;
	}

	public int getChieu() {
		return chieu;
	}

	public void setChieu(int chieu) {
		this.chieu = chieu;
	}

	public Date getNgayLap() {
		return ngayLap;
	}

	public void setNgayLap(Date ngayLap) {
		this.ngayLap = ngayLap;
	}

	public Date getNgayHt() {
		return ngayHt;
	}

	public void setNgayHt(Date ngayHt) {
		this.ngayHt = ngayHt;
	}

	public Date getNgayTao() {
		return ngayTao;
	}

	public void setNgayTao(Date ngayTao) {
		this.ngayTao = ngayTao;
	}

	public Date getNgaySua() {
		return ngaySua;
	}

	public void setNgaySua(Date ngaySua) {
		this.ngaySua = ngaySua;
	}

	public Date getNgayTt() {
		return ngayTt;
	}

	public void setNgayTt(Date ngayTt) {
		this.ngayTt = ngayTt;
	}

	public LoaiTien getLoaiTien() {
		return loaiTien;
	}

	public void setLoaiTien(LoaiTien loaiTien) {
		this.loaiTien = loaiTien;
	}

	public Tien getSoTien() {
		return soTien;
	}

	public void setSoTien(Tien soTien) {
		this.soTien = soTien;
	}

	public String getLyDo() {
		return lyDo;
	}

	public void setLyDo(String lyDo) {
		this.lyDo = lyDo;
	}

	public int getKemTheo() {
		return kemTheo;
	}

	public void setKemTheo(int kemTheo) {
		this.kemTheo = kemTheo;
	}

	public DoiTuong getDoiTuong() {
		return doiTuong;
	}

	public void setDoiTuong(DoiTuong doiTuong) {
		this.doiTuong = doiTuong;
	}

	public void themTaiKhoan(TaiKhoan taiKhoan) {
		if (taiKhoan == null) {
			return;
		}

		if (taiKhoan.getSoDu() == LoaiTaiKhoan.NO) {
			if (taiKhoanNoDs == null)
				taiKhoanNoDs = new ArrayList<>();

			if (!taiKhoanNoDs.contains(taiKhoan)) {
				taiKhoanNoDs.add(taiKhoan);
				soTien.setGiaTri(soTien.getGiaTri()
						+ taiKhoan.getSoTien().getSoTien() * taiKhoan.getSoTien().getLoaiTien().getBanRa());
			}
		} else if (taiKhoan.getSoDu() == LoaiTaiKhoan.CO) {
			if (taiKhoanCoDs == null)
				taiKhoanCoDs = new ArrayList<>();

			if (!taiKhoanCoDs.contains(taiKhoan)) {
				taiKhoanCoDs.add(taiKhoan);
			}
		}

	}

	public void themTaiKhoan(List<TaiKhoan> taiKhoanDs) {
		if (taiKhoanDs == null) {
			return;
		}

		Iterator<TaiKhoan> iter = taiKhoanDs.iterator();
		while (iter.hasNext()) {
			TaiKhoan taiKhoan = iter.next();
			themTaiKhoan(taiKhoan);
		}
	}

	public List<TaiKhoan> getTaiKhoanDs() {
		List<TaiKhoan> taiKhoanDs = new ArrayList<>();
		if (taiKhoanNoDs != null) {
			taiKhoanDs.addAll(taiKhoanNoDs);
		}
		if (taiKhoanCoDs != null) {
			taiKhoanDs.addAll(taiKhoanCoDs);
		}

		return taiKhoanDs;
	}

	public List<TaiKhoan> getTaiKhoanNoDs() {
		return taiKhoanNoDs;
	}

	public void setTaiKhoanNoDs(List<TaiKhoan> taiKhoanNoDs) {
		this.taiKhoanNoDs = taiKhoanNoDs;
	}

	public List<TaiKhoan> getTaiKhoanCoDs() {
		return taiKhoanCoDs;
	}

	public void setTaiKhoanCoDs(List<TaiKhoan> taiKhoanCoDs) {
		this.taiKhoanCoDs = taiKhoanCoDs;
	}

	public List<NghiepVuKeToan> getNvktDs() {
		return nvktDs;
	}

	public void setNvktDs(List<NghiepVuKeToan> nvktDs) {
		this.nvktDs = nvktDs;
	}

	public List<KetChuyenButToan> getKcbtDs() {
		if (kcbtDs == null) {
			kcbtDs = new ArrayList<>();
		}
		return kcbtDs;
	}

	public void setKcbtDs(List<KetChuyenButToan> kcbtDs) {
		this.kcbtDs = kcbtDs;
	}

	public void themKetChuyenButToan(KetChuyenButToan ketChuyenButToan) {
		if (ketChuyenButToan == null)
			return;

		if (kcbtDs == null) {
			kcbtDs = new ArrayList<>();
		}

		int pos = kcbtDs.indexOf(ketChuyenButToan);
		if (pos > -1) {
			KetChuyenButToan ketChuyenButToanTmpl = kcbtDs.get(pos);
			ketChuyenButToanTmpl.tron(ketChuyenButToan);
		} else {
			kcbtDs.add(ketChuyenButToan);
		}
	}

	public void themKetChuyenButToan(List<KetChuyenButToan> ketChuyenButToanDs) {
		if (ketChuyenButToanDs == null) {
			return;
		}

		Iterator<KetChuyenButToan> iter = ketChuyenButToanDs.iterator();
		while (iter.hasNext()) {
			themKetChuyenButToan(iter.next());
		}
	}

	public TaiKhoan getTkThue() {
		return tkThue;
	}

	public void setTkThue(TaiKhoan tkThue) {
		this.tkThue = tkThue;
	}

	public LoaiTaiKhoan getThanhToan() {
		return thanhToan;
	}

	public void setThanhToan(LoaiTaiKhoan thanhToan) {
		this.thanhToan = thanhToan;
	}

	public KhoHang getKhoBai() {
		return khoBai;
	}

	public void setKhoBai(KhoHang khoBai) {
		this.khoBai = khoBai;
	}

	public List<HangHoa> getHangHoaDs() {
		return hangHoaDs;
	}

	public void setHangHoaDs(List<HangHoa> hangHoaDs) {
		soTien = new Tien();
		soTien.setLoaiTien(getLoaiTien());

		if (hangHoaDs != null) {
			Iterator<HangHoa> iter = hangHoaDs.iterator();
			while (iter.hasNext()) {
				HangHoa hangHoa = iter.next();
				double tien = hangHoa.getSoLuong() * hangHoa.getGiaKho().getSoTien();
				soTien.setSoTien(tien + soTien.getSoTien());
			}
			soTien.setGiaTri(soTien.getSoTien() * soTien.getLoaiTien().getBanRa());
		}
		this.hangHoaDs = hangHoaDs;
	}

	public void themHangHoa(HangHoa hangHoa) {
		if (hangHoa == null)
			return;

		if (hangHoaDs == null)
			hangHoaDs = new ArrayList<>();

		if (!hangHoaDs.contains(hangHoa)) {
			hangHoaDs.add(hangHoa);
			if (hangHoa.getGiaKho() != null) {
				double tien = hangHoa.getSoLuong() * hangHoa.getGiaKho().getSoTien();
				soTien.setLoaiTien(this.getLoaiTien());
				soTien.setSoTien(tien + soTien.getSoTien());
				soTien.setGiaTri(soTien.getSoTien() * soTien.getLoaiTien().getBanRa());
			}
		}
	}

	public void themHangHoa(List<HangHoa> hangHoaDs) {
		if (hangHoaDs == null)
			return;

		Iterator<HangHoa> iter = hangHoaDs.iterator();
		while (iter.hasNext()) {
			HangHoa hangHoa = iter.next();
			themHangHoa(hangHoa);
		}
	}

	public int getSoTkLonNhat() {
		int soTkLonNhat = 0;

		if (taiKhoanNoDs == null) {
			if (taiKhoanCoDs != null) {
				soTkLonNhat = taiKhoanCoDs.size();
			}
		} else {
			if (taiKhoanCoDs == null) {
				soTkLonNhat = taiKhoanNoDs.size();
			} else {
				soTkLonNhat = taiKhoanNoDs.size() > taiKhoanCoDs.size() ? taiKhoanNoDs.size() : taiKhoanCoDs.size();
			}
		}

		return soTkLonNhat;
	}

	public int getSoHangHoa() {
		if (hangHoaDs != null)
			return hangHoaDs.size();

		return 0;
	}

	public int getSoDongNkc() {
		int soDongNkc = 1;

		if (loaiCt.equals(CHUNG_TU_KT_TH)) {
			if (taiKhoanNoDs == null) {
				if (taiKhoanCoDs != null) {
					soDongNkc += taiKhoanCoDs.size();
				}
			} else {
				if (taiKhoanCoDs == null) {
					soDongNkc += taiKhoanNoDs.size();
				} else {
					soDongNkc += taiKhoanNoDs.size() + taiKhoanCoDs.size();
				}
			}
		} else {
			soDongNkc += getSoTkLonNhat();
		}

		return soDongNkc;
	}

	public KyKeToan getKyKeToan() {
		return kyKeToan;
	}

	public void setKyKeToan(KyKeToan kyKeToan) {
		this.kyKeToan = kyKeToan;
	}

	@Override
	public String toString() {
		String out = maCt + " " + loaiCt + " " + soCt;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof ChungTu)) {
			return false;
		}

		ChungTu item = (ChungTu) obj;
		try {
			if (maCt != item.getMaCt())
				return false;

			if (soCt != item.getSoCt()) {
				return false;
			}

			if (loaiCt == null) {
				if (item.getLoaiCt() != null)
					return false;
			} else if (item.getLoaiCt() == null) {
				return false;
			} else if (!loaiCt.trim().equals(item.getLoaiCt().trim())) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
