package com.shadow.books.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity

public class Suggestion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String message;
	private String name;
//	private String email; // optional
	private float rating;
	private String contactNumber;

	private boolean deleted;
	@Column(updatable = false)
	private long createdOn;
	private long modifiedOn;

	public Long getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "Suggestion [id=" + id + ", message=" + message + ", name=" + name + ", rating=" + rating
				+ ", contactNumber=" + contactNumber + ", deleted=" + deleted + ", createdOn=" + createdOn
				+ ", modifiedOn=" + modifiedOn + "]";
	}

}
