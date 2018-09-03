package com.idi.finance.validator;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.idi.finance.bean.bctc.BalanceAssetItem;

public class BalanceSheetValidator implements Validator {
	private static final Logger logger = Logger.getLogger(BalanceSheetValidator.class);

	@Override
	public boolean supports(Class<?> cls) {
		return cls == BalanceAssetItem.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		BalanceAssetItem bai = (BalanceAssetItem) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "assetCode", "NotEmpty.Bai.assetCode");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "childs[0].taiKhoanDs[0].maTkGoc",
				"NotEmpty.Bai.LoaiTaiKhoan.maTk");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "childs[0].taiKhoanDs[0].maTk",
				"NotEmpty.Bai.LoaiTaiKhoan.maTk");
	}
}
