package com.idi.finance.validator;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.idi.finance.bean.bctc.BaoCaoTaiChinh;
import com.idi.finance.bean.bctc.BaoCaoTaiChinhCon;

public class BaoCaoTaiChinhValidator implements Validator {
	private static final Logger logger = Logger.getLogger(BaoCaoTaiChinhValidator.class);

	@Override
	public boolean supports(Class<?> cls) {
		return cls == BaoCaoTaiChinh.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		BaoCaoTaiChinh bctc = (BaoCaoTaiChinh) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tieuDe", "NotEmpty.bctc.tieuDe");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "batDau", "NotEmpty.bctc.batDau");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ketThuc", "NotEmpty.bctc.ketThuc");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nguoiLap", "NotEmpty.bctc.nguoiLap");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "giamDoc", "NotEmpty.bctc.giamDoc");

		if (bctc.getBctcDs() == null || bctc.getBctcDs().size() == 0) {
			errors.rejectValue("bctcDs", "NotEmpty.bctc.bctcDs");
		} else {
			boolean khongChon = true;
			for (BaoCaoTaiChinhCon bctcTmpl : bctc.getBctcDs()) {
				khongChon &= bctcTmpl.getLoaiBctc() == 0;
			}

			if (khongChon) {
				errors.rejectValue("bctcDs", "NotEmpty.bctc.bctcDs");
			}
		}
	}
}
