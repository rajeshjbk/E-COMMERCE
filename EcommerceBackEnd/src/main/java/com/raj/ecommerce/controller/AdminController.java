package com.raj.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raj.ecommerce.DTO.AdminDTO;
import com.raj.ecommerce.DTO.CustomerDTO;
import com.raj.ecommerce.DTO.UserDTO;
import com.raj.ecommerce.model.User;
import com.raj.ecommerce.service.UserService;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping(value = "/ecom/admin")
@CrossOrigin("*")
public class AdminController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping(value="signIn")
	public ResponseEntity<User> addUser(@RequestBody CustomerDTO user) throws Exception{
		
		//Encoded Password will be saved in the database
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User addedUser = userService.addUser(user);
		
		return ResponseEntity.ok(addedUser);
	}
	
	@PutMapping(value="/updatedPassword/{userId}")
	public ResponseEntity<User> updateUserPassword(
			@PathVariable("userId") Integer customerId,
			@RequestBody UserDTO userDto) throws Exception{
		
		User updatedUser = userService.changePassword(customerId, userDto);
		return ResponseEntity.ok(updatedUser);
	}	
}
