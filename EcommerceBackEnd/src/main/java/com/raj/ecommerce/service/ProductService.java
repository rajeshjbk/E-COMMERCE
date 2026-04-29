package com.raj.ecommerce.service;

import java.util.List;

import com.raj.ecommerce.DTO.ProductDTO;
import com.raj.ecommerce.exception.ProductException;
import com.raj.ecommerce.model.Product;

public interface ProductService {

	public Product addProduct(Product productObj) throws ProductException;
	
	public Product updateProduct(Integer productId, ProductDTO productDTO) throws ProductException;
	
	public List<Product> getAllProducts() throws ProductException;
	
	public List<Product> getAllProducts(String productName) throws ProductException;

	public List<Product> getAllProductsByCategory(String categoryName) throws ProductException;
	
	public List<Product> getAllProducts(String keyword, String sortDirection, String sortBy) throws ProductException;
	
	public Product getSingleProduct(Integer productId) throws ProductException;
	
	public void removeProduct(Integer productId) throws ProductException;
	
}
