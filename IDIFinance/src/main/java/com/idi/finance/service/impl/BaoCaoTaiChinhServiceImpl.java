package com.idi.finance.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idi.finance.bean.Tien;
import com.idi.finance.bean.bctc.DuLieuKeToan;
import com.idi.finance.bean.bctc.KyKeToanCon;
import com.idi.finance.bean.kyketoan.KyKeToan;
import com.idi.finance.bean.kyketoan.SoDuKy;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;
import com.idi.finance.bean.taikhoan.TaiKhoan;
import com.idi.finance.dao.SoKeToanDAO;
import com.idi.finance.dao.TaiKhoanDAO;
import com.idi.finance.service.BaoCaoTaiChinhService;
import com.idi.finance.utils.DateUtils;

@Service
public class BaoCaoTaiChinhServiceImpl implements BaoCaoTaiChinhService {
	private static final Logger logger = Logger.getLogger(BaoCaoTaiChinhServiceImpl.class);

	@Autowired
	SoKeToanDAO soKeToanDAO;

	@Autowired
	TaiKhoanDAO taiKhoanDAO;

	@Override
	public DuLieuKeToan taoBangCdps(Date batDau, Date ketThuc, List<LoaiTaiKhoan> loaiTaiKhoanDs, KyKeToan kyKeToan) {
		KyKeToanCon kktCon = new KyKeToanCon(batDau, ketThuc);

		return taoBangCdps(kktCon, loaiTaiKhoanDs, kyKeToan);
	}

	@Override
	public DuLieuKeToan taoBangCdps(KyKeToanCon kktCon, List<LoaiTaiKhoan> loaiTaiKhoanDs, KyKeToan kyKeToan) {
		LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
		loaiTaiKhoan.themLoaiTaiKhoan(loaiTaiKhoanDs);

		return taoBangCdps(kktCon, loaiTaiKhoan, kyKeToan);
	}

	@Override
	public DuLieuKeToan taoBangCdps(KyKeToanCon kktCon, LoaiTaiKhoan loaiTaiKhoan, KyKeToan kyKeToan) {
		if (kyKeToan == null || kyKeToan.getBatDau() == null || loaiTaiKhoan == null
				|| loaiTaiKhoan.getLoaiTaiKhoanDs() == null || kktCon == null || kktCon.getDau() == null
				|| kktCon.getCuoi() == null) {
			return null;
		}

		DuLieuKeToan duLieuKeToan = new DuLieuKeToan(kktCon, loaiTaiKhoan);

		List<TaiKhoan> dauKyDs = soKeToanDAO.tongPhatSinh(kyKeToan.getBatDau(),
				DateUtils.prevMilisecond(kktCon.getDau()));
		dauKyDs = tronNoCoDauKy(dauKyDs, kyKeToan.getSoDuKyDs());

		List<TaiKhoan> tongPsDs = soKeToanDAO.tongPhatSinh(kktCon.getDau(), kktCon.getCuoi());
		duLieuKeToan = tongPhatSinh(duLieuKeToan, tongPsDs, dauKyDs);

		return duLieuKeToan;
	}

	private List<TaiKhoan> tronNoCoDauKy(List<TaiKhoan> dauKyDs, List<SoDuKy> soDuKyDs) {
		if (dauKyDs == null || soDuKyDs == null) {
			return dauKyDs;
		}

		logger.info("Trộn nợ/có đầu kỳ");
		Iterator<TaiKhoan> dkIter = dauKyDs.iterator();
		while (dkIter.hasNext()) {
			TaiKhoan taiKhoan = dkIter.next();

			Iterator<SoDuKy> sdkIter = soDuKyDs.iterator();
			while (sdkIter.hasNext()) {
				SoDuKy soDuKy = sdkIter.next();

				if (taiKhoan.getLoaiTaiKhoan().equals(soDuKy.getLoaiTaiKhoan())) {
					Tien soTien = taiKhoan.getSoTien();
					if (taiKhoan.getSoDu() == LoaiTaiKhoan.NO) {
						soTien.setGiaTri(soTien.getGiaTri() + soDuKy.getNoDauKy());
					} else {
						soTien.setGiaTri(soTien.getGiaTri() + soDuKy.getCoDauKy());
					}

					taiKhoan.setSoTien(soTien);
				}
			}
		}

		Iterator<SoDuKy> sdkIter = soDuKyDs.iterator();
		while (sdkIter.hasNext()) {
			SoDuKy soDuKy = sdkIter.next();

			boolean daXuLy = false;
			Iterator<TaiKhoan> iter = dauKyDs.iterator();
			while (iter.hasNext()) {
				TaiKhoan taiKhoan = iter.next();

				if (soDuKy.getLoaiTaiKhoan().equals(taiKhoan.getLoaiTaiKhoan())) {
					daXuLy = true;
					break;
				}
			}

			if (!daXuLy) {
				if (soDuKy.getNoDauKy() > 0) {
					TaiKhoan taiKhoan = new TaiKhoan();

					Tien tien = new Tien();
					tien.setGiaTri(soDuKy.getNoDauKy());

					taiKhoan.setLoaiTaiKhoan(soDuKy.getLoaiTaiKhoan());
					taiKhoan.setSoTien(tien);
					taiKhoan.setSoDu(LoaiTaiKhoan.NO);

					dauKyDs.add(taiKhoan);
				}

				if (soDuKy.getCoDauKy() > 0) {
					TaiKhoan taiKhoan = new TaiKhoan();

					Tien tien = new Tien();
					tien.setGiaTri(soDuKy.getCoDauKy());

					taiKhoan.setLoaiTaiKhoan(soDuKy.getLoaiTaiKhoan());
					taiKhoan.setSoTien(tien);
					taiKhoan.setSoDu(LoaiTaiKhoan.CO);

					dauKyDs.add(taiKhoan);
				}
			}
		}

		logger.info("Số dư đầu kỳ");
		for (Iterator<TaiKhoan> iter = dauKyDs.iterator(); iter.hasNext();) {
			TaiKhoan taiKhoan = iter.next();
			logger.info(taiKhoan);
		}

		return dauKyDs;
	}

