package com.idi.hr.validator;

//import org.apache.commons.validator.routines.FloatValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.idi.hr.bean.LeaveInfo;
import com.idi.hr.common.PropertiesManager;
import com.idi.hr.dao.LeaveDAO;

//import org.apache.commons.validator.routines.EmailValidator;

@Component
public class LeaveValidator implements Validator {

	// common-validator library.
	//private EmailValidator emailValidator = EmailValidator.getInstance();

	// Các class được hỗ trợ Validate
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz == LeaveInfo.class;
	}
	@Autowired
	LeaveDAO leaveDAO;
	
	PropertiesManager hr = new PropertiesManager("hr.properties");
	
	@Override
	public void validate(Object target, Errors errors) {
		try {
			LeaveInfo leaveInfo = (LeaveInfo) target;
	
			//kiem tra xem da xin di muon qua so lan trong thang
			int year = Integer.parseInt(leaveInfo.getDate().toString().substring(0, 4));
			System.err.println("year " + year);
			int month = leaveInfo.getDate().getMonth();
			System.err.println("month " + month);
			int employeeId = leaveInfo.getEmployeeId();
			String leaveType = leaveInfo.getLeaveType();
			int lateQuataPerMonth = Integer.parseInt(hr.get("COME_LATE_PER_MONTH").toString());
			System.err.println("validate leave info");
			if(leaveDAO.getLeaveRequestUsed(leaveType, year, month + 1, employeeId) > lateQuataPerMonth) {
				System.err.println("over late");
				errors.rejectValue("overLate", "Pattern.leaveInfo.overLate");
			}
			if(leaveDAO.getLeaveReport(String.valueOf(year), String.valueOf(month + 1), employeeId, leaveType) > 0) {
				System.err.println("duplicate");
				errors.rejectValue("duplicate", "Pattern.leaveInfo.duplicate");
			}			

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
