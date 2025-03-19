package com.LoanTrack.Backend.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "isSuccess" })
public class MessageData implements ResponsableObject {

	private static final long serialVersionUID = 4577150179164191005L;

	private String message;
	
	private boolean isSuccess = false;

	public MessageData() {

	}

	public MessageData(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

}
