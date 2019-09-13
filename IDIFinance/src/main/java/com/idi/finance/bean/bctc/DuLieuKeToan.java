package com.idi.finance.bean.bctc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.idi.finance.bean.chungtu.ChungTu;
import com.idi.finance.bean.doituong.DoiTuong;
import com.idi.finance.bean.hanghoa.HangHoa;
import com.idi.finance.bean.hanghoa.KhoHang;
import com.idi.finance.bean.soketoan.NghiepVuKeToan;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;

public class DuLieuKeToan {
	private static final Logger logger = Logger.getLogger(DuLieuKeToan.class);
	private KyKeToanCon kyKeToan;
	private LoaiTaiKhoan loaiTaiKhoan;
	private DoiTuong doiTuong;
	private List<DoiTuong> doiTuongDs;
	private HangHoa hangHoa;
	private KhoHang khoHang;
	private int chieu = ChungTu.MUA;

	private double soDuDauKy;
	private double noDauKy;
	private double coDauKy;

	private double soDuCuoiKy;
	private double noCuoiKy;
	private double coCuoiKy;

	private double tongNoPhatSinh;
	private double tongCoPhatSinh;

	private double soLuongDuDauKy;
	private double soLuongNhapDauKy;
	private double soLuongXuatDauKy;

	private double soLuongDuCuoiKy;
	private double soLuongNhapCuoiKy;
	private double soLuongXuatCuoiKy;

	private double soLuongNhapPhatSinh;
	private double soLuongXuatPhatSinh;

	private DuLieuKeToan duLieuKeToan;
	private List<DuLieuKeToan> duLieuKeToanDs;
	private List<NghiepVuKeToan> nghiepVuKeToanDs;

	public DuLieuKeToan() {

	}

	public DuLieuKeToan(KyKeToanCon kyKeToan) {
		this.kyKeToan = kyKeToan;
	}

	public DuLieuKeToan(KyKeToanCon kyKeToan, LoaiTaiKhoan loaiTaiKhoan) {
		this.kyKeToan = kyKeToan;
		this.loaiTaiKhoan = loaiTaiKhoan;
	}

	public KyKeToanCon getKyKeToan() {
		return kyKeToan;
	}

	public void setKyKeToan(KyKeToanCon kyKeToan) {
		this.kyKeToan = kyKeToan;
	}

	public LoaiTaiKhoan getLoaiTaiKhoan() {
		return loaiTaiKhoan;
	}

	public void setLoaiTaiKhoan(LoaiTaiKhoan loaiTaiKhoan) {
		this.loaiTaiKhoan = loaiTaiKhoan;
	}

	public DoiTuong getDoiTuong() {
		return doiTuong;
	}

	public void setDoiTuong(DoiTuong doiTuong) {
		this.doiTuong = doiTuong;
	}

	public List<DoiTuong> getDoiTuongDs() {
		return doiTuongDs;
	}

	public void setDoiTuongDs(List<DoiTuong> doiTuongDs) {
		this.doiTuongDs = doiTuongDs;
	}

	public HangHoa getHangHoa() {
		return hangHoa;
	}

	public void setHangHoa(HangHoa hangHoa) {
		this.hangHoa = hangHoa;
	}

	public KhoHang getKhoHang() {
		return khoHang;
	}

	public void setKhoHang(KhoHang khoHang) {
		this.khoHang = khoHang;
	}

	public int getChieu() {
		return chieu;
	}

	public void setChieu(int chieu) {
		this.chieu = chieu;
	}

	public double getSoDuDauKy() {
		return soDuDauKy;
	}

	public void setSoDuDauKy(double soDuDauKy) {
		this.soDuDauKy = soDuDauKy;
	}

	public double getNoDauKy() {
		return noDauKy;
	}

	public void setNoDauKy(double noDauKy) {
		this.noDauKy = noDauKy;
		capNhatNoCoCuoiKy();
	}

	public double getCoDauKy() {
		return coDauKy;
	}

