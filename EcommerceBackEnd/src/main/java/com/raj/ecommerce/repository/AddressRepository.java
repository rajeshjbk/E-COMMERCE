package com.raj.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.raj.ecommerce.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

	//For a User we want to get Address ==> Query
	
	/* 
	 * Query ==> Develop will write both both abstract method and implementation
	 * also at interface layer
	 * 
	 */
	
	@Query("SELECT a from Address a where a.user.userId=:userId")
	List<Address> getUserAddressList(Integer userId);
	
}
