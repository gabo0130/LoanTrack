package com.LoanTrack.Backend.infrastructure.config;

import java.util.Optional;

import com.LoanTrack.Backend.infrastructure.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.LoanTrack.Backend.domain.dto.DefaultErrorResponse;
import com.LoanTrack.Backend.domain.dto.ErrorDataBasic;
import com.LoanTrack.Backend.domain.dto.PathRoutesControllersEnum;

@Component("validationErrorBuilder")
public class ValidationErrorBuilder {
	
	@Autowired
	private MessageUtil messageUtil;

	public DefaultErrorResponse fromBindingErrors(Errors errors) {

		DefaultErrorResponse error = new DefaultErrorResponse();
		error.setMessage("Validation failed. " + errors.getErrorCount() + " error(s)");
		PathRoutesControllersEnum pathInfo = PathRoutesControllersEnum.getByObjectName(errors.getObjectName());
		Optional.ofNullable(pathInfo).ifPresent(path -> error.setPath(pathInfo.getRuta()));
		
		for (ObjectError objectError : errors.getAllErrors()) {
			if(FieldError.class.isAssignableFrom(objectError.getClass())) {
				FieldError fieldError = (FieldError) objectError;
				ErrorDataBasic errorField = new ErrorDataBasic();
				errorField.setField(fieldError.getField());
				errorField.setMessage(fieldError.getDefaultMessage());
				error.addError(errorField);
			}
			else {
				ErrorDataBasic errorField = new ErrorDataBasic();
				errorField.setMessage(messageUtil.getMessage(objectError.getDefaultMessage()));
				error.addError(errorField);
			}
			
		}

		return error;
	}

}
