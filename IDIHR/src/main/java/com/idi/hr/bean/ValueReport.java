package com.idi.hr.bean;

import java.io.Serializable;

public class ValueReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3825883033166333676L;
	
	private int count;
	private float value;
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public float getValue() {
		return value;
	}
	
	public void setValue(float value) {
		this.value = value;
	}
}
