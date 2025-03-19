package com.LoanTrack.Backend.domain.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PageRequest implements Serializable {

	public static final int PAGE_SIZE_DEFAULT = 50;
	private static final long serialVersionUID = 1L;
	
	private Integer size;
	private Integer page;
	private String order;
	private String direction;

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	public int getPageFormat() {
		return this.page == null ? 0 : this.page;
	}
	
	public int getSizeFormat() {
		return this.size == null ? PageRequest.PAGE_SIZE_DEFAULT : this.size;
		
	}
	
	public int getFirstResult() {
		return getPageFormat() * getSizeFormat();
	}
	
	@JsonIgnore
	public boolean isAsc(){
		return this.direction != null && this.direction.equals("asc");
	}

}
