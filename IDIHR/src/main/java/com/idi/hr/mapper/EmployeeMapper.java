package com.idi.hr.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.idi.hr.bean.EmployeeInfo;

public class EmployeeMapper implements RowMapper<EmployeeInfo> {

	@Override
	public EmployeeInfo mapRow(ResultSet rs, int nowNum) throws SQLException {

		int employeeId = rs.getInt("EMPLOYEE_ID");
		String loginAccount = rs.getString("LOGIN_ACCOUNT");
		String fullName = rs.getString("FULL_NAME");
		String gender = rs.getString("GENDER");
		String maritalStatus = rs.getString("MARITAL_STATUS");
		String jobTitle = rs.getString("JOB_TITLE");
		String department = rs.getString("DEPARTMENT");
		String DOB = rs.getString("DOB");
		String personalId = rs.getString("PERSONAL_ID");
		String issueDate = rs.getString("ISSUE_DATE");
		String issuePlace = rs.getString("ISSUE_PLACE");				
		String phoneNo = rs.getString("PHONE_NO");
		String joinDate = rs.getString("JOIN_DATE");
		String officalJoinDate = rs.getString("OFFICIAL_JOIN_DATE");
		String email = rs.getString("EMAIL");
		String terminationDate = rs.getString("TERMINATION_DATE");
		String reasonforLeave = rs.getString("REASON_FOR_LEAVE");
		String currentAdress = rs.getString("CURRENT_ADDRESS");
		String permanentAdress = rs.getString("PERMANENT_ADDRESS");
		String note = rs.getString("NOTE");

		String emerName = rs.getString("EMER_NAME");
		String emerPhoneNo = rs.getString("EMER_PHONE_NO");
		String nation = rs.getString("NATION");

		String workStatus = rs.getString("WORK_STATUS");
		String statusName = rs.getString("STATUS_NAME");
		String bankNo = rs.getString("BANK_NO");
		String bankName = rs.getString("BANK_NAME");
		String bankBranch = rs.getString("BANK_BRANCH");
		String imagePath = rs.getString("IMAGE_PATH");
		String salarySocicalInsu = rs.getString("SALA_SOCI_INSU");
		String socicalInsuNo = rs.getString("SOCIAL_INSU_NO");
		String healthInsuNo = rs.getString("HEALTH_INSU_NO");
		String percentSocicalInsu = rs.getString("PERCENT_SOCI_INSU");

		return new EmployeeInfo(employeeId, loginAccount, gender, fullName, maritalStatus, jobTitle, department, DOB,
				personalId, issueDate, issuePlace, phoneNo, joinDate, officalJoinDate, email, terminationDate, reasonforLeave,
				currentAdress, permanentAdress, note, emerName, emerPhoneNo, nation, workStatus, statusName, bankNo, bankName,
				bankBranch, imagePath, salarySocicalInsu, socicalInsuNo, healthInsuNo, percentSocicalInsu);

	}
}
