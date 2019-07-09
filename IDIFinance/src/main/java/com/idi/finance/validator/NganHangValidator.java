package com.idi.finance.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.idi.finance.bean.doituong.NganHang;

public class NganHangValidator implements Validator {
	@Override
	public boolean supports(Class<?> cls) {
		return cls == NganHang.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tenVt", "NotEmpty.NganHang.tenVt");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tenDd", "NotEmpty.NganHang.tenDd");
	}
}
