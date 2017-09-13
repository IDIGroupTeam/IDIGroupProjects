package com.idi.hr.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.idi.hr.bean.EmployeeInfo;
import com.idi.hr.dao.EmployeeDAO;

import org.apache.commons.validator.routines.EmailValidator;

@Component
public class EmployeeValidator implements Validator {

	// common-validator library.
	private EmailValidator emailValidator = EmailValidator.getInstance();

	// Các class được hỗ trợ Validate
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz == EmployeeInfo.class;
	}
	@Autowired
	private EmployeeDAO employeeDAO;
	
	@Override
	public void validate(Object target, Errors errors) {
		EmployeeInfo employeeFrom = (EmployeeInfo) target;

		// Kiểm tra các field của EmployeeInfo.
		// (Xem thêm file property: messages/validator.property)
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullName", "NotEmpty.employeeForm.fullName");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.employeeForm.email");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "NotEmpty.employeeForm.gender");

		if (!emailValidator.isValid(employeeFrom.getEmail())) {
			// Error in email field.
			// Lỗi trường email.
			errors.rejectValue("email", "Pattern.employeeForm.email");
		}
		//kiem tra duplicate account name
		
		if(employeeDAO.getAccount(employeeFrom.getLoginAccount()) > 0) {
			//System.out.println("dupicate login account is existing...");
			errors.rejectValue("loginAccount", "Pattern.employeeForm.loginAccount");
		}		
	}
}
