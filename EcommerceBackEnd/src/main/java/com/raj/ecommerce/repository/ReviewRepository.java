package com.raj.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.raj.ecommerce.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer>{

	@Query("SELECT r FROM Review r WHERE r.product.productId=:productId")
	public List<Review> findAllReviewsByProductId(Integer productId);
	
	
}
