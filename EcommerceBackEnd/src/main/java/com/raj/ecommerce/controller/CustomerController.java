package com.raj.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raj.ecommerce.DTO.CustomerDTO;
import com.raj.ecommerce.DTO.UserDTO;
import com.raj.ecommerce.model.User;
import com.raj.ecommerce.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/ecom/customers")
public class CustomerController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/add-customer")
	public ResponseEntity<User> addUser(@Valid @RequestBody CustomerDTO user) throws Exception{
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		User addUser = userService.addUser(user);
		
		return ResponseEntity.ok(addUser);
	}
	
	@PutMapping(value="/updatedPassword/{customerId}")
	public ResponseEntity<User> updateUserPassword(
			@PathVariable("customerId") Integer customerId,
			@Valid @RequestBody UserDTO userDto) throws Exception{
		
		User updatedUser = userService.changePassword(customerId, userDto);
		return ResponseEntity.ok(updatedUser);
	}	
	
	@DeleteMapping("/delete/{customerId}")
	public ResponseEntity<String> deactiveUser(@PathVariable("customerId") Integer customerId) throws Exception{
		
		String message = userService.deactivate(customerId);
		
		return ResponseEntity.ok(message);
	}
	
	@GetMapping("/get-all-customer")
	public ResponseEntity<List<User>>  getAllUserDetails() throws Exception{
		
		List<User> users = userService.getAllUserDetails();
		
		return ResponseEntity.ok(users);
	}
}
