package com.idi.finance.bean.bctc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;

public class BalanceAssetItem {
	private static final Logger logger = Logger.getLogger(BalanceAssetItem.class);

	public static final String CDKT_CT_110 = "110";
	public static final String LCTT_CT_60 = "60";

	private String assetCode;
	private String assetName;
	private String assetCodeName;
	private String rule;
	private String note;
	private int soDu = -1;

	private BalanceAssetItem parent;
	private List<BalanceAssetItem> childs;
	private List<LoaiTaiKhoan> taiKhoanDs;

	private int row;

	public String getAssetCode() {
		return assetCode;
	}

	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public String getAssetCodeName() {
		return assetCodeName;
	}

	public void setAssetCodeName(String assetCodeName) {
		this.assetCodeName = assetCodeName;
	}

	public BalanceAssetItem getParent() {
		return parent;
	}

	public void setParent(BalanceAssetItem parent) {
		this.parent = parent;
	}

	public List<BalanceAssetItem> getChilds() {
		return childs;
	}

	public void setChilds(List<BalanceAssetItem> childs) {
		this.childs = childs;
	}

	public void addChild(BalanceAssetItem child) {
		if (child != null) {
			if (childs == null) {
				childs = new ArrayList<>();
			}

			if (!childs.contains(child)) {
				childs.add(child);
			}
		}
	}

	public void addChild(List<BalanceAssetItem> childs) {
		if (childs != null) {
			if (this.childs == null) {
				this.childs = new ArrayList<>();
			}

			Iterator<BalanceAssetItem> iter = childs.iterator();
			while (iter.hasNext()) {
				BalanceAssetItem bai = iter.next();
				addChild(bai);
			}
		}
	}

	public List<LoaiTaiKhoan> getTaiKhoanDs() {
		return taiKhoanDs;
	}

	public void setTaiKhoanDs(List<LoaiTaiKhoan> taiKhoanDs) {
		this.taiKhoanDs = taiKhoanDs;
	}

	public void themTaiKhoan(LoaiTaiKhoan taiKhoan) {
		if (taiKhoan == null)
			return;

		if (taiKhoanDs == null)
			taiKhoanDs = new ArrayList<>();

		if (!taiKhoanDs.contains(taiKhoan)) {
			taiKhoanDs.add(taiKhoan);
		}
	}

	public void themTaiKhoan(List<LoaiTaiKhoan> taiKhoanDs) {
		if (taiKhoanDs == null)
			return;

		if (this.taiKhoanDs == null)
			this.taiKhoanDs = new ArrayList<>();

		Iterator<LoaiTaiKhoan> iter = taiKhoanDs.iterator();
		while (iter.hasNext()) {
			LoaiTaiKhoan taiKhoan = iter.next();
			themTaiKhoan(taiKhoan);
		}
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getSoDu() {
		return soDu;
	}

	public void setSoDu(int soDu) {
		this.soDu = soDu;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	@Override
	public String toString() {
		String out = assetCode + " " + assetName;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof BalanceAssetItem)) {
			return false;
		}

		BalanceAssetItem item = (BalanceAssetItem) obj;
		try {
			if (assetCode == null) {
				if (item.getAssetCode() != null)
					return false;
			} else if (item.getAssetCode() == null) {
				return false;
			} else if (!assetCode.trim().equalsIgnoreCase(item.getAssetCode().trim())) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
