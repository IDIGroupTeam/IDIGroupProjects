package com.idi.finance.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.idi.finance.bean.hanghoa.HangHoa;

public class HangHoaValidator implements Validator {

	@Override
	public boolean supports(Class<?> cls) {
		return cls == HangHoa.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tenHh", "NotEmpty.HangHoa.tenHh");
	}
}
