package com.idi.hr.validator;

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
			
			if(leaveType.startsWith("DM") && leaveDAO.getRequestComeLateUsed(year, month + 1, employeeId) > lateQuataPerMonth) {
				System.err.println("Di muon qua so lan cho phep");
				errors.rejectValue("overLate", "Pattern.leaveInfo.overLate");
			}
			
			if(leaveInfo.getDate().compareTo(leaveInfo.getToDate()) >= 0) {
				System.err.println("from date >= to date");
				errors.rejectValue("toDateInvalid", "Pattern.leaveInfo.toDateInvalid");
			}
			
			//check dulicate
			if(leaveDAO.checkLeaveDuplicate(leaveInfo.getDate(), employeeId, leaveType) > 0) {
				System.err.println("thong tin da ton tai");
				errors.rejectValue("duplicate", "Pattern.leaveInfo.duplicate");
			}			

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
