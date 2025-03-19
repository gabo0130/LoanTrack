package com.LoanTrack.Backend.domain.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -3596542658327575037L;
	
	public BusinessException(String message) {
		super(message);
	}

}
