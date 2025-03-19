package com.LoanTrack.Backend.domain.dto;

import java.io.Serializable;

public abstract class ObjectResponse<T> implements Serializable {

	private static final long serialVersionUID = -5828161540875004270L;

	private String statusCode;
	private T data;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
