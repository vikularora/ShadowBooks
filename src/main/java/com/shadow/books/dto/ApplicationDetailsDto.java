package com.shadow.books.dto;

public class ApplicationDetailsDto {

	private String link;
	private String body;

	public String getLink() {
		return link;
	}

	public String getBody() {
		return body;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "ApplicationDetailsDto [link=" + link + ", body=" + body + "]";
	}

}
