package com.idi.hr.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.idi.hr.bean.EmployeeInfo;

public class EmployeeMapper implements RowMapper<EmployeeInfo> {

	@Override
	public EmployeeInfo mapRow(ResultSet rs, int nowNum) throws SQLException {

		String employeeId = rs.getString("EMPLOYEE_ID");
		String loginAccount = rs.getString("LOGIN_ACCOUNT");
		String fullName = rs.getString("FULL_NAME");
		String gender = rs.getString("GENDER");
		String maritalStatus = rs.getString("MARITAL_STATUS");
		String jobTitle = rs.getString("JOB_TITLE");
		String department = rs.getString("DEPARTMENT");
		Date DOB = rs.getDate("DOB");
		String personalId = rs.getString("PERSONAL_ID");
		Date issueDate = rs.getDate("ISSUE_DATE");
		String phoneNo = rs.getString("PHONE_NO");
		Date joinDate = rs.getDate("JOIN_DATE");
		Date officalJoinDate = rs.getDate("OFFICIAL_JOIN_DATE");
		String email = rs.getString("EMAIL");
		Date terminationDate = rs.getDate("TERMINATION_DATE");
		String reasonforLeave = rs.getString("REASON_FOR_LEAVE");
		String currentAdress = rs.getString("CURRENT_ADDRESS");
		String permanentAdress = rs.getString("PERMANENT_ADDRESS");
		String note = rs.getString("NOTE");
		
		String emerName = rs.getString("EMER_NAME");
		String emerPhoneNo = rs.getString("EMER_PHONE_NO");
		String nation = rs.getString("NATION");
		byte[] image = rs.getBytes("IMAGE");
		String workStatus = rs.getString("WORK_STATUS");
		String bankNo = rs.getString("BANK_NO");
		String bankName = rs.getString("BANK_NAME");
		String bankBranch = rs.getString("BANK_BRANCH");
		String salary = rs.getString("SALARY");
		String salarySocicalInsu = rs.getString("SALA_SOCI_INSU");
		String socicalInsuNo = rs.getString("SOCIAL_INSU_NO");
		String healthInsuNo = rs.getString("HEALTH_INSU_NO");
		String percentSocicalInsu = rs.getString("PERCENT_SOCI_INSU");

		return new EmployeeInfo(employeeId, loginAccount, gender, fullName, maritalStatus, jobTitle, department, DOB,
				personalId, issueDate, phoneNo, joinDate, officalJoinDate, email, terminationDate, reasonforLeave,
				currentAdress, permanentAdress, note, emerName, emerPhoneNo, nation, image, workStatus, bankNo,
				bankName, bankBranch, salary, salarySocicalInsu, socicalInsuNo, healthInsuNo, percentSocicalInsu);

	}
}