	public void setCoDauKy(double coDauKy) {
		this.coDauKy = coDauKy;
		capNhatNoCoCuoiKy();
	}

	public double getSoDuCuoiKy() {
		return soDuCuoiKy;
	}

	public void setSoDuCuoiKy(double soDuCuoiKy) {
		this.soDuCuoiKy = soDuCuoiKy;
	}

	public double getNoCuoiKy() {
		return noCuoiKy;
	}

	public void setNoCuoiKy(double noCuoiKy) {
		this.noCuoiKy = noCuoiKy;
	}

	public double getCoCuoiKy() {
		return coCuoiKy;
	}

	public void setCoCuoiKy(double coCuoiKy) {
		this.coCuoiKy = coCuoiKy;
	}

	public double getTongNoPhatSinh() {
		return tongNoPhatSinh;
	}

	public void setTongNoPhatSinh(double tongNoPhatSinh) {
		this.tongNoPhatSinh = tongNoPhatSinh;
		capNhatNoCoCuoiKy();
	}

	public double getTongCoPhatSinh() {
		return tongCoPhatSinh;
	}

	public void setTongCoPhatSinh(double tongCoPhatSinh) {
		this.tongCoPhatSinh = tongCoPhatSinh;
		capNhatNoCoCuoiKy();
	}

	public double getSoLuongDuDauKy() {
		return soLuongDuDauKy;
	}

	public void setSoLuongDuDauKy(double soLuongDuDauKy) {
		this.soLuongDuDauKy = soLuongDuDauKy;
	}

	public double getSoLuongNhapDauKy() {
		return soLuongNhapDauKy;
	}

	public void setSoLuongNhapDauKy(double soLuongNhapDauKy) {
		this.soLuongNhapDauKy = soLuongNhapDauKy;
		capNhatNoCoCuoiKy();
	}

	public double getSoLuongXuatDauKy() {
		return soLuongXuatDauKy;
	}

	public void setSoLuongXuatDauKy(double soLuongXuatDauKy) {
		this.soLuongXuatDauKy = soLuongXuatDauKy;
		capNhatNoCoCuoiKy();
	}

	public double getSoLuongDuCuoiKy() {
		return soLuongDuCuoiKy;
	}

	public void setSoLuongDuCuoiKy(double soLuongDuCuoiKy) {
		this.soLuongDuCuoiKy = soLuongDuCuoiKy;
	}

	public double getSoLuongNhapCuoiKy() {
		return soLuongNhapCuoiKy;
	}

	public void setSoLuongNhapCuoiKy(double soLuongNhapCuoiKy) {
		this.soLuongNhapCuoiKy = soLuongNhapCuoiKy;
	}

	public double getSoLuongXuatCuoiKy() {
		return soLuongXuatCuoiKy;
	}

	public void setSoLuongXuatCuoiKy(double soLuongXuatCuoiKy) {
		this.soLuongXuatCuoiKy = soLuongXuatCuoiKy;
	}

	public double getSoLuongNhapPhatSinh() {
		return soLuongNhapPhatSinh;
	}

	public void setSoLuongNhapPhatSinh(double soLuongNhapPhatSinh) {
		this.soLuongNhapPhatSinh = soLuongNhapPhatSinh;
		capNhatNoCoCuoiKy();
	}

	public double getSoLuongXuatPhatSinh() {
		return soLuongXuatPhatSinh;
	}

	public void setSoLuongXuatPhatSinh(double soLuongXuatPhatSinh) {
		this.soLuongXuatPhatSinh = soLuongXuatPhatSinh;
		capNhatNoCoCuoiKy();
	}

	public List<NghiepVuKeToan> getNghiepVuKeToanDs() {
		return nghiepVuKeToanDs;
	}

	public void setNghiepVuKeToanDs(List<NghiepVuKeToan> nghiepVuKeToanDs) {
		this.nghiepVuKeToanDs = nghiepVuKeToanDs;
	}

