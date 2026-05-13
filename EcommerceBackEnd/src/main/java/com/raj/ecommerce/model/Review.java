package com.raj.ecommerce.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "user_review")
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_id")
	private Integer reviewId;
	
	@Column(name = "comment")
	@NotNull(message = "Please Provide Rating, Can't be Null")
	private String comment;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	@Column(name = "rating")
	private Integer rating;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	@JsonIgnore
	private Product product;

	public Integer getReviewId() {
		return reviewId;
	}

	public void setReviewId(Integer reviewId) {
		this.reviewId = reviewId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Review(@NotNull(message = "Please Provide Rating, Can't be Null") String comment, LocalDateTime createdAt,
			Integer rating, User user, Product product) {
		super();
		this.comment = comment;
		this.createdAt = createdAt;
		this.rating = rating;
		this.user = user;
		this.product = product;
	}
	
	public Review() {
		
	}

	@Override
	public String toString() {
		return "Review [reviewId=" + reviewId + ", comment=" + comment + ", createdAt=" + createdAt + ", rating="
				+ rating + ", user=" + user + ", product=" + product + "]";
	}
	
	
}
