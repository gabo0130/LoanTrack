package com.LoanTrack.Backend.infrastructure.config;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.LoanTrack.Backend.domain.exception.BaseException;
import com.LoanTrack.Backend.domain.util.ErrorDescription;
import com.LoanTrack.Backend.domain.util.FormatUtils;
import com.LoanTrack.Backend.infrastructure.util.MessageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.LoanTrack.Backend.domain.dto.DefaultErrorResponse;
import com.LoanTrack.Backend.domain.dto.ErrorDataBasic;

@Component("validationErrorUtils")
public class ValidationErrorUtils {

	private static final String DEFAULT_MESSAGE_KEY = "errors.service.default";

	private MessageUtil messageUtil;

	public DefaultErrorResponse getError(BaseException errors) {
		DefaultErrorResponse error = new DefaultErrorResponse();
		error.setMessage("Validation failed. " + errors.getErrors().getAllErrors().size() + " error(s)");

		if (errors.getMessageKey() != null && !errors.getMessageKey().equals(DEFAULT_MESSAGE_KEY)) {
			error.addError(new ErrorDataBasic(null,
					messageUtil.getMessage(errors.getMessageKey(), errors.getMessageKeyValues())));
		}
		if (errors.getErrors() != null) {
			List<ErrorDataBasic> errorBasic = errors.getErrors().getAllErrors().stream()
					.map(detail -> new ErrorDataBasic(null,
							messageUtil.getMessage(detail.getMessageKey(), convertParams(detail))))
					.collect(Collectors.toList());
			error.addAll(errorBasic);
		}
		return error;
	}
	
	public DefaultErrorResponse getErrorFromGenericException(Exception errors) {
		DefaultErrorResponse error = new DefaultErrorResponse();
		
		if (StringUtils.isNotBlank(errors.getMessage())) {
			error.addError(new ErrorDataBasic(null, errors.getMessage()));
		}
		return error;
	}

	private Object[] convertParams(ErrorDescription errorDescription) {
		Object[] parameters = null;
		if (errorDescription.getMessageKeyValues() != null) {
			parameters = new Object[errorDescription.getMessageKeyValues().length];
			for (int i = 0; i < errorDescription.getMessageKeyValues().length; i++) {
				Object oldParameter = errorDescription.getMessageKeyValues()[i];
				Object newParameter = oldParameter;

				if (oldParameter instanceof Long) {
					newParameter = FormatUtils.formatLongToString((Long) oldParameter);
				} else if (oldParameter instanceof Integer) {
					newParameter = FormatUtils.formatIntegerToString((Integer) oldParameter);
				} else if (oldParameter instanceof Date) {
					newParameter = FormatUtils.formatDateToString((Date) oldParameter);
				}
				parameters[i] = newParameter;
			}
		}
		return parameters;
	}

	public MessageUtil getMessageUtil() {
		return messageUtil;
	}

	@Autowired
	public void setMessageUtil(MessageUtil messageUtil) {
		this.messageUtil = messageUtil;
	}

}
