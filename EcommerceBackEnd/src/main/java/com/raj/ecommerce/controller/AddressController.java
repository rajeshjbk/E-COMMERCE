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
import org.springframework.web.bind.annotation.RestController;

import com.raj.ecommerce.model.Address;
import com.raj.ecommerce.service.AddressService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value="/ecom/customer-address")
@CrossOrigin("*")
public class AddressController {

	@Autowired
	private AddressService addressService;
	
	@PostMapping(value = "/{userId}")
	public ResponseEntity<Address> addAddressToUser(
			@PathVariable Integer userId, @Valid @RequestBody Address addressObj){
		
		Address addedAddress = addressService.addAddressToUser(userId, addressObj);
		
		return new ResponseEntity<Address>(addedAddress, HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{addressId}")
	public ResponseEntity<Address> updatedAddress(@Valid @RequestBody Address addObj,
			@PathVariable Integer addressId){
		
		Address updateAddress = addressService.updateAddress(addressId, addObj);
		
		return ResponseEntity.ok(updateAddress);
	}
	
	@DeleteMapping("/delete/{addressId}")
	public ResponseEntity<String> removeAddress(@PathVariable Integer addressId){
		
		addressService.removeAddress(addressId);
		
		return ResponseEntity.ok("Address Removed Successfully");
	}
	
	@GetMapping(value = "/{userId}")
	public ResponseEntity<List<Address>> getAllUserAddress(@PathVariable Integer userId){
		
		List<Address> allUserAddress = addressService.getAllUserAddress(userId);
		
		return ResponseEntity.ok(allUserAddress);
	}
}
