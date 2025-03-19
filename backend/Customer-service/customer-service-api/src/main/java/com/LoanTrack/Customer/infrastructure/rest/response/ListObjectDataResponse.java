package com.LoanTrack.Backend.infrastructure.rest.response;

import com.LoanTrack.Backend.domain.dto.ObjectResponseSuccess;

public class ListObjectDataResponse extends ObjectResponseSuccess<ListObjectData> {

	private static final long serialVersionUID = 2935215810887185658L;

	public ListObjectDataResponse(ListObjectData data) {
		super(data);
	}
	
}
