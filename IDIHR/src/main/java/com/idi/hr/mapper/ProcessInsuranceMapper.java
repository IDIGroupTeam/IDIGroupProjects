package com.idi.hr.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.idi.hr.bean.ProcessInsurance;

public class ProcessInsuranceMapper implements RowMapper<ProcessInsurance> {

	public ProcessInsurance mapRow(ResultSet rs, int nowNum) throws SQLException {

		String socicalInsuNo = rs.getString("SOCIAL_INSU_NO");
		String salarySocicalInsu = rs.getString("SALA_SOCI_INSU");
		String companyPay = rs.getString("COMPANY_PAY");
		String fromDate = rs.getString("FROM_DATE");
		String toDate = rs.getString("TO_DATE");
		String comment = rs.getString("COMMENT");

		return new ProcessInsurance(socicalInsuNo, salarySocicalInsu, companyPay, fromDate, toDate, comment);
	}

}
