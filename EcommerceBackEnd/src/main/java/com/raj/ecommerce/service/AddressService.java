package com.raj.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.raj.ecommerce.exception.AddressException;
import com.raj.ecommerce.model.Address;

public interface AddressService {

	public Address addAddressToUser(Integer userId, Address address) throws AddressException;
	
	public Address updateAddress(Integer userId, Address address);
	
	public void removeAddress(Integer addressId) throws AddressException;
	
	public List<Address> getAllUserAddress(Integer userId) throws AddressException;
	
} 
