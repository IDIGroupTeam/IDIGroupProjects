package com.idi.finance.validator;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.idi.finance.bean.chungtu.ChungTu;

public class ChungTuValidator implements Validator {
	private static final Logger logger = Logger.getLogger(ChungTuValidator.class);

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String ID_PATTERN = "[0-9]+";
	private static final String STRING_PATTERN = "[a-zA-Z]+";
	private static final String MOBILE_PATTERN = "[0-9]{10}";

	@Override
	public boolean supports(Class<?> cls) {
		return cls == ChungTu.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		ChungTu chungTu = (ChungTu) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "doiTuong.tenDt", "NotEmpty.chungTu.doiTuong.tenDt");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "soTien.soTien", "NotEmpty.chungTu.soTien.soTien");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lyDo", "NotEmpty.chungTu.lyDo");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "soTien.tien.banRa", "NotEmpty.chungTu.soTien.tien.banRa");
		
		
	}
}