	private void capNhatNoCoCuoiKy() {
		// Số tiền
		noCuoiKy = noDauKy + tongNoPhatSinh;
		coCuoiKy = coDauKy + tongCoPhatSinh;

		soDuDauKy = noDauKy - coDauKy;
		soDuCuoiKy = noCuoiKy - coCuoiKy;

		// Số lượng
		soLuongNhapCuoiKy = soLuongNhapDauKy + soLuongNhapPhatSinh;
		soLuongXuatCuoiKy = soLuongXuatDauKy + soLuongXuatPhatSinh;

		soLuongDuDauKy = soLuongNhapDauKy - soLuongXuatDauKy;
		soLuongDuCuoiKy = soLuongNhapCuoiKy - soLuongXuatCuoiKy;
	}

	public void themNghiepVuKeToan(NghiepVuKeToan nghiepVuKeToan) {
		if (nghiepVuKeToan == null) {
			return;
		}

		if (nghiepVuKeToanDs == null) {
			nghiepVuKeToanDs = new ArrayList<>();
		}

		if (!nghiepVuKeToanDs.contains(nghiepVuKeToan)) {
			nghiepVuKeToanDs.add(nghiepVuKeToan);
			Collections.sort(nghiepVuKeToanDs);
		}
	}

	public void themNghiepVuKeToan(List<NghiepVuKeToan> nghiepVuKeToanDs) {
		if (nghiepVuKeToanDs == null) {
			return;
		}

		Iterator<NghiepVuKeToan> iter = nghiepVuKeToanDs.iterator();
		while (iter.hasNext()) {
			themNghiepVuKeToan(iter.next());
		}
	}

	public void tinhSoTienTonNghiepVuKeToanDs(LoaiTaiKhoan loaiTaiKhoan) {
		if (loaiTaiKhoan == null) {
			return;
		}

		if (nghiepVuKeToanDs != null && nghiepVuKeToanDs.size() > 0) {
			Collections.sort(nghiepVuKeToanDs);

			double ton = soDuDauKy * loaiTaiKhoan.getSoDu() * -1;
			double slTon = soLuongDuDauKy;

			Iterator<NghiepVuKeToan> iter = nghiepVuKeToanDs.iterator();
			while (iter.hasNext()) {
				NghiepVuKeToan nghiepVuKeToan = iter.next();

				// So tien ton
				try {
					if (nghiepVuKeToan.getTaiKhoanNo().getLoaiTaiKhoan().isTrucHe(loaiTaiKhoan)) {
						ton += nghiepVuKeToan.getTaiKhoanNo().getSoTien().getGiaTri() * loaiTaiKhoan.getSoDu() * -1;
					} else if (nghiepVuKeToan.getTaiKhoanCo().getLoaiTaiKhoan().isTrucHe(loaiTaiKhoan)) {
						ton += nghiepVuKeToan.getTaiKhoanCo().getSoTien().getGiaTri() * loaiTaiKhoan.getSoDu();
					}

					nghiepVuKeToan.setTon(ton);
				} catch (Exception e) {
					// e.printStackTrace();
				}

				// So luong ton
				try {
					slTon += nghiepVuKeToan.getHangHoa().getSoLuong() * nghiepVuKeToan.getChungTu().getChieu();
					nghiepVuKeToan.setSlTon(slTon);
				} catch (Exception e) {
					// e.printStackTrace();
				}
			}
		}
	}

	public DuLieuKeToan getDuLieuKeToan() {
		return duLieuKeToan;
	}

	public void setDuLieuKeToan(DuLieuKeToan duLieuKeToan) {
		this.duLieuKeToan = duLieuKeToan;
	}

	public List<DuLieuKeToan> getDuLieuKeToanDs() {
		return duLieuKeToanDs;
	}

	public void setDuLieuKeToanDs(List<DuLieuKeToan> duLieuKeToanDs) {
		this.duLieuKeToanDs = duLieuKeToanDs;
	}

