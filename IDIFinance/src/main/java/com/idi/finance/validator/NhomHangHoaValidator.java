package com.idi.finance.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.idi.finance.bean.hanghoa.NhomHang;

public class NhomHangHoaValidator implements Validator {

	@Override
	public boolean supports(Class<?> cls) {
		return cls == NhomHang.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tenNhomHh", "NotEmpty.NhomHangHoa.tenNhomHh");
	}
}
