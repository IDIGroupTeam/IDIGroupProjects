package com.idi.finance.hangso;

public enum KpiChartType {
	BAR(1, "Cột"), LINE(2, "Đường thẳng");

	private int value;
	private String name;

	private KpiChartType(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
}
