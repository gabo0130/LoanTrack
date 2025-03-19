package com.LoanTrack.Backend.domain.dto;

import org.springframework.http.HttpStatus;

public class ObjectResponseSuccess<T> extends ObjectResponse<T> {

	private static final long serialVersionUID = -6927859513321670051L;

	public ObjectResponseSuccess(T data) {
		super.setData(data);
		super.setStatusCode(HttpStatus.OK.getReasonPhrase());
	}

}
