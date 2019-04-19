package com.idi.task.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.idi.task.bean.Task;
import com.idi.task.dao.TaskDAO;

//import org.apache.commons.validator.routines.EmailValidator;

@Component
public class TaskValidator implements Validator {

	// common-validator library.
	//private EmailValidator emailValidator = EmailValidator.getInstance();

	// Các class được hỗ trợ Validate
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz == Task.class;
	}
	
	@Autowired
	private TaskDAO taskDAO;
	
	@Override
	public void validate(Object target, Errors errors){
		//System.err.println("Validator ...");
		Task task = (Task) target;
		try {		
			if(taskDAO.taskIsExits(task.getTaskName())) {
				//System.out.println("Duplicate taskName is existing...");
				errors.rejectValue("taskName", "Pattern.task.taskName");
			}	
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
