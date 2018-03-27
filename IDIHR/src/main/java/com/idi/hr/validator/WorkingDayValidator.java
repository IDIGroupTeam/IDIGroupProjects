package com.idi.hr.validator;

//import org.apache.commons.validator.routines.FloatValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.idi.hr.bean.WorkingDay;
import com.idi.hr.dao.WorkingDayDAO;

//import org.apache.commons.validator.routines.EmailValidator;

@Component
public class WorkingDayValidator implements Validator {

	// common-validator library.
	//private EmailValidator emailValidator = EmailValidator.getInstance();

	// Các class được hỗ trợ Validate
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz == WorkingDay.class;
	}
	@Autowired
	WorkingDayDAO workingDayDAO;
	
	//private static FloatValidator floatValidate;
	
	@Override
	public void validate(Object target, Errors errors) {
		WorkingDay workingDay = (WorkingDay) target;

		// Kiểm tra các field của workingDay.
		// (Xem thêm file property: messages/validator.property)
/*		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullName", "NotEmpty.employeeForm.fullName");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.employeeForm.email");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "NotEmpty.employeeForm.gender");*/

		//kiem tra duplicate
		if(workingDayDAO.getWorkingDay(workingDay.getMonth(), workingDay.getForCompany()).getWorkDayOfMonth() != null) {
			System.err.println(workingDayDAO.getWorkingDay(workingDay.getMonth(), workingDay.getForCompany()).getWorkDayOfMonth());
			//System.out.println("duplicate");
			errors.rejectValue("duplicate", "Pattern.workingDay.duplicate");
		}	
/*		
		try {
			if(!floatValidate.isValid(workingDay.getWorkDayOfMonth())) {
				errors.rejectValue("workDayOfMonth", "Pattern.workingDay.workDayOfMonth");
			}						
		}catch(Exception e) {
			e.printStackTrace();
		}*/
	}
}
