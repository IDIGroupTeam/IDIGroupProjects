package com.idi.finance.validator;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.idi.finance.bean.chungtu.ChungTu;
import com.idi.finance.bean.chungtu.TaiKhoan;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;

public class ChungTuValidator implements Validator {
	private static final Logger logger = Logger.getLogger(ChungTuValidator.class);

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String ID_PATTERN = "[0-9]+";
	private static final String STRING_PATTERN = "[a-zA-Z]+";
	private static final String MOBILE_PATTERN = "[0-9]{10}";

	@Override
	public boolean supports(Class<?> cls) {
		return cls == ChungTu.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		ChungTu chungTu = (ChungTu) target;

		if (chungTu.getLoaiCt() != null) {
			if (chungTu.getLoaiCt().trim().equals(ChungTu.CHUNG_TU_PHIEU_THU)
					|| chungTu.getLoaiCt().trim().equals(ChungTu.CHUNG_TU_BAO_CO)) {
				// Validate cho phần phiếu thu hoặc báo có
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "doiTuong.tenDt", "NotEmpty.chungTu.doiTuong.tenDt");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lyDo", "NotEmpty.chungTu.lyDo");

				if (chungTu.getLoaiTien().getBanRa() == 0) {
					errors.rejectValue("loaiTien.banRa", "NotEmptyOrEqual0.chungTu.loaiTien.banRa");
				}

				// Kiểm tra dữ liệu phần định khoản
				List<TaiKhoan> taiKhoanDs = chungTu.getTaiKhoanDs();
				boolean coTkCo = true;
				boolean coTrungLapTkCo = false;

				Iterator<TaiKhoan> iter = taiKhoanDs.iterator();
				while (iter.hasNext()) {
					TaiKhoan taiKhoan = iter.next();

					if (taiKhoan.getSoDu() == LoaiTaiKhoan.CO && taiKhoan.getLoaiTaiKhoan().getMaTk().equals("0")) {
						coTkCo = false;
					}
				}

				if (!coTkCo) {
					errors.rejectValue("taiKhoanCoDs[0].loaiTaiKhoan.maTk",
							"NotEmpty.taiKhoanCoDs[0].loaiTaiKhoan.maTk");
				}

				// Kiểm tra tài khoản trùng lặp ở bên có
				iter = taiKhoanDs.iterator();
				while (iter.hasNext()) {
					TaiKhoan taiKhoan = iter.next();

					if (taiKhoan.getSoDu() == LoaiTaiKhoan.CO && !taiKhoan.getLoaiTaiKhoan().getMaTk().equals("0")) {
						boolean daGap = false;
						Iterator<TaiKhoan> inIter = taiKhoanDs.iterator();
						while (inIter.hasNext()) {
							TaiKhoan taiKhoanKhac = inIter.next();

							if (taiKhoanKhac.getSoDu() == LoaiTaiKhoan.CO
									&& !taiKhoanKhac.getLoaiTaiKhoan().getMaTk().equals("0")) {
								if (taiKhoan.getLoaiTaiKhoan().equals(taiKhoanKhac.getLoaiTaiKhoan())) {
									if (!daGap) {
										daGap = true;
									} else {
										coTrungLapTkCo = true;
										break;
									}
								}
							}
						}

						if (coTrungLapTkCo) {
							break;
						}
					}
				}

				if (coTrungLapTkCo) {
					errors.rejectValue("taiKhoanCoDs[0].loaiTaiKhoan.maTk",
							"Duplicate.taiKhoanCoDs[0].loaiTaiKhoan.maTk");
				}
			} else if (chungTu.getLoaiCt().trim().equals(ChungTu.CHUNG_TU_PHIEU_CHI)
					|| chungTu.getLoaiCt().trim().equals(ChungTu.CHUNG_TU_BAO_NO)) {
				// Validate cho phần phiếu chi hoặc báo nợ
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "doiTuong.tenDt", "NotEmpty.chungTu.doiTuong.tenDt");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lyDo", "NotEmpty.chungTu.lyDo");

				if (chungTu.getLoaiTien().getBanRa() == 0) {
					errors.rejectValue("loaiTien.banRa", "NotEmptyOrEqual0.chungTu.loaiTien.banRa");
				}

				// Kiểm tra dữ liệu phần định khoản
				List<TaiKhoan> taiKhoanDs = chungTu.getTaiKhoanDs();
				boolean coTkNo = true;
				boolean coTrungLapTkNo = false;

				Iterator<TaiKhoan> iter = taiKhoanDs.iterator();
				while (iter.hasNext()) {
					TaiKhoan taiKhoan = iter.next();

					if (taiKhoan.getSoDu() == LoaiTaiKhoan.NO && taiKhoan.getLoaiTaiKhoan().getMaTk().equals("0")) {
						coTkNo = false;
					}
				}

				if (!coTkNo) {
					errors.rejectValue("taiKhoanNoDs[0].loaiTaiKhoan.maTk",
							"NotEmpty.taiKhoanNoDs[0].loaiTaiKhoan.maTk");
				}

				// Kiểm tra tài khoản trùng lặp ở bên nợ
				iter = taiKhoanDs.iterator();
				while (iter.hasNext()) {
					TaiKhoan taiKhoan = iter.next();

					if (taiKhoan.getSoDu() == LoaiTaiKhoan.NO && !taiKhoan.getLoaiTaiKhoan().getMaTk().equals("0")) {
						boolean daGap = false;
						Iterator<TaiKhoan> inIter = taiKhoanDs.iterator();
						while (inIter.hasNext()) {
							TaiKhoan taiKhoanKhac = inIter.next();

							if (taiKhoanKhac.getSoDu() == LoaiTaiKhoan.NO
									&& !taiKhoanKhac.getLoaiTaiKhoan().getMaTk().equals("0")) {
								if (taiKhoan.getLoaiTaiKhoan().equals(taiKhoanKhac.getLoaiTaiKhoan())) {
									if (!daGap) {
										daGap = true;
									} else {
										coTrungLapTkNo = true;
										break;
									}
								}
							}
						}

						if (coTrungLapTkNo) {
							break;
						}
					}
				}

				if (coTrungLapTkNo) {
					errors.rejectValue("taiKhoanNoDs[0].loaiTaiKhoan.maTk",
							"Duplicate.taiKhoanNoDs[0].loaiTaiKhoan.maTk");
				}
			} else if (chungTu.getLoaiCt().trim().equals(ChungTu.CHUNG_TU_KT_TH)) {
				// Validate cho phần kế toán tổng hợp
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "doiTuong.tenDt", "NotEmpty.chungTu.doiTuong.tenDt");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lyDo", "NotEmpty.chungTu.lyDo");

