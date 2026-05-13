package com.raj.ecommerce.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_shipper")
public class Shipper {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shipper_id")
	private Integer shipperId;

	@NotNull(message = "Name is Mandatory and it can't be Null")
	@Column(name = "name")
	private String name;

	@NotNull(message = "PhoneNumber is Mandatory and it can't be Null")
	@Column(name = "phone_number")
	private String phoneNumber;

	@OneToMany(mappedBy = "shipper", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<ShippingDetails> shippingDetails = new ArrayList<>();

	public Integer getShipperId() {
		return shipperId;
	}

	public void setShipperId(Integer shipperId) {
		this.shipperId = shipperId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<ShippingDetails> getShippingDetails() {
		return shippingDetails;
	}

	public void setShippingDetails(List<ShippingDetails> shippingDetails) {
		this.shippingDetails = shippingDetails;
	}

	public Shipper(@NotNull(message = "Name is Mandatory and it can't be Null") String name,
			@NotNull(message = "PhoneNumber is Mandatory and it can't be Null") String phoneNumber,
			List<ShippingDetails> shippingDetails) {
		super();
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.shippingDetails = shippingDetails;
	}

	public Shipper() {

	}

	@Override
	public String toString() {
		return "Shipper [shipperId=" + shipperId + ", name=" + name + ", phoneNumber=" + phoneNumber
				+ ", shippingDetails=" + shippingDetails + "]";
	}
}
