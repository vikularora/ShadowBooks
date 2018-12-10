package com.shadow.books.util;

import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.shadow.books.domain.LineItem;

public class ShoppingCart {

	@NotNull(message = "identify Yourself")
	private Long userId;
	@NotNull
	@Size(min = 1, message = "hey! Let we go")
	private Set<LineItem> lineItems;

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

	@Override
	public String toString() {
		return "ShoppingCart [userId=" + userId + ", lineItems=" + lineItems + "]";
	}

}
