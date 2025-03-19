package com.LoanTrack.Backend.domain.dto;

import java.util.ArrayList;
import java.util.List;

public class DefaultErrorResponse implements ResponsableObject {

	private static final long serialVersionUID = 3922543039941808845L;

	private Integer status;
	private String error;
	private List<ErrorDataBasic> errors = new ArrayList<ErrorDataBasic>();
	private String message;
	private String path;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public List<ErrorDataBasic> getErrors() {
		return errors;
	}

	public void setErrors(List<ErrorDataBasic> errors) {
		this.errors = errors;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void addError(ErrorDataBasic error) {
		this.errors.add(error);
	}

	public boolean addAll(List<ErrorDataBasic> errors) {
		return this.errors.addAll(errors);
	}

}
