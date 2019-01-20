package com.shadow.books.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@SQLDelete(sql = "update address set deleted = true where id=?")
@Where(clause = "deleted=false")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String houseNumber;
	private String street;
	private String area;
	private String landmark;
	private String contactNo;
	private Long userId;
	private boolean isSelected;

	private boolean deleted;
	@Column(updatable = false)
	private long createdOn;
	private long modifiedOn;

	public Long getId() {
		return id;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public String getArea() {
		return area;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", name=" + name + ", houseNumber=" + houseNumber + ", street=" + street
				+ ", area=" + area + ", landmark=" + landmark + ", contactNo=" + contactNo + ", userId=" + userId
				+ ", isSelected=" + isSelected + ", deleted=" + deleted + ", createdOn=" + createdOn + ", modifiedOn="
				+ modifiedOn + "]";
	}

}
