package com.idi.hr.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.idi.hr.bean.Insurance;

public class InsuranceMapper implements RowMapper<Insurance> {

	public Insurance mapRow(ResultSet rs, int nowNum) throws SQLException {

		int employeeId = rs.getInt("EMPLOYEE_ID");
		String employeeName= rs.getString("FULL_NAME");
		String socicalInsuNo = rs.getString("SOCIAL_INSU_NO");
		String salarySocicalInsu = rs.getString("SALA_SOCI_INSU");
		String percentSInsuC = rs.getString("PERCENT_SOCI_INSU_C");
		String percentSInsuE = rs.getString("PERCENT_SOCI_INSU_E");
		String payType = rs.getString("PAY_TYPE");
		String salaryZone = rs.getString("SALARY_ZONE");
		String place = rs.getString("PLACE");

		String status = rs.getString("STATUS");
		String hInsuNo = rs.getString("HEALTH_INSU_NO");
		String hInsuPlace = rs.getString("HEALTH_INSU_PLACE");
		String comment = rs.getString("COMMENT");

		return new Insurance(employeeId, employeeName, socicalInsuNo, salarySocicalInsu, percentSInsuC, 
				percentSInsuE, payType,	salaryZone, place, status, hInsuNo, hInsuPlace, comment);
	}

}