	private DuLieuKeToan tongPhatSinh(DuLieuKeToan duLieuKeToan, List<TaiKhoan> tongPsDs, List<TaiKhoan> dauKyDs) {
		if (duLieuKeToan == null || duLieuKeToan.getKyKeToan() == null || duLieuKeToan.getLoaiTaiKhoan() == null)
			return null;

		if (tongPsDs == null) {
			tongPsDs = new ArrayList<>();
		}

		if (dauKyDs == null) {
			dauKyDs = new ArrayList<>();
		}

		List<LoaiTaiKhoan> loaiTaiKhoanDs = duLieuKeToan.getLoaiTaiKhoan().getLoaiTaiKhoanDs();
		if (loaiTaiKhoanDs != null && loaiTaiKhoanDs.size() > 0) {
			List<DuLieuKeToan> duLieuKeToanDs = new ArrayList<>();
			List<LoaiTaiKhoan> loaiTaiKhoanConDs = new ArrayList<>();

			Iterator<LoaiTaiKhoan> iter = loaiTaiKhoanDs.iterator();
			while (iter.hasNext()) {
				LoaiTaiKhoan loaiTaiKhoan = iter.next();

				DuLieuKeToan duLieuKeToanCon = new DuLieuKeToan(duLieuKeToan.getKyKeToan(), loaiTaiKhoan);
				duLieuKeToanCon.setDuLieuKeToan(duLieuKeToan);
				boolean coDuLieu = false;

				Iterator<TaiKhoan> tpsIter = tongPsDs.iterator();
				while (tpsIter.hasNext()) {
					TaiKhoan taiKhoan = tpsIter.next();

					if (taiKhoan.getLoaiTaiKhoan().equals(loaiTaiKhoan)) {
						if (taiKhoan.getSoDu() == LoaiTaiKhoan.NO) {
							duLieuKeToanCon.setTongNoPhatSinh(taiKhoan.getSoTien().getGiaTri());
						} else {
							duLieuKeToanCon.setTongCoPhatSinh(taiKhoan.getSoTien().getGiaTri());
						}
						coDuLieu = true;
					}
				}

				Iterator<TaiKhoan> dkIter = dauKyDs.iterator();
				while (dkIter.hasNext()) {
					TaiKhoan taiKhoan = dkIter.next();

					if (taiKhoan.getLoaiTaiKhoan().equals(loaiTaiKhoan)) {
						if (taiKhoan.getSoDu() == LoaiTaiKhoan.NO) {
							duLieuKeToanCon.setNoDauKy(taiKhoan.getSoTien().getGiaTri());
						} else {
							duLieuKeToanCon.setCoDauKy(taiKhoan.getSoTien().getGiaTri());
						}
						coDuLieu = true;
					}
				}

				if (coDuLieu) {
					loaiTaiKhoanConDs.add(loaiTaiKhoan);
					duLieuKeToanCon = tongPhatSinh(duLieuKeToanCon, tongPsDs, dauKyDs);
					duLieuKeToanDs.add(duLieuKeToanCon);
				}
			}

			duLieuKeToan.getLoaiTaiKhoan().setLoaiTaiKhoanDs(loaiTaiKhoanConDs);
			duLieuKeToan.setDuLieuKeToanDs(duLieuKeToanDs);
		}

		if (duLieuKeToan.getLoaiTaiKhoan().getMaTk() == null
				|| duLieuKeToan.getLoaiTaiKhoan().getMaTk().trim().equals("")) {
			// Đây là gốc cây
			if (duLieuKeToan.getDuLieuKeToanDs() != null && duLieuKeToan.getDuLieuKeToanDs().size() > 0) {
				Iterator<DuLieuKeToan> iter = duLieuKeToan.getDuLieuKeToanDs().iterator();
				while (iter.hasNext()) {
					DuLieuKeToan duLieuKeToanCon = iter.next();

					// Tính tổng nợ/có phát sinh cho dữ liệu kế toán gốc
					duLieuKeToan
							.setTongNoPhatSinh(duLieuKeToan.getTongNoPhatSinh() + duLieuKeToanCon.getTongNoPhatSinh());
					duLieuKeToan
							.setTongCoPhatSinh(duLieuKeToan.getTongCoPhatSinh() + duLieuKeToanCon.getTongCoPhatSinh());

					// Tính nơ/có đầu kỳ cho dữ liệu kế toán gốc
					duLieuKeToan.setNoDauKy(duLieuKeToan.getNoDauKy() + duLieuKeToanCon.getNoDauKy());
					duLieuKeToan.setCoDauKy(duLieuKeToan.getCoDauKy() + duLieuKeToanCon.getCoDauKy());
				}
			}
		}

		return duLieuKeToan;
	}
}
