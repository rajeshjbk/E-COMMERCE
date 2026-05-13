package com.raj.ecommerce.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tbl_products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Integer productId;

	@NotNull(message = "Product Name is Mandatory and can't be Null")
	@Column(name = "prod_name")
	private String name;

	@NotNull(message = "Product Description is Mandatory and can't be Null")
	@Column(name = "prod_description")
	@Size(min = 10, max = 50)
	private String description;

	@NotNull(message = "Image URL is Mandatory and can't be Null")
	@Column(name = "img_url")
	private String imageUrl;

	@NotNull(message = "Product Price is Mandatory and can't be Null")
	@Column(name = "prod_price")
	private Double price;

	@NotNull(message = "Product Category is Mandatory and can't be Null")
	@Column(name = "category_name")
	private String category;

	@Column(name = "is_available ")
	private Boolean isAvailable = true;

	// One To Many With OrderItem
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<OrderItem> orderItem = new ArrayList<>();

	// One To Many Reviews
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("product")
	private List<Review> reviews = new ArrayList<>();

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public List<OrderItem> getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(List<OrderItem> orderItem) {
		this.orderItem = orderItem;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public Product(@NotNull(message = "Product Name is Mandatory and can't be Null") String name,
			@NotNull(message = "Product Description is Mandatory and can't be Null") @Size(min = 10, max = 50) String description,
			@NotNull(message = "Image URL is Mandatory and can't be Null") String imageUrl,
			@NotNull(message = "Product Price is Mandatory and can't be Null") Double price,
			@NotNull(message = "Product Category is Mandatory and can't be Null") String category, Boolean isAvailable,
			List<OrderItem> orderItem, List<Review> reviews) {
		super();
		this.name = name;
		this.description = description;
		this.imageUrl = imageUrl;
		this.price = price;
		this.category = category;
		this.isAvailable = isAvailable;
		this.orderItem = orderItem;
		this.reviews = reviews;
	}

	public Product() {

	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", name=" + name + ", description=" + description + ", imageUrl="
				+ imageUrl + ", price=" + price + ", category=" + category + ", isAvailable=" + isAvailable + "]";
	}

}
