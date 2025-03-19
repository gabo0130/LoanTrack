package com.LoanTrack.Backend.domain.util;

public enum ErroresEnum {

	ERROR_VALIDACION_REQUEST("request.validation.fields.error");

	private String messageKey;

	private ErroresEnum(String messageKey) {
		this.messageKey = messageKey;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public static String getMessageKey(ErroresEnum error) {
		return error.getMessageKey();
	}

}
