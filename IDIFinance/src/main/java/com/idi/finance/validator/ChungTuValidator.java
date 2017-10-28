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
			if (chungTu.getLoaiCt().trim().equals(ChungTu.CHUNG_TU_PHIEU_THU)) {
				// Validate cho phần phiếu thu
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "doiTuong.tenDt", "NotEmpty.chungTu.doiTuong.tenDt");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lyDo", "NotEmpty.chungTu.lyDo");

				if (chungTu.getSoTien().getSoTien() == 0 && errors.getFieldError("soTien.soTien") == null) {
					errors.rejectValue("soTien.soTien", "NotEmptyOrEqual0.chungTu.soTien.soTien");
				}

				if (chungTu.getSoTien().getTien().getBanRa() == 0) {
					errors.rejectValue("soTien.tien.banRa", "NotEmptyOrEqual0.chungTu.soTien.tien.banRa");
				}

				// Kiểm tra dữ liệu phần định khoản
				List<TaiKhoan> taiKhoanDs = chungTu.getTaiKhoanDs();
				boolean coTkCo = false;
				boolean coTrungLapTkCo = false;
				double noSoTien = 0;
				double coSoTien = 0;

				Iterator<TaiKhoan> iter = taiKhoanDs.iterator();
				while (iter.hasNext()) {
					TaiKhoan taiKhoan = iter.next();

					if (taiKhoan.getGhiNo() == LoaiTaiKhoan.NO) {
						noSoTien += taiKhoan.getSoTien();
					} else if (taiKhoan.getGhiNo() == LoaiTaiKhoan.CO
							&& !taiKhoan.getTaiKhoan().getMaTk().equals("0")) {
						coTkCo = true;
						coSoTien += taiKhoan.getSoTien();
					}
				}

				if (!coTkCo) {
					errors.rejectValue("taiKhoanCoDs[0].taiKhoan.maTk", "NotEmpty.taiKhoanCoDs[0].taiKhoan.maTk");
				} else {
					if (chungTu.getSoTien().getSoTien() != noSoTien) {
						errors.rejectValue("soTien.soTien", "NotEqual.chungTu.soTien.soTien.no");
						errors.rejectValue("taiKhoanNoDs[0].soTien", "NotEqual.taiKhoanNoDs[0].soTien");
					} else if (noSoTien != coSoTien) {
						errors.rejectValue("taiKhoanNoDs[0].soTien", "NotEmpty.taiKhoanNoDs[0].soTien");
					}
				}

				// Kiểm tra tài khoản trùng lặp ở bên có
				iter = taiKhoanDs.iterator();
				while (iter.hasNext()) {
					TaiKhoan taiKhoan = iter.next();

					if (taiKhoan.getGhiNo() == LoaiTaiKhoan.CO && !taiKhoan.getTaiKhoan().getMaTk().equals("0")) {
						boolean daGap = false;
						Iterator<TaiKhoan> inIter = taiKhoanDs.iterator();
						while (inIter.hasNext()) {
							TaiKhoan taiKhoanKhac = inIter.next();

							if (taiKhoanKhac.getGhiNo() == LoaiTaiKhoan.CO
									&& !taiKhoanKhac.getTaiKhoan().getMaTk().equals("0")) {
								if (taiKhoan.equals(taiKhoanKhac)) {
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
					errors.rejectValue("taiKhoanCoDs[0].taiKhoan.maTk", "Duplicate.taiKhoanCoDs[0].taiKhoan.maTk");
				}
			} else if (chungTu.getLoaiCt().trim().equals(ChungTu.CHUNG_TU_PHIEU_CHI)) {
				// Validate cho phần phiếu chi
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "doiTuong.tenDt", "NotEmpty.chungTu.doiTuong.tenDt");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lyDo", "NotEmpty.chungTu.lyDo");

				if (chungTu.getSoTien().getSoTien() == 0 && errors.getFieldError("soTien.soTien") == null) {
					errors.rejectValue("soTien.soTien", "NotEmptyOrEqual0.chungTu.soTien.soTien");
				}

				if (chungTu.getSoTien().getTien().getBanRa() == 0) {
					errors.rejectValue("soTien.tien.banRa", "NotEmptyOrEqual0.chungTu.soTien.tien.banRa");
				}

				// Kiểm tra dữ liệu phần định khoản
				List<TaiKhoan> taiKhoanDs = chungTu.getTaiKhoanDs();
				boolean coTkNo = false;
				boolean coTrungLapTkNo = false;
				double noSoTien = 0;
				double coSoTien = 0;

				Iterator<TaiKhoan> iter = taiKhoanDs.iterator();
				while (iter.hasNext()) {
					TaiKhoan taiKhoan = iter.next();

					if (taiKhoan.getGhiNo() == LoaiTaiKhoan.NO && !taiKhoan.getTaiKhoan().getMaTk().equals("0")) {
						coTkNo = true;
						noSoTien += taiKhoan.getSoTien();
					} else if (taiKhoan.getGhiNo() == LoaiTaiKhoan.CO) {
						coSoTien += taiKhoan.getSoTien();
					}
				}

				if (!coTkNo) {
					errors.rejectValue("taiKhoanNoDs[0].taiKhoan.maTk", "NotEmpty.taiKhoanNoDs[0].taiKhoan.maTk");
				} else {
					if (chungTu.getSoTien().getSoTien() != coSoTien) {
						errors.rejectValue("soTien.soTien", "NotEqual.chungTu.soTien.soTien.co");
						errors.rejectValue("taiKhoanCoDs[0].soTien", "NotEqual.taiKhoanCoDs[0].soTien");
					} else if (noSoTien != coSoTien) {
						errors.rejectValue("taiKhoanCoDs[0].soTien", "NotEmpty.taiKhoanCoDs[0].soTien");
					}
				}

				// Kiểm tra tài khoản trùng lặp ở bên nợ
				iter = taiKhoanDs.iterator();
				while (iter.hasNext()) {
					TaiKhoan taiKhoan = iter.next();

					if (taiKhoan.getGhiNo() == LoaiTaiKhoan.NO && !taiKhoan.getTaiKhoan().getMaTk().equals("0")) {
						boolean daGap = false;
						Iterator<TaiKhoan> inIter = taiKhoanDs.iterator();
						while (inIter.hasNext()) {
							TaiKhoan taiKhoanKhac = inIter.next();

							if (taiKhoanKhac.getGhiNo() == LoaiTaiKhoan.NO
									&& !taiKhoanKhac.getTaiKhoan().getMaTk().equals("0")) {
								if (taiKhoan.equals(taiKhoanKhac)) {
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
					errors.rejectValue("taiKhoanNoDs[0].taiKhoan.maTk", "Duplicate.taiKhoanNoDs[0].taiKhoan.maTk");
				}
			}
		}

	}
}
