package com.idi.finance.bean.hanghoa;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.format.annotation.NumberFormat;

import com.idi.finance.bean.chungtu.TaiKhoan;
import com.idi.finance.bean.chungtu.Tien;
import com.idi.finance.bean.soketoan.NghiepVuKeToan;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;

public class HangHoa {
	private static final Logger logger = Logger.getLogger(HangHoa.class);
	public static final int TK_THANH_TOAN = 1;
	public static final int TK_KHO = 2;
	public static final int TK_DOANH_THU = 3;
	public static final int TK_CHI_PHI = 4;
	public static final int TK_GIA_VON = 9;

	public static final int TK_THUE_NK = 5;
	public static final int TK_THUE_XK = 6;
	public static final int TK_THUE_TTDB = 7;
	public static final int TK_THUE_GTGT = 8;
	public static final int TK_THUE_GTGT_DU = 9;

	public static final int TINH_CHAT_VTHH = 1;
	public static final int TINH_CHAT_DV = 2;
	public static final int TINH_CHAT_TP = 3;

	private int maHh;
	private String kyHieuHh;
	private String tenHh;
	private String kyHieuTenHh;
	private int tinhChat;
	private DonVi donVi;
	private NhomHang nhomHh;
	private int thoiHanBh;
	private String moTa;
	private String nguonGoc;

	private Tien donGia = new Tien();
	private Tien giaKho = new Tien();
	@NumberFormat(pattern = "#")
	private double soLuong;

	private KhoHang kho;
	private TaiKhoan tkThanhtoan;
	private TaiKhoan tkKho;
	private TaiKhoan tkDoanhThu;
	private TaiKhoan tkChiPhi;
	private TaiKhoan tkGiaVon;
	private TaiKhoan tkChietKhau;
	private TaiKhoan tkGiamGia;
	private TaiKhoan tkTraLai;

	private TaiKhoan tkThueGtgtDu;
	private TaiKhoan tkThueGtgt;
	private TaiKhoan tkThueTtdb;
	private TaiKhoan tkThueXk;
	private TaiKhoan tkThueNk;
	private TaiKhoan tkThue;

	private List<NghiepVuKeToan> nvktDs;

	@NumberFormat(pattern = "#")
	private double tyLeCktm;
	@NumberFormat(pattern = "#")
	private double thueSuatGtgt;
	@NumberFormat(pattern = "#")
	private double thueSuatXk;
	@NumberFormat(pattern = "#")
	private double thueSuatNk;
	@NumberFormat(pattern = "#")
	private double thueSuatTtdb;

	private KhoHang khoMd;
	private LoaiTaiKhoan tkKhoMd;
	private LoaiTaiKhoan tkDoanhThuMd;
	private LoaiTaiKhoan tkChiPhiMd;
	private LoaiTaiKhoan tkChietKhauMd;
	private LoaiTaiKhoan tkGiamGiaMd;
	private LoaiTaiKhoan tkTraLaiMd;
	private Tien giaBanMd;

	@NumberFormat(pattern = "#")
	private double tyLeCktmMd;
	@NumberFormat(pattern = "#")
	private double thueSuatGtgtMd = 10;
	@NumberFormat(pattern = "#")
	private double thueSuatXkMd;
	@NumberFormat(pattern = "#")
	private double thueSuatNkMd;
	@NumberFormat(pattern = "#")
	private double thueSuatTtdbMd;

	private List<DonGia> donGiaDs;

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

	public String getKyHieuTenHh() {
		return kyHieuTenHh;
	}

