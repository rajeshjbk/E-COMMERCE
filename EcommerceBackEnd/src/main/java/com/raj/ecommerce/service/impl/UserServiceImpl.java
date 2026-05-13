package com.raj.ecommerce.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.raj.ecommerce.DTO.AdminDTO;
import com.raj.ecommerce.DTO.CustomerDTO;
import com.raj.ecommerce.DTO.UserDTO;
import com.raj.ecommerce.model.User;
import com.raj.ecommerce.model.UserAccountStatus;
import com.raj.ecommerce.model.UserRole;
import com.raj.ecommerce.repository.UserRepository;
import com.raj.ecommerce.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	/*
	 * UserRepository userRepository = ioc.getUserRepository();
	 * UserServiceImpl.setUserRepository(userRepository);
	 */

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User addUser(CustomerDTO customer) throws Exception {

		User newCustomer = new User();
		newCustomer.setEmail(customer.getEmail());
		newCustomer.setPassword(passwordEncoder.encode(customer.getPassword()));
		newCustomer.setFirstName(customer.getFirstName());
		newCustomer.setLastName(customer.getLastName());
		newCustomer.setPhoneNumber(customer.getPhoneNumber());
		newCustomer.setRole(UserRole.ROLE_USER);
		newCustomer.setRegisterTime(LocalDateTime.now());
		newCustomer.setAccountStatus(UserAccountStatus.ACTIVE);

		//verify the given customer already exists or not
		Optional<User> userObj = userRepository.findByEmail(customer.getEmail());

		if(userObj.isPresent()) {

			throw new RuntimeErrorException(null,"User Email Already Exists");
		}
		
		return userRepository.save(newCustomer);
	}

	@Override
	public User addUserAdmin(AdminDTO adminUser) throws Exception {

		User newCustomer = new User();
		newCustomer.setEmail(adminUser.getEmail());
		newCustomer.setPassword(passwordEncoder.encode(adminUser.getPassword()));
		newCustomer.setFirstName(adminUser.getFirstName());
		newCustomer.setLastName(adminUser.getLastName());
		newCustomer.setPhoneNumber(adminUser.getPhoneNumber());
		newCustomer.setRole(UserRole.ROLE_ADMIN);
		newCustomer.setRegisterTime(LocalDateTime.now());
		newCustomer.setAccountStatus(UserAccountStatus.ACTIVE);

		//verify the given customer already exists or not
		Optional<User> userObj = userRepository.findByEmail(adminUser.getEmail());

		if(userObj.isPresent()) {

			throw new RuntimeErrorException(null,"Admin Email Already Exists");
		}
		return userRepository.save(newCustomer);	

	}

	@Override
	public User getUserByEmailId(String emailId) throws Exception {
		
		return userRepository.findByEmail(emailId).orElseThrow(()->new Exception("Email Id Not Found"));
	}

	@Override
	public User getUserDetails(Integer userId) throws Exception {
		
		return userRepository.findById(userId).orElseThrow(()->new Exception("User Id Not Found"));
	}
	
	@Override
	public User changePassword(Integer userId, UserDTO customer) throws Exception {
		
		User userObj = userRepository.findById(userId).orElseThrow(()-> new Exception("User Not Found"));
		//findById ==> select * from user where userid=userId
		
		if(customer.getNewPassword().length()>=5) {
			
			userObj.setPassword(passwordEncoder.encode(customer.getNewPassword()));
	        return userRepository.save(userObj);
		
		}else {
			throw new RuntimeErrorException(null,"Please Provide a Valid Password");
		}
	}

	@Override
	public List<User> getAllUserDetails() throws Exception {
		
		return userRepository.findAll();

	}

    @Override
    public String deactivate(Integer userId) throws Exception {
    	User existingUser = userRepository.findById(userId).orElseThrow(()->new Exception("User Id Not Found"));
    	existingUser.setAccountStatus(UserAccountStatus.DEACTIVE);
    	userRepository.save(existingUser);
    	
    	return "Account is Deactivated Successfully"; 
    }
}


