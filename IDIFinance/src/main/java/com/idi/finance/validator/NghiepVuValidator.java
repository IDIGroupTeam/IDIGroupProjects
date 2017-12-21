package com.idi.finance.validator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.idi.finance.bean.congviec.NghiepVu;
import com.idi.finance.dao.CongViecDAO;

public class NghiepVuValidator implements Validator {
	private static final Logger logger = Logger.getLogger(NghiepVuValidator.class);

	@Autowired
	CongViecDAO congViecDAO;

	@Override
	public boolean supports(Class<?> cls) {
		return cls == NghiepVu.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		NghiepVu nghiepVu = (NghiepVu) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tenNv", "NotEmpty.NghiepVu.tenNv");
		if (nghiepVu.getDoKho() < 1 || nghiepVu.getDoKho() > 5) {
			errors.rejectValue("doKho", "OutOfRange.NghiepVu.doKho");
			logger.info(nghiepVu.getDoKho());
		}
	}
}
