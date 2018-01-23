package com.idi.finance.bean;

public class BalanceAssetItem {
	private String assetCode;
	private String assetName;
	private String rule;
	private String note;

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

	@Override
	public String toString() {
		String out = assetCode + "  " + assetName + " " + rule + " " + note;
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

			if (assetName == null) {
				if (item.getAssetName() != null)
					return false;
			} else if (item.getAssetName() == null) {
				return false;
			} else if (!assetName.trim().equalsIgnoreCase(item.getAssetName().trim())) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
