package com.raj.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raj.ecommerce.DTO.UserSigninDetail;
import com.raj.ecommerce.exception.UserException;
import com.raj.ecommerce.model.User;
import com.raj.ecommerce.service.UserService;

@RestController
@RequestMapping(value="/ecom")
public class LoginController {

	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/signIn")
	public ResponseEntity<UserSigninDetail> getLoggedInCustomerDetailsHandler(Authentication authObj){
		
		try {
			
			User userObj = userService.getUserByEmailId(authObj.getName());
			UserSigninDetail signinSuccessData = new UserSigninDetail();
			signinSuccessData.setId(userObj.getUserId());
			signinSuccessData.setFirstName(userObj.getFirstName());
			signinSuccessData.setLastName(userObj.getLastName());
			signinSuccessData.setSignInStatus("SUCCESS");
			
			return new ResponseEntity<>(signinSuccessData, HttpStatus.OK);
		
		}catch (Exception e) {
			throw new UserException("Invalid Password");
		}
	}
}
