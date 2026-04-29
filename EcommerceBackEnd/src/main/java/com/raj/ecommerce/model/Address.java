package com.raj.ecommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "user_address")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "address_id")
	private Integer addressId;
	
	@Size(max=20)
	@NotNull(message = "Flat Number is Mandatory")
	@Column(name = "flat_no")
	private String flatNo;
	
	@NotNull(message = "Street is Mandatory")
	@Column(name = "street")
	private String street;
	
	@Size(max=20)
	@NotNull(message = "City is Mandatory")
	@Column(name = "city")
	private String city;
	
	@Size(max=20)
	@NotNull(message = "State is Mandatory")
	@Column(name = "state")
	private String state;
	
	@NotNull(message = "ZipCode is Mandatory")
	@Column(name = "zipCode")
	private String zipCode;
	
	@ManyToOne
	@JoinColumn(name = "userid")
	private User user;

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Address(@Size(max = 20) @NotNull(message = "Flat Number is Mandatory") String flatNo,
			@NotNull(message = "Street is Mandatory") String street,
			@Size(max = 20) @NotNull(message = "City is Mandatory") String city,
			@Size(max = 20) @NotNull(message = "State is Mandatory") String state,
			@NotNull(message = "ZipCode is Mandatory") String zipCode, User user) {
		super();
		this.flatNo = flatNo;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.user = user;
	}

	public Address() {}

	@Override
	public String toString() {
		return "Address [addressId=" + addressId + ", flatNo=" + flatNo + ", street=" + street + ", city=" + city
				+ ", state=" + state + ", zipCode=" + zipCode + ", user=" + user + "]";
	}
	
	
}
