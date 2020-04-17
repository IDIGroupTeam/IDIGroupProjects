package com.idi.hr.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.idi.hr.bean.SalaryDetail;

public class SalaryDetailMapper  implements RowMapper<SalaryDetail> {

	public SalaryDetail mapRow(ResultSet rs, int nowNum) throws SQLException {
		int employeeId = rs.getInt("EMPLOYEE_ID");
		String basicSalary = rs.getString("BASIC_SALARY");
		String exchangeRate = rs.getString("EXCHANGE_RATE");
		String finalSalary = rs.getString("ACTUAL_SALARY");
		String overTimeN = rs.getString("OVER_TIME_N");
		String overTimeW = rs.getString("OVER_TIME_W");
		String overTimeH = rs.getString("OVER_TIME_H");
		String overTimeSalary = rs.getString("OVER_TIME_SALARY");
		String bounus = rs.getString("BOUNUS");
		String subsidize = rs.getString("SUBSIDIZE");
		String advancePayed = rs.getString("ADVANCE_PAYED");
		String taxPersonal = rs.getString("TAX_PERSONAL");
		int month = rs.getInt("MONTH");
		int year = rs.getInt("YEAR");;
		String desc = rs.getString("S.COMMENT");		
		String payedInsurance = rs.getString("PAYED_INSURANCE");
		String cPayedInsur = rs.getString("C_PAYED_INSUR");
		String fullName = rs.getString("FULL_NAME");
		String phoneNo = rs.getString("PHONE_NO");
		String bankNo = rs.getString("BANK_NO");
		String bankName = rs.getString("BANK_NAME");
		String bankBranch = rs.getString("BANK_BRANCH");
		String salary = rs.getString("SALARY");
		String department = rs.getString("DEPARTMENT");
		String jobTitle = rs.getString("JOB_TITLE");
		String salaryInsurance = rs.getString("SALA_SOCI_INSU");
		String percentCompanyPay = rs.getString("PERCENT_SOCI_INSU_C");
		String percentEmployeePay = rs.getString("PERCENT_SOCI_INSU_E");
		int workcomplete = rs.getInt("WORK_COMPLETE");
		String workedDay = rs.getString("WORKED_DAY");
		String other = rs.getString("OTHER");
		String arrears = rs.getString("ARREARS");
		String payStatus = rs.getString("PAY_STATUS");
		return new SalaryDetail(employeeId, basicSalary, exchangeRate, finalSalary, overTimeN, overTimeW, overTimeH, 
				overTimeSalary, bounus, subsidize, advancePayed, taxPersonal, month, year, desc,
				payedInsurance, cPayedInsur, fullName, phoneNo, bankNo, bankName, bankBranch, salary, department, 
				jobTitle, salaryInsurance, percentCompanyPay, percentEmployeePay, workcomplete, 
				workedDay, other, arrears, payStatus);
	}	
}
