package com.idi.finance.validator;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.idi.finance.bean.NhaCungCap;
import com.idi.finance.dao.NhaCungCapDAO;

public class NhaCungCapValidator implements Validator {
	// common-validator library.
	private EmailValidator emailValidator = EmailValidator.getInstance();

	@Autowired
	private NhaCungCapDAO nhaCungCapDAO;

	@Override
	public boolean supports(Class<?> cls) {
		return cls == NhaCungCap.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		NhaCungCap nhaCungCap = (NhaCungCap) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tenNcc", "NotEmpty.NhaCungCap.tenNcc");
		
		if (nhaCungCap.getEmail() != null && !nhaCungCap.getEmail().trim().equals("")) {
			if (!emailValidator.isValid(nhaCungCap.getEmail())) {
				errors.rejectValue("email", "Pattern.NhaCungCap.email");
			}
		}
	}
}
