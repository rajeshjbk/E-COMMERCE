package com.raj.ecommerce.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.raj.ecommerce.DTO.ProductDTO;
import com.raj.ecommerce.exception.ProductException;
import com.raj.ecommerce.model.Product;
import com.raj.ecommerce.repository.ProductRepository;
import com.raj.ecommerce.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository prodRepo;

	@Override
	public Product addProduct(Product productObj) throws ProductException {

		if(productObj==null) {

			throw new ProductException("Product Input is Empty");
		}
		return prodRepo.save(productObj);  //Insert the data into Product Table
	}

	@Override
	public Product updateProduct(Integer productId, ProductDTO productDTO) throws ProductException {

		Product dbProduct = prodRepo.findById(productId).orElseThrow(()-> new ProductException("Product Id Not Found"));
		dbProduct.setDescription(productDTO.getDescription());
		dbProduct.setImageUrl(productDTO.getImageUrl());
		dbProduct.setPrice(productDTO.getPrice());
		dbProduct.setCategory(productDTO.getCategory());
		dbProduct.setName(productDTO.getName());

		return prodRepo.save(dbProduct);
	}

	@Override
	public List<Product> getAllProducts() throws ProductException {

		List<Product> prdList = prodRepo.findAll();

		if(prdList.isEmpty()) {

			throw new ProductException("No Products available in Database");
		}
		return prdList;
	}

	@Override
	public List<Product> getAllProducts(String productName) throws ProductException {

		List<Product> prdList = prodRepo.findByName(productName);

		if(prdList.isEmpty()) {

			throw new ProductException("No Products available in Database");
		}
		return prdList;
	}

	@Override
	public List<Product> getAllProductsByCategory(String categoryName) throws ProductException {
		
		List<Product> prdList = prodRepo.findByName(categoryName);

		if(prdList.isEmpty()) {

			throw new ProductException("No Products available in Database");
		}
		return prdList;
	}

	@Override
	public List<Product> getAllProducts(
	        String keyword,
	        String sortDirection,
	        String sortBy)
	        throws ProductException {

	    Sort sort = Sort.by(
	            sortDirection.equalsIgnoreCase("asc")
	                    ? Sort.Direction.ASC
	                    : Sort.Direction.DESC,
	            sortBy
	    );

	    List<Product> products;

	    if (keyword != null && !keyword.isBlank()) {

	        products = prodRepo.findByNameContainingIgnoreCase(keyword, sort);
	    
	    } else {

	        products = prodRepo.findAll(sort);
	    }

	    if (products.isEmpty()) {
	        throw new ProductException(
	                "No Products available in Database"
	        );
	    }

	    return products;
	}
	@Override
	public Product getSingleProduct(Integer productId) throws ProductException {
		
		return prodRepo.findById(productId).orElseThrow(()-> new ProductException("Product Id Not Found"));
	}

	@Override
	public void removeProduct(Integer productId) throws ProductException {
		
		Product dbProd = prodRepo.findById(productId).orElseThrow(()-> new ProductException("Product Id Not Found"));
		
		prodRepo.delete(dbProd);
	}
}
