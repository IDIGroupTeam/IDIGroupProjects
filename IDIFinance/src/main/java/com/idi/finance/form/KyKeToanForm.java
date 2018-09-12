package com.idi.finance.form;

import java.io.File;

import com.idi.finance.bean.kyketoan.KyKeToan;

public class KyKeToanForm {
	private KyKeToan kyKeToan;
	private File file;

	public KyKeToan getKyKeToan() {
		return kyKeToan;
	}

	public void setKyKeToan(KyKeToan kyKeToan) {
		this.kyKeToan = kyKeToan;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
}
