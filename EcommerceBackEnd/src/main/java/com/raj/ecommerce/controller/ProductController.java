package com.raj.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raj.ecommerce.DTO.ProductDTO;
import com.raj.ecommerce.model.Product;
import com.raj.ecommerce.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/ecom/products")
@CrossOrigin("*")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@PostMapping(value = "add")
	public ResponseEntity<Product> addProduct(@Valid @RequestBody Product productObj){
	
		Product addedProduct = productService.addProduct(productObj);
		
		return new ResponseEntity<Product>(addedProduct, HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{productId}")
	public ResponseEntity<Product> updateProduct(@PathVariable Integer productId, @Valid @RequestBody ProductDTO productDTO){
		
		Product updatedProduct = productService.updateProduct(productId, productDTO);
		
		return ResponseEntity.ok(updatedProduct);
	}
	
	/*@GetMapping(value="/all")
	public ResponseEntity<List<Product>> getAllProducts(){
		
		List<Product> allProducts = productService.getAllProducts();
		return ResponseEntity.ok(allProducts);
	}
	*/	
	@GetMapping(value = "/name/{prodName}")
	public ResponseEntity<List<Product>> getAllProductsByName(@PathVariable String prodName){
		
		List<Product> allProducts = productService.getAllProducts(prodName);
		return ResponseEntity.ok(allProducts);
	}
	
	@GetMapping(value = "/category/{categoryName}")
	public ResponseEntity<List<Product>> getAllProductsByCategory(@PathVariable String categoryName){
		
		List<Product> allProducts = productService.getAllProducts(categoryName);
		return ResponseEntity.ok(allProducts);
	}
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<Product>> getAllProducts(
			@RequestParam String keyword,
			@RequestParam(required = false, defaultValue = "asc") String sortDirection,
			@RequestParam(required = false, defaultValue = "price") String sortBy){
		
		List<Product> allProducts = productService.getAllProducts(keyword, sortDirection, sortBy);
	
		return ResponseEntity.ok(allProducts);
	}
	
	@GetMapping(value = "/{productId}")
	public ResponseEntity<Product> getSingleProduct(@PathVariable Integer productId){
		
		Product product = productService.getSingleProduct(productId);
		
		return ResponseEntity.ok(product);
	}
	
	@DeleteMapping("/delete/{productId}")
	public ResponseEntity<String> removeProduct(@PathVariable Integer productId){
		
		productService.removeProduct(productId);
		
		return new ResponseEntity<String>("Product is Removed Successfully", HttpStatus.OK);
	}
}
