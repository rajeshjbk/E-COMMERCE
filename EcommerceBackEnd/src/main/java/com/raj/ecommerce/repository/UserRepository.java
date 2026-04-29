package com.raj.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raj.ecommerce.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	//With UserRepository.java ==> we can perform all CURD Operations
	
	/* Derived Query
	 * 
	 * Developer will write only abstract method and framework implement 
	 */
	
	public Optional<User> findByEmail(String email);
	//select * from tbl_user where user_email=email;
	//ResultSet Converted into Optional<User>
	
	
}
