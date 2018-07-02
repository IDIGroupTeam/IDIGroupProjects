package com.idi.finance.validator;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.idi.finance.bean.chungtu.ChungTu;
import com.idi.finance.bean.chungtu.KetChuyenButToan;
import com.idi.finance.bean.chungtu.TaiKhoan;
import com.idi.finance.bean.hanghoa.HangHoa;
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
			} else if (chungTu.getLoaiCt().trim().equals(ChungTu.CHUNG_TU_MUA_HANG)) {
				// Validate cho chứng từ mua hàng
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "doiTuong.tenDt", "NotEmpty.chungTu.doiTuong.tenDt");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lyDo", "NotEmpty.chungTu.lyDo");

				if (chungTu.getLoaiTien().getBanRa() == 0) {
					errors.rejectValue("loaiTien.banRa", "NotEmptyOrEqual0.chungTu.loaiTien.banRa");
				}

				if (chungTu.getHangHoaDs() != null) {
					int id = 0;
					Iterator<HangHoa> iter = chungTu.getHangHoaDs().iterator();
					while (iter.hasNext()) {
						HangHoa hangHoa = iter.next();
						logger.info("Kiểm tra dữ liệu từng mặt hóa " + hangHoa);

						if (hangHoa.getMaHh() == 0) {
							errors.rejectValue("hangHoaDs[" + id + "].maHh", "NotEmpty.hangHoa.maHh");
						}

						if (chungTu.getTinhChatCt() == 1 || chungTu.getTinhChatCt() == 2) {
							if (hangHoa.getKho() == null || hangHoa.getKho().getMaKho() == 0) {
								errors.rejectValue("hangHoaDs[" + id + "].kho.maKho", "NotEmpty.hangHoa.kho.maKho");
							}
						}

						if (hangHoa.getTkKho() == null || hangHoa.getTkKho().getLoaiTaiKhoan() == null
								|| hangHoa.getTkKho().getLoaiTaiKhoan().getMaTk() == null
								|| hangHoa.getTkKho().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
							errors.rejectValue("hangHoaDs[" + id + "].tkKho.loaiTaiKhoan.maTk",
									"NotEmpty.hangHoa.tkKho.loaiTaiKhoan.maTk");
						}

						if (hangHoa.getTkThanhtoan() == null || hangHoa.getTkThanhtoan().getLoaiTaiKhoan() == null
								|| hangHoa.getTkThanhtoan().getLoaiTaiKhoan().getMaTk() == null
								|| hangHoa.getTkThanhtoan().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
							errors.rejectValue("hangHoaDs[" + id + "].tkThanhtoan.loaiTaiKhoan.maTk",
									"NotEmpty.hangHoa.tkThanhtoan.loaiTaiKhoan.maTk");
						}

						if (hangHoa.getTkThueNk() != null) {
							if (hangHoa.getTkThueNk().getLoaiTaiKhoan() == null
									|| hangHoa.getTkThueNk().getLoaiTaiKhoan().getMaTk() == null
									|| hangHoa.getTkThueNk().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
								if (hangHoa.getTkThueNk().getSoTien() != null
										&& hangHoa.getTkThueNk().getSoTien().getSoTien() > 0) {
									errors.rejectValue("hangHoaDs[" + id + "].tkThueNk.loaiTaiKhoan.maTk",
											"NotEmpty.hangHoa.tkThueNk.loaiTaiKhoan.maTk");
								}
							}
						}

						if (hangHoa.getTkThueTtdb() != null) {
							if (hangHoa.getTkThueTtdb().getLoaiTaiKhoan() == null
									|| hangHoa.getTkThueTtdb().getLoaiTaiKhoan().getMaTk() == null
									|| hangHoa.getTkThueTtdb().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
								if (hangHoa.getTkThueTtdb().getSoTien() != null
										&& hangHoa.getTkThueTtdb().getSoTien().getSoTien() > 0) {
									errors.rejectValue("hangHoaDs[" + id + "].tkThueTtdb.loaiTaiKhoan.maTk",
											"NotEmpty.hangHoa.tkThueTtdb.loaiTaiKhoan.maTk");
								}
							}
						}

						if (hangHoa.getTkThueGtgt() != null) {
							if (hangHoa.getTkThueGtgt().getLoaiTaiKhoan() == null
									|| hangHoa.getTkThueGtgt().getLoaiTaiKhoan().getMaTk() == null
									|| hangHoa.getTkThueGtgt().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
								if (hangHoa.getTkThueGtgt().getSoTien() != null
										&& hangHoa.getTkThueGtgt().getSoTien().getSoTien() > 0) {
									errors.rejectValue("hangHoaDs[" + id + "].tkThueGtgt.loaiTaiKhoan.maTk",
											"NotEmpty.hangHoa.tkThueGtgt.loaiTaiKhoan.maTk");
								}
							}
						}

						id++;
					}
				}
				logger.info(errors);
			} else if (chungTu.getLoaiCt().trim().equals(ChungTu.CHUNG_TU_BAN_HANG)) {
				// Validate cho chứng từ mua hàng
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "doiTuong.tenDt", "NotEmpty.chungTu.doiTuong.tenDt");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lyDo", "NotEmpty.chungTu.lyDo");

				if (chungTu.getLoaiTien().getBanRa() == 0) {
					errors.rejectValue("loaiTien.banRa", "NotEmptyOrEqual0.chungTu.loaiTien.banRa");
				}

				if (chungTu.getHangHoaDs() != null) {
					int id = 0;
					Iterator<HangHoa> iter = chungTu.getHangHoaDs().iterator();
					while (iter.hasNext()) {
						HangHoa hangHoa = iter.next();
						logger.info("Kiểm tra dữ liệu từng mặt hóa " + hangHoa);

						if (hangHoa.getMaHh() == 0) {
							errors.rejectValue("hangHoaDs[" + id + "].maHh", "NotEmpty.hangHoa.maHh");
						}

						if (hangHoa.getGiaKho() != null) {
							logger.info("gia von " + hangHoa.getGiaKho().getMaGia());
						}

						if (hangHoa.getGiaKho() == null || hangHoa.getGiaKho().getMaGia() == 0) {
							errors.rejectValue("hangHoaDs[" + id + "].giaKho.maGia", "NotEmpty.hangHoa.giaKho.maGia");
						}

						if (hangHoa.getTkThanhtoan() == null || hangHoa.getTkThanhtoan().getLoaiTaiKhoan() == null
								|| hangHoa.getTkThanhtoan().getLoaiTaiKhoan().getMaTk() == null
								|| hangHoa.getTkThanhtoan().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
							errors.rejectValue("hangHoaDs[" + id + "].tkThanhtoan.loaiTaiKhoan.maTk",
									"NotEmpty.hangHoa.tkThanhtoan.loaiTaiKhoan.maTk");
						}

						if (hangHoa.getTkDoanhThu() == null || hangHoa.getTkDoanhThu().getLoaiTaiKhoan() == null
								|| hangHoa.getTkDoanhThu().getLoaiTaiKhoan().getMaTk() == null
								|| hangHoa.getTkDoanhThu().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
							errors.rejectValue("hangHoaDs[" + id + "].tkDoanhThu.loaiTaiKhoan.maTk",
									"NotEmpty.hangHoa.tkDoanhThu.loaiTaiKhoan.maTk");
						}

						if (hangHoa.getTkKho() == null || hangHoa.getTkKho().getLoaiTaiKhoan() == null
								|| hangHoa.getTkKho().getLoaiTaiKhoan().getMaTk() == null
								|| hangHoa.getTkKho().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
							errors.rejectValue("hangHoaDs[" + id + "].tkKho.loaiTaiKhoan.maTk",
									"NotEmpty.hangHoa.tkKho.loaiTaiKhoan.maTk");
						}

						if (hangHoa.getTkGiaVon() == null || hangHoa.getTkGiaVon().getLoaiTaiKhoan() == null
								|| hangHoa.getTkGiaVon().getLoaiTaiKhoan().getMaTk() == null
								|| hangHoa.getTkGiaVon().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
							logger.info("tk gia von " + hangHoa.getTkGiaVon().getLoaiTaiKhoan());
							errors.rejectValue("hangHoaDs[" + id + "].tkGiaVon.loaiTaiKhoan.maTk",
									"NotEmpty.hangHoa.tkGiaVon.loaiTaiKhoan.maTk");
						}

						if (chungTu.getTinhChatCt() == 1 || chungTu.getTinhChatCt() == 2) {
							if (hangHoa.getKho() == null || hangHoa.getKho().getMaKho() == 0) {
								errors.rejectValue("hangHoaDs[" + id + "].kho.maKho", "NotEmpty.hangHoa.kho.maKho");
							}
						}

						if (hangHoa.getTkThueXk() != null) {
							if (hangHoa.getTkThueXk().getLoaiTaiKhoan() == null
									|| hangHoa.getTkThueXk().getLoaiTaiKhoan().getMaTk() == null
									|| hangHoa.getTkThueXk().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
								if (hangHoa.getTkThueXk().getSoTien() != null
										&& hangHoa.getTkThueXk().getSoTien().getSoTien() > 0) {
									errors.rejectValue("hangHoaDs[" + id + "].tkThueXk.loaiTaiKhoan.maTk",
											"NotEmpty.hangHoa.tkThueXk.loaiTaiKhoan.maTk");
								}
							}
						}

						if (hangHoa.getTkThueGtgt() != null) {
							if (hangHoa.getTkThueGtgt().getLoaiTaiKhoan() == null
									|| hangHoa.getTkThueGtgt().getLoaiTaiKhoan().getMaTk() == null
									|| hangHoa.getTkThueGtgt().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
								if (hangHoa.getTkThueGtgt().getSoTien() != null
										&& hangHoa.getTkThueGtgt().getSoTien().getSoTien() > 0) {
									errors.rejectValue("hangHoaDs[" + id + "].tkThueGtgt.loaiTaiKhoan.maTk",
											"NotEmpty.hangHoa.tkThueGtgt.loaiTaiKhoan.maTk");
								}
							}
						}

						id++;
					}
				}
			} else if (chungTu.getLoaiCt().trim().equals(ChungTu.CHUNG_TU_KT_TH)) {

				// Validate cho phần kế toán tổng hợp
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "doiTuong.tenDt", "NotEmpty.chungTu.doiTuong.tenDt");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lyDo", "NotEmpty.chungTu.lyDo");

				if (chungTu.getLoaiTien().getBanRa() == 0) {
					errors.rejectValue("loaiTien.banRa", "NotEmptyOrEqual0.chungTu.loaiTien.banRa");
				}

				// Giới hạn không để cả hai bên nợ có đề có số tài khoản lớn hơn 1
				if (chungTu.getTaiKhoanNoDs() != null && chungTu.getTaiKhoanNoDs().size() > 1
						&& chungTu.getTaiKhoanCoDs() != null && chungTu.getTaiKhoanCoDs().size() > 1) {
					errors.rejectValue("soTien.soTien", "tooMany.taiKhoan.noCo");
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
			} else if (chungTu.getLoaiCt().trim().equals(ChungTu.CHUNG_TU_KET_CHUYEN)) {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lyDo", "NotEmpty.chungTu.tenKetChuyen");

				if (chungTu.getKcbtDs() == null || chungTu.getKcbtDs().size() == 0) {
					errors.rejectValue("kcbtDs", "NotEmpty.ketChuyenButToan");
				} else {
					boolean duocChon = false;
					Iterator<KetChuyenButToan> iter = chungTu.getKcbtDs().iterator();
					while (iter.hasNext()) {
						KetChuyenButToan ketChuyenButToan = iter.next();

						if (ketChuyenButToan.isChon()) {
							duocChon = true;
							break;
						}
					}

					if (!duocChon) {
						errors.rejectValue("kcbtDs", "NotEmpty.ketChuyenButToan");
					}
				}
			}
		}
	}
}
