package com.idi.finance.bean.hanghoa;

import org.springframework.format.annotation.NumberFormat;

import com.idi.finance.bean.chungtu.TaiKhoan;
import com.idi.finance.bean.chungtu.Tien;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;

public class HangHoa {
	public static final int TK_THANH_TOAN = 1;
	public static final int TK_KHO = 2;
	public static final int TK_DOANH_THU = 3;
	public static final int TK_CHI_PHI = 4;

	private int maHh;
	private String kyHieuHh;
	private String tenHh;
	private int tinhChat;
	private DonVi donVi;
	private NhomHang nhomHh;
	private KhoHang kho;
	private Tien giaMua;
	private Tien giaNhapKho;
	private Tien giaBan;
	@NumberFormat(pattern = "#")
	private double soLuong;

	private TaiKhoan tkKho;
	private TaiKhoan tkThanhtoan;
	private TaiKhoan tkDoanhThu;
	private TaiKhoan tkChiPhi;
	private TaiKhoan tkThue;

	private KhoHang khoMd;
	private LoaiTaiKhoan tkKhoMd;
	private LoaiTaiKhoan tkDoanhThuMd;
	private LoaiTaiKhoan tkChiPhiMd;

	public int getMaHh() {
		return maHh;
	}

	public void setMaHh(int maHh) {
		this.maHh = maHh;
	}

	public String getKyHieuHh() {
		return kyHieuHh;
	}

	public void setKyHieuHh(String kyHieuHh) {
		this.kyHieuHh = kyHieuHh;
	}

	public String getTenHh() {
		return tenHh;
	}

	public void setTenHh(String tenHh) {
		this.tenHh = tenHh;
	}

	public int getTinhChat() {
		return tinhChat;
	}

	public void setTinhChat(int tinhChat) {
		this.tinhChat = tinhChat;
	}

	public DonVi getDonVi() {
		return donVi;
	}

	public void setDonVi(DonVi donVi) {
		this.donVi = donVi;
	}

	public NhomHang getNhomHh() {
		return nhomHh;
	}

	public void setNhomHh(NhomHang nhomHh) {
		this.nhomHh = nhomHh;
	}

	public KhoHang getKho() {
		return kho;
	}

	public void setKho(KhoHang kho) {
		this.kho = kho;
	}

	public Tien getGiaMua() {
		return giaMua;
	}

	public void setGiaMua(Tien giaMua) {
		this.giaMua = giaMua;
	}

	public Tien getGiaNhapKho() {
		return giaNhapKho;
	}

	public void setGiaNhapKho(Tien giaNhapKho) {
		this.giaNhapKho = giaNhapKho;
	}

	public Tien getGiaBan() {
		return giaBan;
	}

	public void setGiaBan(Tien giaBan) {
		this.giaBan = giaBan;
	}

	public double getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(double soLuong) {
		this.soLuong = soLuong;
	}

	public TaiKhoan getTkKho() {
		return tkKho;
	}

	public void setTkKho(TaiKhoan tkKho) {
		this.tkKho = tkKho;
	}

	public TaiKhoan getTkThanhtoan() {
		return tkThanhtoan;
	}

	public void setTkThanhtoan(TaiKhoan tkThanhtoan) {
		this.tkThanhtoan = tkThanhtoan;
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

	public TaiKhoan getTkThue() {
		return tkThue;
	}

	public void setTkThue(TaiKhoan tkThue) {
		this.tkThue = tkThue;
	}

	public KhoHang getKhoMd() {
		return khoMd;
	}

	public void setKhoMd(KhoHang khoMd) {
		this.khoMd = khoMd;
	}

	public LoaiTaiKhoan getTkKhoMd() {
		return tkKhoMd;
	}

	public void setTkKhoMd(LoaiTaiKhoan tkKhoMd) {
		this.tkKhoMd = tkKhoMd;
	}

	public LoaiTaiKhoan getTkDoanhThuMd() {
		return tkDoanhThuMd;
	}

	public void setTkDoanhThuMd(LoaiTaiKhoan tkDoanhThuMd) {
		this.tkDoanhThuMd = tkDoanhThuMd;
	}

	public LoaiTaiKhoan getTkChiPhiMd() {
		return tkChiPhiMd;
	}

	public void setTkChiPhiMd(LoaiTaiKhoan tkChiPhiMd) {
		this.tkChiPhiMd = tkChiPhiMd;
	}

	@Override
	public String toString() {
		String out = maHh + "  " + kyHieuHh + "  " + tenHh;
		return out;
	}

	public void tronTk(HangHoa hangHoa) {
		if (hangHoa == null)
			return;

		if (this.getTkKho() == null) {
			this.setTkKho(hangHoa.getTkKho());
		}
		if (this.getTkDoanhThu() == null) {
			this.setTkDoanhThu(hangHoa.getTkDoanhThu());
		}
		if (this.getTkChiPhi() == null) {
			this.setTkChiPhi(hangHoa.getTkChiPhi());
		}
		if (this.getTkThanhtoan() == null) {
			this.setTkThanhtoan(hangHoa.getTkThanhtoan());
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof HangHoa)) {
			return false;
		}

		HangHoa item = (HangHoa) obj;
		try {
			if (maHh != item.getMaHh()) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
