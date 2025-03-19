package com.LoanTrack.Backend.domain.util;

public class ErrorDescription extends BaseMessage {
	
	private static final long serialVersionUID = 1L;

	public ErrorDescription(String messageKey) {
		super(messageKey);
	}

	public ErrorDescription(String messageKey, Object[] messageKeyValues) {
		super(messageKey, messageKeyValues);
	}

	public ErrorDescription(String field, String messageKey, Object[] messageKeyValues) {
		super(field, messageKey, messageKeyValues);
	}

}

