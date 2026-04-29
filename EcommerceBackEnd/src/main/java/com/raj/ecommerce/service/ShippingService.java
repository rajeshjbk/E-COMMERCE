package com.raj.ecommerce.service;

import com.raj.ecommerce.DTO.ShippingDTO;
import com.raj.ecommerce.exception.ShippingException;
import com.raj.ecommerce.model.ShippingDetails;

public interface ShippingService {

	public ShippingDetails setShippingDetails(Integer orderId, Integer shipperId, ShippingDetails shippingObj) 
			throws ShippingException; 
	
	public ShippingDetails updateShippingDetails(Integer shippingId, ShippingDTO shippingDTO) 
			throws ShippingException; 
	
	public void deleteShippingDetails(Integer shippingId) throws ShippingException;
	
}
