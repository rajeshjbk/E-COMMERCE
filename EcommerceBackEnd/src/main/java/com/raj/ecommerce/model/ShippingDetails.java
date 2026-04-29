package com.raj.ecommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_shipping_details")
public class ShippingDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shipping_id")
	private Integer shippingId;
	
	@NotNull(message = "Address is Mandatory and it can't be Null")
	@Column(name = "address")
	private String address;
	
	@NotNull(message = "City is Mandatory and it can't be Null")
	@Column(name = "city")
	private String city;
	
	@NotNull(message = "Country is Mandatory and it can't be Null")
	@Column(name = "country")
	private String country;
	
	@NotNull(message = "PostalCode is Mandatory and it can't be Null")
	@Column(name = "postal_code")
	private String postalCode;
	
	@NotNull(message = "State is Mandatory and it can't be Null")
	@Column(name = "state")
	private String state;
	
	@ManyToOne
	@JoinColumn(name = "shipper_id")
	private Shipper shipper;
	
    @OneToOne
    private Orders order;

	public Integer getShippingId() {
		return shippingId;
	}

	public void setShippingId(Integer shippingId) {
		this.shippingId = shippingId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Shipper getShipper() {
		return shipper;
	}

	public void setShipper(Shipper shipper) {
		this.shipper = shipper;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	public ShippingDetails(@NotNull(message = "Address is Mandatory and it can't be Null") String address,
			@NotNull(message = "City is Mandatory and it can't be Null") String city,
			@NotNull(message = "Country is Mandatory and it can't be Null") String country,
			@NotNull(message = "PostalCode is Mandatory and it can't be Null") String postalCode,
			@NotNull(message = "State is Mandatory and it can't be Null") String state, Shipper shipper, Orders order) {
		super();
		this.address = address;
		this.city = city;
		this.country = country;
		this.postalCode = postalCode;
		this.state = state;
		this.shipper = shipper;
		this.order = order;
	}

	public ShippingDetails() {}

	@Override
	public String toString() {
		return "ShippingDetails [shippingId=" + shippingId + ", address=" + address + ", city=" + city + ", country="
				+ country + ", postalCode=" + postalCode + ", state=" + state + ", shipper=" + shipper + ", order="
				+ order + "]";
	}
}
