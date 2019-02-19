package com.shadow.books.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@SQLDelete(sql = "update item set deleted = true where id=?")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
//	private byte picture[];
	private String imageUrl;
	private String description;
	private String language;
	private float price;
	private String status;
	private String category;
	@Transient
	private float discountedPrice;
	@Transient
	private MultipartFile file;
	private float discount;
	private int quantity;
	private boolean deleted;
	@Column(updatable = false)
	private long createdOn;
	private long modifiedOn;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getLanguage() {
		return language;
	}

	public String getCategory() {
		return category;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public void setCreatedOn(long createdOn) {
		this.createdOn = createdOn;
	}

	public void setModifiedOn(long modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public Integer getPrice() {
		return (int) price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Integer getDiscount() {
		return (int) discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public Integer getDiscountedPrice() {
		return (int) discountedPrice;
	}

	public void setDiscountedPrice(float discountedPrice) {
		this.discountedPrice = discountedPrice;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", imageUrl=" + imageUrl + ", description=" + description
				+ ", language=" + language + ", price=" + price + ", status=" + status + ", category=" + category
				+ ", discountedPrice=" + discountedPrice + ", file=" + file + ", discount=" + discount + ", quantity="
				+ quantity + ", deleted=" + deleted + ", createdOn=" + createdOn + ", modifiedOn=" + modifiedOn + "]";
	}

}
