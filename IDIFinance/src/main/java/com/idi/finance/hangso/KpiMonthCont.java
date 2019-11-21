package com.idi.finance.hangso;

public enum KpiMonthCont {
	THANG_1("Tháng 1"), THANG_2("Tháng 2"), THANG_3("Tháng 3"), THANG_4("Tháng 4"), THANG_5("Tháng 5"), THANG_6(
			"Tháng 6"), THANG_7("Tháng 7"), THANG_8(
					"Tháng 8"), THANG_9("Tháng 9"), THANG_10("Tháng 10"), THANG_11("Tháng 11"), THANG_12("Tháng 12");

	private String value;

	private KpiMonthCont(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
