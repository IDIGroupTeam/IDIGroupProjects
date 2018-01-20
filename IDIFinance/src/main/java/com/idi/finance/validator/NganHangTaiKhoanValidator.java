package com.idi.finance.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.idi.finance.bean.doitac.NganHangTaiKhoan;

public class NganHangTaiKhoanValidator implements Validator {
	@Override
	public boolean supports(Class<?> cls) {
		return cls == NganHangTaiKhoan.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		NganHangTaiKhoan nganHangTaiKhoan = (NganHangTaiKhoan) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "soTk", "NotEmpty.NganHangTaiKhoan.soTk");
		if (nganHangTaiKhoan.getNganHang() == null || nganHangTaiKhoan.getNganHang().getMaNh() == 0) {
			errors.rejectValue("nganHang.maNh", "NotEmpty.NganHangTaiKhoan.NganHang.maNh");
		}
	}
}
