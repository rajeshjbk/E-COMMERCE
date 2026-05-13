package com.raj.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raj.ecommerce.model.Cart;
import com.raj.ecommerce.service.CartService;

@RestController
@RequestMapping(value="/ecom/cart")
@CrossOrigin("*")
public class CartController {

	@Autowired
	private CartService cartService;

	@PostMapping(value = "/add-product")
	public ResponseEntity<Cart> addProductToCart(@RequestParam Integer userId, @RequestParam Integer productId){
   
		Cart productToCart = cartService.addProductToCart(userId, productId);

		return new ResponseEntity<Cart>(productToCart, HttpStatus.CREATED);
	}

	@PutMapping(value = "/increase-productQty/{userId}/{productId}")
	public ResponseEntity<Cart> increaseProductQuantity(@PathVariable Integer userId, @PathVariable Integer productId){

		Cart cart = cartService.increaseProductQuantity(userId, productId);

		return ResponseEntity.ok(cart);
	}
	
	@PutMapping(value = "/decrease-productQty/{userId}/{productId}")
	public ResponseEntity<Cart> decreaseProductQuantity(@PathVariable Integer userId, @PathVariable Integer productId){

		Cart cart = cartService.decreaseProductQuantity(userId, productId);

		return ResponseEntity.ok(cart);
	}
	
	@DeleteMapping(value = "/remove-product/{cartId}/{productId}")
	public ResponseEntity<String> removeProductFromCar(@PathVariable Integer cartId, @PathVariable Integer productId){

		cartService.removeProductFromCart(cartId, productId);
		
        String msg = "Product is Removed From Cart";
        
		return ResponseEntity.ok(msg);
	}
	
	@DeleteMapping(value = "/remove-all-product/{cartId}")
	public ResponseEntity<String> removeAllProductsFromCart(@PathVariable Integer cartId){

		cartService.removeAllProductsFromCart(cartId);
		
        String msg = "Cart is Empty";
        
		return ResponseEntity.ok(msg);
	}

	@GetMapping(value = "/products/{cartId}")
	public ResponseEntity<Cart> getAllCartProduct(@PathVariable Integer cartId){
		
		Cart products = cartService.getAllCartProducts(cartId);
		
		return new ResponseEntity<Cart>(products,HttpStatus.OK);
	}
	
}
