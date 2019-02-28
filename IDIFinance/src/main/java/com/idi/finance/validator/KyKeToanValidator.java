package com.idi.finance.validator;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.idi.finance.bean.kyketoan.KyKeToan;
import com.idi.finance.dao.KyKeToanDAO;

public class KyKeToanValidator implements Validator {
	private static final Logger logger = Logger.getLogger(KyKeToanValidator.class);

	@Autowired
	KyKeToanDAO kyKeToanDAO;

	@Override
	public boolean supports(Class<?> cls) {
		return cls == KyKeToan.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		KyKeToan kyKeToan = (KyKeToan) target;

		logger.info(kyKeToan);

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tenKyKt", "NotEmpty.KyKeToan.tenKyKt");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "batDau", "NotEmpty.KyKeToan.batDau");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ketThuc", "NotEmpty.KyKeToan.ketThuc");

		if (kyKeToan != null && kyKeToan.getBatDau() != null && kyKeToan.getKetThuc() != null) {
			if (!kyKeToan.getBatDau().before(kyKeToan.getKetThuc())) {
				errors.rejectValue("ketThuc", "NotBefore.KyKeToan.ketThuc");
			} else {
				Date ketThuc = null;

				if (kyKeToan.getMaKyKt() == 0) {
					ketThuc = kyKeToanDAO.layKetThucLonNhat();
				} else {
					KyKeToan kyKeToanGoc = kyKeToanDAO.layKyKeToan(kyKeToan.getMaKyKt());
					ketThuc = kyKeToanDAO.layKetThucLonNhat(kyKeToanGoc.getKetThuc());
				}

				if (ketThuc != null && !kyKeToan.getBatDau().after(ketThuc)) {
					errors.rejectValue("batDau", "NotValid.KyKeToan.batDau");
				}
			}
		}
	}
}
