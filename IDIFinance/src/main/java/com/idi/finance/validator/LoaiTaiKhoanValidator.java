package com.idi.finance.validator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;
import com.idi.finance.dao.TaiKhoanDAO;

public class LoaiTaiKhoanValidator implements Validator {
	private static final Logger logger = Logger.getLogger(LoaiTaiKhoanValidator.class);

	@Autowired
	TaiKhoanDAO taiKhoanDAO;

	@Override
	public boolean supports(Class<?> cls) {
		return cls == LoaiTaiKhoan.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		LoaiTaiKhoan loaiTaiKhoan = (LoaiTaiKhoan) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "maTk", "NotEmpty.LoaiTaiKhoan.maTk");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tenTk", "NotEmpty.LoaiTaiKhoan.tenTk");

		if (taiKhoanDAO.layTaiKhoan(loaiTaiKhoan.getMaTk()) != null && loaiTaiKhoan.isNew()) {
			errors.rejectValue("maTk", "LoaiTaiKhoan.tontai");
		} else if (loaiTaiKhoan.getMaTk() != null && !loaiTaiKhoan.getMaTk().trim().equals("")) {
			if (loaiTaiKhoan.getMaTkCha() != null && !loaiTaiKhoan.getMaTkCha().trim().equals("")) {
				String maTk = loaiTaiKhoan.getMaTk().trim();
				String maTkCha = loaiTaiKhoan.getMaTkCha().trim();
				if (maTk.trim().length() <= maTkCha.trim().length()) {
					errors.rejectValue("maTk", "LoaiTaiKhoan.QuaNganCha");
				} else if (!maTk.substring(0, maTkCha.length()).equals(maTkCha)) {
					errors.rejectValue("maTk", "LoaiTaiKhoan.DungChuan");
				}
			} else {
				if (loaiTaiKhoan.getMaTk().trim().length() < 3) {
					errors.rejectValue("maTk", "LoaiTaiKhoan.QuaNgan");
				}
			}
		}
	}
}