	public void capNhatDuLieuKeToan(DuLieuKeToan duLieuKeToan) {
		if (duLieuKeToan != null) {
			List<DuLieuKeToan> ketQuaDs = new ArrayList<>();
			ketQuaDs.add(duLieuKeToan);
			capNhatDuLieuKeToan(ketQuaDs);
		}
	}

	public void capNhatDuLieuKeToan(List<DuLieuKeToan> duLieuKeToanDs) {
		noDauKy = 0;
		coDauKy = 0;
		tongNoPhatSinh = 0;
		tongCoPhatSinh = 0;
		this.duLieuKeToanDs = duLieuKeToanDs;

		if (this.duLieuKeToanDs != null) {
			Iterator<DuLieuKeToan> iter = this.duLieuKeToanDs.iterator();
			while (iter.hasNext()) {
				DuLieuKeToan duLieuKeToan = iter.next();

				// Số tiền
				noDauKy += duLieuKeToan.getNoDauKy();
				coDauKy += duLieuKeToan.getCoDauKy();

				tongNoPhatSinh += duLieuKeToan.getTongNoPhatSinh();
				tongCoPhatSinh += duLieuKeToan.getTongCoPhatSinh();

				// Số lượng
				soLuongNhapDauKy += duLieuKeToan.getSoLuongNhapDauKy();
				soLuongXuatDauKy += duLieuKeToan.getSoLuongXuatDauKy();

				soLuongNhapPhatSinh += duLieuKeToan.getSoLuongNhapPhatSinh();
				soLuongXuatPhatSinh += duLieuKeToan.getSoLuongXuatPhatSinh();
			}

			// Số tiền
			noCuoiKy = noDauKy + tongNoPhatSinh;
			coCuoiKy = coDauKy + tongCoPhatSinh;

			soDuDauKy = noDauKy - coDauKy;
			soDuCuoiKy = noCuoiKy - coCuoiKy;

			// Số lượng
			soLuongNhapCuoiKy = soLuongNhapDauKy + soLuongNhapPhatSinh;
			soLuongXuatCuoiKy = soLuongXuatDauKy + soLuongXuatPhatSinh;

			soLuongDuDauKy = soLuongNhapDauKy - soLuongXuatDauKy;
			soLuongDuCuoiKy = soLuongNhapCuoiKy - soLuongXuatCuoiKy;
		}
	}

	public void themDuLieuKeToan(DuLieuKeToan duLieuKeToan) {
		if (duLieuKeToan == null) {
			return;
		}

		if (duLieuKeToanDs == null) {
			duLieuKeToanDs = new ArrayList<>();
		}

		int pos = duLieuKeToanDs.indexOf(duLieuKeToan);
		if (pos > -1) {
			DuLieuKeToan duLieuKeToanTmpl = duLieuKeToanDs.get(pos);
			duLieuKeToanTmpl.tron(duLieuKeToan);
		} else {
			duLieuKeToanDs.add(duLieuKeToan);
		}
	}

	public void themDuLieuKeToan(List<DuLieuKeToan> duLieuKeToanDs) {
		if (duLieuKeToanDs == null) {
			return;
		}

		Iterator<DuLieuKeToan> iter = duLieuKeToanDs.iterator();
		while (iter.hasNext()) {
			themDuLieuKeToan(iter.next());
		}
	}

