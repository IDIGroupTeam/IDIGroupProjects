/**
 * Copyright of IDI Group, 2020
 */
package com.idi.finance.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.idi.finance.bean.bieudo.KpiChart;

/**
 * @author HaiTD
 *
 *         Apr 26, 2020
 */
public class KpiChartValidator implements Validator {
	@Override
	public boolean supports(Class<?> cls) {
		return cls == KpiChart.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "chartTitle", "NotEmpty.KpiChart.chartTitle");
	}
}
