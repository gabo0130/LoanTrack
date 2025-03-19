package com.LoanTrack.Backend.infrastructure.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.LoanTrack.Backend.domain.dto.DefaultErrorResponse;
import com.LoanTrack.Backend.domain.exception.BaseException;
import com.LoanTrack.Backend.domain.exception.BusinessException;
import com.LoanTrack.Backend.domain.util.ErroresEnum;
import com.LoanTrack.Backend.infrastructure.util.MessageUtil;

@ControllerAdvice(basePackages = "com.LoanTrack.Backend.infrastructure.rest.controller")
public class RestExceptionHandler {

	private static Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

	@Autowired
	private MessageUtil messageUtil;

	@Autowired
	@Qualifier("validationErrorUtils")
	private ValidationErrorUtils validationErrorUtils;

	@Autowired
	@Qualifier("validationErrorBuilder")
	private ValidationErrorBuilder validationErrorBuilder;

	@ExceptionHandler(BindException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public DefaultErrorResponse handleException(BindException e) {
		LOGGER.error("Ocurrió un error de validacion", e);
		DefaultErrorResponse response = createValidationErrorBinding(e);
		populateHttpDataError(response, HttpStatus.BAD_REQUEST, ErroresEnum.ERROR_VALIDACION_REQUEST);
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		return response;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public DefaultErrorResponse handleException(MethodArgumentNotValidException e) {
		LOGGER.error("Ocurrió un error de validacion", e);
		DefaultErrorResponse response = createValidationErrorMethod(e);
		populateHttpDataError(response, HttpStatus.BAD_REQUEST, ErroresEnum.ERROR_VALIDACION_REQUEST);
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		return response;
	}

	private DefaultErrorResponse createValidationErrorBinding(BindException e) {
		return validationErrorBuilder.fromBindingErrors(e.getBindingResult());
	}

	private DefaultErrorResponse createValidationErrorMethod(MethodArgumentNotValidException e) {
		return validationErrorBuilder.fromBindingErrors(e.getBindingResult());
	}

	@ExceptionHandler(BaseException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public DefaultErrorResponse handleException(BaseException e) {
		LOGGER.error("Ocurrió un error de validacion", e);
		DefaultErrorResponse response = this.validationErrorUtils.getError(e);
		populateHttpDataError(response, HttpStatus.BAD_REQUEST, ErroresEnum.ERROR_VALIDACION_REQUEST);
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		return response;
	}

	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public DefaultErrorResponse handleException(BusinessException e) {
		LOGGER.error("Ocurrió un error de negocio", e);
		DefaultErrorResponse response = this.validationErrorUtils.getErrorFromGenericException(e);
		populateHttpDataError(response, HttpStatus.BAD_REQUEST, ErroresEnum.ERROR_VALIDACION_REQUEST);
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		return response;
	}

	@SuppressWarnings("rawtypes")
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ResponseEntity handleException(Exception e) {
		LOGGER.error("Ocurrió un error inesperado", e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	private void populateHttpDataError(DefaultErrorResponse response, HttpStatus status,
			ErroresEnum errorValidacionRequest) {
		response.setStatus(status.value());
		response.setError(messageUtil.getMessage(errorValidacionRequest.getMessageKey()));

	}

}
