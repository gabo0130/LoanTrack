package com.LoanTrack.Backend.domain.dto;

public class ErrorDataBasic {

	private String field;
	private String message;

	public ErrorDataBasic() {
		super();
	}

	public ErrorDataBasic(String field, String message) {
		super();
		this.field = field;
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
