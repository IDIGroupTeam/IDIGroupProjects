package com.idi.finance.validator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.idi.finance.bean.hanghoa.HangHoa;
import com.idi.finance.dao.HangHoaDAO;

public class HangHoaValidator implements Validator {
	private static final Logger logger = Logger.getLogger(HangHoaValidator.class);

	@Autowired
	HangHoaDAO hangHoaDAO;

	@Override
	public boolean supports(Class<?> cls) {
		return cls == HangHoa.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		HangHoa hangHoa = (HangHoa) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tenHh", "NotEmpty.HangHoa.tenHh");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "kyHieuHh", "NotEmpty.HangHoa.kyHieuHh");

		if (hangHoa.getNhomHh() != null && hangHoa.getNhomHh().getMaNhomHh() == 0) {
			errors.rejectValue("nhomHh.maNhomHh", "NotEmpty.HangHoa.nhomHh.maNhomHh");
		}

		if (hangHoa.getDonVi() != null && hangHoa.getDonVi().getMaDv() == 0) {
			errors.rejectValue("donVi.maDv", "NotEmpty.HangHoa.donVi.maDv");
		}

		if (!hangHoaDAO.kiemTraDuyNhat(hangHoa.getMaHh(), hangHoa.getKyHieuHh())) {
			errors.rejectValue("kyHieuHh", "TonTai.HangHoa.kyHieuHh");
		}
	}
}
