package com.idi.finance.validator;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.idi.finance.bean.KhachHang;
import com.idi.finance.dao.KhachHangDAO;

public class KhachHangValidator implements Validator {
	// common-validator library.
	private EmailValidator emailValidator = EmailValidator.getInstance();

	@Autowired
	private KhachHangDAO khachHangDAO;

	@Override
	public boolean supports(Class<?> cls) {
		return cls == KhachHang.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		KhachHang khachHang = (KhachHang) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tenKh", "NotEmpty.KhachHang.tenKh");

		if (khachHang.getEmail() != null && !khachHang.getEmail().trim().equals("")) {
			if (!emailValidator.isValid(khachHang.getEmail())) {
				errors.rejectValue("email", "Pattern.KhachHang.email");
			}
		}
	}
}
