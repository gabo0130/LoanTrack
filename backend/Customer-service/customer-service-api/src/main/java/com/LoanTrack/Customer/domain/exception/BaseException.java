package com.LoanTrack.Backend.domain.exception;

import com.LoanTrack.Backend.domain.util.ErrorDescription;
import com.LoanTrack.Backend.domain.util.Errors;

public abstract class BaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String messageKey = "errors.service.default";

	private Object[] messageKeyValues;

	private Errors errors;

	protected BaseException() {
		super();
	}

	protected BaseException(Throwable cause) {
		super(cause);
	}

	protected BaseException(String messageKey) {
		super();
		this.messageKey = messageKey;
	}

	protected BaseException(Throwable cause, String messageKey, Object... messageKeyValues) {
		super(cause);
		this.messageKey = messageKey;
		this.messageKeyValues = messageKeyValues;
	}

	protected BaseException(String messageKey, Object... messageKeyValues) {
		super();
		this.messageKey = messageKey;
		this.messageKeyValues = messageKeyValues;
	}

	protected <T extends ErrorDescription> BaseException(Throwable cause, T error) {
		super(cause);
		this.messageKey = error.getMessageKey();
		this.messageKeyValues = error.getMessageKeyValues();
	}

	protected BaseException(Throwable cause, Errors errors) {
		super(cause);
		this.errors = errors;
	}

	protected BaseException(Errors errors) {
		super();
		this.errors = errors;
	}

	public Object[] getMessageKeyValues() {
		return messageKeyValues;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public Errors getErrors() {
		return errors;
	}

	public <T extends ErrorDescription> void addError(T error) {
		if (error != null) {
			this.errors.addError(error);
		}
	}
}
