package com.raj.ecommerce.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raj.ecommerce.exception.ShipperException;
import com.raj.ecommerce.model.Shipper;
import com.raj.ecommerce.repository.ShipperRepository;
import com.raj.ecommerce.repository.ShippingRepository;
import com.raj.ecommerce.service.ShipperService;

@Service
public class ShipperServiceImpl implements ShipperService {

	@Autowired
	private ShippingRepository shippingRepo;
	
	@Autowired
	private ShipperRepository shipperRepo;
	
	@Override
	public Shipper saveShipper(Shipper shipper) throws ShipperException {
		
		if(shipper == null) {
			
			throw new ShipperException("Shipper Object can't be Null");
		}
		
		return shipperRepo.save(shipper);
	}

	@Override
	public Shipper getShipperById(Integer shipperId) throws ShipperException {
		
		Shipper existingShipper = shipperRepo.findById(shipperId)
				.orElseThrow(()-> new ShipperException("Shipper Details Not Found"));
		
		return existingShipper;
	}

	@Override
	public List<Shipper> getAllShippers() throws ShipperException {
		
		return shipperRepo.findAll();
	}

	@Override
	public void deleteShipperById(Integer shipperId) throws ShipperException {
		
		Shipper existingShipper = shipperRepo.findById(shipperId)
				.orElseThrow(()-> new ShipperException("Shipper Details Not Found"));
        shipperRepo.delete(existingShipper);
	}

}
