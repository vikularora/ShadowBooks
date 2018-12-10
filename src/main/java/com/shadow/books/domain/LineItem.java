package com.shadow.books.domain;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@SQLDelete(sql = "update LineItem set deleted = true where id=?")
@Where(clause = "deleted=false")
public class LineItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long userId;
	private Long productId;
	private Long quantity;
	private Float price;
//	private Float discount;
	private Float amount;
	private String status;
	private Long orderId;

	@Transient
	private String name;
	@Transient
	private String language;
	@Transient
	private Byte picture[];

	private boolean deleted;
	@Column(updatable = false)
	private long createdOn;
	private long modifiedOn;

	public Long getId() {
		return id;
	}

	public Long getUserId() {
		return userId;
	}

	public Long getProductId() {
		return productId;
	}

	public Long getQuantity() {
		return quantity;
	}

	public Float getPrice() {
		return price;
	}

	public Float getAmount() {
		return amount;
	}

	public String getStatus() {
		return status;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public long getCreatedOn() {
		return createdOn;
	}

	public long getModifiedOn() {
		return modifiedOn;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public void setCreatedOn(long createdOn) {
		this.createdOn = createdOn;
	}

	public void setModifiedOn(long modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getName() {
		return name;
	}

	public String getLanguage() {
		return language;
	}

	public Byte[] getPicture() {
		return picture;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setPicture(Byte[] picture) {
		this.picture = picture;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		return "LineItem [id=" + id + ", userId=" + userId + ", productId=" + productId + ", quantity=" + quantity
				+ ", price=" + price + ", amount=" + amount + ", status=" + status + ", orderId=" + orderId + ", name="
				+ name + ", language=" + language + ", picture=" + Arrays.toString(picture) + ", deleted=" + deleted
				+ ", createdOn=" + createdOn + ", modifiedOn=" + modifiedOn + "]";
	}

}
