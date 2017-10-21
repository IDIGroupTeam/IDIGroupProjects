package com.idi.finance.validator;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.idi.finance.bean.chungtu.ChungTu;
import com.idi.finance.bean.chungtu.TaiKhoan;
import com.idi.finance.utils.Contants;

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
		double noSoTien = 0;
		double coSoTien = 0;

		Iterator<TaiKhoan> iter = taiKhoanDs.iterator();
		while (iter.hasNext()) {
			TaiKhoan taiKhoan = iter.next();

			if (taiKhoan.getGhiNo() == Contants.NO) {
				noSoTien += taiKhoan.getSoTien();
			} else if (taiKhoan.getGhiNo() == Contants.CO && !taiKhoan.getTaiKhoan().getMaTk().equals("0")) {
				coTkCo = true;
				coSoTien += taiKhoan.getSoTien();
			}
		}

		if (!coTkCo) {
			errors.rejectValue("taiKhoanDs[1].taiKhoan.maTk", "NotEmpty.taiKhoanDs[1].taiKhoan.maTk");
		} else {
			if (chungTu.getSoTien().getSoTien() != noSoTien) {
				errors.rejectValue("soTien.soTien", "NotEqual.chungTu.soTien.soTien");
				errors.rejectValue("taiKhoanDs[0].soTien", "NotEqual.taiKhoanDs[0].soTien");
			} else if (noSoTien != coSoTien) {
				errors.rejectValue("taiKhoanDs[0].soTien", "NotEmpty.taiKhoanDs[0].soTien");
			}
		}
	}
}
