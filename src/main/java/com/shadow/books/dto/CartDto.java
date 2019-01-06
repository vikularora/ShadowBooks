package com.shadow.books.dto;

import java.util.ArrayList;
import java.util.List;

import com.shadow.books.domain.Address;
import com.shadow.books.domain.LineItem;

public class CartDto {

	private String type;
	private Long userId;
	private Long productId;
	private Integer quantity;
	private List<LineItem> cartDetails = new ArrayList<LineItem>();;
	private List<Address> address;

	public String getType() {
		return type;
	}

	public Long getUserId() {
		return userId;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<LineItem> getCartDetails() {
		return cartDetails;
	}

	public void setCartDetails(List<LineItem> cartDetails) {
		this.cartDetails = cartDetails;
	}

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "CartDto [type=" + type + ", userId=" + userId + ", productId=" + productId + ", quantity=" + quantity
				+ ", cartDetails=" + cartDetails + ", address=" + address + "]";
	}

}
