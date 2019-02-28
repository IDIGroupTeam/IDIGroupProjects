package com.idi.finance.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.idi.finance.bean.hanghoa.KhoHang;

public class KhoBaiValidator implements Validator {

	@Override
	public boolean supports(Class<?> cls) {
		return cls == KhoHang.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "kyHieuKho", "NotEmpty.KhoBai.kyHieuKho");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tenKho", "NotEmpty.KhoBai.tenKho");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "diaChi", "NotEmpty.KhoBai.diaChi");
	}
}