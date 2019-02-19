package com.shadow.books.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OrderOnDemand implements Serializable {

	private static final long serialVersionUID = 4782064730197532482L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String bookName;
	private String language;
	private Integer quantity;
	private String contactNo;
	private String authorName;
	private String edition;
	private String name;
	private String contactNumber;

	private boolean deleted;
	@Column(updatable = false)
	private long createdOn;
	private long modifiedOn;

	public Long getId() {
		return id;
	}

	public String getBookName() {
		return bookName;
	}

	public String getLanguage() {
		return language;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public String getContactNo() {
		return contactNo;
	}

	public String getAuthorName() {
		return authorName;
	}

	public String getEdition() {
		return edition;
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

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public void setEdition(String edition) {
		this.edition = edition;
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

	public String getContactNumber() {
		return contactNumber;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	@Override
	public String toString() {
		return "OrderOnDemand [id=" + id + ", bookName=" + bookName + ", language=" + language + ", quantity="
				+ quantity + ", contactNo=" + contactNo + ", authorName=" + authorName + ", edition=" + edition
				+ ", name=" + name + ", contactNumber=" + contactNumber + ", deleted=" + deleted + ", createdOn="
				+ createdOn + ", modifiedOn=" + modifiedOn + "]";
	}

}
