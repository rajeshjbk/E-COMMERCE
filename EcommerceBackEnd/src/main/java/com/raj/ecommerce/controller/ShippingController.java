package com.raj.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raj.ecommerce.DTO.ShippingDTO;
import com.raj.ecommerce.model.ShippingDetails;
import com.raj.ecommerce.service.ShippingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/ecom/order-shipping")
@CrossOrigin("*")
public class ShippingController {
	
	@Autowired
	private ShippingService shippingService;
	
	@PostMapping("/{orderId}/{shipperId}")
	public ResponseEntity<ShippingDetails> setShippingDetails(@PathVariable Integer orderId,
			@PathVariable Integer shipperId,
			@Valid @RequestBody ShippingDetails shipObj){
		
		ShippingDetails shippingObj=shippingService.setShippingDetails(orderId, shipperId, shipObj);
		return ResponseEntity.ok(shippingObj);
	}
	
	
	@PutMapping("/{shippingId}")
	public ResponseEntity<ShippingDetails> setShippingDetails(@PathVariable Integer shippingId,
		@Valid @RequestBody ShippingDTO shipObj){
		
		ShippingDetails shippingObj=shippingService.updateShippingDetails(shippingId, shipObj);
		return ResponseEntity.ok(shippingObj);
	}
	
	@DeleteMapping("/{shippingId}")
	public ResponseEntity<Void> deleteShippingDetails(@PathVariable Integer shippingId) {
		
		shippingService.deleteShippingDetails(shippingId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