	public void setKyHieuTenHh(String kyHieuTenHh) {
		this.kyHieuTenHh = kyHieuTenHh;
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

	public int getThoiHanBh() {
		return thoiHanBh;
	}

	public void setThoiHanBh(int thoiHanBh) {
		this.thoiHanBh = thoiHanBh;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public String getNguonGoc() {
		return nguonGoc;
	}

	public void setNguonGoc(String nguonGoc) {
		this.nguonGoc = nguonGoc;
	}

	public KhoHang getKho() {
		return kho;
	}

	public void setKho(KhoHang kho) {
		this.kho = kho;
	}

	public Tien getDonGia() {
		return donGia;
	}

	public void setDonGia(Tien donGia) {
		this.donGia = donGia;
	}

	public Tien getGiaKho() {
		return giaKho;
	}

	public void setGiaKho(Tien giaKho) {
		this.giaKho = giaKho;
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

	public TaiKhoan getTkThueGtgtDu() {
		return tkThueGtgtDu;
	}

	public void setTkThueGtgtDu(TaiKhoan tkThueGtgtDu) {
		this.tkThueGtgtDu = tkThueGtgtDu;
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

	public List<NghiepVuKeToan> getNvktDs() {
		return nvktDs;
	}

	public void setNvktDs(List<NghiepVuKeToan> nvktDs) {
		this.nvktDs = nvktDs;
	}

	public double getTyLeCktm() {
		return tyLeCktm;
	}

	public void setTyLeCktm(double tyLeCktm) {
		this.tyLeCktm = tyLeCktm;
	}

	public double getThueSuatGtgt() {
		return thueSuatGtgt;
	}

	public void setThueSuatGtgt(double thueSuatGtgt) {
		this.thueSuatGtgt = thueSuatGtgt;
	}

	public double getThueSuatXk() {
		return thueSuatXk;
	}

	public void setThueSuatXk(double thueSuatXk) {
		this.thueSuatXk = thueSuatXk;
	}

	public double getThueSuatNk() {
		return thueSuatNk;
	}

	public void setThueSuatNk(double thueSuatNk) {
		this.thueSuatNk = thueSuatNk;
	}

	public double getThueSuatTtdb() {
		return thueSuatTtdb;
	}

	public void setThueSuatTtdb(double thueSuatTtdb) {
		this.thueSuatTtdb = thueSuatTtdb;
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

	public LoaiTaiKhoan getTkChietKhauMd() {
		return tkChietKhauMd;
	}

	public void setTkChietKhauMd(LoaiTaiKhoan tkChietKhauMd) {
		this.tkChietKhauMd = tkChietKhauMd;
	}

	public LoaiTaiKhoan getTkGiamGiaMd() {
		return tkGiamGiaMd;
	}

	public void setTkGiamGiaMd(LoaiTaiKhoan tkGiamGiaMd) {
		this.tkGiamGiaMd = tkGiamGiaMd;
	}

	public LoaiTaiKhoan getTkTraLaiMd() {
		return tkTraLaiMd;
	}

	public void setTkTraLaiMd(LoaiTaiKhoan tkTraLaiMd) {
		this.tkTraLaiMd = tkTraLaiMd;
	}

	public Tien getGiaBanMd() {
		return giaBanMd;
	}

	public void setGiaBanMd(Tien giaBanMd) {
		this.giaBanMd = giaBanMd;
	}

	public double getTyLeCktmMd() {
		return tyLeCktmMd;
	}

	public void setTyLeCktmMd(double tyLeCktmMd) {
		this.tyLeCktmMd = tyLeCktmMd;
	}

	public double getThueSuatGtgtMd() {
		return thueSuatGtgtMd;
	}

	public void setThueSuatGtgtMd(double thueSuatGtgtMd) {
		this.thueSuatGtgtMd = thueSuatGtgtMd;
	}

	public double getThueSuatXkMd() {
		return thueSuatXkMd;
	}

	public void setThueSuatXkMd(double thueSuatXkMd) {
		this.thueSuatXkMd = thueSuatXkMd;
	}

	public double getThueSuatNkMd() {
		return thueSuatNkMd;
	}

	public void setThueSuatNkMd(double thueSuatNkMd) {
		this.thueSuatNkMd = thueSuatNkMd;
	}

	public double getThueSuatTtdbMd() {
		return thueSuatTtdbMd;
	}

	public void setThueSuatTtdbMd(double thueSuatTtdbMd) {
		this.thueSuatTtdbMd = thueSuatTtdbMd;
	}

	public List<DonGia> getDonGiaDs() {
		return donGiaDs;
	}

	public void setDonGiaDs(List<DonGia> donGiaDs) {
		this.donGiaDs = donGiaDs;
	}

	@Override
	public String toString() {
		String out = maHh + "  " + kyHieuHh + "  " + tenHh;
		return out;
	}

	public void tronTk(HangHoa hangHoa) {
		if (hangHoa == null)
			return;

		if (this.getTkKho() == null && hangHoa.getTkKho() != null) {
			this.setTkKho(hangHoa.getTkKho());
		}
		if (this.getTkDoanhThu() == null && hangHoa.getTkDoanhThu() != null) {
			this.setTkDoanhThu(hangHoa.getTkDoanhThu());
		}
		if (this.getTkGiaVon() == null && hangHoa.getTkGiaVon() != null) {
			this.setTkGiaVon(hangHoa.getTkGiaVon());
		}
		if (this.getTkChiPhi() == null && hangHoa.getTkChiPhi() != null) {
			this.setTkChiPhi(hangHoa.getTkChiPhi());
		}
		if (this.getTkThanhtoan() == null && hangHoa.getTkThanhtoan() != null) {
			this.setTkThanhtoan(hangHoa.getTkThanhtoan());
		}
		if (this.getTkThueNk() == null && hangHoa.getTkThueNk() != null) {
			this.setTkThueNk(hangHoa.getTkThueNk());
		}
		if (this.getThueSuatNk() == 0) {
			this.setThueSuatNk(hangHoa.getThueSuatNk());
		}
		if (this.getTkThueXk() == null && hangHoa.getTkThueXk() != null) {
			this.setTkThueXk(hangHoa.getTkThueXk());
		}
		if (this.getThueSuatXk() == 0) {
			this.setThueSuatXk(hangHoa.getThueSuatXk());
		}
		if (this.getTkThueTtdb() == null && hangHoa.getTkThueTtdb() != null) {
			this.setTkThueTtdb(hangHoa.getTkThueTtdb());
		}
		if (this.getThueSuatTtdb() == 0) {
			this.setThueSuatTtdb(hangHoa.getThueSuatTtdb());
		}
		if (this.getTkThueGtgt() == null && hangHoa.getTkThueGtgt() != null) {
			this.setTkThueGtgt(hangHoa.getTkThueGtgt());
		}
		if (this.getThueSuatGtgt() == 0) {
			this.setThueSuatGtgt(hangHoa.getThueSuatGtgt());
		}
		if (this.getTkThueGtgtDu() == null && hangHoa.getTkThueGtgtDu() != null) {
			this.setTkThueGtgtDu(hangHoa.getTkThueGtgtDu());
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
