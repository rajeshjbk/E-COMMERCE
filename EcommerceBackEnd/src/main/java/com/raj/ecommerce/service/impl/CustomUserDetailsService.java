package com.raj.ecommerce.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.raj.ecommerce.controller.LoginController;
import com.raj.ecommerce.model.User;
import com.raj.ecommerce.repository.UserRepository;

//Framework Service Class for Dynamic Security ==> UserDetailsService
//Framework Authentication Object ==> UserDetails

@Service
public class CustomUserDetailsService implements UserDetailsService{

    private final LoginController loginController;

	@Autowired
	private UserRepository userRepo;

    CustomUserDetailsService(LoginController loginController) {
        this.loginController = loginController;
    }
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> userObj = userRepo.findByEmail(username);
		//The above API Makes a database call and gives the User Object. User is database Object
		
		if(userObj.isPresent()) {
			
			User dbUser = userObj.get();
			List<GrantedAuthority> roleList = new ArrayList<>();
			SimpleGrantedAuthority roleObj = new SimpleGrantedAuthority(dbUser.getRole().toString());
			roleList.add(roleObj);
			
			return new org.springframework.security.core.userdetails.User(dbUser.getEmail(), dbUser.getPassword(), roleList);
		
		}else {
			
			throw  new BadCredentialsException("User does not exists in the System... "+username);
		}
	}
}
