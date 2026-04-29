package com.raj.ecommerce.service;

import java.util.List;

import com.raj.ecommerce.exception.ReviewException;
import com.raj.ecommerce.model.Review;

public interface ReviewService {

	public Review addReviewToProduct(Integer productId, Integer userId, Review reviewObj) throws ReviewException;
	
	public Review updateReviewToProduct(Integer reviewId, Review reviewObj) throws ReviewException;
	
	public void deleteReview(Integer reviewId) throws ReviewException;
	
	public List<Review> getAllReviewsOfProduct(Integer productId) throws ReviewException;
	
}
