package com.shadow.books.dto;

public class SizeDto {

	private Integer size;
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "SizeDto [size=" + size + ", status=" + status + "]";
	}

}
