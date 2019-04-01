package com.idi.finance.bean.chungtu;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.idi.finance.bean.LoaiTien;
import com.idi.finance.bean.Tien;
import com.idi.finance.bean.doituong.DoiTuong;
import com.idi.finance.bean.hanghoa.HangHoa;
import com.idi.finance.bean.hanghoa.KhoHang;
import com.idi.finance.bean.kyketoan.KyKeToan;
import com.idi.finance.bean.soketoan.NghiepVuKeToan;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;
import com.idi.finance.bean.taikhoan.TaiKhoan;

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
	private DoiTuong doiTuong = new DoiTuong();
	private List<TaiKhoan> taiKhoanNoDs;
	private List<TaiKhoan> taiKhoanCoDs;
	private List<TaiKhoan> taiKhoanKtthDs;
	private List<NhomDinhKhoan> nhomDkDs;
	private List<NghiepVuKeToan> nvktDs;
	private List<KetChuyenButToan> kcbtDs;

	private String nghiepVu;

	private LoaiTaiKhoan thanhToan;
	private KhoHang khoBai;
	private List<HangHoa> hangHoaDs;
	private KyKeToan kyKeToan;

	private TaiKhoan tkThanhtoan;
	private TaiKhoan tkKho;
	private TaiKhoan tkDoanhThu;
	private TaiKhoan tkChiPhi;
	private TaiKhoan tkGiaVon;
	private TaiKhoan tkChietKhau;
	private TaiKhoan tkGiamGia;
	private TaiKhoan tkTraLai;

	private TaiKhoan tkThueGtgt;
	private TaiKhoan tkThueTtdb;
	private TaiKhoan tkThueXk;
	private TaiKhoan tkThueNk;
	private TaiKhoan tkThue;

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
				soTien.setSoTien(soTien.getGiaTri() / taiKhoan.getSoTien().getLoaiTien().getBanRa());
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

	public void themTaiKhoanKtth(TaiKhoan taiKhoan) {
		if (taiKhoan == null) {
			return;
		}

		if (taiKhoanKtthDs == null)
			taiKhoanKtthDs = new ArrayList<>();

		if (!taiKhoanKtthDs.contains(taiKhoan)) {
			taiKhoanKtthDs.add(taiKhoan);
			if (taiKhoan.getSoDu() == LoaiTaiKhoan.NO) {
				soTien.setGiaTri(soTien.getGiaTri() + taiKhoan.getNo().getSoTien() * loaiTien.getBanRa());
				soTien.setSoTien(soTien.getSoTien() + taiKhoan.getNo().getSoTien());
			}
		}
	}

	public void themTaiKhoanKtth(List<TaiKhoan> taiKhoanDs) {
		if (taiKhoanDs == null) {
			return;
		}

		Iterator<TaiKhoan> iter = taiKhoanDs.iterator();
		while (iter.hasNext()) {
			TaiKhoan taiKhoan = iter.next();
			themTaiKhoanKtth(taiKhoan);
		}
	}

	public void themNhomDinhKhoan(NhomDinhKhoan nhomDinhKhoan) {
		if (nhomDinhKhoan == null) {
			return;
		}

		if (nhomDkDs == null)
			nhomDkDs = new ArrayList<>();

		if (!nhomDkDs.contains(nhomDinhKhoan)) {
			nhomDkDs.add(nhomDinhKhoan);
		}
	}

	public void themNhomDinhKhoan(List<NhomDinhKhoan> nhomDinhKhoanDs) {
		if (nhomDinhKhoanDs == null) {
			return;
		}

		Iterator<NhomDinhKhoan> iter = nhomDinhKhoanDs.iterator();
		while (iter.hasNext()) {
			NhomDinhKhoan nhomDinhKhoan = iter.next();
			themNhomDinhKhoan(nhomDinhKhoan);
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

	public List<TaiKhoan> getTaiKhoanKtthDs() {
		return taiKhoanKtthDs;
	}

	public void setTaiKhoanKtthDs(List<TaiKhoan> taiKhoanKtthDs) {
		this.taiKhoanKtthDs = taiKhoanKtthDs;
	}

	public List<NhomDinhKhoan> getNhomDkDs() {
		return nhomDkDs;
	}

	public void setNhomDkDs(List<NhomDinhKhoan> nhomDkDs) {
		this.nhomDkDs = nhomDkDs;
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

	public String getNghiepVu() {
		return nghiepVu;
	}

	public void setNghiepVu(String nghiepVu) {
		this.nghiepVu = nghiepVu;
	}

	public void setKcbtDs(List<KetChuyenButToan> kcbtDs) {
		this.kcbtDs = null;
		themKetChuyenButToan(kcbtDs);
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
		this.hangHoaDs = new ArrayList<>();

		tkThanhtoan = null;
		tkKho = null;
		tkDoanhThu = null;
		tkChiPhi = null;
		tkGiaVon = null;
		tkChietKhau = null;
		tkGiamGia = null;
		tkTraLai = null;

		tkThueGtgt = null;
		tkThueTtdb = null;
		tkThueXk = null;
		tkThueNk = null;
		tkThue = null;

		themHangHoa(hangHoaDs);
	}

	public void themHangHoa(HangHoa hangHoa) {
		if (hangHoa == null)
			return;

		if (hangHoaDs == null)
			hangHoaDs = new ArrayList<>();

		// Cập nhật giá trị trước khi thêm
		capNhatTongHangHoa(hangHoa);

		// Thêm vào danh sách
		int pos = hangHoaDs.indexOf(hangHoa);
		if (pos == -1) {
			hangHoaDs.add(hangHoa);
			if (hangHoa.getGiaKho() != null) {
				double tien = hangHoa.getSoLuong() * hangHoa.getGiaKho().getSoTien();
				soTien.setLoaiTien(this.getLoaiTien());
				soTien.setSoTien(tien + soTien.getSoTien());
				soTien.setGiaTri(soTien.getSoTien() * soTien.getLoaiTien().getBanRa());
			}
		} else {
			// Trộn thêm các tài khoản vào
			HangHoa hangHoaTmpl = hangHoaDs.get(pos);
			hangHoaTmpl.tronTk(hangHoa);
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

	private void capNhatTongHangHoa(HangHoa hangHoa) {
		if (hangHoa == null)
			return;

		// Các tk dùng cho mua bán
		if (hangHoa.getTkThanhtoan() != null && hangHoa.getTkThanhtoan().getLoaiTaiKhoan() != null
				&& hangHoa.getTkThanhtoan().getLoaiTaiKhoan().getMaTk() != null
				&& !hangHoa.getTkThanhtoan().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
			if (tkThanhtoan == null) {
				tkThanhtoan = new TaiKhoan();
				tkThanhtoan.setLoaiTaiKhoan(hangHoa.getTkThanhtoan().getLoaiTaiKhoan());
				tkThanhtoan.setSoDu(hangHoa.getTkThanhtoan().getSoDu());
			}

			int pos = hangHoaDs.indexOf(hangHoa);
			HangHoa hangHoaTmpl = null;
			if (pos > -1) {
				hangHoaTmpl = hangHoaDs.get(pos);
			}

			if (pos == -1 || (hangHoaTmpl != null && hangHoaTmpl.getTkThanhtoan() == null)
					|| (hangHoaTmpl != null && hangHoaTmpl.getTkThanhtoan() != null
							&& hangHoaTmpl.getTkThanhtoan().getSoTien().getSoTien() == 0)) {
				tkThanhtoan.getSoTien().setSoTien(
						tkThanhtoan.getSoTien().getSoTien() + hangHoa.getTkThanhtoan().getSoTien().getSoTien());
				tkThanhtoan.getSoTien().setGiaTri(
						tkThanhtoan.getSoTien().getGiaTri() + hangHoa.getTkThanhtoan().getSoTien().getGiaTri());
			}
		}

		if (hangHoa.getTkKho() != null && hangHoa.getTkKho().getLoaiTaiKhoan() != null
				&& hangHoa.getTkKho().getLoaiTaiKhoan().getMaTk() != null
				&& !hangHoa.getTkKho().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
			if (tkKho == null) {
				tkKho = new TaiKhoan();
				tkKho.setLoaiTaiKhoan(hangHoa.getTkKho().getLoaiTaiKhoan());
				tkKho.setSoDu(hangHoa.getTkKho().getSoDu());
			}

			int pos = hangHoaDs.indexOf(hangHoa);
			HangHoa hangHoaTmpl = null;
			if (pos > -1) {
				hangHoaTmpl = hangHoaDs.get(pos);
			}

			if (pos == -1 || (hangHoaTmpl != null && hangHoaTmpl.getTkKho() == null) || (hangHoaTmpl != null
					&& hangHoaTmpl.getTkKho() == null && hangHoaTmpl.getTkKho().getSoTien().getSoTien() == 0)) {
				tkKho.getSoTien().setSoTien(tkKho.getSoTien().getSoTien() + hangHoa.getTkKho().getSoTien().getSoTien());
				tkKho.getSoTien().setGiaTri(tkKho.getSoTien().getGiaTri() + hangHoa.getTkKho().getSoTien().getGiaTri());
			}
		}

		if (hangHoa.getTkDoanhThu() != null && hangHoa.getTkDoanhThu().getLoaiTaiKhoan() != null
				&& hangHoa.getTkDoanhThu().getLoaiTaiKhoan().getMaTk() != null
				&& !hangHoa.getTkDoanhThu().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
			if (tkDoanhThu == null) {
				tkDoanhThu = new TaiKhoan();
				tkDoanhThu.setLoaiTaiKhoan(hangHoa.getTkDoanhThu().getLoaiTaiKhoan());
				tkDoanhThu.setSoDu(hangHoa.getTkDoanhThu().getSoDu());
			}

			int pos = hangHoaDs.indexOf(hangHoa);
			HangHoa hangHoaTmpl = null;
			if (pos > -1) {
				hangHoaTmpl = hangHoaDs.get(pos);
			}

			if (pos == -1 || (hangHoaTmpl != null && hangHoaTmpl.getTkDoanhThu() == null)
					|| (hangHoaTmpl != null && hangHoaTmpl.getTkDoanhThu() == null
							&& hangHoaTmpl.getTkDoanhThu().getSoTien().getSoTien() == 0)) {
				tkDoanhThu.getSoTien().setSoTien(
						tkDoanhThu.getSoTien().getSoTien() + hangHoa.getTkDoanhThu().getSoTien().getSoTien());
				tkDoanhThu.getSoTien().setGiaTri(
						tkDoanhThu.getSoTien().getGiaTri() + hangHoa.getTkDoanhThu().getSoTien().getGiaTri());
			}
		}

		if (hangHoa.getTkChiPhi() != null && hangHoa.getTkChiPhi().getLoaiTaiKhoan() != null
				&& hangHoa.getTkChiPhi().getLoaiTaiKhoan().getMaTk() != null
				&& !hangHoa.getTkChiPhi().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
			if (tkChiPhi == null) {
				tkChiPhi = new TaiKhoan();
				tkChiPhi.setLoaiTaiKhoan(hangHoa.getTkChiPhi().getLoaiTaiKhoan());
				tkChiPhi.setSoDu(hangHoa.getTkChiPhi().getSoDu());
			}

			int pos = hangHoaDs.indexOf(hangHoa);
			HangHoa hangHoaTmpl = null;
			if (pos > -1) {
				hangHoaTmpl = hangHoaDs.get(pos);
			}

			if (pos == -1 || (hangHoaTmpl != null && hangHoaTmpl.getTkChiPhi() == null) || (hangHoaTmpl != null
					&& hangHoaTmpl.getTkChiPhi() == null && hangHoaTmpl.getTkChiPhi().getSoTien().getSoTien() == 0)) {
				tkChiPhi.getSoTien()
						.setSoTien(tkChiPhi.getSoTien().getSoTien() + hangHoa.getTkChiPhi().getSoTien().getSoTien());
				tkChiPhi.getSoTien()
						.setGiaTri(tkChiPhi.getSoTien().getGiaTri() + hangHoa.getTkChiPhi().getSoTien().getGiaTri());
			}
		}

		if (hangHoa.getTkGiaVon() != null && hangHoa.getTkGiaVon().getLoaiTaiKhoan() != null
				&& hangHoa.getTkGiaVon().getLoaiTaiKhoan().getMaTk() != null
				&& !hangHoa.getTkGiaVon().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
			if (tkGiaVon == null) {
				tkGiaVon = new TaiKhoan();
				tkGiaVon.setLoaiTaiKhoan(hangHoa.getTkGiaVon().getLoaiTaiKhoan());
				tkGiaVon.setSoDu(hangHoa.getTkGiaVon().getSoDu());
			}

			int pos = hangHoaDs.indexOf(hangHoa);
			HangHoa hangHoaTmpl = null;
			if (pos > -1) {
				hangHoaTmpl = hangHoaDs.get(pos);
			}

			if (pos == -1 || (hangHoaTmpl != null && hangHoaTmpl.getTkGiaVon() == null) || (hangHoaTmpl != null
					&& hangHoaTmpl.getTkGiaVon() == null && hangHoaTmpl.getTkGiaVon().getSoTien().getSoTien() == 0)) {
				tkGiaVon.getSoTien()
						.setSoTien(tkGiaVon.getSoTien().getSoTien() + hangHoa.getTkGiaVon().getSoTien().getSoTien());
				tkGiaVon.getSoTien()
						.setGiaTri(tkGiaVon.getSoTien().getGiaTri() + hangHoa.getTkGiaVon().getSoTien().getGiaTri());
			}
		}

		if (hangHoa.getTkChietKhau() != null && hangHoa.getTkChietKhau().getLoaiTaiKhoan() != null
				&& hangHoa.getTkChietKhau().getLoaiTaiKhoan().getMaTk() != null
				&& !hangHoa.getTkChietKhau().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
			if (tkChietKhau == null) {
				tkChietKhau = new TaiKhoan();
				tkChietKhau.setLoaiTaiKhoan(hangHoa.getTkChietKhau().getLoaiTaiKhoan());
				tkChietKhau.setSoDu(hangHoa.getTkChietKhau().getSoDu());
			}

			int pos = hangHoaDs.indexOf(hangHoa);
			HangHoa hangHoaTmpl = null;
			if (pos > -1) {
				hangHoaTmpl = hangHoaDs.get(pos);
			}

			if (pos == -1 || (hangHoaTmpl != null && hangHoaTmpl.getTkChietKhau() == null)
					|| (hangHoaTmpl != null && hangHoaTmpl.getTkChietKhau() == null
							&& hangHoaTmpl.getTkChietKhau().getSoTien().getSoTien() == 0)) {
				tkChietKhau.getSoTien().setSoTien(
						tkChietKhau.getSoTien().getSoTien() + hangHoa.getTkChietKhau().getSoTien().getSoTien());
				tkChietKhau.getSoTien().setGiaTri(
						tkChietKhau.getSoTien().getGiaTri() + hangHoa.getTkChietKhau().getSoTien().getGiaTri());
			}
		}

		if (hangHoa.getTkGiamGia() != null && hangHoa.getTkGiamGia().getLoaiTaiKhoan() != null
				&& hangHoa.getTkGiamGia().getLoaiTaiKhoan().getMaTk() != null
				&& !hangHoa.getTkGiamGia().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
			if (tkGiamGia == null) {
				tkGiamGia = new TaiKhoan();
				tkGiamGia.setLoaiTaiKhoan(hangHoa.getTkGiamGia().getLoaiTaiKhoan());
				tkGiamGia.setSoDu(hangHoa.getTkGiamGia().getSoDu());
			}

			int pos = hangHoaDs.indexOf(hangHoa);
			HangHoa hangHoaTmpl = null;
			if (pos > -1) {
				hangHoaTmpl = hangHoaDs.get(pos);
			}

			if (pos == -1 || (hangHoaTmpl != null && hangHoaTmpl.getTkGiamGia() == null) || (hangHoaTmpl != null
					&& hangHoaTmpl.getTkGiamGia() == null && hangHoaTmpl.getTkGiamGia().getSoTien().getSoTien() == 0)) {
				tkGiamGia.getSoTien()
						.setSoTien(tkGiamGia.getSoTien().getSoTien() + hangHoa.getTkGiamGia().getSoTien().getSoTien());
				tkGiamGia.getSoTien()
						.setGiaTri(tkGiamGia.getSoTien().getGiaTri() + hangHoa.getTkGiamGia().getSoTien().getGiaTri());
			}
		}

		if (hangHoa.getTkTraLai() != null && hangHoa.getTkTraLai().getLoaiTaiKhoan() != null
				&& hangHoa.getTkTraLai().getLoaiTaiKhoan().getMaTk() != null
				&& !hangHoa.getTkTraLai().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
			if (tkTraLai == null) {
				tkTraLai = new TaiKhoan();
				tkTraLai.setLoaiTaiKhoan(hangHoa.getTkTraLai().getLoaiTaiKhoan());
				tkTraLai.setSoDu(hangHoa.getTkTraLai().getSoDu());
			}

			int pos = hangHoaDs.indexOf(hangHoa);
			HangHoa hangHoaTmpl = null;
			if (pos > -1) {
				hangHoaTmpl = hangHoaDs.get(pos);
			}

			if (pos == -1 || (hangHoaTmpl != null && hangHoaTmpl.getTkTraLai() == null) || (hangHoaTmpl != null
					&& hangHoaTmpl.getTkTraLai() == null && hangHoaTmpl.getTkTraLai().getSoTien().getSoTien() == 0)) {
				tkTraLai.getSoTien()
						.setSoTien(tkTraLai.getSoTien().getSoTien() + hangHoa.getTkTraLai().getSoTien().getSoTien());
				tkTraLai.getSoTien()
						.setGiaTri(tkTraLai.getSoTien().getGiaTri() + hangHoa.getTkTraLai().getSoTien().getGiaTri());
			}
		}

		// Các tk thuế
		if (hangHoa.getTkThueGtgt() != null && hangHoa.getTkThueGtgt().getLoaiTaiKhoan() != null
				&& hangHoa.getTkThueGtgt().getLoaiTaiKhoan().getMaTk() != null
				&& !hangHoa.getTkThueGtgt().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
			if (tkThueGtgt == null) {
				tkThueGtgt = new TaiKhoan();
				tkThueGtgt.setLoaiTaiKhoan(hangHoa.getTkThueGtgt().getLoaiTaiKhoan());
				tkThueGtgt.setSoDu(hangHoa.getTkThueGtgt().getSoDu());
			}

			int pos = hangHoaDs.indexOf(hangHoa);
			HangHoa hangHoaTmpl = null;
			if (pos > -1) {
				hangHoaTmpl = hangHoaDs.get(pos);
			}

			if (pos == -1 || (hangHoaTmpl != null && hangHoaTmpl.getTkThueGtgt() == null)
					|| (hangHoaTmpl != null && hangHoaTmpl.getTkThueGtgt() == null
							&& hangHoaTmpl.getTkThueGtgt().getSoTien().getSoTien() == 0)) {
				tkThueGtgt.getSoTien().setSoTien(
						tkThueGtgt.getSoTien().getSoTien() + hangHoa.getTkThueGtgt().getSoTien().getSoTien());
				tkThueGtgt.getSoTien().setGiaTri(
						tkThueGtgt.getSoTien().getGiaTri() + hangHoa.getTkThueGtgt().getSoTien().getGiaTri());
			}
		}

		if (hangHoa.getTkThueTtdb() != null && hangHoa.getTkThueTtdb().getLoaiTaiKhoan() != null
				&& hangHoa.getTkThueTtdb().getLoaiTaiKhoan().getMaTk() != null
				&& !hangHoa.getTkThueTtdb().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
			if (tkThueTtdb == null) {
				tkThueTtdb = new TaiKhoan();
				tkThueTtdb.setLoaiTaiKhoan(hangHoa.getTkThueTtdb().getLoaiTaiKhoan());
				tkThueTtdb.setSoDu(hangHoa.getTkThueTtdb().getSoDu());
			}

			int pos = hangHoaDs.indexOf(hangHoa);
			HangHoa hangHoaTmpl = null;
			if (pos > -1) {
				hangHoaTmpl = hangHoaDs.get(pos);
			}

			if (pos == -1 || (hangHoaTmpl != null && hangHoaTmpl.getTkThueTtdb() == null)
					|| (hangHoaTmpl != null && hangHoaTmpl.getTkThueTtdb() == null
							&& hangHoaTmpl.getTkThueTtdb().getSoTien().getSoTien() == 0)) {
				tkThueTtdb.getSoTien().setSoTien(
						tkThueTtdb.getSoTien().getSoTien() + hangHoa.getTkThueTtdb().getSoTien().getSoTien());
				tkThueTtdb.getSoTien().setGiaTri(
						tkThueTtdb.getSoTien().getGiaTri() + hangHoa.getTkThueTtdb().getSoTien().getGiaTri());
			}
		}

		if (hangHoa.getTkThueXk() != null && hangHoa.getTkThueXk().getLoaiTaiKhoan() != null
				&& hangHoa.getTkThueXk().getLoaiTaiKhoan().getMaTk() != null
				&& !hangHoa.getTkThueXk().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
			if (tkThueXk == null) {
				tkThueXk = new TaiKhoan();
				tkThueXk.setLoaiTaiKhoan(hangHoa.getTkThueXk().getLoaiTaiKhoan());
				tkThueXk.setSoDu(hangHoa.getTkThueXk().getSoDu());
			}

			int pos = hangHoaDs.indexOf(hangHoa);
			HangHoa hangHoaTmpl = null;
			if (pos > -1) {
				hangHoaTmpl = hangHoaDs.get(pos);
			}

			if (pos == -1 || (hangHoaTmpl != null && hangHoaTmpl.getTkThueXk() == null) || (hangHoaTmpl != null
					&& hangHoaTmpl.getTkThueXk() == null && hangHoaTmpl.getTkThueXk().getSoTien().getSoTien() == 0)) {
				tkThueXk.getSoTien()
						.setSoTien(tkThueXk.getSoTien().getSoTien() + hangHoa.getTkThueXk().getSoTien().getSoTien());
				tkThueXk.getSoTien()
						.setGiaTri(tkThueXk.getSoTien().getGiaTri() + hangHoa.getTkThueXk().getSoTien().getGiaTri());
			}
		}

		if (hangHoa.getTkThueNk() != null && hangHoa.getTkThueNk().getLoaiTaiKhoan() != null
				&& hangHoa.getTkThueNk().getLoaiTaiKhoan().getMaTk() != null
				&& !hangHoa.getTkThueNk().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
			if (tkThueNk == null) {
				tkThueNk = new TaiKhoan();
				tkThueNk.setLoaiTaiKhoan(hangHoa.getTkThueNk().getLoaiTaiKhoan());
				tkThueNk.setSoDu(hangHoa.getTkThueNk().getSoDu());
			}

			int pos = hangHoaDs.indexOf(hangHoa);
			HangHoa hangHoaTmpl = null;
			if (pos > -1) {
				hangHoaTmpl = hangHoaDs.get(pos);
			}

			if (pos == -1 || (hangHoaTmpl != null && hangHoaTmpl.getTkThueNk() == null) || (hangHoaTmpl != null
					&& hangHoaTmpl.getTkThueNk() == null && hangHoaTmpl.getTkThueNk().getSoTien().getSoTien() == 0)) {
				tkThueNk.getSoTien()
						.setSoTien(tkThueNk.getSoTien().getSoTien() + hangHoa.getTkThueNk().getSoTien().getSoTien());
				tkThueNk.getSoTien()
						.setGiaTri(tkThueNk.getSoTien().getGiaTri() + hangHoa.getTkThueNk().getSoTien().getGiaTri());
			}
		}

		if (hangHoa.getTkThue() != null && hangHoa.getTkThue().getLoaiTaiKhoan() != null
				&& hangHoa.getTkThue().getLoaiTaiKhoan().getMaTk() != null
				&& !hangHoa.getTkThue().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
			if (tkThue == null) {
				tkThue = new TaiKhoan();
				tkThue.setLoaiTaiKhoan(hangHoa.getTkThue().getLoaiTaiKhoan());
				tkThue.setSoDu(hangHoa.getTkThue().getSoDu());
			}

			int pos = hangHoaDs.indexOf(hangHoa);
			HangHoa hangHoaTmpl = null;
			if (pos > -1) {
				hangHoaTmpl = hangHoaDs.get(pos);
			}

			if (pos == -1 || (hangHoaTmpl != null && hangHoaTmpl.getTkThue() == null) || (hangHoaTmpl != null
					&& hangHoaTmpl.getTkThue() == null && hangHoaTmpl.getTkThue().getSoTien().getSoTien() == 0)) {
				tkThue.getSoTien()
						.setSoTien(tkThue.getSoTien().getSoTien() + hangHoa.getTkThue().getSoTien().getSoTien());
				tkThue.getSoTien()
						.setGiaTri(tkThue.getSoTien().getGiaTri() + hangHoa.getTkThue().getSoTien().getGiaTri());
			}
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
		} else if (loaiCt.equals(CHUNG_TU_MUA_HANG)) {
			soDongNkc = 0;
			if (tkKho != null && tkKho.getSoTien() != null && tkKho.getSoTien().getSoTien() > 0) {
				soDongNkc++;
			}
			if (tkThanhtoan != null && tkThanhtoan.getSoTien() != null && tkThanhtoan.getSoTien().getSoTien() > 0) {
				soDongNkc++;
			}
			if (tkThueGtgt != null && tkThueGtgt.getSoTien() != null && tkThueGtgt.getSoTien().getSoTien() > 0) {
				soDongNkc++;
			}
			if (tkThueTtdb != null && tkThueTtdb.getSoTien() != null && tkThueTtdb.getSoTien().getSoTien() > 0) {
				soDongNkc++;
			}
			if (tkThueNk != null && tkThueNk.getSoTien() != null && tkThueNk.getSoTien().getSoTien() > 0) {
				soDongNkc++;
			}
		} else if (loaiCt.equals(CHUNG_TU_BAN_HANG)) {
			soDongNkc = 0;
			if (tkKho != null && tkKho.getSoTien() != null && tkKho.getSoTien().getSoTien() > 0) {
				soDongNkc++;
				// Giá vốn ghi cùng dòng với tkKho
			}
			if (tkThanhtoan != null && tkThanhtoan.getSoTien() != null && tkThanhtoan.getSoTien().getSoTien() > 0) {
				soDongNkc++;
			}
			if (tkDoanhThu != null && tkDoanhThu.getSoTien() != null && tkDoanhThu.getSoTien().getSoTien() > 0) {
				soDongNkc++;
			}
			if (tkThueGtgt != null && tkThueGtgt.getSoTien() != null && tkThueGtgt.getSoTien().getSoTien() > 0) {
				soDongNkc++;
			}
			if (tkThueXk != null && tkThueXk.getSoTien() != null && tkThueXk.getSoTien().getSoTien() > 0) {
				soDongNkc++;
			}
		} else if (loaiCt.equals(CHUNG_TU_KET_CHUYEN)) {
			if (kcbtDs != null && kcbtDs.size() > 0) {
				Iterator<KetChuyenButToan> iter = kcbtDs.iterator();
				while (iter.hasNext()) {
					KetChuyenButToan ketChuyenButToan = iter.next();

					try {
						if (ketChuyenButToan.getTaiKhoanNo().getSoTien().getSoTien() > 0
								&& ketChuyenButToan.getTaiKhoanCo().getSoTien().getSoTien() > 0) {
							soDongNkc++;
						}
					} catch (Exception e) {
					}
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

	public TaiKhoan getTkThanhtoan() {
		return tkThanhtoan;
	}

	public void setTkThanhtoan(TaiKhoan tkThanhtoan) {
		this.tkThanhtoan = tkThanhtoan;
	}

	public TaiKhoan getTkKho() {
		return tkKho;
	}

	public void setTkKho(TaiKhoan tkKho) {
		this.tkKho = tkKho;
	}

	public TaiKhoan getTkDoanhThu() {
		return tkDoanhThu;
	}

	public void setTkDoanhThu(TaiKhoan tkDoanhThu) {
		this.tkDoanhThu = tkDoanhThu;
	}

	public TaiKhoan getTkChiPhi() {
		return tkChiPhi;
	}

	public void setTkChiPhi(TaiKhoan tkChiPhi) {
		this.tkChiPhi = tkChiPhi;
	}

	public TaiKhoan getTkGiaVon() {
		return tkGiaVon;
	}

	public void setTkGiaVon(TaiKhoan tkGiaVon) {
		this.tkGiaVon = tkGiaVon;
	}

	public TaiKhoan getTkChietKhau() {
		return tkChietKhau;
	}

	public void setTkChietKhau(TaiKhoan tkChietKhau) {
		this.tkChietKhau = tkChietKhau;
	}

	public TaiKhoan getTkGiamGia() {
		return tkGiamGia;
	}

	public void setTkGiamGia(TaiKhoan tkGiamGia) {
		this.tkGiamGia = tkGiamGia;
	}

	public TaiKhoan getTkTraLai() {
		return tkTraLai;
	}

	public void setTkTraLai(TaiKhoan tkTraLai) {
		this.tkTraLai = tkTraLai;
	}

	public TaiKhoan getTkThueGtgt() {
		return tkThueGtgt;
	}

	public void setTkThueGtgt(TaiKhoan tkThueGtgt) {
		this.tkThueGtgt = tkThueGtgt;
	}

	public TaiKhoan getTkThueTtdb() {
		return tkThueTtdb;
	}

	public void setTkThueTtdb(TaiKhoan tkThueTtdb) {
		this.tkThueTtdb = tkThueTtdb;
	}

	public TaiKhoan getTkThueXk() {
		return tkThueXk;
	}

	public void setTkThueXk(TaiKhoan tkThueXk) {
		this.tkThueXk = tkThueXk;
	}

	public TaiKhoan getTkThueNk() {
		return tkThueNk;
	}

	public void setTkThueNk(TaiKhoan tkThueNk) {
		this.tkThueNk = tkThueNk;
	}

	public TaiKhoan getTkThue() {
		return tkThue;
	}

	public void setTkThue(TaiKhoan tkThue) {
		this.tkThue = tkThue;
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
