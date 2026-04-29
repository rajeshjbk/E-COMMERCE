package com.raj.ecommerce.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.raj.ecommerce.model.Product;
import com.raj.ecommerce.model.Review;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
	
	public List<Product> findByName(String name);
	//As its derived Query, We no need to implement this.
	
	@Query("SELECT p FROM Product p WHERE p.category=:category")
	public List<Product> getProductsByCategory(String category);
	
	@Query("SELECT p FROM Product p WHERE p.name like '%keyword% order by sortBy desc' ")
	public List<Product> findAllByNameContainingIgnoreCase(String keyword, Sort sortOrder, String sortBy);
  //select * from product where name = keyword order by	name sortorder
   
	
}


