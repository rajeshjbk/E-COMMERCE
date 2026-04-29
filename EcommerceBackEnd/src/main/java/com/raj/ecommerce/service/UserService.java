package com.raj.ecommerce.service;

import java.util.List;

import com.raj.ecommerce.DTO.AdminDTO;
import com.raj.ecommerce.DTO.CustomerDTO;
import com.raj.ecommerce.DTO.UserDTO;
import com.raj.ecommerce.model.User;

public interface UserService {

	//Adding Customer
	//insert into user ... values...(....)
	public User addUser(CustomerDTO customer) throws Exception;
	
	//Adding UserAdmin
	public User addUserAdmin(AdminDTO adminUser) throws Exception;
	
	//select * from user where email=....
	public User getUserByEmailId(String emailId) throws Exception;
	
	public User getUserDetails(Integer userId) throws Exception;
	
	//update user set pass=new....
	public User changePassword(Integer userId, UserDTO customer) throws Exception;
	
	//select * from user
	public List<User> getAllUserDetails() throws Exception;
	
	public String deactivate(Integer userId) throws Exception;
	
}