				if (chungTu.getLoaiTien().getBanRa() == 0) {
					errors.rejectValue("loaiTien.banRa", "NotEmptyOrEqual0.chungTu.loaiTien.banRa");
				}

				// Kiểm tra dữ liệu phần định khoản
				List<TaiKhoan> taiKhoanDs = chungTu.getTaiKhoanDs();
				boolean coTkNo = true;
				boolean coTkCo = true;
				boolean coTrungLapTk = false;
				double noSoTien = 0;
				double coSoTien = 0;

				Iterator<TaiKhoan> iter = taiKhoanDs.iterator();
				while (iter.hasNext()) {
					TaiKhoan taiKhoan = iter.next();

					if (taiKhoan.getSoDu() == LoaiTaiKhoan.NO && taiKhoan.getLoaiTaiKhoan().getMaTk().equals("0")) {
						coTkNo = false;
					} else if (taiKhoan.getSoDu() == LoaiTaiKhoan.CO
							&& taiKhoan.getLoaiTaiKhoan().getMaTk().equals("0")) {
						coTkCo = false;
					}

					if (taiKhoan.getSoDu() == LoaiTaiKhoan.NO && !taiKhoan.getLoaiTaiKhoan().getMaTk().equals("0")) {
						noSoTien += taiKhoan.getSoTien().getSoTien();
					} else if (taiKhoan.getSoDu() == LoaiTaiKhoan.CO
							&& !taiKhoan.getLoaiTaiKhoan().getMaTk().equals("0")) {
						coSoTien += taiKhoan.getSoTien().getSoTien();
					}
				}

				if (!coTkNo) {
					errors.rejectValue("taiKhoanNoDs[0].loaiTaiKhoan.maTk",
							"NotEmpty.taiKhoanNoDs[0].loaiTaiKhoan.maTk");
				} else if (!coTkCo) {
					errors.rejectValue("taiKhoanCoDs[0].loaiTaiKhoan.maTk",
							"NotEmpty.taiKhoanCoDs[0].loaiTaiKhoan.maTk");
				} else {
					if (noSoTien != coSoTien) {
						errors.rejectValue("soTien.soTien", "NotEmpty.taiKhoanNoDs[0].soTien.soTien");
					}
				}

