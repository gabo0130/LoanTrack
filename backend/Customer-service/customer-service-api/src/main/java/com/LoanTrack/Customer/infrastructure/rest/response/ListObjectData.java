package com.LoanTrack.Backend.infrastructure.rest.response;

import java.util.List;

public class ListObjectData {

	private List<? extends Object> data;

	public ListObjectData() {

	}

	public ListObjectData(List<? extends Object> data) {
		this.data = data;
	}

	public List<? extends Object> getData() {
		return data;
	}

	public void setData(List<? extends Object> data) {
		this.data = data;
	}

}
