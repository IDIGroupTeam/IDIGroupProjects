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

		if (bai.getType().equals(BalanceAssetItem.BCTC_CDKT_THAP)) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "taiKhoanDs[0].maTk", "NotEmpty.Bai.LoaiTaiKhoan.maTk");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "taiKhoanDs[0].maTkGoc",
					"NotEmpty.Bai.LoaiTaiKhoan.maTkGoc");
		} else if (bai.getType().equals(BalanceAssetItem.BCTC_CDKT_CAO)) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "assetCode", "NotEmpty.Bai.assetName");
		} else if (bai.getType().equals(BalanceAssetItem.BCTC_KQHDKD)) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "taiKhoanDs[0].maTk", "NotEmpty.Bai.LoaiTaiKhoan.maTk");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "taiKhoanDs[0].maTkGoc",
					"NotEmpty.Bai.LoaiTaiKhoan.maTkGoc");
		} else if (bai.getType().equals(BalanceAssetItem.BCTC_LCTT_THAP)) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "taiKhoanDs[0].maTk", "NotEmpty.Bai.LoaiTaiKhoan.maTk");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "taiKhoanDs[0].doiUng.maTk",
					"NotEmpty.Bai.LoaiTaiKhoan.doiUng.maTk");
		} else if (bai.getType().equals(BalanceAssetItem.BCTC_LCTT_CAO)) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "assetCode", "NotEmpty.Bai.assetName");
		}
	}
}
