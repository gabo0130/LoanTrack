package com.LoanTrack.Backend.domain.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Errors implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private List<ErrorDescription> errors = new ArrayList<ErrorDescription>();

	public Errors() {
	}

	public Errors(List<ErrorDescription> errors) {
		this.errors = errors;
	}

	public Errors addError(String messageKey) {
		return this.addError(new ErrorDescription(messageKey));
	}

	public Errors addError(String messageKey, Object[] messageKeyValues) {
		return this.addError(new ErrorDescription(messageKey, messageKeyValues));
	}

	public Errors addError(String field, String messageKey, Object... messageKeyValues) {
		return this.addError(new ErrorDescription(field, messageKey, messageKeyValues));
	}

	public Errors addError(ErrorDescription error) {
		if (error != null) {
			this.errors.add(error);
		}
		return this;
	}

	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	public List<ErrorDescription> getAllErrors() {
		return errors;
	}

}
