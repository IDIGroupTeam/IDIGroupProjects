package com.idi.finance.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.idi.finance.bean.DungChung;
import com.idi.finance.bean.chungtu.ChungTu;
import com.idi.finance.bean.chungtu.KetChuyenButToan;
import com.idi.finance.bean.doituong.DoiTuong;
import com.idi.finance.bean.hanghoa.HangHoa;
import com.idi.finance.bean.kyketoan.KyKeToan;
import com.idi.finance.bean.soketoan.NghiepVuKeToan;
import com.idi.finance.bean.taikhoan.TaiKhoan;
import com.idi.finance.dao.ChungTuDAO;
import com.idi.finance.dao.KyKeToanDAO;

public class ChungTuValidator implements Validator {
	private static final Logger logger = Logger.getLogger(ChungTuValidator.class);

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String ID_PATTERN = "[0-9]+";
	private static final String STRING_PATTERN = "[a-zA-Z]+";
	private static final String MOBILE_PATTERN = "[0-9]{10}";

	@Autowired
	DungChung dungChung;

	@Autowired
	ChungTuDAO chungTuDAO;

	@Autowired
	KyKeToanDAO kyKeToanDAO;

	@Override
	public boolean supports(Class<?> cls) {
		return cls == ChungTu.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		ChungTu chungTu = (ChungTu) target;
		KyKeToan kyKeToan = dungChung.getKyKeToan();

		if (!kiemTraKyketoanMo(chungTu)) {
			errors.rejectValue("ngayHt", "chungTu.ngayHt.kyKeToan.dong");
		}

		if (chungTu.getLoaiCt() != null) {
			if (chungTu.getLoaiCt().trim().equals(ChungTu.CHUNG_TU_PHIEU_THU)
					|| chungTu.getLoaiCt().trim().equals(ChungTu.CHUNG_TU_BAO_CO)) {
				// Validate cho phần phiếu thu hoặc báo có
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "doiTuong.tenDt", "NotEmpty.chungTu.doiTuong.tenDt");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lyDo", "NotEmpty.chungTu.lyDo");

				if (chungTu.getLoaiTien().getBanRa() == 0) {
					errors.rejectValue("loaiTien.banRa", "NotEmptyOrEqual0.chungTu.loaiTien.banRa");
				}

				try {
					if (chungTu.getMaCt() == 0) {
						int count = chungTuDAO.kiemTraSoChungTu(chungTu.getSoCt(), chungTu.getLoaiCt(),
								kyKeToan.getBatDau(), kyKeToan.getKetThuc());
						if (count > 0) {
							errors.rejectValue("soCt", "Existed.chungTu.soCt");
						}
					}
				} catch (Exception e) {
					// logger.info(e.getMessage());
				}

				List<TaiKhoan> taiKhoanCoDs = chungTu.getTaiKhoanCoDs();
				for (int j = 0; j < taiKhoanCoDs.size(); j++) {
					TaiKhoan taiKhoanCo = taiKhoanCoDs.get(j);

					logger.info("Co " + taiKhoanCo.getLoaiTaiKhoan().getMaTk() + " - "
							+ taiKhoanCo.getSoTien().getSoTien());

					if (taiKhoanCo.getLoaiTaiKhoan().getMaTk().isEmpty()) {
						errors.rejectValue("taiKhoanCoDs[" + j + "].loaiTaiKhoan.maTk",
								"NotEmpty.taiKhoanCoDs.loaiTaiKhoan.maTk");
					}
				}

				// Kiểm tra tài khoản trùng lặp ở bên có
				List<TaiKhoan> taiKhoanTmplDs = new ArrayList<>();
				Iterator<TaiKhoan> iter = taiKhoanCoDs.iterator();
				while (iter.hasNext()) {
					TaiKhoan taiKhoan = iter.next();

					if (!taiKhoan.getLoaiTaiKhoan().getMaTk().isEmpty()) {
						int pos = taiKhoanTmplDs.indexOf(taiKhoan);
						if (pos > -1) {
							errors.rejectValue("taiKhoanCoDs[" + pos + "].loaiTaiKhoan.maTk",
									"Duplicate.taiKhoanCoDs.loaiTaiKhoan.maTk");
						} else {
							taiKhoanTmplDs.add(taiKhoan);
						}
					}
				}
			} else if (chungTu.getLoaiCt().trim().equals(ChungTu.CHUNG_TU_PHIEU_CHI)
					|| chungTu.getLoaiCt().trim().equals(ChungTu.CHUNG_TU_BAO_NO)) {
				// Validate cho phần phiếu chi hoặc báo nợ
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "doiTuong.tenDt", "NotEmpty.chungTu.doiTuong.tenDt");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lyDo", "NotEmpty.chungTu.lyDo");

				if (chungTu.getLoaiTien().getBanRa() == 0) {
					errors.rejectValue("loaiTien.banRa", "NotEmptyOrEqual0.chungTu.loaiTien.banRa");
				}

				try {
					if (chungTu.getMaCt() == 0) {
						int count = chungTuDAO.kiemTraSoChungTu(chungTu.getSoCt(), chungTu.getLoaiCt(),
								kyKeToan.getBatDau(), kyKeToan.getKetThuc());
						if (count > 0) {
							errors.rejectValue("soCt", "Existed.chungTu.soCt");
						}
					}
				} catch (Exception e) {
					logger.info(e.getMessage());
				}

				List<TaiKhoan> taiKhoanNoDs = chungTu.getTaiKhoanNoDs();
				for (int j = 0; j < taiKhoanNoDs.size(); j++) {
					TaiKhoan taiKhoanNo = taiKhoanNoDs.get(j);

					logger.info("No " + taiKhoanNo.getLoaiTaiKhoan().getMaTk() + " - "
							+ taiKhoanNo.getSoTien().getSoTien());

					if (taiKhoanNo.getLoaiTaiKhoan().getMaTk().isEmpty()) {
						errors.rejectValue("taiKhoanNoDs[" + j + "].loaiTaiKhoan.maTk",
								"NotEmpty.taiKhoanNoDs.loaiTaiKhoan.maTk");
					}
				}

				// Kiểm tra tài khoản trùng lặp ở bên nợ
				List<TaiKhoan> taiKhoanTmplDs = new ArrayList<>();
				Iterator<TaiKhoan> iter = taiKhoanNoDs.iterator();
				while (iter.hasNext()) {
					TaiKhoan taiKhoan = iter.next();

					if (!taiKhoan.getLoaiTaiKhoan().getMaTk().isEmpty()) {
						int pos = taiKhoanTmplDs.indexOf(taiKhoan);
						if (pos > -1) {
							errors.rejectValue("taiKhoanNoDs[" + pos + "].loaiTaiKhoan.maTk",
									"Duplicate.taiKhoanNoDs.loaiTaiKhoan.maTk");
						} else {
							taiKhoanTmplDs.add(taiKhoan);
						}
					}
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
										&& hangHoa.getTkThueNk().getSoTien().getGiaTri() > 0) {
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
										&& hangHoa.getTkThueTtdb().getSoTien().getGiaTri() > 0) {
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
										&& hangHoa.getTkThueGtgt().getSoTien().getGiaTri() > 0) {
									errors.rejectValue("hangHoaDs[" + id + "].tkThueGtgt.loaiTaiKhoan.maTk",
											"NotEmpty.hangHoa.tkThueGtgt.loaiTaiKhoan.maTk");
								}
							}
						}

						id++;
					}

					// Validate kế toán tổng hợp - nếu có
					if (chungTu != null && chungTu.getNvktDs() != null) {
						id = 0;
						logger.info("chungTu.getNvktDs(): " + chungTu.getNvktDs().size());
						Iterator<NghiepVuKeToan> ktthIter = chungTu.getNvktDs().iterator();
						while (ktthIter.hasNext()) {
							NghiepVuKeToan nvkt = ktthIter.next();

							TaiKhoan taiKhoanNo = nvkt.getTaiKhoanNo();
							TaiKhoan taiKhoanCo = nvkt.getTaiKhoanCo();

							logger.info("nvkt: " + nvkt);
							logger.info("taiKhoanNo: " + taiKhoanNo);
							logger.info("taiKhoanCo: " + taiKhoanCo);

							if (taiKhoanNo != null && taiKhoanNo.getLoaiTaiKhoan().getMaTk() != null
									&& !taiKhoanNo.getLoaiTaiKhoan().getMaTk().isEmpty()
									&& taiKhoanNo.getSoTien() != null && taiKhoanNo.getSoTien().getSoTien() > 0
									&& taiKhoanCo != null && taiKhoanCo.getLoaiTaiKhoan().getMaTk() != null
									&& !taiKhoanCo.getLoaiTaiKhoan().getMaTk().isEmpty()) {
								// Valid data, do nothing
							} else {
								// Invalid data, send message
								if (taiKhoanNo == null || taiKhoanNo.getLoaiTaiKhoan().getMaTk() == null
										|| taiKhoanNo.getLoaiTaiKhoan().getMaTk().isEmpty()) {
									// errors.rejectValue("nvktDs[" + id + "].taiKhoanNo.loaiTaiKhoan.maTk", "");
								} else if (taiKhoanNo.getSoTien() == null || taiKhoanNo.getSoTien().getSoTien() == 0) {
									// errors.rejectValue("nvktDs[" + id + "].taiKhoanNo.soTien.soTien", "");
								} else if (taiKhoanCo == null || taiKhoanCo.getLoaiTaiKhoan().getMaTk() == null
										|| taiKhoanCo.getLoaiTaiKhoan().getMaTk().isEmpty()) {
									// errors.rejectValue("nvktDs[" + id + "].taiKhoanCo.loaiTaiKhoan.maTk", "");
								}
							}
							id++;
						}
					}
				}
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
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lyDo", "NotEmpty.chungTu.lyDo");

				if (chungTu.getLoaiTien().getBanRa() == 0) {
					errors.rejectValue("loaiTien.banRa", "NotEmptyOrEqual0.chungTu.loaiTien.banRa");
				}

				// Kiểm tra dữ liệu phần định khoản
				HashMap<Integer, TaiKhoan> nhomDkMap = new HashMap<>();
				List<TaiKhoan> taiKhoanDs = chungTu.getTaiKhoanKtthDs();
				for (int j = 0; j < taiKhoanDs.size(); j++) {
					TaiKhoan taiKhoan = taiKhoanDs.get(j);

					if (taiKhoan.getLyDo() == null || taiKhoan.getLyDo().trim().isEmpty()) {
						errors.rejectValue("taiKhoanKtthDs[" + j + "].lyDo", "NotEmpty.chungTu.lyDo");
					}
					if (taiKhoan.getLoaiTaiKhoan() == null || taiKhoan.getLoaiTaiKhoan().getMaTk() == null
							|| taiKhoan.getLoaiTaiKhoan().getMaTk().trim().isEmpty()) {
						errors.rejectValue("taiKhoanKtthDs[" + j + "].loaiTaiKhoan.maTk",
								"ThieuDinhKhoan.LoaiTaiKhoan.maTk");
					}

					Integer nhomDk = new Integer(taiKhoan.getNhomDk());
					TaiKhoan taiKhoanTmpl = nhomDkMap.get(nhomDk);
					if (taiKhoanTmpl == null) {
						taiKhoanTmpl = new TaiKhoan();
						taiKhoanTmpl.getNo().setSoTien(taiKhoan.getNo().getSoTien());
						taiKhoanTmpl.getCo().setSoTien(taiKhoan.getCo().getSoTien());
						nhomDkMap.put(nhomDk, taiKhoanTmpl);
					} else {
						taiKhoanTmpl.getNo().setSoTien(taiKhoanTmpl.getNo().getSoTien() + taiKhoan.getNo().getSoTien());
						taiKhoanTmpl.getCo().setSoTien(taiKhoanTmpl.getCo().getSoTien() + taiKhoan.getCo().getSoTien());
					}
				}

				// Kiểm tra tính cân bằng nợ có của các nhóm định khoản
				Iterator<Integer> iter = nhomDkMap.keySet().iterator();
				while (iter.hasNext()) {
					Integer nhomDk = iter.next();
					TaiKhoan taiKhoanTmpl = nhomDkMap.get(nhomDk);

					if (taiKhoanTmpl.getNo().getSoTien() != taiKhoanTmpl.getCo().getSoTien()) {
						taiKhoanTmpl.setCanBang(false);
					}
				}

				for (int j = 0; j < taiKhoanDs.size(); j++) {
					TaiKhoan taiKhoan = taiKhoanDs.get(j);
					Integer nhomDk = new Integer(taiKhoan.getNhomDk());

					TaiKhoan taiKhoanTmpl = nhomDkMap.get(nhomDk);
					if (taiKhoanTmpl != null && !taiKhoanTmpl.isCanBang()) {
						errors.rejectValue("taiKhoanKtthDs[" + j + "].lyDo",
								"ThieuDinhKhoan.NhomDinhKhoan.KhongCanBang");
					}
				}

				// Kiểm tra, trong cùng nhóm tài khoản,
				// đối tượng ghi vào các tài khoản phải giống nhau
				HashMap<Integer, DoiTuong> nhomDkDtMap = new HashMap<>();
				for (int j = 0; j < taiKhoanDs.size(); j++) {
					TaiKhoan taiKhoan = taiKhoanDs.get(j);

					DoiTuong doiTuong = nhomDkDtMap.get(taiKhoan.getNhomDk());
					if (doiTuong == null) {
						if (taiKhoan.getDoiTuong().getMaDt() != 0 && taiKhoan.getDoiTuong().getLoaiDt() != 0) {
							nhomDkDtMap.put(taiKhoan.getNhomDk(), taiKhoan.getDoiTuong());
						}
					} else {
						if (!taiKhoan.getDoiTuong().equals(doiTuong)) {
							if (taiKhoan.getDoiTuong().getMaDt() == 0 || taiKhoan.getDoiTuong().getLoaiDt() == 0) {
								taiKhoan.setDoiTuong(doiTuong);
							}
						}
					}
				}
				for (int j = 0; j < taiKhoanDs.size(); j++) {
					TaiKhoan taiKhoan = taiKhoanDs.get(j);
					DoiTuong doiTuong = nhomDkDtMap.get(taiKhoan.getNhomDk());
					if (doiTuong == null) {
						errors.rejectValue("taiKhoanKtthDs[" + j + "].doiTuong.khoaDt",
								"NotEmpty.chungTu.doiTuong.tenDt");
					}
				}

				// Giới hạn quan hệ 1-n của nhóm ĐK

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

		// Validate ngay_ht & ngay_tt: dd/MM/yyyy: 12/09/2018
		// đã được xử lý ở ChungTuController, do binder làm
		// Phần validate dưới đây chỉ kiểm tra khi người dùng không nhập gì
		if (chungTu.getNgayHt() == null && !errors.hasFieldErrors("ngayHt")) {
			errors.rejectValue("ngayHt", "NotEmpty.mainFinanceForm.ngayHt");
		}
		if (chungTu.getNgayLap() == null && !errors.hasFieldErrors("ngayLap")) {
			errors.rejectValue("ngayLap", "NotEmpty.mainFinanceForm.ngayLap");
		}
	}

	private boolean kiemTraKyketoanMo(ChungTu chungTu) {
		KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(chungTu);
		if (kyKeToan != null && kyKeToan.getTrangThai() == KyKeToan.DONG) {
			return false;
		}

		return true;
	}
}
