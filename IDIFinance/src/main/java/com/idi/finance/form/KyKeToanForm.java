package com.idi.finance.form;

import org.springframework.web.multipart.MultipartFile;

import com.idi.finance.bean.kyketoan.KyKeToan;

public class KyKeToanForm {
	private KyKeToan kyKeToan;
	private MultipartFile soDuKyFile;

	public KyKeToan getKyKeToan() {
		return kyKeToan;
	}

	public void setKyKeToan(KyKeToan kyKeToan) {
		this.kyKeToan = kyKeToan;
	}

	public MultipartFile getSoDuKyFile() {
		return soDuKyFile;
	}

	public void setSoDuKyFile(MultipartFile soDuKyFile) {
		this.soDuKyFile = soDuKyFile;
	}
}
