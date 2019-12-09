package com.idi.finance.hangso;

public enum KpiMonthCont {
	THANG_1(1, "Tháng 1"), THANG_2(2, "Tháng 2"), THANG_3(3, "Tháng 3"), THANG_4(4, "Tháng 4"), THANG_5(5,
			"Tháng 5"), THANG_6(6, "Tháng 6"), THANG_7(7, "Tháng 7"), THANG_8(8, "Tháng 8"), THANG_9(9,
					"Tháng 9"), THANG_10(10, "Tháng 10"), THANG_11(11, "Tháng 11"), THANG_12(12, "Tháng 12");

	private int key;
	private String value;

	private KpiMonthCont(int key, String value) {
		this.key = key;
		this.value = value;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
