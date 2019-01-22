package com.shadow.books.util;

import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.shadow.books.domain.LineItem;

public class LineItemCart {


	private Long userId;
	
	@Size(min = 1, message = "hey! Let we go")
	private Set<LineItem> lineItems;

	@Override
	public String toString() {
		return "LineItemCart [userId=" + userId + ", lineItems=" + lineItems + "]";
	}

	public Long getUserId() {
		return userId;
	}

	public Set<LineItem> getLineItems() {
		return lineItems;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setLineItems(Set<LineItem> lineItems) {
		this.lineItems = lineItems;
	}
	
	
	

}
