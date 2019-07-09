package com.idi.finance.validator;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.idi.finance.bean.chungtu.KetChuyenButToan;

public class KetChuyenButToanValidator implements Validator {
	private static final Logger logger = Logger.getLogger(KetChuyenButToanValidator.class);

	@Override
	public boolean supports(Class<?> cls) {
		return cls == KetChuyenButToan.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tenKc", "NotEmpty.KetChuyenButToan.tenKc");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "congThuc", "NotEmpty.KetChuyenButToan.congThuc");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "taiKhoanNo.loaiTaiKhoan.maTk",
				"NotEmpty.KetChuyenButToan.taiKhoanNo.loaiTaiKhoan.maTk");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "taiKhoanNo.loaiTaiKhoan.maTk",
				"NotEmpty.KetChuyenButToan.taiKhoanCo.loaiTaiKhoan.maTk");
	}
}
