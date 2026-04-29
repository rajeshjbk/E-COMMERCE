package com.raj.ecommerce.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raj.ecommerce.exception.ProductException;
import com.raj.ecommerce.exception.ReviewException;
import com.raj.ecommerce.exception.UserException;
import com.raj.ecommerce.model.Product;
import com.raj.ecommerce.model.Review;
import com.raj.ecommerce.model.User;
import com.raj.ecommerce.repository.ProductRepository;
import com.raj.ecommerce.repository.ReviewRepository;
import com.raj.ecommerce.repository.UserRepository;
import com.raj.ecommerce.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private ReviewRepository reviewRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public Review addReviewToProduct(Integer productId, Integer userId, Review reviewObj) throws ReviewException {
		
		Product dbProduct = productRepo.findById(productId).
				orElseThrow(()-> new ProductException("Product Id Not Found"));
		
		User userObj = userRepo.findById(userId).
				orElseThrow(()-> new UserException("User Not Found"));
		
		userObj.getReviews().add(reviewObj);  //Adding Review to the User
		reviewObj.setUser(userObj);   //Adding User to the Review
		dbProduct.getReviews().add(reviewObj);  //Adding Review to the Product
		
		userRepo.save(userObj);  //User will be saved
		productRepo.save(dbProduct);  //Product Object will be saved
		
		return reviewRepo.save(reviewObj); //Review will be saved
	}

	@Override
	public Review updateReviewToProduct(Integer reviewId, Review reviewObj) throws ReviewException {
		
		Review existReview = reviewRepo.findById(reviewId).
				orElseThrow(()-> new ReviewException("Review Id Not Found"));
		
		//copy the data from inputobject to existReviewObject
		existReview.setRating(reviewObj.getRating());
		existReview.setComment(reviewObj.getComment());
		
		return reviewRepo.save(existReview); //Review will be saved
	}

	@Override
	public void deleteReview(Integer reviewId) throws ReviewException {
	
		Review existReview = reviewRepo.findById(reviewId).
				orElseThrow(()-> new ReviewException("Review Id Not Found"));
		
		reviewRepo.delete(existReview);
	}

	@Override
	public List<Review> getAllReviewsOfProduct(Integer productId) throws ReviewException {
		
		return reviewRepo.findAllReviewsByProductId(productId);
	}

}