				// Kiểm tra tài khoản trùng lặp
				iter = taiKhoanDs.iterator();
				while (iter.hasNext()) {
					TaiKhoan taiKhoan = iter.next();

					if (!taiKhoan.getLoaiTaiKhoan().getMaTk().equals("0")) {
						boolean daGap = false;
						Iterator<TaiKhoan> inIter = taiKhoanDs.iterator();
						while (inIter.hasNext()) {
							TaiKhoan taiKhoanKhac = inIter.next();

							if (!taiKhoanKhac.getLoaiTaiKhoan().getMaTk().equals("0")) {
								if (taiKhoan.getLoaiTaiKhoan().equals(taiKhoanKhac.getLoaiTaiKhoan())) {
									if (!daGap) {
										daGap = true;
									} else {
										coTrungLapTk = true;
										break;
									}
								}
							}
						}

						if (coTrungLapTk) {
							break;
						}
					}
				}

				if (coTrungLapTk) {
					errors.rejectValue("soTien.soTien", "Duplicate.taiKhoanDs[0].loaiTaiKhoan.maTk");
				}
			} else if (chungTu.getLoaiCt().trim().equals(ChungTu.CHUNG_TU_BT_KC)) {
				// Validate cho phần kế toán tổng hợp
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "doiTuong.tenDt", "NotEmpty.chungTu.doiTuong.tenDt");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lyDo", "NotEmpty.chungTu.lyDo");

				if (chungTu.getLoaiTien().getBanRa() == 0) {
					errors.rejectValue("loaiTien.banRa", "NotEmptyOrEqual0.chungTu.loaiTien.banRa");
				}

				// Kiểm tra dữ liệu phần định khoản
				List<TaiKhoan> taiKhoanDs = chungTu.getTaiKhoanDs();
				boolean coTkNo = true;
				boolean coTkCo = true;
				boolean coTrungLapTk = false;
				double noSoTien = 0;
				double coSoTien = 0;

				Iterator<TaiKhoan> iter = taiKhoanDs.iterator();
				while (iter.hasNext()) {
					TaiKhoan taiKhoan = iter.next();

					if (taiKhoan.getSoDu() == LoaiTaiKhoan.NO && taiKhoan.getLoaiTaiKhoan().getMaTk().equals("0")) {
						coTkNo = false;
					} else if (taiKhoan.getSoDu() == LoaiTaiKhoan.CO
							&& taiKhoan.getLoaiTaiKhoan().getMaTk().equals("0")) {
						coTkCo = false;
					}

					if (taiKhoan.getSoDu() == LoaiTaiKhoan.NO && !taiKhoan.getLoaiTaiKhoan().getMaTk().equals("0")) {
						noSoTien += taiKhoan.getSoTien().getSoTien();
					} else if (taiKhoan.getSoDu() == LoaiTaiKhoan.CO
							&& !taiKhoan.getLoaiTaiKhoan().getMaTk().equals("0")) {
						coSoTien += taiKhoan.getSoTien().getSoTien();
					}
				}

				if (!coTkNo) {
					errors.rejectValue("taiKhoanNoDs[0].loaiTaiKhoan.maTk",
							"NotEmpty.taiKhoanNoDs[0].loaiTaiKhoan.maTk");
				} else if (!coTkCo) {
					errors.rejectValue("taiKhoanCoDs[0].loaiTaiKhoan.maTk",
							"NotEmpty.taiKhoanCoDs[0].loaiTaiKhoan.maTk");
				} else {
					if (noSoTien != coSoTien) {
						errors.rejectValue("soTien.soTien", "NotEmpty.taiKhoanNoDs[0].soTien.soTien");
					}
				}

				// Kiểm tra tài khoản trùng lặp
				iter = taiKhoanDs.iterator();
				while (iter.hasNext()) {
					TaiKhoan taiKhoan = iter.next();

					if (!taiKhoan.getLoaiTaiKhoan().getMaTk().equals("0")) {
						boolean daGap = false;
						Iterator<TaiKhoan> inIter = taiKhoanDs.iterator();
						while (inIter.hasNext()) {
							TaiKhoan taiKhoanKhac = inIter.next();

							if (!taiKhoanKhac.getLoaiTaiKhoan().getMaTk().equals("0")) {
								if (taiKhoan.getLoaiTaiKhoan().equals(taiKhoanKhac.getLoaiTaiKhoan())) {
									if (!daGap) {
										daGap = true;
									} else {
										coTrungLapTk = true;
										break;
									}
								}
							}
						}

						if (coTrungLapTk) {
							break;
						}
					}
				}

				if (coTrungLapTk) {
					errors.rejectValue("soTien.soTien", "Duplicate.taiKhoanDs[0].loaiTaiKhoan.maTk");
				}
			}
		}
	}
}
