package com.idi.finance.dao;

import java.util.List;

import com.idi.finance.bean.TaiKhoan;

public interface TaiKhoanDAO {
	public void insertOrUpdateTaiKhoanDm(List<TaiKhoan> taiKhoanDM);

	public List<TaiKhoan> listTaiKhoanDm();
}
