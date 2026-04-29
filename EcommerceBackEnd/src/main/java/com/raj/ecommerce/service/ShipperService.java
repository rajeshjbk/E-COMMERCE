package com.raj.ecommerce.service;

import java.util.List;

import com.raj.ecommerce.exception.ShipperException;
import com.raj.ecommerce.model.Shipper;

public interface ShipperService {

	public Shipper saveShipper(Shipper shipper) throws ShipperException;
	
	public Shipper getShipperById(Integer shipperId) throws ShipperException;
	
	public List<Shipper> getAllShippers() throws ShipperException;
	
	public void deleteShipperById(Integer shipperId) throws ShipperException;
	
}
