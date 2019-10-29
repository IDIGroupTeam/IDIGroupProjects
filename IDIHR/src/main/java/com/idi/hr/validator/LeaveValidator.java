package com.idi.hr.validator;

import java.util.Date;
import java.text.SimpleDateFormat;

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
	
			SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy"); 
			Date dateTemp = dt.parse(leaveInfo.getfDate()); 
			SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
			String fromDate = dt1.format(dateTemp);
			SimpleDateFormat dt2 = new SimpleDateFormat("yyyy-MM-dd"); 
			Date d = dt2.parse(fromDate);
			leaveInfo.setDate(d);
			
			//kiem tra xem da xin di muon qua so lan trong thang
			int year = Integer.parseInt(leaveInfo.getfDate().substring(6, 10));
			int month = Integer.parseInt(leaveInfo.getfDate().substring(3, 5));
			int employeeId = leaveInfo.getEmployeeId();
			String leaveType = leaveInfo.getLeaveType();
			int lateQuataPerMonth = Integer.parseInt(hr.get("COME_LATE_PER_MONTH").toString());
			
			if(leaveType.startsWith("DM") && leaveDAO.getRequestComeLateUsed(year, month, employeeId) > lateQuataPerMonth) {
				//System.err.println("Di muon qua so lan cho phep");
				errors.rejectValue("overLate", "Pattern.leaveInfo.overLate");
			}
			
			if(leaveInfo.getToDate() != null && leaveInfo.getToDate().length() > 0) {
				//System.err.println("nhap cho nhieu ngay");
				Date toDate = dt.parse(leaveInfo.getToDate());
				if(leaveInfo.getDate().compareTo(toDate) >= 0) {
					//System.err.println("from date >= to date");
					errors.rejectValue("toDateInvalid", "Pattern.leaveInfo.toDateInvalid");
				}
			}
			
			//check dulicate
			//System.err.println("date in validate: " + leaveInfo.getDate());
			if(leaveDAO.checkLeaveDuplicate(leaveInfo.getDate(), employeeId, leaveType) > 0) {
				//System.err.println("thong tin da ton tai");
				errors.rejectValue("duplicate", "Pattern.leaveInfo.duplicate");
			}			

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