	public void tron(DuLieuKeToan duLieuKeToan) {
		if (duLieuKeToan == null) {
			return;
		}

		if (kyKeToan == null) {
			kyKeToan = duLieuKeToan.getKyKeToan();
		}

		if (loaiTaiKhoan == null) {
			loaiTaiKhoan = duLieuKeToan.getLoaiTaiKhoan();
		}

		if (doiTuong == null) {
			doiTuong = duLieuKeToan.getDoiTuong();
		}

		if (hangHoa == null) {
			hangHoa = duLieuKeToan.getHangHoa();
		}

		if (khoHang == null) {
			khoHang = duLieuKeToan.getKhoHang();
		}

		if (this.duLieuKeToan == null) {
			this.duLieuKeToan = duLieuKeToan.getDuLieuKeToan();
		}

		if (chieu != ChungTu.MUA && chieu != ChungTu.BAN) {
			chieu = duLieuKeToan.getChieu();
		}

		// Số tiền
		noDauKy += duLieuKeToan.getNoDauKy();
		coDauKy += duLieuKeToan.getCoDauKy();

		tongNoPhatSinh += duLieuKeToan.getTongNoPhatSinh();
		tongCoPhatSinh += duLieuKeToan.getTongCoPhatSinh();

		noCuoiKy = noDauKy + tongNoPhatSinh;
		coCuoiKy = coDauKy + tongCoPhatSinh;

		soDuDauKy = noDauKy - coDauKy;
		soDuCuoiKy = noCuoiKy - coCuoiKy;

		// Số lượng
		soLuongNhapDauKy += duLieuKeToan.getSoLuongNhapDauKy();
		soLuongXuatDauKy += duLieuKeToan.getSoLuongXuatDauKy();

		soLuongNhapPhatSinh += duLieuKeToan.getSoLuongNhapPhatSinh();
		soLuongXuatPhatSinh += duLieuKeToan.getSoLuongXuatPhatSinh();

		soLuongNhapCuoiKy = soLuongNhapDauKy + soLuongNhapPhatSinh;
		soLuongXuatCuoiKy = soLuongXuatDauKy + soLuongXuatPhatSinh;

		soLuongDuDauKy = soLuongNhapDauKy - soLuongXuatDauKy;
		soLuongDuCuoiKy = soLuongNhapCuoiKy - soLuongXuatCuoiKy;

		themDuLieuKeToan(duLieuKeToan.getDuLieuKeToanDs());
		themNghiepVuKeToan(duLieuKeToan.getNghiepVuKeToanDs());
	}

	@Override
	public String toString() {
		String out = kyKeToan + ":  " + loaiTaiKhoan + ": " + doiTuong + ": " + hangHoa + ": " + khoHang + ": "
				+ noDauKy + " " + coDauKy + " " + tongNoPhatSinh + " " + tongCoPhatSinh + " " + noCuoiKy + " "
				+ coCuoiKy + " " + soLuongNhapDauKy + " " + soLuongXuatDauKy + " " + soLuongNhapPhatSinh + " "
				+ soLuongXuatPhatSinh;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof DuLieuKeToan)) {
			return false;
		}

		DuLieuKeToan item = (DuLieuKeToan) obj;

		if (kyKeToan == null) {
			if (item.getKyKeToan() != null)
				return false;
		} else if (item.getKyKeToan() == null) {
			return false;
		} else if (!kyKeToan.equals(item.getKyKeToan())) {
			return false;
		}

		if (loaiTaiKhoan == null) {
			if (item.getLoaiTaiKhoan() != null)
				return false;
		} else if (item.getLoaiTaiKhoan() == null) {
			return false;
		} else if (!loaiTaiKhoan.equals(item.getLoaiTaiKhoan())) {
			return false;
		}

		if (doiTuong == null) {
			if (item.getDoiTuong() != null)
				return false;
		} else if (item.getDoiTuong() == null) {
			return false;
		} else if (!doiTuong.equals(item.getDoiTuong())) {
			return false;
		}

		if (hangHoa == null) {
			if (item.getHangHoa() != null)
				return false;
		} else if (item.getHangHoa() == null) {
			return false;
		} else if (!hangHoa.equals(item.getHangHoa())) {
			return false;
		}

		if (khoHang == null) {
			if (item.getKhoHang() != null)
				return false;
		} else if (item.getKhoHang() == null) {
			return false;
		} else if (!khoHang.equals(item.getKhoHang())) {
			return false;
		}

		return true;
	}
}
